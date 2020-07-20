package com.diegosl.lightlifejava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {




        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void botonRegistro(View view){
        Intent i;
        EditText et_correo = (EditText) findViewById(R.id.txt_correo_login);
        EditText et_contraseña = (EditText) findViewById(R.id.txt_pass_login);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        if(usuarioRegistrado(et_correo.getText().toString())){  //USUARIO REGISTARDO
            UserModel u =getUsuario(et_correo.getText().toString());
            if(et_contraseña.getText().toString().equals(u.getContraseña())){
                UserModel um = dataBaseHelper.getUsuario(et_correo.getText().toString());
                Date inicioSesion = new Date();
                Date cierreSesion = new Date();
                UserSession us = new UserSession(um, inicioSesion, cierreSesion);
                ((Aplicacion) this.getApplication()).setSesionActiva(us);
                i = new Intent(this, PantallaPrincipal.class);
                startActivity(i);
                String correo = u.getCorreo();
                i.putExtra("correo", correo);
                finish();
            }else{
                Toast.makeText(MainActivity.this, "CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
            }

        }else{  //USUARIO NUEVO
            i = new Intent(this, RegistroUsuario.class);
            startActivity(i);
            finish();
        }


    }
    public void botonRecuperar(View view){
        Intent i;
            i = new Intent(this, RecuperarContrasenia_1.class);

        startActivity(i);
        finish();
    }


    private boolean usuarioRegistrado(String correo) {

        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
        List<UserModel> lista= new ArrayList<>();
        lista = dbHelper.getTodo();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getCorreo().equals(correo) && !lista.get(i).getCorreo().equals("")){
                return true;
            }
        }
        return false;


    }
    private UserModel getUsuario(String correo){
        UserModel usuario = new UserModel();
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
        List<UserModel> lista= new ArrayList<>();
        lista = dbHelper.getTodo();

        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getCorreo().equals(correo)){
                usuario.setAltura(lista.get(i).getAltura());
                usuario.setContraseña(lista.get(i).getContraseña());
                usuario.setCorreo(lista.get(i).getCorreo());
                usuario.setEdad(lista.get(i).getEdad());
                usuario.setGenero(lista.get(i).getGenero());
                usuario.setNombre(lista.get(i).getNombre());
                usuario.setPeso(lista.get(i).getPeso());
                usuario.setTutor(lista.get(i).getTutor());
                usuario.setSesion(lista.get(i).getSesion());
                usuario.setCodigo(lista.get(i).getCodigo());
            }
        }
    return usuario;
    }
    private void botonCerrarSesion(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}