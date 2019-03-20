package tn.igc.projectone.uploadEnonce;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Data;
import tn.igc.projectone.upload.fragments.NewFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainUploadFragment extends Fragment {
    public static APIInterface apiInterface;


    // used to store the list of selected tags (liste de filiere)
//    private ArrayList<String> selectedMajors;
    private ArrayList<Data> selectedMajorsData;
    private ArrayList<Data> majors;
    private ArrayList<Data> subjectsSem;


    private LinearLayout filiereListLayout;
    private LinearLayout sessionLayout;
    private boolean multipleTags = false;
    private Spinner spinnerFil;
    private Spinner spinnerMat, spinnerType, spinnerSession;
    private int semesterNbr = 1;
    private ImageView plusImg;
    private TextView plusTxt;
    private ProgressBar load;
    private Button upload;

    public MainUploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Upload");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_upload, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        selectedMajors = new ArrayList<>();
        selectedMajorsData = new ArrayList<>();

        filiereListLayout = view.findViewById(R.id.filiere_list);
        plusImg = view.findViewById(R.id.plusImg);
        plusTxt = view.findViewById(R.id.plusTxt);
        load = view.findViewById(R.id.load);
        upload = view.findViewById(R.id.uploadBtn);

        //upload btn
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        //arrays used for static values
        String semArr[] = {"Semestre 1", "Semestre 2"};
        final String[] typeArr = {"Examen", "DS", "Cours", "TD", "TP"};
        final String[] sessionArr = {"Principale", "Rattrapage"};

        spinnerMat = view.findViewById(R.id.spinner_matiere);
        spinnerFil = view.findViewById(R.id.spinner_filier);

        spinnerType = view.findViewById(R.id.spinner_type);
        final Spinner spinnerSem = view.findViewById(R.id.spinner_semester);
        // session section
        spinnerSession = view.findViewById(R.id.spinner_session);
        sessionLayout = view.findViewById(R.id.session_section);

        majorsList();

        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<String> adapterSem = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, semArr);
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, typeArr);

        // Apply the adapter to the spinner

        spinnerSem.setAdapter(adapterSem);
        spinnerType.setAdapter(adapterType);

        // Setup listener
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //majors Spinner
                if (parent == spinnerFil) {
                    String tagString = spinnerFil.getSelectedItem().toString();

                    //no item is selected
                    if (tagString.equals(majors.get(0).getName()))
                        return;

                        //an item is selected
                    else {
                        if (!Data.contains(tagString, selectedMajorsData)) {
                            if (multipleTags) {
                                //multiple selection
//                                selectedMajors.add(tagString);
                                String tempId = Data.getIdFromName(tagString, majors);
                                selectedMajorsData.add(new Data(tempId, tagString));

                                filiereListLayout.addView(addTag(tagString));
                                //return to default selection
                                spinnerFil.setSelection(0);
                            } else {
                                //single selection
                                if (selectedMajorsData.size() > 0) {
//                                    selectedMajors.clear();
                                    selectedMajorsData.clear();
                                }
//                                selectedMajors.add(tagString);
                                String tempId = Data.getIdFromName(tagString, majors);
                                selectedMajorsData.add(new Data(tempId, tagString));
                            }
                        } else {
                            //return to default selection
                            spinnerFil.setSelection(0);
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

                //type spinner
                if (parent == spinnerType) {
                    String typeString = parent.getSelectedItem().toString();
                    if (typeString.equals(typeArr[0])) {
                        ArrayAdapter<String> adapterSession = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, sessionArr);
                        spinnerSession.setAdapter(adapterSession);
                        sessionLayout.setVisibility(View.VISIBLE);
                    } else {
                        sessionLayout.setVisibility(View.INVISIBLE);
                    }

                }

                if ((parent == spinnerFil) || (parent == spinnerSem))
                    subjectsList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };


        spinnerFil.setOnItemSelectedListener(listener);
        spinnerSem.setOnItemSelectedListener(listener);
        spinnerType.setOnItemSelectedListener(listener);

        //  multipleTags listener
        filiereListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!multipleTags) {
                    String selection = spinnerFil.getSelectedItem().toString();
                    if (!selection.equals(majors.get(0).getName())) {
                        multipleTags = true;
                        ((ViewGroup) v).removeAllViews();
                        ((ViewGroup) v).addView(addTag(selection));
                        //return to default
                        spinnerFil.setSelection(0);
                    } else
                        Toast.makeText(getContext(), "Choisir une filiere", Toast.LENGTH_SHORT).show();

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

                if (selectedMajorsData.size() > 1) {
//                    selectedMajors.remove(tag);
                    Data.remove(tag, selectedMajorsData);
                    ((ViewGroup) v.getParent()).removeView(v);
                } else {
                    //final tag to remove
                    ((ViewGroup) v.getParent()).removeView(v);
//                    selectedMajors.remove(tag);
                    Data.remove(tag, selectedMajorsData);

                    filiereListLayout.addView(plusImg);
                    filiereListLayout.addView(plusTxt);
                    multipleTags = false;
                }
                subjectsList();


            }
        });

        return view;
    }

    /**
     * methods used to update the interface after user selection
     */
    private void subjectsList() {

        load.setVisibility(View.VISIBLE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Log.d("majorsData", "subjectsList: " + selectedMajorsData);
        Call<JsonArray> call_subjects = apiInterface.getSubjectsByMajor(Data.copyIds(selectedMajorsData));
        call_subjects.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray resArr = response.body().getAsJsonArray();
                subjectsSem = new ArrayList<>();

                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    JsonObject subObj = resArr.get(i).getAsJsonObject();
                    String subName = subObj.get("name").getAsString();
                    String subId = subObj.get("_id").getAsString();
                    int subSemester = subObj.get("semestre").getAsInt();
                    if (subSemester == semesterNbr)
                        subjectsSem.add(new Data(subId, subName));


                }

                String[] matArr;
                matArr = Data.copyValues(subjectsSem);

                ArrayAdapter<String> adapterMat = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, matArr);

                spinnerMat.setAdapter(adapterMat);

                load.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("oops", "onFailure: " + t.fillInStackTrace());

            }


        });
    }


    /**
     * updates the spinner controlling the major list
     */
    public void majorsList() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        majors = new ArrayList<>();
        //premier element
        majors.add(new Data("0", "Choisir une filiere"));

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
                    String id = obj.get("_id").getAsString();
                    majors.add(new Data(id, name));


                }

                final String filArr[] = Data.copyValues(majors);
                try {

                    ArrayAdapter<String> adapterFil = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, filArr);
                    spinnerFil.setAdapter(adapterFil);
                } catch (NullPointerException ignored) {

                }

                load.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("oops", "onFailure: " + t.fillInStackTrace());

            }


        });


    }

    /**
     * pass the required data for the upload
     */
    public void upload() {
        String type;
        String session = null;
        String subId;
        try {
            subId = spinnerMat.getSelectedItem().toString();
            subId = Data.getIdFromName(subId, subjectsSem);
            type = spinnerType.getSelectedItem().toString();
        } catch (NullPointerException ex) {
            Toast.makeText(getContext(), "Choisir une matiere !! ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sessionLayout.getVisibility() == View.VISIBLE)
            session = spinnerSession.getSelectedItem().toString();

        Fragment fragment = NewFragment.newInstance(subId, type,session);
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
    }


}
