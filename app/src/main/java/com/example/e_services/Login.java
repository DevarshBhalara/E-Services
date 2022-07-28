package com.example.e_services;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Login extends AppCompatActivity {
    EditText ed_email,ed_password;
    Button btn_signup,btn_login;
    TextView tv_forgot_password;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (!getSharedPreferences("shared",MODE_PRIVATE).getString("user_name","-1").equals("-1")){

            if(getSharedPreferences("shared",MODE_PRIVATE).getString("user_role","-1").equals("U"))
            {
                Intent i = new Intent(getApplicationContext(),UserDashboard.class);
                startActivity(i);
                finish();
            }else if(getSharedPreferences("shared",MODE_PRIVATE).getString("user_role","-1").equals("W"))
            {
                Intent i = new Intent(getApplicationContext(),WorkerDashboard.class);
                startActivity(i);
                finish();
            }


        }


        ed_email=findViewById(R.id.ed_loginemail);
        ed_password=findViewById(R.id.ed_loginpassword);
        btn_signup=findViewById(R.id.btn_signup);
        btn_login=findViewById(R.id.btn_login);
        tv_forgot_password=findViewById(R.id.tv_login_forgotpassword);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);


        getSupportActionBar().hide();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FisrtPage.class );
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_email.getText().toString().equals("") && ed_password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter Your Email ID and Password", Toast.LENGTH_SHORT).show();
                }
                else{

                    mProgressBar.setVisibility(View.VISIBLE);
                    login();
                }
            }
        });



    }

    private void login() {
        String s = getResources().getString( R.string.strlink);
        String str = s+"login.php?"+
                "id="+ed_email.getText().toString()+
                "&password="+ed_password.getText().toString();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    mProgressBar.setVisibility(View.INVISIBLE);
                    if(response.getString("status").equals("1")){
                        //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        if(response.getString("role").equals("U"))
                        {
                            //Toast.makeText(getApplicationContext(), "Welcome User", Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPreferences = getSharedPreferences( "shared",
                                    MODE_PRIVATE );
                            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor myEdit = sharedPreferences.edit();



                            myEdit.putString("uniq_id",response.getString("uniq_id")); //login table id
                            myEdit.putString("user_id",response.getString("user_id")); //user table id
                            myEdit.putString("user_name",response.getString("name"));
                            myEdit.putString("user_email",response.getString("email"));
                            myEdit.putString("user_mobile_number",response.getString("mobile_number"));
                            myEdit.putString("user_password",response.getString("password"));
                            myEdit.putString("user_role",response.getString("role"));
                            myEdit.apply();

                            Log.e("uniq_id",response.getString("uniq_id"));
                            Log.e("user_id",response.getString("user_id"));
                            Log.e("user_name",response.getString("name"));
                            Log.e("user_email",response.getString("email"));
                            Log.e("user_mobilenumber",response.getString("mobile_number"));
                            Log.e("user_password",response.getString("password"));
                            Log.e("user_role",response.getString("role"));

                            Intent i = new Intent(getApplicationContext(), UserDashboard.class);
                            String name = response.getString("name");
                            i.putExtra("n", name);
                            startActivity(i);
                            finish();



                        }
                        else if(response.getString("role").equals("W"))
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences( "shared",
                                    MODE_PRIVATE );
                            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            myEdit.putString("uniq_id",response.getString("uniq_id"));
                            myEdit.putString("user_id",response.getString("user_id"));
                            myEdit.putString("user_name",response.getString("name"));
                            myEdit.putString("user_email",response.getString("email"));
                            myEdit.putString("user_mobile_number",response.getString("mobile_number"));
                            myEdit.putString("user_password",response.getString("password"));
                            myEdit.putString("user_role",response.getString("role"));
                            myEdit.apply();

                            Log.e("uniq_id",response.getString("uniq_id"));
                            Log.e("user_id",response.getString("user_id"));
                            Log.e("user_name",response.getString("name"));
                            Log.e("user_email",response.getString("email"));
                            Log.e("user_mobilenumber",response.getString("mobile_number"));
                            Log.e("user_password",response.getString("password"));
                            Log.e("user_role",response.getString("role"));

                            Intent i = new Intent(getApplicationContext(),WorkerDashboard.class);
                            String name = response.getString("name");
                            i.putExtra("n", name);
                            startActivity(i);
                            finish();

                           // Toast.makeText(getApplicationContext(), "Welcome Worker", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Not proper response", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else if(response.getString("status").equals("0")){
                        Toast.makeText(getApplicationContext(), "Email or Password Wrong", Toast.LENGTH_SHORT).show();
                    }



                }catch (JSONException e){
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.INVISIBLE);
                error.printStackTrace();
            }
        });

        RequestQueue a = Volley.newRequestQueue(this);
        a.add(jsonObjectRequest);

    }
}