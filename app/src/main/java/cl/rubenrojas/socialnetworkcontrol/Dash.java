package cl.rubenrojas.socialnetworkcontrol;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Adapters.RRSS_Adapter_Monitoreo;
import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.CircleTransform;
import cl.rubenrojas.socialnetworkcontrol.Classes.RRSS;
import cl.rubenrojas.socialnetworkcontrol.Classes.Usuario;
import cl.rubenrojas.socialnetworkcontrol.Classes.Utils;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Dash extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvRRSS;
    private RRSS_Adapter_Monitoreo adapter;
    SQLiteDatabase bd;
    TextView nav_header_email, nav_header_name;
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(Dash.this,
                "SNC", null, Integer.parseInt(Dash.this.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();
        user = Usuario.getUsuario(bd);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        nav_header_name = (TextView) header.findViewById(R.id.nav_header_name);

        nav_header_email = (TextView)header.findViewById(R.id.nav_header_email);
        Picasso.with(Dash.this).load(user.getPhoto_url()).transform(new CircleTransform()).into((ImageView)header.findViewById(R.id.nav_header_icon));
        nav_header_name.setText(Utils.capitalize(user.getNombre_usuario().toLowerCase()));

        nav_header_email.setText(user.getCorreo().toLowerCase());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        rvRRSS = (RecyclerView)findViewById(R.id.rvRRSS);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvRRSS.setLayoutManager(llm);
        rvRRSS.setHasFixedSize(true);
        setData();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash, menu);
        return true;
    }
    public void setData(){
        ArrayList<RRSS> estados = RRSS.getRRSSSeleccionadas(bd);
        adapter = new RRSS_Adapter_Monitoreo(estados, Dash.this);
        adapter.notifyDataSetChanged();
        SlideInRightAnimationAdapter slideAdapter = new SlideInRightAnimationAdapter(adapter);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        slideAdapter.setFirstOnly(false);
        rvRRSS.setAdapter(slideAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.estados) {
            Intent i = new Intent(Dash.this, Estados.class);
            startActivity(i);
        } else if (id == R.id.monitoreo) {
            Intent i = new Intent(Dash.this, Monitoreo.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}