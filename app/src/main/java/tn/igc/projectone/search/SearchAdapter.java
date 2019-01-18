package tn.igc.projectone.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import tn.igc.projectone.R;

public class SearchAdapter extends BaseAdapter implements Filterable {

CustomFilter cs ;
    Context c;
    ArrayList<String> originalArray,tempArray;
    public SearchAdapter(Context c,ArrayList<String> originalArray){
        this.c=c;
        this.originalArray=originalArray;
        this.tempArray=originalArray;
    }
    @Override
    public int getCount() {
        return originalArray.size();
    }

    @Override
    public Object getItem(int position) {
        return originalArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.cardview,null);
        TextView t1=(TextView)row.findViewById(R.id.textview);
        t1.setText(originalArray.get(position));

        return row;
    }

    @Override
    public Filter getFilter() {
        if(cs==null){
            cs=new CustomFilter();
        }
        return cs;
    }
    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                constraint = constraint.toString().toUpperCase();

                ArrayList<String> filters = new ArrayList<>();
                for (int i = 0; i < tempArray.size(); i++) {
                    if (tempArray.get(i).toUpperCase().contains(constraint)) {
                        String fruit = tempArray.get(i);
                        filters.add(fruit);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }
          else {
                results.count=tempArray.size();
                results.values=tempArray;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
originalArray = (ArrayList<String>) results.values;
notifyDataSetChanged();
        }
    }
}
