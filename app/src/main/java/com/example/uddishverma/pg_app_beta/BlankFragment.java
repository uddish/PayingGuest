package com.example.uddishverma.pg_app_beta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG ="tv_test" ;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_blank, container, false);

        findView(v);

        Bundle b=getArguments();
        setDetails(b);


       /* TextView tv= (TextView) v.findViewById(R.id.tv_frag);
        tv.setText(mParam1+":"+b.get("PG ID"));*/
        return v;
    }


    TextView pg_name,pg_location,owners_name,contact_no,email_id,wifi,ac,parking,tv,lunch,dinner,breakfast,ro_water,hot_water,security,refrigerator,min_rent,deposit;

    private void findView(View v)
    {
         pg_name= (TextView) v.findViewById(R.id.pg_name);
        owners_name= (TextView) v.findViewById(R.id.owners_name);
        contact_no= (TextView) v.findViewById(R.id.contact);
        email_id= (TextView) v.findViewById(R.id.email);
        wifi= (TextView) v.findViewById(R.id.wifi);
        ac= (TextView) v.findViewById(R.id.ac);
        parking= (TextView) v.findViewById(R.id.parking);
        tv= (TextView) v.findViewById(R.id.tv);
        lunch= (TextView) v.findViewById(R.id.lunch);
        dinner= (TextView) v.findViewById(R.id.dinner);
        breakfast= (TextView) v.findViewById(R.id.breakast);
        ro_water= (TextView) v.findViewById(R.id.ro);
        hot_water= (TextView) v.findViewById(R.id.hot_water);
        security= (TextView) v.findViewById(R.id.security);
        refrigerator= (TextView) v.findViewById(R.id.refrigerator);
        min_rent= (TextView) v.findViewById(R.id.min_rent);
        deposit= (TextView) v.findViewById(R.id.deposit);

    }

    private void setDetails(Bundle b)
    {
        pg_name.setText(b.getString("PG Name"));
        owners_name.setText( b.getString("OWNER NAME"));
        contact_no.setText(Double.toString(b.getDouble("CONTACT NO")));
        email_id.setText(b.getString("EMAIL"));

        min_rent.setText(Double.toString(b.getDouble("RENT")));
        deposit.setText(Double.toString(b.getDouble("DEPOSIT")));

        if(b.getBoolean("WIFI")==true)
            wifi.setText("YES");
        else wifi.setText("NO");

        if(b.getBoolean("AC")==true)
            ac.setText("YES");
        else ac.setText("NO");

        if(b.getBoolean("REFRIGERATOR")==true)
            refrigerator.setText("YES");
        else refrigerator.setText("NO");

        if(b.getBoolean("PARKING")==true)
            parking.setText("YES");
        else parking.setText("NO");

        if(b.getBoolean("TV")==true)
            tv.setText("YES");
        else tv.setText("NO");

        if(b.getBoolean("LUNCH")==true)
            lunch.setText("YES");
        else lunch.setText("NO");

        if(b.getBoolean("DINNER")==true)
            dinner.setText("YES");
        else dinner.setText("NO");

        if(b.getBoolean("BREAKFAST")==true)
            breakfast.setText("YES");
        else breakfast.setText("NO");

        if(b.getBoolean("RO")==true)
            ro_water.setText("YES");
        else ro_water.setText("NO");

        if(b.getBoolean("HOT WATER")==true)
            hot_water.setText("YES");
        else hot_water.setText("NO");

        if(b.getBoolean("SECURITY")==true)
            security.setText("YES");
        else security.setText("NO");




    }










    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}