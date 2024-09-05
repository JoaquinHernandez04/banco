package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import java.time.LocalDate;

public class Cliente extends Persona {

    private long id;
    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;


    public Cliente(ClienteDto clienteDto) {

        if (clienteDto.getDni() == null || clienteDto.getDni().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío");
        }

        this.setDni(Long.parseLong(clienteDto.getDni()));
        this.setNombre(clienteDto.getNombre());
        this.setApellido(clienteDto.getApellido());
        this.setDireccion(clienteDto.getDireccion());
        this.setFechaNacimiento(LocalDate.parse(clienteDto.getFechaNacimiento()));


        this.tipoPersona = TipoPersona.fromString(clienteDto.getTipoPersona());
        this.banco = clienteDto.getBanco();
        this.fechaAlta = LocalDate.now();
    }

    public Cliente() {
        super();
    }


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
