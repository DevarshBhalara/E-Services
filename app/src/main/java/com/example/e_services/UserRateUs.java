package com.example.e_services;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRateUs extends AppCompatActivity {
    EditText edRevRateus;
    RatingBar rbRateus;
    Button btnSubmit;
    float getRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate_us);

        edRevRateus = findViewById(R.id.edReviewRateus);
        rbRateus = findViewById(R.id.ratingBarRateus);
        btnSubmit = findViewById(R.id.btnAddReviewRateus);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRating = rbRateus.getRating();
                if(getRating==0.0){
                    Toast.makeText(getApplicationContext(), "Please Fill rating bar!", Toast.LENGTH_SHORT).show();
                }else{
                    addRev();
                }
            }
        });



    }

    private void addRev() {

        String aa = String.valueOf(getRating);
        String s = getResources().getString( R.string.strlink);
        String str = s+"appFeedback.php?"+
                "id="+getSharedPreferences("shared",MODE_PRIVATE).getString("uniq_id","-1")+
                "&feedback="+edRevRateus.getText().toString()+
                "&rating="+aa;

        Log.e("String",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getString("status").equals("1")){
                        Toast.makeText(getApplicationContext(), "Thank you for your Feedback", Toast.LENGTH_SHORT).show();
                        showDialogYes();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT).show();
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

    private void showDialogYes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserRateUs.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.download);
        builder.setMessage("Thanks for your Feedback!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}