package com.diegosl.lightlifejava;

import java.util.Date;

public class UserSession {
    public UserModel usuario;
    public Date inicioSesion;
    public Date cierreSesion;

    public UserSession(UserModel usuario, Date inicioSesion, Date cierreSesion) {
        this.usuario = usuario;
        this.inicioSesion = inicioSesion;
        this.cierreSesion = cierreSesion;
    }

    public Date getCierreSesion() {
        return cierreSesion;
    }

    public void setCierreSesion(Date cierreSesion) {
        this.cierreSesion = cierreSesion;
    }

    public UserModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }

    public Date getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(Date inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "usuario=" + usuario +
                ", inicioSesion=" + inicioSesion +
                ", cierreSesion=" + cierreSesion +
                '}';
    }
}
