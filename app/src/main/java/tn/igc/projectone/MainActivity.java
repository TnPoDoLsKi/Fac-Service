    package tn.igc.projectone;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import tn.igc.projectone.search.fragment.Search;


public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        fragment = new AddFragment();
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
                            selectedFragment = new AddFragment();
                            break;
                        case R.id.add_button:
                            selectedFragment = new AddFragment();
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


}
