package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import java.io.*;
import java.util.*;

public class BorrarCliente {
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public static ResponseEntity<String> borrarCliente(String dni) {
        List<String> clientes = new ArrayList<>();
        boolean clienteEncontrado = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (!campos[3].equals(dni)) {
                    clientes.add(linea);
                } else {
                    clienteEncontrado = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al intentar leer el archivo de clientes",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (clienteEncontrado) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
                for (String cliente : clientes) {
                    escritor.write(cliente);
                    escritor.newLine();
                }
                return new ResponseEntity<>("El cliente con DNI " + dni + " ha sido eliminado.", HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error al intentar escribir en el archivo de clientes",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("El cliente con DNI " + dni + " no existe.", HttpStatus.NOT_FOUND);
        }
    }
}