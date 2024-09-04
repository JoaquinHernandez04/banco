package ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.PlanPago;

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
            // Cambiar cómo se escribe el plan de pagos
            escritor.write(prestamo.getPlanPagos().stream()
                    .map(planPago -> planPago.getCuotaNro() + ":" + planPago.getMonto())
                    .collect(Collectors.joining(";"))); // Formato: cuotaNro:monto;cuotaNro:monto;...
            escritor.newLine();

            System.out.println("Datos del préstamo guardados en " + PRESTAMOSTXT + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }

    public List<Prestamo> obtenerPrestamoPorCbu(long cbu) {
        List<Prestamo> prestamos = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(PRESTAMOSTXT))) {
            String linea;
            lector.readLine(); // Leer la línea de encabezado
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[3]) == cbu) {
                    Prestamo prestamo = parsePrestamoToObjet(datos);
                    prestamos.add(prestamo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prestamos;
    }

    public void borrarPrestamo(long CBU) {
        List<String> prestamoStr = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(PRESTAMOSTXT))) {
            String linea = lector.readLine();
            prestamoStr.add(linea); // Agrega la línea de encabezado
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[3]) != CBU) { // Comparación corregida a campos[3]
                    prestamoStr.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (!prestamoStr.isEmpty()) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(PRESTAMOSTXT))) {
                for (String prestamosStr : prestamoStr) {
                    escritor.write(prestamosStr);
                    escritor.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        }
    }

    private Prestamo parsePrestamoToObjet(String[] datos) {
        Prestamo prestamo = new Prestamo();
        prestamo.setMonto(Double.parseDouble(datos[0]));
        prestamo.setPlazoMeses(Integer.parseInt(datos[1]));
        prestamo.setEstado(datos[2]);

        Cliente cliente = new Cliente(); // Placeholder, obtener cliente real del servicio
        cliente.setId(Long.parseLong(datos[3]));
        prestamo.setCliente(cliente);

        // Cambiar cómo se parsea el plan de pagos
        String[] pagos = datos[4].split(";"); // Divide el plan de pagos por el punto y coma
        List<PlanPago> planPagos = new ArrayList<>();
        for (String pago : pagos) {
            String[] detallesPago = pago.split(":"); // Divide cada cuota por el colon
            int cuotaNro = Integer.parseInt(detallesPago[0]);
            double monto = Double.parseDouble(detallesPago[1]);
            planPagos.add(new PlanPago(cuotaNro, monto)); // Agrega un nuevo PlanPago a la lista
        }
        prestamo.setPlanPagos(planPagos);

        return prestamo;
    }
}