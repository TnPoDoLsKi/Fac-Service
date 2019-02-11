package tn.igc.projectone.filiere.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.API.APIClient;
import tn.igc.projectone.filiere.API.APIInterface;
import tn.igc.projectone.filiere.Adapters.filiereCustomAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process1Fragment extends Fragment {

	Button button;
	LinearLayout layout;
    private APIInterface apiInterface;
    private ArrayList<String> majors;

    public Process1Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_process1, container, false);
	}

	@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//		layout = getView().findViewById(R.id.liste_formation);
        final FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        final ProgressBar bar = getActivity().findViewById(R.id.filiere_progressBar);


        apiInterface = APIClient.getClient().create(APIInterface.class);
        majors = new ArrayList<String>();

        Call<JsonArray> call_all_majors = apiInterface.getAllMajors();
        call_all_majors.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                //get all (formation)
                JsonArray resArr = response.body().getAsJsonArray();
                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    JsonObject obj = resArr.get(i).getAsJsonObject();
                    //getting all majors names
                    JsonObject formation = obj.get("formation").getAsJsonObject();
                    String formationName = formation.get("description").getAsString();
                    majors.add(formationName);


                }

                //set the adapter
                final String filArr[] = (String[]) majors.toArray(new String[0]);
//                String test[] = {"License", "Prepa"};
                ListAdapter adapter = new filiereCustomAdapter(getContext(), filArr, fm, bar);
                ListView listView = getView().findViewById(R.id.liste_formation);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("SHIT", "onFailure: " + t.fillInStackTrace());

            }


        });


    }
}
