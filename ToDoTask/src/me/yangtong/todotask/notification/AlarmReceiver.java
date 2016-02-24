package me.yangtong.todotask.notification;

import me.yangtong.L;
import me.yangtong.todotask.MainActivity;
import me.yangtong.todotask.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.mContext = context;
		sendNotificatio();
		L.i("AlarmReceiver  onReceive");
		Toast.makeText(context, "闹铃时间到了！！！！", Toast.LENGTH_LONG).show();
	}
	
	
	private void sendNotificatio(){
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, new Intent(mContext,MainActivity.class), 0);
		NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_stat_action_alarm_on;
		notification.tickerText = "New task to do!";
		notification.setLatestEventInfo(mContext, "New Task", "You have new task to do!", pendingIntent);
		notification.number = 1;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.notify(1,notification);
	}

}
