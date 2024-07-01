package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ModificarCliente {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public static void modificarCliente(String dni, String nombre, String apellido, String nuevoDni,
            LocalDate fechaNacimiento, String tipoPersona, String banco, LocalDate fechaAlta) {
        List<String> nuevosDatos = new ArrayList<>();
        boolean clienteEncontrado = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 1 && campos[3].trim().equals(dni.trim())) {
                    clienteEncontrado = true;
                    // Actualizar los campos según los parámetros recibidos
                    campos[1] = nombre;
                    campos[2] = apellido;
                    campos[3] = nuevoDni;
                    campos[4] = fechaNacimiento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    campos[5] = tipoPersona;
                    campos[6] = banco;
                    campos[7] = fechaAlta.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                nuevosDatos.add(String.join(",", campos));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clienteEncontrado) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
                for (String datos : nuevosDatos) {
                    escritor.write(datos);
                    escritor.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Cliente modificado con éxito.");
        } else {
            System.out.println("No se encontraron clientes con el DNI: " + dni);
        }
    }
}