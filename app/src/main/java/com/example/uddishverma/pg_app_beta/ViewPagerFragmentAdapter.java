package com.example.uddishverma.pg_app_beta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by UddishVerma on 21/08/16.
 */

/**
 * This class contains the data to be passed to the ViewPagerFragment class
 * to attach them to the view pager
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    public static final String TAG = "ViewPagerFragmentAdapter";

    static String imageUrlOne, imageUrlTwo, imageUrlThree, imageUrlFour;

    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment frag;
        switch (position)   {
            case 0:
//                Log.d(TAG, "getItem: Image URL 1 " + imageUrlOne);
                frag = viewPagerFragment.newInstance(imageUrlOne, "one");
                break;
            case 1:
//                Log.d(TAG, "getItem: Image URL 2 " + imageUrlTwo);
                frag = viewPagerFragment.newInstance(imageUrlTwo, "two");
                break;
            case 2:
//                Log.d(TAG, "getItem: Image URL 3 " + imageUrlThree);
                frag = viewPagerFragment.newInstance(imageUrlThree, "three");
                break;
            case 3:default:
//                Log.d(TAG, "getItem: Image URL 4 " + imageUrlFour);
                frag = viewPagerFragment.newInstance(imageUrlFour, "four");
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public static void getBundle(Bundle b)   {
        imageUrlOne = b.getString("IMAGE_ONE");
        imageUrlTwo = b.getString("IMAGE_TWO");
//        Log.d(TAG, "getBundle: IMAGE URL 1 BY BUNDLE " + b.getString("IMAGE_THREE"));
//        Log.d(TAG, "getBundle: IMAGE URL 2 BY BUNDLE " + b.getString("IMAGE_TWO"));
//        Log.d(TAG, "getBundle: IMAGE URL 3 BY BUNDLE " + b.getString("IMAGE_ONE"));
        imageUrlThree = b.getString("IMAGE_THREE");
        imageUrlFour = b.getString("IMAGE_FOUR");
        Log.d(TAG, "getItem: Image URL 1" + imageUrlOne);
        Log.d(TAG, "getItem: Image URL 2" + imageUrlTwo);
        Log.d(TAG, "getItem: Image URL 3" + imageUrlThree);
        Log.d(TAG, "getItem: Image URL 4" + imageUrlFour);
    }
}
