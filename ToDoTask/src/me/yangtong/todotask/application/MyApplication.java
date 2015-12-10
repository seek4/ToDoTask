package me.yangtong.todotask.application;

import java.util.List;

import me.yangtong.L;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import android.app.Application;

public class MyApplication extends Application {

	TaskOperate taskOperate;
	
	@Override
	public void onCreate() {
		super.onCreate();
		L.i("ToDoTask MyApplication onCreate");
		//	update status of the tasks that time exceeded but status still TODO 
		//  to TIME_EXCEED
		taskOperate = TaskOperate.getInstance(MyApplication.this);
		List<Task> timeExceedTasks = taskOperate.getTodoTimeExceedTask();
		for(Task task:timeExceedTasks){
			task.setStatus(Task.STATUS_TIME_EXCEED);
			taskOperate.updateTask(task);
		}
	}
	
}
