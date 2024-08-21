package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CalificacionCrediticiaRechazadaException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private PrestamoDao prestamoDao;

    // Servicio externo simulado para calificación crediticia
    private boolean verificarCalificacionCrediticia(String dni) {
        // Simulación: Clientes con DNI que terminan en número impar tienen mala
        // calificación
        return Integer.parseInt(dni.substring(dni.length() - 1)) % 2 == 0;
    }

    public Prestamo solicitarPrestamo(long numeroCliente, double montoPrestamo, String moneda, int plazoMeses)
            throws ClienteNoEncontradoException, CuentaNoEncontradaException, CalificacionCrediticiaRechazadaException {

        // Verificar si el cliente existe
        Cliente cliente = clienteService.mostrarCliente(numeroCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }

        // Verificar si el cliente tiene una cuenta en la moneda solicitada
        List<Cuenta> cuentasCliente = cuentaService.mostrarCuenta(cliente.getDni());
        Cuenta cuenta = null;
        for (Cuenta c : cuentasCliente) {
            if (c.getMoneda().toString().equals(moneda)) {
                cuenta = c;
                break;
            }
        }

        if (cuenta == null) {
            throw new CuentaNoEncontradaException("Cuenta en la moneda solicitada no encontrada");
        }

        // Verificar calificación crediticia
        if (!verificarCalificacionCrediticia(String.valueOf(cliente.getDni()))) {
            throw new CalificacionCrediticiaRechazadaException(
                    "El cliente no cumple con los requisitos de calificación crediticia");
        }

        // Calcular el plan de pagos con un interés fijo del 5% anual
        double interesAnual = 0.05;
        double tasaMensual = interesAnual / 12;
        double cuotaMensual = (montoPrestamo * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -plazoMeses));

        List<Double> planPagos = new ArrayList<>();
        for (int i = 0; i < plazoMeses; i++) {
            planPagos.add(cuotaMensual);
        }

        // Crear el préstamo y actualizar el saldo de la cuenta
        Prestamo prestamo = new Prestamo(montoPrestamo, plazoMeses, planPagos, "APROBADO", cliente);
        cuenta.setBalance(cuenta.getBalance() + montoPrestamo);

        // Convertir Cuenta a CuentaDto y dar de alta la cuenta actualizada
        cuentaService.darDeAltaCuenta(convertirCuentaADto(cuenta));

        // Guardar el préstamo en la capa de persistencia
        prestamoDao.guardarPrestamo(prestamo);

        return prestamo;
    }

    public List<Prestamo> consultarPrestamosPorCliente(long numeroCliente) throws ClienteNoEncontradoException {
        Cliente cliente = clienteService.mostrarCliente(numeroCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }

        return prestamoDao.obtenerPrestamosPorCliente(cliente.getId());
    }

    // Método auxiliar para convertir de Cuenta a CuentaDto
    private CuentaDto convertirCuentaADto(Cuenta cuenta) {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setCbu(cuenta.getCBU());
        cuentaDto.setNombre(cuenta.getNombre());
        cuentaDto.setTipoCuenta(cuenta.getTipoCuenta().toString());
        cuentaDto.setBalance(cuenta.getBalance());
        cuentaDto.setMoneda(cuenta.getMoneda().toString());
        cuentaDto.setFechaCreacion(cuenta.getFechaCreacion().toString());
        cuentaDto.setDniTitular(cuenta.getDniTitular());
        return cuentaDto;
    }
}