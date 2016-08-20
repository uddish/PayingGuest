package com.example.uddishverma.pg_app_beta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by UddishVerma on 21/08/16.
 */

/**
 * This class contains the data to be passed to the ViewPagerFragment class
 * to attach them to the view pager
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag;
        switch (position)   {
            case 0:
                frag = viewPagerFragment.newInstance("frag", "one");
                break;
            case 1:
                frag = viewPagerFragment.newInstance("frag", "two");
                break;
            case 2:default:
                frag = viewPagerFragment.newInstance("frag", "three");
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
