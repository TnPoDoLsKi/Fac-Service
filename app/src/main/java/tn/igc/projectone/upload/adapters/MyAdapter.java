package tn.igc.projectone.upload.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.other.FileImage;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FileImage> list = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void addImage(ArrayList<FileImage> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    private ArrayList<FileImage> removerItem(int position,ArrayList<FileImage> l) {

        l.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, l.size());
    return l;

    }
    public ArrayList<FileImage> getList() {


        return this.list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.image, parent, false);
        Holder vholder = new Holder(v);

        return vholder ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Uri imageUri = Uri.fromFile(new File(list.get(position)));// For files on device
        //Log.e("hello", "- " + imageUri.toString());
        File f = new File(list.get(position).getImage());
        Bitmap d = new BitmapDrawable(context.getResources(), f.getAbsolutePath()).getBitmap();
        //Bitmap scaled = com.fxn.utility.Utility.getScaledBitmap(512, com.fxn.utility.Utility.getExifCorrectedBitmap(f));
        Bitmap scaled = com.fxn.utility.Utility.getScaledBitmap(512, d);
        ((Holder) holder).iv.setImageBitmap(scaled);

        ((Holder) holder).txt_name.setText(f.getName());
        // ((Holder) holder).iv.setImageURI(imageUri);
        ((Holder) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               list= removerItem(position,list);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView txt_name;
        public Button btn_delete;


        public Holder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            txt_name = itemView.findViewById(R.id.tvname);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
    public void updateList(ArrayList<FileImage> newList){
        list =new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }


}