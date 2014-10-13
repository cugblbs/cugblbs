package com.zd.lbsx.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zd.lbsx.R;
import com.zd.lbsx.XActInfoDetail;
import com.zd.lbsx.adpter.InfoAdapter;
import com.zd.lbsx.bean.Info;
import com.zd.lbsx.utils.HtmlCaptureUtils;
import com.zd.lbsx.utils.StringUtils;

public class XFgInfoNew extends XFgBase {

	private ListView info_list;
	private InfoAdapter adapter;
	private ArrayList<Info> list = new ArrayList<Info>();
	String contentString="";

	@Override
	protected int setFragmentView() {
		return R.layout.fg_info_new;
	}

	@Override
	protected void initView(View v) {
		info_list = (ListView) v.findViewById(R.id.info_list);
		new CaptureHtmlTask().execute();
	}

	@Override
	protected void initListener() {
		info_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				new CaptureHtmlContentTask(list.get(position).getNews_content()).execute();
			}
		});

	}

	@Override
	protected void initData() {

	}

	class CaptureHtmlTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			list.clear();
			list = HtmlCaptureUtils
					.captureHtml("http://www.cugb.edu.cn/index.action");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (list != null) {
				adapter = new InfoAdapter(list, getActivity());
				info_list.setAdapter(adapter);
			} else {
				Log.i("list------------->", "null");
			}
			super.onPostExecute(result);
		}
	}
	
	class CaptureHtmlContentTask extends AsyncTask<Void, Void, Void> {

		String url="";
		public CaptureHtmlContentTask(String url){
			this.url=url;
		}
		
		@Override
		protected void onPreExecute() {
			contentString="";
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			contentString = HtmlCaptureUtils.CaptureContent(url);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (!StringUtils.isEmpty(contentString)) {
				Intent intent=new Intent();			
				intent.putExtra("content", "  "+contentString);
				intent.setClass(getActivity(), XActInfoDetail.class);
				startActivity(intent);
			} else {
				Log.i("contentString------------->", "null");
			}
			super.onPostExecute(result);
		}
	}
}
