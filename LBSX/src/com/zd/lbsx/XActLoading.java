package com.zd.lbsx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class XActLoading extends XActBase {

	private Handler mHandler=new Handler();
	private ImageView iv_loading;
	private TextView tv;
	private TranslateAnimation translateAnimation;
	@Override
	protected int setContentLayout() {
		return R.layout.act_loading;
	}

	@Override
	protected void initView() {
		iv_loading=(ImageView)findViewById(R.id.iv_loading);
		tv=(TextView) findViewById(R.id.app_name);
		translateAnimation = new TranslateAnimation(0.0f, 0.0f,0.0f,-250.0f);  
		//设置动画时间                
		translateAnimation.setDuration(1000);  
		translateAnimation.setFillAfter(true);
		iv_loading.startAnimation(translateAnimation); 
		tv.startAnimation(translateAnimation);
	}

	@Override
	protected void initListener() {
	}

	@Override
	protected void initData() {
		
	}

	@Override
	public void onClick(View v) {
		
	}
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mHandler.postDelayed(new Runnable() {			
			@Override
			public void run() {
				Intent intent=new Intent(XActLoading.this,XActMain.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
		
	}


}
