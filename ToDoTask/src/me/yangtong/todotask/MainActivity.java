package me.yangtong.todotask;

import com.astuetz.PagerSlidingTabStrip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Window;

public class MainActivity extends ActionBarActivity {
	
	private HomeFragment homeFragment;
	
	private OtherFragment otherFragment;
	
	private RecentFragment recentFragment;
	
	private PagerSlidingTabStrip tabs;
	
	private DisplayMetrics dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		dm = getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager)this.findViewById(R.id.pager);
		tabs  =(PagerSlidingTabStrip)this.findViewById(R.id.tabs);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs.setViewPager(pager);
		setTabsValue();
		pager.setCurrentItem(1);
	}
	
	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "最近一周", "待完成", "其他" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (recentFragment == null) {
					recentFragment = new RecentFragment();
				}
				return recentFragment;
			case 1:
				if (homeFragment == null) {
					homeFragment = new HomeFragment();
				}
				return homeFragment;
			case 2:
				if (otherFragment == null) {
					otherFragment = new OtherFragment();
				}
				return otherFragment;
			default:
				return null;
			}
		}
	}
	
	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}
}
