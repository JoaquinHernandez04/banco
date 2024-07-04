package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BorrarCuenta {
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Cuentas.txt";

    public static boolean borrarCuenta(String CBU) {
        List<String> cuentas = new ArrayList<>();
        boolean cuentaEncontrada = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (!campos[0].equals(CBU)) {
                    cuentas.add(linea);
                } else {
                    cuentaEncontrada = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (cuentaEncontrada) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
                for (String cuenta : cuentas) {
                    escritor.write(cuenta);
                    escritor.newLine();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}