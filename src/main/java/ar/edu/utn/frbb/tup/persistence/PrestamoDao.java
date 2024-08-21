package ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.Cliente;

@Repository
public class PrestamoDao {
    private static final String PRESTAMOSTXT = "C:\\Users\\joaqu\\Desktop\\banco\\src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Prestamo.txt";

    public void guardarPrestamo(Prestamo prestamo) {
        boolean archivoNuevo = !(new File(PRESTAMOSTXT).exists());

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(PRESTAMOSTXT, true))) {
            if (archivoNuevo) {
                escritor.write("monto,plazoMeses,estado,clienteId,planPagos");
                escritor.newLine();
            }

            escritor.write(prestamo.getMonto() + ",");
            escritor.write(prestamo.getPlazoMeses() + ",");
            escritor.write(prestamo.getEstado() + ",");
            escritor.write(prestamo.getCliente().getId() + ",");
            escritor.write(prestamo.getPlanPagos().toString().replace("[", "").replace("]", ""));
            escritor.newLine();

            System.out.println("Datos del préstamo guardados en " + PRESTAMOSTXT + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }

    public List<Prestamo> obtenerPrestamosPorCliente(long clienteId) {
        List<Prestamo> prestamos = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(PRESTAMOSTXT))) {
            String linea;
            lector.readLine(); // Leer la cabecera

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Long.parseLong(datos[3]) == clienteId) {
                    prestamos.add(parsePrestamoToObjet(datos));
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de préstamos: " + ex.getMessage());
        }

        return prestamos;
    }

    private Prestamo parsePrestamoToObjet(String[] datos) {
        Prestamo prestamo = new Prestamo();
        prestamo.setMonto(Double.parseDouble(datos[0]));
        prestamo.setPlazoMeses(Integer.parseInt(datos[1]));
        prestamo.setEstado(datos[2]);

        // Aquí deberías obtener el cliente por su ID (datos[3])
        Cliente cliente = new Cliente(); // Placeholder, obtener cliente real del servicio
        cliente.setId(Long.parseLong(datos[3]));
        prestamo.setCliente(cliente);

        String[] pagos = datos[4].split(" ");
        List<Double> planPagos = new ArrayList<>();
        for (String pago : pagos) {
            planPagos.add(Double.parseDouble(pago.trim()));
        }
        prestamo.setPlanPagos(planPagos);

        return prestamo;
    }
}