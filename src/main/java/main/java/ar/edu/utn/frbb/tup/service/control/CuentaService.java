package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.model.*;
import main.java.ar.edu.utn.frbb.tup.persistence.*;
import main.java.ar.edu.utn.frbb.tup.exception.*;

public class CuentaService {
    public void darDeAltaCuenta(Cuenta cuenta) throws CuentaAlreadyExistsException {

        SummitCuenta.escribirEnArchivo(cuenta);
    }

  }
