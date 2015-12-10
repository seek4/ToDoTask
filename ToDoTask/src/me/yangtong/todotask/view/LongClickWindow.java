package me.yangtong.todotask.view;

import me.yangtong.todotask.R;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class LongClickWindow extends PopupWindow {

	private OnClickListener onClickListener;
	private View contentView;
	
	
	private TextView textFinish;
	private TextView textGiveUp;
	private TextView textEdit;
	
	public LongClickWindow(Activity context,OnClickListener onClickListener){
		this.onClickListener = onClickListener;
		LayoutInflater layoutInflater = context.getLayoutInflater();
		contentView = layoutInflater.inflate(R.layout.popup_longclick, null);
		this.setWidth(LayoutParams.WRAP_CONTENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setContentView(contentView);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
        // 刷新状态  
        this.update();  
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);  
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);  
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.AnimationPreview);  
        
        textFinish = (TextView)contentView.findViewById(R.id.text_finished);
        textGiveUp = (TextView)contentView.findViewById(R.id.text_giveup);
        textEdit = (TextView)contentView.findViewById(R.id.text_edit);
        
        textFinish.setOnClickListener(mOnClickListener);
        textGiveUp.setOnClickListener(mOnClickListener);
        textEdit.setOnClickListener(mOnClickListener);
	}
	
	OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onClickListener.onClick(v);
			dismiss();
		}
	};
	
}
