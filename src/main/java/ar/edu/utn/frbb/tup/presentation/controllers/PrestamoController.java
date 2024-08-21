package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CalificacionCrediticiaRechazadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // Endpoint para solicitar un préstamo
    @PostMapping
    public ResponseEntity<Map<String, Object>> solicitarPrestamo(@RequestBody Map<String, Object> request) {
        long numeroCliente = ((Number) request.get("numeroCliente")).longValue();
        double montoPrestamo = ((Number) request.get("montoPrestamo")).doubleValue();
        String moneda = (String) request.get("moneda");
        int plazoMeses = ((Number) request.get("plazoMeses")).intValue();

        Map<String, Object> response = new HashMap<>();

        try {
            Prestamo prestamo = prestamoService.solicitarPrestamo(numeroCliente, montoPrestamo, moneda, plazoMeses);
            response.put("estado", prestamo.getEstado());
            response.put("mensaje", "El monto del préstamo fue acreditado en su cuenta");
            response.put("planPagos", prestamo.getPlanPagos());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ClienteNoEncontradoException | CuentaNoEncontradaException e) {
            response.put("estado", "RECHAZADO");
            response.put("mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (CalificacionCrediticiaRechazadaException e) {
            response.put("estado", "RECHAZADO");
            response.put("mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para consultar el estado de los préstamos
    @GetMapping("/{clienteId}")
    public ResponseEntity<Map<String, Object>> consultarPrestamos(@PathVariable long clienteId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Prestamo> prestamos = prestamoService.consultarPrestamosPorCliente(clienteId);
            response.put("numeroCliente", clienteId);
            response.put("prestamos", prestamos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ClienteNoEncontradoException e) {
            response.put("mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
/*
 * Implementar un endpoint para que los clientes puedan solicitar un préstamo
 * debe recibir como parámetros el número de cliente, el monto del
 * préstamo solicitado, la moneda y el plazo en meses.
 */