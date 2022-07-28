package com.example.e_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkerHisortyAdapter extends RecyclerView.Adapter<WorkerHisortyAdapter.ViewHolder> {
    Context ctx;
    LayoutInflater layoutInflater;
    List<WorkerHistoryModel> historyModels;

    public WorkerHisortyAdapter(Context ctx, List<WorkerHistoryModel> historyModels) {
        this.ctx = ctx;
        this.historyModels = historyModels;
    }

    @NonNull
    @Override
    public WorkerHisortyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_history,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerHisortyAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(historyModels.get(position).getUname());
        holder.tvMobile.setText(historyModels.get(position).getU_mono());
        holder.tvDate.setText(historyModels.get(position).getDatework());
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvMobile,tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.username);
            tvMobile = itemView.findViewById(R.id.mobile_number);
            tvDate = itemView.findViewById(R.id.date);



        }
    }
}
