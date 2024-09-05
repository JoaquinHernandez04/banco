package ar.edu.utn.frbb.tup.presentation.validator;

import org.springframework.stereotype.Component;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoDto;

@Component
public class PrestamoValidator {

    public void validarPrestamo(PrestamoDto prestamoDto) {

        validarDNI(prestamoDto.getNumeroCliente());


        if (prestamoDto.getMontoPrestamo() <= 0) {
            throw new IllegalArgumentException("El monto del préstamo debe ser mayor que cero");
        }


        if (prestamoDto.getPlazoEnMeses() <= 0) {
            throw new IllegalArgumentException("El plazo del préstamo debe ser mayor que cero");
        }


        if (prestamoDto.getMoneda() == null || prestamoDto.getMoneda().isEmpty()) {
            throw new IllegalArgumentException("La moneda del préstamo no puede estar vacía");
        }

    }


    private void validarDNI(long dni) {
        if (dni <= 0) {
            throw new IllegalArgumentException("El DNI debe ser un número positivo");
        }
    }
}