package cl.rubenrojas.socialnetworkcontrol.Classes;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by rubro on 08-12-2016.
 */

public class RRSS {
    public final String nombre;
    public final String imagen;
    public final String packageName;
    String estado;
    String descripcion;

    public RRSS(String nombre, String imagen, String packageName) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "RRSS{" +
                "nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }

    public boolean isPackageInstalled(PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(this.packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static ArrayList<RRSS> getRRSSSeleccionadas(SQLiteDatabase bd){
        ArrayList<RRSS> listado = new ArrayList<>();
        String query = "select nombre, imagen, packageName from rrss_seleccionada order by nombre";
        Cursor fila = bd.rawQuery(query, null);
        RRSS rrss;

        ////Log.d("Develop", fila.getString(0));
        int test = -1;
        while(fila.moveToNext()){
            rrss = new RRSS(fila.getString(0), fila.getString(1), fila.getString(2));
            if(test<0){
                rrss.setEstado("ACTIVO");
                rrss.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                        "tempor incididunt ut labore et dolore magna aliqua.");
            }
            else{
                rrss.setEstado("INACTIVO");
                rrss.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                        "tempor incididunt ut labore et dolore magna aliqua.");
            }

            listado.add(rrss);
            test= test *(-1);
        }
        return listado;
    }

    public boolean insertBD(SQLiteDatabase bd){
        Boolean pass;
        ContentValues registro = new ContentValues();
        registro.put("nombre", this.nombre);
        registro.put("imagen", this.imagen);
        registro.put("packageName", this.packageName);

        try{
            bd.insert("rrss_seleccionada", null, registro);
            pass= true;
        }
        catch (Exception e){
            //Log.d("Develop", e.toString());
            pass = false;
        }
        return pass;
    }
    public boolean deleteBD(SQLiteDatabase bd){
        Boolean pass;
        try{
            bd.delete("usuario", "packageName='" + this.packageName+"'", null);
            pass = true;
        }
        catch (Exception e){
            pass = false;
        }
        return pass;
    }
}
