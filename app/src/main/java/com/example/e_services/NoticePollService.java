package com.example.e_services;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class NoticePollService extends IntentService {
    List<WorkerNewBookModel> workerNewBookModels;
    WorkerNewBookAdapter workerNewBookAdapter;
    int[] a;
    String id;
    int count1;
    TestModel testModel;
    /**
     * @param name
     * @deprecated
     */

    private static final String TAG = "com.e_services.NoticePollService";
    private static final long POLL_INTERVAL_MS = TimeUnit.SECONDS.toMillis(30);
    private NotificationChannel mNotificationChannel;

    public static Intent newIntent(Context aContext){
        return new Intent(aContext, NoticePollService.class);
    }
    public NoticePollService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        id = getSharedPreferences("shared", Context.MODE_PRIVATE).getString("user_id","-1");
        Log.e("try","inside notice");
        if (Build.VERSION.SDK_INT >= 26) {
            mNotificationChannel = createNotificationChannel();

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                    .createNotificationChannel(mNotificationChannel);

            Notification notification = new NotificationCompat.Builder(this, mNotificationChannel.getId())
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Intent i = NoticePollService.newIntent(getApplicationContext());
        PendingIntent pi = PendingIntent.getForegroundService(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                volleyCall();
            }
        },POLL_INTERVAL_MS, TimeUnit.MINUTES.toMillis(1));
        return START_STICKY;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Received an intent: " + intent);
        if(!isNetworkAvailableAndConnected())
            return;
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = connectivityManager.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                connectivityManager.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    private void volleyCall() {

        String s = getResources().getString( R.string.strlink);
        String str = s+"newBookWorker.php?wid="+id;
        //Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = this.getSharedPreferences("shared",MODE_PRIVATE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {


                try {
                    int count = response.getInt("count");
                    SharedPreferences sharedPreferences = getSharedPreferences( "shared", MODE_PRIVATE );
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor myEdit2 = sharedPreferences.edit();


                    myEdit2.putString("idNo",response.getString("count"));

                    String n = getSharedPreferences("shared",MODE_PRIVATE).getString("idNo","-1");
                    Log.e("notice","test");


                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    checkValid();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue a = Volley.newRequestQueue(getApplicationContext());
        a.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag")
    private void checkValid(){

        String lastResult = getSharedPreferences("shared",MODE_PRIVATE).getString("idNo","-1");
        if(lastResult == null || 0 < Integer.parseInt(lastResult)){
            //Log.d(TAG, "Got a new result: " + mNotice.getNoticeId());
            Resources resources = getResources();
            Intent i = NoticePollService.newIntent(this);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
                    0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, mNotificationChannel.getId())
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setContentTitle("Test")
                    .setContentText("TEst")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(1337, builder.build());
        }
        else {
            Log.d(TAG, "Got a old result");
        }
        new TestModel(lastResult,NoticePollService.this);
;    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = "test";
        String description = "tes";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("default", name, importance);channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        return channel;
    }


}

