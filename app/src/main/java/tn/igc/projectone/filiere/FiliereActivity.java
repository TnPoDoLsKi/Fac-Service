package tn.igc.projectone.filiere;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Utils;

public class FiliereActivity extends AppCompatActivity {

	ProgressBar bar ;
	FragmentManager fragmentManager =  getSupportFragmentManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filiere);
		bar = findViewById(R.id.filiere_progressBar);
		Utils.nextProcess(fragmentManager,bar);
	}


	@Override
	public void onBackPressed() {
		if (Utils.processProgress != 1) {
			Utils.processProgress--;
			bar.setProgress(Utils.processProgress*35);
			super.onBackPressed();
		}else this.finish();
	}
}
