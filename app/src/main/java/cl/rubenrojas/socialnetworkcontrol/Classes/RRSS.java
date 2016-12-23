package cl.rubenrojas.socialnetworkcontrol.Classes;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rubro on 08-12-2016.
 */

public class RRSS {
    public final String nombre;
    public final String imagen;
    public final String packageName;

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
