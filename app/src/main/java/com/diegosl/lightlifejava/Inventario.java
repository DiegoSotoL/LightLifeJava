package com.diegosl.lightlifejava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Inventario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Inventario");
        visibilityEspada();
        visibilityBaculo();
        visibilityYelmo();
        visibilityGorro();
        visibilityArmadura();
        visibilityToga();
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent i;
        i = new Intent(this, PantallaPrincipal.class);
        startActivity(i);
        finish();
        return true;
    }
    public boolean visibilityEspada(){
        DataBaseHelper db = new DataBaseHelper(this);
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        if(db.usuarioConEspada(correo)){
            findViewById(R.id.tr_espada).setVisibility(View.VISIBLE);
            findViewById(R.id.noPoseeArmas).setVisibility(View.GONE);
            return true;
        }
        return false;
    }
    public boolean visibilityBaculo(){
        DataBaseHelper db = new DataBaseHelper(this);
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        if(db.usuarioConBaculo(correo)){
            findViewById(R.id.tr_baculo).setVisibility(View.VISIBLE);
            findViewById(R.id.noPoseeArmas).setVisibility(View.GONE);
            return true;
        }
        return false;
    }
    public boolean visibilityYelmo(){
        DataBaseHelper db = new DataBaseHelper(this);
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        if(db.usuarioConYelmo(correo)){
            findViewById(R.id.tr_yelmo).setVisibility(View.VISIBLE);
            findViewById(R.id.noPoseeSombreros).setVisibility(View.GONE);
            return true;
        }
        return false;
    }
    public boolean visibilityGorro(){
        DataBaseHelper db = new DataBaseHelper(this);
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        if(db.usuarioConGorro(correo)){
            findViewById(R.id.tr_gorro).setVisibility(View.VISIBLE);
            findViewById(R.id.noPoseeSombreros).setVisibility(View.GONE);
            return true;
        }
        return false;
    }
    public boolean visibilityArmadura(){
        DataBaseHelper db = new DataBaseHelper(this);
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        if(db.usuarioConArmadura(correo)){
            findViewById(R.id.tr_armadura).setVisibility(View.VISIBLE);
            findViewById(R.id.noPoseeVestimenta).setVisibility(View.GONE);
            return true;
        }
        return false;
    }
    public boolean visibilityToga(){
        DataBaseHelper db = new DataBaseHelper(this);
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        if(db.usuarioConToga(correo)){
            findViewById(R.id.tr_toga).setVisibility(View.VISIBLE);
            findViewById(R.id.noPoseeVestimenta).setVisibility(View.GONE);
            return true;
        }
        return false;
    }
}