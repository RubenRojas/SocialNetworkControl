package cl.rubenrojas.socialnetworkcontrol.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.RRSS;
import cl.rubenrojas.socialnetworkcontrol.R;

/**
 * Created by rubro on 10-01-2017.
 */

public class RRSS_Adapter_Monitoreo extends RecyclerView.Adapter<RRSS_Adapter_Monitoreo.RRSSViewHolder>  {

    ArrayList<RRSS> redesSociales;
    Context mContext;




    public static class RRSSViewHolder extends RecyclerView.ViewHolder{
        TextView rrss_estado, rrss_desc_estado;
        RelativeLayout rrss_indicador;
        ImageView rrss_logo;

        RRSSViewHolder(View itemView){
            super(itemView);
            rrss_estado = (TextView)itemView.findViewById(R.id.rrss_estado);
            rrss_desc_estado = (TextView)itemView.findViewById(R.id.rrss_desc_estado);

            rrss_logo = (ImageView)itemView.findViewById(R.id.rrss_logo);

            rrss_indicador = (RelativeLayout)itemView.findViewById(R.id.rrss_indicador);


        }

    }
    public RRSS_Adapter_Monitoreo(ArrayList<RRSS> redes, Context context){
        this.redesSociales = redes;
        this.mContext = context;
    }

    @Override
    public RRSS_Adapter_Monitoreo.RRSSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monitoreo, parent, false);
        RRSS_Adapter_Monitoreo.RRSSViewHolder pvh = new RRSS_Adapter_Monitoreo.RRSSViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RRSS_Adapter_Monitoreo.RRSSViewHolder holder, int position) {

        final SQLiteDatabase bd;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mContext,
                "SNC", null, Integer.parseInt(mContext.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();

        final RRSS rrss = redesSociales.get(position);

        int id_img = mContext.getResources().getIdentifier(rrss.imagen, "drawable", mContext.getPackageName());
        holder.rrss_logo.setImageResource(id_img);
        PackageManager pm = mContext.getPackageManager();
        try{
            if(rrss.getEstado().equals("ACTIVO")){
                holder.rrss_indicador.setBackgroundColor(Color.parseColor("#00897b"));
                holder.rrss_estado.setText(mContext.getText(R.string.rrss_estado_activo));
                holder.rrss_desc_estado.setText(mContext.getText(R.string.rrss_desc_estado_activo));
            }
            else{
                holder.rrss_indicador.setBackgroundColor(Color.parseColor("#c62828"));
                holder.rrss_estado.setText(mContext.getText(R.string.rrss_estado_inactivo));
                holder.rrss_desc_estado.setText(mContext.getText(R.string.rrss_desc_estado_inactivo));
                holder.rrss_desc_estado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setTitle(R.string.rrss_estado_inactivo);
                        alertDialog.setMessage(rrss.getDescripcion());

                        alertDialog.setIcon(R.drawable.info);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });


                        alertDialog.show();
                    }
                });
            }
        }
        catch (Exception e){
            holder.rrss_indicador.setBackgroundColor(Color.parseColor("#c62828"));
            holder.rrss_estado.setText(mContext.getText(R.string.rrss_estado_inactivo));
            holder.rrss_desc_estado.setText(mContext.getText(R.string.rrss_desc_estado_inactivo));
            holder.rrss_desc_estado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle(R.string.rrss_estado_inactivo);
                    alertDialog.setMessage(rrss.getDescripcion());

                    alertDialog.setIcon(R.drawable.info);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

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

