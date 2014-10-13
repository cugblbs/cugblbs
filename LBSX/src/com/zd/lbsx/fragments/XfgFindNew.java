package com.zd.lbsx.fragments;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zd.lbsx.R;
import com.zd.lbsx.adpter.LocalFileAdpter;
import com.zd.lbsx.bean.LocalFileBean;
import com.zd.lbsx.utils.FileUtils;
import com.zd.util.UploadUtil;

public class XfgFindNew extends XFgBase implements OnItemClickListener{

    private static String requestURL = "http://geekzhu.xicp.net/XgMy/upload";
	private ListView file_list;
	private LocalFileAdpter localFileAdp;
	private ArrayList<LocalFileBean> list = new ArrayList<LocalFileBean>();
    ProgressDialog pd;//进度条
    
    Handler handler=new Handler(){
    	public void handleMessage(Message msg) {
    		if(msg.what == 1){
    			if(msg.arg1 >= 100){
    				pd.dismiss();
    				Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
    			}else{
    				pd.setProgress(msg.arg1);
    			}
    		}
    	};
    };

	@Override
	protected int setFragmentView() {
		return R.layout.fg_find_new;
	}

	@Override
	protected void initView(View v) {
		file_list = (ListView) v.findViewById(R.id.file_list);
	}

	@Override
	protected void initListener() {
		file_list.setOnItemClickListener(this);
	}

	@Override
	protected void initData() {
		localFileAdp = new LocalFileAdpter(list, getActivity());
		file_list.setAdapter(localFileAdp);
		new ScanFileTask().execute();

	}

	class ScanFileTask extends AsyncTask<Void, File, Void> {
		
		@Override
		protected void onPreExecute() {
			list.clear();
			super.onPreExecute();
		}
		
		private void tranverseAllFile(File file) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					publishProgress(f);
					tranverseAllFile(f);
				} else {
					publishProgress(f);
					String filename = f.getName();
					String filePath=f.getAbsolutePath();
					long filesize =  f.length();
					if (FileUtils.isStudyFile(f)) {
						LocalFileBean localFileBean = new LocalFileBean(
								filename, filesize,filePath);
						list.add(localFileBean);
						publishProgress(f);
					}
				}
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			tranverseAllFile(Environment.getExternalStorageDirectory());
			return null;
		}
		
		@Override
		protected void onProgressUpdate(File... values) {
			File file=values[0];
			if(FileUtils.isStudyFile(file)){  
				localFileAdp.notifyDataSetChanged();
			}
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			localFileAdp.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
//		CheckBox cbx=(CheckBox) view.findViewById(R.id.cbx_select);
//		cbx.setVisibility(View.VISIBLE);
//		cbx.setChecked(true);
		LocalFileBean localbean=list.get(pos);
		System.out.println(localbean.getUrlPath());
		String picPath=localbean.getUrlPath();
    	if(picPath == null){
    		Log.i("<<<<<","picPath是空的");
    		Toast.makeText(getActivity(), "请先选择文档~", Toast.LENGTH_SHORT).show();
    		return;
    	}
        final File file = new File(picPath);
        if(file!=null)
        {
        	//显示进度条
        	showProgress(getActivity());
        	
        	new Thread(new Runnable() {

				@Override
				public void run() { 
					String request = null;
					
					request = UploadUtil.uploadFile(file, requestURL, handler);
					
					if(request != null){
						Message msg = new Message();
						msg.what = 0;
						handler.sendMessage(msg);
					}
				}
			}).start();
        	
        }
	}
	
	public void showProgress (Activity activity){
		//将进度条的完成进度重设为0
		pd = new ProgressDialog(activity);
		pd.setMax((int) com.zd.util.ProgressDialog.MAX_PROGRESS);
		//设置对话框标题
		pd.setTitle("正在上传");
		//设置对话框显示的内容
		pd.setMessage("进度");
		//设置对话框不能用取消按钮关闭
		pd.setCancelable(false);
		//设置对话框的进度风格
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//设置对话框的进度条是否显示进度
		pd.setIndeterminate(false);
		pd.show();
	}


}
