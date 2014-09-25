package com.zd.lbsx.adpter;
import java.util.ArrayList;

import com.zd.lbsx.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class AdpSpinner extends BaseAdapter {
	
	private ArrayList<String> list;
	private LayoutInflater mInflater;
	
	public AdpSpinner(Context context,ArrayList<String> list) {
		super();
		this.list=list;
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int pos) {
		return list.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return list.get(pos).hashCode();
	}

	@Override
	public View getView(int pos, View convetView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if(convetView==null){
			viewHolder=new ViewHolder();
			convetView =mInflater.inflate(R.layout.spinner_item, null);
			viewHolder.tv=(TextView) convetView.findViewById(R.id.spinner_item);
			convetView.setTag(viewHolder);
		}
		else{
			viewHolder=(ViewHolder) convetView.getTag();
		}
		viewHolder.tv.setText(list.get(pos).toString());
		return convetView;
	}
	
	
	class ViewHolder
	{
		TextView tv;
	}

}
