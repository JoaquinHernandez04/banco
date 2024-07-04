package main.java.ar.edu.utn.frbb.tup.persistence;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SummitMovimientos {
    private static final String NOMBRE_ARCHIVO_CUENTAS = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Cuentas.txt";
    private static final String NOMBRE_ARCHIVO_MOVIMIENTOS = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Movimientos.txt";

    public static void registrarMovimiento(String CBU, double monto, String tipoMovimiento) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO_MOVIMIENTOS, true))) {
            escritor.write(CBU + "," + monto + "," + tipoMovimiento);
            escritor.newLine();
            System.out.println("Movimiento registrado en " + NOMBRE_ARCHIVO_MOVIMIENTOS + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo de movimientos: " + ex.getMessage());
        }

        actualizarSaldo(CBU, monto);
    }

    public static void registrarMovimientoTransferencia(String CBU_INICIO, String CBU_FINAL, double monto,
            String tipoMovimiento) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO_MOVIMIENTOS, true))) {
            escritor.write(CBU_INICIO + "," + CBU_FINAL + "," + monto + "," + tipoMovimiento);
            escritor.newLine();
            System.out.println("Movimiento registrado en " + NOMBRE_ARCHIVO_MOVIMIENTOS + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo de movimientos: " + ex.getMessage());
        }

        actualizarSaldo(CBU_INICIO, -monto); // Resta el monto al CBU_INICIO
        actualizarSaldo(CBU_FINAL, monto); // Suma el monto al CBU_FINAL
    }

    public static void registrarMovimientoRetiro(String CBU, double monto, String tipoMovimiento) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO_MOVIMIENTOS, true))) {
            escritor.write(CBU + "," + monto + "," + tipoMovimiento);
            escritor.newLine();
            System.out.println("Movimiento registrado en " + NOMBRE_ARCHIVO_MOVIMIENTOS + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo de movimientos: " + ex.getMessage());
        }

        actualizarSaldo(CBU, -monto); // Resta el monto al saldo de la cuenta
    }

    public static String buscarCuentaPorCBU(String CBU) {
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_CUENTAS))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 0) {
                    String cbuCuenta = campos[0];
                    if (cbuCuenta.equals(CBU)) {
                        return linea;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo adecuado de excepción
        }
        return null; // Si no se encuentra la cuenta
    }

    public static double obtenerSaldo(String CBU) {
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_CUENTAS))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 3) {
                    String cbuCuenta = campos[0];
                    if (cbuCuenta.equals(CBU)) {
                        return Double.parseDouble(campos[3]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo adecuado de excepción
        }
        return 0.0; // Si no se encuentra la cuenta
    }

    private static void actualizarSaldo(String CBU, double monto) {
        // Método para actualizar el saldo en el archivo de cuentas
        try {
            BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_CUENTAS));
            BufferedWriter escritor = new BufferedWriter(new FileWriter("temp.txt"));

            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 3 && campos[0].equals(CBU)) {
                    double saldoActual = Double.parseDouble(campos[3]);
                    campos[3] = String.valueOf(saldoActual + monto);
                    linea = String.join(",", campos);
                }
                escritor.write(linea + "\n");
            }

            lector.close();
            escritor.close();

            Files.move(Paths.get("temp.txt"), Paths.get(NOMBRE_ARCHIVO_CUENTAS),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo adecuado de excepción
        }
    }
}
