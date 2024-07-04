package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.service.control.CuentaService;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.BorrarCuenta;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.MostrarCuentas;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping("/crearCuenta/{dni}")
    public ResponseEntity<String> crearCuenta(@RequestBody Cuenta cuenta, @PathVariable String dni) {
        try {
            cuentaService.darDeAltaCuenta(cuenta, dni);
            return new ResponseEntity<>("Cuenta creada para el titular con DNI: " + dni,
                    HttpStatus.CREATED);
        } catch (ClienteAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mostrar/{dni}")
    public List<String> mostrarCuentasPorDni(@PathVariable String dni) {
        return MostrarCuentas.mostrarCuentas(dni);
    }

    @GetMapping("/mostrar")
    public List<String> mostrarTodasLasCuentas() {
        return MostrarCuentas.mostrarTodasLasCuentas();
    }

    @DeleteMapping("/eliminar/{cbu}")
    public ResponseEntity<String> eliminarCuentaPorCBU(@PathVariable("cbu") String cbu) {
        try {
            BorrarCuenta.borrarCuenta(cbu);
            return new ResponseEntity<>("Cuenta eliminada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
