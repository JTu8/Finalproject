package com.example.htmjs.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage == null) {
            return;
        }

        if(remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification body: " + remoteMessage.getNotification().getBody());

        }
    }

    private void handleNotification(String message) {
        if(!NotificationUtils.isAppInBackRound(getApplicationContext())) {
            Intent intent = new Intent(Config.PUSH_NOTIFICATION);
            intent.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
        else {

        }
    }

    private void handleDataMessage(JSONObject jsonObject) {
        Log.e(TAG, "push json: " + jsonObject);

        try {
            JSONObject data = jsonObject.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackRound = data.getBoolean("is_backround");
            String imageUrl = data.getString("image");
            String timeStamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message" + message);
            Log.e(TAG, "isBackround" + isBackRound);
            Log.e(TAG, "payload" + payload);
            Log.e(TAG, "imageUrl" + imageUrl);
            Log.e(TAG, "timestamp" + timeStamp);

            if(!NotificationUtils.isAppInBackRound(getApplicationContext())) {
                Intent intent = new Intent(Config.PUSH_NOTIFICATION);
                intent.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }
            else {
                Intent resultIntent = new Intent(getApplicationContext(), MainPage.class);
                resultIntent.putExtra("message", message);

                if(TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timeStamp, resultIntent, imageUrl);
                }
                else {
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timeStamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException ex) {
            Log.e(TAG, "Json exception: " + ex.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
