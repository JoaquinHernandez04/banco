package ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrestamoServiceTest {

    @Mock
    private PrestamoDao prestamoDao;

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private PrestamoService prestamoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSolicitarPrestamo_ClienteNoEncontrado() throws ClienteNoEncontradoException {
        when(clienteService.mostrarCliente(anyLong())).thenReturn(null);

        PrestamoDto prestamoDto = getPrestamoDto();

        assertThrows(ClienteNoEncontradoException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));

        verify(clienteService, times(1)).mostrarCliente(anyLong());
        verify(cuentaDao, times(0)).actualizarBalanceCuenta(anyLong(), any(Double.class));
    }

    @Test
    public void testVerEstadoPrestamosPorCbu() {
        Prestamo prestamo1 = new Prestamo();
        Prestamo prestamo2 = new Prestamo();
        when(prestamoDao.obtenerPrestamoPorCbu(anyLong())).thenReturn(List.of(prestamo1, prestamo2));

        List<Prestamo> prestamos = prestamoService.verEstadoPrestamosPorCbu(12345678L);

        verify(prestamoDao, times(1)).obtenerPrestamoPorCbu(anyLong());
        assertEquals(2, prestamos.size());
    }

    @Test
    public void testConsultarPrestamosPorCliente_ClienteNoEncontrado() throws ClienteNoEncontradoException {
        when(clienteService.mostrarCliente(anyLong())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> prestamoService.consultarPrestamosPorCliente(12345678L));

        verify(clienteService, times(1)).mostrarCliente(anyLong());
        verify(prestamoDao, times(0)).obtenerPrestamoPorCbu(anyLong());
    }

    @Test
    public void testConsultarPrestamosPorCliente() throws ClienteNoEncontradoException {
        Cliente cliente = getCliente();
        when(clienteService.mostrarCliente(anyLong())).thenReturn(cliente);

        Prestamo prestamo1 = new Prestamo();
        Prestamo prestamo2 = new Prestamo();
        when(prestamoDao.obtenerPrestamoPorCbu(anyLong())).thenReturn(List.of(prestamo1, prestamo2));

        List<Prestamo> prestamos = prestamoService.consultarPrestamosPorCliente(12345678L);

        verify(prestamoDao, times(1)).obtenerPrestamoPorCbu(anyLong());
        assertEquals(2, prestamos.size());
    }

    // Métodos auxiliares para crear objetos de prueba

    private PrestamoDto getPrestamoDto() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(12345678L);
        prestamoDto.setMontoPrestamo(50000.0);
        prestamoDto.setPlazoEnMeses(12);
        prestamoDto.setMoneda("ARS");
        return prestamoDto;
    }

    private Cliente getCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        return cliente;
    }

    private Cuenta getCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setCBU(12345678L);
        cuenta.setBalance(50000.0);
        return cuenta;
    }
}