package me.yangtong.todotask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.yangtong.Utils;
import me.yangtong.L;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import me.yangtong.view.DateTimePickDialog;
import me.yangtong.view.DateTimePickDialog.OnTimeSelectedListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends Activity {
	
	private TaskOperate taskOperate;

	private EditText editTitle;
	private Spinner spinnerLevel;
	private EditText editDesp;
	private Button btnStartTime;
	private Button btnEndTime;
	private Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add);
		initView();
		taskOperate = TaskOperate.getInstance(AddActivity.this);
	}
	
	private void initView(){
		editTitle = (EditText)this.findViewById(R.id.edit_title);
		spinnerLevel = (Spinner)this.findViewById(R.id.spinner_level);
		editDesp = (EditText)this.findViewById(R.id.edit_desp);
		btnStartTime = (Button)this.findViewById(R.id.btn_starttime);
		btnStartTime.setOnClickListener(btnClickListener);
		btnStartTime.setText(Utils.getTodayDate());
		btnEndTime = (Button)this.findViewById(R.id.btn_endtime);
		btnEndTime.setOnClickListener(btnClickListener);
		btnEndTime.setText(Utils.getTomorrowDate());
		btnSave = (Button)this.findViewById(R.id.btn_save);
		btnSave.setOnClickListener(btnClickListener);
	}

	private long startTime = System.currentTimeMillis();
	private long endTime = System.currentTimeMillis()+24*60*60*1000;
	
	OnClickListener btnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_starttime:
				// 2015-12-22 11:12
				String[] tmpStrings = (""+btnStartTime.getText()).split(" ");
				String[] tmpDateStrigns = tmpStrings[0].split("-");
				String[] tmpTimeStrings = tmpStrings[1].split(":");
				DateTimePickDialog dateTimePickDialog = new DateTimePickDialog(AddActivity.this, 
						new OnTimeSelectedListener() {
							
							@Override
							public void onTimeSelected(long selectedTime) {
								startTime = selectedTime;
								btnStartTime.setText(""+Utils.getDateByMilli(selectedTime));
							}
						}, Integer.parseInt(tmpDateStrigns[0]), Integer.parseInt(tmpDateStrigns[1])-1, Integer.parseInt(tmpDateStrigns[2]),
						Integer.parseInt(tmpTimeStrings[0]), Integer.parseInt(tmpTimeStrings[1]));
				dateTimePickDialog.show();
				break;
			case R.id.btn_endtime:
				String[] tmpStrings2 = (""+btnEndTime.getText()).split(" ");
				String[] tmpDateStrigns2 = tmpStrings2[0].split("-");
				String[] tmpTimeStrings2 = tmpStrings2[1].split(":");
				DateTimePickDialog dateTimePickDialog2 = new DateTimePickDialog(AddActivity.this, 
						new OnTimeSelectedListener() {
							
							@Override
							public void onTimeSelected(long selectedTime) {
								endTime = selectedTime;
								btnEndTime.setText(""+Utils.getDateByMilli(selectedTime));
							}
						}, Integer.parseInt(tmpDateStrigns2[0]), Integer.parseInt(tmpDateStrigns2[1])-1, Integer.parseInt(tmpDateStrigns2[2]),
						Integer.parseInt(tmpTimeStrings2[0]), Integer.parseInt(tmpTimeStrings2[1]));
				dateTimePickDialog2.show();
				break;
			case R.id.btn_save:
				saveCurTask();
				finish();
				break;

			default:
				break;
			}		
		}		
	};
	
	private void saveCurTask(){
		Task task = new Task();
		task.setTitle(""+editTitle.getText());
		task.setDescription(""+editDesp.getText());
		int level = spinnerLevel.getSelectedItemPosition();
		task.setLevel(level+1);
		task.setStartTime(startTime);
		task.setEndTime(endTime);
		task.setStatus(Task.STATUS_TODO);
		taskOperate.insertTask(task);
	}
	
}
