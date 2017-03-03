package cl.rubenrojas.socialnetworkcontrol;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.Usuario;

import static cl.rubenrojas.socialnetworkcontrol.Classes.CheckNetwork.isInternetAvailable;


public class Splash extends AppCompatActivity {
    SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "SNC", null, Integer.parseInt(getString(R.string.database_version)));
        bd = admin.getWritableDatabase();

        if(isInternetAvailable(Splash.this)){
            if(Usuario.someoneLogued(bd)){
                continuar(Dash.class);
            }
            else{
                continuar(Login.class);
            }

        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Sin Conexión a internet.")
                    .setMessage("Esta aplicación necesita una conexión a internet para funcionar.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Splash.this.finish();
                        }
                    }).create().show();
        }
    }
    private void continuar(final Class cls){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, cls));
                Splash.this.finish();

            }
        }, 1500);
    }

}
