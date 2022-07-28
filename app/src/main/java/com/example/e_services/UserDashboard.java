package com.example.e_services;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class UserDashboard extends AppCompatActivity {
    TextView tv1;

    TabLayout tabLayout;
    TabItem mhome,msearch,mfeedback;
    PagerAdapter pagerAdapter;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dashboard_try);

        mhome=findViewById(R.id.home);
        msearch=findViewById(R.id.search);
        mfeedback=findViewById(R.id.feedback);
        ViewPager viewPager = findViewById(R.id.fragmentcontainer);
        tabLayout=findViewById(R.id.include);

        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),3);
        viewPager.setAdapter(pagerAdapter);

       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());
               if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2){
                   pagerAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserDashboard.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getSharedPreferences("shared", MODE_PRIVATE).edit().clear().apply();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if(id == R.id.itemEditProfile){
            Intent i = new Intent(getApplicationContext(),UserEditProfile.class);
            startActivity(i);
        }
        if(id== R.id.itemFeedback){
            Intent i = new Intent(getApplicationContext(),UserRateUs.class);
            startActivity(i);
        }
        return true;
    }
}