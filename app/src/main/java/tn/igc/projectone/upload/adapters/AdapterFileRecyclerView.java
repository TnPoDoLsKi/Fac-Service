package tn.igc.projectone.upload.adapters;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.other.FileImage;

public class AdapterFileRecyclerView extends RecyclerView.Adapter<AdapterFileRecyclerView.MyViewHolder> {

    Context mContext ;
    public ArrayList<FileImage> listfile;


    public AdapterFileRecyclerView(Context context, ArrayList<FileImage> listfile) {
        this.mContext = context;
        this.listfile= listfile;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.cardmariem,viewGroup,false);
        // ------------------------------- Add final to vHolder declart -------------------------------------------------------
        final MyViewHolder vHolder = new MyViewHolder(v);



        return vHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.pathName.setText(listfile.get(i).getName());
       /*Uri imageUri = Uri.fromFile(new File(listfile.get(i).getImage()));// For files on device
        File f = new File(listfile.get(i).getImage());*/
        Picasso.get().load(listfile.get(i).getImage()).into(myViewHolder.image);




    }

    @Override
    public int getItemCount() {
        return listfile.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public View cardView;
        public Button btnDelete;
        private TextView pathName;
        private ImageView image ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
          //  cardView =(CardView) itemView.findViewById(R.id.cardmariem);
            btnDelete=(Button) itemView.findViewById(R.id.btn_delete) ;
            pathName= (TextView) itemView.findViewById(R.id.tvname) ;
            image = (ImageView) itemView.findViewById(R.id.my_image);

        }
    }
    public void updateList(ArrayList<FileImage> newList){
        listfile =new ArrayList<>();
        listfile.addAll(newList);
        notifyDataSetChanged();
    }


}

