package ar.edu.utn.frbb.tup.presentation.modelDto;


import ar.edu.utn.frbb.tup.model.Cuenta;


public class CuentaDto {
    private String nombre;
    private long dniTitular;
    private String tipoCuenta;
    private String moneda;
    private long cbu;
    private double balance;
    private String fechaCreacion;

    public CuentaDto() {
    }

    public CuentaDto(Cuenta cuenta) {
        this.cbu = cuenta.getCBU();
        this.nombre = cuenta.getNombre();
        this.tipoCuenta = cuenta.getTipoCuenta().toString();
        this.balance = cuenta.getBalance();
        this.moneda = cuenta.getMoneda().toString();
        this.fechaCreacion = cuenta.getFechaCreacion().toString();
        this.dniTitular = cuenta.getDniTitular();
    }

    // Getters y setters actualizados
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

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
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
