package tn.igc.projectone.filiere;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import tn.igc.projectone.*;

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

		for (int i = 0; i < layout.getChildCount(); i++) {
			button = (Button) layout.getChildAt(i);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Utils.nextProcess(fm,bar);
				}
			});
		}



	}





	//	// TODO: Rename method, update argument and hook method into UI event
//	public void onButtonPressed(Uri uri) {
//		if (mListener != null) {
//			mListener.onFragmentInteraction(uri);
//		}
//	}
//
//	@Override
//	public void onAttach(Context context) {
//		super.onAttach(context);
//		if (context instanceof OnFragmentInteractionListener) {
//			mListener = (OnFragmentInteractionListener) context;
//		} else {
//			throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
//		}
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		mListener = null;
//	}
//
//	/**
//	 * This interface must be implemented by activities that contain this
//	 * fragment to allow an interaction in this fragment to be communicated
//	 * to the activity and potentially other fragments contained in that
//	 * activity.
//	 * <p>
//	 * See the Android Training lesson <a href=
//	 * "http://developer.android.com/training/basics/fragments/communicating.html"
//	 * >Communicating with Other Fragments</a> for more information.
//	 */
//	public interface OnFragmentInteractionListener {
//		// TODO: Update argument type and name
//		void onFragmentInteraction(Uri uri);
//	}
}
