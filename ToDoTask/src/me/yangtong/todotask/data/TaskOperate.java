package me.yangtong.todotask.data;

import java.util.ArrayList;
import java.util.List;

import me.yangtong.L;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 处理Task与数据库相关的操作
 * 
 * @author yangtong
 *
 */
public class TaskOperate {

	private static TaskOperate instance;

	private Context mContext;

	private static String DB_NAME = "task.db";
	private static int DB_VERSION = 1;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	private TaskOperate(Context context) {
		mContext = context;
		dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public synchronized static TaskOperate getInstance(Context context) {
		if (instance == null) {
			instance = new TaskOperate(context.getApplicationContext());
		}
		return instance;
	}

	public void terminate() {
		db.close();
		dbHelper.close();
	}

	
	

	public List<Task> getAllTask() {
		List<Task> tasks = new ArrayList<Task>();
		Cursor cursor = db.query(DatabaseHelper.TB_NAME, null, null, null,
				null, null, DatabaseHelper.ID + " DESC");
		if (cursor == null || cursor.getCount() <= 0) {
			return tasks;
		}
		cursor.moveToFirst();
		Task task;
		while (!cursor.isAfterLast()) {
			task = new Task();
			task.id = cursor.getInt(0);
			task.level = cursor.getInt(1);
			task.title = cursor.getString(2);
			task.description = cursor.getString(3);
			task.startTime = cursor.getLong(4);
			task.endTime = cursor.getLong(5);
			task.status = cursor.getInt(6);
			task.closedTime = cursor.getLong(7);
			tasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();
		return tasks;
	}

	public void updateTask(Task task) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.LEVEL, task.level);
		contentValues.put(DatabaseHelper.TITLE, task.title);
		contentValues.put(DatabaseHelper.DESCRIPTION, task.description);
		contentValues.put(DatabaseHelper.START_TIME, task.startTime);
		contentValues.put(DatabaseHelper.END_TIME, task.endTime);
		contentValues.put(DatabaseHelper.STATUS, task.status);
		contentValues.put(DatabaseHelper.CLOED_TIME, task.closedTime);
		db.update(DatabaseHelper.TB_NAME, contentValues, "id = ?",
				new String[] { "" + task.id });
	}

