package com.example.e_services;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.Locale;

public class UserEditProfile extends AppCompatActivity {
    EditText muname,umono,uemail,udob,ucity,uaddress;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch swEdit;
    String add;

    ProgressBar progressBar;
    TextView mtvL;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        muname = findViewById(R.id.ed_uname_user);
        umono = findViewById(R.id.ed_mobile_number_user);

        uemail = findViewById(R.id.ed_email_user);

        ucity = findViewById(R.id.ed_city_user);
        uaddress = findViewById(R.id.ed_address_user);

        relativeLayout = findViewById(R.id.rlLoading);
        mtvL = findViewById(R.id.tvEditL);
        progressBar = findViewById(R.id.progEdit);

        getSupportActionBar().hide();
        details();

        muname.setEnabled(false);
        umono.setEnabled(false);
        uemail.setEnabled(false);

        ucity.setEnabled(false);
        uaddress.setEnabled(false);

        swEdit = findViewById(R.id.switchEdit);

        swEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    muname.setEnabled(true);
                    muname.setFocusable(true);
                    muname.setFocusableInTouchMode(true);

                    umono.setEnabled(true);
                    umono.setFocusable(true);
                    umono.setFocusableInTouchMode(true);

                    uemail.setEnabled(true);
                    uemail.setFocusable(true);
                    uemail.setFocusableInTouchMode(true);

                    ucity.setEnabled(true);
                    ucity.setFocusable(true);
                    ucity.setFocusableInTouchMode(true);

                    uaddress.setEnabled(true);
                    uaddress.setFocusable(true);
                    uaddress.setFocusableInTouchMode(true);


                }else{


                    muname.setEnabled(false);
                    muname.setFocusable(false);
                    muname.setFocusableInTouchMode(false);

                    umono.setEnabled(false);
                    umono.setFocusable(false);
                    umono.setFocusableInTouchMode(false);

                    uemail.setEnabled(false);
                    uemail.setFocusable(false);
                    uemail.setFocusableInTouchMode(false);

                    ucity.setEnabled(false);
                    ucity.setFocusable(false);
                    ucity.setFocusableInTouchMode(false);

                    uaddress.setEnabled(false);
                    uaddress.setFocusable(false);
                    uaddress.setFocusableInTouchMode(false);

                  
                    relativeLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    mtvL.setVisibility(View.VISIBLE);

                    String s = getResources().getString( R.string.strlink);
                    String str = s+"editUserProfile.php?"+
                            "uid="+ getSharedPreferences("shared",MODE_PRIVATE).getString("user_id","-1") +
                            "&id="+ getSharedPreferences("shared",MODE_PRIVATE).getString("uniq_id","-1") +
                            "&uname="+muname.getText().toString()+
                            "&mono="+umono.getText().toString()+
                            "&address="+uaddress.getText().toString()+
                            "&email="+uemail.getText().toString()+
                            "&city="+ucity.getText().toString();
                    Log.e("Update link" , ""+str);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{

                                if(response.getString("status").equals("1")){

                                    //Toast.makeText(getApplicationContext(),"updated", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences = getSharedPreferences( "shared", MODE_PRIVATE );
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putString("user_name",muname.getText().toString());
                                    myEdit.putString("user_mobile_number",umono.getText().toString());
                                    myEdit.apply();
                                    relativeLayout.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    mtvL.setVisibility(View.INVISIBLE);

                                    //Toast.makeText(getApplicationContext(), getSharedPreferences("shared",MODE_PRIVATE).getString("user_name","-1"), Toast.LENGTH_SHORT).show();
                                    showDialogYes();
                                    //Toast.makeText(getApplicationContext(),"updated", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Please try again later!", Toast.LENGTH_SHORT).show();

                                }


                            }catch (JSONException e){
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
                    //Toast.makeText(getApplicationContext(), uaddress.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showDialogYes() {

        AlertDialog.Builder builder = new AlertDialog.Builder(UserEditProfile.this);
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
        String str = s+"fetchUserDetail.php?"+
                "uid="+ getSharedPreferences("shared",MODE_PRIVATE).getString("user_id","-1") ;
        Log.e("str",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getString("status").equals("1")){
                        relativeLayout.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        mtvL.setVisibility(View.INVISIBLE);

                        muname.setText(response.getString("uname"));
                        uaddress.setText(response.getString("uaddress"));
                        uemail.setText(response.getString("uemail"));
                        umono.setText(response.getString("mono"));
                        ucity.setText(response.getString("ucity"));


                        //Toast.makeText(getApplicationContext(),response.getString("uname") , Toast.LENGTH_SHORT).show();
                    }
                   else{
                        Toast.makeText(getApplicationContext(), "Failed to load Data! Please try again later", Toast.LENGTH_SHORT).show();

                    }


                }catch (JSONException e){
                    e.printStackTrace();

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