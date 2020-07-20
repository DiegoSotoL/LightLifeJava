package com.diegosl.lightlifejava;

import android.app.Application;

public class Aplicacion extends Application {

    private UserSession sesionActiva;

    public UserSession getSesionActiva() {
        return sesionActiva;
    }

    public void setSesionActiva(UserSession sesionActiva) {
        this.sesionActiva = sesionActiva;
    }
}