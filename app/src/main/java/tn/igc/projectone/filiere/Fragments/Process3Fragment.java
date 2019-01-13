package tn.igc.projectone.filiere.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Adapters.filiereCustomAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Process3Fragment extends Fragment {


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

		String test[] = {"Genie Logiciel", "Informatique Industruel"};
		ListAdapter adapter = new filiereCustomAdapter(getContext(), test);
		ListView listView = getView().findViewById(R.id.liste_filiere);
		listView.setAdapter(adapter);
	}
}
