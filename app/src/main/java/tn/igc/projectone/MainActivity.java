package tn.igc.projectone;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tn.igc.projectone.Home.HeadlinesFragment;
import tn.igc.projectone.Home.Home_Fragment;

// Hello from the other side


// From Wael



// From Achouri

//From Mariam
//from masmoudi
// Hello from wassim ^_^
// Nouri says Hi 









//from chaima
public class MainActivity extends AppCompatActivity implements HeadlinesFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Home_Fragment home_fragment = new Home_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.home_layout,home_fragment , home_fragment.getTag())
                .commit();
    }

    @Override
    public void onArticleSelected(int position) {

    }
}
