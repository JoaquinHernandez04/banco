package ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frbb.tup.service.PrestamoService;
import ar.edu.utn.frbb.tup.exception.CalificacionCrediticiaRechazadaException;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.MonedaInvalidaException;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.PrestamoEstadoDto;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentaSinSaldoException;
import ar.edu.utn.frbb.tup.exception.TipoMonedasInvalidasException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping("/prestamo")
    public ResponseEntity<Prestamo> realizarPrestamo(@RequestBody PrestamoDto prestamoDto)
            throws CuentaNoEncontradaException, TipoMonedasInvalidasException,
            ClienteNoEncontradoException, CalificacionCrediticiaRechazadaException {

        // Aquí podrías agregar validaciones adicionales si es necesario
        // prestamoValidator.validarPrestamo(prestamoDto);

        // Llamamos al servicio para procesar la solicitud de préstamo
        Prestamo prestamo = prestamoService.solicitarPrestamo(prestamoDto);

        // Devolvemos la respuesta HTTP con el estado 200 OK y el préstamo creado
        return new ResponseEntity<>(prestamo, HttpStatus.OK);

        /*
         * {
         * "numeroCliente": 42958792,
         * "plazoEnMeses": 3,
         * "montoPrestamo": 500,
         * "moneda": "ARS"
         * }
         */
    }

    @GetMapping("/prestamo/{numeroCliente}")
    public ResponseEntity<List<PrestamoEstadoDto>> consultarEstadoPrestamos(@PathVariable long numeroCliente)
            throws ClienteNoEncontradoException {
        // Llamada al servicio para obtener el estado de los préstamos
        List<PrestamoEstadoDto> prestamos = prestamoService.consultarEstadoPrestamos(numeroCliente);

        // Devolver la lista de préstamos en la respuesta
        return ResponseEntity.ok(prestamos);
    }
}

