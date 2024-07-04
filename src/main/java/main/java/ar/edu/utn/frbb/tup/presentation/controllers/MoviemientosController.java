package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.persistence.SummitMovimientos;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.modulos.Deposito;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.modulos.Retiro;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")

public class MoviemientosController {
    @PostMapping("/depositar/{cbu}")
    public ResponseEntity<String> realizarDeposito(@PathVariable String cbu, @RequestBody double monto) {
        // Validar que el monto sea positivo
        if (monto <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El monto debe ser positivo.");
        }

        // Realizar el depósito
        Deposito.realizarDeposito(cbu, monto);

        return ResponseEntity.status(HttpStatus.OK).body("Depósito realizado correctamente para CBU: " + cbu);
    }

    @PostMapping("/retirar/{cbu}")
    public ResponseEntity<String> realizarRetiro(
            @PathVariable String cbu,
            @RequestBody Map<String, Double> requestBody) {

        if (requestBody.containsKey("monto")) {
            double monto = requestBody.get("monto");
            Retiro.realizarRetiro(cbu, monto);
            Retiro.actualizarArchivoCuentas(cbu, monto);
            SummitMovimientos.registrarMovimientoRetiro(cbu, monto, "RETIRO");
            return ResponseEntity.ok("Retiro realizado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("El parámetro 'monto' es requerido.");
        }
    }
}
