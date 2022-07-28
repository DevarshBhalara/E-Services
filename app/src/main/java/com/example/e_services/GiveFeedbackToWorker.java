package com.example.e_services;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class GiveFeedbackToWorker extends AppCompatActivity {
    EditText medReview;
    RatingBar rb1;
    Button btnRev;
    String wid;
    String uid,rating;
    float getrating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback_to_worker);
        medReview = findViewById(R.id.edReview);
        rb1 = findViewById(R.id.ratingBar);
        btnRev = findViewById(R.id.btnAddReview);

        Intent i = getIntent();
        wid = i.getStringExtra("wid");
        rating = i.getStringExtra("rating");
        uid = getSharedPreferences("shared",MODE_PRIVATE).getString("user_id","-1");
        btnRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float getrating2 = rb1.getRating();
                if(!medReview.getText().toString().trim().equals("") || getrating2!=0.0 ){
                    addReview();
                    updateRatingToWorker();
                    //Toast.makeText(getApplicationContext(), "Please Valid", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter Valid Review", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateRatingToWorker() {
        float rat = Float.parseFloat(rating);
        getrating = rb1.getRating();

        float frating = (rat+getrating)/2;
        Log.e("NewRating",""+frating);




        String s = getResources().getString( R.string.strlink);
        String str = s+"updateWorkerRating.php?"+
                "wid="+wid+
                "&rating="+frating;


        Log.e("String",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getString("status").equals("1")){
                        //Toast.makeText(getApplicationContext(), "Rating Updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Toast.makeText(getApplicationContext(), "Rating not Updated", Toast.LENGTH_SHORT).show();
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

    private void addReview() {
        String rev = medReview.getText().toString();
        getrating = rb1.getRating();
        String.valueOf(getrating);
        String s = getResources().getString( R.string.strlink);
        String str = s+"addFeedbackToWorker.php?"+
                "uid="+uid+
                "&wid="+wid+
                "&feedback="+rev+
                "&rating="+getrating;

        Log.e("String",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getString("status").equals("1")){
                        Toast.makeText(getApplicationContext(), "Review Send", Toast.LENGTH_SHORT).show();
                        showDialogYes();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Review Not Send", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(GiveFeedbackToWorker.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.download);
        builder.setMessage("Review Send Successfully!")
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