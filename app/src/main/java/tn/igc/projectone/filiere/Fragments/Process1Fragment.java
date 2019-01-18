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
import android.widget.ProgressBar;

import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process1Fragment extends Fragment {

	Button button;
	LinearLayout layout;
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

		layout = getView().findViewById(R.id.liste_formation);
		final FragmentManager fm = getActivity().getSupportFragmentManager();
		final ProgressBar bar = getActivity().findViewById(R.id.filiere_progressBar);
		View.OnClickListener click = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String formation = (((Button) view).getText().toString());
				Utils.nextProcess(fm, bar, formation);

				Log.d("CLICK", "onClick: " + formation);
			}
		};


		for (int i = 0; i < layout.getChildCount(); i++) {
			button = (Button) layout.getChildAt(i);
			button.setOnClickListener(click);

        }



	}





}
