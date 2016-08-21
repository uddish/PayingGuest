package com.example.uddishverma.pg_app_beta;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewPagerFragment extends Fragment {

    ImageView imageView;
    Context ctx;

    public static final String TAG = "viewPagerFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public viewPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static viewPagerFragment newInstance(String param1, String param2) {
        viewPagerFragment fragment = new viewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        findView(rootView);
        Bundle b = getArguments();
        //Calling the getBundle() function in Adapter Class and passing the bundle there
        ViewPagerFragmentAdapter.getBundle(b);
        setDetails(b);
        return rootView;
    }

    public void findView(View v)  {
        imageView = (ImageView) v.findViewById(R.id.fragment_image_view);
    }

    public void setDetails(Bundle b)    {
//        Log.d(TAG, "setDetails: Image Url : " + b.getString("IMAGE_ONE"));
//        Log.d(TAG, "setDetails: Image Url : " + b.getString("IMAGE_TWO"));
//        Log.d(TAG, "setDetails: Image Url : " + b.getString("IMAGE_THREE"));
        Log.d(TAG, "setDetails: IMAGE URL!!!!!!! " + mParam1);
//        Picasso.with(ctx).load(b.getString("IMAGE_ONE")).resize(900, 600).centerCrop().into(imageView);
        Picasso.with(getContext()).load(mParam1).resize(950, 500).centerCrop().into(imageView);
//        Picasso.with(getContext()).load(Uri.parse(b.getString("IMAGE_ONE"))).resize(600, 600).centerCrop().into(imageView);
    }

}
