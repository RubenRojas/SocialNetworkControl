package cl.rubenrojas.socialnetworkcontrol.Classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by rubro on 03-03-2017.
 */

public class Usuario {
    String nombre_usuario;
    String correo;
    String photo_url;

    public Usuario(String nombre_usuario, String correo, String photo_url) {
        this.nombre_usuario = nombre_usuario;
        this.correo = correo;
        this.photo_url = photo_url;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre_usuario='" + nombre_usuario + '\'' +
                ", correo='" + correo + '\'' +
                ", photo_url='" + photo_url + '\'' +
                '}';
    }

    /*************************************
     FUNCIONES DE BASE DE DATSOS
     *************************************/
    public static boolean someoneLogued(SQLiteDatabase bd){
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
    }
    public boolean insertUsuario(SQLiteDatabase bd){
        Boolean pass;
        try{
            String query = "insert into usuario(nombre_usuario, correo, photo_url) " +
                    "values ('"+this.nombre_usuario.toUpperCase()+"', '"+this.correo.toUpperCase()+
                    "', '"+this.photo_url+"' )";
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
            bd.delete("usuario", "correo='" + this.correo+"'", null);
            pass = true;
        }
        catch (Exception e){
            pass = false;
        }
        return pass;
    }
}
