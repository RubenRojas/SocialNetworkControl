package cl.rubenrojas.socialnetworkcontrol.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.rubenrojas.socialnetworkcontrol.Classes.AdminSQLiteOpenHelper;
import cl.rubenrojas.socialnetworkcontrol.Classes.Estado;
import cl.rubenrojas.socialnetworkcontrol.Estados;
import cl.rubenrojas.socialnetworkcontrol.R;

/**
 * Created by rubro on 31-12-2016.
 */

public class Estado_Adapter extends RecyclerView.Adapter<Estado_Adapter.EstadoViewHolder> {

    ArrayList<Estado> listaEstados;
    Context mContext;




    public static class EstadoViewHolder extends RecyclerView.ViewHolder{

        TextView estado, mensaje;
        ImageView editar, borrar;

        EstadoViewHolder(View itemView){
            super(itemView);
            estado = (TextView) itemView.findViewById(R.id.estado);
            mensaje = (TextView) itemView.findViewById(R.id.mensaje);
            editar = (ImageView)itemView.findViewById(R.id.editar);
            borrar = (ImageView)itemView.findViewById(R.id.borrar);

        }

    }
    public Estado_Adapter(ArrayList<Estado> estados, Context context){
        this.listaEstados = estados;
        this.mContext = context;
    }

    @Override
    public EstadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estado, parent, false);
        EstadoViewHolder pvh = new EstadoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EstadoViewHolder holder, final int position) {

        final SQLiteDatabase bd;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mContext,
                "SNC", null, Integer.parseInt(mContext.getString(R.string.database_version)));
        bd = admin.getWritableDatabase();

        final Estado estado = listaEstados.get(position);
        holder.estado.setText(estado.getEstado());
        holder.mensaje.setText(estado.getMensaje());
        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle(R.string.delete_estado_title);
                alertDialog.setMessage(R.string.delete_estado_desc);

                alertDialog.setIcon(R.drawable.trash);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        estado.deleteUsuario(bd);
                        listaEstados.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaEstados.size());
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alertDialog.show();
            }
        });
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Editar", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        try{
            return listaEstados.size();
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
