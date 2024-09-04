
package ar.edu.utn.frbb.tup.presentation.modelDto;

import java.util.List;

public class ClientePrestamosDto {
    private long numeroCliente;
    private List<PrestamoEstadoDto> prestamos;

    public ClientePrestamosDto(long numeroCliente, List<PrestamoEstadoDto> prestamos) {
        this.numeroCliente = numeroCliente;
        this.prestamos = prestamos;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public List<PrestamoEstadoDto> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<PrestamoEstadoDto> prestamos) {
        this.prestamos = prestamos;
    }
}