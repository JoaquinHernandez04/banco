package ar.edu.utn.frbb.tup.presentation.modelDto;

import ar.edu.utn.frbb.tup.model.Cuenta;

public class CuentaDto {
    private String nombre;
    private long dniTitular;
    private String tipoCuenta;
    private String tipoMoneda;
    private long cbu;
    private double balance;
    private String moneda;
    private String fechaCreacion;

    public CuentaDto() {
    }

    // Constructor que acepta un objeto Cuenta
    public CuentaDto(Cuenta cuenta) {
        this.cbu = cuenta.getCBU();
        this.nombre = cuenta.getNombre();
        this.tipoCuenta = cuenta.getTipoCuenta().toString();
        this.balance = cuenta.getBalance();
        this.moneda = cuenta.getMoneda().toString();
        this.fechaCreacion = cuenta.getFechaCreacion().toString();
        this.dniTitular = cuenta.getDniTitular();
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getDniTitular() {
        return dniTitular;
    }

    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public CuentaDto setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public String getMoneda() {
        return tipoMoneda;
    }

    public CuentaDto setMoneda(String moneda) {
        this.tipoMoneda = moneda;
        return this;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getCbu() {
        return cbu;
    }

    public void setCbu(long cbu) {
        this.cbu = cbu;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
