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



public class WorkerNewBookFragment extends Fragment {
    RecyclerView recyclerView;
    List<WorkerNewBookModel> workerNewBookModels;
    WorkerNewBookAdapter workerNewBookAdapter;
    int[] a;
    String id;

    TextView tv1;

    ProgressBar progressBar;
    TextView mtvL;
    RelativeLayout relativeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.worker_newbook_fragment,null);

        recyclerView=v.findViewById(R.id.recycleBook);
        tv1 = v.findViewById(R.id.tvNoNew);

        relativeLayout = v.findViewById(R.id.rlLoadingNewbook);
        mtvL = v.findViewById(R.id.tvNewbook);
        progressBar = v.findViewById(R.id.progNewbook);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.refreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        id = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE).getString("user_id","-1");
        newBook();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(null);
                newBook();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return v;
    }

    private void newBook() {

        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mtvL.setVisibility(View.VISIBLE);

        String s = getResources().getString( R.string.strlink);
        String str = s+"newBookWorker.php?wid="+id;
        //Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mtvL.setVisibility(View.INVISIBLE);

                    int count = response.getInt("count");
                    if(count>=1) {
                        tv1.setVisibility(View.INVISIBLE);
                        workerNewBookModels = new ArrayList<>();
                        a = new int[count];
                        Log.e("count", "" + count);


                        for (int i = 0; i < count; i++) {

                            a[i] = response.getInt("wid" + i);
                            Log.e("Array id", "" + a[i]);
                            Log.e("id=", "" + "" + response.getString("wid" + i));

                            workerNewBookModels.add(new WorkerNewBookModel(response.getString("wid" + i), response.getString("uid" + i), response.getString("wname" + i), response.getString("uname" + i), response.getString("umobile_number" + i), response.getString("datework" + i),response.getString("total"+i),response.getString("uaddress"+i)));

                            //String st = response.getString("wid"+i)+response.getString("name"+i)+response.getString("mobile_no"+i)+response.getString("profession"+i);
                            //Toast.makeText(getContext(), st  , Toast.LENGTH_SHORT).show();

                            workerNewBookAdapter = new WorkerNewBookAdapter(getContext(), workerNewBookModels);
                            recyclerView.setAdapter(workerNewBookAdapter);
                        }

                        workerNewBookAdapter.notifyDataSetChanged();
                    }else{
                        tv1.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "No one booked you", Toast.LENGTH_SHORT).show();
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
