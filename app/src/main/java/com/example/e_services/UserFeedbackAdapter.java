package com.example.e_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class UserFeedbackAdapter extends RecyclerView.Adapter<UserFeedbackAdapter.ViewHolder> {
    Context ctx;
    LayoutInflater layoutInflater;
    List<UserFeedbackModel> userFeedbackModels;

    public UserFeedbackAdapter(Context ctx, List<UserFeedbackModel> userFeedbackModels) {
        this.ctx = ctx;
        this.userFeedbackModels = userFeedbackModels;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_user,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(userFeedbackModels.get(position).getWname());
        holder.tvFeedback.setText(userFeedbackModels.get(position).getFeedback());
        holder.tvRating.setText(userFeedbackModels.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return userFeedbackModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvFeedback,tvRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.workername);
            tvFeedback = itemView.findViewById(R.id.feedback);
            tvRating = itemView.findViewById(R.id.rating);
        }
    }
}
