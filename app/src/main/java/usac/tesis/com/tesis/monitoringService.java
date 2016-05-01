package usac.tesis.com.tesis;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

import usac.tesis.com.tesis.utils.GPSTracker;
import usac.tesis.com.tesis.utils.WSCaller;

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        GPSTracker tracker = new GPSTracker(context);
        Calendar calendar = Calendar.getInstance();

        String id = preferences.getInt("tesisUserID",-1)+"";
        String hora = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        String posicion = tracker.getLatitude()+","+tracker.getLongitude();
        int dia_del_mes = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int dia = calendar.get(Calendar.DAY_OF_WEEK);

        new settingsChecker(context).execute(id, hora, posicion, dia_del_mes + "", dia + "");
    }

    public class settingsChecker extends AsyncTask<String,Void,String>{

        private Context context;

        public settingsChecker(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String hora = params[1];
            String posicion = params[2];
            int dia_del_mes = Integer.parseInt(params[3]);
            int dia = Integer.parseInt(params[4]);

            WSCaller caller = new WSCaller("consultar");
            caller.addStringParam("arg0",id);
            caller.addStringParam("arg1",hora);
            caller.addStringParam("arg2", posicion);
            caller.addIntParam("arg3", dia_del_mes-1);
            caller.addIntParam("arg4", dia-1);

            return caller.call();
        }

        @Override
        protected void onPostExecute(String mensaje) {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager nm = (NotificationManager)this.context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification n = new Notification.Builder(this.context)
                    .setContentTitle("Respuesta")
                    .setContentText(mensaje)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(sound)
                    .build();

            nm.notify(1, n);
        }
    }

}
