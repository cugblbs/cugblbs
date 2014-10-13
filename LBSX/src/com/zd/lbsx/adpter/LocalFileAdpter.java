package com.zd.lbsx.adpter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zd.lbsx.R;
import com.zd.lbsx.bean.LocalFileBean;

public class LocalFileAdpter extends BaseAdapter {

	ArrayList<LocalFileBean> mlist;
	Context mContext;
	LayoutInflater mInflater;

	public LocalFileAdpter(ArrayList<LocalFileBean> list, Context context) {
		this.mlist = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int pos) {
		return mlist.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return mlist.get(pos).hashCode();
	}

	@Override
	public View getView(int pos, View contentView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (contentView == null) {
			viewHolder = new ViewHolder();
			contentView = mInflater.inflate(R.layout.fg_find_item, null);
			viewHolder.tv_name = (TextView) contentView
					.findViewById(R.id.tv_name);
			viewHolder.tv_size = (TextView) contentView
					.findViewById(R.id.tv_size);
			viewHolder.cbx=(CheckBox)contentView.findViewById(R.id.cbx_select);
			contentView.setTag(viewHolder);

		}else {
			viewHolder=(ViewHolder) contentView.getTag();
		}
		viewHolder.tv_name.setText(mlist.get(pos).getFilename());
		viewHolder.tv_size.setText(mlist.get(pos).getSize()/1024+"KB");
		if(!viewHolder.cbx.isChecked()){
			viewHolder.cbx.setVisibility(View.GONE);	
		}
		return contentView;
	}

	class ViewHolder {
		TextView tv_name;
		TextView tv_size;
		CheckBox cbx;
	}

}
