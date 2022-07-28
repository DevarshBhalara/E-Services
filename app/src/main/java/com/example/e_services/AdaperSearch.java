package com.example.e_services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaperSearch extends RecyclerView.Adapter<AdaperSearch.ViewHolder> {

    Context ctx;
    LayoutInflater inflater;
    List<SearchModel> searchModels;
   

    public AdaperSearch(Context ctx,List<SearchModel> searchModels){
        this.ctx = ctx;
        this.searchModels = searchModels;
    }

    @NonNull
    @Override
    public AdaperSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaperSearch.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = searchModels.get(position).getId();
                //Toast.makeText(view.getContext(), id,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ctx,WorkerProfile.class);
                i.putExtra("wid",id);

                ctx.startActivity(i);

            }
        });

        holder.name.setText(searchModels.get(position).getWname());
        holder.mono.setText(searchModels.get(position).getMobile_number());
        holder.prof.setText(searchModels.get(position).getProfession());

    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView name,prof,mono;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            name = itemView.findViewById(R.id.name);
            prof = itemView.findViewById(R.id.prof);
            mono = itemView.findViewById(R.id.mono);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
