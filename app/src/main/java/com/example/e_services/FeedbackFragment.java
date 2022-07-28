package com.example.e_services;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class FeedbackFragment extends Fragment {
    RecyclerView recyclerView;
    TextView mtvF;
    List<UserFeedbackModel> userFeedbackModels;
    UserFeedbackAdapter userFeedbackAdapter;

    int[] a;
    String id;

    ProgressBar progressBar;
    TextView mtvL;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feedbackfragment,null);

        recyclerView = v.findViewById(R.id.recycoffeedback);
        mtvF = v.findViewById(R.id.tvNoF);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        id  = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE).getString("user_id","-1");

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.refreshLayoutHome);

        relativeLayout = v.findViewById(R.id.rlLoadingFeedback);
        mtvL = v.findViewById(R.id.tvFeedback);
        progressBar = v.findViewById(R.id.progFeedback);

        loadFeedback();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFeedback();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        return v;
    }

    private void loadFeedback() {
        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mtvL.setVisibility(View.VISIBLE);


        String s = getResources().getString( R.string.strlink);
        String str = s+"userViewFeedback.php?uid="+id;
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
                        mtvF.setVisibility(View.INVISIBLE);
                        userFeedbackModels = new ArrayList<>();
                        a = new int[count];
                        Log.e("count", "" + count);

                        for(int i=0;i<count;i++){

                            userFeedbackModels.add(new UserFeedbackModel(response.getString("wid"+i),response.getString("wname"+i),response.getString("uname"+i),response.getString("feedback"+i),response.getString("rating"+i)));

                            userFeedbackAdapter = new UserFeedbackAdapter(getContext(),userFeedbackModels);
                            recyclerView.setAdapter(userFeedbackAdapter);


                        }
                        userFeedbackAdapter.notifyDataSetChanged();
                    }else{
                        mtvF.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "NO feedback available", Toast.LENGTH_SHORT).show();
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
