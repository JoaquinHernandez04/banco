package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;


public class Movimiento {
    private Long CBU;

    private TipoOperacion tipoOperacion;
    private double monto;

    public long getCBU() {
        return CBU;
    }

    public void setCBU(long CBU) {
        this.CBU = CBU;
    }



    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }


    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
