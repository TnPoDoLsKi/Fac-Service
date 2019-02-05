package tn.igc.projectone.documentList.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.Document;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<Document> lsDocument;
    private ArrayList<Document> documentListFull;


    public RecyclerViewAdapter(Context context, ArrayList<Document> lsDocument) {
        this.mContext = context;
        this.lsDocument = lsDocument;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.cardview, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_nom.setText(lsDocument.get(i).getDocName());
        myViewHolder.tv_nom2.setText(lsDocument.get(i).getProfileName());
        myViewHolder.img_matr.setImageResource(lsDocument.get(i).getChecked());
        myViewHolder.img_matr2.setImageResource(lsDocument.get(i).getProfilePic());

    }

    @Override
    public int getItemCount() {
        return lsDocument.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nom;
        private ImageView img_matr;
        private TextView tv_nom2;
        private ImageView img_matr2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nom = (TextView) itemView.findViewById(R.id.textview);
            img_matr = (ImageView) itemView.findViewById(R.id.circularImageView);
            tv_nom2 = (TextView) itemView.findViewById(R.id.textView1);
            img_matr2 = (ImageView) itemView.findViewById(R.id.circularImageView2);

        }
    }

    public void updateList(ArrayList<Document> newList) {
        lsDocument = new ArrayList<>();
        lsDocument.addAll(newList);
        notifyDataSetChanged();
    }


}
