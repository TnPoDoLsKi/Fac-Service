package tn.igc.projectone.Home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;


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
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);
        ll = (LinearLayout) findViewById(R.id.lvv);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

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
