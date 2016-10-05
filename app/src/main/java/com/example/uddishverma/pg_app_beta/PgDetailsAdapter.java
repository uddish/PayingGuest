package com.example.uddishverma.pg_app_beta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Naman on 07-08-2016.
 */
//************************************Class to join card view with recycler view****************************************************
public class PgDetailsAdapter extends RecyclerView.Adapter<PgDetailsAdapter.DetailsViewHolder>
{

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference imageDownloadRef;

    public static final String TAG = "PgDetailsAdapter";

    ArrayList<PgDetails_POJO.PgDetails> pgObject = new ArrayList<>();

    Context ctx;

    public PgDetailsAdapter(ArrayList<PgDetails_POJO.PgDetails> pgObject,Context ctx) {
        this.pgObject = pgObject;
        this.ctx = ctx;
    }


    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_new,parent,false);

        DetailsViewHolder detailsViewHolder = new DetailsViewHolder(view,ctx,pgObject);

        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(final DetailsViewHolder holder, int position)    {

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

//        holder.state_tv.setText(details.getState());
        String rent = String.valueOf((int)details.getRent());
        holder.rent_tv.setText("₹" + rent);

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

            Intent intent = new Intent(this.ctx,FragmentCaller.class);
            intent.putExtra("PG ID",obj.getId());
            intent.putExtra("PG Name",obj.getPgName());
            intent.putExtra("OWNER NAME",obj.getOwnerName());
            intent.putExtra("CONTACT NO",obj.getContactNo());
            intent.putExtra("EMAIL",obj.getEmail());
            intent.putExtra("INSTITUTE",obj.getNearbyInstitute());
            intent.putExtra("WIFI",obj.getWifi());
            intent.putExtra("AC",obj.getAc());
            intent.putExtra("REFRIGERATOR",obj.getFridge());
            intent.putExtra("PARKING",obj.getParking());
            intent.putExtra("TV",obj.getTv());
            intent.putExtra("LUNCH",obj.getLunch());
            intent.putExtra("DINNER",obj.getDinner());
            intent.putExtra("BREAKFAST",obj.getBreakfast());
            intent.putExtra("RO",obj.getRoWater());
            intent.putExtra("HOT WATER",obj.getHotWater());
            intent.putExtra("SECURITY",obj.getSecurity());
            intent.putExtra("RENT",obj.getRent());
            intent.putExtra("DEPOSIT",obj.getDepositAmount());
            intent.putExtra("ADDRESS", obj.getAddressOne());
            intent.putExtra("LOCALITY", obj.getLocality());
            intent.putExtra("CITY", obj.getCity());
            intent.putExtra("STATE", obj.getState());
            intent.putExtra("PINCODE", obj.getPinCode());
            intent.putExtra("EXTRAFEATURES", obj.getExtraFeatures());
            //Sending the intents for the Pg Images which are to be attached to the View Pager
            intent.putExtra("IMAGE_ONE", obj.getPgImageOne());
            intent.putExtra("IMAGE_TWO", obj.getPgImageTwo());
            intent.putExtra("IMAGE_THREE", obj.getPgImageThree());
            intent.putExtra("IMAGE_FOUR", obj.getPgImageFour());

            Log.d(TAG,"calling");
            this.ctx.startActivity(intent);
        }
    }
}