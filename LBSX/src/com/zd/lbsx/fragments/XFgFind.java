package com.zd.lbsx.fragments;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.zd.lbsx.R;

import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class XFgFind extends XFgBase {

	private String basePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath().toString();
	private String uploadFile = basePath + "/gps_stats.txt";
	private String srcPath = basePath + "/gps_stats.txt";
	private String actionUrl = "http://192.168.191.1:8080/Upload/upload";

	private Button bt_upload;

	@Override
	protected int setFragmentView() {
		return R.layout.fg_find;
	}

	@Override
	protected void initView(View v) {
		bt_upload = (Button) v.findViewById(R.id.bt_upload);

	}

	@Override
	protected void initListener() {
		bt_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						uploadFile();
					}
				}).start();
			}
		});

	}

	@Override
	protected void initData() {

	}

	/* 上传文件至Server的方法 */
	private void uploadFile() {

		System.out.println(Environment.getExternalStorageDirectory()
				.getAbsolutePath().toString());
		
		String uploadUrl = "http://192.168.95.1:8080/Upload/upload";
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(false);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			DataOutputStream dos = new DataOutputStream(
					httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ srcPath.substring(srcPath.lastIndexOf("/") + 1)
					+ "\""
					+ end);
			dos.writeBytes(end);

//			String filePathString="Environment.getExternalStorageDirectory().getAbsolutePath()"
//					+ "/books";
//			
//			File file = new File(filePathString);
//			if (!file.exists())
//				file.mkdir();
//			File trueFile = new File(srcPath);
			FileInputStream fis = new FileInputStream(srcPath);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);

			}
			fis.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();

			System.out.println("上传成功");
			dos.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
