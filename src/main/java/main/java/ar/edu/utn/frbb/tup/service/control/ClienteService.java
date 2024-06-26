package main.java.ar.edu.utn.frbb.tup.service.control;

import java.time.LocalDate;

import main.java.ar.edu.utn.frbb.tup.model.*;
import main.java.ar.edu.utn.frbb.tup.persistence.*;
import main.java.ar.edu.utn.frbb.tup.exception.*;

public class ClienteService {



    public void darDeAltaCliente(Cliente cliente) throws ClienteAlreadyExistsException {
        if (SummitCliente.findByDni(String.valueOf(cliente.getDni())) != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + cliente.getDni());
        }

        if (cliente.getEdad() < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor a 18 años");
        }

        if (cliente.getNombre().isEmpty() || cliente.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El nombre y apellido del cliente son obligatorios");
        }

        // Verificar que la fecha de nacimiento no sea en el futuro
        if (cliente.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser en el futuro");
        }

        // Guardar cliente en la "base de datos" (archivo)
        SummitCliente.escribirEnArchivo(cliente);
    }

}

