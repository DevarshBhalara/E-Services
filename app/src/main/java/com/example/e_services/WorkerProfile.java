package com.example.e_services;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.List;

public class WorkerProfile extends AppCompatActivity {
    TextView mworker_name,mworker_mono,mworker_address,mworker_city,mworker_rating,mworker_mail,mworker_profession;
    //RecyclerView recyclerView;
    String wid,mono;

    TextView mtvNoRev;
    RelativeLayout loadin_layout;
    ProgressBar loading_progressbar;
    TextView loading_textview;
    Button mbtnBook,mbtnCall;
    DatePicker datePicker;
    String strDate;
    RecyclerView recyclerView;
    CardView c;
    List<WorkerProfileReviewModel> workerProfileReviewModelList;
    WorkerProfileReviewAdapter workerProfileReviewAdapter;
    int[] a;

    SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private static final int SMS_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        c= findViewById(R.id.worker_details_card2);
        mtvNoRev = findViewById(R.id.tvNoRev);

        mworker_name=findViewById(R.id.worker_name_data);
        mworker_mono=findViewById(R.id.worker_mono_data);
        mworker_address=findViewById(R.id.worker_add_data);
        mworker_city=findViewById(R.id.worker_data_city);
        mworker_rating=findViewById(R.id.worker_data_rating);
        //mworker_mail=findViewById(R.id.worker_data_email);
        mworker_profession=findViewById(R.id.profession_in_profile2);
        mbtnBook=findViewById(R.id.btnBook);
        loadin_layout = findViewById(R.id.loading);
        loading_progressbar = findViewById(R.id.prog);
        loading_textview = findViewById(R.id.tv);
        mbtnCall = findViewById(R.id.btnCall);
        recyclerView = findViewById(R.id.recycrev);

        getSupportActionBar().hide();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent i = getIntent();
        wid =i.getStringExtra("wid");
        //Toast.makeText(getApplicationContext(), "From Worker Profile"+id, Toast.LENGTH_SHORT).show();
        loadin_layout.setVisibility(View.VISIBLE);
        loading_progressbar.setVisibility(View.VISIBLE);
        loading_textview.setVisibility(View.VISIBLE);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        loadData();

        if (ActivityCompat.checkSelfPermission(WorkerProfile.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(WorkerProfile.this, Manifest.permission.SEND_SMS)) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(WorkerProfile.this);
                builder.setTitle("Need SMS Permission");
                builder.setMessage("This app needs SMS permission to send Messages.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(WorkerProfile.this,
                                new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.SEND_SMS, false)) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(WorkerProfile.this);
                builder.setTitle("Need SMS Permission");
                builder.setMessage("This app needs SMS permission to send Messages.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(),
                                "Go to Permissions to Grant SMS permissions", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(WorkerProfile.this, new String[]{Manifest.permission.SEND_SMS}
                        , SMS_PERMISSION_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.SEND_SMS, true);
            editor.commit();

        }



        mbtnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerProfile.this);
                View vv = LayoutInflater.from(WorkerProfile.this)
                        .inflate(R.layout.date_time, null);
                datePicker = (DatePicker) vv.findViewById(R.id.datetime);
                builder.setView(vv);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Select Date!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                strDate = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                                //Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();
                                book();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void book() {
        String s = getResources().getString( R.string.strlink);
        String str = s+"bookWorker.php?wid="+wid+
                "&uid="+getSharedPreferences("shared",MODE_PRIVATE).getString("user_id","-1")+
                "&wname="+mworker_name.getText().toString()+
                "&uname="+getSharedPreferences("shared",MODE_PRIVATE).getString("user_name","-1")+
                "&profession="+mworker_profession.getText().toString()+
                "&datework="+strDate+
                "&mobile_number="+getSharedPreferences("shared",MODE_PRIVATE).getString("user_mobile_number","-1");

        Log.e("str",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                try{
                    if(response.getString("status").equals("1")){
                        SendMessage(mworker_mono.getText().toString().trim(),"New customer available!Please Check Application for more Detail");
                        Toast.makeText(getApplicationContext(), "Booked", Toast.LENGTH_SHORT).show();

                    }else if(response.getString("status").equals("0")){
                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                    }


                }catch (JSONException e){


                    Toast.makeText(getApplicationContext(), "Something Went Wrong!Try again later", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong!Try again later", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestQueue a = Volley.newRequestQueue(this);
        a.add(jsonObjectRequest);

    }

    public void SendMessage(String strMobileNo, String strMessage) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null);
            Toast.makeText(getApplicationContext(), "Your Message Sent", Toast.LENGTH_LONG).show();
            showd();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void showd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkerProfile.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Your Message has been Sent!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData() {

        String s = getResources().getString( R.string.strlink);
        String str = s+"loadWorkerProfile.php?wid="+wid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(JSONObject response){
                try{
                    if(response.getString("status").equals("1")){
                        loadin_layout.setVisibility(View.INVISIBLE);
                        loading_progressbar.setVisibility(View.INVISIBLE);
                        loading_textview.setVisibility(View.INVISIBLE);


                        //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        mworker_name.setText(response.getString("name"));
                        mworker_mono.setText(response.getString("mobile_no"));
                        mworker_address.setText(response.getString("address"));
                        mworker_profession.setText(response.getString("profession"));
                        mworker_city.setText(response.getString("city"));

                       /* if(response.getString("email").trim().equals("null")){

                        }else{
                            mworker_mail.setText(response.getString("email"));
                        }*/
                        //mworker_mail.setText(response.getString("email"));
                        if(response.getString("rating").trim().equals("0")){

                        }else
                        {
                            String st = response.getString("rating");
                            float rat = Float.parseFloat(st);
                            mworker_rating.setText(String.format("%.1f",rat));
                        }

                        //mworker_rating.setText(response.getString("rating"));

                        loadReview();

                        mbtnCall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    mono = "tel:"+response.getString("mobile_no").trim();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse(mono));
                                startActivity(intent);
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), "Profile Doesn't Exists", Toast.LENGTH_SHORT).show();
                    }


                }catch (JSONException e){
                    loadin_layout.setVisibility(View.INVISIBLE);
                    loading_progressbar.setVisibility(View.INVISIBLE);
                    loading_textview.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Something Went Wrong!Try again later", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong!Try again later", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestQueue a = Volley.newRequestQueue(this);
        a.add(jsonObjectRequest);

    }

    private void loadReview() {
        String s = getResources().getString( R.string.strlink);
        String str = s+"workerProfileLoadReview.php?wid="+wid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(JSONObject response){
                try{
                    int count = response.getInt("count");
                    if(count>=1){
                        workerProfileReviewModelList = new ArrayList<>();
                        a = new int[count];

                        for(int i=0;i<count;i++){

                            workerProfileReviewModelList.add(new WorkerProfileReviewModel(response.getString("uname"+i),response.getString("feedback"+i),response.getString("rating"+i)));

                            workerProfileReviewAdapter = new WorkerProfileReviewAdapter(getApplicationContext(),workerProfileReviewModelList);
                            recyclerView.setAdapter(workerProfileReviewAdapter);
                        }
                        workerProfileReviewAdapter.notifyDataSetChanged();

                    }else{
                        //c.setMinimumHeight(10);
                        c.setVisibility(View.INVISIBLE);
                        mtvNoRev.setVisibility(View.VISIBLE);
                        //Toast.makeText(getApplicationContext(), "No Feedback Availble", Toast.LENGTH_SHORT).show();
                    }



                }catch (JSONException e){
                    loadin_layout.setVisibility(View.INVISIBLE);
                    loading_progressbar.setVisibility(View.INVISIBLE);
                    loading_textview.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Something Went Wrong!Try again later", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong!Try again later", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestQueue a = Volley.newRequestQueue(this);
        a.add(jsonObjectRequest);


    }

}
