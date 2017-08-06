package com.example.gsm;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Factory_map extends Activity implements LocationListener{
	 TextView mDisplay;
	 Button button01;
	 Button button02;
	 Button button03;
	 Button button04;
	 AsyncTask<Void, Void, Void> mRegisterTask;
	    private boolean getService = false;		//�O�_�w�}�ҩw��A��
	    String str_longitude;
	    String str_latitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_factory_map);
		  testLocationProvider();
		  WebView myWebView = (WebView) findViewById(R.id.webView);
		 // TextView check = (TextView) findViewById(R.id.textView1);		
		  button02 =(Button)findViewById(R.id.button1);
		  button02.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {

	            	 Toast.makeText(Factory_map.this, "�Y�ɪŦæa��",Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent();
	            	 
	            	 intent.setClass(Factory_map.this, MainActivity.class);
	            	 startActivity(intent); 
	            	 
	            	 }         

	        });     
		  button03 =(Button)findViewById(R.id.button2);
		  button03.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {

	            	 Toast.makeText(Factory_map.this, "�u�t��T",Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent();
	            	 Bundle bundlse = new Bundle();
	            	 Bundle bundle = new Bundle();
	            	 bundle.putString("uid",bundlse.getString("uid"));
	            	 intent.setClass(Factory_map.this, Factory.class);
	            	 intent.putExtras(bundle);
	            	 startActivity(intent); 
	            }         

	        });     
		  button04 =(Button)findViewById(R.id.button3);
		  button04.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {

	            	 Toast.makeText(Factory_map.this, "�]�w",Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent();
	            	 intent.setClass(Factory_map.this, Setting.class);
	            	 startActivity(intent); 

	            }         

	        });     
		  
		  
		//  check.setText(str_longitude + "  "+str_latitude); 
		    myWebView.getSettings().setJavaScriptEnabled(true); 
	        myWebView.requestFocus();
	        myWebView.setWebViewClient(new MyWebViewClient());	      
	       
	      //  myWebView.loadUrl("http://140.127.220.216:8080/rwd_map2.php?longitude="+"120.348629"+"&latitude="+"22.648767");
	          myWebView.loadUrl("http://140.127.220.216:8080/rwd_map2.php?longitude="+"120.2871463"+"&latitude="+"22.7327844");
	     //   check.setText("http://140.127.220.216:8080/rwd_map.php2?longitude="+str_longitude+"&latitude="+str_latitude); 
	}
	  private LocationManager lms;
	    private String bestProvider = LocationManager.GPS_PROVIDER;	//�̨θ�T���Ѫ�
	    private void locationServiceInitial() {
	    	lms = (LocationManager) getSystemService(LOCATION_SERVICE);	//���o�t�Ωw��A��
	    	Criteria criteria = new Criteria();	//��T���Ѫ̿���з�
	    	bestProvider = lms.getBestProvider(criteria, true);	//��ܺ�ǫ׳̰������Ѫ�
	    	Location location = lms.getLastKnownLocation(bestProvider);
	    	getLocation(location);
	    }

	    private void getLocation(Location location) {	//�N�w���T��ܦb�e����
	    	if(location != null) {    	
	   		
	    		Double longitude = location.getLongitude();	//���o�g��
	    		Double latitude = location.getLatitude();	//���o�n�� 
	    		
	    	
	    	}
	    	else {	    		
	    		 location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    		 Double longitude= location.getLongitude();//���o�g��
	    		 Double latitude = location.getLatitude();//���o�n��
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
				//�A�ȴ��Ѫ̡B��s�W�v60000�@��=1�����B�̵u�Z���B�a�I���ܮɩI�s����
			}
		}

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			if(getService) {
				lms.removeUpdates(this);	//���}�����ɰ����s
			}
		}

		@Override
		protected void onRestart() {	//�q�䥦�������^��
			// TODO Auto-generated method stub
			super.onRestart();
			testLocationProvider();
		}
		 private void testLocationProvider() {
		        //���o�t�Ωw��A��
		        LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
		        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
		        	//�p�GGPS�κ����w��}�ҡA�I�slocationServiceInitial()��s��m
		        	getService = true;	//�T�{�}�ҩw��A��
		        	locationServiceInitial();
		        } else {
		        	Toast.makeText(this, "�ж}�ҩw��A��", Toast.LENGTH_LONG).show();
		        	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//�}�ҳ]�w����
		        }
		    }
		    
	 private class MyWebViewClient extends WebViewClient {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            return super.shouldOverrideUrlLoading(view, url);
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.factory_map, menu);
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
