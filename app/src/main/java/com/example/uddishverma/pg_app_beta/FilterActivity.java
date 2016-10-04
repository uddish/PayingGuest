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

import java.util.ArrayList;
import java.util.Arrays;

public class FilterActivity extends AppCompatActivity {

    private static final String TAG ="FilterTestCode" ;
    ListView detailsListView;
    ArrayList<String> detailsList;

    ListView rightList;

    ArrayList<String> colleges;
    ArrayList<String> institutes;

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
        detailsList.add("Coaching Institute");
        detailsList.add("Rent");

        /******************************************************/
        checkBox= (CheckBox) findViewById(R.id.filter_chkbox);

        /******************************************************/

        filterApplyBtn= (Button) findViewById(R.id.filter_apply_btn);

        toolbar = (Toolbar) findViewById(R.id.filter_toolbar);
        setSupportActionBar(toolbar);


        locality = new Filter_POJO[]
                {
                        new Filter_POJO("Dwarka"),
                        new Filter_POJO("Pitampura"),
                        new Filter_POJO("Rohini"),
                        new Filter_POJO("Rithala"),
                        new Filter_POJO("Dilshad Garden"),
                        new Filter_POJO("Vikas Puri"),
                        new Filter_POJO("Janak Puri"),
                        new Filter_POJO("Jhilmil"),
                        new Filter_POJO("Pascim Vihar")
                };

        localityList = new ArrayList<Filter_POJO>();
        localityList.addAll(Arrays.asList(locality));




     /* String l[]={"Dwarka","Pitampura","Rohini","Rithala","Dilshad Garden","Vikas Puri","Jhilmil","Paschim Vihar"};

        locality=new ArrayList<String>();
        for(int i = 0; i < l.length; i++)
        {
            locality.add(l[i]);
        }
*/


        /*************************************************************************/
        //Adding colleges

        String c[] = {"DTU","NSIT","IIT DELHI","IIIT DELHI","NIT DELHI","MAIT"};
        colleges = new ArrayList<String >();
        for(int i = 0; i < c.length; i++)
        {
            colleges.add(c[i]);
        }

/***********************************************************************************/


        detailsListView = (ListView) findViewById(R.id.list_view_left);

        rightList= (ListView) findViewById(R.id.list_view_right);

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

               /* else if(position == 1)
                {
                    RightListDetailsAdapter adapter=new RightListDetailsAdapter(colleges);
                    rightList.setAdapter(adapter);
                }*/
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




        filterApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ArrayList<Filter_POJO> checkedListLocality=new ArrayList<Filter_POJO>();
                checkedListLocality.addAll(Arrays.asList(locality));
                ArrayList<String> localityList = new ArrayList<String>(4);

                for(int i = 0; i < checkedListLocality.size(); i++)
                {
                    Filter_POJO ob = checkedListLocality.get(i);
                    if(ob.isChecked())
                    {
                        Log.d(TAG,"checked:"+ob.getName()+"\n");
                        localityList.add(ob.getName());
                    }
                }

               Intent i = new Intent(getApplicationContext(), FindPGActivity.class);
                i.putStringArrayListExtra("list", localityList);
                i.putExtra("source", "filter");
                startActivity(i);
            }
        });

    }

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
                        Toast.makeText(getApplicationContext(),
                                "" + cb.getText() +
                                        " is " + "selected",
                                Toast.LENGTH_LONG).show();

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
