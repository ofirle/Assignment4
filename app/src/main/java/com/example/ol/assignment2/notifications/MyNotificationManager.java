package com.example.ol.assignment2.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.ol.assignment2.R;
import com.example.ol.assignment2.SplashScreenActivity;

public class MyNotificationManager {
    private Context mContext;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mContext = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body, String type) {
        Intent intent = new Intent(mContext, SplashScreenActivity.class);
        intent.putExtra("notifType", type);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, Constant.CHANNEL_ID)
                .setSmallIcon(R.drawable.mobile_ebook_black_orange)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(1, mBuilder.build());

        }
    }
}
