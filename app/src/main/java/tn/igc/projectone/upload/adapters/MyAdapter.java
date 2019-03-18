package tn.igc.projectone.upload.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.Interface.RecyclerViewClickListener;
import tn.igc.projectone.upload.other.FileImage;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<FileImage> list = new ArrayList<>();
    private Context context;
    private RecyclerViewClickListener mListener;


    public MyAdapter(Context context, RecyclerViewClickListener listener) {

        this.context = context;
        mListener = listener;
    }

    public void addImage(ArrayList<FileImage> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    public void removeImage(int position) {
        this.list.remove(position);  // remove the item from list
        notifyItemRemoved(position);
    }

    public ArrayList<FileImage> getList() {
        return this.list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
            inflate(R.layout.image, parent, false);
        Holder vholder = new Holder(v, mListener);
        Log.e("onCreateViewHolder", " 2  ");

        return vholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //cette méthode est appelée chaque fois que vous faites défiler le contenu du viewholder
        //Uri imageUri = Uri.fromFile(new File(list.get(position)));// For files on device
        //Log.e("hello", "- " + imageUri.toString());
        File f = new File(list.get(position).getImage());
        Bitmap d = new BitmapDrawable(context.getResources(), f.getAbsolutePath()).getBitmap();
        //Bitmap scaled = com.fxn.utility.Utility.getScaledBitmap(512, com.fxn.utility.Utility.getExifCorrectedBitmap(f));
        Bitmap scaled = com.fxn.utility.Utility.getScaledBitmap(512, d);

        OutputStream imagefile = null;
        try {
            imagefile = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        d.compress(Bitmap.CompressFormat.JPEG, 70, imagefile);


        ((Holder) holder).iv.setImageBitmap(d);

        ((Holder) holder).txt_name.setText(f.getName());
        Log.e("onBindViewHolder", " 1  ");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView iv;
        public TextView txt_name;
        public Button btn_delete;


        public Holder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            txt_name = itemView.findViewById(R.id.tvname);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            mListener = listener;
            btn_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());

        }
    }
}