package tn.igc.projectone.Settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
public class SettingsFragment extends Fragment {


    private Spinner spinnerFil;
    private APIInterface apiInterface;
    private ArrayList<String> majors;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerFil = view.findViewById(R.id.spinner_filier);
        majorsList();
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
                Log.d("Oops", "onFailure: " + t.fillInStackTrace());

            }


        });


    }
}
