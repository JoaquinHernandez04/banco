package main.java.ar.edu.utn.frbb.tup.exception;

public class ClienteAlreadyExistsException extends Throwable {
    public ClienteAlreadyExistsException(String message) {
        super(message);
    }
}
