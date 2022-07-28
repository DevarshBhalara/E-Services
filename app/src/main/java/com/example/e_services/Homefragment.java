package com.example.e_services;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class Homefragment extends Fragment {
    TextView mtv1,mNo;
    Switch sw1;
    RecyclerView recyclerView;

    List<UserHistoryModel> userHistoryModels;
    UserHistoryAdapter userHistoryAdapter;
    UserHistoryAdapterNo userHistoryAdapterNo;
    int[] a;
    String id;

    ProgressBar progressBar;
    TextView mtvL;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.homefragment,null);
        mtv1 = v.findViewById(R.id.tvYes);
        sw1 = v.findViewById(R.id.switchHistory);
        recyclerView = v.findViewById(R.id.recycofhome);
        mNo = v.findViewById(R.id.tvNo);

        relativeLayout = v.findViewById(R.id.rlLoadingHistory);
        mtvL = v.findViewById(R.id.tvHistory);
        progressBar = v.findViewById(R.id.progHistory);

        //SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.refreshLayoutHome);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        id  = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE).getString("user_id","-1");

        history();

        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){

                            recyclerView.setAdapter(null);
                            mtv1.setText("Unaccepted");
                            historyReject();

                        }else{
                            recyclerView.setAdapter(null);
                            mtv1.setText("Accepted");
                            history();

                        }
                    }
                });
                swipeRefreshLayout.setRefreshing(false);

            }
        });*/
        
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    recyclerView.setAdapter(null);
                    mtv1.setText("Unaccepted");
                    historyReject();

                }else{

                    recyclerView.setAdapter(null);
                    mtv1.setText("Accepted");
                    history();

                }
            }
        });
        
        

        return v;
    }

    private void historyReject() {
        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mtvL.setVisibility(View.VISIBLE);

        String s = getResources().getString( R.string.strlink);
        String str = s+"userHistoryReject.php?uid="+id;
        Log.e("string",""+str);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);

                    int count = response.getInt("count");
                    if(count>=1){
                        mNo.setVisibility(View.INVISIBLE);
                        userHistoryModels = new ArrayList<>();
                        a = new int[count];
                        Log.e("count", "" + count);

                        for(int i=0;i<count;i++){

                            userHistoryModels.add(new UserHistoryModel(response.getString("wname"+i),response.getString("uname"+i),response.getString("mono"+i),response.getString("datebook"+i),response.getString("wid"+i)));

                            userHistoryAdapterNo = new UserHistoryAdapterNo(getContext(),userHistoryModels);
                            recyclerView.setAdapter(userHistoryAdapterNo);


                        }
                        userHistoryAdapterNo.notifyDataSetChanged();
                        //Toast.makeText(getContext(), "Rejected Requests", Toast.LENGTH_SHORT).show();
                    }else{
                        mNo.setVisibility(View.VISIBLE);

                        //Toast.makeText(getContext(), "Sorry! No history available", Toast.LENGTH_SHORT).show();
                    }

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

    private void history() {
        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mtvL.setVisibility(View.VISIBLE);

        String s = getResources().getString( R.string.strlink);
        String str = s+"userHistoryYes.php?uid="+id;
        Log.e("string",""+str);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);

                    int count = response.getInt("count");
                    if(count>=1){
                        mNo.setVisibility(View.INVISIBLE);
                        userHistoryModels = new ArrayList<>();
                        a = new int[count];
                        Log.e("count", "" + count);

                        for(int i=0;i<count;i++){

                            userHistoryModels.add(new UserHistoryModel(response.getString("wname"+i),response.getString("uname"+i),response.getString("mono"+i),response.getString("datebook"+i),response.getString("wid"+i),response.getString("rating"+i)));

                            userHistoryAdapter = new UserHistoryAdapter(getContext(),userHistoryModels);
                            recyclerView.setAdapter(userHistoryAdapter);
                            Log.e("Rating",""+response.getString("rating"+i));

                        }
                        userHistoryAdapter.notifyDataSetChanged();
                        //Toast.makeText(getContext(), "Accepted Requests", Toast.LENGTH_SHORT).show();
                    }else{
                        mNo.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "Sorry! No history available", Toast.LENGTH_SHORT).show();
                    }

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
