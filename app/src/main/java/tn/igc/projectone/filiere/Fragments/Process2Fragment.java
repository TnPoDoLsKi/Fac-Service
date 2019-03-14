package tn.igc.projectone.filiere.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Data;
import tn.igc.projectone.filiere.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process2Fragment extends Fragment {


	ConstraintLayout layout ;
    private ArrayList<Data> levels;
    private FragmentManager fm;
    private ProgressBar bar;
    View.OnClickListener click;
    Button b[] = new Button[3];


    public Process2Fragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_process2, container, false);
	}
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		layout = getView().findViewById(R.id.liste_niveau);

        fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        bar = getActivity().findViewById(R.id.filiere_progressBar);


        click = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String niveau = (((Button) view).getText().toString());
                Data dataToPass = Data.getDataFromName(niveau, levels);
                Log.d("Oops", "onClick: " + dataToPass + "  " + Utils.selectedMajor);
                Utils.nextProcess(fm, bar, dataToPass);

			}
		};

        b[0] = getView().findViewById(R.id.button_1ere);
        b[0].setOnClickListener(click);
        b[1] = getView().findViewById(R.id.button_2eme);
        b[1].setOnClickListener(click);
        b[2] = getView().findViewById(R.id.button_3eme);

        ImageView img3 = getView().findViewById(R.id.img_3eme);

        getLevels(b[2], img3);

    }

    public void getLevels(final Button b3, final ImageView img3) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        levels = new ArrayList<>();
        String id = Utils.selectedMajor.get(0).getId();
        Call<JsonArray> call_all_levels = apiInterface.getAllLevels(id);

        call_all_levels.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() == null) {
                    Log.d("Oops", "onResponse: " + response.code());
                    return;
                }
                //get all (formation)
                JsonArray resArr = response.body().getAsJsonArray();
                Log.d("Oops", "onResponse: " + resArr);
                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    //get all levels here
                    JsonObject obj = resArr.get(i).getAsJsonObject();
                    String name = obj.get("name").getAsString();
//                    levels.add(new Data(obj.get("_id").getAsString(),b[i].getText().toString()));
                    levels.add(new Data(obj.get("_id").getAsString(), name));
                    b[i].setText(name);


                }
                if (size < 3) {
                    b3.setVisibility(View.GONE);
                    img3.setVisibility(View.GONE);
                } else
                    b3.setOnClickListener(click);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Oops", "onFailure: " + t.fillInStackTrace());

            }


        });


    }


}
