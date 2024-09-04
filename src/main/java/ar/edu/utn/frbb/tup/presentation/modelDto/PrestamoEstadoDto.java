package ar.edu.utn.frbb.tup.presentation.modelDto;

public class PrestamoEstadoDto {
    private double monto;
    private int plazoMeses;
    private int pagosRealizados;
    private double saldoRestante;
    private String estado;

    public PrestamoEstadoDto(double monto, int plazoMeses, int pagosRealizados, double saldoRestante, String estado) {
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.pagosRealizados = pagosRealizados;
        this.saldoRestante = saldoRestante;
        this.estado = estado;
    }

    // Getters y Setters
    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public int getPagosRealizados() {
        return pagosRealizados;
    }

    public void setPagosRealizados(int pagosRealizados) {
        this.pagosRealizados = pagosRealizados;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
