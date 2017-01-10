package cl.rubenrojas.socialnetworkcontrol;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import static cl.rubenrojas.socialnetworkcontrol.Classes.CheckNetwork.isInternetAvailable;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        if(isInternetAvailable(Splash.this)){
                continuar();
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
    private void continuar(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, Login.class));
                Splash.this.finish();

            }
        }, 1500);
    }

}
