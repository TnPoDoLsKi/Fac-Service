package tn.igc.projectone.filiere.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Data;
import tn.igc.projectone.filiere.Utils.Utils;

public class filiereCustomAdapter extends ArrayAdapter<String> {
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;
    private ArrayList<Data> dataArrayList;

    /**
     * @param context :context
     * @param filiere : Data of the list
     * @param fm      : reference to the fragmentManager
     * @param pb      : reference to the progressBar
     */
    public filiereCustomAdapter(Context context, Data[] filiere, FragmentManager fm, ProgressBar pb) {
        super(context, R.layout.button_filiere_process3, Data.copyValues(filiere));
        dataArrayList = new ArrayList<>(Arrays.asList(filiere));
        fragmentManager = fm;
        progressBar = pb;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View filiereView = layoutInflater.inflate(R.layout.button_filiere_process3, parent, false);

        Button button = filiereView.findViewById(R.id.filiere_btn);
        button.setText(getItem(position));
        //listener to pass to the next fragment/activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String filiere = (((Button) view).getText().toString());
                Data dataToPass = Data.getDataFromName(filiere, dataArrayList);
                if (Utils.processProgress < 2) {
                    Utils.nextProcess(fragmentManager, progressBar, dataToPass);
                } else {
                    Utils.nextProcess(fragmentManager, progressBar, dataToPass, getContext());
                }
                Log.d("Oops", "onClick: " + dataToPass + "  " + Utils.selectedMajor);

            }
        });

        return filiereView;
    }
}
