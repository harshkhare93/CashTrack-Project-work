package trainedge.cashtrack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiver extends BroadcastReceiver implements TextToSpeech.OnInitListener {
    final public static String ONE_TIME = "trainedge.cashtrack.onetime";
    final public static String REPEATING = "trainedge.cashtrack.reapeating";
    private SharedPreferences pref;
    private TextToSpeech engine;
    private String msg;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        if (intent.getAction().equals("trainedge.cashtrack.MY_ALARM")) {
            //You can do the processing here.
            Bundle extras = intent.getExtras();
            StringBuilder msgStr = new StringBuilder();

            if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {
                //Make sure this intent has been sent by the one-time timer button.
                msgStr.append("Add your expenses ");
            }
            Format formatter = new SimpleDateFormat("hh:mm:ss a");
            msgStr.append(formatter.format(new Date()));
            msg = "Time to add some expense for " + msgStr;
            NewMessageNotification.notify(context, msg, 0);
            pref = context.getSharedPreferences(Constants.SETTING_PREF, MODE_PRIVATE);
            Intent startTts = new Intent(context, TTSactivity.class);
            startTts.putExtra("trainedge.cashtrack.EXTRA_MSG", msg);
            startTts.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startTts.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startTts.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            context.startActivity(startTts);
            //Release the lock
            wl.release();
        }
        if (intent.getAction().equals("trainedge.cashtrack.EVENT_ALARM")) {
            msg = intent.getStringExtra("trainedge.cashtrack.EXTRA_MSG");
            NewMessageNotification.notify(context, msg, 0);
        }


    }

    public void SetAlarm(Context context, int hourOfDay, int minute) {
        Calendar calSet = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            calSet.set(Calendar.DAY_OF_WEEK, i);
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
            Intent intent = new Intent("trainedge.cashtrack.MY_ALARM");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
            Log.i("SERVICE", "alarm set" + i);
        }
    }


    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeTimer(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            engine.setLanguage(Locale.ENGLISH);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public void SetAlarm(Context context, Date selectedDate, int hourOfDay, int minute, String msg) {
        Calendar calSet = Calendar.getInstance();
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        calSet.set(Calendar.YEAR, selectedDate.getYear() + 1900);
        calSet.set(Calendar.MONTH, selectedDate.getMonth());
        calSet.set(Calendar.DAY_OF_WEEK, selectedDate.getDay());
        Intent intent = new Intent("trainedge.cashtrack.EVENT_ALARM");
        intent.putExtra("trainedge.cashtrack.EXTRA_MSG", msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        Log.i("SERVICE", "alarm set");
        Toast.makeText(context, "Event schedule set", Toast.LENGTH_SHORT).show();
    }
}
