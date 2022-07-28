package com.example.e_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkerYesClick extends AppCompatActivity {
    EditText edMsg;
    Button btnSend;

    SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private static final int SMS_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_yes_click);
        Intent i = getIntent();

        String uid = i.getStringExtra("uid");
        String wid = i.getStringExtra("wid");
        String date_c = i.getStringExtra("date_c");
        String mono = i.getStringExtra("mono");
        String uname = i.getStringExtra("uname");
        String wname = i.getStringExtra("wname");
        String total = i.getStringExtra("total");
        //Toast.makeText(getApplicationContext(), total, Toast.LENGTH_SHORT).show();
        edMsg = findViewById(R.id.ed_timing);
        btnSend = findViewById(R.id.btnSend);


        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);


        getSupportActionBar().hide();
        //Toast.makeText( this ,uid , Toast.LENGTH_SHORT).show();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(WorkerYesClick.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Are you Sure?")
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String msg = edMsg.getText().toString();
                                SendMessage(mono, msg);
                                updateStatus(uid,wid,date_c,mono,msg,uname,wname);
                                updateStatusInTable(total);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();



            }
        });


        if (ActivityCompat.checkSelfPermission(WorkerYesClick.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(WorkerYesClick.this, Manifest.permission.SEND_SMS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerYesClick.this);
                builder.setTitle("Need SMS Permission");
                builder.setMessage("This app needs SMS permission to send Messages.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(WorkerYesClick.this,
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
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerYesClick.this);
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
                ActivityCompat.requestPermissions(WorkerYesClick.this, new String[]{Manifest.permission.SEND_SMS}
                        , SMS_PERMISSION_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.SEND_SMS, true);
            editor.commit();

        }
    }

    private void updateStatusInTable(String total) {
        String s = getResources().getString( R.string.strlink);
        String str = s+"updateWorkerStatus.php?"+
                "total="+total;
        Log.e("String",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getString("status").equals("1")){
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
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

    public void updateStatus(String uid,String wid,String date_c,String mono,String msg,String uname,String wname) {

        String s = getResources().getString( R.string.strlink);
        String str = s+"book_conform.php?"+
                "wid="+wid+
                "&uid="+uid+
                "&date_c="+date_c+
                "&msg="+msg+
                "&uname="+uname+
                "&wname="+wname;
        Log.e("String",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getString("status").equals("1")){
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();
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

    public void SendMessage(String strMobileNo, String strMessage) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null);
            Toast.makeText(getApplicationContext(), "Your Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
