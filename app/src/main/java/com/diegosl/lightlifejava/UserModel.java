package com.diegosl.lightlifejava;

public class UserModel {
        private String nombre;
        private int edad;
        private String correo;
        private String contraseña;
        private String genero;
        private int altura;
        private int peso;
        private String tutor;
        private int sesion;//1 activa ; 0 inactiva
        private int codigo;
        private int puntos;

    public UserModel(String nombre, int edad, String correo, String contraseña, String genero, int altura, int peso, String tutor, int sesion, int codigo, int puntos) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.contraseña = contraseña;
        this.genero = genero;
        this.altura = altura;
        this.peso = peso;
        this.tutor = tutor;
        this.sesion = sesion;
        this.codigo=codigo;
        this.puntos=puntos;
    }

    public UserModel() {
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getSesion() {
        return sesion;
    }

    public void setSesion(int sesion) {
        this.sesion = sesion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", genero='" + genero + '\'' +
                ", altura=" + altura +
                ", peso=" + peso +
                ", tutor='" + tutor + '\'' +
                ", sesion=" + sesion +
                ", codigo=" + codigo +
                ", puntos=" + puntos +
                '}';
    }
}
