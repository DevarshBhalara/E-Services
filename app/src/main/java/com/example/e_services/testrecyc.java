package com.example.e_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class testrecyc extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btn;
    List<SearchModel> s1;
    AdaperSearch adaperSearch;
    int[] a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testrecyc);
        btn=findViewById(R.id.btn);
        recyclerView=findViewById(R.id.recyc);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        lodaList();



    }

    private void lodaList() {


        String s = getResources().getString( R.string.strlink);
        String str = s+"searchWorker.php?worker=plumber&loc=rajkot";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int count = response.getInt("count");

                    s1=new ArrayList<>();
                    a=new int[count];
                    Log.e("count",""+count);
                   for(int i = 0; i< count;i++)
                    {
                        Log.e("id=",""+""+response.getString("wid"+i));
                        s1.add(new SearchModel(response.getString("wid"+i),response.getString("name"+i),response.getString("mobile_no"+i),response.getString("profession"+i)));
                        String st = response.getString("wid"+i)+response.getString("name"+i)+response.getString("mobile_no"+i)+response.getString("profession"+i);
                        Toast.makeText(getApplicationContext(), st  , Toast.LENGTH_SHORT).show();
                        adaperSearch = new AdaperSearch(getApplicationContext(),s1);
                        recyclerView.setAdapter(adaperSearch);
                    }

                    adaperSearch.notifyDataSetChanged();



                } catch (JSONException e) {
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
