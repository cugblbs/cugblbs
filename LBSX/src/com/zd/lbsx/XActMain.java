package com.zd.lbsx;
/*
 * create by Juice Zhu 2014.7.31
 */
import com.zd.lbsx.fragments.XFgBase;
import com.zd.lbsx.fragments.XFgFind;
import com.zd.lbsx.fragments.XFgInfo;
import com.zd.lbsx.fragments.XFgMy;
import com.zd.lbsx.fragments.XFgRoute;

import android.view.View;
import android.widget.RadioGroup;

public class XActMain extends XActBase implements
		android.widget.RadioGroup.OnCheckedChangeListener {

	private RadioGroup radioGroup;
	
	@Override
	public void onClick(View v) {
		//¼àÌý°´Å¥°´¶¯
		//this is the test message 
	}

	@Override
	protected int setContentLayout() {
		return R.layout.act_main;
	}

	@Override
	protected void initView() {
		radioGroup=(RadioGroup) findViewById(R.id.main_tab_group);
	}

	@Override
	protected void initListener() {
		radioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	protected void initData() {
		XFgRoute fg = new XFgRoute();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.content_frame, fg).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		XFgBase fg = null;
		switch (checkedId) {
		case R.id.rb_route:
			fg = new XFgRoute();
			break;
		case R.id.rb_info:
			fg = new XFgInfo();
			break;
		case R.id.rb_my:
			fg=new XFgMy();
			break;
		case R.id.rb_find:
			fg=new XFgFind();
			break;
		default:
			fg = new XFgRoute();
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fg).commit();
	}

}
