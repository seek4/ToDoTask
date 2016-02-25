package me.yangtong.todotask;

import java.util.List;
import java.util.zip.Inflater;

import me.yangtong.L;
import me.yangtong.todotask.data.Task;
import me.yangtong.todotask.data.TaskOperate;
import me.yangtong.view.SummaryArc;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RecentFragment extends Fragment {

	private View view;
	private Context mContext;

	TaskOperate taskOperate;

	private LinearLayout layoutSummary;
	private SummaryArc viewSummary;

	private ExpandableListView listTask;
	private AllTasksAdapter allTasksAdapter;

	private Button btnSwitch;
	private ListView listTimeLine;
	
	private List<Task> allTasks;
	private List<Task> finishedTasks;
	private List<Task> timeExceedTasks;
	private List<Task> giveUpTasks;

	private String[] groups = { "已完成", "未完成", "已放弃" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		L.i("RecentFragment onCreateView");
		view = inflater.inflate(R.layout.fragment_recent, container, false);
		mContext = getActivity();
		initData();
		initView();	
		return view;
	}

	@Override
	public void onResume() {
		L.i("RecentFragment  onResume");
		super.onResume();
		updateView();
	}

	private void initView(){
		layoutSummary = (LinearLayout) view.findViewById(R.id.layout_summary);
		listTask = (ExpandableListView) view
				.findViewById(R.id.list_recent_tasks);
		viewSummary = new SummaryArc(mContext, finishedTasks.size(),
				timeExceedTasks.size(), giveUpTasks.size());
		layoutSummary.addView(viewSummary);
		allTasksAdapter = new AllTasksAdapter(mContext);
		listTask.setAdapter(allTasksAdapter);
		listTask.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent(mContext, ViewActivity.class);
				intent.putExtra("id", (int) id);
				mContext.startActivity(intent);
				return false;
			}
		});
		btnSwitch = (Button)view.findViewById(R.id.btn_switch);
		btnSwitch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 没想要时间该怎么排布，是时间段，不是时间点，而且有重叠部分
//				if(listTimeLine.getVisibility()==View.VISIBLE){
//					listTimeLine.setVisibility(View.GONE);
//					listTask.setVisibility(View.VISIBLE);
//				}else {
//					listTimeLine.setVisibility(View.VISIBLE);
//					listTask.setVisibility(View.GONE);
//				}
			}
		});
		listTimeLine = (ListView)view.findViewById(R.id.list_timeline);
	}
	
	private void initData(){
		
		taskOperate = TaskOperate.getInstance(mContext);
		finishedTasks = taskOperate.getLastWeekFinishedTask();
		timeExceedTasks = taskOperate.getLastWeekTimeExceedTask();
		giveUpTasks = taskOperate.getLastWeekGiveUpTask();
	}
	
	
	private void updateView() {
		finishedTasks = taskOperate.getLastWeekFinishedTask();
		timeExceedTasks = taskOperate.getLastWeekTimeExceedTask();
		giveUpTasks = taskOperate.getLastWeekGiveUpTask();
		viewSummary.setFinishedNum(finishedTasks.size());
		viewSummary.setTimeExceedNum(timeExceedTasks.size());
		viewSummary.setGiveUpNum(giveUpTasks.size());
		allTasksAdapter.notifyDataSetChanged();
	}

	class AllTasksAdapter extends BaseExpandableListAdapter {

		private LayoutInflater mLayoutInflater;

		public AllTasksAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getGroupCount() {
			return groups.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			switch (groupPosition) {
			case 0:
				return finishedTasks.size();
			case 1:
				return timeExceedTasks.size();
			case 2:

			default:
				return giveUpTasks.size();
			}
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}

		@Override
		public Task getChild(int groupPosition, int childPosition) {
			switch (groupPosition) {
			case 0:
				return finishedTasks.get(childPosition);
			case 1:
				return timeExceedTasks.get(childPosition);
			case 2:

			default:
				return giveUpTasks.get(childPosition);
			}
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			switch (groupPosition) {
			case 0:
				return finishedTasks.get(childPosition).getId();
			case 1:
				return timeExceedTasks.get(childPosition).getId();
			case 2:

			default:
				return giveUpTasks.get(childPosition).getId();
			}
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			convertView = mLayoutInflater.inflate(R.layout.recent_list_group,
					null);
			TextView textTitle = (TextView) convertView
					.findViewById(R.id.text_group_name);
			textTitle.setText(groups[groupPosition]);
			TextView textNum = (TextView) convertView
					.findViewById(R.id.text_num);
			textNum.setText("" + getChildrenCount(groupPosition));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ChildViewHolder viewHolder = new ChildViewHolder();
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(
						R.layout.recent_list_group_item, null);
				viewHolder.textTitle = (TextView) convertView
						.findViewById(R.id.text_title);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ChildViewHolder) convertView.getTag();
			}

			viewHolder.textTitle.setText(getChild(groupPosition, childPosition)
					.getTitle());
			return convertView;
		}

		private class ChildViewHolder {
			TextView textTitle;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}
	
	
	class TimeLineAdapter extends BaseAdapter{
		LayoutInflater mInflater;
		List<Task> mTasks;
		
		public TimeLineAdapter(Context context,List<Task> tasks){
			mInflater = LayoutInflater.from(context);
			mTasks = tasks;
		}

		
		public void updateData(List<Task> tasks){
			this.mTasks = tasks;
		}
		
		@Override
		public int getCount() {
			return mTasks.size();
		}

		@Override
		public Task getItem(int position) {
			return mTasks.get(position);
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.item_recent_timeline, null);
				viewHolder = new ViewHolder();
				viewHolder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
				viewHolder.textStatus = (TextView)convertView.findViewById(R.id.text_status);
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			Task task = getItem(position);
			if(task!=null){
				
			}
			return convertView;
		}
		
		class ViewHolder{
			public TextView textTitle;
			public TextView textStatus;
		}
		
	}

}
