package me.yangtong.todotask;

import java.util.List;

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
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
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
		layoutSummary = (LinearLayout) view.findViewById(R.id.layout_summary);
		listTask = (ExpandableListView) view
				.findViewById(R.id.list_recent_tasks);
		mContext = getActivity();
		taskOperate = TaskOperate.getInstance(mContext);

		finishedTasks = taskOperate.getLastWeekFinishedTask();
		timeExceedTasks = taskOperate.getLastWeekTimeExceedTask();
		giveUpTasks = taskOperate.getLastWeekGiveUpTask();

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
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		updateView();
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
			// TODO Auto-generated method stub
			return groups.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			return true;
		}

	}

}
