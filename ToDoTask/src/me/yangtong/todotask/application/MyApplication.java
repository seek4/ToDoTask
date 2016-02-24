package me.yangtong.todotask.application;

import java.util.Calendar;
import java.util.List;

import me.yangtong.L;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import me.yangtong.todotask.notification.AlarmReceiver;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyApplication extends Application {

	TaskOperate taskOperate;
	
	private static final String ACTION_ALARM = "me.yangtong.task.alarm";
	
	@Override
	public void onCreate() {
		super.onCreate();
		L.i("ToDoTask MyApplication onCreate");
		//	update status of the tasks that time exceeded but status still ToDO 
		//  to TIME_EXCEED
		taskOperate = TaskOperate.getInstance(MyApplication.this);
		List<Task> timeExceedTasks = taskOperate.getTodoTimeExceedTask();
		if(timeExceedTasks!=null&&timeExceedTasks.size()>0){
			Toast.makeText(MyApplication.this, ""+timeExceedTasks.size()+"个事项已逾期", Toast.LENGTH_LONG).show();
			for(Task task:timeExceedTasks){
				task.setStatus(Task.STATUS_TIME_EXCEED);
				taskOperate.updateTask(task);
			}
		}
		
		// 将结束时间在今天内的Task添加到Alarm
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis()+10*1000);
		
		Intent intent = new Intent(MyApplication.this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(MyApplication.this, 0, intent, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
		//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (1*1000), pi);
	}
	
}
