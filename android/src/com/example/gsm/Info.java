package com.example.gsm;

import static com.example.gsm.CommonUtilities.TAG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class Info extends Activity {

	private static String getStringFromInputStream(InputStream is)
            throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    
    byte[] buffer = new byte[1024];
    int len = -1;
    while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
    }
    is.close();
    String state = os.toString();
    os.close();
    return state;
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		Bundle bundle = getIntent().getExtras();
		TextView a = (TextView)findViewById(R.id.text001);
		int dir=bundle.getInt("dir");
		String[] strlist = bundle.getString("info").split(">");
		ArrayList<String> scripts = new ArrayList<String>();
		WebView myWebView = (WebView) findViewById(R.id.webView);	
		if(dir==1){
			myWebView.destroy();
		}
		//String[] a =  (String[])scripts.toArray();
		
		selectitem(strlist,dir).show();
		//String[] ss;
		//ArrayList<String> scripts = new ArrayList<String>();
		
		
		
		//a.setText(bundle.getString("info"));
	}
	public AlertDialog.Builder selectitem(final String[] item,final int dir){
	    AlertDialog.Builder dialog_list = new AlertDialog.Builder(Info.this);
	    dialog_list.setTitle("以下是收尋結果-請選擇單位");
	    
	    dialog_list.setItems(item, new DialogInterface.OnClickListener(){
	         @Override
	         //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
	         public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	                Toast.makeText(Info.this, "你選的是" + item[which], Toast.LENGTH_SHORT).show();
	               // selectitem_t(items).show();  	                
	               //gets_(item[which].split("\r\n")[0],dir);
	                WebView myWebView = (WebView) findViewById(R.id.webView);	
	                myWebView.destroy();
	          	  myWebView.getSettings().setJavaScriptEnabled(true); 
	                 myWebView.requestFocus();
	                 myWebView.setWebViewClient(new MyWebViewClient());	   
	                 Bundle bundle = getIntent().getExtras();
	                 TextView stes = (TextView)findViewById(R.id.text001);	
	                 stes.setText(item[which].split("\r\n")[0]+"\r\n"+item[which].split("\r\n")[1]+"\r\n"+ item[which].split("\r\n")[2]);
	             myWebView.loadUrl("http://140.127.220.216:8080/json/map.php?longitude="+item[which].split("\r\n")[3].split(",")[0]+"&latitude="+item[which].split("\r\n")[3].split(",")[1]);
	          }
	    });
	   return dialog_list;
	    
	
}
	 private class MyWebViewClient extends WebViewClient {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            return super.shouldOverrideUrlLoading(view, url);
	        }
	    }
	private static void gets_(final String endpoint,final String dir){
		   
		 Thread thread = new Thread(){
		      public void run(){
	 HttpURLConnection conn = null;

	// String data = "username=" + URLEncoder.encode(username) + "&password="+ URLEncoder.encode(password);
	String url = "http://140.127.220.216:8080/json/getdata.php?dir="+dir+"&serc="+endpoint;//+ data;
	
	 try {
	        
	        URL mURL = new URL("");
	        conn = (HttpURLConnection) mURL.openConnection();

	        conn.setRequestMethod("GET");
	        conn.setReadTimeout(5000);
	        conn.setConnectTimeout(10000);

	        int responseCode = conn.getResponseCode();
	        if (responseCode == 200) {
	        	InputStream is = conn.getInputStream();
	        	String state = getStringFromInputStream(is);
	            String[] aa =   state.split(" ");	        	                 
	        } else {
	                Log.i(TAG, "失敗" + responseCode);
	        }

	} catch (Exception e) {	
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
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
