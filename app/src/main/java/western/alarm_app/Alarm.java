package western.alarm_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;

/**
 * Created by Agoston on 2016-09-17.
 */
public class Alarm extends BroadcastReceiver
{
	private Ringtone ringtone;
	Context context;

	public Alarm(Context context)
	{
		this.context = context;
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		ringtone = RingtoneManager.getRingtone(context, uri);
	}

	public void disableAlarm()
	{
		if(ringtone != null)
		{
			ringtone.stop();
		}
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
		wakeLock.acquire();

		ringtone.play();

		wakeLock.release();
	}

	public void setAlarm(Context context, long timeMillis)
	{
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, Alarm.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);
	}

	/**
	 * Cancels the alarm so it will not trigger.
	 * @param context
	 */
	public void cancelAlarm(Context context)
	{
		Intent intent = new Intent(context, Alarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}
