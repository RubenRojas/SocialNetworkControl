package cl.rubenrojas.socialnetworkcontrol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;



import java.util.ArrayList;


import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.Usuario;
import cl.rubenrojas.socialnetworkcontrol.Classes.Utils;
import cl.rubenrojas.socialnetworkcontrol.Classes.WebRequest;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    SQLiteDatabase bd;

    private static final int SIGN_IN_CODE = 9001;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Log.d("Develop", "Login.java");
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "SNC", null, Integer.parseInt(getString(R.string.database_version)));
        bd = admin.getWritableDatabase();



        findViewById(R.id.googleButton).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Develop", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            account = result.getSignInAccount();
            updateUI(true);

        } else {
            updateUI(false);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void updateUI(boolean signedIn) {
        Usuario us;
        if (signedIn) {
            try{
                Log.d("Develop", "Account: "+account.toString());
                Log.d("Develop", "account.getEmail: "+account.getEmail());
                Log.d("Develop", "account.getDisplayName(): "+account.getDisplayName());
                if(account.getPhotoUrl() == null) {
                    Log.d("Develop", "account.getPhotoUrl().equals(\"null\"): " + account.getPhotoUrl());
                    us = new Usuario(account.getDisplayName(), account.getEmail(), "http://www.accrinet.com/images/3030_orig.png");
                }
                else{
                    Log.d("Develop", "account.getPhotoUrl() == null : "+account.getPhotoUrl().toString());
                    us = new Usuario(account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString());

                }

                Toast.makeText(Login.this, "Bienvenido "+account.getDisplayName(), Toast.LENGTH_SHORT).show();
                us.insertUsuario(bd);

                Intent i = new Intent(Login.this, SelectRRSS.class);
                startActivity(i);
                Login.this.finish();

            }catch (Exception e){
                Log.d("Develop", "Acount null: "+e.getMessage());
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }



        } else {
            findViewById(R.id.googleButton).setVisibility(View.VISIBLE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.googleButton:
                signIn();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Login.this, Splash.class);
        Login.this.startActivity(i);
        Login.this.finish();
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    private class registraUsuario extends AsyncTask<ArrayList<String>, Void, Void> {
        private String resultadoLogin;
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Validando su Información");
            pDialog.setCancelable(true);
            pDialog.show();
            //Log.d("Develop", "Cancelando carrera");
        }
        @Override
        protected Void doInBackground(ArrayList<String>... passing) {
            ArrayList<String> passed = passing[0]; //get passed arraylist
            WebRequest webreq = new WebRequest();
            String URL = "https://www.legaltaxi.cl/pruebas/legaltaxi/service/login/registroV2.php?" +
                    "email="+passed.get(0)+"&origen="+passed.get(1)+"&nombre="+passed.get(2);
            URL = Utils.removeEspUrl(URL);
            resultadoLogin = webreq.makeWebServiceCall(URL, WebRequest.GET);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //Toast.makeText(getApplicationContext(), "Carrera Cancelada.", Toast.LENGTH_LONG).show();
            /*
            if(resultadoLogin.equals("ValidaTelefono")){
                Intent i = new Intent(Login.this, PedirTelefono.class);
                startActivity(i);
                Login.this.finish();
            }
            else{
                us.setFono(resultadoLogin);
                us.setMovilValidado("SI");
                us.updateUsuario(bd);
                Intent i = new Intent(Login.this, Dash.class);
                startActivity(i);
                Login.this.finish();
            }*/
            Intent i = new Intent(Login.this, SelectRRSS.class);
            startActivity(i);
            Login.this.finish();
        }
    }
}