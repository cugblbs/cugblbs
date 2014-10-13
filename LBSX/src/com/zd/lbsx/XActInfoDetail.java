package com.zd.lbsx;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.zd.lbsx.utils.StringUtils;

public class XActInfoDetail extends XActBase {

	private TextView tv_detail;
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int setContentLayout() {
		return R.layout.info_detail;
	}

	@Override
	protected void initView() {
		tv_detail=(TextView) findViewById(R.id.tv_detail);
		tv_detail.setMovementMethod(ScrollingMovementMethod.getInstance()); 
	}

	@Override
	protected void initListener() {
		
	}

	@Override
	protected void initData() {
		Intent intent=getIntent();
		String content=intent.getStringExtra("content");
		if(!StringUtils.isEmpty(content)){
			tv_detail.setText(content);
		}
	}
	
}
