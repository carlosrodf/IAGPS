package usac.tesis.com.tesis;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import usac.tesis.com.tesis.utils.GPSTracker;

/**
 * Created by root on 6/04/16.
 */
public class monitoringService extends BroadcastReceiver {

    private double latitud;
    private double longitud;

    public monitoringService() {
        Log.d("Monitor", "Fue construido");
    }

    @Override //CONSULTA POR POSIBLES CAMBIOS DE PERFIL
    public void onReceive(Context context, Intent intent) {

        GPSTracker tracker = new GPSTracker(context);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = new Notification.Builder(context)
                .setContentTitle("Monitoreando")
                .setContentText(tracker.getLatitude() + "," + tracker.getLongitude())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(sound)
                .build();

        nm.notify(1, n);
    }

}