	public void insertTask(Task task) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.LEVEL, task.level);
		contentValues.put(DatabaseHelper.TITLE, task.title);
		contentValues.put(DatabaseHelper.DESCRIPTION, task.description);
		contentValues.put(DatabaseHelper.START_TIME, task.startTime);
		contentValues.put(DatabaseHelper.END_TIME, task.endTime);
		contentValues.put(DatabaseHelper.STATUS, task.status);
		contentValues.put(DatabaseHelper.CLOED_TIME, task.closedTime);
		db.insert(DatabaseHelper.TB_NAME, null, contentValues);
	}

	public void deleteTask(Task task) {
		deleteById(task.id);
	}

	public void deleteById(int id) {
		db.delete(DatabaseHelper.TB_NAME, DatabaseHelper.ID + "=?",
				new String[] { "" + id });
	}

	public Task getTaskById(int id) {
		Task task;
		Cursor cursor = db.query(DatabaseHelper.TB_NAME, null,
				DatabaseHelper.ID+"=?", new String[] { ""+id }, null, null, null);
		if(cursor==null||cursor.getCount()==0){
			return null;
		}else {
			cursor.moveToFirst();
			task = new Task();
			task.id = cursor.getInt(0);
			task.level = cursor.getInt(1);
			task.title = cursor.getString(2);
			task.description = cursor.getString(3);
			task.startTime = cursor.getLong(4);
			task.endTime = cursor.getLong(5);
			task.status = cursor.getInt(6);
			task.closedTime = cursor.getLong(7);
			cursor.close();
			return task;
		}
		
	}
	
	
	
	
	
	private static final String SELECT_TODAY_TODO_TASKS = DatabaseHelper.START_TIME
			+ "<? and " + DatabaseHelper.END_TIME + ">? and status=?";

	/**
	 * get today's todo task order by endtime
	 * 
	 * @return
	 */
	public List<Task> getTodayTodoTask() {
		String currMilli = "" + System.currentTimeMillis();
		List<Task> todayTasks = new ArrayList<Task>();
		Cursor cursor = db.query(DatabaseHelper.TB_NAME, null,
				SELECT_TODAY_TODO_TASKS, new String[] { currMilli, currMilli,
						"" + Task.STATUS_TODO }, null, null, "endtime ASC");
		if (cursor == null || cursor.getCount() <= 0) {
			return todayTasks;
		}
		Task task;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			task = new Task();
			task.id = cursor.getInt(0);
			task.level = cursor.getInt(1);
			task.title = cursor.getString(2);
			task.description = cursor.getString(3);
			task.startTime = cursor.getLong(4);
			task.endTime = cursor.getLong(5);
			task.status = cursor.getInt(6);
			task.closedTime = cursor.getLong(7);
			todayTasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();
		return todayTasks;
	}

	private static final String SELECT_CLOSED_CLOSED_STATUS = DatabaseHelper.CLOED_TIME
			+ ">? and " + DatabaseHelper.CLOED_TIME + "<? and status=?";
	
	/**
	 * 得到七天内完成了的Task
	 * @return
	 */
	public List<Task> getLastWeekFinishedTask() {
		String currMilli = "" + System.currentTimeMillis();
		String lastWeekMilli = ""+(System.currentTimeMillis()-7*24*60*60*1000);
		List<Task> lastWeekFininshedTasks = new ArrayList<Task>();
		Cursor cursor = db.query(DatabaseHelper.TB_NAME, null,
				SELECT_CLOSED_CLOSED_STATUS, new String[] { lastWeekMilli, currMilli,
						"" + Task.STATUS_FINISHED }, null, null, "closedTime ASC");
		if (cursor == null || cursor.getCount() <= 0) {
			return lastWeekFininshedTasks;
		}
		Task task;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			task = new Task();
			task.id = cursor.getInt(0);
			task.level = cursor.getInt(1);
			task.title = cursor.getString(2);
			task.description = cursor.getString(3);
			task.startTime = cursor.getLong(4);
			task.endTime = cursor.getLong(5);
			task.status = cursor.getInt(6);
			task.closedTime = cursor.getLong(7);
			lastWeekFininshedTasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();
		return lastWeekFininshedTasks;
	}
	
	private static final String SELECT_END_END_STATUS = DatabaseHelper.END_TIME
			+ ">? and " + DatabaseHelper.END_TIME + "<? and status=?";
	
	/**
	 * 得到七天内未完成（超时）的Task
	 * @return
	 */
	public List<Task> getLastWeekTimeExceedTask() {
		String currMilli = "" + System.currentTimeMillis();
		String lastWeekMilli = ""+(System.currentTimeMillis()-7*24*60*60*1000);
		List<Task> lastWeekTimeExceedTasks = new ArrayList<Task>();
		Cursor cursor = db.query(DatabaseHelper.TB_NAME, null,
				SELECT_END_END_STATUS, new String[] { lastWeekMilli, currMilli,
						"" + Task.STATUS_TIME_EXCEED }, null, null, "closedTime ASC");
		if (cursor == null || cursor.getCount() <= 0) {
			return lastWeekTimeExceedTasks;
		}
		Task task;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			task = new Task();
			task.id = cursor.getInt(0);
			task.level = cursor.getInt(1);
			task.title = cursor.getString(2);
			task.description = cursor.getString(3);
			task.startTime = cursor.getLong(4);
			task.endTime = cursor.getLong(5);
			task.status = cursor.getInt(6);
			task.closedTime = cursor.getLong(7);
			lastWeekTimeExceedTasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();
		return lastWeekTimeExceedTasks;
	}
	
	
	
	/**
	 * 得到七天内放弃了的Task
	 * @return
	 */
	public List<Task> getLastWeekGiveUpTask() {
		String currMilli = "" + System.currentTimeMillis();
		String lastWeekMilli = ""+(System.currentTimeMillis()-7*24*60*60*1000);
		List<Task> lastWeekGiveUpTasks = new ArrayList<Task>();
		Cursor cursor = db.query(DatabaseHelper.TB_NAME, null,
				SELECT_CLOSED_CLOSED_STATUS, new String[] { lastWeekMilli, currMilli,
						"" + Task.STATUS_GIVEUP }, null, null, "closedTime ASC");
		if (cursor == null || cursor.getCount() <= 0) {
			return lastWeekGiveUpTasks;
		}
		Task task;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			task = new Task();
			task.id = cursor.getInt(0);
			task.level = cursor.getInt(1);
			task.title = cursor.getString(2);
			task.description = cursor.getString(3);
			task.startTime = cursor.getLong(4);
			task.endTime = cursor.getLong(5);
			task.status = cursor.getInt(6);
			task.closedTime = cursor.getLong(7);
			lastWeekGiveUpTasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();
		return lastWeekGiveUpTasks;
	}
	
	
	private static final String SELECT_END_STATUS = DatabaseHelper.END_TIME + "<? and status=?";
	
	
	/**
	 * 得到已经过了结束时间但状态仍为TODO的Task（这种状态需要修改为TIME_EXCEED）
	 * @return
	 */
	public List<Task> getTodoTimeExceedTask(){	
		String currMilli = "" + System.currentTimeMillis();
		List<Task> todoTimeExceedTasks = new ArrayList<Task>();
		Cursor cursor = db.query(DatabaseHelper.TB_NAME, null,
				SELECT_END_STATUS, new String[] { currMilli,
						"" + Task.STATUS_TODO }, null, null, "endTime ASC");
		if (cursor == null || cursor.getCount() <= 0) {
			return todoTimeExceedTasks;
		}
		Task task;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			task = new Task();
			task.id = cursor.getInt(0);
			task.level = cursor.getInt(1);
			task.title = cursor.getString(2);
			task.description = cursor.getString(3);
			task.startTime = cursor.getLong(4);
			task.endTime = cursor.getLong(5);
			task.status = cursor.getInt(6);
			task.closedTime = cursor.getLong(7);
			todoTimeExceedTasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();
		return todoTimeExceedTasks;
	}
	
	
	public void setTaskFinished(Task task) {
		if (task == null) {
			return;
		}
		task.setStatus(Task.STATUS_FINISHED);
		task.setClosedTime(System.currentTimeMillis());
		updateTask(task);
	}

	public void setTaskGiveUp(Task task) {
		if (task == null) {
			return;
		}
		task.setStatus(Task.STATUS_GIVEUP);
		task.setClosedTime(System.currentTimeMillis());
		updateTask(task);
	}

}
