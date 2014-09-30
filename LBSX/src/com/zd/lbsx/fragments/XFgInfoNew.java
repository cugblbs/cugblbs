package com.zd.lbsx.fragments;

import java.util.ArrayList;

import com.baidu.location.ad;
import com.baidu.location.r;
import com.zd.lbsx.R;
import com.zd.lbsx.adpter.InfoAdapter;
import com.zd.lbsx.bean.Info;

import android.view.View;
import android.widget.ListView;

public class XFgInfoNew extends XFgBase {

	private ListView info_list;
	private InfoAdapter adapter;
	private ArrayList<Info> list = new ArrayList<Info>();

	@Override
	protected int setFragmentView() {
		return R.layout.fg_info_new;
	}

	@Override
	protected void initView(View v) {
		info_list = (ListView) v.findViewById(R.id.info_list);
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void initData() {
		for (int i = 0; i < 10; i++) {
			Info info = new Info(i, "newsName" + i,
					"this is the news content,this is just a test message~");
			list.add(info);
		}
		adapter = new InfoAdapter(list, getActivity());
		info_list.setAdapter(adapter);

	}

}
