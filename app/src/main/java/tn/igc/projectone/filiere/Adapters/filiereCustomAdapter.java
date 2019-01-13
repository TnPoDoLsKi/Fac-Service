package tn.igc.projectone.filiere.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import tn.igc.projectone.R;
import tn.igc.projectone.filiere.Utils.Utils;

public class filiereCustomAdapter extends ArrayAdapter<String> {
	public filiereCustomAdapter(Context context, String[] filiere) {
		super(context, R.layout.button_filiere_process3, filiere);
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {

		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View filiereView = layoutInflater.inflate(R.layout.button_filiere_process3, parent, false);

		Button button = filiereView.findViewById(R.id.filiere_btn);
		button.setText(getItem(position));

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String filiere = (((Button) view).getText().toString());
				Utils.nextProcess(null, null, filiere);

			}
		});

		return filiereView;
	}
}
