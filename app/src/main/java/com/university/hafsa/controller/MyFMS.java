package com.university.hafsa.controller;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.university.hafsa.R;
import com.university.hafsa.view.activities.MainActivity;

public class MyFMS extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;
    private static final int NOTIFICATION_ID = 1;
    private Bundle bundle;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
        System.out.println("Refreshed token: " + token);
        // Optionally send the token to your server or handle it as needed
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if the message contains a data payload
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            // Extract data from the message
            bundle = new Bundle();
            for (String key : remoteMessage.getData().keySet()) {
                bundle.putString(key, remoteMessage.getData().get(key));
                Log.d(TAG, "key: " + key + ", value: " + remoteMessage.getData().get(key));
            }
        }

        // Check if the message contains a notification payload
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void showNotification(String title, String messageBody) {
        Context context = getApplicationContext();
        // Check if the app is running on Android 13 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if the POST_NOTIFICATIONS permission is granted
            if (ActivityCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                // If not granted, you might need to handle this scenario
                // Ideally, request permission in an activity context
                Log.w(TAG, "Notification permission not granted");
                return;
            }
        }

        // Create an intent for the notification tap action
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(bundle); // Include the bundle data
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Define the notification channel ID
        String channelId = getString(R.string.default_notification_channel_id);

        // Build the notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)  // Set the small icon
                        .setContentTitle(title)                            // Set the notification title
                        .setContentText(messageBody)                       // Set the notification message
                        .setAutoCancel(true)                               // Automatically remove the notification when tapped
                        .setContentIntent(pendingIntent);                  // Set the intent for notification tap

        // Get the notification manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // For Android O and higher, create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
