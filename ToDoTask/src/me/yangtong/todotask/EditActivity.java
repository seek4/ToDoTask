package me.yangtong.todotask;

import me.yangtong.Utils;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import me.yangtong.view.DateTimePickDialog;
import me.yangtong.view.DateTimePickDialog.OnTimeSelectedListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class EditActivity extends Activity {
	
	private Task task;
	private int id;
	private TaskOperate taskOperate;
	
	private ImageButton btnTitleBack;
	private Button btnSave;
	private EditText editTitle;
	private EditText editDesp;
	private Spinner spinnerLevel;
	private Button btnStartTime;
	private Button btnEndTime;
	
	private long startTime;
	private long endTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		
		findView();
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		initData();
		freshView();
	}


	

	private void findView() {
		btnTitleBack = (ImageButton)this.findViewById(R.id.btn_title_back);
		btnTitleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO show dialog to ask if save
				finish();
			}
		});
		editTitle = (EditText)this.findViewById(R.id.edit_title);
		editDesp = (EditText)this.findViewById(R.id.edit_desp);
		btnSave = (Button)this.findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (saveCurTask()) {
					finish();
				}
			}
		});
		spinnerLevel = (Spinner)this.findViewById(R.id.spinner_level);
		btnStartTime = (Button)this.findViewById(R.id.btn_starttime);
		btnStartTime.setOnClickListener(btnClickListener);
		btnEndTime = (Button)this.findViewById(R.id.btn_endtime);
		btnEndTime.setOnClickListener(btnClickListener);
	}
	
	
	OnClickListener btnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_starttime:
				// getText >> 2015-12-22 11:12
				String[] tmpStrings = ("" + btnStartTime.getText()).split(" ");
				String[] tmpDateStrigns = tmpStrings[0].split("-");
				String[] tmpTimeStrings = tmpStrings[1].split(":");
				DateTimePickDialog dateTimePickDialog = new DateTimePickDialog(
						EditActivity.this, new OnTimeSelectedListener() {

							@Override
							public void onTimeSelected(long selectedTime) {
								startTime = selectedTime;
								btnStartTime.setText(""
										+ Utils.getDateByMilli(selectedTime));
							}
						}, Integer.parseInt(tmpDateStrigns[0]),
						Integer.parseInt(tmpDateStrigns[1]) - 1,
						Integer.parseInt(tmpDateStrigns[2]),
						Integer.parseInt(tmpTimeStrings[0]),
						Integer.parseInt(tmpTimeStrings[1]));
				dateTimePickDialog.show();
				break;
			case R.id.btn_endtime:
				String[] tmpStrings2 = ("" + btnEndTime.getText()).split(" ");
				String[] tmpDateStrigns2 = tmpStrings2[0].split("-");
				String[] tmpTimeStrings2 = tmpStrings2[1].split(":");
				DateTimePickDialog dateTimePickDialog2 = new DateTimePickDialog(
						EditActivity.this, new OnTimeSelectedListener() {

							@Override
							public void onTimeSelected(long selectedTime) {
								endTime = selectedTime;
								btnEndTime.setText(""
										+ Utils.getDateByMilli(selectedTime));
							}
						}, Integer.parseInt(tmpDateStrigns2[0]),
						Integer.parseInt(tmpDateStrigns2[1]) - 1,
						Integer.parseInt(tmpDateStrigns2[2]),
						Integer.parseInt(tmpTimeStrings2[0]),
						Integer.parseInt(tmpTimeStrings2[1]));
				dateTimePickDialog2.show();
				break;
			case R.id.btn_save:
				if (saveCurTask()) {
					finish();
				}
				break;
			case R.id.btn_title_back:
				finish();
				break;
			default:
				break;
			}
		}
	};
	
	
	private void initData(){
		taskOperate = TaskOperate.getInstance(EditActivity.this);
		id = getIntent().getIntExtra("id", -1);
		task = taskOperate.getTaskById(id);
		if(task==null){
			Toast.makeText(EditActivity.this, "出错了。。。", Toast.LENGTH_SHORT).show();
			finish();
		}
		startTime = task.startTime;
		endTime = task.endTime;
	}
	
	private void freshView(){
		editTitle.setText(""+task.title);
		editDesp.setText(task.description);
		btnStartTime.setText(Utils.getDateByMilli(startTime));
		btnEndTime.setText(Utils.getDateByMilli(endTime));
	}
	
	private boolean saveCurTask() {
		if ((editTitle.getText() == null || ("" + editTitle.getText())
				.equals(""))
				&& (editDesp.getText() == null || ("" + editDesp.getText())
						.equals(""))) {
			Toast.makeText(EditActivity.this,
					getString(R.string.toast_no_title_desp), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (endTime < System.currentTimeMillis()) {
			Toast.makeText(EditActivity.this,
					getString(R.string.toast_endtime_exceed),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (endTime < startTime) {
			Toast.makeText(EditActivity.this,
					getString(R.string.toast_endtime_before_start),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		task.setTitle("" + editTitle.getText());
		task.setDescription("" + editDesp.getText());
		int level = spinnerLevel.getSelectedItemPosition();
		task.setLevel(level + 1);
		task.setStartTime(startTime);
		task.setEndTime(endTime);
		task.setStatus(Task.STATUS_TODO);
		taskOperate.updateTask(task);
		return true;
	}
	
}
