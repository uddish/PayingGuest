package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Arrays;

import static com.google.android.gms.analytics.internal.zzy.s;

public class FilterActivity extends AppCompatActivity {



    String localityCheckCode="000";
    String collegeCheckCode="111";
    String rentCheckCode="222";
    private static final String TAG ="FilterTestCode" ;
    ListView detailsListView;
    ArrayList<String> detailsList;

    ListView rightList;

    Filter_POJO rent[];
    ArrayList<Filter_POJO> rentList;




    //   ArrayList<String> colleges;
    Filter_POJO colleges[];
    ArrayList<Filter_POJO> collegeList;

    Filter_POJO locality[];
    ArrayList<Filter_POJO> localityList;

    Button filterApplyBtn;

    CheckBox checkBox;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        detailsList = new ArrayList<String>();
        detailsList.add("Locality");
        detailsList.add("College");
        detailsList.add("Rent");


        /******************************************************/
        checkBox= (CheckBox) findViewById(R.id.filter_chkbox);

        /******************************************************/

        filterApplyBtn= (Button) findViewById(R.id.filter_apply_btn);

        toolbar = (Toolbar) findViewById(R.id.filter_toolbar);
        setSupportActionBar(toolbar);


        locality = new Filter_POJO[]
                {
                        new Filter_POJO("Anand Vihar"),
                        new Filter_POJO("Dilshad Garden"),
                        new Filter_POJO("Dwarka"),
                        new Filter_POJO("Janak Puri"),
                        new Filter_POJO("Jhilmil"),
                        new Filter_POJO("Lajpat Nagar"),
                        new Filter_POJO("Pitampura"),
                        new Filter_POJO("Rohini"),
                        new Filter_POJO("Rithala"),
                        new Filter_POJO("Vikas Puri"),
                        new Filter_POJO("Paschim Vihar")
                };

        localityList = new ArrayList<Filter_POJO>();
        localityList.addAll(Arrays.asList(locality));


/*************************************************************************/
        //Adding colleges
        colleges = new Filter_POJO[]
                {
                        new Filter_POJO("DTU"),
                        new Filter_POJO("NSIT"),
                        new Filter_POJO("IIT DELHI"),
                        new Filter_POJO("NIT DELHI"),
                        new Filter_POJO("MAIT"),
                        new Filter_POJO("BVP"),
                        new Filter_POJO("USIT"),
                        new Filter_POJO("IIIT DELHI"),
                        new Filter_POJO("JAYPEE NOIDA")
                };


        collegeList = new ArrayList<Filter_POJO>();
        collegeList.addAll(Arrays.asList(colleges));

/*
        String c[] = {"DTU","NSIT","IIT DELHI","IIIT DELHI","NIT DELHI","MAIT"};
        colleges = new ArrayList<String >();
        for(int i = 0; i < c.length; i++)
        {
            colleges.add(c[i]);
        }*/

/**************************************************************************/
        rent = new Filter_POJO[]
                {

                        new Filter_POJO("Below 5000"),
                        new Filter_POJO("5000-10000"),
                        new Filter_POJO("10000-15000"),
                        new Filter_POJO("More than 15000")
                };


        rentList = new ArrayList<Filter_POJO>();
        rentList.addAll(Arrays.asList(rent));






    /**********************************************************************/
        detailsListView = (ListView) findViewById(R.id.list_view_left);

        rightList = (ListView) findViewById(R.id.list_view_right);

        DetailsListAdapter listAdapter = new DetailsListAdapter(detailsList);
        detailsListView.setAdapter(listAdapter);

        detailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                if(position == 0)
                {
                    RightListDetailsAdapter adapter=new RightListDetailsAdapter(localityList);
                    rightList.setAdapter(adapter);
                }

               else if(position == 1)
                {
                    RightListDetailsAdapter adapter=new RightListDetailsAdapter(collegeList);
                    rightList.setAdapter(adapter);
                }

                else if(position==2)
                {
                    RightListDetailsAdapter adapter=new RightListDetailsAdapter(rentList);
                    rightList.setAdapter(adapter);
                }
            }
        });


        rightList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Filter_POJO ob= (Filter_POJO) adapterView.getItemAtPosition(position);
                Log.d(TAG,"tick"+ob.getName());
            }
        });



