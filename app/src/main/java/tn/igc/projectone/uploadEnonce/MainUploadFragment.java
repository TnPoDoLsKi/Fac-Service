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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import tn.igc.projectone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainUploadFragment extends Fragment {

    // used to store the list of selected tags (liste de filiere)
    private ArrayList<String> filiereList;
    private LinearLayout filiereListLayout;
    private boolean multipleTags = false;


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

        filiereList = new ArrayList<>();
        filiereListLayout = view.findViewById(R.id.filiere_list);


        final String filArr[] = {"Prep-A1", "Prep-A2", "FI-A1", "LFSI-A1",};
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

// Setup listener
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((parent == spinnerFil) || (parent == spinnerSem))
                    refreshMat();


                if (parent == spinnerFil) {
                    String tagString = spinnerFil.getSelectedItem().toString();
                    if (!filiereList.contains(tagString)) {
                        if (multipleTags) {
                            filiereList.add(tagString);
                            filiereListLayout.addView(addTag(tagString));
                        } else {
                            if (filiereList.size() > 0)
                                filiereList.remove(0);
                            filiereList.add(tagString);
                        }
                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };


        spinnerFil.setOnItemSelectedListener(listener);


//  multipleTags listener
        filiereListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!multipleTags) {
                    multipleTags = true;
                    ((ViewGroup) v).removeAllViews();
                    ((ViewGroup) v).addView(addTag(spinnerFil.getSelectedItem().toString()));
                }
            }
        });
    }

    /**
     * methods used to update the interface after user selection
     */
    private void refreshMat() {


    }

    /**
     * method used to append new tags (filiere)
     *
     * @param tag : tag to be added
     */
    private View addTag(final String tag) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.button_tag_upload_ennonce, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMarginEnd(10);

        view.setLayoutParams(lp);
        ((TextView) view.findViewById(R.id.filiere_name)).setText(tag);

        //each tag is removed onClick
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (filiereList.size() > 1) {
                    filiereList.remove(tag);
                    ((ViewGroup) v.getParent()).removeView(v);
                    Log.d("REMOVE", "onClick:" + filiereList);
                }

            }
        });

        return view;
    }

}
