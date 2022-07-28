package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.Markers;
import com.example.currentplacedetailsonmap.R;

import java.util.List;

public class MonitoringService extends Service {

    private NotificationManager mNM;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
        Toast.makeText(this, "Monitoring Service Started", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("mainch" , "mainch",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationChannel channel2 = new NotificationChannel("popch" , "popch",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel2);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        DatabaseSQLITE db = new DatabaseSQLITE(MonitoringService.this);
        String city_name = intent.getStringExtra("city_name");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mainch")
                .setSmallIcon(R.drawable.blue_recycle)
                .setContentTitle("Monitoring Mode Enabled")
                .setContentText("You are currently monitoring " + city_name)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("You are currently monitoring " + city_name))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "popch")
                .setSmallIcon(R.drawable.red_recycle)
                .setContentTitle("FOUND CLOSE TO CAPACITY SPOT")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1,builder.build());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                int id = intent.getExtras().getInt("pos");
                List<Markers> markers = db.get_marker_from_city(id);
                for (int i=0;i<markers.size();i++){
                    double red = 95f/100f * markers.get(i).get_max_capacity();
                    double size = markers.get(i).getTotal();
                    if (size >=red){
                        builder2.setContentText("The spot " + markers.get(i).getPoint_name() + " from " + city_name + " is nearing capacity!")
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("The spot " + markers.get(i).getPoint_name() + " from " + city_name + " is nearing capacity!"));
                        notificationManager.notify(2,builder2.build());
                    }
                }
                handler.postDelayed(this, 120000); // Running every 2 minutes.
            }
        }, 120000); // First run after 2 minutes .
        return START_STICKY;
    }

    public void onDestroy(){
        Toast.makeText(this, "Monitoring Service Stopped", Toast.LENGTH_SHORT).show();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
        super.onDestroy();
    }
}
