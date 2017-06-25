package com.example.dimpychhabra.capo;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class letsCapo_Frag1 extends Fragment implements View.OnClickListener {

    private static View view;
    private static Button button;
    private static EditText seats,to, from, fromTime, toTime, pp1, pp2, pp3, pp1Time, pp2Time, pp3Time, date, pp1Price, pp2Price, pp3Price;
    private static FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    private String DataParseUrl = "http://impycapo.esy.es/offerRide.php";

    public letsCapo_Frag1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lets_capo__frag1, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        button = (Button) view.findViewById(R.id.bSubmit);
        to = (EditText)view.findViewById(R.id.to);
        from = (EditText)view.findViewById(R.id.from);
        fromTime = (EditText)view.findViewById(R.id.fromTime);
        date = (EditText) view.findViewById(R.id.date);
        toTime = (EditText) view.findViewById(R.id.toTime);
        pp1 = (EditText) view.findViewById(R.id.pp1);
        pp2 = (EditText) view.findViewById(R.id.pp2);
        pp3 = (EditText) view.findViewById(R.id.pp3);
        pp1Time = (EditText) view.findViewById(R.id.pp1Time);
        pp2Time = (EditText) view.findViewById(R.id.pp1Time);
        pp3Time = (EditText) view.findViewById(R.id.pp3Time);
        pp1Price = (EditText) view.findViewById(R.id.pp1Price);
        pp2Price = (EditText) view.findViewById(R.id.pp2Price);
        pp3Price = (EditText) view.findViewById(R.id.pp3Price);
        seats = (EditText) view.findViewById(R.id.seats);
    }

    private void setListeners() {
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bSubmit:
                if (date.equals("") || to.equals("") || from.equals("") || fromTime.equals("") || toTime.equals("") || pp1.equals("") || pp1Time.equals("") || pp1Price.equals(""))
                {   Log.e("in lets capo frag1"," equals null");
                    new CustomToast().Show_Toast(getActivity(), view, "Please enter All details in order to proceed. and atleast 1 PICK UP POINT");
                }
                else {
                    String DATE, TO, FROM, TOTIME, FROMTIME, PP1, PP1TIME, PP1PRICE, PP2 = null, PP2TIME = null, PP2PRICE = null, PP3 = null, PP3TIME = null, PP3PRICE = null, SEATS;
                    DATE = date.getText().toString();
                    TO = to.getText().toString();
                    FROM = from.getText().toString();
                    TOTIME = toTime.getText().toString();
                    FROMTIME = fromTime.getText().toString();
                    PP1 = pp1.getText().toString();
                    PP1TIME = pp1Time.getText().toString();
                    PP1PRICE = pp1Price.getText().toString();
                    if (!pp2Time.equals(null)) {
                        PP2 = pp2.getText().toString();
                        PP2TIME = pp2Time.getText().toString();
                        PP2PRICE = pp2Price.getText().toString();
                    }
                    if (!pp3Time.equals(null)) {
                        PP3 = pp3.getText().toString();
                        PP3TIME = pp3Time.getText().toString();
                        PP3PRICE = pp3Price.getText().toString();
                    }
                    SEATS = seats.getText().toString();

                    insertData(DATE, TO, FROM, TOTIME, FROMTIME, PP1, PP1TIME, PP1PRICE, PP2, PP2TIME, PP2PRICE, PP3, PP3TIME, PP3PRICE, SEATS);

                }break;
        }
    }

    private void insertData(String DATE, String TO, String FROM, String TOTIME, String FROMTIME, String PP1, String PP1TIME, String PP1PRICE, String PP2, String PP2TIME, String PP2PRICE, String PP3, String PP3TIME, String PP3PRICE, String SEATS) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching The File....");
        progressDialog.show();

        final String Date = DATE;
        final String To = TO;
        final String From = FROM;
        final String ToTime = TOTIME;
        final String FromTime = FROMTIME;
        final String Pp1 = PP1;
        final String Pp1Time = PP1TIME;
        final String Pp1Price = PP1PRICE;
        final String Pp2 = PP2;
        final String Pp2Time = PP2TIME;
        final String Pp2Price = PP2PRICE;
        final String Pp3 = PP3;
        final String Pp3Time = PP3TIME;
        final String Pp3Price = PP3PRICE;
        final String Seats = SEATS;

        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response != null && response.length() > 0) {
                    progressDialog.dismiss();
                    Log.e(" On RESPONSE : ","  "+ response);
                    new CustomToast().Show_Toast(getActivity(), view,
                            "Data Sucessfully Added");
                    //Toast.makeText(getContext(), "Data Sucessfully Added" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(getContext(), "Response : ______", Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0)
                            Toast.makeText(getContext(), "OnErrorResponse : "+error.toString(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getContext(), "OnErrorResponse : ______", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SharedPreferences spref = getActivity().getSharedPreferences(BaseActivity.MyPref, Context.MODE_PRIVATE);
                final String b = spref.getString(BaseActivity.Phone, null);
                params.put("u_id", b.trim());
                params.put("from", From);  //
                params.put("date", Date);
                params.put("fromTime", FromTime);   //
                params.put("to", To.trim().toUpperCase());   //
                params.put("toTime", ToTime);     //
                params.put("seats", Seats);     //
                params.put("extra", "Merry CarPooling!");
                params.put("Pp1", Pp1);     //
                params.put("Pp1Time", Pp1Time);     //
                params.put("Pp1Price", Pp1Price);     //
                params.put("Pp2", Pp2);      //
                params.put("Pp2Time", Pp2Time);
                params.put("Pp2Price", Pp2Price);     //
                params.put("Pp3", Pp3);
                params.put("Pp3Time", Pp3Time);
                params.put("Pp3Price", Pp3Price);     //

                return params;
            }
        };
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}