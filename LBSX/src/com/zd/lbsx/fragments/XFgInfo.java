package com.zd.lbsx.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zd.lbsx.R;
//import com.zd.lbsx.XActMain.BackWeview;
import com.zd.lbsx.XActInfoItemDetails;
import com.zd.lbsx.fragments.XFgMy.ConnectionDetector;

//public class XFgInfo extends XFgBase implements OnItemClickListener, BackWeview {
public class XFgInfo extends XFgBase implements OnItemClickListener {
	private WebView WebView_Info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int setFragmentView() {
		return R.layout.fg_info;
	}

	@Override
	protected void initView(View v) {
		WebView_Info = (WebView) v.findViewById(R.id.WebView_Info);
	}

	@Override
	protected void initData() {
		WebView_Info.setWebChromeClient(new WebChromeClient());
		WebView_Info.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.i("page start-------------------->", "page start");
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.i("page finished-------------------->", "page finished");
				super.onPageFinished(view, url);
			}
		});

		// WebSettings webSet = WebView_Info.getSettings();
		// webSet.setJavaScriptEnabled(true);
		// WebView_Info.addJavascriptInterface(new DemoJavaScriptInterface(),
		// "demo");

		WebView_Info.loadUrl("http://geekzhu.xicp.net/XgMy/Shownewslist");

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		/**
		 * 按键响应，在WebView查看网页时，按返回键的时候按浏览历史退回，如果不做此处理则整个WebView返回退出
		 */
		WebView_Info.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Log.i("s1", "XFgInfo_on_back");
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK
							&& WebView_Info.canGoBack()) { // 表示按返回键 时的操作
						WebView_Info.goBack(); // 后退
						return true; // 已处理
					}
				}
				return false;
			}
		});
	}

	// @Override
	// public boolean backwebview() {
	// Log.i("back", "back");
	// if (WebView_Info.canGoBack()) {
	// WebView_Info.goBack();
	// return true;
	// } else {
	// return false;
	// }
	// }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		/*
		 * Toast toast = Toast.makeText(getActivity(),
		 * "点击我",Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER, 0, 0);
		 * //创建图片视图对象 ImageView imageView = new ImageView(getActivity()); //设置图片
		 * imageView.setImageResource (images[position]);
		 * 
		 * //imageView.setImageResource
		 * ((Integer)listItems_Info.get(position).get("ImageItem")); //获得toast布局
		 * LinearLayout toastView = (LinearLayout)toast.getView(); //设置此布局为横向的
		 * toastView.setOrientation(LinearLayout.HORIZONTAL); //将imageView
		 * 加入到此布局中的第一个位置 toastView.addView(imageView,0); toast.show();
		 */
		Intent intent = new Intent();
		intent.putExtra("position", position);
		intent.setClass(getActivity(), XActInfoItemDetails.class);
		startActivity(intent);
	}
}
