package tn.igc.projectone.filiere.Utils;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.SaveSharedPreference;
import tn.igc.projectone.filiere.Fragments.Process1Fragment;
import tn.igc.projectone.filiere.Fragments.Process2Fragment;
import tn.igc.projectone.filiere.Fragments.Process3Fragment;


public class Utils {

    public static int processProgress = 0;
    public static ArrayList<Data> selectedMajor;
    /**
     * @param fragmentManager : a Fragment Manager
     * @param bar             : a reference to the progressBar
     */


    //	this method calls the next fragment (process)
    // this version is only called the first time
    public static void nextProcess(FragmentManager fragmentManager, ProgressBar bar) {
        selectedMajor = new ArrayList<>();

        nextProcess(fragmentManager, bar, null);
    }

    //	Overload
    public static void nextProcess(FragmentManager fragmentManager, ProgressBar bar, Data toSend) {
        if (toSend != null) {
            selectedMajor.add(processProgress - 1, toSend);
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


    public static void nextProcess(FragmentManager fragmentManager, ProgressBar progressBar, Data dataToPass, Context context) {
        selectedMajor.add(processProgress - 1, dataToPass);
        Log.d("toPass", "nextProcess: " + dataToPass);
        SaveSharedPreference.setMajor(context, dataToPass.getId());
        SaveSharedPreference.setMajorName(context, dataToPass.getName());

        context.startActivity(new Intent(context, MainActivity.class));
    }
}
