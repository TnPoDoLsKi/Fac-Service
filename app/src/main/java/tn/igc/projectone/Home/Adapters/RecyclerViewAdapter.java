package tn.igc.projectone.Home.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tn.igc.projectone.Home.Classes.Matiere;
import tn.igc.projectone.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext ;
    List<Matiere> lstMatieres;
    Dialog dialog ;

    public RecyclerViewAdapter(Context context, List<Matiere> lstMatieres) {
        this.mContext = context;
        this.lstMatieres = lstMatieres;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v=LayoutInflater.from(mContext).inflate(R.layout.item_matiere,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_choix);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext," "+lstMatieres.get(vHolder.getAdapterPosition()).getNom_matr(),Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });
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
