package me.yangtong.todotask.service;

import java.util.ArrayList;
import java.util.List;

import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AnalysisService extends Service {

	private List<Task> hasNotifyedTasks;
	TaskOperate taskOperate;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO 
		hasNotifyedTasks = new ArrayList<Task>();
		MyThread scanThread = new MyThread();
		taskOperate = TaskOperate.getInstance(AnalysisService.this);
		scanThread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class MyThread extends Thread{

		@Override
		public void run() {
			super.run();
		}	
	}
	

	

}
