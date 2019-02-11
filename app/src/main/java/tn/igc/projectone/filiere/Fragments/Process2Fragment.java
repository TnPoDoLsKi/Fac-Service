package tn.igc.projectone.filiere.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process2Fragment extends Fragment {


	private Button button;
	ConstraintLayout layout ;

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
		final FragmentManager fm = getActivity().getSupportFragmentManager();
		final ProgressBar bar = getActivity().findViewById(R.id.filiere_progressBar);

		View.OnClickListener click = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String niveau = (((Button) view).getText().toString());
				Utils.nextProcess(fm, bar, niveau);

				Log.d("CLICK", "onClick: " + niveau);
			}
		};

		Button b1 = getView().findViewById(R.id.button_1ere);
		b1.setOnClickListener(click);
        Button b2 = getView().findViewById(R.id.button_2eme);
		b2.setOnClickListener(click);
        Button b3 = getView().findViewById(R.id.button_3eme);
		b3.setOnClickListener(click);
        ImageView img3 = getView().findViewById(R.id.img_3eme);

        // testing for some majors to remove 3rd year
        ArrayList<String> twoYearMajors = new ArrayList<>();
        twoYearMajors.add("Master");
        twoYearMajors.add("Prep");
        twoYearMajors.add("5c5080a2bb95dc104b9934ad");

        if (twoYearMajors.contains(Utils.filere[0])) {
            b3.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
        }
	}

}
