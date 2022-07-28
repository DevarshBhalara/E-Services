package com.example.e_services;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    List<SearchModel> s1;
    AdaperSearch adaperSearch;
    int[] a;

    RelativeLayout rl2;
    RelativeLayout rl;
    ProgressBar pg;
    TextView t;
    //EditText mtype,mloc;
    int[] a1;
    Spinner mtype,mloc;
    Button btn;
    List<String> list = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.searchfragment,null);

        recyclerView = v.findViewById(R.id.recycofsearch);
        Button btn = v.findViewById(R.id.btn_search);
        mtype = v.findViewById(R.id.search_type);
        mloc = v.findViewById(R.id.search_loc);
        //sp=(Spinner)v.findViewById(R.id.spinner1);


        getWtype();
        //animation
         rl = v.findViewById(R.id.loading);
        pg = v.findViewById(R.id.prog);
         t = v.findViewById(R.id.tv);
         rl2 = v.findViewById(R.id.mainrl);

        //ScrollView scr = v.findViewById(R.id.scroll);
        //scr.fullScroll(View.FOCUS_DOWN);
        //scr.setSmoothScrollingEnabled(true);

        rl.setVisibility(View.VISIBLE);
        pg.setVisibility(View.VISIBLE);
        t.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        defaultList();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mtype.getSelectedItemId()==0)
                {
                    Toast.makeText(getContext(), "Please Select type!", Toast.LENGTH_SHORT).show();
                }else{
                    recyclerView.setAdapter(null);
                    rl.setVisibility(View.VISIBLE);
                    pg.setVisibility(View.VISIBLE);
                    t.setVisibility(View.VISIBLE);

                        loadList();

                }

            }
        });

        return v;
    }

    private void getWtype() {
        String s = getResources().getString( R.string.strlink);
        String str = s+"gettype.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int count = response.getInt("count");

                    s1=new ArrayList<>();
                    a=new int[count];
                    Log.e("count",""+count);
                    list.clear();
                    String f = "Chose a type...";
                    list.add(f);

                    for(int i = 0; i< count;i++)
                    {


                        list.add(response.getString("profession"+i));
                        Log.e("Test",""+response.getString("profession"+i));

                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mtype.setAdapter(dataAdapter);



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

        RequestQueue a = Volley.newRequestQueue(getContext());
        a.add(jsonObjectRequest);
    }

    private void defaultList() {

        String s = getResources().getString( R.string.strlink);
        String str = s+"defaultSearchUser.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                rl.setVisibility(View.INVISIBLE);
                pg.setVisibility(View.INVISIBLE);
                t.setVisibility(View.INVISIBLE);

                try {
                    int count = response.getInt("count");

                    s1=new ArrayList<>();
                    a=new int[count];
                    Log.e("count",""+count);


                    for(int i = 0; i< count;i++)
                    {

                        a[i] =response.getInt("wid"+i);
                        Log.e("Array id",""+a[i]  );
                        Log.e("id=",""+""+response.getString("wid"+i));

                        s1.add(new SearchModel(response.getString("wid"+i),response.getString("name"+i),response.getString("mobile_no"+i),response.getString("profession"+i)));

                        //String st = response.getString("wid"+i)+response.getString("name"+i)+response.getString("mobile_no"+i)+response.getString("profession"+i);
                        //Toast.makeText(getContext(), st  , Toast.LENGTH_SHORT).show();

                        adaperSearch = new AdaperSearch(getContext(),s1);
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

        RequestQueue a = Volley.newRequestQueue(getContext());
        a.add(jsonObjectRequest);
    }

    private void loadList() {

        String s = getResources().getString( R.string.strlink);
        String str = s+"searchWorker.php?"+
                    "worker="+mtype.getSelectedItem().toString()+
                    "&loc="+((mloc.getSelectedItemId()==0)?" ":mloc.getSelectedItem().toString());
        Log.e("link",""+str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                rl.setVisibility(View.INVISIBLE);
                pg.setVisibility(View.INVISIBLE);
                t.setVisibility(View.INVISIBLE);
                SearchModel searchModel = new SearchModel();
                try {
                    int count = response.getInt("count");

                    s1=new ArrayList<>();
                    a=new int[count];
                    Log.e("count",""+count);
                    for(int i = 0; i< count;i++)
                    {
                        Log.e("id=",""+""+response.getString("wid"+i));
                        s1.add(new SearchModel(response.getString("wid"+i),response.getString("name"+i),response.getString("mobile_no"+i),response.getString("profession"+i)));
                        //String st = response.getString("wid"+i)+response.getString("name"+i)+response.getString("mobile_no"+i)+response.getString("profession"+i);
                        //Toast.makeText(getContext(), st  , Toast.LENGTH_SHORT).show();
                        adaperSearch = new AdaperSearch(getContext(),s1);
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

        RequestQueue a = Volley.newRequestQueue(getContext());
        a.add(jsonObjectRequest);
    }
}
