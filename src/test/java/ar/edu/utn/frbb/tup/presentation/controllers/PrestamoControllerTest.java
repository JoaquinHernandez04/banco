package ar.edu.utn.frbb.tup.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.exception.CalificacionCrediticiaRechazadaException;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.TipoMonedasInvalidasException;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClientePrestamosDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoEstadoDto;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import ar.edu.utn.frbb.tup.presentation.validator.PrestamoValidator;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrestamoControllerTest {

    @Mock
    PrestamoService prestamoService;

    @Mock
    PrestamoValidator prestamoValidator;

    @InjectMocks
    PrestamoController prestamoController;

    private PrestamoDto prestamoDto;
    private Prestamo prestamo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(42958792L);
        prestamoDto.setPlazoEnMeses(3);
        prestamoDto.setMontoPrestamo(500);
        prestamoDto.setMoneda("ARS");

        prestamo = new Prestamo();
    }

    @Test
    public void testRealizarPrestamo() throws CuentaNoEncontradaException, TipoMonedasInvalidasException,
            ClienteNoEncontradoException, CalificacionCrediticiaRechazadaException {

        when(prestamoService.solicitarPrestamo(any(PrestamoDto.class))).thenReturn(prestamo);

        ResponseEntity<Prestamo> response = prestamoController.realizarPrestamo(prestamoDto);

        verify(prestamoValidator, times(1)).validarPrestamo(prestamoDto);
        verify(prestamoService, times(1)).solicitarPrestamo(prestamoDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(prestamo, response.getBody());
    }

    @Test
    public void testRealizarPrestamoError() throws CuentaNoEncontradaException, TipoMonedasInvalidasException,
            ClienteNoEncontradoException, CalificacionCrediticiaRechazadaException {

        doThrow(CalificacionCrediticiaRechazadaException.class).when(prestamoService)
                .solicitarPrestamo(any(PrestamoDto.class));

        assertThrows(CalificacionCrediticiaRechazadaException.class,
                () -> prestamoController.realizarPrestamo(prestamoDto));

        verify(prestamoValidator, times(1)).validarPrestamo(prestamoDto);
        verify(prestamoService, times(1)).solicitarPrestamo(prestamoDto);
    }

    @Test
    public void testConsultarEstadoPrestamos() throws ClienteNoEncontradoException {

        List<PrestamoEstadoDto> prestamos = List.of(new PrestamoEstadoDto());
        when(prestamoService.consultarEstadoPrestamos(anyLong())).thenReturn(prestamos);

        ResponseEntity<ClientePrestamosDto> response = prestamoController.consultarEstadoPrestamos(42958792L);

        verify(prestamoService, times(1)).consultarEstadoPrestamos(anyLong());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(42958792L, response.getBody().getNumeroCliente());
        assertEquals(prestamos, response.getBody().getPrestamos());
    }

    @Test
    public void testConsultarEstadoPrestamosError() throws ClienteNoEncontradoException {

        doThrow(ClienteNoEncontradoException.class).when(prestamoService).consultarEstadoPrestamos(anyLong());

        assertThrows(ClienteNoEncontradoException.class, () -> prestamoController.consultarEstadoPrestamos(42958792L));

        verify(prestamoService, times(1)).consultarEstadoPrestamos(anyLong());
    }
}
