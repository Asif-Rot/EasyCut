package com.example.easycut;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


public class NotificationService extends Application {
    public static final String CHANNEL = "channel_sched";

    @Override
    public void onCreate() {
        super.onCreate();

        CreateNotificationChannel();
    }

    private void CreateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL,
                    "Channel Sched",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is a channel try");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
