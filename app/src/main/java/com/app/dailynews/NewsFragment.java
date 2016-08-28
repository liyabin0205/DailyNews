package com.app.dailynews;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.xmlpull.v1.XmlPullParser;

import com.app.dailynews.model.News;
import loopj.android.image.SmartImageView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewsFragment extends Fragment {

	private ViewPager mViewPaper;
	private List<ImageView> images;
	private List<View> dots;
	private int currentItem;
	// 记录上一次点的位置
	private int oldPosition = 0;
	// 存放图片的id
	private int[] imageIds = new int[] { R.drawable.pic1, R.drawable.pic2,
			R.drawable.pic3, R.drawable.pic4, R.drawable.pic5 };
	// 存放图片的标题
	private String[] titles = new String[] { "泛娱乐时代思考", "新片全国上映", "夏季高温避暑", "高校招生结束",
			"全民健身何处去" };
	private TextView title;
	private ViewPagerAdapter adapter;
	private ScheduledExecutorService scheduledExecutorService;

	List<News> newsList;
	ListView lv;
	/**
	 * 接收子线程传递过来的数据
	 */

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					mViewPaper.setCurrentItem(currentItem);
					break;
				case 1:
					lv.setAdapter(new MyAdapter());
					break;
			}

		}
	};

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View newsView = inflater
				.inflate(R.layout.newsfragment, container, false);// 关联布局文件
		mViewPaper = (ViewPager) newsView.findViewById(R.id.vp);
		lv = (ListView) newsView.findViewById(R.id.lv);
		// 显示的图片
		images = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(NewsFragment.this.getActivity());
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
		}

		// 显示的小点
		dots = new ArrayList<View>();
		dots.add(newsView.findViewById(R.id.dot_0));
		dots.add(newsView.findViewById(R.id.dot_1));
		dots.add(newsView.findViewById(R.id.dot_2));
		dots.add(newsView.findViewById(R.id.dot_3));
		dots.add(newsView.findViewById(R.id.dot_4));

		title = (TextView) newsView.findViewById(R.id.title);
		title.setText(titles[0]);

		adapter = new ViewPagerAdapter();
		mViewPaper.setAdapter(adapter);

		mViewPaper
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						title.setText(titles[position]);
						dots.get(position).setBackgroundResource(
								R.drawable.dot_normal);
						dots.get(oldPosition).setBackgroundResource(
								R.drawable.dot_focused);

						oldPosition = position;
						currentItem = position;
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});
		getNewsInfo();

		return newsView;
	}

	@SuppressWarnings("unchecked")
	private void init() {

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				News news = newsList.get(position);
				String detail = news.getDetail();
				String title = news.getTitle();
				String url = news.getImageUrl();
				String time = news.getComment();
				Intent intent = new Intent(NewsFragment.this.getActivity(),DetailsActivity.class);
				intent.putExtra("extra",position);
				intent.putExtra("extra1",detail);
				intent.putExtra("extra2",title);
				intent.putExtra("extra3",url);
				intent.putExtra("extra4",time);
				startActivity(intent);
			}
		});
	}

	/**
	 * 自定义Adapter
	 */
	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView(images.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			view.addView(images.get(position));
			return images.get(position);
		}

	}

	/**
	 * 利用线程池定时执行动画轮播
	 */
	@Override
	public void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 2,
				4, TimeUnit.SECONDS);
	}

	private class ViewPageTask implements Runnable {

		@Override
		public void run() {
			currentItem = (currentItem + 1) % imageIds.length;

			handler.sendEmptyMessage(0);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	class MyAdapter extends BaseAdapter{
		//得到模型层中元素的数量，用来确定listview需要有多少个条目
		@Override
		public int getCount(){
			return newsList.size();
		}


		@Override
		//返回一个View对象，作为listview的条目显示至界面
		public View getView(int position, View convertView, ViewGroup parent) {

			News news = newsList.get(position);
			View v = null;
			ViewHolder mHolder;
			if(convertView == null){
				v = View.inflate(NewsFragment.this.getActivity(), R.layout.item, null);
				mHolder = new ViewHolder();
				//把布局文件中所有组件的对象封装至ViewHolder对象中
				mHolder.tv_title = (TextView) v.findViewById(R.id.tv_title);
				mHolder.tv_detail = (TextView) v.findViewById(R.id.tv_detail);
				mHolder.tv_comment = (TextView) v.findViewById(R.id.tv_comment);
				mHolder.tv_discuss = (TextView) v.findViewById(R.id.tv_discuss);
				mHolder.siv = (SmartImageView) v.findViewById(R.id.iv);
				//把ViewHolder对象封装至View对象中
				v.setTag(mHolder);
			}
			else{
				v = convertView;
				mHolder = (ViewHolder) v.getTag();
			}
			//给三个文本框设置内容
			mHolder.tv_title.setText(news.getTitle());

			mHolder.tv_detail.setText(news.getDetail());

			mHolder.tv_comment.setText(news.getComment());

			mHolder.tv_discuss.setText(news.getDiscuss());

			//给新闻图片imageview设置内容
			mHolder.siv.setImageUrl(news.getImageUrl());

			init();

			return v;
		}

		class ViewHolder{
			//条目的布局文件中有什么组件，这里就定义什么属性
			TextView tv_title;
			TextView tv_detail;
			TextView tv_comment;
			TextView tv_discuss;
			SmartImageView siv;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}
	private void getNewsInfo() {
		Thread t = new Thread(){
			@Override
			public void run() {
				String path = "http://10.11.56.215:8080/news.xml";
				//String path = "http://127.0.0.1:8080/news.xml";
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					//发送http GET请求，获取相应码
					if(conn.getResponseCode() == 200){
						InputStream is = conn.getInputStream();
						//使用pull解析器，解析这个流
						parseNewsXml(is);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private void parseNewsXml(InputStream is) {
		XmlPullParser xp = Xml.newPullParser();
		try {
			xp.setInput(is, "utf-8");
			//对节点的事件类型进行判断，就可以知道当前节点是什么节点
			int type = xp.getEventType();
			News news = null;
			while(type != XmlPullParser.END_DOCUMENT){
				switch (type) {
					case XmlPullParser.START_TAG:
						if("newslist".equals(xp.getName())){
							newsList = new ArrayList<News>();
						}
						else if("news".equals(xp.getName())){
							news = new News();
						}
						else if("title".equals(xp.getName())){
							String title = xp.nextText();
							news.setTitle(title);
						}
						else if("detail".equals(xp.getName())){
							String detail = xp.nextText();
							news.setDetail(detail);
						}
						else if("comment".equals(xp.getName())){
							String comment = xp.nextText();
							news.setComment(comment);
						}
						else if("discuss".equals(xp.getName())){
							String discuss = xp.nextText();
							news.setDiscuss(discuss);
						}
						else if("image".equals(xp.getName())){
							String image = xp.nextText();
							news.setImageUrl(image);
						}
						break;
					case XmlPullParser.END_TAG:
						if("news".equals(xp.getName())){
							newsList.add(news);
						}
						break;

				}
				//解析完当前节点后，把指针移动至下一个节点，并返回它的事件类型
				type = xp.next();
			}


			//发消息，让主线程设置listview的适配器，如果消息不需要携带数据，可以发送空消息
			handler.sendEmptyMessage(1);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
