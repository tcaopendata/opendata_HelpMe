package com.example.gsm;

import static com.example.gsm.CommonUtilities.TAG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity {
	SeekBar see; 
	TextView	seetext;
	int  off = 0;
	int  val = 0;
	String setside="";
	String area ="";
	private CheckBox c1;
	private CheckBox c2;
	private CheckBox c3;
	private CheckBox c4;
	private CheckBox c5;
	private CheckBox c6;
	private CheckBox c7;
	private CheckBox c8;
	private CheckBox c9;
	private CheckBox c10;
	private CheckBox c11;
	private CheckBox c12;
	

	 private static String getStringFromInputStream(InputStream is)
             throws IOException {
     ByteArrayOutputStream os = new ByteArrayOutputStream();
     
     byte[] buffer = new byte[1024];
     int len = -1;
     // 一定要写len=is.read(buffer)
     // 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
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
		setContentView(R.layout.activity_setting);
	//	Bundle bundle = getIntent().getExtras();
	//	bundle.getString("info");
		//Log.v(TAG,bundle.getString("info"));
		
		 Spinner spinner = (Spinner)findViewById(R.id.spinner);
	        final String[] list =  {"萬里區","金山區","板橋區","汐止區","深坑區","石碇區","瑞芳區","平溪區","雙溪區","貢寮區","新店區","坪林區","烏來區","永和區","中和區","土城區","三峽區","樹林區","鶯歌區","三重區","新莊區","泰山區","林口區","蘆洲區","五股區","八里區","淡水區","三芝區","石門區"};
	        ArrayAdapter<String> cList = new ArrayAdapter<String>(Setting.this, 
	                android.R.layout.simple_spinner_dropdown_item, 
	                list);
	        spinner.setAdapter(cList);
	        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	               area =list[position]; 
	            }

	            @Override
	            public void onNothingSelected(AdapterView<?> parent) {

	            }
	        });
		RadioButton radio = (RadioButton)findViewById(R.id.radioButton2);
		radio.setChecked(true);
	
		
       
     
		 Button Botton = (Button)findViewById(R.id.button3);
		 Botton.setOnClickListener(new Button.OnClickListener(){ 

	            @Override
	            public void onClick(View v) {
	            	 RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
	        		 switch(rg.getCheckedRadioButtonId()){
	                 case R.id.radioButton1:
	                     //txv.setText("adults ticktet"); //顯示結果
	                	 off =0;
	                     break;
	                 case R.id.radioButton2:
	                   //  txv.setText("children ticket"); //顯示結果
	                	 off=1;
	                     break;
	        		 }
	        		 Bundle bundles = getIntent().getExtras();
	        		
	            	 Toast.makeText(v.getContext(), bundles.getString("uid")+"開關:"+off +" "+"地區:"+ setside +" 設定值:"+val, Toast.LENGTH_LONG).show();
	            	 Thread thread = new Thread(){
	       		      public void run(){
	       	  HttpURLConnection conn = null;
	       	 Bundle bundle = getIntent().getExtras();
	           // String data = "username=" + URLEncoder.encode(username) + "&password="+ URLEncoder.encode(password);
	            String url = "http://140.127.220.216:8080/changegcm.php?regId="+bundle.getString("uid")+"&pot="+area+"&swith="+off;//+ data;
	            Log.v(TAG,url);
	          
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
	                            Log.v(TAG, "連線成功" + state + "' 來自 " + url);   
	                       //     Toast.makeText(this, "連線成功"+ "開關:"+off +" "+"地區:"+ setside +" 設定值:"+val, Toast.LENGTH_LONG).show();
	                          //  str =   state ;
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
		 });
		 
		
	       
		
	}
	private CheckBox.OnCheckedChangeListener chklistener = new CheckBox.OnCheckedChangeListener(){		
		
		@Override		
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			
			if(c1.isChecked()){
				setside ="美濃";
				
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
			}
			if(c2.isChecked()){
				setside ="橋頭";
				c1.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
			}
			if(c3.isChecked()){
				setside ="仁武";
				c2.setChecked(false);
				c1.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
			}
			if(c4.isChecked()){
				setside ="鳳山";
				c2.setChecked(false);
				c3.setChecked(false);
				c1.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
				//setside ="大寮";
			}
			if(c5.isChecked()){
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c1.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);		
				setside ="大寮";
			}
			if(c6.isChecked()){
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c1.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
				setside ="林園";
			}
			if(c7.isChecked()){
				setside ="楠梓";
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c1.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
			}
			if(c8.isChecked()){
				setside ="左營";
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c1.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
			}
			if(c9.isChecked()){
				setside ="前金";
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c1.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
			}
			if(c10.isChecked()){
				setside ="前鎮";
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c1.setChecked(false);
				c11.setChecked(false);
				c12.setChecked(false);				
			}
			if(c11.isChecked()){
				setside ="小港";
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c1.setChecked(false);
				c12.setChecked(false);				
			}
			if(c12.isChecked()){
				setside ="復興";
				c2.setChecked(false);
				c3.setChecked(false);
				c4.setChecked(false);
				c5.setChecked(false);
				c6.setChecked(false);
				c7.setChecked(false);
				c8.setChecked(false);
				c9.setChecked(false);
				c10.setChecked(false);
				c11.setChecked(false);
				c1.setChecked(false);				
			}
			
		}	
		};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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
