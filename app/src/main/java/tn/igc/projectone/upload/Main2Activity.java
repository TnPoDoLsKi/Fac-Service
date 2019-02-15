package tn.igc.projectone.upload;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.fxn.pix.Pix;

import tn.igc.projectone.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Pix.start(Main2Activity.this,200);

    }
}
