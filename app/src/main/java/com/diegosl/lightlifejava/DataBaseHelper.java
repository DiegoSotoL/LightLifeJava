package com.diegosl.lightlifejava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "lightLifeDB", null, 1);
    }

    //se ejecuta la proimera vez quie se abbre la bd
    @Override
    public void onCreate(SQLiteDatabase db) {
        //se genera las tablas
        String st = "CREATE TABLE USERS_TABLE (CORREO STRING PRIMARY KEY, CONTRASEÑA STRING, NOMBRE STRING, EDAD INTEGER, GENERO STRING, ALTURA INTEGER, PESO INTEGER, TUTOR STRING, SESION INTEGER, CODIGO INTEGER, PUNTOS INTEGER)";
        db.execSQL(st);
        String st2 = "CREATE TABLE ALIMENTACION (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE STRING, PROGRESO INTEGER, MAX INTEGER, CORREO STRING,FOREIGN KEY(CORREO) REFERENCES USERS_TABLE(CORREO))";
        db.execSQL(st2);
        String st3 = "CREATE TABLE EJERCICIO (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE STRING, PROGRESO INTEGER, MAX INTEGER, CORREO STRING, FOREIGN KEY(CORREO) REFERENCES USERS_TABLE(CORREO))";
        db.execSQL(st3);
        String st4 = "CREATE TABLE PREMIOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE STRING, VALOR INTEGER, DESCRIPCION STRING, CATEGORIA STRING, CORREO STRING, FOREIGN KEY(CORREO) REFERENCES USERS_TABLE(CORREO))";
        db.execSQL(st4);

    }

    //se eejcuta cuando se realice una actualizacion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<UserModel> getTodo() {
        List<UserModel> listaUsuarios = new ArrayList<>();
        String query = "SELECT * FROM USERS_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String correo = cursor.getString(0);
                String contraseña = cursor.getString(1);
                String nombre = cursor.getString(2);
                int edad = cursor.getInt(3);
                String genero = cursor.getString(4);
                int altura = cursor.getInt(5);
                int peso = cursor.getInt(6);
                String tutor = cursor.getString(7);
                int sesion = cursor.getInt(8);
                int codigo = cursor.getInt(9);
                int puntos = cursor.getInt(10);
                UserModel um = new UserModel(nombre, edad, correo, contraseña, genero, altura, peso, tutor, sesion, codigo, puntos);
                listaUsuarios.add(um);

            } while (cursor.moveToNext());
        } else {

        }

        return listaUsuarios;

    }

    public int addOne(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CORREO", userModel.getCorreo());
        cv.put("CONTRASEÑA", userModel.getContraseña());
        cv.put("NOMBRE", userModel.getNombre());
        cv.put("EDAD", userModel.getEdad());
        cv.put("GENERO", userModel.getGenero());
        cv.put("ALTURA", userModel.getAltura());
        cv.put("PESO", userModel.getPeso());
        cv.put("TUTOR", userModel.getTutor());
        cv.put("SESION", userModel.getSesion());
        cv.put("CODIGO", userModel.getCodigo());
        cv.put("PUNTOS", userModel.getPuntos());
        String throwInsert = "";
        try {
            db.insertOrThrow("USERS_TABLE", null, cv);
        } catch (SQLException e) {
            throwInsert = e.getLocalizedMessage();
        }
        if (throwInsert.equals("UNIQUE constraint failed: USERS_TABLE.CORREO (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)")) {//usuario duplicado
            return 1;
        }

        if (userModel.getAltura() == 0 || userModel.getContraseña().equals("") || userModel.getCorreo().equals("") || userModel.getEdad() == 0 || userModel.getGenero().equals("") || userModel.getNombre().equals("") || userModel.getTutor().equals("") || userModel.getPeso() == 0) {
            return 2;
        }
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = userModel.getCorreo();

        Matcher mather = pattern.matcher(email);

        if (mather.find() == false) {
           return 3;
        }
        if (throwInsert.equals("")) {
            return 0;
        }
        return 0;

    }

    /* public boolean cambiarContraseña (String correo, String contraseñaNueva){
         UserModel u = getUsuario(correo);
         u.setContraseña(contraseñaNueva);
         boolean e =eliminarUsuario(correo);
         boolean a = addOne(u);
         return e & a;
     }*/
    public UserModel getUsuario(String correo) {
        UserModel usuario = new UserModel();
        List<UserModel> lista = new ArrayList<>();
        lista = getTodo();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCorreo().equals(correo)) {
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
                usuario.setPuntos(lista.get(i).getPuntos());
            }
        }
        return usuario;
    }

    public boolean eliminarUsuario(String correo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from USERS_TABLE where CORREO = " + correo);
        return true;

    }

    public boolean CerrarSesion(String correo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE USERS_TABLE SET SESION = 0 WHERE CORREO = " + "'" + correo + "'");
        return true;
    }

    public boolean objetoObtenido(String correo, String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM PREMIOS  WHERE CORREO = '" + correo + "' AND NOMBRE = '" + nombre + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe el objeto en el usuario
            return true;
        } else {
            return false;
        }

    }
    public boolean cambiarContraseña(String correo, String contraseña){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        db.execSQL("UPDATE USERS_TABLE SET CONTRASEÑA = '" + contraseña + "'WHERE CORREO = " + "'" + correo + "'");
        return true;
    }
    public boolean completarAbdominales(String correo){

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String ejercicio = "ABDOMINALES";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + ejercicio + "'");
        return true;
    }
    public boolean completarBurpees(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String ejercicio = "BURPEES";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + ejercicio + "'");
        return true;
    }
    public boolean completarSentadillas(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String ejercicio = "SENTADILLAS";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + ejercicio + "'");
        return true;
    }
    public boolean completarFlexiones(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String ejercicio = "FLEXIONES";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + ejercicio + "'");
        return true;
    }
    public boolean completarAlvapor(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String comida = "ALVAPOR";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + comida + "'");
        return true;
    }
    public boolean completarFrutasyverduras(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String comida = "FRUTASYVERDURAS";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + comida + "'");
        return true;
    }
    public boolean completarHidratacion(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String comida = "HIDRATACION";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + comida + "'");
        return true;
    }
    public boolean completar5comidas(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int progreso =0;
        int puntos=0;
        int nuevoPuntaje=0;

        String comida = "5COMIDAS";

        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
            nuevoPuntaje = puntos + 100;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + nuevoPuntaje + "'WHERE CORREO = " + "'" + correo + "'");


        }
        progreso=0;
        db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + progreso + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + comida + "'");
        return true;
    }
    public boolean agregarHidratacion(String correo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "HIDRATACION";
        String query = "SELECT * FROM ALIMENTACION  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + 1;

            db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + 1;
            cv.put("NOMBRE", "HIDRATACION");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 5);
            cv.put("CORREO", correo);


            long users_table = db.insert("ALIMENTACION", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }

    public boolean agregar5comidas(String correo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "5COMIDAS";
        String query = "SELECT * FROM ALIMENTACION  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de 5comidas asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + 1;

            db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + 1;
            cv.put("NOMBRE", "5COMIDAS");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 5);
            cv.put("CORREO", correo);


            long users_table = db.insert("ALIMENTACION", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }

    public boolean agregarAlVapor(String correo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "ALVAPOR";
        String query = "SELECT * FROM ALIMENTACION  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo al vapor asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + 1;

            db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + 1;
            cv.put("NOMBRE", "ALVAPOR");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 1);
            cv.put("CORREO", correo);


            long users_table = db.insert("ALIMENTACION", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }

    public boolean agregarFrutasYVerduras(String correo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "FRUTASYVERDURAS";
        String query = "SELECT * FROM ALIMENTACION  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de frutasyverduras asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + 1;

            db.execSQL("UPDATE ALIMENTACION SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + 1;
            cv.put("NOMBRE", "FRUTASYVERDURAS");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 4);
            cv.put("CORREO", correo);


            long users_table = db.insert("ALIMENTACION", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }

    public boolean agregarFlexiones(String correo, int cant) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "FLEXIONES";
        String query = "SELECT * FROM EJERCICIO  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + cant;

            db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + 1;
            cv.put("NOMBRE", "FLEXIONES");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 10);
            cv.put("CORREO", correo);


            long users_table = db.insert("EJERCICIO", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }
    public int getProgresoEjercicio(String correo, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int progreso = 0;
        String query = "SELECT * FROM EJERCICIO  WHERE NOMBRE = '" + tipo + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
        }

        return progreso;

    }
    public int getProgresoAlimentacion(String correo, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int progreso = 0;
        String query = "SELECT * FROM ALIMENTACION  WHERE NOMBRE = '" + tipo + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
        }

        return progreso;

    }
    public boolean agregarAbdominales(String correo, int cant) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "ABDOMINALES";
        String query = "SELECT * FROM EJERCICIO  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + cant;

            db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + cant;
            cv.put("NOMBRE", "ABDOMINALES");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 25);
            cv.put("CORREO", correo);


            long users_table = db.insert("EJERCICIO", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }

    public boolean agregarBurpees(String correo, int cant) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "BURPEES";
        String query = "SELECT * FROM EJERCICIO  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + cant;

            db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + cant;
            cv.put("NOMBRE", "BURPEES");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 10);
            cv.put("CORREO", correo);


            long users_table = db.insert("EJERCICIO", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }

    public boolean agregarSentadillas(String correo, int cant) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        int progreso = 0;
        int suma = 0;
        String h = "SENTADILLAS";
        String query = "SELECT * FROM EJERCICIO  WHERE NOMBRE = '" + h + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            progreso = cursor.getInt(2);
            suma = progreso + cant;

            db.execSQL("UPDATE EJERCICIO SET PROGRESO = '" + suma + "'WHERE CORREO = " + "'" + correo + "' AND NOMBRE = '" + h + "'");

            return true;
        } else {
            suma = progreso + cant;
            cv.put("NOMBRE", "SENTADILLAS");
            cv.put("PROGRESO", suma);
            cv.put("MAX", 15);
            cv.put("CORREO", correo);


            long users_table = db.insert("EJERCICIO", null, cv);
            if (users_table == -1) {

                return false;
            } else {

                return true;
            }
        }
    }

    public int canjearSombrero(String correo) {
        int descontar = 100;
        int puntosUsuario = 0;
        int puntosFinales = 0;
        if (objetoObtenido(correo, "SOMBRERO POTENCIADOR")) {
            return 3;
        }
        if (this.getUsuario(correo).getPuntos() < 0) {//SALDO INSUFUCIENTE
            return 1;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getWritableDatabase();
        String throwInsert = "";
        cv.put("NOMBRE", "SOMBRERO POTENCIADOR");
        cv.put("VALOR", 100);
        cv.put("CORREO", correo);
        cv.put("CATEGORIA", "SOMBRERO");
        cv.put("DESCRIPCION", "UN SOMBRERO QUE POTENCIA LOS ATAQUES MAGICOS");


        try {
            db.insertOrThrow("PREMIOS", null, cv);
            puntosUsuario = this.getUsuario(correo).getPuntos();
            puntosFinales = puntosUsuario - descontar;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + puntosFinales + "'WHERE CORREO = " + "'" + correo + "'");

            return 2;
        } catch (SQLException e) {
            throwInsert = e.getLocalizedMessage();
            return 0;
        }
    }

    public int canjearEspada(String correo) {
        int descontar = 400;
        int puntosUsuario = 0;
        int puntosFinales = 0;
        if (objetoObtenido(correo, "ESPADA MAGICA")) {
            return 3;
        }
        if (this.getUsuario(correo).getPuntos() < 0) {//SALDO INSUFUCIENTE
            return 1;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getWritableDatabase();
        String throwInsert = "";
        cv.put("NOMBRE", "ESPADA MAGICA");
        cv.put("VALOR", 400);
        cv.put("CORREO", correo);
        cv.put("CATEGORIA", "ARMAS");
        cv.put("DESCRIPCION", "UNA ESPADA QUE FUNCIONA CON MAGIA");


        try {
            db.insertOrThrow("PREMIOS", null, cv);
            puntosUsuario = this.getUsuario(correo).getPuntos();
            puntosFinales = puntosUsuario - descontar;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + puntosFinales + "'WHERE CORREO = " + "'" + correo + "'");

            return 2;
        } catch (SQLException e) {
            throwInsert = e.getLocalizedMessage();
            return 0;
        }
    }
    public int canjearBaculo(String correo) {
        int descontar = 300;
        int puntosUsuario = 0;
        int puntosFinales = 0;
        if (objetoObtenido(correo, "BACULO")) {
            return 3;
        }
        if (this.getUsuario(correo).getPuntos() < 300) {//SALDO INSUFUCIENTE
            return 1;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getWritableDatabase();
        String throwInsert = "";
        cv.put("NOMBRE", "BACULO");
        cv.put("VALOR", 300);
        cv.put("CORREO", correo);
        cv.put("CATEGORIA", "ARMAS");
        cv.put("DESCRIPCION", "BACULO POTENCIADOR");


        try {
            db.insertOrThrow("PREMIOS", null, cv);
            puntosUsuario = this.getUsuario(correo).getPuntos();
            puntosFinales = puntosUsuario - descontar;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + puntosFinales + "'WHERE CORREO = " + "'" + correo + "'");

            return 2;
        } catch (SQLException e) {
            throwInsert = e.getLocalizedMessage();
            return 0;
        }
    }
    public int canjearYelmo(String correo) {
        int descontar = 200;
        int puntosUsuario = 0;
        int puntosFinales = 0;
        if (objetoObtenido(correo, "YELMO DE PLACAS")) {
            return 3;
        }
        if (this.getUsuario(correo).getPuntos() < 200) {//SALDO INSUFUCIENTE
            return 1;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getWritableDatabase();
        String throwInsert = "";
        cv.put("NOMBRE", "YELMO DE PLACAS");
        cv.put("VALOR", 200);
        cv.put("CORREO", correo);
        cv.put("CATEGORIA", "SOMBRERO");
        cv.put("DESCRIPCION", "YELMO DE OBSIDIANA MUY RESISTENTE");


        try {
            db.insertOrThrow("PREMIOS", null, cv);
            puntosUsuario = this.getUsuario(correo).getPuntos();
            puntosFinales = puntosUsuario - descontar;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + puntosFinales + "'WHERE CORREO = " + "'" + correo + "'");

            return 2;
        } catch (SQLException e) {
            throwInsert = e.getLocalizedMessage();
            return 0;
        }
    }
    public int canjearArmadura(String correo) {
        int descontar = 400;
        int puntosUsuario = 0;
        int puntosFinales = 0;
        if (objetoObtenido(correo, "ARMADURA")) {
            return 3;
        }
        if (this.getUsuario(correo).getPuntos() < 400) {//SALDO INSUFUCIENTE
            return 1;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getWritableDatabase();
        String throwInsert = "";
        cv.put("NOMBRE", "ARMADURA");
        cv.put("VALOR", 400);
        cv.put("CORREO", correo);
        cv.put("CATEGORIA", "VESTIMENTA");
        cv.put("DESCRIPCION", "ARMADURA RESISTENTE DE PLACAS");


        try {
            db.insertOrThrow("PREMIOS", null, cv);
            puntosUsuario = this.getUsuario(correo).getPuntos();
            puntosFinales = puntosUsuario - descontar;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + puntosFinales + "'WHERE CORREO = " + "'" + correo + "'");

            return 2;
        } catch (SQLException e) {
            throwInsert = e.getLocalizedMessage();
            return 0;
        }
    }
    public int canjearToga(String correo) {
        int descontar = 600;
        int puntosUsuario = 0;
        int puntosFinales = 0;
        if (objetoObtenido(correo, "TOGA")) {
            return 3;
        }
        if (this.getUsuario(correo).getPuntos() < 600) {//SALDO INSUFUCIENTE
            return 1;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db2 = this.getWritableDatabase();
        String throwInsert = "";
        cv.put("NOMBRE", "TOGA");
        cv.put("VALOR", 600);
        cv.put("CORREO", correo);
        cv.put("CATEGORIA", "VESTIMENTA");
        cv.put("DESCRIPCION", "TOGA POTENCIADORA ANTI MAGIA");


        try {
            db.insertOrThrow("PREMIOS", null, cv);
            puntosUsuario = this.getUsuario(correo).getPuntos();
            puntosFinales = puntosUsuario - descontar;

            db2.execSQL("UPDATE USERS_TABLE SET PUNTOS = '" + puntosFinales + "'WHERE CORREO = " + "'" + correo + "'");

            return 2;
        } catch (SQLException e) {
            throwInsert = e.getLocalizedMessage();
            return 0;
        }
    }
    public int getPuntosUser(String correo){
        int puntos =0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USERS_TABLE  WHERE CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            puntos = cursor.getInt(10);
        }
        return puntos;
    }
    public int getCantidadEjercicios(String correo, String ejercicio) {

        SQLiteDatabase db2 = this.getReadableDatabase();


        String query = "SELECT * FROM EJERCICIO  WHERE NOMBRE = '" + ejercicio + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            return cursor.getInt(2);
        } else {
            return 0;
        }
    }
    public int getCantidadAlimentacion(String correo, String nombre) {

        SQLiteDatabase db2 = this.getReadableDatabase();


        String query = "SELECT * FROM ALIMENTACION  WHERE NOMBRE = '" + nombre + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo de hidratacion asociado al usuario cons esion activa, por ende se debe modificar el progreso
            return cursor.getInt(2);
        } else {
            return 0;
        }
    }

    public boolean usuarioConEspada(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = "ESPADA MAGICA";

        String query = "SELECT * FROM PREMIOS  WHERE NOMBRE = '" + nombre + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo
            return true;
        } else {
            return false;
        }
    }
    public boolean usuarioConBaculo(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = "BACULO";

        String query = "SELECT * FROM PREMIOS  WHERE NOMBRE = '" + nombre + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo
            return true;
        } else {
            return false;
        }
    }
    public boolean usuarioConYelmo(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = "YELMO DE PLACAS";

        String query = "SELECT * FROM PREMIOS  WHERE NOMBRE = '" + nombre + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo
            return true;
        } else {
            return false;
        }
    }
    public boolean usuarioConGorro(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = "SOMBRERO POTENCIADOR";

        String query = "SELECT * FROM PREMIOS  WHERE NOMBRE = '" + nombre + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo
            return true;
        } else {
            return false;
        }
    }
    public boolean usuarioConArmadura(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = "ARMADURA";

        String query = "SELECT * FROM PREMIOS  WHERE NOMBRE = '" + nombre + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo
            return true;
        } else {
            return false;
        }
    }
    public boolean usuarioConToga(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = "TOGA";

        String query = "SELECT * FROM PREMIOS  WHERE NOMBRE = '" + nombre + "' AND CORREO = '" + correo + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {//existe un objetivo
            return true;
        } else {
            return false;
        }
    }
}



