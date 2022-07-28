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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WorkerFeedbackFragment extends Fragment {
    RecyclerView recyclerView;
    List<UserFeedbackModel> userFeedbackModels;
    WorkerFeedbackAdapter workerFeedbackAdapter;

    int[] a;
    String id;

    TextView tv1;
    ProgressBar progressBar;
    TextView mtvL;
    RelativeLayout relativeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.worker_feedback_fragment,null);

        recyclerView = v.findViewById(R.id.recycoffeedbackworker);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tv1 = v.findViewById(R.id.tvNoFeedback);

        relativeLayout = v.findViewById(R.id.rlLoadingFeedbackW);
        mtvL = v.findViewById(R.id.tvNewFeedbackW);
        progressBar = v.findViewById(R.id.progFeedbackW);

        id = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE).getString("user_id","-1");

        loadFeedback();
        return v;


    }

    private void loadFeedback() {

        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mtvL.setVisibility(View.VISIBLE);

        String s = getResources().getString( R.string.strlink);
        String str = s+"viewWorkerFeedback.php?wid="+id;
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
                        userFeedbackModels = new ArrayList<>();
                        a = new int[count];
                        Log.e("count", "" + count);

                        for(int i=0;i<count;i++){

                            userFeedbackModels.add(new UserFeedbackModel(response.getString("uname"+i),response.getString("feedback"+i),response.getString("rating"+i)));

                            workerFeedbackAdapter = new WorkerFeedbackAdapter(getContext(),userFeedbackModels);
                            recyclerView.setAdapter(workerFeedbackAdapter);


                        }
                        workerFeedbackAdapter.notifyDataSetChanged();
                    }else{
                        tv1.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "NO feedback available", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Some Internal Error Occurred", Toast.LENGTH_SHORT).show();
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
