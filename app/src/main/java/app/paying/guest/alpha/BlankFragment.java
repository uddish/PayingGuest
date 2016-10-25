package app.paying.guest.alpha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.example.uddishverma.pg_app_beta.R;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

//TODO fix maps

public class BlankFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "tv_test";

    static int REQUEST_PERM_CALL = 445;

    static String address = null;
    String pgName;

    CustomPagerAdapter customPagerAdapter;

    boolean isOpen = false;

    //For runtime permissions
    String reqCallPerm[] = new String[]{
            android.Manifest.permission.CALL_PHONE,
    };
    String reqLocationPerm[] = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    Animation fabOpen, fabClose, fabClockwise, fabAnticlockwise;

    SweetAlertDialog pDialog;

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

        /**********************************Setting the address in the String*********************************************/
        address = b.getString("ADDRESS") + " " + b.getString("LOCALITY") + " " + b.getString("STATE");
        Log.d(TAG, "onCreateView: ADDRESS " + address);


        //Adding call intent on the floating action button
        fabcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCallPerm()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + String.valueOf((int) b.getDouble("CONTACT NO"))));
                    startActivity(intent);
                } else {
                    askCallPerm();
                }
            }
        });

        //Starting Maps Activity
        fabloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasLocationPerm()) {

                    pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Please Wait");
                    pDialog.show();
                    Log.d(TAG, "onCreateView: ADDRESS " + address);

                    try {
                        geoLocate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    askLocationPerm();
                }
            }
//
        });

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOpen) {

                    fabloc.startAnimation(fabClose);
                    fabcall.startAnimation(fabClose);
                    fabPlus.startAnimation(fabAnticlockwise);
                    fabloc.setClickable(false);
                    fabcall.setClickable(false);
                    isOpen = false;
                } else {

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
    TextView extraFeatures, nearbyIns, location;

    ImageView wifi, ac, parking, tv, lunch, dinner, breakfast, ro_water, hot_water, security, refrigerator;

    FloatingActionButton fabPlus, fabloc, fabcall;

    ViewPager vPager;

    private void findView(View v) {

        vPager = (ViewPager) v.findViewById(R.id.img_view_pager);

        fabPlus = (FloatingActionButton) v.findViewById(R.id.fab_btn_add);
        fabloc = (FloatingActionButton) v.findViewById(R.id.fab_btn_loc);
        fabcall = (FloatingActionButton) v.findViewById(R.id.fab_btn_call);

        pg_name = (TextView) v.findViewById(R.id.pg_name);
        location = (TextView) v.findViewById(R.id.loc);
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
        extraFeatures = (TextView) v.findViewById(R.id.extra_tv);
        nearbyIns = (TextView) v.findViewById(R.id.nearby_ins);

    }

    private void setDetails(Bundle b) {
        pg_name.setText(b.getString("PG Name"));
        pgName = (b.getString("PG Name"));
        owners_name.setText(b.getString("OWNER NAME"));
        contact_no.setText(String.valueOf((int) b.getDouble("CONTACT NO")));
        email_id.setText(b.getString("EMAIL"));
        min_rent.setText(String.valueOf((int) b.getDouble("RENT")));
        deposit.setText(String.valueOf((int) b.getDouble("DEPOSIT")));
        location.setText(b.getString("ADDRESS") + " " + b.getString("LOCALITY") + ", " + " " + b.getString("STATE")
                + "-" + String.valueOf((int) b.getDouble("PINCODE")));
        nearbyIns.setText(b.getString("INSTITUTE") + " (Nearby Institute)");
        if (!b.getString("EXTRAFEATURES").equals("")) {
            extraFeatures.setText(b.getString("EXTRAFEATURES"));
        } else {
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


    public void geoLocate() throws IOException {

        Geocoder gc = new Geocoder(getActivity());
        List<Address> list = gc.getFromLocationName(address, 1);

        if(list.size() != 0) {
            Address add = list.get(0);

            double lat = add.getLatitude();
            double lon = add.getLongitude();

            Intent i = new Intent(getActivity(), MapsActivity.class);
            i.putExtra("latitude", lat);
            i.putExtra("longitude", lon);
            i.putExtra("pgname", pgName);
            pDialog.dismiss();
            startActivity(i);
        }
        else {
            pDialog.dismissWithAnimation();
            Toast.makeText(getActivity(), "The Map is Not Available Right Now!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERM_CALL) {

            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: CALL PERMISSION GRANTED");
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean hasCallPerm() {
        return (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);
    }

    private void askCallPerm() {
        ActivityCompat.requestPermissions(getActivity(), reqCallPerm, REQUEST_PERM_CALL);
    }

    private boolean hasLocationPerm() {
        return (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void askLocationPerm() {
        ActivityCompat.requestPermissions(getActivity(), reqLocationPerm, REQUEST_PERM_CALL);
    }


}