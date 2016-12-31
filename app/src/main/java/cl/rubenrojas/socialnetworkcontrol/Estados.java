package cl.rubenrojas.socialnetworkcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Adapters.Estado_Adapter;
import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.Estado;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Estados extends AppCompatActivity {
    RecyclerView listadoEstados;
    private Estado_Adapter adapter;
    SQLiteDatabase bd;
    View viewAdd, viewEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Estados");
        setSupportActionBar(myToolbar);


        listadoEstados = (RecyclerView)findViewById(R.id.listadoEstados);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listadoEstados.setLayoutManager(llm);
        listadoEstados.setHasFixedSize(true);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(Estados.this,
                "SNC", null, Integer.parseInt(Estados.this.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();

        setData();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Estados.this,"FAV", Toast.LENGTH_SHORT).show();
                LayoutInflater li = LayoutInflater.from(Estados.this);
                viewAdd = li.inflate(R.layout.dialog_add, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Estados.this);
                alertDialog.setTitle(R.string.add_estado_title);
                alertDialog.setMessage(R.string.add_estado_desc);
                alertDialog.setView(viewAdd);
                alertDialog.setIcon(R.drawable.compose);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //String url = txtUrl.getText().toString();
                                EditText estado = (EditText)viewAdd.findViewById(R.id.estado);
                                EditText mensaje = (EditText)viewAdd.findViewById(R.id.mensaje);
                                Estado tmp = new Estado(estado.getText().toString(), mensaje.getText().toString());
                                tmp.insertEstado(bd);
                                setData();
                            }
                        });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });

                alertDialog.show();

            }
        });



    }
    public void setData(){
        ArrayList<Estado> estados = Estado.getEstados(bd);
        adapter = new Estado_Adapter(estados, Estados.this);
        adapter.notifyDataSetChanged();
        SlideInRightAnimationAdapter slideAdapter = new SlideInRightAnimationAdapter(adapter);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        slideAdapter.setFirstOnly(false);
        listadoEstados.setAdapter(slideAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
