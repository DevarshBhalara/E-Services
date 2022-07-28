package com.example.e_services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.ViewHodlder> {
    Context ctx;
    LayoutInflater layoutInflater;
    List<UserHistoryModel> userHistoryModelList;

    public UserHistoryAdapter(Context ctx, List<UserHistoryModel> userHistoryModelList) {
        this.ctx = ctx;
        this.userHistoryModelList = userHistoryModelList;
    }

    @NonNull
    @Override
    public UserHistoryAdapter.ViewHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_user_history,parent,false);
        return new ViewHodlder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHistoryAdapter.ViewHodlder holder, int position) {
        holder.tvName.setText(userHistoryModelList.get(position).getWname());
        holder.tvMono.setText(userHistoryModelList.get(position).getW_mono());
        holder.tvDate.setText(userHistoryModelList.get(position).getDatebook());
        String id = userHistoryModelList.get(position).getWid();
        String rating = userHistoryModelList.get(position).getRating();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx,GiveFeedbackToWorker.class);
                i.putExtra("wid",id);
                i.putExtra("rating",rating);
                ctx.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userHistoryModelList.size();
    }

    public class ViewHodlder extends RecyclerView.ViewHolder{
        TextView tvName,tvMono,tvDate;
        CardView cardView;
        public ViewHodlder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.username);
            tvMono = itemView.findViewById(R.id.mobile_number);
            tvDate = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
