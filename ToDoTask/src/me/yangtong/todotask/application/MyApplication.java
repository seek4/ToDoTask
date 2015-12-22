package me.yangtong.todotask.application;

import java.util.List;

import me.yangtong.L;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import android.app.Application;
import android.widget.Toast;

public class MyApplication extends Application {

	TaskOperate taskOperate;
	
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
	}
	
}
