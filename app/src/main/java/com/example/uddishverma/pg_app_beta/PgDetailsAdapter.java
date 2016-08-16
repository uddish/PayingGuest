package com.example.uddishverma.pg_app_beta;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Naman on 07-08-2016.
 */
//************************************Class to join card view with recycler view****************************************************
public class PgDetailsAdapter extends RecyclerView.Adapter<PgDetailsAdapter.DetailsViewHolder>
{

    public static final String TAG = "PgDetailsAdapter";

    ArrayList<PgDetails_POJO.PgDetails> pgObject = new ArrayList<>();

    public PgDetailsAdapter(ArrayList<PgDetails_POJO.PgDetails> pgObject) {
        this.pgObject = pgObject;
    }


    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);

        DetailsViewHolder detailsViewHolder = new DetailsViewHolder(view);

        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position)
    {

        PgDetails_POJO.PgDetails details = pgObject.get(position);

//        holder.pg_img.setImageResource(details.getImageId());
        holder.name_tv.setText(details.getPgName());
        holder.address_tv.setText(details.getAddressOne());
        holder.address_tv.setSelected(true);

        holder.state_tv.setText(details.getState());
        String rent = String.valueOf((int)details.getRent());
        holder.rent_tv.setText("RENT : " + rent);

        if(details.getWifi()==true)
            holder.ic_wifi.setImageResource(R.drawable.ic_wifi_blue_grey_700_24dp);
        else
            holder.ic_wifi.setImageResource(R.drawable.ic_signal_wifi_off_grey_700_24dp);

        if(details.getBreakfast()==true)
            holder.ic_lunch.setImageResource(R.drawable.ic_restaurant_grey_700_24dp);
    }

    @Override
    public int getItemCount()
    {
        return pgObject.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder
    {
       ImageView pg_img,ic_wifi,ic_lunch, ic_parking, ic_metro;
        TextView name_tv,address_tv, state_tv, city_tv, rent_tv;

        public DetailsViewHolder(View view)
        {
            super(view);

            pg_img = (ImageView) view.findViewById(R.id.pg_image);
            name_tv = (TextView) view.findViewById(R.id.pg_name_tv);
            address_tv = (TextView) view.findViewById(R.id.address_tv);
            state_tv = (TextView) view.findViewById(R.id.state_tv);
            rent_tv = (TextView) view.findViewById(R.id.rent_tv);
            ic_wifi = (ImageView) view.findViewById(R.id.ic_wifi);
            ic_lunch = (ImageView) view.findViewById(R.id.ic_lunch);
            ic_parking = (ImageView) view.findViewById(R.id.ic_car_parking);
            ic_metro = (ImageView) view.findViewById(R.id.ic_metro);

        }
    }
}