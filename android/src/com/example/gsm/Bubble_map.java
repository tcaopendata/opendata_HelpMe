package com.example.gsm;



import static com.example.gsm.CommonUtilities.TAG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Bubble_map extends Activity {
	static String lat_;
	static String long_;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//http://140.127.220.216:8080/json/send.php?lat=
		final String uid ;
		Bundle bundle = getIntent().getExtras();
		uid=bundle.getString("uid");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bubble_map);
		Button Button03 = (Button)findViewById(R.id.button3);
		Button03.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {
	            	            	 
	            	 Intent intent = new Intent();
	              	 Bundle bundle = new Bundle();
	               	 intent.setClass(Bubble_map.this, Select.class);
	                 intent.putExtras(bundle);
	              	 startActivity(intent);       	
	            }

	        });     
		Button button4 = (Button)findViewById(R.id.button4);
		button4.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {
	            	            	 
	            	 Intent intent = new Intent();
	              	 Bundle bundle = new Bundle();
	              	 bundle.putString("uid",uid);
	               	 intent.setClass(Bubble_map.this, Setting.class);
	                 intent.putExtras(bundle);
	              	 startActivity(intent);       	
	            }

	        });     
		Button Button＿03 = (Button)findViewById(R.id.button2);
		Button＿03.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {
	            	            	 
	            	 Intent intent = new Intent();
	              	 Bundle bundle = new Bundle();
	               	 intent.setClass(Bubble_map.this, MainActivity.class);
	                 intent.putExtras(bundle);
	              	 startActivity(intent);       	
	            }

	        });   //button4
		Button Button01 = (Button)findViewById(R.id.button1);
		Button01.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {
	            	            	 
	            	
	            	 final String[] item = {"公益團體","弱勢團體義賣","捐發票團體"};

	            	    AlertDialog.Builder dialog_list = new AlertDialog.Builder(Bubble_map.this);
	            	    dialog_list.setTitle("選擇類別");
	            	    dialog_list.setItems(item, new DialogInterface.OnClickListener(){
	            	         @Override

	            	         //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
	            	         public void onClick(DialogInterface dialog, int which) {
	            	                // TODO Auto-generated method stub
	            	        	  final ProgressDialog myDialog = ProgressDialog.show(Bubble_map.this, "上傳中", "資料上傳中...請稍後(please wait...)");
	            	                
	            	                new Thread()
	            	                {
	            	                    public void run()
	            	                    {
	            	                        try
	            	                        {
	            	                        	 gets("http://140.127.220.216:8080/web_rtc.php");
	            	                            sleep(3000);
	            	                            
	            	                            }
	            	                        catch(Exception e)
	            	                        {
	            	                            e.printStackTrace();
	            	                        }
	            	                        finally
	            	                        {
	            	                        	myDialog.dismiss();
	            	                        }
	            	                    }  
	            	                }.start();
	            	                
	            	          }
	            	    });
	            	    dialog_list.show();
	            	
	            }

	        });     
		
	    WebView myWebView = (WebView) findViewById(R.id.webView);	   
		  myWebView.getSettings().setJavaScriptEnabled(true); 
	        myWebView.requestFocus();
	        myWebView.setWebViewClient(new MyWebViewClient());	   
	       // Bundle bundle = getIntent().getExtras();
long_ = bundle.getString("str_longitude");
lat_ =bundle.getString("str_latitude");
	    myWebView.loadUrl("http://140.127.220.216:8080/bubble.php?longitude="+bundle.getString("str_longitude")+"&latitude="+bundle.getString("str_latitude"));
	}
	////
	
	
	
	////
	 private class MyWebViewClient extends WebViewClient {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            return super.shouldOverrideUrlLoading(view, url);
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bubble_map, menu);
		return true;
	}
	 private static String getStringFromInputStream(InputStream is)
             throws IOException {
     ByteArrayOutputStream os = new ByteArrayOutputStream();
     // 模板代码 必须熟练
     byte[] buffer = new byte[1024];
     int len = -1;
     // 一定要写len=is.read(buffer)
     // 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
     while ((len = is.read(buffer)) != -1) {
             os.write(buffer, 0, len);
     }
     is.close();
     String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
     os.close();
     return state;
}
	 
	private static void gets(final String endpoint){
		 Thread thread = new Thread(){
		      public void run(){
	  HttpURLConnection conn = null;

   // String data = "username=" + URLEncoder.encode(username) + "&password="+ URLEncoder.encode(password);
    String url = "http://140.127.220.216:8080/json/send.php?lat="+lat_+"&long="+long_;//+ data;
    try {
            // 利用string url构建URL对象
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
           	 InputStream is = conn.getInputStream();
                String state = getStringFromInputStream(is);
                String[] aa =   state.split(" ");
                    Log.v(TAG, "Str連線成功" + state + "' 來自 " + url);                    
                   // str =   state ;
            } else {
                    Log.i(TAG, "失敗" + responseCode);
            }

    } catch (Exception e) {
   	  Log.v(TAG, "get53 '" + e + "' to " + url);

            e.printStackTrace();
    } finally {

            if (conn != null) {
                    conn.disconnect();
            }
         
          
    }
		      }
		      
		 };
		 thread.start();
	        
	      }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		
		}
		return super.onOptionsItemSelected(item);
	}
}
