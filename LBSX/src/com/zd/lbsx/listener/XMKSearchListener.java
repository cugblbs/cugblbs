package com.zd.lbsx.listener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKStep;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.zd.lbsx.R;

public class XMKSearchListener implements MKSearchListener {

	MapView MyMapView = null;
	Activity mainActivity = null;
	public MKPlanNode defaultStart = null;

	public XMKSearchListener(Activity activity, MapView mapView) {
		this.MyMapView = mapView;
		this.mainActivity = activity;

	}

	@Override
	public void onGetAddrResult(MKAddrInfo res, int error) {
		if (error != 0) {
            String str = String.format("错误号：%d", error);
            Toast.makeText(mainActivity, str,
                    Toast.LENGTH_LONG).show();
            return;
        }
        // 地图移动到该点
		MyMapView.getController().animateTo(res.geoPt);
        if (res.type == MKAddrInfo.MK_GEOCODE) {
            // 地理编码：通过地址检索坐标点
            String strInfo = String.format("纬度：%f 经度：%f",
                    res.geoPt.getLatitudeE6() / 1e6,
                    res.geoPt.getLongitudeE6() / 1e6);
            Toast.makeText(mainActivity, strInfo,
                    Toast.LENGTH_LONG).show();
        }
        if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
            // 反地理编码：通过坐标点检索详细地址及周边poi
            String strInfo = res.strAddr;
            Toast.makeText(mainActivity, strInfo,
                    Toast.LENGTH_LONG).show();

        }
        // 生成ItemizedOverlay图层用来标注结果点
        ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(
                null, MyMapView);
        // 生成Item
        OverlayItem item = new OverlayItem(res.geoPt, "", null);
        // 得到需要标在地图上的资源
        Drawable marker = mainActivity.getResources().getDrawable(
                R.drawable.ic_launcher);
        // 为maker定义位置和边界
        marker.setBounds(0, 0, marker.getIntrinsicWidth(),
                marker.getIntrinsicHeight());
        // 给item设置marker
        item.setMarker(marker);
        // 在图层上添加item
        itemOverlay.addItem(item);

        // 清除地图其他图层
        MyMapView.getOverlays().clear();
        // 添加一个标注ItemizedOverlay图层
        MyMapView.getOverlays().add(itemOverlay);
        // 执行刷新使生效
        MyMapView.refresh();

	}

	@Override
	public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiDetailSearchResult(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

 
	@Override
	public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		// 首先判断是否搜索到结果
		if (arg2 != 0 || arg0 == null) {
			Toast.makeText(mainActivity.getApplicationContext(), "没有找到结果！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 将结果绘制到地图上
		if (arg0.getCurrentNumPois() > 0) {
			PoiOverlay poiOverlay = new PoiOverlay(mainActivity, MyMapView);
			poiOverlay.setData(arg0.getAllPoi());
			MyMapView.getOverlays().clear();
			MyMapView.getOverlays().add(poiOverlay);
			MyMapView.refresh();
			// 当arg1为2（公交线路）或4（地铁线路）时， poi坐标为空
			for (MKPoiInfo info : arg0.getAllPoi()) {
				if (info.pt != null) {
					MyMapView.getController().animateTo(info.pt);
					defaultStart = new MKPlanNode();
					defaultStart.pt = info.pt;
					break;
				}
			}
		}

	}

	@Override
	public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetTransitRouteResult(MKTransitRouteResult result, int arg1) {
	}

	@Override
	public void onGetWalkingRouteResult(MKWalkingRouteResult result, int arg1) {
		if (result == null) {
			return;
		}
		System.out.println("这是路径显示的回调方法`");
		MyMapView.getOverlays().clear();
		RouteOverlay routeOverlay = new RouteOverlay(mainActivity, MyMapView);
		routeOverlay.setData(result.getPlan(0).getRoute(0));
		MyMapView.getOverlays().add(routeOverlay);
		MyMapView.refresh();
		GeoPoint point = new GeoPoint((int) (39.997161 * 1E6),
				(int) (116.354123 * 1E6));
		MKRoute route = result.getPlan(0).getRoute(0);
		TextView routeTextView = (TextView) mainActivity
				.findViewById(R.id.route_info);
		routeTextView.setText("");
		routeTextView.setVisibility(View.VISIBLE);
		for (int i = 0; i < route.getNumSteps(); i++) {
			MKStep step = route.getStep(i);
			routeTextView.append(i + 1 + "、" + step.getContent().toString()
					+ "\n");

		}
	}

}
