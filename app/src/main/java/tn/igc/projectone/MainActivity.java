package tn.igc.projectone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import tn.igc.projectone.Home.Fragments.Matiere_Fragment;
import tn.igc.projectone.search.fragment.Search;
import tn.igc.projectone.upload.fragments.NewFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        Fragment fragment = new Matiere_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null).replace(R.id.container, fragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) navigationItemReselectedListener);
    }
     BottomNavigationView.OnNavigationItemSelectedListener navigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = new Search() ;
                    switch (menuItem.getItemId()){

                        case R.id.home_button:
                            selectedFragment = new Matiere_Fragment();
                            break;
                        case R.id.add_button:
                            selectedFragment = new NewFragment();
                            break;
                        case R.id.parametre_button:
                            selectedFragment = new AddFragment();
                            break;
                        case R.id.search_button:
                            selectedFragment = new Search();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container,selectedFragment).commit();
                    return  true ;
                }


    };
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
//
}
