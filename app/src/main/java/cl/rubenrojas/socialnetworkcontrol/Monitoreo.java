package cl.rubenrojas.socialnetworkcontrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Adapters.RRSS_Adapter_Monitoreo;
import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.Estado;
import cl.rubenrojas.socialnetworkcontrol.Classes.RRSS;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Monitoreo extends AppCompatActivity {

    RecyclerView rvRRSS;
    private RRSS_Adapter_Monitoreo adapter;
    SQLiteDatabase bd;
    TextView no_disponible;
    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(Monitoreo.this,
                "SNC", null, Integer.parseInt(Monitoreo.this.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();
        no_disponible = (TextView)findViewById(R.id.no_disponible);
        rvRRSS = (RecyclerView)findViewById(R.id.rvRRSS);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvRRSS.setLayoutManager(llm);
        rvRRSS.setHasFixedSize(true);
        setData();

        getSupportActionBar().setTitle("Monitoreo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        switchCompat = (SwitchCompat)findViewById(R.id.switchCompat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Develop", "Switch click: "+switchCompat.isChecked());
                if(switchCompat.isChecked()){
                    rvRRSS.setVisibility(View.VISIBLE);
                    no_disponible.setVisibility(View.INVISIBLE);
                    setData();
                }
                else{
                    rvRRSS.setVisibility(View.INVISIBLE);
                    no_disponible.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public void setData(){
        ArrayList<RRSS> estados = RRSS.getRRSSSeleccionadas(bd);
        adapter = new RRSS_Adapter_Monitoreo(estados, Monitoreo.this);
        adapter.notifyDataSetChanged();
        SlideInRightAnimationAdapter slideAdapter = new SlideInRightAnimationAdapter(adapter);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        slideAdapter.setFirstOnly(false);
        rvRRSS.setAdapter(slideAdapter);
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
