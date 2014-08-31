package com.zd.lbsx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PersonalPage extends Activity {
	Button fanhui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onetiezi);
		fanhui = (Button) findViewById(R.id.Return);
		fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(PersonalPage.this, MainActivity.class);
//				startActivity(intent);
				PersonalPage.this.finish();
			}
		});
		int[] ImageIds = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
		String[] arr1 = { "Zldevil2011", "Paradiser", "Karvenyi", "许大三",
				"evenzero007" };
		String[] arr2 = { "周五了，有人玩么？", "劳比，找妹子", "空调呢", "烦死了", "打死你" };
		final List<Map<String, Object>> personallistItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < arr1.length; ++i) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("Touxiang", ImageIds[i]);
			listItem.put("Author", arr1[i]);
			listItem.put("Title", arr2[i]);
			personallistItems.add(listItem);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, personallistItems,
				R.layout.personalitem, new String[] { "Touxiang", "Author", "Title" },
				new int[] { R.id.Personaltouxiang, R.id.Personauthor, R.id.title });
		ListView list = (ListView) findViewById(R.id.HuifuList);
		list.setAdapter(simpleAdapter);
	}
}
