package com.diegosl.lightlifejava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecuperarContrasenia_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Recuperacion de contraseña");
        //actionBar.hide();
        setContentView(R.layout.activity_recuperar_contrasenia_1);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent i;
        i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        return true;
    }
    public void botonRecuperar(View view){
        EditText et_correo = (EditText) findViewById(R.id.et_correo_recuperar);

        if(usuarioRegistrado(et_correo.getText().toString())){
            Intent i;
            i = new Intent(this, Recuperar_Contrasenia_2.class);
            i.putExtra("correo", et_correo.getText().toString());
            startActivity(i);
            finish();
        }else{
            Toast.makeText(RecuperarContrasenia_1.this, "EL CORREO INGRESADO NO ESTA REGISTARDO EN NUESTR SISTEMA", Toast.LENGTH_SHORT).show();
        }

    }
    private boolean usuarioRegistrado(String correo) {

        DataBaseHelper dbHelper = new DataBaseHelper(RecuperarContrasenia_1.this);
        List<UserModel> lista= new ArrayList<>();
        lista = dbHelper.getTodo();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getCorreo().equals(correo)){
                return true;
            }
        }
        return false;


    }
}