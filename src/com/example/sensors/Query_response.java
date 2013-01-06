package com.example.sensors;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class Query_response extends Activity{
public static String TAG="Query_response";
Context context=this;
String response = null;
JSONObject jObj=null;
JSONObject new_j=null;
JSONObject j1;
ProgressDialog progressBar; 
String URL1=":9000/data/query";
String URL=CommonThing.TAG_URL+URL1;
long from;
long to;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
       
		setContentView(R.layout.query_response);
        Bundle extras = getIntent().getExtras(); 
        final String j = extras.getString("Qjson");
        

   
        Button button_plot = (Button) findViewById(R.id.button1);
      
		
		
        Button back = (Button) findViewById(R.id.button2);
        back.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			 
    			
    			// TODO Auto-generated method stub
    			 Intent aIntent = new Intent (Query_response.this,MenuActivity.class);
    	 		aIntent.putExtra("secretcode", CommonThing.code);
    	 
    	 			startActivity(aIntent);
    	        
    		}
    	});
        button_plot.setOnClickListener(new View.OnClickListener() {
        	
             
			
			
			@Override
            public void onClick(View v) {
        	
				Log.i(TAG,j);
        		new MyAsyncTask().execute(j,null,URL);
        		
        	}
        });
}
	

	private  class MyAsyncTask extends AsyncTask<String,Void,String>
    {

        ProgressDialog mProgressDialog;
       
        protected  void onPostExecute(String ans) {
            mProgressDialog.dismiss();
            Log.i(TAG,ans);
            Intent aIntent = new Intent (Query_response.this,GraphActivity.class);
     		
 			aIntent.putExtra("response",ans);
 			startActivity(aIntent);
        
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(Query_response.this, "Loading...", "Data is Loading...");
        }
       
      
        @Override
        protected String doInBackground(String...json)  {
           // your network operation
        	HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
    		HttpResponse response;
    		HttpPost post = new HttpPost(URL);
    		String j1= new String(json[0]);
    		StringEntity se;
    		try {
    			se = new StringEntity(j1);
    			se.setContentType((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
    			
    	        post.setEntity(se);
    	        
    	        response = client.execute(post);
    	        System.out.println("ENTERE SEND----");
    	       
    	       
    	        if(response!=null){
                     InputStream in = response.getEntity().getContent(); //Get the data in the entity
                     String ans = CommonThing.convertStreamToString(in);
                  
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
