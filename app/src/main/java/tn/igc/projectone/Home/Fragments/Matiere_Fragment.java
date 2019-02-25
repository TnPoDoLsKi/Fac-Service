package tn.igc.projectone.Home.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
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
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private LinearLayout ll;
    View v;

    BottomNavigationView bottomNavigationView;

    public Matiere_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.matiere_fragment, container, false);
        tabLayout = (TabLayout)v.findViewById(R.id.tablayout_id);
        viewPager = (ViewPager)v.findViewById(R.id.viewPager_id);
        setupViewPager(viewPager);
        ll = (LinearLayout)v.findViewById(R.id.lvv);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bottomNavigationView.getSelectedItemId()!=R.id.home_button)
        {
            bottomNavigationView.setSelectedItemId(R.id.home_button);
        }
    }

    public void setupViewPager(ViewPager viewPager) {


        adapter = new ViewPagerAdapter(getChildFragmentManager());

        // adapter.AddFragment();
        adapter.AddFragment(new Fragment_sem1(), "Semestre 1");
        adapter.AddFragment(new Fragment_sem2(), "Semestre 2");

        viewPager.setAdapter(adapter);

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bottomNavigationView =(BottomNavigationView) getActivity().findViewById(R.id.bottomBar);


    }
}