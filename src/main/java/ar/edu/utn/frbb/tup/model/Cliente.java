package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import java.time.LocalDate;

public class Cliente extends Persona {

    private long id;
    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;

    // Constructor que acepta un ClienteDto
    public Cliente(ClienteDto clienteDto) {

        super(
            Long.parseLong(clienteDto.getDni()),
            clienteDto.getNombre(), 
            clienteDto.getApellido(), 
            clienteDto.getDireccion(), 
            LocalDate.parse(clienteDto.getFechaNacimiento())
        );
        this.tipoPersona = TipoPersona.fromString(clienteDto.getTipoPersona()); 
        this.banco = clienteDto.getBanco();
        this.fechaAlta = LocalDate.now();
    }

    public Cliente() {
        super();
    }

    // Getters y setters para id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

}
