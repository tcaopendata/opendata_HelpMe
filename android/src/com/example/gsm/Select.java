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
import android.widget.TextView;
import android.widget.Toast;

public class Select extends Activity {
	static String info="";
	static int type0=0;
	static int type1=0;
	static int type2=0;
	static String area="";
	//TextView	seetext;
	
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
		setContentView(R.layout.activity_select);
	   	 final String[] item = {"補助資訊","福利機構"};

	   	    AlertDialog.Builder dialog_listC = new AlertDialog.Builder(Select.this);
	   	 dialog_listC.setTitle("您想選擇類別是?");
	   	dialog_listC.setItems(item, new DialogInterface.OnClickListener(){
	   	         @Override

	   	         //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
	   	         public void onClick(DialogInterface dialog, int whichs) {
	   	                // TODO Auto-generated method stub
	   	        	String[] items2 = null;
	   	        	type0 = whichs+1;
	   	        	 if(whichs ==0){
	   	        		items2 = new String[]{"少年福利服務中心","弱勢家庭兒童及少年社區照顧服務","全類別"};
	   	        	 }
	   	        	 if(whichs ==1){
	   	        		items2 = new String[]{"兒童與青少年","老年","身心障礙"};
	   	        	 }
	   	        	
	   	        	
	   	        	selectitem_2(items2).show();
	   	          }
	   	    });
	 	dialog_listC.show();
		
		
		
	  
	}
 	public AlertDialog.Builder selectitem_2(final String[] item){
   	    AlertDialog.Builder dialog_list = new AlertDialog.Builder(Select.this);
   	    dialog_list.setTitle("您想選擇類別是?");
   	    dialog_list.setItems(item, new DialogInterface.OnClickListener(){
   	         @Override

   	         //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
   	         public void onClick(DialogInterface dialog, int which) {
   	                // TODO Auto-generated method stub
   	        	String[] items = null;
   	        	type1 = which+1;
   	        	 if(which ==0){
   	        		 items = new String[]{"少年福利服務中心","弱勢家庭兒童及少年社區照顧服務"};
   	        	 }
   	        	 if(which ==1){
   	        		 items = new String[]{"公共托老中心(含日照中心)","新北市老人福利機構","低收入戶老人機構安置補助暨老人保護安置合約機構"};
   	        	 }
   	        	 if(which ==2){
   	        		 items = new String[]{"日間作業設施服務單位","日間照顧服務中心名冊","社區日間照顧服務中心","家庭資源中心名冊","福利機構"};
   	   	        	
   	        	 }
   	        	   	        	
   	        	selectitem(items).show();
   	          }
   	    });
   	  return dialog_list;
   	// Toast.makeText(Select.this, "5你選的是" + info, Toast.LENGTH_SHORT).show();
 	//seetext.setText(info);
 	}
public AlertDialog.Builder selectitem(final String[] item){
	

	    AlertDialog.Builder dialog_list = new AlertDialog.Builder(Select.this);
	    dialog_list.setTitle("選擇服務");
	    dialog_list.setItems(item, new DialogInterface.OnClickListener(){
	         @Override

	         //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
	         public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	        	 type2 = which+1;
	          //      Toast.makeText(Select.this, "你選的是" + item[which], Toast.LENGTH_SHORT).show();
	                String[] items = {"所有區域","萬里區","金山區","板橋區","汐止區","深坑區","石碇區","瑞芳區","平溪區","雙溪區","貢寮區","新店區","坪林區","烏來區","永和區","中和區","土城區","三峽區","樹林區","鶯歌區","三重區","新莊區","泰山區","林口區","蘆洲區","五股區","八里區","淡水區","三芝區","石門區"};
	                selectitem_t(items).show();
	          }
	    });
	   return dialog_list;
	    
	
}
public AlertDialog.Builder selectitem_t(final String[] item){

    AlertDialog.Builder dialog_list = new AlertDialog.Builder(Select.this);
    dialog_list.setTitle("選擇地區");
    dialog_list.setItems(item, new DialogInterface.OnClickListener(){
         @Override

         //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
         public void onClick(DialogInterface dialog, int which) {
        	 area = item[which];
        	 if(area =="所有區域"){
        		 area = "北";
        	 }
                // TODO Auto-generated method stub
                Toast.makeText(Select.this, "你選的是" + item[which], Toast.LENGTH_SHORT).show();
                final ProgressDialog myDialog = ProgressDialog.show(Select.this, "讀取中", "資料分析中...請稍後(please wait...)");
               // Toast.makeText(Select.this, "4你選的是" + info, Toast.LENGTH_SHORT).show();
                
                new Thread()
                {
                    public void run()
                    {
                        try
                        {
                        	try{
        	                    
        	                    gets_("");
        	                       
        	                    
        	                   }
        	                   catch(Exception e){
        	                   	
        	                   }

                             sleep(3000);
                             Intent intent = new Intent();
    	                	 Bundle bundle = new Bundle();
    	                	 bundle.putInt("dir",type0);
    	                	 bundle.putString("info",info);
    	                     intent.setClass(Select.this, Info.class);
    	                     intent.putExtras(bundle);
    	                	 startActivity(intent);
                           //  Toast.makeText(Select.this, "1你選的是" + info, Toast.LENGTH_SHORT).show();
                             
                            
                            
                            }
                        catch(Exception e)
                        {
                        	//   Toast.makeText(Select.this, "2你選的是" + info, Toast.LENGTH_SHORT).show();
                               
                            e.printStackTrace();
                        }
                        finally
                        {
                        	 //  Toast.makeText(Select.this, "3你選的是" + info, Toast.LENGTH_SHORT).show();
                               
                        	myDialog.dismiss();
                        }
                      //  Toast.makeText(Select.this, "4你選的是" + info, Toast.LENGTH_SHORT).show();
                        
                    }  
                }.start();
               
          }
         
    });
    
   return dialog_list;
    

}
private static void gets_(final String endpoint){
   
	 Thread thread = new Thread(){
	      public void run(){
 HttpURLConnection conn = null;

// String data = "username=" + URLEncoder.encode(username) + "&password="+ URLEncoder.encode(password);
   String url = "http://140.127.220.216:8080/json/readjson.php?type0="+type0+"&type1="+type1+"&type2="+type2+"&area="+area;//+ data;
try {
        
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
               // str =   state ;
                info = state;
              //  seetexts.setText("資料: ");
                //Log.v(TAG, "info: " + info + "' 來自 " + url);                    

                //seetext.setText(state);
                 
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select, menu);
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
