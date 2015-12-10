package me.yangtong.todotask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.yangtong.Utils;
import me.yangtong.L;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
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

	OnClickListener btnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_starttime:
				String[] tmpStrings = (""+btnStartTime.getText()).split("-");
				DatePickerDialog startDatePickerDialog = new DatePickerDialog(AddActivity.this, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						btnStartTime.setText(Utils.getDisplayDate(year, monthOfYear, dayOfMonth));
					}
				}, Integer.parseInt(tmpStrings[0]), Integer.parseInt(tmpStrings[1])-1, Integer.parseInt(tmpStrings[2]));
				startDatePickerDialog.show();
				break;
			case R.id.btn_endtime:
				String[] tmpStrings2 = (""+btnEndTime.getText()).split("-");
				DatePickerDialog endDatePickerDialog = new DatePickerDialog(AddActivity.this, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						btnEndTime.setText(Utils.getDisplayDate(year, monthOfYear, dayOfMonth));
					}
				}, Integer.parseInt(tmpStrings2[0]),Integer.parseInt(tmpStrings2[1])-1, Integer.parseInt(tmpStrings2[2]));
				endDatePickerDialog.show();
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
		task.setStartTime(Utils.getMilloByDisplayDate(""+btnStartTime.getText()));
		task.setEndTime(Utils.getMilloByDisplayDate(""+btnEndTime.getText()));
		task.setStatus(Task.STATUS_TODO);
		taskOperate.insertTask(task);
	}
	
}
