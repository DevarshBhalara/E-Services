package com.example.e_services;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class registration_worker extends AppCompatActivity {

    Button btn;
    EditText wname,wnumber,wpass,wconf_pass,waddress,wcity,wmail,wprofession,wdob;
    TextInputLayout t;
    RadioButton w_male,w_female;
    Spinner spinner1;
    String prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_worker);
        wname=findViewById(R.id.ed_uname_worker);
        wnumber=findViewById(R.id.ed_mobile_number_worker);
        wpass=findViewById(R.id.ed_password_worker);
        wconf_pass=findViewById(R.id.ed_confo_password_worker);
        waddress=findViewById(R.id.ed_address_worker);
        wcity=findViewById(R.id.ed_city_worker);
        wmail=findViewById(R.id.ed_email_worker);
        wprofession=findViewById(R.id.profession);
        wdob=findViewById(R.id.ed_dob_worker);
        w_male=findViewById(R.id.male_worker);

        w_female=findViewById(R.id.female_worker);
        spinner1 = findViewById(R.id.spinner1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==4)
                {
                    wprofession.setVisibility(View.VISIBLE);

                }else
                {
                    wprofession.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        wdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(registration_worker.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String str = year+"-"+(month+1)+"-"+dayOfMonth;
                            wdob.setText(str);

                            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        }
                    }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                    wdob.clearFocus();
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void insert_worker(View view) {
        if(wname.getText().toString().equals("") && wnumber.getText().toString().equals("") && wpass.getText().toString().equals("") && wconf_pass.getText().toString().equals("") &&
        wmail.getText().toString().equals("")  && wdob.getText().toString().equals("") && waddress.getText().toString().equals("") &&
        wcity.getText().toString().equals("")  ){
            Toast.makeText(getApplicationContext(), "Enter Values in All Fields", Toast.LENGTH_SHORT).show();
        }
        else {
            if(wpass.getText().toString().equals(wconf_pass.getText().toString())){
                prof= (String)spinner1.getSelectedItem();
                if(prof.equals("Other"))
                {
                    prof = wprofession.getText().toString();
                    new_insert_worker();
                    //Toast.makeText(getApplicationContext(), prof,    Toast.LENGTH_SHORT).show();
                }else
                {
                    new_insert_worker();
                    //Toast.makeText(getApplicationContext(), prof, Toast.LENGTH_SHORT).show();
                }
               // new_insert_worker();
            }
            else{
                Toast.makeText(getApplicationContext(), "Passwords are different in Both Fileds!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void new_insert_worker() {
        String s1 = String.valueOf(spinner1.getSelectedItem());
        String s = getResources().getString( R.string.strlink);
        String str = s+"registration_user_worker.php?"+
                "wname="+wname.getText().toString().trim()+
                "&wmobile_no="+wnumber.getText().toString().trim()+
                "&wpass="+wpass.getText().toString().trim()+
                "&waddress="+waddress.getText().toString().trim()+
                "&wcity="+wcity.getText().toString().trim()+
                "&wgender="+(w_male.isChecked()?'M':'F')+
                "&wmail="+wmail.getText().toString().trim()+
                "&wprofession="+prof+
                "&wdob="+wdob.getText().toString().trim();

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
                        Intent i = new Intent(registration_worker.this, Login.class);
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