package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MostrarTodosClientes {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            // Leer la cabecera (primera línea) y descartarla
            lector.readLine();

            // Leer cada línea del archivo
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");

                // Validar que la línea tiene la cantidad esperada de datos
                if (datos.length < 8) {
                    System.err.println("Línea mal formada: " + linea);
                    continue;
                }

                Cliente cliente = new Cliente();
                cliente.setDni((datos[3])); // Convertir DNI a Long
                cliente.setNombre(datos[1]);
                cliente.setApellido(datos[2]);
                cliente.setFechaNacimiento(LocalDate.parse(datos[4]));

                cliente.setTipoPersona(TipoPersona.fromString(datos[5]));
                cliente.setBanco(datos[6]);

                cliente.setFechaAlta(LocalDate.parse(datos[7].replace(".", ""))); // Eliminar el punto del final

                clientes.add(cliente);
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }

        return clientes;
    }
}