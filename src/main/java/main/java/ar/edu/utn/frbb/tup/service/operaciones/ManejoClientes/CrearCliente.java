package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import main.java.ar.edu.utn.frbb.tup.persistence.SummitCliente;
import main.java.ar.edu.utn.frbb.tup.service.control.CuentaService;

public class CrearCliente {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public static void crearCliente(long dni, String nombre, String apellido, String fechaNacimiento,
            String tipoPersona, String banco, String fechaAlta) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            escritor.write(dni + "," + nombre + "," + apellido + "," + dni + "," + fechaNacimiento + "," + tipoPersona
                    + "," + banco + "," + fechaAlta);
            escritor.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
