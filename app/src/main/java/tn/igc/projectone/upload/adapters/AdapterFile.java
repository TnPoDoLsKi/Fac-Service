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
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.adapters.InstantImageAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.io.File;

import tn.igc.projectone.R;
import tn.igc.projectone.upload.other.FileImage;

public class AdapterFile extends ArrayAdapter<FileImage> {
private Context context;

    public AdapterFile(Context context, int resource, int textViewResourceId, ArrayList<FileImage> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FileImage p=getItem(position);



        View v = super.getView(position, convertView, parent);

        TextView name = (TextView) v.findViewById(R.id.tvname);
        TextView percentage = (TextView) v.findViewById(R.id.Tvpourcent);
        ImageView image = (ImageView) v.findViewById(R.id.my_image);


        percentage.setText(p.getPercentage());
       /*// Picasso.get()
                .load(p.getImage())
                .placeholder(R.drawable.uploadicon)
                .error(R.drawable.uploadicon)
                .into(image);*/

        Uri imageUri = Uri.fromFile(new File(p.getImage()));// For files on device
        Log.e("hello", "- " + imageUri.toString());
        File f = new File(p.getImage());
        name.setText(f.getName());
        Picasso.get().load(imageUri).into(image);

        return v;

    }
}
