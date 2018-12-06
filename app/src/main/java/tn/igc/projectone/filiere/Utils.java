package tn.igc.projectone.filiere;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ProgressBar;

import tn.igc.projectone.R;

import static android.content.ContentValues.TAG;


class Utils {
	static int processProgress = 0;
	//	this method calls the next fragment (process)
	static void nextProcess(FragmentManager fragmentManager,ProgressBar bar){
		processProgress++;

		bar.setProgress(processProgress*35);
		Fragment fragment;
		switch (processProgress){
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
	}


}
