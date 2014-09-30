package com.zd.lbsx.adpter;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import com.baidu.location.r;
import com.zd.lbsx.R;
import com.zd.lbsx.bean.Info;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoAdapter extends BaseAdapter {

	ArrayList<Info> mList;
	LayoutInflater mInflater;

	public InfoAdapter(ArrayList<Info> list, Context context) {
		this.mList = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int pos) {
		return mList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return mList.get(pos).hashCode();
	}

	@Override
	public View getView(int pos, View contentView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (contentView == null) {
			viewHolder = new ViewHolder();
			contentView = mInflater.inflate(R.layout.fg_info_item, null);
			viewHolder.img = (ImageView) contentView
					.findViewById(R.id.ItemImage);
			viewHolder.tv_title = (TextView) contentView
					.findViewById(R.id.ItemTitle);
			viewHolder.tv_bref = (TextView) contentView
					.findViewById(R.id.ItemBrief);
			viewHolder.tv_time = (TextView) contentView
					.findViewById(R.id.ItemTime);
			contentView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) contentView.getTag();
		}
		viewHolder.img.setImageResource(R.drawable.friend);
		Info info = mList.get(pos);
		viewHolder.tv_title.setText(info.getNews_name());
		viewHolder.tv_bref.setText(info.getNews_content().substring(0, 8)
				+ "...");
		viewHolder.tv_time.setText(new Date().getDate());
		return contentView;
	}

	class ViewHolder {
		ImageView img;
		TextView tv_title, tv_bref, tv_time;
	}

}
