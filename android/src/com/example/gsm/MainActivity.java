package com.example.gsm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.gsm.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.gsm.CommonUtilities.EXTRA_MESSAGE;
import static com.example.gsm.CommonUtilities.SENDER_ID;
import static com.example.gsm.CommonUtilities.SERVER_URL;
import static com.example.gsm.CommonUtilities.TAG;
import static com.example.gsm.CommonUtilities.EXTRA_MESSAGE;
import com.google.android.gcm.GCMRegistrar;
import com.example.gsm.R;
import com.example.gsm.R.string;
import com.example.gsm.ServerUtilities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class MainActivity extends Activity  implements LocationListener {
	
	 static String uid="";
	public static String str = "";
	    AsyncTask<Void, Void, Void> mRegisterTask;
	    private boolean getService = false;		//是否已開啟定位服務
	    static String str_longitude;
	    static String str_latitude;
	    static String info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TelephonyManager mTelManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
		 try{
             gets("http://140.127.220.216:8080/web_rtc.php");
            gets_("");
            Log.v(TAG, "finfo連線成功" + uid ); 
            Intent intent = new Intent();
        	 Bundle bundle = new Bundle();
        	 bundle.putString("uid",uid);
        	 bundle.putString("str_longitude", str_longitude);
        	 bundle.putString("str_latitude", str_latitude);
        	 intent.setClass(MainActivity.this, Bubble_map.class);
         	  intent.putExtras(bundle);
            startActivity(intent); 
            
            }
            catch(Exception e){
            	
            }
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 checkNotNull(SERVER_URL, "SERVER_URL");
		 checkNotNull(SENDER_ID, "SENDER_ID");
		   testLocationProvider();	
		      	
	        GCMRegistrar.checkDevice(this);
	        // Make sure the manifest was properly set - comment out this line
	        // while developing the app, then uncomment it when it's ready.
	        GCMRegistrar.checkManifest(this);	      
	         // mDisplay = (TextView) findViewById(R.id.display);
	           registerReceiver(mHandleMessageReceiver,
	                new IntentFilter(DISPLAY_MESSAGE_ACTION));
	        final String regId = GCMRegistrar.getRegistrationId(this);
	        Log.v(TAG, "regIdregId連線成功" + regId ); 
	        uid = regId;
	        if (regId.equals("")) {
	            // Automatically registers application on startup.
	            GCMRegistrar.register(this, SENDER_ID);	            
	        } 
	        else {
	            // Device is already registered on GCM, check server.
	            if (GCMRegistrar.isRegisteredOnServer(this)) {
	                // Skips registration
	             //   mDisplay.append(getString(R.string.already_registered) + "\n");
	                String serverUrl =SERVER_URL + "register.php?regId="+regId;
	               try{	            	   
	            	   post(serverUrl);
	               }
	               catch(Exception e){
	            	   
	               }
	               
	            } else {
	                // Try to register again, but not in the UI thread.
	                // It's also necessary to cancel the thread onDestroy(),
	                // hence the use of AsyncTask instead of a raw thread.
	                final Context context = this;
	                mRegisterTask = new AsyncTask<Void, Void, Void>() {

	                    @Override
	                    protected Void doInBackground(Void... params) {
	                        boolean registered =
	                                ServerUtilities.register(context, regId);
	                        // At this point all attempts to register with the app
	                        // server failed, so we need to unregister the device
	                        // from GCM - the app will try to register again when
	                        // it is restarted. Note that GCM will send an
	                        // unregistered callback upon completion, but
	                        // GCMIntentService.onUnregistered() will ignore it.
	                        if (!registered) {
	                            GCMRegistrar.unregister(context);
	                        }
	                        
	                        return null;
	                    }

	                    @Override
	                    protected void onPostExecute(Void result) {
	                        mRegisterTask = null;
	                    }

	                };
	                mRegisterTask.execute(null, null, null);
	            }
	            
	         

	        }
	}
	 private void testLocationProvider() {
	        //取得系統定位服務
	        LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
	        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	        	//如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
	        	getService = true;	//確認開啟定位服務
	        	locationServiceInitial();
	        } else {
	        	Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
	        	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//開啟設定頁面
	        }
	    }
	    
	    private LocationManager lms;
	    private String bestProvider = LocationManager.GPS_PROVIDER;	//最佳資訊提供者
	    private void locationServiceInitial() {
	    	lms = (LocationManager) getSystemService(LOCATION_SERVICE);	//取得系統定位服務
	    	Criteria criteria = new Criteria();	//資訊提供者選取標準
	    	bestProvider = lms.getBestProvider(criteria, true);	//選擇精準度最高的提供者
	    	Location location = lms.getLastKnownLocation(bestProvider);
	    	getLocation(location);
	    }

	    private void getLocation(Location location) {	//將定位資訊顯示在畫面中
	    	if(location != null) {    	
	   		
	    		Double longitude = location.getLongitude();	//取得經度
	    		Double latitude = location.getLatitude();	//取得緯度 
	    		
	    	
	    	}
	    	else {	    		
	    		 location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    		 Double longitude= location.getLongitude();//取得經度
	    		 Double latitude = location.getLatitude();//取得緯度
	    	     Toast.makeText(this, String.valueOf(longitude) , Toast.LENGTH_LONG).show();
	    	     str_longitude = String.valueOf(longitude);
	    	     str_latitude = String.valueOf(latitude);
	    	}
	    }
	    
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			if(getService) {
				lms.requestLocationUpdates(bestProvider, 1000, 1, this);
				//服務提供者、更新頻率60000毫秒=1分鐘、最短距離、地點改變時呼叫物件
			}
		}

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			if(getService) {
				lms.removeUpdates(this);	//離開頁面時停止更新
			}
		}

		@Override
		protected void onRestart() {	//從其它頁面跳回時
			// TODO Auto-generated method stub
			super.onRestart();
			testLocationProvider();
		}
	 private class MyWebViewClient extends WebViewClient {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            return super.shouldOverrideUrlLoading(view, url);
	        }
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
     String url = "http://140.127.220.216:8080/web_rtc.php";//+ data;
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
                     str =   state ;
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
	 private static void gets_(final String endpoint){
		 Thread thread = new Thread(){
		      public void run(){
	  HttpURLConnection conn = null;

    // String data = "username=" + URLEncoder.encode(username) + "&password="+ URLEncoder.encode(password);
     String url = "http://140.127.220.216:8080/recode.php?regId="+uid;//+ data;
     Log.v(TAG, "uid連線成功" + uid + "' 來自 " + url);              
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
                     Log.v(TAG, "info連線成功" + state + "' 來自 " + url);                    
                    str =   state ;
                     info = state;
                	
    	         	 
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
	private static void post(String endpoint)
            throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
     
        
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
              throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
      }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	  protected void onDestroy() {
	        if (mRegisterTask != null) {
	            mRegisterTask.cancel(true);
	        }
	        unregisterReceiver(mHandleMessageReceiver);
	        GCMRegistrar.onDestroy(this);
	        super.onDestroy();
	    }

	    private void checkNotNull(Object reference, String name) {
	        if (reference == null) {
	            throw new NullPointerException(
	                    getString(R.string.error_config, name));
	        }
	    }

	    private final BroadcastReceiver mHandleMessageReceiver =
	            new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
	          //  mDisplay.append(newMessage + "\n");
	        }
	    };
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
}
