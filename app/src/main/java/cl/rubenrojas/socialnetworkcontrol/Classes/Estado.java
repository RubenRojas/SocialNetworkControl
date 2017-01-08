package cl.rubenrojas.socialnetworkcontrol.Classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rubro on 31-12-2016.
 */

public class Estado {
    String estado;
    String mensaje;

    public Estado(String estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Estado{" +
                "estado='" + estado + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }

    /*************************************
     FUNCIONES DE BASE DE DATSOS
     *************************************/
    /*public static boolean someoneLogued(SQLiteDatabase bd){
        String query = "select correo from usuario limit 1";
        Cursor fila = bd.rawQuery(query, null);
        ////Log.d("Develop", fila.getString(0));
        if (fila.moveToFirst()) {
            //Log.d("Develop", fila.getString(0));
            return true;
        }
        else{
            return false;
        }
    }*/
    public static ArrayList<Estado> getEstados(SQLiteDatabase bd){
        String query = "select estado, mensaje from estado";
        Cursor fila = bd.rawQuery(query, null);
        Estado us;
        ArrayList<Estado> arr = new ArrayList<>();
        ////Log.d("Develop", fila.getString(0));
        while(fila.moveToNext()){
            us =  new Estado(fila.getString(0), fila.getString(1));
            arr.add(us);
        }
        return arr;

    }
    public static ArrayList<String> getListaEstados(SQLiteDatabase bd){
        String query = "select estado, mensaje from estado order by estado asc";
        Cursor fila = bd.rawQuery(query, null);
        Estado us;
        ArrayList<String> arr = new ArrayList<>();
        ////Log.d("Develop", fila.getString(0));
        while(fila.moveToNext()){
            us =  new Estado(fila.getString(0), fila.getString(1));
            arr.add(us.getEstado());
        }
        return arr;

    }
    public boolean insertEstado(SQLiteDatabase bd){
        Boolean pass;
        ContentValues registro = new ContentValues();
        registro.put("estado", this.estado.toUpperCase());
        registro.put("mensaje", this.mensaje.toUpperCase());

        try{
            String query = "insert into estado(estado, mensaje) values ('"+this.estado.toUpperCase()+"', '"+this.mensaje.toUpperCase()+"')";
            Log.d("Develop", "Query: "+query);
            bd.execSQL(query);

            pass= true;
        }
        catch (Exception e){
            Log.d("Develop", e.toString());
            pass = false;
        }
        Log.d("Develop", "Pass: "+pass);
        return pass;
    }

    public boolean deleteUsuario(SQLiteDatabase bd){
        Boolean pass;
        try{
            bd.delete("estado", "estado='" + this.estado+"' and mensaje = '"+this.mensaje+"'", null);
            pass = true;
        }
        catch (Exception e){
            pass = false;
        }
        return pass;
    }
    public static Estado getEstado(SQLiteDatabase bd, String estado) {
        String query = "select estado, mensaje from estado where estado = '"+estado+"'limit 1";
        Cursor fila = bd.rawQuery(query, null);
        Estado us;
        ////Log.d("Develop", fila.getString(0));
        if (fila.moveToFirst()) {
            us = new Estado(fila.getString(0), fila.getString(1));
            return us;
        }
        else{
            return null;
        }
    }
    
}
