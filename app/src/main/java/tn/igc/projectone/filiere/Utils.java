package tn.igc.projectone.filiere;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ProgressBar;

import tn.igc.projectone.R;


class Utils {

	static int processProgress = 0;
	static String[] filere;

	/**
	 * @param fragmentManager : a Fragment Manager
	 * @param bar             : a reference to the progressBar
	 */
	//	this method calls the next fragment (process)
	// this version is only called the first time
	static void nextProcess(FragmentManager fragmentManager, ProgressBar bar) {
		filere = new String[3];
		nextProcess(fragmentManager, bar, null);
	}

	//	Overload
	static void nextProcess(FragmentManager fragmentManager, ProgressBar bar, String toSend) {
		if (toSend != null) filere[processProgress - 1] = toSend;
		if (fragmentManager == null) {
			Log.d("CLICK", filere[0] + "|" + filere[1] + "|" + filere[2] + "|");
			// here pass to next activity
			return;
		}


		Fragment fragment;
		switch (++processProgress){
			case 1: fragment = new Process1Fragment(); break;
			case 2: fragment = new Process2Fragment(); break;
			case 3: fragment = new Process3Fragment(); break;
			default: return;
		}

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.filiere_frag_container,fragment,"process");
		// the first fragment is not part of the stack
		if (!(fragment instanceof Process1Fragment)) {
			fragmentTransaction.addToBackStack("filiereStack");
		}
		fragmentTransaction.commit();
		bar.setProgress(processProgress*35);
	}


}
