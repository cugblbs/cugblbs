package com.zd.lbsx.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zd.lbsx.R;
import com.zd.lbsx.XActInfoItemDetails;

public class XFgInfo extends XFgBase implements  OnItemClickListener{
	private ListView listView_Info;
	private ArrayList<HashMap<String, Object>> listItems_Info;
	private SimpleAdapter listItemAdapter;
	private int[]images;
	private String[] titles , briefs,times;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	protected int setFragmentView() {
		return R.layout.fg_info;
	}
	@Override
	protected void initView(View v) {
		listView_Info = (ListView) v.findViewById(R.id.ListView_Info);
	}
	@Override
	protected void initData() {
		listItems_Info =new ArrayList<HashMap<String, Object>>();
		images = new int[] { R.drawable.cat, R.drawable.dog,// 相当于获取到的
				R.drawable.friend, R.drawable.love, R.drawable.lovely,R.drawable.cat, R.drawable.dog,
				R.drawable.friend, R.drawable.love, R.drawable.lovely };
		titles = new String[] { "cat", "dog", "friend", "love",
				"lovely" ,"cat", "dog", "friend", "love",
				"lovely"};
		briefs = new String[] { "猫咪真可爱", "超逗的狗", "他们是好朋友", "它们好有爱",
				"阿狸真可爱", "猫咪真可爱", "超逗的狗", "他们是好朋友", "它们好有爱",
				"阿狸真可爱" };
		times = new String[] { "2014-07-29", "2014-08-01",
				"2014-08-01", "2014-08-01", "2014-08-01","2014-07-29", "2014-08-01",
				"2014-08-01", "2014-08-01", "2014-08-01" };
		for (int i = 0; i < 10; i++) {// 从对象中拿出值
			HashMap<String, Object> listItem_Info = new HashMap<String, Object>();
			listItem_Info.put("ItemImage", images[i]);
			listItem_Info.put("ItemTitle", titles[i]);
			listItem_Info.put("ItemBrief", briefs[i]);
			listItem_Info.put("ItemTime", times[i]);
			listItems_Info.add(listItem_Info);
		}
		
		 listItemAdapter = new SimpleAdapter(getActivity(),// this是当前Activity的对象
					listItems_Info,// 数据源 为填充数据后的ArrayList类型的对象
					R.layout.fg_info_item,// 子项的布局.xml文件名
					new String[] { "ItemImage", "ItemTitle", "ItemBrief",
							"ItemTime" },
					// 这个String数组中的元素就是list对象中的列
					new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemBrief,
							R.id.ItemTime });
	    // 添加并显示
	    listView_Info.setAdapter(listItemAdapter);
	
	}
	@Override
	protected void initListener() {
		listView_Info.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		/*Toast toast  = Toast.makeText(getActivity(), "点击我",Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		//创建图片视图对象
		ImageView  imageView = new ImageView(getActivity());
		//设置图片
		imageView.setImageResource (images[position]);
		
		//imageView.setImageResource ((Integer)listItems_Info.get(position).get("ImageItem"));
		//获得toast布局
		LinearLayout toastView = (LinearLayout)toast.getView();
		//设置此布局为横向的
		toastView.setOrientation(LinearLayout.HORIZONTAL);
		//将imageView 加入到此布局中的第一个位置
		toastView.addView(imageView,0);
		toast.show();*/
		Intent intent = new Intent();
		intent.putExtra("position", position);
		intent.setClass(getActivity(),XActInfoItemDetails.class);
		startActivity(intent);
	}	
}
