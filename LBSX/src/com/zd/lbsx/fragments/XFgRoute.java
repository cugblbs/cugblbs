package com.zd.lbsx.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.zd.application.MyApplication;
import com.zd.lbsx.R;
import com.zd.lbsx.XActSearchRoute;
import com.zd.lbsx.adpter.AdpSpinner;
import com.zd.lbsx.listener.XMKSearchListener;

@SuppressLint("ValidFragment")
public class XFgRoute extends XFgBase implements OnItemSelectedListener,
		OnClickListener {
	private Spinner spin_address;
	private Button bt_search_route;
	private static BMapManager bMapManager;
	private MapView mapView;
	private MKSearch mkSearch;
	private ArrayList<String> list = new ArrayList<String>();
	private String startString;
	private String endString;

	public XFgRoute(String startString, String endString) {
		super();
		this.startString = startString;
		this.endString = endString;
	}

	public XFgRoute() {
		super();
	}

	@Override
	protected int setFragmentView() {
		return R.layout.fg_route;
	}

	@Override
	protected void initView(View v) {
		spin_address = (Spinner) v.findViewById(R.id.spinner);
		bt_search_route = (Button) v.findViewById(R.id.bt_search_route);
	}

	@Override
	protected void initListener() {
		spin_address.setOnItemSelectedListener(this);
		bt_search_route.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		// ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(
		// getActivity(), R.layout.spinner_item, new String[] {
		// "中国地质大学(北京)9号楼", "中国地质大学(北京)综合楼" });

		list.add("中国地质大学(北京)9号楼");
		list.add("中国地质大学-东门");
		list.add("中国地质大学海洋学院");
		list.add("中国地质大学综合楼");
		list.add("中国地质大学19号楼");
		list.add("中国地质大学18号楼");
		list.add("中国地质大学学生食堂");
		list.add("中国地质大学教工食堂");
		list.add("中国地质大学夏日广场");
		list.add("中国地质大学教3楼");
		list.add("中国地质大学教2楼");
		list.add("中国地质大学运动场");
		list.add("中国地质大学图书馆");

		AdpSpinner spinAdapter = new AdpSpinner(getActivity(), list);
		spin_address.setAdapter(spinAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		bMapManager = new BMapManager(getActivity().getApplicationContext());
		bMapManager.init(MyApplication.getAK(), null);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mapView = (MapView) getActivity().findViewById(R.id.bmapsView);
		mapView.setBuiltInZoomControls(false);
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		MapController mMapController = mapView.getController();
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		GeoPoint point = new GeoPoint((int) (39.997161 * 1E6),
				(int) (116.354123 * 1E6));
		// 设置地图中心点
		mMapController.setCenter(point);
		// 设置地图zoom级别
		mMapController.setZoom(20);
		mkSearch = new MKSearch();
		mkSearch.init(bMapManager,
				new XMKSearchListener(getActivity(), mapView));
	}

	@Override
	public void onDestroy() {
		mapView.destroy();
		if (bMapManager != null) {
			bMapManager.destroy();
			bMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	public void onPause() {
		mapView.onPause();
		if (bMapManager != null) {
			bMapManager.stop();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		mapView.onResume();
		if (bMapManager != null) {
			bMapManager.start();
		}
		super.onResume();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long id) {
		// 获取点中项的字符串
		String keyString = arg0.getAdapter().getItem(position).toString();
		// 判断非空
		if (keyString.equals("")) {
			Toast.makeText(getActivity(), "请重新选择项目", 1000).show();
		} else {
			Log.i("onItemSelected------->", "onItemSelected");
			mkSearch.poiSearchInCity("北京", keyString);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_search_route:
			Intent intent = new Intent(getActivity(), XActSearchRoute.class);
			startActivityForResult(intent, 100);
			break;
		default:
			break;
		}
	}

	class SearchRouteTask extends AsyncTask<String, Integer, String> {
		Geocoder geocoder;
		GeoPoint sgeoPoint = null;
		GeoPoint egeoPoint = null;
		MKPlanNode startMkPlanNode, endMkPlanNode;

		@Override
		protected void onPreExecute() {
			Log.i("Task is prepare to run", "Task is prepare to run");
			geocoder=new Geocoder(getActivity());
			startMkPlanNode = new MKPlanNode();
			endMkPlanNode = new MKPlanNode();
		}

		@Override
		protected String doInBackground(String... arg0) {
			Log.i("Task is doinbackground", "Task is doinbackground");
			try {
				List<Address> tempList=new ArrayList<Address>();
				tempList = geocoder.getFromLocationName(startString, 1);
				while (tempList.size() == 0) {
					tempList = geocoder.getFromLocationName(startString, 1);
				}
				Address startAddress = tempList.get(0);
				double slon = startAddress.getLongitude();
				double slat = startAddress.getLatitude();
				sgeoPoint = new GeoPoint((int) (slat * 1E6), (int) (slon * 1E6));
				tempList = geocoder.getFromLocationName(endString, 1);
				while (tempList.size() == 0) {
					tempList = geocoder.getFromLocationName(endString, 1);
				}
				Address endAddress = tempList.get(0);
				double elon = endAddress.getLongitude();
				double elat = endAddress.getLatitude();
				egeoPoint = new GeoPoint((int) (elat * 1E6), (int) (elon * 1E6));
				startMkPlanNode.pt = sgeoPoint;
				endMkPlanNode.pt = egeoPoint;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "result";
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("Task is finished", "Task is finished~");
			mkSearch.walkingSearch(null, startMkPlanNode, null, endMkPlanNode);
		}

	}

	private SearchRouteTask searchRouteTask = null;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 200) {
			startString=data.getStringExtra("start");
			endString=data.getStringExtra("end");
			new SearchRouteTask().execute("");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
