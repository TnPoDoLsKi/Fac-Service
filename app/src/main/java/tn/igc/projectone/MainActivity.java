package tn.igc.projectone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tn.igc.projectone.filiere.FiliereActivity;


// Hello from the other side


// From Wael



// From Achouri

//From Mariam
//from masmoudi
// Hello from wassim ^_^
// Nouri says Hi

//from chaima
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(MainActivity.this,FiliereActivity.class));
    }
}
