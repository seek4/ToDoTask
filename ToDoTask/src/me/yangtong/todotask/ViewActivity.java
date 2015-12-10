package me.yangtong.todotask;

import me.yangtong.L;
import me.yangtong.Utils;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import me.yangtong.view.LineProgressView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends Activity {

	
	TaskOperate taskOperate;
	private Task task;
	
	private TextView textTitle;
	private TextView textContent;
	private TextView textStartTime;
	private TextView textEndTime;
	private LineProgressView viewProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_view);
		initData();
		initView();
		
	}
	
	private void initView(){
		textTitle = (TextView)this.findViewById(R.id.view_text_title);
		textTitle.setText(task.title);
		textContent = (TextView)this.findViewById(R.id.view_text_content);
		textContent.setText(task.description);
		textStartTime = (TextView)this.findViewById(R.id.text_starttime);
		textStartTime.setText(Utils.getDateByMilli(task.startTime));
		textEndTime = (TextView)this.findViewById(R.id.text_endtime);
		textEndTime.setText(Utils.getDateByMilli(task.endTime));
		viewProgress = (LineProgressView)this.findViewById(R.id.view_progress);
		viewProgress.setProgress(task.getProgress());
	}
	
	private void initData(){
		taskOperate = TaskOperate.getInstance(ViewActivity.this);
		Intent intent = getIntent();
		int id  =intent.getIntExtra("id", -1);
		task = taskOperate.getTaskById(id);
		if(task==null){
			Toast.makeText(ViewActivity.this, "出错了。。。", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
}
