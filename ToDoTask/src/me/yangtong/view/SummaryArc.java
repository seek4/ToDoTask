package me.yangtong.view;


import me.yangtong.L;
import me.yangtong.todotask.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;

/**
 * 统计结果的柱形图
 *
 */
public class SummaryArc extends View { 

	private Paint paint_black, paintText;
	private Paint paintFinished,paintTimeExceed,paintGiveUp;
	
	private RectF rectf;
	private float tb;
	private int blackColor = 0x70000000; // 底黑色
	private int textColor = 0xdd444231; // 灰黑色
	private int finishedColor = 0xdd1be332; // 绿色
	private int timeExceedColor = 0xdde61125; // 红色
	private int giveUpColor = 0xdde6cf11; //黄色
	private int score;
	private float arc_y = 0f;
	private int score_text;

	
	private int finishedNum = 0;
	private int timeExceedNum = 0;
	private int giveUpNum = 0;
	private int sumNum = 0;
	
	public SummaryArc(Context context, int finishedNum,int timeExceedNum,int giveUpNum) {
		super(context);
		init(finishedNum,timeExceedNum,giveUpNum);
	}

	public void init(int finishedNum,int timeExceedNum,int giveUpNum) {
		this.finishedNum = finishedNum;
		this.timeExceedNum = timeExceedNum;
		this.giveUpNum = giveUpNum;
		this.sumNum = finishedNum+timeExceedNum+giveUpNum;
		Resources res = getResources();
		tb = res.getDimension(R.dimen.historyscore_tb);

		paint_black = new Paint();
		paint_black.setAntiAlias(true);
		paint_black.setColor(blackColor);
		paint_black.setStrokeWidth(tb * 1.0f);
		paint_black.setStyle(Style.STROKE);

		paintText = new Paint();
		paintText.setAntiAlias(true);
		paintText.setColor(textColor);
		paintText.setTextSize(tb*6.0f);
		paintText.setStrokeWidth(tb * 0.2f);
		paintText.setTextAlign(Align.CENTER);
		paintText.setStyle(Style.STROKE);
		
		paintFinished = new Paint();
		paintFinished.setAntiAlias(true);
		paintFinished.setColor(finishedColor);
		paintFinished.setStrokeWidth(tb * 1.0f);
		paintFinished.setStyle(Style.STROKE);
		
		paintTimeExceed = new Paint();
		paintTimeExceed.setAntiAlias(true);
		paintTimeExceed.setColor(timeExceedColor);
		paintTimeExceed.setStrokeWidth(tb * 1.0f);
		paintTimeExceed.setStyle(Style.STROKE);
		
		paintGiveUp = new Paint();
		paintGiveUp.setAntiAlias(true);
		paintGiveUp.setColor(giveUpColor);
		paintGiveUp.setStrokeWidth(tb * 1.0f);
		paintGiveUp.setStyle(Style.STROKE);

		rectf = new RectF();
		rectf.set(tb * 0.5f, tb * 0.5f, tb * 18.5f, tb * 18.5f);

		setLayoutParams(new LayoutParams((int) (tb * 19.5f), (int) (tb * 19.5f)));

//		this.getViewTreeObserver().addOnPreDrawListener(
//				new OnPreDrawListener() {
//					public boolean onPreDraw() {
//						new thread();
//						getViewTreeObserver().removeOnPreDrawListener(this);
//						return false;
//					}
//				});
	}

	protected void onDraw(Canvas c) {
		super.onDraw(c);
		c.drawArc(rectf, -90, 360, false, paint_black);
		arc_y = 120;
		
		if(sumNum==0){
			c.drawText("" + 0+"%", tb * 9.7f, tb * 11.0f, paintText);
			return;
		}
		float finishedArc = ((float)finishedNum/sumNum)*360;
		L.i("finined Arc "+finishedArc);
		float timeExceedArc = ((float)timeExceedNum/sumNum)*360;
		L.i("timeExceedArc Arc "+timeExceedArc);
		float giveUpArc = ((float)giveUpNum/sumNum)*360;
		c.drawArc(rectf, -90, finishedArc, false, paintFinished);
		c.drawArc(rectf, -90+finishedArc, timeExceedArc, false, paintTimeExceed);
		c.drawArc(rectf, -90+finishedArc+timeExceedArc, giveUpArc, false, paintGiveUp);
		c.drawText("" + finishedNum*100/sumNum+"%", tb * 9.7f, tb * 11.0f, paintText);
	}

	public void setFinishedNum(int num){
		this.finishedNum = num;
		sumNum = finishedNum +timeExceedNum+giveUpNum;
		postInvalidate();
	}
	
	public void setTimeExceedNum(int num){
		this.timeExceedNum = num;
		sumNum = finishedNum +timeExceedNum+giveUpNum;
		postInvalidate();
	}
	
	public void setGiveUpNum(int num){
		this.giveUpNum = num;
		sumNum = finishedNum +timeExceedNum+giveUpNum;
		postInvalidate();
	}
	
	class thread implements Runnable {
		private Thread thread;
		private int statek;
		int count;

		public thread() {
			thread = new Thread(this);
			thread.start();
		}

		public void run() {
			while (true) {
				switch (statek) {
				case 0:
					try {
						Thread.sleep(200);
						statek = 1;
					} catch (InterruptedException e) {
					}
					break;
				case 1:
					try {
						Thread.sleep(15);
						arc_y += 3.6f;
						score_text++;
						count++;
						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
				if (count >= score)
					break;
			}
		}
	}

}
