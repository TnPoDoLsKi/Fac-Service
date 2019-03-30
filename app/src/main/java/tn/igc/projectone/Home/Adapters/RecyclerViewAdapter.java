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

import com.google.firebase.analytics.FirebaseAnalytics;

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


    Context mContext ;
    private FirebaseAnalytics mFirebaseAnalytics;
    List<Matiere> lstMatieres;
    Dialog dialog ;
    Button btc;
    Button bttd;
    Button bttp;
    Button btex;
    Button btds;
    String type;
    String mat_id;
    String id_m;
    int nb_c;
    int nb_td;
    int nb_ds;
    int nb_ex;
    int nb_tp;


    public RecyclerViewAdapter(Context context,List<Matiere> lstMatieres) {
        this.mContext = context;
        this.lstMatieres = lstMatieres;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v=LayoutInflater.from(mContext).inflate(R.layout.item_matiere,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);

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

                id_m = lstMatieres.get(vHolder.getAdapterPosition()).getId();
                nb_c = lstMatieres.get(vHolder.getAdapterPosition()).getCour_c();
                nb_td=lstMatieres.get(vHolder.getAdapterPosition()).getTD_c();
                nb_tp=lstMatieres.get(vHolder.getAdapterPosition()).getTP_c();
                nb_ds=lstMatieres.get(vHolder.getAdapterPosition()).getDS_c();
                nb_ex=lstMatieres.get(vHolder.getAdapterPosition()).getEX_c();
                btc.setText("COURS ("+nb_c+")");
                bttd.setText("TD ("+nb_td+")");
                bttp.setText("TP ("+nb_tp+")");
                btds.setText("DS ("+nb_ds+")");
                btex.setText("EX ("+nb_ex+")");

                btc.setEnabled(nb_c>0);
                bttd.setEnabled(nb_td>0);
                bttp.setEnabled(nb_tp>0);
                btds.setEnabled(nb_ds>0);
                btex.setEnabled(nb_ex>0);


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
        int id ;
        Bundle bundle = new Bundle();


        if (v==btc)
            type="C";

        else if(v==bttd)
            type="TD";
        else if(v==bttp)
            type="TP";
        else if(v==btds)
            type="DS";
        else if(v==btex)
            type="EX";


        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, type);

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        trans();


    }
    public void trans(){
        Bundle data = new Bundle();
        data.putString("type", type);
        data.putString("mat_id", id_m);
        dialog.dismiss();
        Fragment fragment = new DocumentList();
        fragment.setArguments(data);
        FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null).replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder{
        private TextView tv_nom;
        private ImageView img_matr ;
        private CardView cardView;
        //        private SmartImageView img_matr ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView =(CardView) itemView.findViewById(R.id.item_card);
            tv_nom = (TextView) itemView.findViewById(R.id.nom_matiere_id) ;
            img_matr = (ImageView) itemView.findViewById(R.id.img_matiere_id);
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
