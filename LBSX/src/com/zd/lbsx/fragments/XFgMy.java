package com.zd.lbsx.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zd.lbsx.R;

public class XFgMy extends XFgBase implements OnClickListener{
	
	private WebView contentWebView = null;
	private TextView title = null;
	private EditText content = null;
	private Button button = null;
	
	@Override
	protected int setFragmentView() {
		return R.layout.fg_my;
	}
	@SuppressLint("JavascriptInterface")
	@Override
	protected void initView(View v) {
		contentWebView = (WebView)v.findViewById(R.id.webview);
		title = (TextView)v.findViewById(R.id.Biaoti);
		content = (EditText)v.findViewById(R.id.Neirong);
		button = (Button) v.findViewById(R.id.Fatie);
		 //启用javascript
		contentWebView.getSettings().setJavaScriptEnabled(true);
		contentWebView.setWebViewClient(new MyWebViewClient());
		contentWebView.loadUrl("http://192.168.1.170:8080/Android/Start");
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { 
				String Title = title.getText().toString();
				String Content = content.getText().toString();
				contentWebView.loadUrl("javascript:javacalljs('" + Title + "','" + Content + "')");
			}
		});
		contentWebView.addJavascriptInterface(this, "post");
	}
	@Override
	protected void initListener() {
	}
	@Override
	protected void initData() {
	}
	
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//用了webview
	}
	
	@Override
	public void onClick(View v) {
		
	}
	class MyWebViewClient extends WebViewClient {  
	    @Override  
	    public boolean shouldOverrideUrlLoading(WebView view, String url){  
	    // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边  
	       view.loadUrl(url);  
	       return true;  
	       }  
	}  
	
}
