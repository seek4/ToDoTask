package me.yangtong.todotask;

import me.yangtong.Utils;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import me.yangtong.view.LineProgressView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends Activity {

	
	TaskOperate taskOperate;
	private Task task;
	private int id;
	
	private ImageButton btnTitleBack;
	private ImageButton btnTitleEdit;
	private TextView textTitle;
	private TextView textContent;
	private TextView textStartTime;
	private TextView textEndTime;
	private LineProgressView viewProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_view);
		initData();
		initView();
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if(id!=-1){
			task = taskOperate.getTaskById(id);
		}
		if(task==null){
			Toast.makeText(ViewActivity.this, "出错了。。。", Toast.LENGTH_SHORT).show();
			finish();
		}
		freshView();
	}

	
	private void freshView(){
		textTitle.setText(task.title);
		textContent.setText(task.description);
		textStartTime.setText(Utils.getDateByMilli(task.startTime));
		textEndTime.setText(Utils.getDateByMilli(task.endTime));
		viewProgress.setProgress(task.getProgress());
	}


	private void initView(){
		btnTitleEdit = (ImageButton)this.findViewById(R.id.btn_title_edit);
		btnTitleEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewActivity.this, EditActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
		btnTitleBack = (ImageButton)this.findViewById(R.id.btn_title_back);
		btnTitleBack.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();	
			}
		});
		textTitle = (TextView)this.findViewById(R.id.view_text_title);
		textContent = (TextView)this.findViewById(R.id.view_text_content);
		textStartTime = (TextView)this.findViewById(R.id.text_starttime);
		textEndTime = (TextView)this.findViewById(R.id.text_endtime);
		viewProgress = (LineProgressView)this.findViewById(R.id.view_progress);
	}
	
	private void initData(){
		taskOperate = TaskOperate.getInstance(ViewActivity.this);
		Intent intent = getIntent();
		id  =intent.getIntExtra("id", -1);
	}
	
}
