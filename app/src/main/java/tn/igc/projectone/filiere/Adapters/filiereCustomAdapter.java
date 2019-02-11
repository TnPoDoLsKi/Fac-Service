package tn.igc.projectone.filiere.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Utils;

public class filiereCustomAdapter extends ArrayAdapter<String> {
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;

    /**
     * @param context :context
     * @param filiere : strings of the list
     * @param fm      : reference to the fragmentManager
     * @param pb      : reference to the progressBar
     */
    public filiereCustomAdapter(Context context, String[] filiere, FragmentManager fm, ProgressBar pb) {
		super(context, R.layout.button_filiere_process3, filiere);
        fragmentManager = fm;
        progressBar = pb;
    }

    @NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {

		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View filiereView = layoutInflater.inflate(R.layout.button_filiere_process3, parent, false);

		Button button = filiereView.findViewById(R.id.filiere_btn);
		button.setText(getItem(position));
        //listener to pass to the next fragment/activity
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String filiere = (((Button) view).getText().toString());
                Utils.nextProcess(fragmentManager, progressBar, filiere);

			}
		});

		return filiereView;
	}
}
