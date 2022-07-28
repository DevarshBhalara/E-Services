package com.example.e_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkerProfileReviewAdapter extends RecyclerView.Adapter<WorkerProfileReviewAdapter.ViewHolder> {
    Context ctx;
    LayoutInflater layoutInflater;
    List<WorkerProfileReviewModel> workerProfileReviewModelList;

    public WorkerProfileReviewAdapter(Context ctx, List<WorkerProfileReviewModel> workerProfileReviewModelList) {
        this.ctx = ctx;
        this.workerProfileReviewModelList = workerProfileReviewModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_profile_feedback_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvuname.setText(workerProfileReviewModelList.get(position).getUname());
        holder.tvreview.setText(workerProfileReviewModelList.get(position).getFeedback());
        holder.tvrating.setText(workerProfileReviewModelList.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return workerProfileReviewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvuname,tvreview,tvrating;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            tvuname = itemView.findViewById(R.id.tvUserName);
            tvreview = itemView.findViewById(R.id.tvReviewUser);
            tvrating = itemView.findViewById(R.id.tvRatingUser);
        }
    }
}
