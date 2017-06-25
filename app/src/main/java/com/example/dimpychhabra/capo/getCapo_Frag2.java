package com.example.dimpychhabra.capo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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


public class getCapo_Frag2 extends Fragment implements View.OnClickListener{
    private static View view;
    private static TextView tv1;
    private static Button b1;
    ListView listViewFoundRid;
    private static FragmentManager fragmentManager;
    ArrayList<Ride> ridesList;
    final Context context = getActivity();

    String data;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    private String DataParseUrl = "http://impycapo.esy.es/foundRidesList.php";
    private String DataParseUrl2="http://impycapo.esy.es/proposeRide.php";
    ProgressDialog progressDialog;

    public getCapo_Frag2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            data = bundle.getString("to:");
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_get_capo__frag2, container, false);
        //progressNewsList = (ProgressBar) view.findViewById(R.id.progressNewsList);


        initViews();
        Log.e("!frag2 Received Data : ", ""+data);
        Toast.makeText(getActivity().getApplicationContext(), "  >>>>>> " + data, Toast.LENGTH_SHORT).show();

        //fetch data for final location
        fetch();
        setListeners();

        listViewFoundRid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final  View v = view;
                final int posi = position;
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Let's Capo?")
                        .setMessage("You may request a ride to "+ridesList.get(position).getDesti_loc()+" for " +
                                "Rs. "+ridesList.get(position).getPp_price()+ " NOW!\nOr not! :p")
                        .setPositiveButton("Lets CAPO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                changestatus(ridesList.get(posi).getR_id(), ridesList.get(posi).getPp_id() );
                                ridesList.get(posi).putStatus("1");
                                ((RidesFoundAdapter)listViewFoundRid.getAdapter()).notifyDataSetChanged();
                                //sendSms();
                                //Toast.makeText(getActivity().getApplicationContext(), "WE CAPO-ED! <3", Toast.LENGTH_SHORT).show();
                                new CustomToast().Show_Toast(getActivity(), v, "Woo-Hoo! You just requested for a capo ride.\nKeep a check on your 'My Rides' to see when your proposal is accepted.");
                            }
                        })
                        .setNegativeButton("NOPE!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity().getApplicationContext(), "Let's Keep scrolling?!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(R.drawable.capopic)
                        .show();
            }
        });



        return view;
    }

    private void changestatus(String r_id, String pp_id) {

        SharedPreferences spref = this.getActivity().getSharedPreferences(BaseActivity.MyPref, Context.MODE_PRIVATE);
        final String b = spref.getString(BaseActivity.Phone, null);
        final String rid = r_id;
        final String ppid = pp_id;
        Log.e(" in change stat "," request sent");
        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() > 0) {
                    Log.e(" on res "," "+response);
                } else {

                    Log.e(" on res else"," "+response);
                    Toast.makeText(getContext(), "No-one is going your way!", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in MY_Found_ride_Frag", " error in parsing data");
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("riderID", b);
                params.put("rideID", rid);
                params.put("ppid", ppid);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

        ((RidesFoundAdapter)listViewFoundRid.getAdapter()).notifyDataSetChanged();
    }

    private void fetch() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching The File....");
        progressDialog.show();

        SharedPreferences spref = this.getActivity().getSharedPreferences(BaseActivity.MyPref, Context.MODE_PRIVATE);
        final String b = spref.getString(BaseActivity.Phone, null);

        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && response.length() > 0) {
                    Log.e(" on res "," "+response);
                    ((ViewGroup) b1.getParent()).removeView(b1);
                    ((ViewGroup) tv1.getParent()).removeView(tv1);
                    ridesList = RidesJSONParser.parseData(response);
                    RidesFoundAdapter rideAdapter = new RidesFoundAdapter(getActivity(), ridesList);
                    listViewFoundRid.setAdapter(rideAdapter);
                } else {
                    ((ViewGroup) listViewFoundRid.getParent()).removeView(listViewFoundRid);
                    Log.e(" on res else"," "+response);
                    Toast.makeText(getContext(), "No-one is going your way!", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in MY_Found_ride_Frag", " error in parsing data");
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("college", data);
                params.put("rider", b);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        tv1 = (TextView)view.findViewById(R.id.tv1);
        b1 = (Button)view.findViewById(R.id.b1);
        listViewFoundRid = (ListView)view.findViewById(R.id.listViewFoundRid);
    }

    private void setListeners() {
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                break;
            case R.id.tv1:
                Log.e("ablah ", " ablhad ");
                break;

        }
    }
}


