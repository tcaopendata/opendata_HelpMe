package com.example.gsm;

import com.example.gsm.Factory_map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Factory extends Activity {
	 Button button01;
	 Button button02;
	 Button button03;
	 Button button04;
	 Button button0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_factory);
		  WebView myWebView = (WebView) findViewById(R.id.webView);
		  myWebView.getSettings().setJavaScriptEnabled(true); 
	        myWebView.requestFocus();
	        myWebView.setWebViewClient(new MyWebViewClient());	      
	        Button buttonTel = (Button) findViewById(R.id.button4);
	     // myWebView.loadUrl("http://ww3.epa.gov.tw/public/Case_Add.aspx");
	        Bundle bundle = getIntent().getExtras();
			bundle.getString("info");
	      myWebView.loadUrl("http://140.127.220.216:8080/rwd_map_f.php?longitude="+"120.2871463"+"&latitude="+"22.7327844");
		//	 myWebView.loadUrl("http://140.127.220.216:8080/rwd_map_f.php?longitude="+"120.348629"+"&latitude="+"22.648767");
	    // myWebView.loadUrl("http://140.127.220.216:8080/rwd_map_f.php?longitude="+bundle.getString("longitude")+"&latitude="+bundle.getString("latitude"));
	      //22.648767, 120.348629
			// ?longitude="+str_longitude+"&latitude="+str_latitude
	      button0 =(Button)findViewById(R.id.Button01);
	      button0.setOnClickListener(new Button.OnClickListener(){ 

	            @Override

	            public void onClick(View v) {

	            	 Toast.makeText(Factory.this, "舉發地圖",Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent();
	            	 intent.setClass(Factory.this, Bubble_map.class);
	            	 startActivity(intent); 

	            }         

	        });     
	       button02 =(Button)findViewById(R.id.button1);
			  button02.setOnClickListener(new Button.OnClickListener(){ 

		            @Override

		            public void onClick(View v) {

		            	 Toast.makeText(Factory.this, "即時空污地圖",Toast.LENGTH_SHORT).show();
		            	 Intent intent = new Intent();
		            	 intent.setClass(Factory.this, MainActivity.class);
		            	 startActivity(intent); 

		            }         

		        });     
	        
	        button03 =(Button)findViewById(R.id.button2);
			  button03.setOnClickListener(new Button.OnClickListener(){ 

		            @Override

		            public void onClick(View v) {
		            	
		            
		            	 
		            	 Toast.makeText(Factory.this, "工廠資訊",Toast.LENGTH_SHORT).show();
		            	 Intent intent = new Intent();
		            	 intent.setClass(Factory.this, Factory.class);
		            	 startActivity(intent); 
		            	 
		            }         

		        });     
			  button04 =(Button)findViewById(R.id.button3);
			  button04.setOnClickListener(new Button.OnClickListener(){ 

		            @Override

		            public void onClick(View v) {
		            	 Bundle bundles = getIntent().getExtras();
		             	Bundle bundle = new Bundle();
		            	 bundle.putString("uid",bundles.getString("uid"));
		            //	 bundle.putString("info",bundles.getString("info"));
		            	 Toast.makeText(Factory.this, "設定",Toast.LENGTH_SHORT).show();
		            	 Intent intent = new Intent();
		            	 intent.putExtras(bundle);
		            	 intent.setClass(Factory.this, Setting.class);
		            	 startActivity(intent); 

		            }  
		            

		        });  
			  buttonTel.setOnClickListener(new Button.OnClickListener() {
		            public void onClick(View view) {
		            	 LayoutInflater inflater = LayoutInflater.from(Factory.this);        
		            	   View alert_view = inflater.inflate(R.layout.edit,null);//alert為另外做給alert用的layout
		            	   AlertDialog.Builder builder = new AlertDialog.Builder(Factory.this);
		            	   builder.setTitle("舉發");
		            	   builder.setMessage("目前情況:");
		            	   builder.setView(alert_view); 
		            	   RadioGroup   input_rad_=(RadioGroup)alert_view.findViewById(R.id.radioGroup_1);
		                   final EditText   input_=(EditText)alert_view.findViewById(R.id.editText_1);
		                   input_.setEnabled(false);
		            	   input_rad_.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		            	   {
		            	       public void onCheckedChanged(RadioGroup group, int checkedId)
		            	       {
		            	           // This will get the radiobutton that has changed in its check state
		            	           RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
		            	           // This puts the value (true/false) into the variable
		            	           boolean isChecked = checkedRadioButton.isChecked();
		            	           // If the radiobutton that has changed in check state is now checked...
		            	           if (isChecked)
		            	           {
		            	        	   Toast.makeText(Factory.this,checkedRadioButton.getText(),Toast.LENGTH_SHORT).show();
		            	               // Changes the textview's text to "Checked: example radiobutton text"
		            	        	   input_.setEnabled(false);
		            	        	   if(checkedRadioButton.getText()!="有異味"){
		            	        	   input_.setEnabled(true);
		            	        	   }
		            	        	  
		            	           }
		            	           
		            	       }
		            	   });
		            	  
		            	
		            	   final AlertDialog dialog = builder.setPositiveButton("取消",
		                           new DialogInterface.OnClickListener() {
		              @Override
		              public void onClick(DialogInterface arg0, int arg1) {
		                    //如果未輸入提示使用者輸入
		                
		              }
		         }).setNegativeButton("確認",new DialogInterface.OnClickListener() 
		         {   
		                   
		              @Override
		                 public void onClick(DialogInterface arg0, int arg1) {
		            	  Toast.makeText(Factory.this,"已送出",Toast.LENGTH_SHORT).show();
		            	
			            	 Intent intent = new Intent();
			            	 intent.setClass(Factory.this, Bubble_map.class);
			            	  Toast.makeText(Factory.this, "舉發地圖",Toast.LENGTH_SHORT).show();
			            	 startActivity(intent); 
		                 }
		         }).create();         
		          
		         dialog.show();//把dialog秀出來
		            }
		        });
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
		getMenuInflater().inflate(R.menu.factory, menu);
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
