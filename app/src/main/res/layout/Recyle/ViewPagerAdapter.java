package com.example.lenovo.project_one.Recyle;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();
    TabLayout tabLayout ;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int i) {
        switch (i){

            case 0 :
                com.example.lenovo.project_one.Recyle.Fragment_sem1 tab1 = new com.example.lenovo.project_one.Recyle.Fragment_sem1();
                return  tab1;
            case 1 :
                com.example.lenovo.project_one.Recyle.Fragment_sem2 tab2 = new com.example.lenovo.project_one.Recyle.Fragment_sem2();
                return tab2;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return lstTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitles.get(position);
    }

    public void AddFragment(Fragment fragment , String title)
    {
        lstFragment.add(fragment);
        lstTitles.add(title);

    }
}
