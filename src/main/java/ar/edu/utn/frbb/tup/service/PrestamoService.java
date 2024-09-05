package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CalificacionCrediticiaRechazadaException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.PlanPago;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoEstadoDto;
import ar.edu.utn.frbb.tup.exception.CuentaSinSaldoException;
import ar.edu.utn.frbb.tup.exception.MonedaInvalidaException;
import ar.edu.utn.frbb.tup.exception.TipoMonedasInvalidasException;
import java.util.ArrayList;
import java.util.List;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
@Service
public class PrestamoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private PrestamoDao prestamoDao;
    @Autowired
    private CuentaDao cuentaDao;

    // Servicio externo simulado para calificación crediticia
    private boolean verificarCalificacionCrediticia(String dni) {
        // Simula la calificación crediticia
        return Integer.parseInt(dni.substring(dni.length() - 1)) % 2 == 0;
    }

    // revisar aca el error
    public List<Prestamo> verEstadoPrestamosPorCbu(long cbu) {
        return prestamoDao.obtenerPrestamoPorCbu(cbu);
    }



    public Prestamo solicitarPrestamo(PrestamoDto prestamoDto)
            throws ClienteNoEncontradoException, CuentaNoEncontradaException, CalificacionCrediticiaRechazadaException {

        // Obtener el número de cliente del DTO
        long numeroCliente = prestamoDto.getNumeroCliente();
        double montoPrestamo = prestamoDto.getMontoPrestamo();
        int plazoMeses = prestamoDto.getPlazoEnMeses();
        String moneda = prestamoDto.getMoneda();

    // Verificar si el cliente existe
    Cliente cliente = clienteService.mostrarCliente(numeroCliente);
    if (cliente == null) {
        throw new ClienteNoEncontradoException("Cliente no encontrado");
    }

    cliente.setId(numeroCliente); // Asignar el número de cliente al cliente
    // Verificar si el cliente tiene una cuenta en la moneda solicitada
    List<Cuenta> cuentasCliente = cuentaService.mostrarCuenta(cliente.getDni());
    Cuenta cuenta = cuentasCliente.stream()
            .filter(c -> c.getMoneda().toString().equalsIgnoreCase(moneda))
            .findFirst()
            .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta en la moneda solicitada no encontrada"));

    // Verificar calificación crediticia
    if (!verificarCalificacionCrediticia(String.valueOf(cliente.getDni()))) {
        throw new CalificacionCrediticiaRechazadaException(
                "El cliente no cumple con los requisitos de calificación crediticia");
    }

    // Calcular el plan de pagos con un interés fijo del 5% anual
    double interesAnual = 0.05;
    double tasaMensual = interesAnual / 12;
    double cuotaMensual = (montoPrestamo * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -plazoMeses));

    List<PlanPago> planPagos = new ArrayList<>();
    for (int i = 1; i <= plazoMeses; i++) {
        planPagos.add(new PlanPago(i, cuotaMensual));
    }

    // Crear el préstamo y actualizar el saldo de la cuenta
    Prestamo prestamo = new Prestamo(montoPrestamo, plazoMeses, planPagos, "APROBADO", cliente);

    // Guardar el préstamo en la capa de persistencia
    double nuevoBalance = cuenta.getBalance() + montoPrestamo;
    cuentaDao.actualizarBalanceCuenta(cuenta.getCBU(), nuevoBalance);
    prestamoDao.guardarPrestamo(prestamo);
    return prestamo;
}

    public List<Prestamo> consultarPrestamosPorCliente(long numeroCliente) throws ClienteNoEncontradoException {
        Cliente cliente = clienteService.mostrarCliente(numeroCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }

        return prestamoDao.obtenerPrestamoPorCbu(cliente.getId());
    }

    public List<PrestamoEstadoDto> consultarEstadoPrestamos(long numeroCliente) throws ClienteNoEncontradoException {
        // Obtener préstamos del cliente usando el DAO
        List<PrestamoEstadoDto> prestamos = prestamoDao.obtenerPrestamosPorCliente(numeroCliente);

        if (prestamos == null || prestamos.isEmpty()) {
            throw new ClienteNoEncontradoException(
                    "No se encontraron préstamos para el cliente con número: " + numeroCliente);
        }

        return prestamos;
    }

    // Método auxiliar para convertir de Cuenta a CuentaDto

}