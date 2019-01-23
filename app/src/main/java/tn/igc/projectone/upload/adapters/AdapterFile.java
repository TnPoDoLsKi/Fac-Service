package tn.igc.projectone.upload.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tn.igc.projectone.R;
import tn.igc.projectone.upload.other.File;

public class AdapterFile extends ArrayAdapter<File> {


    public AdapterFile(Context context, int resource, int textViewResourceId, List<File> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        File p=getItem(position);



        View v = super.getView(position, convertView, parent);

        TextView name = (TextView) v.findViewById(R.id.tvname);
        TextView percentage = (TextView) v.findViewById(R.id.Tvpourcent);
        ImageView image = (ImageView) v.findViewById(R.id.my_image);

        name.setText(p.getName());
        percentage.setText(p.getPercentage());
        Picasso.get()
                .load(p.getImage())
                .placeholder(R.drawable.uploadicon)
                .error(R.drawable.uploadicon)
                .into(image);
        //Picasso.get().load(p.getImage()).into(image);
        return v;

    }
}
