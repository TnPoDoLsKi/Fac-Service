package tn.igc.projectone.filiere.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Adapters.filiereCustomAdapter;
import tn.igc.projectone.filiere.Utils.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process1Fragment extends Fragment {


    private APIInterface apiInterface;
    private ArrayList<Data> majors;

    public Process1Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_process1, container, false);
	}

	@Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//		layout = getView().findViewById(R.id.liste_formation);
        final FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        final ProgressBar bar = getActivity().findViewById(R.id.filiere_progressBar);


        apiInterface = APIClient.getClient().create(APIInterface.class);
        majors = new ArrayList<>();

        Call<JsonArray> call_all_formation = apiInterface.getAllFormations();
        call_all_formation.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                //get all (formation)
                JsonArray resArr = response.body().getAsJsonArray();
                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    JsonObject obj = resArr.get(i).getAsJsonObject();
                    //getting all majors names
                    String formationName = obj.get("name").getAsString();
                    String id = obj.get("_id").getAsString();

                    majors.add(new Data(id, formationName));


                }

                //set the adapter
                final Data filArr[] = majors.toArray(new Data[0]); // ArrayList -> array
                ListAdapter adapter = new filiereCustomAdapter(getContext(), filArr, fm, bar);
                ListView listView = getView().findViewById(R.id.liste_formation);
                listView.setAdapter(adapter);

                view.findViewById(R.id.progressBar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Oops", "onFailure: " + t.fillInStackTrace());

            }


        });


    }
}
