package tn.igc.projectone.filiere.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Adapters.filiereCustomAdapter;
import tn.igc.projectone.filiere.Utils.Data;
import tn.igc.projectone.filiere.Utils.Utils;

import static tn.igc.projectone.uploadEnonce.MainUploadFragment.apiInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process3Fragment extends Fragment {

    ArrayList<Data> majors;

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
        majors = new ArrayList<>();

        Call<JsonArray> call_major_by_level = apiInterface.getMajor(Utils.selectedMajor.get(1).getId());
        call_major_by_level.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.body() == null) return;
                //get all (filiers)
                JsonArray resArr = response.body().getAsJsonArray();
                int size = resArr.size();
                if (size < 0) return;

                for (int i = 0; i < size; i++) {
                    JsonObject obj = resArr.get(i).getAsJsonObject();
                    //getting all majors names
                    String formationName = obj.get("name").getAsString();
                    String id = obj.get("_id").getAsString();

                    majors.add(new Data(id, formationName));

                }

                //set the adapter
                final Data filArr[] = majors.toArray(new Data[0]);
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
