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

        query="create table estado(estado varchar(50)," +
                "mensaje varchar(150)" +
                ")";

        db.execSQL(query);

        query="create table opciones(estadoActivo varchar(50)," +
                "monitoreoActivo varchar(2)"+
                ")";
        db.execSQL(query);
        query = "insert into opciones(estadoActivo, monitoreoActivo) values('0', 'NO'";
        db.execSQL(query);


       // db.execSQL("insert into estado(nombre, descripcion) values ('"+ R.string.accept+"', 'Lo siento, estoy durmiendo', '0', '0')");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists rrss_seleccionada");
        db.execSQL("drop table if exists estado");


        String query="create table rrss_seleccionada(nombre varchar(30) primary key," +
                "imagen varchar(30), packageName varchar(80)" +
                ")";

        db.execSQL(query);

        query="create table estado(estado varchar(50)," +
                "mensaje varchar(150)" +
                ")";

        db.execSQL(query);


        query="create table opciones(estadoActivo varchar(50)," +
                "monitoreoActivo varchar(2)"+
                ")";
        db.execSQL(query);
        query = "insert into opciones(estadoActivo, monitoreoActivo) values('0', 'NO'";
        db.execSQL(query);


    }



}
