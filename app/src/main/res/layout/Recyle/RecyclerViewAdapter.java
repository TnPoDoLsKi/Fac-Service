package com.example.lenovo.project_one.Recyle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.project_one.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext ;
    List<Matiere> lstMatieres;

    public RecyclerViewAdapter(Context context, List<Matiere> lstMatieres) {
        this.mContext = context;
        this.lstMatieres = lstMatieres;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v=LayoutInflater.from(mContext).inflate(R.layout.item_matiere,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_nom.setText(lstMatieres.get(i).getNom_matr());

        myViewHolder.img_matr.setImageResource(lstMatieres.get(i).getImg_matr());
        //myViewHolder.img_matr.setImageUrl(...)
    }

    @Override
    public int getItemCount() {
        return lstMatieres.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_nom;
        private ImageView img_matr ;
        //        private SmartImageView img_matr ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nom = (TextView) itemView.findViewById(R.id.nom_matiere_id) ;
            img_matr = (ImageView) itemView.findViewById(R.id.img_matiere_id);
            //img_matr = (SmartImageView) itemView.findViewById(R.id.img_matiere_id);
        }
    }
}
