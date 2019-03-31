package com.example.ol.assignment2.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String,String> data;
        String notifType, newBookNotif = null;
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        data = remoteMessage.getData();
        notifType = data.get("type");

        MyNotificationManager.getInstance(getApplicationContext())
                .displayNotification(title,body, notifType);
    }
}
