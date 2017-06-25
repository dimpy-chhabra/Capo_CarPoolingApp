package com.example.dimpychhabra.capo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Main_found_Frag extends Fragment {
    private static ListView listView;
    private static View view;
    private static TextView tv2;
    ArrayList<Ride> ridesList;
    private static FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    String res;
    private String DataParseUrl = "http://impycapo.esy.es/test.php";


    public Main_found_Frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_main_found_, container, false);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        fragmentManager = getActivity().getSupportFragmentManager();
        //     initViews();
        //     setListeners();
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainerTrack, new Main_offered_Frag(),
                                BaseActivity.offered_rides_Frag).commit();
            }
        });

        listView = (ListView) view.findViewById(R.id.list);

        fetch();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Ride ride = ridesList.get(position);
                //menu pop up... click on one to send a request! and then print toast as a new row is created in the propsal table
                Toast.makeText(getActivity().getApplicationContext(), "yo " + ride.getR_id(), Toast.LENGTH_SHORT).show();


                Toast.makeText(getActivity(), "onCLick!!!", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }


    private void fetch() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Rides....");
        progressDialog.show();

        SharedPreferences spref = this.getActivity().getSharedPreferences(BaseActivity.MyPref, Context.MODE_PRIVATE);
        final String b = spref.getString(BaseActivity.Phone, null);
        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && response.length() > 0) {
                    Log.e(" on res "," "+response);
                    ridesList = RidesJSONParser.parseData(response);

                    RidesFoundAdapter rideAdapter = new RidesFoundAdapter(getActivity(), ridesList);
                    listView.setAdapter(rideAdapter);
                } else {
                    Log.e(" on res else"," "+response);
                    Toast.makeText(getContext(), "You havn't requested any Capo!", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in Main_Found_Frag", " error in parsing data>> "+error.toString());
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", b);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }
}