package com.diegosl.lightlifejava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PantallaPrincipal extends AppCompatActivity {

    private long ultimoClick = 0;
    public static final long SIN_HACER_CLICK = 120000;

    private AppBarConfiguration mAppBarConfiguration;
    private Button BotonCanjearEspada;
    TextView TextViewAb;
    Button botonCompletar;
    TextView infoAbs;
    TextView infoFlex;
    TextView infoBurp;
    TextView infoSent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {






        //String nombre, int edad, String correo, String contraseña, String genero, int altura, int peso, String tutor, int sesion, int codigo

        String c = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView correo = (TextView) header.findViewById(R.id.tv_correo_navi);
        TextView nombre = (TextView) header.findViewById(R.id.tv_nombre_navi);
        DataBaseHelper db= new DataBaseHelper(this);

        correo.setText(db.getUsuario(c).getCorreo());
        nombre.setText(db.getUsuario(c).getNombre());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.diarioFragment, R.id.alimentacionFragment, R.id.actividadesFragment, R.id.canjearPuntosFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantalla_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_cerrarSesion:{

                Intent i;
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public boolean verObjeto(View view){
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        String nombre = "ESPADA MAGICA";
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        boolean b = db.objetoObtenido(correo, nombre);
        if(b){
            Button btn = (Button) findViewById(R.id.btn_canjear_espada);
            btn.setEnabled(false);
        }
        return b;
    }
    public void Ejercicio1(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=NnVhqMQRvmM")));
    }
    public void Ejercicio2(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=e_EKkqoHxns")));

    }
    public void Ejercicio3(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Uy2nUNX38xE")));

    }
    public void Ejercicio4(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=G8mX-VJrUi8")));

    }
    public void Alimentacion1(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=favRO30dHXQ")));

    }
    public void Alimentacion2(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=22ya83vZNHQ")));

    }
    public void Alimentacion3(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bHsokNUYR7c")));

    }
    public void Alimentacion4(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=kzkv14uFyV0")));

    }
    public void botonUpdate(View view){
        int abs=0, burp=0,flex=0,sent=0,com=0,vap=0,fyv=0,hidr=0;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();

        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        int puntos = db.getPuntosUser(correo);
        abs = db.getCantidadEjercicios(correo, "ABDOMINALES");
        burp = db.getCantidadEjercicios(correo, "BURPEES");
        flex = db.getCantidadEjercicios(correo, "FLEXIONES");
        sent = db.getCantidadEjercicios(correo, "SENTADILLAS");

        com= db.getCantidadAlimentacion(correo, "5COMIDAS");
        vap= db.getCantidadAlimentacion(correo, "ALVAPOR");
        fyv= db.getCantidadAlimentacion(correo, "FRUTASYVERDURAS");
        hidr= db.getCantidadAlimentacion(correo, "HIDRATACION");

        TextViewAb = findViewById(R.id.tv_puntos_diario);
        TextViewAb.setText(puntos+" PUNTOS");

        TextViewAb = findViewById(R.id.tv_abdominales);
        TextViewAb.setText(abs+"/25");
        if(abs==25){
            botonCompletar = findViewById(R.id.btn_completarabdominales);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completarabdominales);
            botonCompletar.setEnabled(false);
        }
        TextViewAb = findViewById(R.id.tv_burpees);
        TextViewAb.setText(burp+"/10");
        if(burp==10){
            botonCompletar = findViewById(R.id.btn_completarburpees);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completarburpees);
            botonCompletar.setEnabled(false);
        }
        TextViewAb = findViewById(R.id.tv_flexiones);
        TextViewAb.setText(flex+"/10");
        if(flex==10){
            botonCompletar = findViewById(R.id.btn_completarflexiones);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completarflexiones);
            botonCompletar.setEnabled(false);
        }
        TextViewAb = findViewById(R.id.tv_sentadillas);
        TextViewAb.setText(sent+"/15");
        if(sent==15){
            botonCompletar = findViewById(R.id.btn_completarsentadillas);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completarsentadillas);
            botonCompletar.setEnabled(false);
        }
        TextViewAb = findViewById(R.id.tv_5comidas);
        TextViewAb.setText(com+"/5");
        if(com==5){
            botonCompletar = findViewById(R.id.btn_completar5comidas);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completar5comidas);
            botonCompletar.setEnabled(false);
        }
        TextViewAb = findViewById(R.id.tv_alvapor);
        TextViewAb.setText(vap+"/1");
        if(vap==1){
            botonCompletar = findViewById(R.id.btn_completaralvapor);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completaralvapor);
            botonCompletar.setEnabled(false);
        }
        TextViewAb = findViewById(R.id.tv_frutasyverduras);
        TextViewAb.setText(fyv+"/4");
        if(fyv==4){
            botonCompletar = findViewById(R.id.btn_completarfrutasyverduras);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completarfrutasyverduras);
            botonCompletar.setEnabled(false);
        }
        TextViewAb = findViewById(R.id.tv_hidratacion);
        TextViewAb.setText(hidr+"/10");
        if(hidr==10){
            botonCompletar = findViewById(R.id.btn_completarhidratacion);
            botonCompletar.setEnabled(true);
        }else{
            botonCompletar = findViewById(R.id.btn_completarhidratacion);
            botonCompletar.setEnabled(false);
        }
    }
    public boolean completarAbdominales(View view){

        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completarAbdominales(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean completarFlexiones(View view){
        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completarFlexiones(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean completarBurpees(View view){
        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completarBurpees(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean completarSentadillas(View view){
        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completarSentadillas(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean completarHidratacion(View view){
        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completarHidratacion(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean completar5comidas(View view){
        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completar5comidas(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean completarFrutasyverduras(View view){
        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completarFrutasyverduras(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean completarAlvapor(View view){
        boolean b;
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
        b = db.completarAlvapor(correo);

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES HAS COMPLETADO EL OBJETIVO,HAS GANADO 100 PUNTOS, SIGUE ESFORZANDOTE!", Toast.LENGTH_LONG).show();
        }
        botonUpdate(view);
        return b;
    }
    public boolean agregarHidratacion(View view){
        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        int progreso = db2.getProgresoAlimentacion(correo, "HIDRATACION");
        if(progreso<10){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregarHidratacion(correo);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE HABITO ALIMENTICIO SUPERA LOS LIMITES, PORFAVOR COBRA TU RECOMPENZA EN EL DIARIO!", Toast.LENGTH_LONG).show();
        }
        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGO EL OBJETIVO DE HIDRATACION A SU DIARIO", Toast.LENGTH_SHORT).show();
        }
        return b;

    }
    public boolean agregar5comidas(View view){
        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        int progreso = db2.getProgresoAlimentacion(correo, "5COMIDAS");
        if(progreso<5){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregar5comidas(correo);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE HABITO ALIMENTICIO SUPERA LOS LIMITES, PORFAVOR COBRA TU RECOMPENZA EN EL DIARIO!", Toast.LENGTH_LONG).show();
        }
        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGO EL OBJETIVO DE COMIDAS DIARIAS A TU PLAN", Toast.LENGTH_SHORT).show();
        }
        return b;

    }
    public boolean agregarFrutasYVerduras(View view){
        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        int progreso = db2.getProgresoAlimentacion(correo, "FRUTASYVERDURAS");
        if(progreso<4){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregarFrutasYVerduras(correo);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE HABITO ALIMENTICIO SUPERA LOS LIMITES, PORFAVOR COBRA TU RECOMPENZA EN EL DIARIO!", Toast.LENGTH_LONG).show();
        }
        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGO EL OBJETIVO DE FRUTAS Y VERDURAS A TU PLAN", Toast.LENGTH_SHORT).show();
        }
        return b;

    }
    public boolean agregarAlVapor(View view){
        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        int progreso = db2.getProgresoAlimentacion(correo, "ALVAPOR");
        if(progreso<1){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregarAlVapor(correo);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE HABITO ALIMENTICIO SUPERA LOS LIMITES, PORFAVOR COBRA TU RECOMPENZA EN EL DIARIO!", Toast.LENGTH_LONG).show();
        }
        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGO EL OBJETIVO DE COMIDAS AL VAPOR A TU PLAN", Toast.LENGTH_SHORT).show();
        }
        return b;
    }
    public boolean agregarSentadillas(View view){
        int suma = 0, progreso=0, cantLetras=0, cant=0;

        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;
        EditText et_sentadillas = (EditText) findViewById(R.id.et_cant_sentadillas);
        cantLetras = et_sentadillas.getText().length();
        if(cantLetras!=0){
            cant= Integer.parseInt(et_sentadillas.getText().toString());
        }else{
            Toast.makeText(PantallaPrincipal.this, "DEBES INGRESAR UNA CANTIDAD DE EJERCICIOS PARA CONTINUAR", Toast.LENGTH_SHORT).show();
            return false;
        }

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        progreso = db2.getProgresoEjercicio(correo, "SENTADILLAS");
        suma = progreso+cant;
        if(suma<=15){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregarSentadillas(correo, cant);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE EJERCICIO SUPERA LOS LIMITES, LO RECOMENDADO SON 15 SENTADILLAS", Toast.LENGTH_LONG).show();
            return false;
        }

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGARON LAS SENTADILLAS A TU PLAN", Toast.LENGTH_SHORT).show();
        }
        return b;
    }
    public boolean agregarFlexiones(View view){
        int suma = 0, progreso=0, cantLetras=0, cant=0;

        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;
        EditText et_flexiones = (EditText) findViewById(R.id.et_cant_flexiones);
        cantLetras = et_flexiones.getText().length();
        if(cantLetras!=0){
            cant= Integer.parseInt(et_flexiones.getText().toString());
        }else{
            Toast.makeText(PantallaPrincipal.this, "DEBES INGRESAR UNA CANTIDAD DE EJERCICIOS PARA CONTINUAR", Toast.LENGTH_SHORT).show();
            return false;
        }

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        progreso = db2.getProgresoEjercicio(correo, "FLEXIONES");
        suma = progreso+cant;
        if(suma<=10){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregarFlexiones(correo, cant);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE EJERCICIO SUPERA LOS LIMITES, LO RECOMENDADO SON 10 FLEXIONES", Toast.LENGTH_LONG).show();
            return false;
        }

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGARON LAS FLEXIONES A TU PLAN", Toast.LENGTH_SHORT).show();
        }
        return b;
    }
    public boolean agregarAbdominales(View view){
        int suma = 0, progreso=0, cantLetras=0, cant=0;

        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;
        EditText et_abdominales = (EditText) findViewById(R.id.et_cant_abdominales);
        cantLetras = et_abdominales.getText().length();
        if(cantLetras!=0){
            cant= Integer.parseInt(et_abdominales.getText().toString());
        }else{
            Toast.makeText(PantallaPrincipal.this, "DEBES INGRESAR UNA CANTIDAD DE EJERCICIOS PARA CONTINUAR", Toast.LENGTH_SHORT).show();
            return false;
        }

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        progreso = db2.getProgresoEjercicio(correo, "ABDOMINALES");
        suma = progreso+cant;
        if(suma<=25){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregarAbdominales(correo, cant);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE EJERCICIO SUPERA LOS LIMITES, LO RECOMENDADO SON 25 ABDOMINALES", Toast.LENGTH_LONG).show();
            return false;
        }

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGARON LOS ABDOMINALES A TU PLAN", Toast.LENGTH_SHORT).show();
        }
        return b;
    }
    public boolean agregarBurpees(View view){
        int suma = 0, progreso=0, cantLetras=0, cant=0;

        DataBaseHelper db2 = new DataBaseHelper(this);
        boolean b = false;
        EditText et_burpees = (EditText) findViewById(R.id.et_cant_burpees);
        cantLetras = et_burpees.getText().length();
        if(cantLetras!=0){
            cant= Integer.parseInt(et_burpees.getText().toString());
        }else{
            Toast.makeText(PantallaPrincipal.this, "DEBES INGRESAR UNA CANTIDAD DE EJERCICIOS PARA CONTINUAR", Toast.LENGTH_SHORT).show();
            return false;
        }

        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        progreso = db2.getProgresoEjercicio(correo, "BURPEES");
        suma = progreso+cant;
        if(suma<=10){
            DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);
            b = db.agregarBurpees(correo, cant);
        }else{
            Toast.makeText(PantallaPrincipal.this, "LA ADICION DE ESTE EJERCICIO SUPERA LOS LIMITES, LO RECOMENDADO SON 10 BURPEES", Toast.LENGTH_LONG).show();
            return false;
        }

        if(b){
            Toast.makeText(PantallaPrincipal.this, "FELICITACIONES! SE AGREGARON LOS BURPEES A TU PLAN", Toast.LENGTH_SHORT).show();
        }
        return b;
    }
    public boolean infoAbdominales(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_abdominales, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean infoBurpees(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_burpees, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean infoSentadillas(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_sentadillas, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean infoFlexiones(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_flexiones, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean infoHidratacion(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_hidratacion, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean infoFrutasyverduras(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_frutasyverduras, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean infoAlvapor(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_alvapor, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean info5comidas(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_5comidas, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        return true;
    }
    public boolean canjearEspada(View view){
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);


        switch (db.canjearEspada(correo)) {
            case 0:
                Toast.makeText(PantallaPrincipal.this, "NO SE HA PODIDO CANJEAR EL OBJETO", Toast.LENGTH_SHORT).show();
                return false;

            case 1:
                Toast.makeText(PantallaPrincipal.this, "SALDO INSUFICIENTE PARA CANJEAR EL PREMIO", Toast.LENGTH_SHORT).show();
                return false;

            case 2:
                Toast.makeText(PantallaPrincipal.this, "FELICITACIONES. HAS ADQUIRIDO LA ESPADA MAGICA!!", Toast.LENGTH_SHORT).show();
                return true;

            case 3:
                Toast.makeText(PantallaPrincipal.this, "YA HAS OBTENIDO ESTE PREMIO", Toast.LENGTH_SHORT).show();
                Button btn = (Button) findViewById(R.id.btn_canjear_espada);
                btn.setEnabled(false);
                return true;
        }
        return true;
    }
    public boolean canjearBaculo(View view){
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);


        switch (db.canjearBaculo(correo)) {
            case 0:
                Toast.makeText(PantallaPrincipal.this, "NO SE HA PODIDO CANJEAR EL OBJETO", Toast.LENGTH_SHORT).show();
                return false;

            case 1:
                Toast.makeText(PantallaPrincipal.this, "SALDO INSUFICIENTE PARA CANJEAR EL PREMIO", Toast.LENGTH_SHORT).show();
                return false;

            case 2:
                Toast.makeText(PantallaPrincipal.this, "FELICITACIONES. HAS ADQUIRIDO EL BACULO!", Toast.LENGTH_SHORT).show();
                return true;

            case 3:
                Toast.makeText(PantallaPrincipal.this, "YA HAS OBTENIDO ESTE PREMIO", Toast.LENGTH_SHORT).show();
                Button btn = (Button) findViewById(R.id.btn_canjear_baculo);
                btn.setEnabled(false);
                return true;
        }
        return true;
    }
    public boolean canjearYelmo(View view){
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);


        switch (db.canjearYelmo(correo)) {
            case 0:
                Toast.makeText(PantallaPrincipal.this, "NO SE HA PODIDO CANJEAR EL OBJETO", Toast.LENGTH_SHORT).show();
                return false;

            case 1:
                Toast.makeText(PantallaPrincipal.this, "SALDO INSUFICIENTE PARA CANJEAR EL PREMIO", Toast.LENGTH_SHORT).show();
                return false;

            case 2:
                Toast.makeText(PantallaPrincipal.this, "FELICITACIONES. HAS ADQUIRIDO EL YERLMO DE PLACAS!", Toast.LENGTH_SHORT).show();
                return true;

            case 3:
                Toast.makeText(PantallaPrincipal.this, "YA HAS OBTENIDO ESTE PREMIO", Toast.LENGTH_SHORT).show();
                Button btn = (Button) findViewById(R.id.btn_canjear_yelmo);
                btn.setEnabled(false);
                return true;
        }
        return true;
    }
    public boolean canjearArmadura(View view){
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);


        switch (db.canjearArmadura(correo)) {
            case 0:
                Toast.makeText(PantallaPrincipal.this, "NO SE HA PODIDO CANJEAR EL OBJETO", Toast.LENGTH_SHORT).show();
                return false;

            case 1:
                Toast.makeText(PantallaPrincipal.this, "SALDO INSUFICIENTE PARA CANJEAR EL PREMIO", Toast.LENGTH_SHORT).show();
                return false;

            case 2:
                Toast.makeText(PantallaPrincipal.this, "FELICITACIONES. HAS ADQUIRIDO LA ARMADURA!", Toast.LENGTH_SHORT).show();
                return true;

            case 3:
                Toast.makeText(PantallaPrincipal.this, "YA HAS OBTENIDO ESTE PREMIO", Toast.LENGTH_SHORT).show();
                Button btn = (Button) findViewById(R.id.btn_canjear_armadura);
                btn.setEnabled(false);
                return true;
        }
        return true;
    }
    public boolean canjearToga(View view){
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);


        switch (db.canjearToga(correo)) {
            case 0:
                Toast.makeText(PantallaPrincipal.this, "NO SE HA PODIDO CANJEAR EL OBJETO", Toast.LENGTH_SHORT).show();
                return false;

            case 1:
                Toast.makeText(PantallaPrincipal.this, "SALDO INSUFICIENTE PARA CANJEAR EL PREMIO", Toast.LENGTH_SHORT).show();
                return false;

            case 2:
                Toast.makeText(PantallaPrincipal.this, "FELICITACIONES. HAS ADQUIRIDO LA TOGA!", Toast.LENGTH_SHORT).show();
                return true;

            case 3:
                Toast.makeText(PantallaPrincipal.this, "YA HAS OBTENIDO ESTE PREMIO", Toast.LENGTH_SHORT).show();
                Button btn = (Button) findViewById(R.id.btn_canjear_toga);
                btn.setEnabled(false);
                return true;
        }
        return true;
    }
    public boolean canjearSombrero(View view){
        String correo = ((Aplicacion) this.getApplication()).getSesionActiva().getUsuario().getCorreo();
        DataBaseHelper db = new DataBaseHelper(PantallaPrincipal.this);


        switch (db.canjearSombrero(correo)) {
            case 0:
                Toast.makeText(PantallaPrincipal.this, "NO SE HA PODIDO CANJEAR EL OBJETO", Toast.LENGTH_SHORT).show();
                return false;

            case 1:
                Toast.makeText(PantallaPrincipal.this, "SALDO INSUFICIENTE PARA CANJEAR EL PREMIO", Toast.LENGTH_SHORT).show();
                return false;

            case 2:
                Toast.makeText(PantallaPrincipal.this, "FELICITACIONES. HAS ADQUIRIDO EL SOMBRERO POTENCIADOR!!", Toast.LENGTH_SHORT).show();
                return true;

            case 3:
                Toast.makeText(PantallaPrincipal.this, "YA HAS OBTENIDO ESTE PREMIO", Toast.LENGTH_SHORT).show();
                Button btn = (Button) findViewById(R.id.btn_canjear_gorro);
                btn.setEnabled(false);
                return true;
        }
        return true;
    }
    public boolean abrirObjetos(View view){
        Intent i;
        i = new Intent(this, Inventario.class);
        startActivity(i);
        finish();
        return true;
    }
}