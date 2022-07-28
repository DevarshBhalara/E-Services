package com.example.e_services;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkerEditProfile extends AppCompatActivity {
    EditText mwname,wmono,wemail,udob,wcity,waddress,wpro;
    Switch sw1;

    ProgressBar progressBar;
    TextView mtvL;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_edit_profile);

        mwname = findViewById(R.id.ed_uname_w);
        wmono = findViewById(R.id.ed_mobile_number_w);
        wemail = findViewById(R.id.ed_email_w);
        wcity = findViewById(R.id.ed_city_w);
        waddress = findViewById(R.id.ed_address_w);
        wpro = findViewById(R.id.ed_profession_w);

        relativeLayout = findViewById(R.id.rlLoadingW);
        mtvL = findViewById(R.id.tvEditLW);
        progressBar = findViewById(R.id.progEditW);

        sw1 = findViewById(R.id.switchEdit);

        details();

        mwname.setEnabled(false);
        wmono.setEnabled(false);
        wemail.setEnabled(false);
        wpro.setEnabled(false);
        wcity.setEnabled(false);
        waddress.setEnabled(false);

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mwname.setEnabled(true);
                    mwname.setFocusable(true);
                    mwname.setFocusableInTouchMode(true);

                    wmono.setEnabled(true);
                    wmono.setFocusable(true);
                    wmono.setFocusableInTouchMode(true);

                    wemail.setEnabled(true);
                    wemail.setFocusable(true);
                    wemail.setFocusableInTouchMode(true);

                    wcity.setEnabled(true);
                    wcity.setFocusable(true);
                    wcity.setFocusableInTouchMode(true);

                    wpro.setEnabled(true);
                    wpro.setFocusable(true);
                    wpro.setFocusableInTouchMode(true);

                    waddress.setEnabled(true);
                    waddress.setFocusable(true);
                    waddress.setFocusableInTouchMode(true);
                }else{
                    mwname.setEnabled(false);
                    mwname.setFocusable(false);
                    mwname.setFocusableInTouchMode(false);

                    wmono.setEnabled(false);
                    wmono.setFocusable(false);
                    wmono.setFocusableInTouchMode(false);

                    wemail.setEnabled(false);
                    wemail.setFocusable(false);
                    wemail.setFocusableInTouchMode(false);

                    wcity.setEnabled(false);
                    wcity.setFocusable(false);
                    wcity.setFocusableInTouchMode(false);

                    wpro.setEnabled(false);
                    wpro.setFocusable(false);
                    wpro.setFocusableInTouchMode(false);

                    waddress.setEnabled(false);
                    waddress.setFocusable(false);
                    waddress.setFocusableInTouchMode(false);

                    relativeLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    mtvL.setVisibility(View.VISIBLE);

                    String s = getResources().getString( R.string.strlink);
                    String str = s+"workerProfileEdit.php?"+
                            "wid="+ getSharedPreferences("shared",MODE_PRIVATE).getString("user_id","-1") +
                            "&id="+ getSharedPreferences("shared",MODE_PRIVATE).getString("uniq_id","-1") +
                            "&wname="+mwname.getText().toString()+
                            "&mono="+wmono.getText().toString()+
                            "&address="+waddress.getText().toString()+
                            "&email="+wemail.getText().toString()+
                            "&city="+wcity.getText().toString()+
                            "&pro="+wpro.getText().toString();
                    Log.e("Update link" , ""+str);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                relativeLayout.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                mtvL.setVisibility(View.INVISIBLE);
                                if(response.getString("status").equals("1")){

                                    //Toast.makeText(getApplicationContext(),"updated", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences = getSharedPreferences( "shared",
                                            MODE_PRIVATE );
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putString("user_name",mwname.getText().toString());
                                    myEdit.putString("user_mobile_number",wmono.getText().toString());
                                    myEdit.apply();
                                    showDialogYes();
                                    //Toast.makeText(getApplicationContext(), getSharedPreferences("shared",MODE_PRIVATE).getString("user_name","-1"), Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(),"Profile Updated", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Try Again Later", Toast.LENGTH_SHORT).show();

                                }


                            }catch (JSONException e){
                                relativeLayout.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                mtvL.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Some Internal Error Occurred!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    RequestQueue a = Volley.newRequestQueue(getApplicationContext());
                    a.add(jsonObjectRequest);

                }
            }
        });




    }

    private void showDialogYes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkerEditProfile.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.download);
        builder.setMessage("Profile Updated Successfully!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void details() {

        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mtvL.setVisibility(View.VISIBLE);

        String s = getResources().getString( R.string.strlink);
        String str = s+"fetchWorkerDetail.php?"+
                "wid="+ getSharedPreferences("shared",MODE_PRIVATE).getString("user_id","-1") ;
        Log.e("str",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);

                    if(response.getString("status").equals("1")){

                        mwname.setText(response.getString("wname"));
                        waddress.setText(response.getString("waddress"));
                        wemail.setText(response.getString("wemail"));
                        wmono.setText(response.getString("mono"));
                        wcity.setText(response.getString("wcity"));
                        wpro.setText(response.getString("pro"));


                        //Toast.makeText(getApplicationContext(),response.getString("wname") , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Fail to load Data! Try again Later", Toast.LENGTH_SHORT).show();

                    }


                }catch (JSONException e){
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Some Internal Error Occurred!", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue a = Volley.newRequestQueue(this);
        a.add(jsonObjectRequest);

    }
}