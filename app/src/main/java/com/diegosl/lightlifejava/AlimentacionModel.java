package com.diegosl.lightlifejava;

public class AlimentacionModel {

        private int id;
        private String nombre;
        private int cantidad_veces;
        private int cantidad_max;
        private String correo;

    public AlimentacionModel(int id, String nombre, int cantidad_veces, int cantidad_max, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad_veces = cantidad_veces;
        this.cantidad_max = cantidad_max;
        this.correo = correo;
    }

    public AlimentacionModel() {
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad_veces() {
        return cantidad_veces;
    }

    public void setCantidad_veces(int cantidad_veces) {
        this.cantidad_veces = cantidad_veces;
    }

    public int getCantidad_max() {
        return cantidad_max;
    }

    public void setCantidad_max(int cantidad_max) {
        this.cantidad_max = cantidad_max;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "AlimentacionModel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad_veces=" + cantidad_veces +
                ", cantidad_max=" + cantidad_max +
                ", correo='" + correo + '\'' +
                '}';
    }
}


