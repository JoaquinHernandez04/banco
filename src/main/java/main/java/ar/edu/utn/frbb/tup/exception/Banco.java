package main.java.ar.edu.utn.frbb.tup.exception;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Cliente> clientes = new ArrayList<>();

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
