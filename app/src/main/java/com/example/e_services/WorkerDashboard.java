package com.example.e_services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class WorkerDashboard extends AppCompatActivity {

    TabLayout tabLayout2;
    TabItem mnewbook,mhistory,mfeedback;
    PagerAdapter2 pagerAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_dashboard_try);

        mnewbook=findViewById(R.id.newBook);
        mhistory=findViewById(R.id.history);
        mfeedback=findViewById(R.id.workerFeedback);
        ViewPager viewPager = findViewById(R.id.fragmentcontainer2);
        tabLayout2=findViewById(R.id.include2);

        pagerAdapter2=new PagerAdapter2(getSupportFragmentManager(),3);
        viewPager.setAdapter(pagerAdapter2);


        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2){
                    pagerAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout2));

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WorkerDashboard.this);
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
            Intent i = new Intent(getApplicationContext(),WorkerEditProfile.class);
            startActivity(i);

        }
        if(id== R.id.itemFeedback){
            Intent i = new Intent(getApplicationContext(),UserRateUs.class);
            startActivity(i);
        }
        return true;
    }
}