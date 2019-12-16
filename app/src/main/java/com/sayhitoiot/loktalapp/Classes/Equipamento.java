package com.sayhitoiot.loktalapp.Classes;

public class Equipamento {
    private String serial;
    private String modelo;
    private String nf;
    private String data;
    private String fotoPatch;

    public Equipamento() {
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFotoPatch() {
        return fotoPatch;
    }

    public void setFotoPatch(String fotoPatch) {
        this.fotoPatch = fotoPatch;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Equipamento{" +
                "serial='" + serial + '\'' +
                ", modelo='" + modelo + '\'' +
                ", nf='" + nf + '\'' +
                ", data='" + data + '\'' +
                ", fotoPatch='" + fotoPatch + '\'' +
                '}';
    }
}
