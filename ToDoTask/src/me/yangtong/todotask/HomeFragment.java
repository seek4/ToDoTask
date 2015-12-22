package me.yangtong.todotask;

import java.util.List;

import me.yangtong.L;
import me.yangtong.Utils;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import me.yangtong.todotask.view.LongClickWindow;
import me.yangtong.view.LineProgressView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private TaskOperate taskOperate;
	private Context mContext;

	private View view;
	private ListView listTask;
	private Button btnAdd;
	private TextView textNoTask;

	private List<Task> todayTasks;

	private TaskAdapter taskAdapter;

	WindowManager.LayoutParams windowLp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
//		if (view == null || taskOperate == null) {
			view = inflater.inflate(R.layout.fragment_home, container, false);
			listTask = (ListView) view.findViewById(R.id.list_task);
			btnAdd = (Button) view.findViewById(R.id.btn_add);
			textNoTask = (TextView) view.findViewById(R.id.text_notask);
			taskOperate = TaskOperate.getInstance(mContext);
			todayTasks = taskOperate.getTodayTodoTask();
			if (todayTasks == null || todayTasks.size() == 0) {
				textNoTask.setVisibility(View.VISIBLE);
				listTask.setVisibility(View.GONE);
			} else {
				textNoTask.setVisibility(View.GONE);
				listTask.setVisibility(View.VISIBLE);
				taskAdapter = new TaskAdapter(getActivity());
				listTask.setAdapter(taskAdapter);
			}
			listTask.setOnItemClickListener(itemClickListener);
			listTask.setOnItemLongClickListener(itemLongClickListener);
			btnAdd.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Skip into add activity
					Intent intent = new Intent(mContext, AddActivity.class);
					startActivity(intent);
				}
			});
//		}
		return view;
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(mContext, ViewActivity.class);
			intent.putExtra("id", (int)id);
			mContext.startActivity(intent);
		}
	};

	OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {

		LongClickWindow longClickWindow;

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			longClickWindow = new LongClickWindow(getActivity(),
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							switch (v.getId()) {
							case R.id.text_finished:
								L.i("text_finished clicked");
								taskOperate.setTaskFinished(taskAdapter
										.getItem(position));
								updateContentView();
								break;
							case R.id.text_giveup:
								L.i("text_giveup clicked");
								taskOperate.setTaskGiveUp(taskAdapter
										.getItem(position));
								updateContentView();
								break;
							case R.id.text_edit:
								L.i("text_edit clicked");
								break;
							default:
								break;
							}
						}
					});
			longClickWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// 恢复原来背景色
					windowLp = getActivity().getWindow().getAttributes();
					windowLp.alpha = 1f;
					getActivity().getWindow().setAttributes(windowLp);
				}
			});
			// 背景颜色变暗
			windowLp = getActivity().getWindow().getAttributes();
			windowLp.alpha = 0.7f;
			getActivity().getWindow().setAttributes(windowLp);
			longClickWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
			return true;
		}
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateContentView();
	}

	private void updateContentView() {
		todayTasks = taskOperate.getTodayTodoTask();
		if (todayTasks == null || todayTasks.size() == 0) {
			textNoTask.setVisibility(View.VISIBLE);
			listTask.setVisibility(View.GONE);
		} else {
			textNoTask.setVisibility(View.GONE);
			listTask.setVisibility(View.VISIBLE);
			if (taskAdapter != null) {
				taskAdapter.notifyDataSetChanged();
			} else {
				taskAdapter = new TaskAdapter(getActivity());
				listTask.setAdapter(taskAdapter);
			}
		}
	}

	class TaskAdapter extends BaseAdapter {

		LayoutInflater mLayoutInflater;

		public TaskAdapter(Context context) {
			// TODO Auto-generated constructor stub
			mLayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return todayTasks.size();
		}

		@Override
		public Task getItem(int position) {
			// TODO Auto-generated method stub
			return todayTasks.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return todayTasks.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = new ViewHolder();
			Task task = getItem(position);
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.item_task, null);
				viewHolder.layoutItem = (RelativeLayout) convertView
						.findViewById(R.id.layout_item);
				viewHolder.textTitle = (TextView) convertView
						.findViewById(R.id.text_title);
//				viewHolder.textLevel = (TextView) convertView
//						.findViewById(R.id.text_level);
				viewHolder.textStartTime = (TextView)convertView
						.findViewById(R.id.text_startTime);
				viewHolder.textEndTime = (TextView) convertView
						.findViewById(R.id.text_endtime);
				viewHolder.viewProgress = (LineProgressView)convertView
						.findViewById(R.id.view_progress);	
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textTitle.setText(task.getTitle());
//			viewHolder.textLevel.setText("" + task.getLevel());
			viewHolder.textStartTime.setText(Utils.getDateByMilli(task.startTime));
			viewHolder.textEndTime.setText(Utils.getDateByMilli(task
					.getEndTime()));
			viewHolder.viewProgress.setProgress(task.getProgress());
			setLayoutBkg(viewHolder.layoutItem, task.getLevel());				
			return convertView;
		}

		public void setLayoutBkg(RelativeLayout layout,int level){
			switch (level) {
			case 1:
				layout.setBackgroundResource(R.drawable.bkg_task_item_1);		
				break;
			case 2:
				layout.setBackgroundResource(R.drawable.bkg_task_item_2);		
				break;
			case 3:
				layout.setBackgroundResource(R.drawable.bkg_task_item_3);		
				break;
			default:
				break;
			}
		}
		
		
		private class ViewHolder {
			public RelativeLayout layoutItem;
			public TextView textTitle;
//			public TextView textLevel;
			public TextView textStartTime;
			public TextView textEndTime;
			public LineProgressView viewProgress;
		}

	}

}
