package tn.igc.projectone.uploadEnonce;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import tn.igc.projectone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainUploadFragment extends Fragment {


    public MainUploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_upload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String filArr[] = {"prep", "ing", "licence"};
        String matArr[] = {"math", "info", "physique"};
        String semArr[] = {"Semestre 1", "Semester 2"};
        String typeArr[] = {"Cours", "TD", "DS", "Examen", "TP"};

        final Spinner spinnerFil = view.findViewById(R.id.spinner_filier);
        final Spinner spinnerSem = view.findViewById(R.id.spinner_semester);
        Spinner spinnerMat = view.findViewById(R.id.spinner_matiere);
        Spinner spinnerType = view.findViewById(R.id.spinner_type);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterFil = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, filArr);
        ArrayAdapter<String> adapterSem = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, semArr);
        ArrayAdapter<String> adapterMat = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, matArr);
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, typeArr);

// Apply the adapter to the spinner
        spinnerFil.setAdapter(adapterFil);
        spinnerSem.setAdapter(adapterSem);
        spinnerMat.setAdapter(adapterMat);
        spinnerType.setAdapter(adapterType);


        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((parent == spinnerFil) || (parent == spinnerSem))
                    refreshMat();
                Log.d("UPLOAD", "refreshMat: " + spinnerFil.getSelectedItem().toString());
                /*
                TODO : add multiple selection
                TODO : add button and tags design

                 */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };


        spinnerFil.setOnItemSelectedListener(listener);


    }

    /**
     * methods used to update the interface after user selection
     */
    private void refreshMat() {


    }

}
