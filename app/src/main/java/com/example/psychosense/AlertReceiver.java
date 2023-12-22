package com.example.psychosense;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.psychosense.HomeActivity;
import com.example.psychosense.R;

public class AlertReceiver extends BroadcastReceiver {
    public static final String TAG = "Idan";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: 0");
        Intent intent1 = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_IMMUTABLE);
        Log.d(TAG, "onReceive: 6");
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "ChanelID")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setContentTitle("It is time for some practice")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        Log.d(TAG, "onReceive: 7");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Log.d(TAG, "onReceive: 8");
        notificationManager.notify(1, notification.build());
        Log.d(TAG, "onReceive: 9");
    }

}