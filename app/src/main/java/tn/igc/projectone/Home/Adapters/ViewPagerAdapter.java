package tn.igc.projectone.Home.Adapters;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import tn.igc.projectone.Home.Fragments.Fragment_sem1;
import tn.igc.projectone.Home.Fragments.Fragment_sem2;

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
                Fragment_sem1 tab1 = new Fragment_sem1();
                return  tab1;
            case 1 :
                Fragment_sem2 tab2 = new Fragment_sem2();
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
