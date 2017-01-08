package cl.rubenrojas.socialnetworkcontrol;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.Estado;

public class EstadosComp extends AppCompatActivity {
    Estado estado;
    String mensaje;
    EditText ed_estado, ed_mensaje;
    Button guardar;
    SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados_comp);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(EstadosComp.this,
                "SNC", null, Integer.parseInt(EstadosComp.this.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();
        ed_estado = (EditText)findViewById(R.id.estado);
        ed_mensaje = (EditText)findViewById(R.id.mensaje);

        try{
            String est = getIntent().getStringExtra("estado").toString();
            String men  = getIntent().getStringExtra("mensaje").toString();
            estado = new Estado(est, men);
            getSupportActionBar().setTitle(getString(R.string.estado_titulo_editar));
            ed_mensaje.setText(men);
            ed_estado.setText(est);
            mensaje = getString(R.string.estado_mensaje_editado);
        }
        catch (Exception e){
            mensaje = getString(R.string.estado_mensaje_creado);
            getSupportActionBar().setTitle(getString(R.string.estado_titulo_crear));
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        guardar = (Button)findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_estado = (EditText)findViewById(R.id.estado);
                ed_mensaje = (EditText)findViewById(R.id.mensaje);

                String est = ed_estado.getText().toString();
                String men = ed_mensaje.getText().toString();
                try{
                    estado.deleteUsuario(bd);
                }
                catch (Exception e){

                }
                estado = new Estado(est, men);
                estado.deleteUsuario(bd);
                estado.insertEstado(bd);





                Toast.makeText(EstadosComp.this, mensaje, Toast.LENGTH_SHORT).show();
                EstadosComp.this.finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
