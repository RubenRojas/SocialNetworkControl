package cl.rubenrojas.socialnetworkcontrol.Adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.List;

import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.RRSS;
import cl.rubenrojas.socialnetworkcontrol.Classes.Utils;
import cl.rubenrojas.socialnetworkcontrol.R;
import cl.rubenrojas.socialnetworkcontrol.SelectRRSS;

/**
 * Created by rubro on 08-12-2016.
 */

public class RRSS_Adapter extends RecyclerView.Adapter<RRSS_Adapter.RRSSViewHolder> {

    ArrayList<RRSS> redesSociales;
    Context mContext;




    public static class RRSSViewHolder extends RecyclerView.ViewHolder{
        CardView cardRRSS;
        TextView nombreRRSS;
        ImageView imagenRRSS;
        CheckBox checkBox;
        LinearLayout linearLayoutRRSS;
        RRSSViewHolder(View itemView){
            super(itemView);
            cardRRSS = (CardView)itemView.findViewById(R.id.cardRRSS);
            nombreRRSS = (TextView)itemView.findViewById(R.id.nombreRRSS);
            imagenRRSS = (ImageView)itemView.findViewById(R.id.imagenRRSS);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkBox);
            linearLayoutRRSS = (LinearLayout)itemView.findViewById(R.id.linearLayoutRRSS);
        }

    }
    public RRSS_Adapter(ArrayList<RRSS> redes, Context context){
        this.redesSociales = redes;
        this.mContext = context;
    }

    @Override
    public RRSSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rrss, parent, false);
        RRSSViewHolder pvh = new RRSSViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RRSSViewHolder holder, int position) {

        final SQLiteDatabase bd;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mContext,
                "SNC", null, Integer.parseInt(mContext.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();

        final RRSS rrss = redesSociales.get(position);
        holder.nombreRRSS.setText(rrss.nombre);
        int id_img = mContext.getResources().getIdentifier(rrss.imagen, "drawable", mContext.getPackageName());
        holder.imagenRRSS.setImageResource(id_img);
        PackageManager pm = mContext.getPackageManager();
        if(!rrss.isPackageInstalled(pm)){
            holder.linearLayoutRRSS.setBackgroundColor(Color.parseColor("#636161"));
            holder.nombreRRSS.setTextColor(Color.WHITE);
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("Develop", "REGISTRO AGREGADO A LA BD");
                    rrss.insertBD(bd);
                }
                else{
                    rrss.deleteBD(bd);
                    Log.d("Develop", "REGISTRO ELIMINADO DE LA BD");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        try{
            return redesSociales.size();
        }
        catch(Exception e){
            //Log.d("Develop", e.getMessage().toString());
            return 0;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
