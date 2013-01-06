package com.example.sensors;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.sensorapp.ListActivity.load;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;




public class ListDeviceActivity extends Activity{
	String TAG ="ListDeviceActivity";
	String URL1=":9000/device/list";
	String URL=CommonThing.TAG_URL+URL1;
  
    Context context=this;
   
	
		protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_list);
		
        MyAsyncTask task=new MyAsyncTask();
        task.execute(CommonThing.code,null,URL);
        
       

       
	}
	
	private  class MyAsyncTask extends AsyncTask<String,Void,String>
    {

        ProgressDialog mProgressDialog;
       
        protected  void onPostExecute(String ans) {
            mProgressDialog.dismiss();
            Intent aIntent = new Intent (ListDeviceActivity.this,DisplayList.class);
 			aIntent.putExtra("answer", ans);
 			startActivity(aIntent);
     
            Log.e(TAG,ans);
       		
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(ListDeviceActivity.this, "Loading...", "Data is Loading...");
        }
       
      
        @Override
        protected String doInBackground(String...json)  {
           // your network operation
        	String code= new String(json[0]);
        	JSONObject j1=new JSONObject();
        	try {
				
				j1.put("secretkey",code);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
    		HttpResponse response;
    		HttpPost post = new HttpPost(URL);
    		
    		StringEntity se;
    		try {
    			se = new StringEntity(j1.toString());
    			se.setContentType((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
    			
    	        post.setEntity(se);
    	        
    	        response = client.execute(post);
    	        System.out.println("ENTERE SEND----");
    	       
    	       // CommonThing.ch_index++;
    	        if(response!=null){
                     InputStream in = response.getEntity().getContent(); //Get the data in the entity
                     String ans = CommonThing.convertStreamToString(in);
                     System.out.println(ans);
                     return ans;
                 
   	       
   	         		}
    	       
    	        else
    	        {
    	        	Log.e(TAG,"no response");
    	        }
    	     
    		}
    		 catch(ConnectTimeoutException e){
    			 e.printStackTrace();
                }catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ClientProtocolException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
			return null;
    		  
            
    	}

    

	
		
		
		
		
	}
   
    	
	
	
	
}


