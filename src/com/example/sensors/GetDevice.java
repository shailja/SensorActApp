package com.example.sensors;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GetDevice extends Activity{
	String TAG ="GetDevice";
	String URL1=":9000/device/get";
	String URL=CommonThing.TAG_URL+URL1;
  
    Context context=this;
	String url; 
    String code;
    ProgressDialog progressBar; 
    String device_name;
    String device;
   
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get);
	
	
		Button del = (Button) findViewById(R.id.btn_get);
		
		del.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) {
				 
				
				// TODO Auto-generated method stub
				final EditText device = (EditText) findViewById(R.id.edit_devicename);
				final String device_name = device.getText().toString();
				Log.i(TAG,"submit button clicked");
				JSONObject jvalue= new JSONObject();
				try {
					jvalue.put("device", device_name);
					jvalue.put("url", URL);
					jvalue.put("code", CommonThing.code);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  new MyAsyncTask().execute(jvalue.toString(),device_name,CommonThing.code);
			}
		});
	}
	
	
	
	
	private  class MyAsyncTask extends AsyncTask<String,String,String>
    {

        ProgressDialog mProgressDialog;
       
        protected  void onPostExecute(String ans) {
            mProgressDialog.dismiss();
            Intent aIntent = new Intent (GetDevice.this,DisplayInfo.class);
 		
 			aIntent.putExtra("getdevice",ans);
 			startActivity(aIntent);
				        		
         
        }

        @Override
        protected void onPreExecute() {
        	
            mProgressDialog = ProgressDialog.show(GetDevice.this, "Loading...", "Data is Loading...");
            
        }
       
        
        @Override
        protected String doInBackground(String...jvalue)  {
        	
		  
        	
        	// your network operation
        	String j2= new String(jvalue[0]);
        	JSONObject j3;
			try {
				j3 = new JSONObject(j2);
				url=j3.getString("url");
	        	device=j3.getString("device");
	        	code=j3.getString("code");
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	
        	HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
    		HttpResponse response;
    		JSONObject j1 = new JSONObject();
    		 try {
				j1.put("secretkey", code);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
             try {
            	// publishProgress(device_name[0]);
           
				j1.put("devicename",device);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
		
				e1.printStackTrace();
			}
             
            try{
    		HttpPost post = new HttpPost(url);
    		
    		StringEntity se;
    		
    			se = new StringEntity(j1.toString());
    			se.setContentType((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
    			
    	        post.setEntity(se);
    	        
    	        response = client.execute(post);
    	        System.out.println("ENTERE SEND----");
    	       
    	       
    	        if(response!=null){
                     InputStream in = response.getEntity().getContent(); //Get the data in the entity
                     String ans = CommonThing.convertStreamToString(in);
                    System.out.println(ans);
                     return ans;
                  
   	       
   	         		}
    	       
    	        else
    	        {
    	        	Log.e(TAG,"no respons");
    	        }
    	     
    		}catch(Exception e){
    			Log.e(TAG,e.toString());
    		}
    	
			return null;
    		
            
    	}

     
	
}
}
