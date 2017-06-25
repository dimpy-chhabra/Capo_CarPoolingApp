package com.example.dimpychhabra.capo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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


public class Main_offered_Frag extends Fragment {


    View view;
    Button button;
    EditText to, from, fromTime;
    TextView tv11, tv22;
    ArrayList<Ride> ridesList;
    ListView listView;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    private static FragmentManager fragmentManager;
    String res = "dummy";
    ProgressDialog progressDialog;
    SharedPreferences spref ;//= getActivity().getSharedPreferences(BaseActivity.MyPref, Context.MODE_PRIVATE);


    private static String DataParseUrl = "http://impycapo.esy.es/offeredRidesList.php";
    private String DataParseUrl2 = "http://impycapo.esy.es/updateStatus.php";

    public Main_offered_Frag() {
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
        view = inflater.inflate(R.layout.fragment_main_offered_, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        tv11 = (TextView) view.findViewById(R.id.tv11);
        listView = (ListView) view.findViewById(R.id.list);

        spref = this.getActivity().getSharedPreferences(BaseActivity.MyPref, Context.MODE_PRIVATE);
        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_out, R.anim.left_enter)
                        .replace(R.id.frameContainerTrack, new Main_found_Frag(),
                                BaseActivity.found_rides_Frag).commit();
            }
        });

        //here we will fetch data from database via volley --> make an object --> set adapter and work accordingly
        volleyToFetchResponse();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Ride ride = ridesList.get(position);
                Log.e("onItem Click ", " " + ride.getR_id());
                final String b = spref.getString(BaseActivity.Phone, null);

                Fragment fragment = new My_offered_rides_Frag();
                Bundle bundle = new Bundle();
                bundle.putString("driverID", b);
                bundle.putString("ppID", ride.getPp_id());
                fragment.setArguments(bundle);

                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainerTrack, fragment,
                                BaseActivity.My_offered_rides_Frag).commit();

            }
        });

        return view;
    }

    private void updateStatus(String r_id, String b, String pp_id , String st) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Rides...");
        progressDialog.show();

        final String ride_id = r_id;
        final String rider_id = b;
        final String  ppID = pp_id;
        final String sta = st;
                 /*
                 $ride_id = $_POST["ride_id"];
                 $pro_id = $_POST["pro_id"];
                 $rider_id = $_POST["rider_id"];
        r_id='$ride_id' AND rider_id='$rider_id' AND _pp_id='$pro_id'
                 */
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
        progressDialog.setMessage("Fetching The File....");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && response.length() > 0) {
                    res = response;
                    Log.e("onResponse :   ",">>"+res);
                    ridesList = RidesJSONParser.parseData(response);
                    RidesOfferedAdapter rideAdapter = new RidesOfferedAdapter(getActivity(), ridesList);
                    listView.setAdapter(rideAdapter);
                } else {
                    Toast.makeText(getContext(), "You Do Not have any pending requests!", Toast.LENGTH_LONG).show();
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
                SharedPreferences spref = getActivity().getSharedPreferences(BaseActivity.MyPref, Context.MODE_PRIVATE);
                final String dr_id = spref.getString(BaseActivity.Phone, null); // getting String
                params.put("mob_no", dr_id.trim());
                Log.e("mob_no",""+dr_id.trim());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

}