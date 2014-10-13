package com.zd.lbsx;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/*
 * create by Juice Zhu
 */
import android.view.View.OnClickListener;

public abstract class XActBase extends FragmentActivity implements OnClickListener {

    /**
     * 初始化布局界面
     * @return 返回布局界面layout的id
     */
    protected abstract int setContentLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();
    /**
     *初始化监听器
     */
    protected abstract void initListener();
    /**
     * 初始化数据
     */
    protected abstract void initData();
    
    protected Context context;
	
    
    @Override
    protected void onCreate(Bundle arg0) {
    	super.onCreate(arg0);
    	setContentView(setContentLayout());
    	context=this;
        initView();
        initListener();
        initData();
    }

}
