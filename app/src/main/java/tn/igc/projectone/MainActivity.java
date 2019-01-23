package tn.igc.projectone;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import tn.igc.projectone.search.fragment.Search;

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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) navigationItemReselectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null ;
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
                    return  true ;
                }


    };
}
