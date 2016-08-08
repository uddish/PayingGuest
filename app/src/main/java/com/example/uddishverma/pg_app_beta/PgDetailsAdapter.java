package com.example.uddishverma.pg_app_beta;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Naman on 07-08-2016.
 */
public class PgDetailsAdapter extends RecyclerView.Adapter<PgDetailsAdapter.DetailsViewHolder>
{

    public static final String TAG = "PgDetailsAdapter";

    ArrayList<PgDetails> pgObject=new ArrayList<PgDetails>();

    public PgDetailsAdapter(ArrayList<PgDetails> pgObject)
    {
        this.pgObject=pgObject;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);


        DetailsViewHolder detailsViewHolder =new DetailsViewHolder(view);

        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position)
    {
        PgDetails details=pgObject.get(position);

        Log.d(TAG, "onBindViewHolder: " + details.getPgName());
        Log.d(TAG, "onBindViewHolder: " + details.getLocation());
        holder.pg_img.setImageResource(details.getImageId());
        holder.name_tv.setText(details.getPgName());
        holder.loc_tv.setText(details.getLocation());

        if(details.getWifi()==true)
            holder.wifi_img.setImageResource(R.drawable.ic_wifi_blue_grey_700_24dp);
        else
            holder.wifi_img.setImageResource(R.drawable.ic_signal_wifi_off_grey_700_24dp);

        if(details.getBreakfast()==true)
            holder.bf_image.setImageResource(R.drawable.ic_free_breakfast_grey_700_24dp);
    }

    @Override
    public int getItemCount()
    {
        return pgObject.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder
    {
       ImageView pg_img,wifi_img,bf_image;
        TextView name_tv,loc_tv;

        public DetailsViewHolder(View view)
        {
            super(view);

            pg_img= (ImageView) view.findViewById(R.id.pg_image);
            wifi_img= (ImageView) view.findViewById(R.id.wifi);
            name_tv= (TextView) view.findViewById(R.id.pg_name);
            loc_tv= (TextView) view.findViewById(R.id.pg_location);
            bf_image= (ImageView) view.findViewById(R.id.breakfast);

        }
    }
}