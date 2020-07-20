package com.diegosl.lightlifejava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Recuperar_Contrasenia_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Recuperacion de contraseña");
        //actionBar.hide();
        setContentView(R.layout.activity_recuperar__contrasenia_2);
        boolean correoEnviado = mandarCorreoRecuperacion(getIntent().getExtras().getString("correo"));
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent i;
        i = new Intent(this, RecuperarContrasenia_1.class);
        startActivity(i);
        finish();
        return true;
    }
    public boolean mandarCorreoRecuperacion(final String destinatario){
        UserModel u = new UserModel();
        DataBaseHelper db= new DataBaseHelper(this);
         u=db.getUsuario(destinatario);

        final String asunto = "RECUPERACION CONTRASEÑA LIGHTLIFE";
        final String cuerpo = "LA CLAVE DE RECUPERACION ES: "+u.getCodigo();
        final String remitente = "lightlifecontacto";  //Para la dirección nomcuenta@gmail.com
        final String clave="dsl159951";
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "dsl159951");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        final Session session = Session.getDefaultInstance(props);
        final MimeMessage message = new MimeMessage(session);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    message.setFrom(new InternetAddress(remitente));

                    message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
                    message.setSubject(asunto);
                    message.setText(cuerpo);
                    Transport transport = session.getTransport("smtp");
                    transport.connect("smtp.gmail.com", remitente, clave);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

                } catch (MessagingException me) {
                    me.printStackTrace();   //Si se produce un error
                }
            }
        });

        return true;
    }
    public void botonRecuperar(View view){
        String correo = getIntent().getExtras().getString("correo");

        EditText et_codigo_recuperacion = (EditText) findViewById(R.id.et_codigo_recuperacion);
        UserModel u = new UserModel();
        u= getUsuario(correo);
        if(u.getCodigo()==Integer.parseInt(et_codigo_recuperacion.getText().toString())){
            Intent i;
            i = new Intent(this, RecuperarContrasenia3.class);
            i.putExtra("correo", correo);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(Recuperar_Contrasenia_2.this, "EL CODIGO DE RECUPERACION ES INCORRECTO", Toast.LENGTH_SHORT).show();
        }
    }
    private UserModel getUsuario(String correo){
        UserModel usuario = new UserModel();
        DataBaseHelper dbHelper = new DataBaseHelper(Recuperar_Contrasenia_2.this);
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
}