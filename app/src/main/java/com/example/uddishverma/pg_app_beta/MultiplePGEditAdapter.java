package com.example.uddishverma.pg_app_beta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MultiplePGEditAdapter extends RecyclerView.Adapter<MultiplePGEditAdapter.DetailsViewHolder> {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference imageDownloadRef;

    public static final String TAG = "PgDetailsAdapter";

    ArrayList<PgDetails_POJO.PgDetails> pgObject = new ArrayList<>();

    Context ctx;

    public MultiplePGEditAdapter(ArrayList<PgDetails_POJO.PgDetails> pgObject,Context ctx) {
        this.pgObject = pgObject;
        this.ctx = ctx;
    }


    @Override
    public MultiplePGEditAdapter.DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_multiple_pgedit_adapter,parent,false);

        MultiplePGEditAdapter.DetailsViewHolder detailsViewHolder = new MultiplePGEditAdapter.DetailsViewHolder(view,ctx,pgObject);

        return detailsViewHolder;
    }


    @Override
    public void onBindViewHolder(final MultiplePGEditAdapter.DetailsViewHolder holder, int position)    {

        PgDetails_POJO.PgDetails details = pgObject.get(position);

        //Fetching the image from the firebase reference;
        Log.d(TAG, "onBindViewHolder: URL" + details.getPgImageOne());
        imageDownloadRef = storage.getReferenceFromUrl(details.getPgImageOne());
        imageDownloadRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(ctx).load(uri).fit().into(holder.pg_img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ctx, "Failed To Upload The Image!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.name_tv.setText(details.getPgName());
        holder.address_tv.setText(details.getAddressOne());
        holder.address_tv.setSelected(true);

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

    public static class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView pg_img,ic_wifi,ic_lunch, ic_parking, ic_metro;
        TextView name_tv,address_tv, state_tv, rent_tv;

        ArrayList<PgDetails_POJO.PgDetails> list = new ArrayList<PgDetails_POJO.PgDetails>();
        Context ctx;

        public DetailsViewHolder(View view,Context ctx, ArrayList<PgDetails_POJO.PgDetails> list)
        {
            super(view);

            this.ctx=ctx;
            this.list=list;

            view.setOnClickListener(this);

            pg_img = (ImageView) view.findViewById(R.id.pg_image);
            name_tv = (TextView) view.findViewById(R.id.pg_name_tv);
            address_tv = (TextView) view.findViewById(R.id.address_tv);
            rent_tv = (TextView) view.findViewById(R.id.rent_tv);
            ic_wifi = (ImageView) view.findViewById(R.id.ic_wifi);
            ic_lunch = (ImageView) view.findViewById(R.id.ic_lunch);
            ic_parking = (ImageView) view.findViewById(R.id.ic_car_parking);
            ic_metro = (ImageView) view.findViewById(R.id.ic_metro);

        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            PgDetails_POJO.PgDetails obj = this.list.get(position);

            Intent intent = new Intent(this.ctx,EditPG.class);
            intent.putExtra("PG ID",obj.getId());
            intent.putExtra("source", "MultiplePGEditApapter");
            //TODO How to add Finish(); here?
            this.ctx.startActivity(intent);
        }
    }
}
