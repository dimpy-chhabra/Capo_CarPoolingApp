package com.example.dimpychhabra.capo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
/*
*Project : CAPO, fully created by
* Dimpy Chhabra, IGDTUW, BTech, IT
* Second year (as of 2017)
* Expected Class of 2019
* Please do not circulate as your own
* Criticism is appreciated to work on memory leaks and bugs
* Contact Info : Find me on Linked in : linkedin.com/in/dimpy-chhabra
*/
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class My_offered_rides_Frag extends Fragment {
    ListView listViewOffRid;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ProgressDialog progressDialog;
    ArrayList<Ride> ridesList;
    String res, res2;
    String riderId;
    String driver, pp ;
    private String DataParseUrl = "http://impycapo.esy.es/proposalsList.php";
    private String DataParseUrl2 = "http://impycapo.esy.es/updateStatus.php";

    public My_offered_rides_Frag() {
        // Required empty public constructor
    }

    private View view;
    String value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            driver = bundle.getString("driverID");
            pp = bundle.getString("ppID");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_offered_rides_, container, false);
        listViewOffRid = (ListView) view.findViewById(R.id.listViewOffRid);

        volleyToFetchResponse();

        listViewOffRid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Ride ride = ridesList.get(position);
                final String ppID = ride.getPp_id();
                final String rID = ride.getR_id();
                final String riderID = ride.getRider_mobNo();

                new AlertDialog.Builder(view.getContext())
                        .setTitle("Ride with "+ride.getRider_name())
                        .setMessage("Accept/ Decline or ignore this proposal!")
                        .setPositiveButton("Lets CAPO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                changestatus(ppID, rID, riderID, "2");
                                //sendSms();
                                Toast.makeText(getActivity().getApplicationContext(), "WE CAPO-ED! <3", Toast.LENGTH_SHORT).show();
                                ridesList.get(position).putStatus("2");
                                int i = Integer.parseInt(ridesList.get(position).getNo_seats());
                                i--;
                                ridesList.get(position).putNo_seats(""+String.valueOf(i));
                                ((RidesOfferedRequestsAdapter)listViewOffRid.getAdapter()).notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("NOPE!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String a =ridesList.get(position).getstatus();
                                changestatus(ppID, rID, riderID, "-1");
                                Toast.makeText(getActivity().getApplicationContext(), " DECLINED", Toast.LENGTH_SHORT).show();
                                ridesList.get(position).putStatus("-1");
                                int i = Integer.parseInt(ridesList.get(position).getNo_seats());
                                i++;
                                if(a.equalsIgnoreCase("2"))
                                    ridesList.get(position).putNo_seats(""+String.valueOf(i));
                                ((RidesOfferedRequestsAdapter)listViewOffRid.getAdapter()).notifyDataSetChanged();

                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity().getApplicationContext(), " ignored!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(R.drawable.trak)
                        .show();

                Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void sendSms() {

        SmsManager smsm = SmsManager.getDefault();
        smsm.sendTextMessage(riderId, null, "Your Proposal has been accepted! Congrats! -Regards Capo( Happy fuel Saving)", null, null);
    }

    private void changestatus(final String pp_id, final String r_id ,final String b, final String st) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating Status");
        progressDialog.show();

        final String ride_id = r_id;
        final String rider_id = b;
        final String ppID = pp_id;
        final String sta = st;

        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && response.length() > 0) {
                    res = response;
                    Log.e("onResponse :   ",">>"+res);

                } else {
                    Toast.makeText(getContext(), "You didnt offer no rides!", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in main_Offered_Frag", " error in parsing data");
                        }
                        else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ride_id", ride_id);
                params.put("rider_id", rider_id);
                params.put("pro_id", ppID);
                params.put("stat", sta);

                Log.e("Sending Data to update"," Send data>"+ride_id+">"+rider_id+">"+ppID);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void volleyToFetchResponse() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching The Requests");
        progressDialog.show();

        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && response.length() > 0) {
                    Log.e("in my offered rides ", " " + response);
                    res = response;

                    ridesList = RidesJSONParserOff.parseData(response);
                    RidesOfferedRequestsAdapter rideAdapter = new RidesOfferedRequestsAdapter(getActivity(), ridesList);
                    listViewOffRid.setAdapter(rideAdapter);

                } else {
                    Toast.makeText(getContext(), "No proposals to ride with you for this pick up point!!! Sorry", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in my_ofrd_rides_Frag", " error in parsing data");
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("driverID", driver);
                params.put("ppID", pp);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

}