package tn.igc.projectone.Home.Fragments;

import android.app.ActionBar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import tn.igc.projectone.Home.Adapters.ViewPagerAdapter;
import tn.igc.projectone.R;

public class Matiere_Fragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager ;
    private ViewPagerAdapter adapter ;
    private LinearLayout ll;
    View v;

    public Matiere_Fragment() {
    }

   /* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.matiere_fragment, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager_id);
        ll = (LinearLayout) v.findViewById(R.id.lvv);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new ViewPagerAdapter(getFragmentManager());

        adapter.AddFragment(new Fragment_sem1(),"Semestre 1");
        adapter.AddFragment(new Fragment_sem2(),"Semestre 2");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);




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

    }*/

}