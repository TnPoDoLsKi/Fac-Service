package tn.igc.projectone.filiere;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import tn.igc.projectone.R;

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
