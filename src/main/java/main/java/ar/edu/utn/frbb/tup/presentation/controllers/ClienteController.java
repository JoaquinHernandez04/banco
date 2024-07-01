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
import org.springframework.web.bind.annotation.RequestParam;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitCliente;

import java.time.LocalDate;
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

    @PutMapping("/ModificarCliente/{dni}") // falta modificar
    public ResponseEntity<String> modificarCliente(@PathVariable("dni") String dni,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellido", required = false) String apellido,
            @RequestParam(value = "nuevoDni", required = false) String nuevoDni,
            @RequestParam(value = "fechaNacimiento", required = false) String fechaNacimiento,
            @RequestParam(value = "tipoPersona", required = false) String tipoPersona,
            @RequestParam(value = "banco", required = false) String banco,
            @RequestParam(value = "fechaAlta", required = false) String fechaAlta) {

        // Parsear fechas si se proporcionan en el formato correcto
        LocalDate fechaNacimientoDate = null;
        LocalDate fechaAltaDate = null;
        if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
            fechaNacimientoDate = LocalDate.parse(fechaNacimiento);
        }
        if (fechaAlta != null && !fechaAlta.isEmpty()) {
            fechaAltaDate = LocalDate.parse(fechaAlta);
        }

        ModificarCliente.modificarCliente(dni, nombre, apellido, nuevoDni, fechaNacimientoDate, tipoPersona, banco,
                fechaAltaDate);

        return new ResponseEntity<>("Operación de modificación del cliente con DNI " + dni + " ejecutada.",
                HttpStatus.OK);
    }
}
