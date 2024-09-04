package ar.edu.utn.frbb.tup.presentation.validator;

import org.springframework.stereotype.Component;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoDto;

@Component
public class PrestamoValidator {

    public void validarPrestamo(PrestamoDto prestamoDto) {
        // Validar que el DNI del cliente sea un número positivo
        validarDNI(prestamoDto.getNumeroCliente());

        // Validar que el monto del préstamo sea mayor que cero
        if (prestamoDto.getMontoPrestamo() <= 0) {
            throw new IllegalArgumentException("El monto del préstamo debe ser mayor que cero");
        }

        // Validar que el plazo del préstamo sea positivo
        if (prestamoDto.getPlazoEnMeses() <= 0) {
            throw new IllegalArgumentException("El plazo del préstamo debe ser mayor que cero");
        }

        // Validar que la moneda no sea nula o vacía
        if (prestamoDto.getMoneda() == null || prestamoDto.getMoneda().isEmpty()) {
            throw new IllegalArgumentException("La moneda del préstamo no puede estar vacía");
        }

    }

    // Método privado para validar que el DNI sea un número positivo
    private void validarDNI(long dni) {
        if (dni <= 0) {
            throw new IllegalArgumentException("El DNI debe ser un número positivo");
        }
    }
}