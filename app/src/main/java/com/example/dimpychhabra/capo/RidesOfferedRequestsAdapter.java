package com.example.dimpychhabra.capo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dimpy on 20/6/17.
 */

public class RidesOfferedRequestsAdapter extends ArrayAdapter<Ride> {

    public RidesOfferedRequestsAdapter(Context context, ArrayList<Ride> ridesArrayList){
        super(context, 0, ridesArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        //getting the view into a variable listItemView
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.request_item, parent, false);
        }
        //This is to ensure the reusablity of a view

        Ride currentRide = getItem(position);

        TextView fromTo = (TextView) listItemView.findViewById(R.id.textViewDirections);
        fromTo.setText(currentRide.getStart_loc() + " TO " + currentRide.getDesti_loc()+" \nVia : "+currentRide.getPp()+" At "+currentRide.getPp_time()+"!");

        ImageView statusImageView = (ImageView) listItemView.findViewById(R.id.statusImageView);
        if(currentRide.getstatus().trim().equalsIgnoreCase("1"))
            statusImageView.setImageResource(R.drawable.req);
        else if(currentRide.getstatus().trim().equalsIgnoreCase("2"))
            statusImageView.setImageResource(R.drawable.ok);
        else if(currentRide.getstatus().trim().equalsIgnoreCase("-1"))
            statusImageView.setImageResource(R.drawable.age);
        else
            statusImageView.setImageResource(R.drawable.statnew);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);


        Log.e("in adapter ", "  Status Value>>"+currentRide.getstatus()+" for from"+currentRide.getPp());

        if(currentRide.getRider_sex().equalsIgnoreCase("F")) {
            imageView.setImageResource(R.drawable.fpp);
            Log.e("Rider's Sex "," Female");
        }else {
            imageView.setImageResource(R.drawable.mpp);
            Log.e("Rider's Sex "," Male");
        }

        TextView RiderName = (TextView) listItemView.findViewById(R.id.textViewRiderName);
        RiderName.setText(currentRide.getRider_name());

        TextView RiderCollege = (TextView) listItemView.findViewById(R.id.textViewRiderCollege);
        RiderCollege.setText(currentRide.getRider_college());

        TextView RiderNumber = (TextView) listItemView.findViewById(R.id.textViewRiderNumber);
        RiderNumber.setText(currentRide.getRider_mobNo());

        TextView price = (TextView) listItemView.findViewById(R.id.Seats);
        price.setText("Seats: \n" + (currentRide.getNo_seats()) );

        TextView tc = (TextView) listItemView.findViewById(R.id.StartingTime);
        tc.setText("ReachAt:" + currentRide.getExp_desti_time());

        TextView extras = (TextView) listItemView.findViewById(R.id.Extra);
        extras.setText("Date:" + currentRide.getDateORide());

        TextView pricetag = (TextView) listItemView.findViewById(R.id.pricetag);
        pricetag.setText("Rs. " + currentRide.getPp_price());

        return listItemView;
    }
}
