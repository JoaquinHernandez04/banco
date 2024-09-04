package ar.edu.utn.frbb.tup.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CalificacionCrediticiaRechazadaException.class)
    public ResponseEntity<Map<String, Object>> handleCalificacionCrediticiaRechazadaException(
            CalificacionCrediticiaRechazadaException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        response.put("path", "/api/prestamo"); // Ajusta esto según tu URL específica

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}