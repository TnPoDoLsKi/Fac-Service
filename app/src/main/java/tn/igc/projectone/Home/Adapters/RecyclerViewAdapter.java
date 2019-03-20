package tn.igc.projectone.Home.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import tn.igc.projectone.Home.Classes.Matiere;
import tn.igc.projectone.Home.Fragments.DocumentList;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {


    Context mContext;
    List<Matiere> lstMatieres;
    Dialog dialog;
    Button btc;
    Button bttd;
    Button bttp;
    Button btex;
    Button btds;
    String type;
    String mat_id;
    String id_m;


    public RecyclerViewAdapter(Context context, List<Matiere> lstMatieres) {
        this.mContext = context;
        this.lstMatieres = lstMatieres;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_matiere, viewGroup, false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_choix);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btc = dialog.findViewById(R.id.btn_c);
        bttd = dialog.findViewById(R.id.btn_td);
        bttp = dialog.findViewById(R.id.btn_tp);
        btds = dialog.findViewById(R.id.btn_ds);
        btex = dialog.findViewById(R.id.btn_ex);


        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, " " + lstMatieres.get(vHolder.getAdapterPosition()).getNom_matr(), Toast.LENGTH_SHORT).show();
                id_m = lstMatieres.get(vHolder.getAdapterPosition()).getId();
                btc.setText("COURS(" + lstMatieres.get(vHolder.getAdapterPosition()).getCour_c() + ")");
                bttd.setText("TD(" + lstMatieres.get(vHolder.getAdapterPosition()).getTD_c() + ")");
                bttp.setText("TP(" + lstMatieres.get(vHolder.getAdapterPosition()).getTP_c() + ")");
                btds.setText("DS(" + lstMatieres.get(vHolder.getAdapterPosition()).getDS_c() + ")");
                btex.setText("EX(" + lstMatieres.get(vHolder.getAdapterPosition()).getEX_c() + ")");


                dialog.show();
            }
        });
        btc.setOnClickListener(this);
        bttd.setOnClickListener(this);
        bttp.setOnClickListener(this);
        btds.setOnClickListener(this);
        btex.setOnClickListener(this);
       /* btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "C";
                trans();
            }
        });
        bttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "TD";
                trans();
            }
        });
        bttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "TP";
                trans();
            }
        });
        btds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "DS";
                trans();
            }
        });
        btex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "EX";
                trans();
            }
        });*/

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

    @Override
    public void onClick(View v) {

        if (v == btc)
            type = "C";
        else if (v == bttd)
            type = "TD";
        else if (v == bttp)
            type = "TP";
        else if (v == btds)
            type = "DS";
        else if (v == btex)
            type = "EX";

        trans();


    }

    public void trans() {
        Bundle data = new Bundle();
        data.putString("type", type);
        data.putString("mat_id", id_m);
        dialog.dismiss();
        Fragment fragment = new DocumentList();
        fragment.setArguments(data);
        FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null).replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nom;
        private ImageView img_matr;
        private CardView cardView;
        //        private SmartImageView img_matr ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.item_card);
            tv_nom = itemView.findViewById(R.id.nom_matiere_id);
            img_matr = itemView.findViewById(R.id.img_matiere_id);
            //img_matr = (SmartImageView) itemView.findViewById(R.id.img_matiere_id);
        }

        public TextView getTv_nom() {
            return tv_nom;
        }

        public ImageView getImg_matr() {
            return img_matr;
        }

        public CardView getCardView() {
            return cardView;
        }
    }
}
