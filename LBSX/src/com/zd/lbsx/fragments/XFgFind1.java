package com.zd.lbsx.fragments;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zd.lbsx.R;
import com.zd.util.UploadUtil;

public class XFgFind1 extends XFgBase implements OnClickListener{
	 private static final String TAG = "uploadImage";
	    private static String requestURL = "http://geekzhu.xicp.net/XgMy/upload";
	    private Button selectImage,uploadImage;
	    private ImageView imageView;
	    
	    public String picPath = null;
	    
	    int progressStatus = 0;//进度条当前状态
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
	    public void onClick(View v) {
	        switch (v.getId()) {
	        case R.id.selectImage:
	            /***
	             * 这个是调用android内置的intent，来过滤图片文件   ，同时也可以过滤其他的  
	             */
	            Intent intent = new Intent();
//	            intent.setType("image/*");
	            intent.setType("application/doc");
	            intent.setAction(Intent.ACTION_GET_CONTENT);       
	            startActivityForResult(intent, 1);
	            break;
	        case R.id.uploadImage:
//	        	Log.i("<><><><>", picPath);
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
							
							request = UploadUtil.uploadFile( file, requestURL, handler);
							
							if(request != null){
								Message msg = new Message();
								msg.what = 0;
								handler.sendMessage(msg);
							}
						}
					}).start();
	            	
	            }
	            break;
	        default:
	            break;
	        }
	    }

//	    
	    @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if(resultCode==Activity.RESULT_OK)
	        {
	            /**
	             * 当选择的图片不为空的话，在获取到图片的途径  
	             */
	            Uri uri = data.getData();
	            Log.e(TAG, "uri = "+ uri); 
	            
	            
	            String path = uri.toString();
	            if(path.endsWith("doc")||path.endsWith("txt")||path.endsWith("pdf"))
                {
                    //显示文本信息
	            	String filePath = path.substring(7);
	            	Log.i("filePath", filePath);
	            	picPath = filePath;
	            	alertSuccess();
	            	
	            	Log.i("picPath", picPath);
                }else{
                	alert();
                }
	            
	            
	            
//	            try {
////	            	String[] pojo = {MediaStore.Images.Media.DATA};
//	                String[] pojo = {MediaStore.Images.Media.DATA};
//	                
//	                Cursor cursor = getActivity().managedQuery(uri, pojo, null, null,null);
//	                if(cursor!=null)
//	                {
//	                    ContentResolver cr = getActivity().getContentResolver();
//	                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//	                    cursor.moveToFirst();
//	                    String path = cursor.getString(colunm_index);
//	                    /***
//	                     * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，这样的话，我们判断文件的后缀名
//	                     * 如果是图片格式的话，那么才可以   
//	                     */
////	                    if(path.endsWith("jpg")||path.endsWith("png"))
//	                    if(path.endsWith("doc")||path.endsWith("txt")||path.endsWith("pdf"))
//	                    {
//	                        picPath = path;
////	                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
////	                        imageView.setImageBitmap(bitmap);
//	                        Log.i("<<<<",picPath);
//	                    }else{alert();}
//	                }else{alert();}
//	                
//	            } catch (Exception e) {
//	            }
	        }
	        
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	    
	    private void alertSuccess()
	    {
	        Dialog dialog = new AlertDialog.Builder(getActivity())
	        .setTitle("提示")
//	        .setMessage("您选择的不是有效的图片")
	        .setMessage("选择成功")
	        .setPositiveButton("确定",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog,
	                            int which) {
	                    }
	                })
	        .create();
	        
	        dialog.show();
	    }
	    
	    private void alert()
	    {
	        Dialog dialog = new AlertDialog.Builder(getActivity())
	        .setTitle("提示")
//	        .setMessage("您选择的不是有效的图片")
	        .setMessage("您选择的不是有效的文本文件")
	        .setPositiveButton("确定",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog,
	                            int which) {
	                        picPath = null;
	                    }
	                })
	        .create();
	        
	        dialog.show();
	    }

		@Override
		protected int setFragmentView() {
			// TODO Auto-generated method stub
			return R.layout.fg_find1;
		}

		@Override
		protected void initView(View v) {
			// TODO Auto-generated method stub
			selectImage = (Button) v.findViewById(R.id.selectImage);
	        uploadImage = (Button) v.findViewById(R.id.uploadImage);
	        
	        
	        imageView = (ImageView) v.findViewById(R.id.imageView);
		}

		@Override
		protected void initListener() {
			// TODO Auto-generated method stub
			selectImage.setOnClickListener(this);
	        uploadImage.setOnClickListener(this);
		}

		@Override
		protected void initData() {
			// TODO Auto-generated method stub
			
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
