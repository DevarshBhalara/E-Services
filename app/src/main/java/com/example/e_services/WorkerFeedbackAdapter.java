package com.example.e_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkerFeedbackAdapter extends RecyclerView.Adapter<WorkerFeedbackAdapter.ViewHolder> {
    Context ctx;
    LayoutInflater layoutInflater;
    List<UserFeedbackModel> userFeedbackModelList;

    public WorkerFeedbackAdapter(Context ctx, List<UserFeedbackModel> userFeedbackModelList) {
        this.ctx = ctx;
        this.userFeedbackModelList = userFeedbackModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_view_feedback,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.uname.setText(userFeedbackModelList.get(position).getUname());
        holder.feeback.setText(userFeedbackModelList.get(position).getFeedback());
        holder.rating.setText(userFeedbackModelList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return userFeedbackModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView uname,feeback,rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            uname = itemView.findViewById(R.id.username);
            feeback = itemView.findViewById(R.id.feedback);
            rating = itemView.findViewById(R.id.rating);

        }
    }
}
