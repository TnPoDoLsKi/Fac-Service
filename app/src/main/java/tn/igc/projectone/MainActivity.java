package tn.igc.projectone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import tn.igc.projectone.upload.fragments.uploadFragment;
import tn.igc.projectone.upload.fragments.uploadFragment1;
import tn.igc.projectone.upload.fragments.uploadFragment2;

// Hello from the other side


// From Wael



// From Achouri

//From Mariam
//from masmoudi









//from chaima
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new uploadFragment2();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag, fragment);
                fragmentTransaction.commit();
//hhhhhhhhhhhhhhhhhhhhhhhhhhhh

            }
        });
    }
}
