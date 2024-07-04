package main.java.ar.edu.utn.frbb.tup.service.operaciones.modulos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitMovimientos;

public class Retiro {
    private static final String TIPO_MOVIMIENTO = "RETIRO";
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Cuentas.txt";

    public static void realizarRetiro(String cbu, double monto) {
        String cbuValidado = SummitMovimientos.buscarCuentaPorCBU(cbu);

        if (cbuValidado == null) {
            System.out.println("El CBU ingresado no existe.");
            return;
        }

        // Registrar el movimiento de retiro
        SummitMovimientos.registrarMovimientoRetiro(cbuValidado, monto, TIPO_MOVIMIENTO);


        actualizarArchivoCuentas(cbuValidado, monto);
    }

    public static void actualizarArchivoCuentas(String cbuValidado, double monto) {
        List<String> lineas = new ArrayList<>();
        boolean cuentaEncontrada = false;

        // Leer el archivo Cuentas.txt y actualizar el balance
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 6) {
                    String cbuCuenta = campos[0];
                    if (cbuCuenta.equals(cbuValidado)) {
                        double balanceActual = Double.parseDouble(campos[3]);
                        double nuevoBalance = balanceActual - monto;
                        campos[3] = String.valueOf(nuevoBalance);
                        cuentaEncontrada = true;
                    }
                }
                lineas.add(String.join(",", campos));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si la cuenta fue encontrada, sobrescribir el archivo con los nuevos datos
        if (cuentaEncontrada) {
            try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get(NOMBRE_ARCHIVO))) {
                for (String linea : lineas) {
                    escritor.write(linea);
                    escritor.newLine();
                }
                System.out.println("El balance ha sido actualizado correctamente.");
            } catch (IOException e) {
                System.err.println("Error al escribir en el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró una cuenta con el CBU proporcionado.");
        }
    }
}