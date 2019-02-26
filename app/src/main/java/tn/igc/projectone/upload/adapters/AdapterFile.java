package tn.igc.projectone.upload.adapters;

import android.content.Context;
import androidx.annotation.NonNull;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.adapters.InstantImageAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import id.zelory.compressor.Compressor;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.other.FileImage;

public class AdapterFile extends ArrayAdapter<FileImage> {
    private final ArrayList<FileImage> listImage;
    private Context context;

    public AdapterFile(Context context, int resource, int textViewResourceId, ArrayList<FileImage> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.listImage = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        FileImage p=getItem(position);



        View v = super.getView(position, convertView, parent);

        TextView name = (TextView) v.findViewById(R.id.tvname);
        ImageView image = (ImageView) v.findViewById(R.id.my_image);
        Button btn_delete = (Button) v.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listImage.remove(position);
                notifyDataSetChanged();

            }
        });


       /*// Picasso.get()
                .load(p.getImage())
                .placeholder(R.drawable.uploadicon)
                .error(R.drawable.uploadicon)
                .into(image);*/
        File f = new File(p.getImage());
        name.setText(f.getName());
        File compressedImage=f;
        try {
            compressedImage = new Compressor(getContext())
                    .setQuality(20)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)

                    .compressToFile(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri = Uri.fromFile(compressedImage);// For files on device
        //Log.e("hello", "- " + imageUri.toString());


        Picasso.get().load(imageUri).into(image);

        return v;

    }
}
