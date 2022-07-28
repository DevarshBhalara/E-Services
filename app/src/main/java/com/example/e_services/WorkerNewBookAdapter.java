package com.example.e_services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkerNewBookAdapter extends RecyclerView.Adapter<WorkerNewBookAdapter.ViewHolder>{
    Context ctx;
    LayoutInflater layoutInflater;
    List<WorkerNewBookModel> bookModels;

    public WorkerNewBookAdapter(Context ctx,List<WorkerNewBookModel> bookModels){
        this.ctx = ctx;
        this.bookModels = bookModels;
    }
    @NonNull
    @Override
    public WorkerNewBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_book,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerNewBookAdapter.ViewHolder holder, int position) {

        holder.username.setText(bookModels.get(position).getUname());
        holder.mobile_number.setText(bookModels.get(position).getMobile_number());
        holder.datework.setText(bookModels.get(position).getDatework());
        holder.address.setText(bookModels.get(position).getAddress());
        String wid = bookModels.get(position).getWid();
        String uid = bookModels.get(position).getUid();
        String date_c = bookModels.get(position).getDatework();
        String mono = bookModels.get(position).getMobile_number();
        String uname = bookModels.get(position).getUname();
        String wname = bookModels.get(position).getWname();
        String total = bookModels.get(position).getTotal();


        holder.mbtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ctx,WorkerYesClick.class);

                i.putExtra("wid",wid);
                i.putExtra("uid",uid);
                i.putExtra("date_c",date_c);
                i.putExtra("mono",mono);
                i.putExtra("wname",wname);
                i.putExtra("uname",uname);
                i.putExtra("total",total);
                ctx.startActivity(i);
            }
        });

       holder.mbtnNo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ctx);
               builder.setTitle(R.string.app_name);
               builder.setIcon(R.mipmap.ic_launcher);
               builder.setMessage("Are you Sure?")
                       .setCancelable(false)
                       .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               Intent i = new Intent(ctx,WorkerNoClick.class);
                               i.putExtra("total",total);
                               i.putExtra("mono",mono);
                               ctx.startActivity(i);

                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                           }
                       });
               androidx.appcompat.app.AlertDialog alert = builder.create();
               alert.show();



           }
       });
       holder.mbtnCall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String cmono = "tel:"+mono;
               Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse(cmono));
               ctx.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView username,mobile_number,datework,address;
        Button mbtnYes,mbtnNo,mbtnCall;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            username = itemView.findViewById(R.id.username);
            mobile_number = itemView.findViewById(R.id.mobile_number);
            datework = itemView.findViewById(R.id.date);
            address = itemView.findViewById(R.id.address);
            mbtnYes = itemView.findViewById(R.id.btnYes);
            mbtnNo = itemView.findViewById(R.id.btnNo);
            mbtnCall = itemView.findViewById(R.id.btnCall);
        }
    }
}
