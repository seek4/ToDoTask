package me.yangtong.view;

import java.util.Calendar;
import java.util.TimeZone;

import me.yangtong.todotask.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * A dialog that can pick date and time
 * 
 * @author yangtong
 *
 */
public class DateTimePickDialog extends Dialog {
	
	private int year;
	private int monthOfYear;
	private int dayOfMonth;
	
	private int hourOfDay;
	private int minute;
	
	private OnTimeSelectedListener timeSelectedListener;

	public DateTimePickDialog(Context context,
			OnTimeSelectedListener timeSelectedListener,
			int year,int monthOfYear,int dayOfMonth,
			int hourOfDay,int minute) {
		super(context);
		this.timeSelectedListener = timeSelectedListener;
		this.year = year;
		this.monthOfYear = monthOfYear;
		this.dayOfMonth = dayOfMonth;
		this.hourOfDay = hourOfDay;
		this.minute = minute;
	}

	private DatePicker datePicker;
	private TimePicker timePicker;
	private Button btnOk;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_date_time_pick);
		datePicker = (DatePicker)this.findViewById(R.id.picker_date);
		timePicker = (TimePicker)this.findViewById(R.id.picker_time);
		setTitle(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute);
		btnOk = (Button)this.findViewById(R.id.btn_ok);
		datePicker.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int mYear, int mMonthOfYear,
					int mDayOfMonth) {
				year = mYear;
				monthOfYear = mMonthOfYear;
				dayOfMonth = mDayOfMonth;
				setTitle(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute);
			}
		});
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(hourOfDay);
		timePicker.setCurrentMinute(minute);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int mHourOfDay, int mMinute) {
				hourOfDay = mHourOfDay;
				minute = mMinute;
				setTitle(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute);
			}
		});
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar c = Calendar.getInstance();
				c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
				c.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
				timeSelectedListener.onTimeSelected(c.getTimeInMillis());
				dismiss();			
			}
		});
	}
	
	public interface OnTimeSelectedListener{
		/**
		 * called when date and time is selected
		 * @param selectedTime milliseconds
		 */
		public void onTimeSelected(long selectedTime);
	}

}
