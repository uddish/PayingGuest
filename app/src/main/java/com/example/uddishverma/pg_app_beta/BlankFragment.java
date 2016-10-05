package com.example.uddishverma.pg_app_beta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "tv_test";

    CustomPagerAdapter customPagerAdapter;

    boolean isOpen = false;

    Animation fabOpen, fabClose, fabClockwise, fabAnticlockwise;

    Context ctx;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: OnCreate Of BlankFragment called ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        fabOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_clockwise);
        fabAnticlockwise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anticlockwise);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        findView(v);
//
        final Bundle b = getArguments();
        setDetails(b);

        //Adding call intent on the floating action button
        fabcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + String.valueOf((int) b.getDouble("CONTACT NO"))));
                startActivity(intent);
            }
        });

        fabloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Location Activity Updating Soon", Toast.LENGTH_SHORT).show();
            }
        });

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen)  {

                    fabloc.startAnimation(fabClose);
                    fabcall.startAnimation(fabClose);
                    fabPlus.startAnimation(fabAnticlockwise);
                    fabloc.setClickable(false);
                    fabcall.setClickable(false);
                    isOpen = false;
                }

                else    {

                    fabloc.startAnimation(fabOpen);
                    fabcall.startAnimation(fabOpen);
                    fabPlus.startAnimation(fabClockwise);
                    fabloc.setClickable(true);
                    fabcall.setClickable(true);
                    isOpen = true;

                }

            }
        });

        customPagerAdapter = new CustomPagerAdapter(getActivity(), b);
        vPager.setAdapter(customPagerAdapter);
        vPager.setPageTransformer(true, new ZoomOutPageTransformer());

        return v;
    }


    TextView pg_name, owners_name, contact_no, email_id, min_rent, deposit;
    TextView pgAddress, city, state, pinCode, extraFeatures, locality, nearbyIns;

    ImageView wifi, ac, parking, tv, lunch, dinner, breakfast,ro_water, hot_water, security, refrigerator;

    FloatingActionButton fabPlus, fabloc, fabcall;

    ViewPager vPager;

    private void findView(View v) {

        vPager = (ViewPager) v.findViewById(R.id.img_view_pager);
//        callFab = (FloatingActionButton) v.findViewById(R.id.call_fab);

        /**
         * TEST FUNCTIONS
         */
        fabPlus = (FloatingActionButton) v.findViewById(R.id.fab_btn_add);
        fabloc = (FloatingActionButton) v.findViewById(R.id.fab_btn_loc);
        fabcall = (FloatingActionButton) v.findViewById(R.id.fab_btn_call);

        pg_name = (TextView) v.findViewById(R.id.pg_name);
        owners_name = (TextView) v.findViewById(R.id.owners_name);
        contact_no = (TextView) v.findViewById(R.id.contact);
        email_id = (TextView) v.findViewById(R.id.email);
        wifi = (ImageView) v.findViewById(R.id.wifi);
        ac = (ImageView) v.findViewById(R.id.ac);
        parking = (ImageView) v.findViewById(R.id.parking);
        tv = (ImageView) v.findViewById(R.id.tv);
        lunch = (ImageView) v.findViewById(R.id.lunch);
        dinner = (ImageView) v.findViewById(R.id.dinner);
        breakfast = (ImageView) v.findViewById(R.id.breakfast);
        ro_water = (ImageView) v.findViewById(R.id.ro);
        hot_water = (ImageView) v.findViewById(R.id.hot_water);
        security = (ImageView) v.findViewById(R.id.security);
        refrigerator = (ImageView) v.findViewById(R.id.refrigerator);
        min_rent = (TextView) v.findViewById(R.id.min_rent);
        deposit = (TextView) v.findViewById(R.id.deposit);
        pgAddress = (TextView) v.findViewById(R.id.address_one);
        city = (TextView) v.findViewById(R.id.city);
        state = (TextView) v.findViewById(R.id.state);
        pinCode = (TextView) v.findViewById(R.id.pinCode);
        extraFeatures = (TextView) v.findViewById(R.id.extra_tv);
        locality = (TextView) v.findViewById(R.id.locality);
        nearbyIns = (TextView) v.findViewById(R.id.nearby_ins);

    }

    private void setDetails(Bundle b) {
        pg_name.setText(b.getString("PG Name"));
        owners_name.setText(b.getString("OWNER NAME"));
        contact_no.setText(String.valueOf((int) b.getDouble("CONTACT NO")));
        email_id.setText(b.getString("EMAIL"));
        min_rent.setText(String.valueOf((int) b.getDouble("RENT")));
        deposit.setText(String.valueOf((int) b.getDouble("DEPOSIT")));
        pgAddress.setText(b.getString("ADDRESS"));
        city.setText(b.getString("CITY"));
        state.setText(b.getString("STATE"));
        pinCode.setText(String.valueOf((int) b.getDouble("PINCODE")));
        locality.setText(b.getString("LOCALITY"));
        nearbyIns.setText(b.getString("INSTITUTE"));
        if(!b.getString("EXTRAFEATURES").equals("")) {
            extraFeatures.setText(b.getString("EXTRAFEATURES"));
        }
        else {
            extraFeatures.setText("NO EXTRA FEATURES SPECIFIED!");
        }

        if (b.getBoolean("WIFI") == true)
            wifi.setImageResource(R.drawable.ic_signal_wifi_4_bar_grey_700_48dp);
        else
            wifi.setImageResource(R.drawable.ic_signal_wifi_off_grey_700_48dp);

        if (b.getBoolean("AC") == true)
            ac.setImageResource(R.drawable.ac);
        else
            ac.setImageResource(R.drawable.ac_no);

        if (b.getBoolean("REFRIGERATOR") == true)
            refrigerator.setImageResource(R.drawable.ic_kitchen_grey_700_48dp);
        else
            refrigerator.setImageResource(R.drawable.fridge_no);

        if (b.getBoolean("PARKING") == true)
            parking.setImageResource(R.drawable.ic_local_parking_grey_700_48dp);
        else
            parking.setImageResource(R.drawable.parking_no);


        if (b.getBoolean("TV") == true)
            tv.setImageResource(R.drawable.ic_tv_grey_700_48dp);

        else
            tv.setImageResource(R.drawable.tv_no);


        if (b.getBoolean("LUNCH") == true)
            lunch.setImageResource(R.drawable.ic_room_service_grey_700_48dp);

        else
            lunch.setImageResource(R.drawable.lunch_no);

        if (b.getBoolean("DINNER") == true)
            dinner.setImageResource(R.drawable.ic_restaurant_grey_700_48dp);
        else
            dinner.setImageResource(R.drawable.dinner_no);

        if (b.getBoolean("BREAKFAST") == true)
            breakfast.setImageResource(R.drawable.ic_free_breakfast_grey_700_48dp);
        else
            breakfast.setImageResource(R.drawable.breakfast_no);

        if (b.getBoolean("RO") == true)
            ro_water.setImageResource(R.drawable.ic_opacity_grey_700_48dp);
        else
            ro_water.setImageResource(R.drawable.ro_no);

        if (b.getBoolean("HOT WATER") == true)
            hot_water.setImageResource(R.drawable.shower);
        else
            hot_water.setImageResource(R.drawable.hot_water_no);

        if (b.getBoolean("SECURITY") == true)
            security.setImageResource(R.drawable.ic_security_grey_700_48dp);
        else
            security.setImageResource(R.drawable.security_no);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: CALLED");
        super.onAttach(context);

    }
}