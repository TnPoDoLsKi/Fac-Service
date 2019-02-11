package tn.igc.projectone.filiere.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.API.APIClient;
import tn.igc.projectone.filiere.API.APIInterface;
import tn.igc.projectone.filiere.Adapters.filiereCustomAdapter;
import tn.igc.projectone.filiere.Utils.Utils;

import static tn.igc.projectone.uploadEnonce.MainUploadFragment.apiInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process3Fragment extends Fragment {

    ArrayList<String> majors;

    public Process3Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_process3, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        majors = new ArrayList<String>();

        Call<JsonArray> call_all_majors = apiInterface.getAllMajors();
        call_all_majors.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                //get all (filiers)
                JsonArray resArr = response.body().getAsJsonArray();
                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    JsonObject obj = resArr.get(i).getAsJsonObject();
                    //getting all majors names
                    String name = obj.get("name").getAsString();
                    //get the level
                    JsonObject level = obj.get("level").getAsJsonObject();
                    String levelName = level.get("description").getAsString();
                    //get the formation
                    JsonObject formation = obj.get("formation").getAsJsonObject();
                    String formationName = formation.get("description").getAsString();
                    if (formationName.equals(Utils.filere[0]) && levelName.equals(Utils.filere[1]))
                        majors.add(name);

                }

                //set the adapter
                final String filArr[] = (String[]) majors.toArray(new String[0]);
//                String test[] = {"Genie Logiciel", "Informatique Industruel"};
                ListAdapter adapter = new filiereCustomAdapter(getContext(), filArr, null, null);
                ListView listView = getView().findViewById(R.id.liste_filiere);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Oops", "onFailure: " + t.fillInStackTrace());

            }


        });


	}
}
