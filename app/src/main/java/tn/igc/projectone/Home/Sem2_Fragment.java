package tn.igc.projectone.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tn.igc.projectone.R;

public class Sem2_Fragment extends Fragment {
    private static final String TAG = "Sem2Fragment";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sem2_fragment,container,false);


        return view;
    }
}
