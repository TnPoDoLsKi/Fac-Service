package tn.igc.projectone.Home;

import android.app.Activity;
import android.app.Application;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import tn.igc.projectone.Home.Fragments.Fragment_sem1;
import tn.igc.projectone.Home.Fragments.Fragment_sem2;
import tn.igc.projectone.Home.Adapters.ViewPagerAdapter;
import tn.igc.projectone.R;

// Hello from the other side


// From Wael



// From Achouri

//From Mariam
//from masmoudi
// Hello from wassim ^_^
// Nouri says Hi 









//from chaima
public class MainActivity extends AppCompatActivity  {

    private TabLayout tabLayout;
    private ViewPager viewPager ;
    private ViewPagerAdapter adapter ;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setTitle("Matiéres");

        //Realm init
        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_main);
        /*Fragment fragment = new Matiere_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragment);
        fragmentTransaction.commit();*/


        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);
        ll = (LinearLayout) findViewById(R.id.lvv);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

       // adapter.AddFragment();
        adapter.AddFragment(new Fragment_sem1(),"Semestre 1");
        adapter.AddFragment(new Fragment_sem2(),"Semestre 2");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
/*class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);
    }
}*/
