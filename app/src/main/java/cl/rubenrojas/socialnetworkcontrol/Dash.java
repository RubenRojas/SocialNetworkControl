package cl.rubenrojas.socialnetworkcontrol;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.Estado;

public class Dash extends AppCompatActivity {

    MaterialSpinner spinerEstados;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(Dash.this,
                "SNC", null, Integer.parseInt(Dash.this.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();

        ArrayList<String> LEstados = Estado.getListaEstados(bd);
        spinerEstados = (MaterialSpinner)findViewById(R.id.listaEstados);
        try{
            spinerEstados.setItems(LEstados);
            spinerEstados.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                }
            });
        }
        catch (Exception e){

        }

    }

}
