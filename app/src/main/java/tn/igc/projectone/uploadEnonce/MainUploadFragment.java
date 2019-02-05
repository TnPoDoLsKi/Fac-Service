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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.API.APIClient;
import tn.igc.projectone.filiere.API.APIInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainUploadFragment extends Fragment {
    public static APIInterface apiInterface;
    public static APIInterface apiInterfaceToken;


    // used to store the list of selected tags (liste de filiere)
    private ArrayList<String> filiereList;
    private LinearLayout filiereListLayout;
    private boolean multipleTags = false;

    private Spinner spinnerFil;
    private Spinner spinnerMat;
    private ArrayList<String> majors;
    private ArrayList<String> subjectsSem1;
    private ArrayList<String> subjectsSem2;
    private int semesterNbr = 1;
    private String subject;

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
        //majorsList();
        //final String filArr[] = {"Prep-A1", "Prep-A2", "FI-A1", "LFSI-A1",};
//        final String filArr[] = (String[]) majorsList().toArray(new String[0]);

        String semArr[] = {"Semestre 1", "Semester 2"};
        String typeArr[] = {"Cours", "TD", "DS", "Examen", "TP"};

        spinnerMat = view.findViewById(R.id.spinner_matiere);
        spinnerFil = view.findViewById(R.id.spinner_filier);
        Spinner spinnerType = view.findViewById(R.id.spinner_type);
        final Spinner spinnerSem = view.findViewById(R.id.spinner_semester);

        majorsList();

// Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<String> adapterSem = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, semArr);
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, typeArr);

// Apply the adapter to the spinner

        spinnerSem.setAdapter(adapterSem);
        spinnerType.setAdapter(adapterType);

// Setup listener
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((parent == spinnerFil) || (parent == spinnerSem))
                    subjectsList();
                Log.d("SHIT", "onItemSelected: ");
                //majors Spinner
                if (parent == spinnerFil) {
                    String tagString = spinnerFil.getSelectedItem().toString();
                    if (!filiereList.contains(tagString)) {
                        if (multipleTags) {
                            filiereList.add(tagString);
                            filiereListLayout.addView(addTag(tagString));
                        } else {
                            if (filiereList.size() > 0)
                                filiereList.clear();
                            filiereList.add(tagString);
                        }
                    }
                }
                //semester Spinner
                if (parent == spinnerSem) {
                    String semString = spinnerSem.getSelectedItem().toString();
                    if (semString.contains("1"))
                        semesterNbr = 1;
                    else
                        semesterNbr = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };


        spinnerFil.setOnItemSelectedListener(listener);
        spinnerSem.setOnItemSelectedListener(listener);

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
                    subjectsList();
                }

            }
        });

        return view;
    }

    /**
     * methods used to update the interface after user selection
     */
    private void subjectsList() {
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<JsonArray> call_all_majors = apiInterface.getAllMajors();
        call_all_majors.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray resArr = response.body().getAsJsonArray();
                subjectsSem1 = new ArrayList<>();
                subjectsSem2 = new ArrayList<>();

                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    JsonObject obj = resArr.get(i).getAsJsonObject();

                    String name = obj.get("name").getAsString();
                    JsonArray subArr = obj.getAsJsonArray("subjects");

                    //adding subjects of the selected subjects
                    if (filiereList.contains(name)) {

                        for (int j = 0; j < subArr.size(); j++) {
                            JsonObject subObj = subArr.get(j).getAsJsonObject();
                            int semester = subObj.get("semestre").getAsInt();
                            String subName = subObj.get("name").getAsString();

                            //adding subjects to 2 seperate arrays
                            if (semester == 1) {
                                subjectsSem1.add(subName);
                            } else if (semester == 2) {
                                subjectsSem2.add(subName);
                            }

                        }
                    }

                }

                String[] matArr = {};

                if (semesterNbr == 1) {
                    matArr = subjectsSem1.toArray(new String[0]);
                } else if (semesterNbr == 2) {
                    matArr = subjectsSem2.toArray(new String[0]);
                }
                ArrayAdapter<String> adapterMat = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, matArr);

                spinnerMat.setAdapter(adapterMat);


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("SHIT", "onFailure: " + t.fillInStackTrace());

            }


        });
    }

    public void majorsList() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        majors = new ArrayList<String>();

        Call<JsonArray> call_all_majors = apiInterface.getAllMajors();
        call_all_majors.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                JsonArray resArr = response.body().getAsJsonArray();

                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    JsonObject obj = resArr.get(i).getAsJsonObject();
                    //getting all majors names
                    String name = obj.get("name").getAsString();
                    majors.add(name);


                }

                final String filArr[] = (String[]) majors.toArray(new String[0]);
                ArrayAdapter<String> adapterFil = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, filArr);
                spinnerFil.setAdapter(adapterFil);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("SHIT", "onFailure: " + t.fillInStackTrace());

            }


        });


    }


}
