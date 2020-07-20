package com.diegosl.lightlifejava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class RegistroUsuario extends AppCompatActivity {
    private EditText et_nombre, et_edad, et_genero, et_altura, et_peso, et_tutor, et_correo, et_contraseña;
    Button registrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Registro");



        //actionBar.hide();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_usuario);

        registrarse =findViewById(R.id.btnRegistro);
        registrarse.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            registrarUsuarioNuevo();

        }


        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent i;
        i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        return true;
    }
    public void registrarUsuarioNuevo(){

        UserModel user;
        try{
            et_nombre =(EditText) findViewById(R.id.txt_nombre);
            et_edad=(EditText) findViewById(R.id.txt_edad);
            et_genero=(EditText) findViewById(R.id.txt_genero);
            et_altura =(EditText) findViewById(R.id.txt_altura);
            et_peso=(EditText) findViewById(R.id.txt_peso);
            et_tutor =(EditText) findViewById(R.id.txt_tutor);
            et_correo=(EditText) findViewById(R.id.txt_correo_login);
            et_contraseña=(EditText) findViewById(R.id.txt_contraseña);
            int codigo = (int) Math.floor(Math.random()*(9999-1000+1)+1000);  // valor random de 4 digitos
            user = new UserModel(et_nombre.getText().toString(), Integer.parseInt(et_edad.getText().toString()), et_correo.getText().toString(), et_contraseña.getText().toString(), et_genero.getText().toString(), Integer.parseInt(et_altura.getText().toString()), Integer.parseInt(et_peso.getText().toString()), et_tutor.getText().toString(), 1,codigo, 1000 );


        }catch(Exception e){
            //Toast.makeText(RegistroUsuario.this, "ERROR:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            user= new UserModel("",0,"","","",0,0,"",0,0,0);
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(RegistroUsuario.this);
        switch (dataBaseHelper.addOne(user)) {
            case 1:
                Toast.makeText(RegistroUsuario.this, "EL USUARIO INGRESADO YA SE ENCUENTRA REGISTRADO", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(RegistroUsuario.this, "PARA CONTINUAR DEBE INGRESAR TODOS LOS DATOS SOLICITADOS", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(RegistroUsuario.this, "EL CORREO INGRESADO NO ES VALIDO", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 0:
                UserModel um = dataBaseHelper.getUsuario(et_correo.getText().toString());
                Date inicioSesion = new Date();
                Date cierreSesion = new Date();
                UserSession us = new UserSession(um, inicioSesion, cierreSesion);
                ((Aplicacion) this.getApplication()).setSesionActiva(us);
                Intent i;
                Toast.makeText(RegistroUsuario.this, "REGISTRO FINALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
                i = new Intent(this, PantallaPrincipal.class);
                startActivity(i);
                finish();

                break;
        }




    }
}