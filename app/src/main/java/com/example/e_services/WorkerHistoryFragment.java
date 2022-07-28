package com.example.e_services;

import android.annotation.SuppressLint;
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

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class WorkerHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    TextView mtvYes;
    Switch mSwitch;
    List<WorkerHistoryModel> historyModelList;
    WorkerHisortyAdapter workerHisortyAdapter;
    int[] a;
    String id;
    TextView tv1;

    ProgressBar progressBar;
    TextView mtvL;
    RelativeLayout relativeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.worker_history_fragment,null);
        mtvYes = v.findViewById(R.id.tvYes);
        mSwitch  = v.findViewById(R.id.switchHistory);
        recyclerView = v.findViewById(R.id.recycleHistory);
        tv1 = v.findViewById(R.id.tvNoHis);

        relativeLayout = v.findViewById(R.id.rlLoadingHistoryW);
        mtvL = v.findViewById(R.id.tvNewHistoryW);
        progressBar = v.findViewById(R.id.progHistoryW);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        id = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE).getString("user_id","-1");

        historyReject();



        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    recyclerView.setAdapter(null);
                    mtvYes.setText("Accepted");
                    Toast.makeText(getContext(), "Accepted Users", Toast.LENGTH_SHORT).show();
                    history();
                }else{
                    recyclerView.setAdapter(null);
                    mtvYes.setText("Unaccepted");
                    Toast.makeText(getContext(), "Unaccepted User", Toast.LENGTH_SHORT).show();
                    historyReject();
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
        String str = s+"historyReject.php?wid="+id;

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
                        tv1.setVisibility(View.INVISIBLE);
                        historyModelList = new ArrayList<>();
                        a = new int[count];
                        Log.e("count", "" + count);

                        for(int i=0;i<count;i++){

                            historyModelList.add(new WorkerHistoryModel( response.getString("wname"+i),response.getString("uname"+i),response.getString("datebook"+i),response.getString("mono"+i)));

                            workerHisortyAdapter = new WorkerHisortyAdapter(getContext(),historyModelList);
                            recyclerView.setAdapter(workerHisortyAdapter);

                        }
                        workerHisortyAdapter.notifyDataSetChanged();
                    }else{
                        tv1.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "NO history available", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Some Internal Error Occurred!", Toast.LENGTH_SHORT).show();
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
        String str = s+"history.php?wid="+id;
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
                        tv1.setVisibility(View.INVISIBLE);
                        historyModelList = new ArrayList<>();
                        a = new int[count];
                        Log.e("count", "" + count);

                        for(int i=0;i<count;i++){

                            historyModelList.add(new WorkerHistoryModel(response.getString("wname"+i),response.getString("uname"+i),response.getString("datebook"+i),response.getString("mono"+i)));

                            workerHisortyAdapter = new WorkerHisortyAdapter(getContext(),historyModelList);
                            recyclerView.setAdapter(workerHisortyAdapter);

                        }
                        workerHisortyAdapter.notifyDataSetChanged();
                    }else{
                        tv1.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "NO history available", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Some Internal Error Occurred!", Toast.LENGTH_SHORT).show();
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
