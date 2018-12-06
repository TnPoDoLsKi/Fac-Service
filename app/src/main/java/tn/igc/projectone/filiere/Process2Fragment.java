package tn.igc.projectone.filiere;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import tn.igc.projectone.R;

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

		for (int i = 0; i < layout.getChildCount(); i++) {
			if (layout.getChildAt(i) instanceof Button) {
				button = (Button) layout.getChildAt(i);
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Utils.nextProcess(fm, bar);
					}
				});
			}
		}
	}

}
