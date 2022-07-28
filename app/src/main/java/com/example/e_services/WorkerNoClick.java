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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkerNoClick extends AppCompatActivity {
    SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private static final int SMS_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_no_click);

        Intent i = getIntent();
        String total = i.getStringExtra("total");
        String mono = i.getStringExtra("mono");

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        updateStatusNo(total);
        SendMessage(mono, "Sorry i am not available today");

        if (ActivityCompat.checkSelfPermission(WorkerNoClick.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(WorkerNoClick.this, Manifest.permission.SEND_SMS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerNoClick.this);
                builder.setTitle("Need SMS Permission");
                builder.setMessage("This app needs SMS permission to send Messages.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(WorkerNoClick.this,
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
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerNoClick.this);
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
                ActivityCompat.requestPermissions(WorkerNoClick.this, new String[]{Manifest.permission.SEND_SMS}
                        , SMS_PERMISSION_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.SEND_SMS, true);
            editor.commit();

        }

    }

    private void updateStatusNo(String total) {
        String s = getResources().getString( R.string.strlink);
        String str = s+"updateWorkerStatusNo.php?"+
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

    public void SendMessage(String strMobileNo, String strMessage) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null);
            Toast.makeText(getApplicationContext(), "Your Message Sent",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(),WorkerDashboard.class);
            startActivity(i);
            finish();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

}
