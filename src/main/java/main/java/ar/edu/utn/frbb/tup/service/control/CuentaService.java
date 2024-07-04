package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitCuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitCliente;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service

public class CuentaService {

    public void darDeAltaCuenta(Cuenta cuenta, String dni) throws ClienteAlreadyExistsException {
        Cliente clienteExistente = SummitCliente.findByDni(dni);
        if (clienteExistente == null) {
            throw new IllegalArgumentException("El titular de la cuenta no existe");
        }

        cuenta.setTitular(clienteExistente);

        // Validaciones de cliente
        if (clienteExistente.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }

        if (clienteExistente.getEdad() < 18) {
            throw new IllegalArgumentException("El titular de la cuenta debe ser mayor a 18 años");
        }

        if (clienteExistente.getNombre().isEmpty() || clienteExistente.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El nombre y apellido del titular de la cuenta son obligatorios");
        }

        if (clienteExistente.getFechaNacimiento().isAfter(LocalDateTime.now().toLocalDate())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser en el futuro");
        }

        // Asignar CBU y fecha de creación
        cuenta.setCBU();
        cuenta.setFechaCreacion(LocalDateTime.now());

        // Guardar cuenta en la "base de datos" (archivo)
        SummitCuenta.escribirEnArchivo(cuenta);
    }
}