/*********************************************************************************************************/
    //   Filter Apply Button code:
        filterApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                ArrayList<String> filteredNameofLocality = new ArrayList<String>();
                ArrayList<Filter_POJO> checkedListLocality = new ArrayList<Filter_POJO>();
                checkedListLocality.addAll(Arrays.asList(locality));
                ArrayList<String> localityList = new ArrayList<String>(4);

                for(int i = 0; i < checkedListLocality.size(); i++)
                {
                    Filter_POJO ob = checkedListLocality.get(i);
                    if(ob.isChecked())
                    {
                        filteredNameofLocality.add(ob.getName());
                        Log.d(TAG,"checked:"+ob.getName()+"\n");
                        localityList.add(ob.getName());
                    }
                }




                ArrayList<String> filteredNameofColleges=new ArrayList<String>();
                ArrayList<Filter_POJO> checkedListColleges=new ArrayList<Filter_POJO>();
                checkedListColleges.addAll(Arrays.asList(colleges));
                ArrayList<String> collegeList = new ArrayList<String>(4);

                for(int i = 0; i < checkedListColleges.size(); i++)
                {
                    Filter_POJO ob = checkedListColleges.get(i);
                    if(ob.isChecked())
                    {
                        filteredNameofColleges.add(ob.getName());
                        Log.d(TAG,"checked:"+ob.getName()+"\n");
                        collegeList.add(ob.getName());
                    }
                }




                ArrayList<String> filteredNameofRent=new ArrayList<String>();
                ArrayList<Filter_POJO> checkedListRent=new ArrayList<Filter_POJO>();
                checkedListRent.addAll(Arrays.asList(rent));
                ArrayList<String> rentList = new ArrayList<String>();

                for(int i = 0; i < checkedListRent.size(); i++)
                {
                    Filter_POJO ob = checkedListRent.get(i);
                    if(ob.isChecked())
                    {
                        filteredNameofRent.add(ob.getName());
                        Log.d(TAG,"checked:"+ob.getName()+"\n");
                        rentList.add(ob.getName());
                    }
                }





                if(filteredNameofLocality.isEmpty() && filteredNameofColleges.isEmpty() && filteredNameofRent.isEmpty())
                {
                    Toast.makeText(FilterActivity.this,"NO field is selected",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Intent intentToFindPgActivity_Filter = new Intent(getApplicationContext(), FindPGActivity.class);
                    intentToFindPgActivity_Filter.putExtra("source","FilterActivity");

                    if (!filteredNameofLocality.isEmpty())
                    {
                        localityCheckCode="0";
                        Log.d(TAG,localityCheckCode);
                        intentToFindPgActivity_Filter.putExtra("localityCheckCode",localityCheckCode);
                        intentToFindPgActivity_Filter.putStringArrayListExtra("filteredLocalityList", filteredNameofLocality);
                    }

                    if (!filteredNameofColleges.isEmpty())
                    {

                        collegeCheckCode="1";
                        Log.d(TAG,collegeCheckCode);
                        intentToFindPgActivity_Filter.putExtra("collegeCheckCode",collegeCheckCode);
                        intentToFindPgActivity_Filter.putStringArrayListExtra("filteredCollegesList",filteredNameofColleges);
                    }


                    if (!filteredNameofRent.isEmpty())
                    {

                        rentCheckCode="2";
                        Log.d(TAG,rentCheckCode);
                        intentToFindPgActivity_Filter.putExtra("rentCheckCode",rentCheckCode);
                        intentToFindPgActivity_Filter.putStringArrayListExtra("filteredRentList",filteredNameofRent);
                    }

                    startActivity(intentToFindPgActivity_Filter);


                  /*  Intent intentToFindPgActivity_Filter = new Intent(getApplicationContext(),FindPGActivity.class);

                    intentToFindPgActivity_Filter.putExtra("source","FilterActivity");
                    intentToFindPgActivity_Filter.putStringArrayListExtra("filteredLocalityList",filteredNameofLocality);
                    intentToFindPgActivity_Filter.putStringArrayListExtra("filteredCollegesList",filteredNameofColleges);

                    startActivity(intentToFindPgActivity_Filter);*/
                }



            }
        });
    }

    /*********************************************************************************************************/


    /***********************************************************************************************/

    private class RightListDetailsAdapter extends BaseAdapter
    {
        private ArrayList<Filter_POJO> list;


        public RightListDetailsAdapter()
        {

        }

        public RightListDetailsAdapter(ArrayList<Filter_POJO> list)
        {
            this.list=list;
        }


        class ExpandViewHolder
        {
            CheckBox filterCheckBox;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater l = getLayoutInflater();
            ExpandViewHolder holder=new ExpandViewHolder();
            if (convertView == null) {
                convertView = l.inflate(R.layout.filter_right_list_layout, null);
                holder.filterCheckBox = (CheckBox) convertView.findViewById(R.id.filter_chkbox);
                convertView.setTag(holder);



                holder.filterCheckBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Filter_POJO ob = (Filter_POJO) cb.getTag();
//                        Toast.makeText(getApplicationContext(),
//                                "" + cb.getText() +
//                                        " is " + "selected",
//                                Toast.LENGTH_LONG).show();

                        ob.setChecked(cb.isChecked());
                    }
                });



            } else {
                holder = (ExpandViewHolder) convertView.getTag();
            }

            Filter_POJO ob=list.get(position);
            holder.filterCheckBox.setText(ob.getName());
            holder.filterCheckBox.setChecked(ob.isChecked());
            holder.filterCheckBox.setTag(ob);
            return convertView;
        }
    }



    /***********************************************************************/
    private class DetailsListAdapter extends BaseAdapter {

        private ArrayList<String> mDetails;

        public DetailsListAdapter(ArrayList<String> mDetails)
        {
            this.mDetails = mDetails;
        }


        class DetailsViewHolder
        {
            TextView filterOptions;
        }

        @Override
        public int getCount() {
            return mDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return mDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater li = getLayoutInflater();
            DetailsViewHolder detailsViewHolder = new DetailsViewHolder();

            if (convertView == null)
            {
                convertView = li.inflate(R.layout.filter_left_list_layout, null);
                detailsViewHolder.filterOptions = (TextView) convertView.findViewById(R.id.options_tv);
                convertView.setTag(detailsViewHolder);
            } else {
                detailsViewHolder = (DetailsViewHolder) convertView.getTag();
            }

            String ob= (String) getItem(position);
            detailsViewHolder.filterOptions.setText(ob);
            return convertView;
        }
    }
}
