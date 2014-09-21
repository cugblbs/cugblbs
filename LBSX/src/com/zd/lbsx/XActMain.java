package com.zd.lbsx;

/*
 * create by Juice Zhu 2014.7.31
 */
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.zd.lbsx.fragments.XFgBase;
import com.zd.lbsx.fragments.XFgFind;
import com.zd.lbsx.fragments.XFgInfo;
import com.zd.lbsx.fragments.XFgMy;
import com.zd.lbsx.fragments.XFgRoute;

public class XActMain extends XActBase implements
		android.widget.RadioGroup.OnCheckedChangeListener {

	private RadioGroup radioGroup;
	private XFgBase xfg=null;

	@Override
	public void onClick(View v) {
	}

	@Override
	protected int setContentLayout() {
		return R.layout.act_main;
	}

	@Override
	protected void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
	}

	@Override
	protected void initListener() {
		radioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	protected void initData() {
		XFgRoute fg = null;
		Intent intent = getIntent();
		String startString = intent.getStringExtra("start");
		String endsString = intent.getStringExtra("end");
		if (startString != null && endsString != null) {
			fg = new XFgRoute(startString, endsString);
		} else {
			fg = new XFgRoute();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fg).commit();
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
			//registInterface((XFgInfo) fg);
			break;
		case R.id.rb_my:
			fg = new XFgMy();
			break;
		case R.id.rb_find:
			fg = new XFgFind();
			break;
		default:
			fg = new XFgRoute();
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fg).commit();
	}

//	public interface BackWeview {
//		boolean backwebview();
//	}
//
//	private void registInterface(XFgInfo fg) {
//		xfg = fg;
//	}
//
//	@Override
//	public void onBackPressed() {
//		if (xfg != null) {
//			boolean islastone = ((XFgInfo) xfg).backwebview();
//			if (!islastone) {
//				this.finish();
//			}
//		}
//	}

	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	 //监听按下返回键
	 if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==
	 KeyEvent.ACTION_DOWN) {
	 /*已经是最后一个fragment
	 * getSupportFragmentManager()或者getFragmentManager()
	 * 具体要看你add to back stack 是用哪个*/
	 //if no more history in stack
	 Log.i("s", "main_on_back");
	 if (XActMain.this.getFragmentManager().getBackStackEntryCount() == 0) {
	 //显示退出框业务逻辑
	 finish();
	 return true;
	 }
	 }
	 return super.onKeyDown(keyCode, event);
	 }
}
