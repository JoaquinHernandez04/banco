package main.java.ar.edu.utn.frbb.tup.service.operaciones.modulos;

import main.java.ar.edu.utn.frbb.tup.persistence.SummitMovimientos;

public class Deposito {

    private static final String TIPO_MOVIMIENTO = "DEPOSITO";

    public static void realizarDeposito(String CBU, double monto) {
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo.");
            return;
        }

        // Registrar el movimiento
        SummitMovimientos.registrarMovimiento(CBU, monto, TIPO_MOVIMIENTO);
    }

}