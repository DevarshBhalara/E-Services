package com.example.e_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Stack;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText uname,unumber,upass,uconf_pass,uaddress,ucity,umail;
    RadioButton r_male,r_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.register_btn);
        uname=findViewById(R.id.ed_uname);
        unumber=findViewById(R.id.ed_mobile_number);
        upass=findViewById(R.id.ed_password);
        uconf_pass=findViewById(R.id.ed_confo_password);
        uaddress=findViewById(R.id.ed_address);
        umail=findViewById(R.id.ed_email);

        ucity=findViewById(R.id.ed_city);
        r_male=findViewById(R.id.male);
        r_female=findViewById(R.id.female);
    }

    public void insert_user(View view) {

        if(uname.getText().toString().equals("") || unumber.getText().toString().equals("") || upass.getText().toString().equals("") ||
            uaddress.getText().toString().equals("") || umail.getText().toString().equals("") )
        {
            Toast.makeText(getApplicationContext(), "Enter Values in All Fields!", Toast.LENGTH_SHORT).show();
        }else {

            if(upass.getText().toString().equals(uconf_pass.getText().toString())){
                new_insert();
            }
            else{
                Toast.makeText(getApplicationContext(), "Passwords are different in Both Fileds!", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void new_insert() {
        String s = getResources().getString( R.string.strlink);
        String str = s+"registration_user.php?"+
                    "name="+uname.getText().toString().trim()+
                    "&mobile_no="+unumber.getText().toString().trim()+
                    "&pass="+upass.getText().toString().trim()+
                    "&address="+uaddress.getText().toString().trim()+
                    "&city="+ucity.getText().toString().trim()+
                    "&gender="+(r_male.isChecked()?'M':'F')+
                    "&mail="+umail.getText().toString().trim();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getString("status").equals("2")){
                        Toast.makeText(getApplicationContext(), "This Email Address is Already in Use", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("0")){
                        Toast.makeText(getApplicationContext(), "Fail to Insert", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3")){
                        Toast.makeText(getApplicationContext(), "Fail t0 insert in Login", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("1")){
                        Toast.makeText(getApplicationContext(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, Login.class);
                        startActivity(i);
                        finish();
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