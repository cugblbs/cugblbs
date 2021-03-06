package com.zd.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class UploadUtil {
	private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 100*1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    /**
     * android上传文件到服务器
     * @param file  需要上传的文件
     * @param RequestURL  请求的rul
     * @return  返回响应的内容
     */
    public static String uploadFile(File file,String RequestURL,Handler handler)
    {
        String result = null;
        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n"; 
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");   
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 
            
            if(file!=null)
            {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png  
                 */
                
                sb.append("Content-Disposition: form-data; name=\"application\"; filename=\""+file.getName()+"\""+LINE_END); 
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                
                FileInputStream is = new FileInputStream(file);
                
                byte[] bytes = new byte[1024];
                int len = 0;
                
                long totalLen = 0;
                
                while((len=is.read(bytes))!=-1)	//从文件输入流上上读
                {
                    dos.write(bytes, 0, len);	//写入流
                    
                    totalLen += len;
                    
//                    Log.i("<<<<<<<<<<<<<", "nowStaturs="+totalLen);
                    
                    Message msg = new Message();//修改界面的进度条
                    msg.what = 1;
                    msg.arg1 = (int) (ProgressDialog.MAX_PROGRESS*totalLen/is.getChannel().size());
                    handler.sendMessage(msg);
                    
                    
                }
                
                Log.i("<<<<<<<<<<<<<>>>>", ""+is.getChannel().size());
                Log.i("<<<<<<<<<<<<<>>>>", ""+file.length());
                Log.i("<<<<<<<<<<<<<>>>>", ""+totalLen);
                
                
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                dos.close();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流  
                 */
//                int res = conn.getResponseCode();  
//                Log.e(TAG, "response code:"+res);
//                if(res==200)
//                {
//                    Log.e(TAG, "request success");
                    InputStream input =  conn.getInputStream();
                    StringBuffer sb1= new StringBuffer();
                    int ss ;
                    while((ss=input.read())!=-1)
                    {
                        sb1.append((char)ss);
                    }
                    result = sb1.toString();
//                    Log.e(TAG, "result : "+ result);
//                }
//                else{
//                    Log.e(TAG, "request error");
//                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
