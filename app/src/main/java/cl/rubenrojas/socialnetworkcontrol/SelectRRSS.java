package cl.rubenrojas.socialnetworkcontrol;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Adapters.RRSS_Adapter;
import cl.rubenrojas.socialnetworkcontrol.Classes.RRSS;
import cl.rubenrojas.socialnetworkcontrol.Classes.Utils;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SelectRRSS extends AppCompatActivity {
    RecyclerView rvRRSS;
    private RRSS_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_rrss);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        rvRRSS = (RecyclerView)findViewById(R.id.rvRRSS);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvRRSS.setLayoutManager(llm);
        rvRRSS.setHasFixedSize(true);




        ArrayList<RRSS> redesSociales = new ArrayList<>();
        redesSociales.add(new RRSS("Messenger", "messenger", "com.facebook.orca"));
        redesSociales.add(new RRSS("Whatsapp", "whatsapp", "com.whatsapp"));
        redesSociales.add(new RRSS("Snapchat", "snapchat", "com.snapchat.android"));
        redesSociales.add(new RRSS("Hangouts", "hangouts", "com.google.android.talk"));

        adapter = new RRSS_Adapter(redesSociales, SelectRRSS.this);
        adapter.notifyDataSetChanged();
        SlideInRightAnimationAdapter slideAdapter = new SlideInRightAnimationAdapter(adapter);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        slideAdapter.setFirstOnly(false);

        rvRRSS.setAdapter(slideAdapter);

        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectRRSS.this, Dash.class));
                SelectRRSS.this.finish();
            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
