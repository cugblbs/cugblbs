package com.zd.lbsx.fragments;

import java.io.File;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.zd.lbsx.R;
import com.zd.lbsx.adpter.LocalFileAdpter;
import com.zd.lbsx.bean.LocalFileBean;
import com.zd.lbsx.utils.FileUtils;

public class XfgFindNew extends XFgBase implements OnItemClickListener{

	private ListView file_list;
	private LocalFileAdpter localFileAdp;
	private ArrayList<LocalFileBean> list = new ArrayList<LocalFileBean>();

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
					long filesize =  f.length();
					if (FileUtils.isStudyFile(f)) {
						LocalFileBean localFileBean = new LocalFileBean(
								filename, filesize);
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
		CheckBox cbx=(CheckBox) view.findViewById(R.id.cbx_select);
		cbx.setVisibility(View.VISIBLE);
		cbx.setChecked(true);
	}



}
