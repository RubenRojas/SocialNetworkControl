package cl.rubenrojas.socialnetworkcontrol.Classes;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import cl.rubenrojas.socialnetworkcontrol.R;

/**
 * Created by rubro on 18-07-2016.
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tabla de usuario
        String query="create table rrss_seleccionada(nombre varchar(30) primary key," +
                "imagen varchar(30), packageName varchar(80)" +
                ")";

        db.execSQL(query);

       // db.execSQL("insert into estado(nombre, descripcion) values ('"+ R.string.accept+"', 'Lo siento, estoy durmiendo', '0', '0')");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("drop table if exists horaCarrera");
        db.execSQL("drop table if exists tiempoMoviemiento");

        String query="create table usuario(correo varchar(25) primary key," +
                "pass varchar(25)," +
                "nombre varchar(25), " +
                "fono varchar(25), " +
                "ultimaCentral varchar(25)," +
                "origen varchar(25),  " + //facebook // google
                "imageUrl text,  " + //facebook // google
                "movilValidado varchar(2)  " + //SI // NO
                ")";
        db.execSQL(query);


        query="create table taximetro(" +
                "tiempo_det varchar(25), " +
                "distancia varchar(25), " +
                "hora_inicial varchar(25), " +
                "valor varchar(25)" +
                ")";
        db.execSQL(query);

        db.execSQL("insert into taximetro(tiempo_det, distancia, hora_inicial, valor ) values ('0', '0', '0', '0')");
    }



}
