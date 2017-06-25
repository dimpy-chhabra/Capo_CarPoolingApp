package com.example.dimpychhabra.capo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dimpy on 20/6/17.
 */

public class RidesJSONParserOff {
    static ArrayList<Ride> ridesList;

    public static ArrayList<Ride> parseData(String content) {

        JSONArray rides_arry = null;
        Ride rides = null;
        try {

            rides_arry = new JSONArray(content);
            ridesList = new ArrayList<>();

            for (int i = 0; i < rides_arry.length(); i++) {

                JSONObject obj = rides_arry.getJSONObject(i);
                rides = new Ride();

                rides.setRider_name(obj.getString("u_name"));
                Log.e(" in RidesJSONPARSEROFF "," NAME>"+obj.getString("u_name"));
                rides.setRider_college(obj.getString("u_college"));
                Log.e(" in RidesJSONPARSEROFF "," COLLEGE>"+obj.getString("u_college"));
                rides.setRider_mobNo(obj.getString("u_id"));
                Log.e(" in RidesJSONPARSEROFF "," MOB>"+obj.getString("u_id"));
                rides.setRider_sex(obj.getString("u_sex"));
                Log.e(" in RidesJSONPARSEROFF "," SEX>"+obj.getString("u_sex"));
                rides.putStatus(obj.getString("status"));
                rides.putDriver_id(obj.getString("dri_u_id"));
                rides.putR_id(obj.getString("r_id"));
                rides.putride_date(obj.getString("r_date"));
                rides.putStart_loc(obj.getString("from_loc"));
                rides.putDesti_loc(obj.getString("to_college"));
                rides.putppId(obj.getString("_pp_id"));
                rides.putpp(obj.getString("pick_point"));
                rides.putpptime(obj.getString("pick_point_time"));
                rides.putppPrice(obj.getString("pick_price"));
                rides.putExp_desti_time(obj.getString("reach_time"));
                rides.putNo_seats(obj.getString("num_seats"));
                rides.putRider_id(obj.getString("rider_id"));

                ridesList.add(rides);
            }
            Log.e(" in JSONPARSER "," No err so far "+ridesList.get(0).getPp()+"  "+ridesList.get(0).getPp_price());
            return ridesList;

        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }



}
