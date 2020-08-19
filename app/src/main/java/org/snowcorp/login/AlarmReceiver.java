package org.snowcorp.login;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlarmReceiver extends BroadcastReceiver {
//    private static final int MY_NOTIFICATION_ID = 4;
//    NotificationManager nfm;
//    Notification ntf;

    private static final String TAG = "Dep_stdy";
    private static final int NOTIFICATION_ID = 001;
    private Notification notification;
    public NotificationManager notificationManager;

//
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        Log.v("Alarm Receiver","Alarm received!");


        Intent it =  new Intent(context, MainActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        createNotification(context, it);
//        createNotification2(context, it);
//

    }

    public void createNotification(Context context, Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Dep_stdy";
            String description = "DepressionStudy1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("DS_01", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
            PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "DS_01")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setTicker("Reminder")
                    .setContentTitle("Depression Study")
                    .setContentText("Please record audio for depression study")
                    .setContentIntent(p)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{150, 300, 150, 400})
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompact = NotificationManagerCompat.from(context);

            notificationManagerCompact.notify(001, builder.build());


            //create a vibration
            try {

                Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone toque = RingtoneManager.getRingtone(context, som);
                toque.play();
            } catch (Exception e) {
            }

    }

//    public void createNotification2(Context context, Intent intent)
//    {
//        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);
//        notification = new Notification.Builder(context)
//                .setContentTitle("Content title")
//                .setContentText("Content text")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentIntent(p)
//                .addAction(R.drawable.ic_action_record, "Record audio",p)
//                .build();
//        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notificationManager.notify(TAG, NOTIFICATION_ID, notification);
//
//        try {
//
//            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone toque = RingtoneManager.getRingtone(context, som);
//            toque.play();
//        } catch (Exception e) {
//        }
//    }

}
