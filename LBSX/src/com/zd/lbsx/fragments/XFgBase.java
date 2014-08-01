package com.zd.lbsx.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class XFgBase extends Fragment {

	private Context mContext;
	private LayoutInflater mInflater;
    /**
     * 初始化布局界面
     * @return 返回布局界面layout的id
     */
    protected abstract int setFragmentView();

    /**
     * 通过v.findViewById()初始化控件
     * @param v
     */
    protected abstract void initView(View v);

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 数据绑定,数组变量初始化
     */
    protected abstract void initData();
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	mContext=getActivity();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	this.mInflater=inflater;
    	View view=inflater.inflate(setFragmentView(), null);
        initView(view);
        initListener();
        initData();
    	return view;
    }
	
}
