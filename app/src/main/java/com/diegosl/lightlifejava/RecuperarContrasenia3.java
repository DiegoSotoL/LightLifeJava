package com.diegosl.lightlifejava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class RecuperarContrasenia3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Recuperacion de contraseña");

        //actionBar.hide();
        setContentView(R.layout.activity_recuperar_contrasenia3);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent i;
        i = new Intent(this, Recuperar_Contrasenia_2.class);
        startActivity(i);
        finish();
        return true;
    }
    public void botonInicio(View view){
        String correo = getIntent().getExtras().getString("correo");
        EditText contraseñaNueva=(EditText) findViewById(R.id.et_contraseña1_cambio);
        EditText contraseñaNueva2=(EditText) findViewById(R.id.et_contraseña2_cambio);

        if(contraseñaNueva.getText().toString().equals(contraseñaNueva2.getText().toString())){//contraseñas iguales remplaza y cambia la actividad

            DataBaseHelper db = new DataBaseHelper(RecuperarContrasenia3.this);
            if(db.getUsuario(correo).getContraseña().equals(contraseñaNueva.getText().toString())){
                Toast.makeText(RecuperarContrasenia3.this, "LA CONTRASEÑA INGRESADA ES IGUAL A LA CONTRASEÑA ANTIGUA", Toast.LENGTH_SHORT).show();
            }else{
                db.cambiarContraseña(correo, contraseñaNueva.getText().toString());
                UserModel um = db.getUsuario(correo);
                Date inicioSesion = new Date();
                Date cierreSesion = new Date();
                UserSession us = new UserSession(um, inicioSesion, cierreSesion);
                ((Aplicacion) this.getApplication()).setSesionActiva(us);
                Intent i = new Intent(this, PantallaPrincipal.class);
                i.putExtra("correo", correo);
                startActivity(i);
                finish();
                Toast.makeText(RecuperarContrasenia3.this, "FELICIDADES HAS CAMBIADO LA CONTRASEÑA CON EXITO!", Toast.LENGTH_SHORT).show();

            }

        }else{//mensaje error de coincidencia
            Toast.makeText(RecuperarContrasenia3.this, "LAS CONTRASEÑAS INGRESADAS NO COINCIDEN", Toast.LENGTH_SHORT).show();
        }

    }
}