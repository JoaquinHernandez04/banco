package ar.edu.utn.frbb.tup.model;

import java.util.List;

public class Prestamo {
    private double monto;
    private int plazoMeses;
    private List<Double> planPagos;
    private String estado;
    private Cliente cliente; // Atributo Cliente

    public Prestamo() {
        // Constructor por defecto
    }

    public Prestamo(double monto, int plazoMeses, List<Double> planPagos, String estado, Cliente cliente) {
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.planPagos = planPagos;
        this.estado = estado;
        this.cliente = cliente; // Asignación del cliente
    }

    // Getters y Setters para cada atributo
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

    public List<Double> getPlanPagos() {
        return planPagos;
    }

    public void setPlanPagos(List<Double> planPagos) {
        this.planPagos = planPagos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Métodos getCliente() y setCliente()
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}