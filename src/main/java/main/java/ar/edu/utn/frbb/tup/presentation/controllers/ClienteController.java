package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import main.java.ar.edu.utn.frbb.tup.service.control.ClienteService;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes.BorrarCliente;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes.ModificarCliente;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes.MostrarTodosClientes;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitCliente;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService = new ClienteService();
    private MostrarTodosClientes mostrarCliente = new MostrarTodosClientes();

    @PostMapping("/CrearCliente")
    public ResponseEntity<String> crearCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.darDeAltaCliente(cliente);
            return new ResponseEntity<>("Cliente creado: " + cliente.getNombre() + " " + cliente.getApellido(),
                    HttpStatus.CREATED);
        } catch (ClienteAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/MostrarClientes")
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = mostrarCliente.obtenerTodosLosClientes();

        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        }
    }

    @GetMapping("/MostrarCliente/{id}")
    public ResponseEntity<Cliente> mostrarClientePorId(@PathVariable("id") String id) {
        Cliente cliente = SummitCliente.findByDni(id);

        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }
    }

    @DeleteMapping("/EliminarCliente/{dni}")
    public ResponseEntity<String> eliminarClientePorDni(@PathVariable("dni") String dni) {
        BorrarCliente.borrarCliente(dni);
        return new ResponseEntity<>("Operación de eliminación del cliente con DNI " + dni + " ejecutada.",
                HttpStatus.OK);
    }

    @PutMapping("/ModificarCliente/{dni}")
    public ResponseEntity<String> modificarCliente(@PathVariable("dni") String dni, @RequestBody Cliente cliente) {
        try {
            // Verificar si el cliente existe y obtener su información actual
            Cliente clienteExistente = SummitCliente.findByDni(dni);
            if (clienteExistente == null) {
                return new ResponseEntity<>("No se encontró ningún cliente con DNI " + dni, HttpStatus.NOT_FOUND);
            }

            // Validar si se proporcionó un nuevo DNI y si es diferente al actual
            if (cliente.getDni() != null && !cliente.getDni().equals(clienteExistente.getDni())) {
                // Verificar si el nuevo DNI ya está en uso por otro cliente
                Cliente clienteConNuevoDni = SummitCliente.findByDni(cliente.getDni());
                if (clienteConNuevoDni != null) {
                    return new ResponseEntity<>("Ya existe un cliente con DNI " + cliente.getDni(),
                            HttpStatus.CONFLICT);
                }
            }

            clienteService.ValidardarDeAltaCliente(cliente);
            ModificarCliente.modificarCliente(
                    dni,
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getDni(),
                    cliente.getFechaNacimiento(),
                    cliente.getTipoPersona().toString(),
                    cliente.getBanco(),
                    cliente.getFechaAlta());
            return new ResponseEntity<>("Cliente modificado con éxito", HttpStatus.OK);
        } catch (ClienteAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}

