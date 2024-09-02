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
import ar.edu.utn.frbb.tup.presentation.validator.PrestamoValidator;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentaSinSaldoException;
import ar.edu.utn.frbb.tup.exception.TipoMonedasInvalidasException;
import java.util.List;
@RestController
@RequestMapping("/api")
public class PrestamoController {

    @Autowired
    private PrestamoValidator prestamoValidator;

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/prestamo/{clienteId}")
    public ResponseEntity<List<Prestamo>> estadoPrestamos(@PathVariable long cbu) {

        return new ResponseEntity<>(prestamoService.consultarPrestamosPorCliente(cbu), HttpStatus.OK);
    }

    @PostMapping("/prestamo")
    public ResponseEntity<Prestamo> realizarPrestamo(@RequestBody PrestamoDto prestamoDto)
            throws CuentaNoEncontradaException, CuentaSinSaldoException, TipoMonedasInvalidasException {
        // prestamoValidator.validarTransferencia(prestamoDto);
        return new ResponseEntity<>(prestamoService.pedirPrestamo(prestamoDto), HttpStatus.OK);
    }

}
