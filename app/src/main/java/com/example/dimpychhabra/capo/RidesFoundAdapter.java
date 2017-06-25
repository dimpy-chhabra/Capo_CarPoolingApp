package com.example.dimpychhabra.capo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
/*
*Project : CAPO, fully created by
* Dimpy Chhabra, IGDTUW, BTech, IT
* Second year (as of 2017)
* Expected Class of 2019
* Please do not circulate as your own
* Criticism is appreciated to work on memory leaks and bugs
* Contact Info : Find me on Linked in : linkedin.com/in/dimpy-chhabra
*
*/
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Dimpy Chhabra on 3/26/2017.
 */

public class RidesFoundAdapter extends ArrayAdapter<Ride>{

    public RidesFoundAdapter(Context context, ArrayList<Ride> ridesArrayList){
        super(context, 0, ridesArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        //getting the view into a variable listItemView
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.ride_item, parent, false);
        }
        //This is to ensure the reusablity of a view

        Ride currentRide = getItem(position);

        TextView fromTo = (TextView) listItemView.findViewById(R.id.textViewDirections);
        fromTo.setText(currentRide.getStart_loc() + " TO " + currentRide.getDesti_loc()+" \nVia : "+currentRide.getPp()+" At "+currentRide.getPp_time()+"!");

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);

        Log.e("in adapter ", "  Status Value>>"+currentRide.getstatus()+" for from"+currentRide.getPp());

        if(currentRide.getstatus().trim().equalsIgnoreCase("1"))
            imageView.setImageResource(R.drawable.statr);
        else if(currentRide.getstatus().trim().equalsIgnoreCase("2"))
            imageView.setImageResource(R.drawable.statg);
        else if(currentRide.getstatus().trim().equalsIgnoreCase("-1"))
            imageView.setImageResource(R.drawable.statb);
        else
            imageView.setImageResource(R.drawable.statnew);

        TextView price = (TextView) listItemView.findViewById(R.id.Seats);
        price.setText("Seats: " + (currentRide.getNo_seats())  +" And Status : "+currentRide.getstatus());

        TextView tc = (TextView) listItemView.findViewById(R.id.StartingTime);
        tc.setText("ReachAt:" + currentRide.getExp_desti_time());

        TextView extras = (TextView) listItemView.findViewById(R.id.Extra);
        extras.setText("Date:" + currentRide.getDateORide());

        TextView pricetag = (TextView) listItemView.findViewById(R.id.pricetag);
        pricetag.setText("Rs. " + currentRide.getPp_price());

        return listItemView;
    }
}
