package me.yangtong.todotask.data;

import me.yangtong.L;

public class Task {
	public int id = -1;
	
	/**
	 * Task重要程度
	 * 0:
	 * 1:LEVEL_MUST 必须要做的
	 * 2:LEVEL_SHOULD 应该做的
	 * 3:LEVEL_CAN  可以做的
	 */
	public int level = 0;
	public static final int LEVEL_DEFAULT = 0;
	public static final int LEVEL_MUST = 1;
	public static final int LEVEL_SHOULD = 2;
	public static final int LEVEL_CAN = 3;
	
	/**
	 * 大致内容
	 */
	public String title = "";
	
	/**
	 * Task具体描述
	 */
	public String description = "";
	
	/**
	 * startTime 用millisecond表示
	 */
	public long startTime;
	
	/**
	 * endTime 用millisecond表示
	 */
	public long endTime;
	
	/**
	 * task的状态
	 * 1: 未做(deadline还没到)
	 * 2: 已完成
	 * 3: 未完成(deadline已过)
	 */
	public int status;
	public static final int STATUS_TODO = 1;
	public static final int STATUS_FINISHED = 2;
	public static final int STATUS_GIVEUP = 3;
	public static final int STATUS_TIME_EXCEED = 4;
	
	/**
	 * 关闭时的时间（完成或者放弃）
	 */
	public long closedTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(long closedTime) {
		this.closedTime = closedTime;
	}	
	
	/**
	 * 得到当前时间已经过了多少
	 */
	public int getProgress(){
		int progress = 0;	
		progress = (int) ((System.currentTimeMillis()-this.startTime)*100/(this.endTime-this.startTime));
		if(progress>100){
			progress = 100;
		}else if (progress<0) {
			progress = 0;
		}
		L.i("getProgress "+progress);
		return progress;
	}
}
