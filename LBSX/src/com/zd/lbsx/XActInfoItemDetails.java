package com.zd.lbsx;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class XActInfoItemDetails extends XActBase{
	private  WebView info_item_webview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// JavaScriptÊ¹ÄÜ
		WebSettings websettings =info_item_webview.getSettings();
	    websettings.setJavaScriptEnabled(true);
	}
	@Override
	protected int setContentLayout() {
		return R.layout.act_info_item_details_view;
	}
	@Override
	protected void initView() {
		info_item_webview = (WebView)findViewById(R.id.webview);
	}
	@Override
	protected void initData() {
		
		info_item_webview.loadUrl("file:///android_asset/info_item_details.html");
		//info_item_webview.loadUrl("http://www.taobao.com/");
		info_item_webview.setWebViewClient(new WebViewClient());
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
