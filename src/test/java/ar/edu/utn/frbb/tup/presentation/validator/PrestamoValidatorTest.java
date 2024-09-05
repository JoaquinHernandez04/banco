package ar.edu.utn.frbb.tup.presentation.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrestamoValidatorTest {

    private PrestamoValidator prestamoValidator;

    @BeforeEach
    public void setUp() {
        prestamoValidator = new PrestamoValidator();
    }

    @Test
    public void testPrestamoValidatorSuccess() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(12345678);
        prestamoDto.setMontoPrestamo(10000.0);
        prestamoDto.setPlazoEnMeses(12);
        prestamoDto.setMoneda("ARS");

        assertDoesNotThrow(() -> prestamoValidator.validarPrestamo(prestamoDto));
    }

    @Test
    public void testPrestamoValidatorErrorDniNegativo() {
        PrestamoDto prestamoDto = new PrestamoDto();
        Prestamo prestamo = new Prestamo();
        prestamoDto.setNumeroCliente(-12345678);
        prestamoDto.setMontoPrestamo(10000.0);
        prestamoDto.setPlazoEnMeses(12);
        prestamoDto.setMoneda("ARS");
        prestamo.setEstado("PENDIENTE");
        assertThrows(IllegalArgumentException.class, () -> prestamoValidator.validarPrestamo(prestamoDto));
    }

    @Test
    public void testPrestamoValidatorMontoMenorOIgualACero() {
        Prestamo prestamo = new Prestamo();
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(12345678);
        prestamoDto.setMontoPrestamo(0.0);
        prestamoDto.setPlazoEnMeses(12);
        prestamoDto.setMoneda("ARS");
        prestamo.setEstado("PENDIENTE");

        assertThrows(IllegalArgumentException.class, () -> prestamoValidator.validarPrestamo(prestamoDto));
    }

    @Test
    public void testPrestamoValidatorPlazoMenorOIgualACero() {
        PrestamoDto prestamoDto = new PrestamoDto();
        Prestamo prestamo = new Prestamo();
        prestamoDto.setNumeroCliente(12345678);
        prestamoDto.setMontoPrestamo(10000.0);
        prestamoDto.setPlazoEnMeses(0);
        prestamoDto.setMoneda("ARS");
        prestamo.setEstado("PENDIENTE");

        assertThrows(IllegalArgumentException.class, () -> prestamoValidator.validarPrestamo(prestamoDto));
    }

    @Test
    public void testPrestamoValidatorMonedaNull() {
        PrestamoDto prestamoDto = new PrestamoDto();
        Prestamo prestamo = new Prestamo();
        prestamoDto.setNumeroCliente(12345678);
        prestamoDto.setMontoPrestamo(10000.0);
        prestamoDto.setPlazoEnMeses(12);
        prestamoDto.setMoneda(null);
        prestamo.setEstado("PENDIENTE");

        assertThrows(IllegalArgumentException.class, () -> prestamoValidator.validarPrestamo(prestamoDto));
    }

}