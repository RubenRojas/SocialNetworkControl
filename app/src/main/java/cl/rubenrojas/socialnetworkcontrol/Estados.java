package cl.rubenrojas.socialnetworkcontrol;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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


                Intent i = new Intent(Estados.this, EstadosComp.class);
                Estados.this.startActivity(i);

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
