package com.example.uddishverma.pg_app_beta;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by UddishVerma on 27/08/16.
 * This class fetches the images from firebase
 * and are set into the viewpager in blankFragment class
 */
public class CustomPagerAdapter extends PagerAdapter {

    public static final String TAG = "CustomPagerAdapter";

    Context mContext;
    LayoutInflater mLayoutInflater;
    Bundle mBundle;
    String imageURLOne;
    String imageURLTwo;
    String imageURLThree;
    String imageURLFour;

    String[] imgArray = {
            imageURLOne, imageURLTwo, imageURLThree, imageURLFour
    };

    public CustomPagerAdapter(Context ctx, Bundle b) {
        mContext = ctx;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBundle = b;
    }

    @Override
    public int getCount() {
        return imgArray.length;
    }

    public void setImageUrl()   {
        imageURLOne = mBundle.getString("IMAGE_ONE");
        imageURLTwo = mBundle.getString("IMAGE_TWO");
        imageURLThree = mBundle.getString("IMAGE_THREE");
        imageURLFour = mBundle.getString("IMAGE_FOUR");
        Log.d(TAG, "CustomPagerAdapter: IMAGE URL " + imageURLOne);
        Log.d(TAG, "setImageUrl: IMAGE URL 2 " + imageURLTwo);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout)(object));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = mLayoutInflater.inflate(R.layout.view_pager_imageview, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_image_view);

        setImageUrl();

        if(position == 0)
        Picasso.with(mContext).load(mBundle.getString("IMAGE_ONE")).fit().into(imageView);
        else if(position == 1)
            Picasso.with(mContext).load(mBundle.getString("IMAGE_TWO")).fit().into(imageView);
        else if(position == 2)
            Picasso.with(mContext).load(mBundle.getString("IMAGE_THREE")).fit().into(imageView);
        else if(position == 3)
            Picasso.with(mContext).load(mBundle.getString("IMAGE_FOUR")).fit().into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
