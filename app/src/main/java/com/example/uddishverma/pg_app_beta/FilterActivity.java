package com.example.uddishverma.pg_app_beta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    ListView detailsListView;
    ArrayList<String> detailsList;

    ListView rightList;
    ArrayList<String> locality;
    ArrayList<String> colleges;
    ArrayList<String> institutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        detailsList=new ArrayList<String>();
        detailsList.add("Locality");
        detailsList.add("College");
        detailsList.add("Coaching Institute");
        detailsList.add("Rent");


        String l[]={"Dwarka","Pitampura","Rohini","Rithala","Dilshad Garden","Vikas Puri","Jhilmil","Paschim Vihar"};

        locality=new ArrayList<String>();
        for(int i=0;i<l.length;i++)
        {
            locality.add(l[i]);
        }

        String c[]={"DTU","NSIT","IIT DELHI","IIIT DELHI","NIT DELHI","MAIT"};
        colleges=new ArrayList<String >();
        for(int i=0;i<c.length;i++)
        {
            colleges.add(c[i]);
        }


        detailsListView = (ListView) findViewById(R.id.list_view_left);

        rightList= (ListView) findViewById(R.id.list_view_right);

        DetailsListAdapter listAdapter = new DetailsListAdapter(detailsList);
        detailsListView.setAdapter(listAdapter);

        detailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                if(position==0)
                {
                    RightListDetailsAdapter adapter=new RightListDetailsAdapter(locality);
                    rightList.setAdapter(adapter);
                }

                else if(position==1)
                {
                    RightListDetailsAdapter adapter=new RightListDetailsAdapter(colleges);
                    rightList.setAdapter(adapter);
                }
            }
        });

    }

    /***********************************************************************************************/

    private class RightListDetailsAdapter extends BaseAdapter
    {
        private ArrayList<String> list;

        public RightListDetailsAdapter(ArrayList<String> list)
        {
            this.list=list;
        }


        class ExpandViewHolder
        {
            RadioButton filterExpandTextView;
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
                holder.filterExpandTextView = (RadioButton) convertView.findViewById(R.id.radioButton);
                convertView.setTag(holder);
            } else {
                holder = (ExpandViewHolder) convertView.getTag();
            }

            String ob= (String) getItem(position);
            holder.filterExpandTextView.setText(ob);
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
