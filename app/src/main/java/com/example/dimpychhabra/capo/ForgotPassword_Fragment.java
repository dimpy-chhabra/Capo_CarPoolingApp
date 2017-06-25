package com.example.dimpychhabra.capo;

/**
 * Created by Dimpy Chhabra on 3/20/2017.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ForgotPassword_Fragment extends Fragment implements
        OnClickListener {
    private static View view;
    ProgressDialog progressDialog;
    private static EditText emailId;
    private static TextView submit, back;
    RequestQueue requestQueue;
    StringRequest stringRequest;


    private static String DataParseUrl = "http://impycapo.esy.es/forgotPass.php";

    public ForgotPassword_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = (EditText) view.findViewById(R.id.registered_emailid);
        submit = (TextView) view.findViewById(R.id.forgot_button);
        back = (TextView) view.findViewById(R.id.backToLoginBtn);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            back.setTextColor(csl);
            submit.setTextColor(csl);

        } catch (Exception e) {
        }

    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new LoginBaseActivity().replaceLoginFragment();
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        final String getEmailId = emailId.getText().toString().trim();


        // Pattern for email id validation
        Pattern p = Pattern.compile(BaseActivity.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your Mobile Nmber.");

            // Check if email id is valid or not
        else if (getEmailId.length()!=10)
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Mobile Number is Invalid.");


            // Else submit email id and fetch passwod or do your stuff
        else{
            Toast.makeText(getActivity(), "Get Forgot Password.",
                    Toast.LENGTH_SHORT).show();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Checking details...");
            progressDialog.show();
            stringRequest = new StringRequest(Request.Method.POST, DataParseUrl, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), " Your password is " + response, Toast.LENGTH_SHORT).show();
                    Log.e("response: ", "  " + response);
                    if (response.equals("Err")) {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Either you made a typo, or Register!");

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "wrong username or password", Toast.LENGTH_LONG).show();
                        Log.e("in login frag", "" + response);
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            if (error != null && error.toString().length() > 0)
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mob", getEmailId);
                    return params;
                }

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}