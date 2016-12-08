package cl.rubenrojas.socialnetworkcontrol.Classes;

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
}
