package cl.rubenrojas.socialnetworkcontrol.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Classes.RRSS;
import cl.rubenrojas.socialnetworkcontrol.R;

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
        RRSSViewHolder(View itemView){
            super(itemView);
            cardRRSS = (CardView)itemView.findViewById(R.id.cardRRSS);
            nombreRRSS = (TextView)itemView.findViewById(R.id.nombreRRSS);
            imagenRRSS = (ImageView)itemView.findViewById(R.id.imagenRRSS);
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
        final RRSS rrss = redesSociales.get(position);
        holder.nombreRRSS.setText(rrss.nombre);
        int id_img = mContext.getResources().getIdentifier(rrss.imagen, "drawable", mContext.getPackageName());
        holder.imagenRRSS.setImageResource(id_img);
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
