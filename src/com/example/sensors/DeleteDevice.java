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

//import com.example.sensorapp.SubmittDeviceActivity.MyAsyncTask;

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
import android.widget.Toast;

public class DeleteDevice extends Activity{
	String TAG ="DeleteDevice";
	String URL1=":9000/device/delete";
	String URL=CommonThing.TAG_URL+URL1;
 
    Context context=this;

    ProgressDialog progressBar; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
	
		Button del = (Button) findViewById(R.id.btn_delete);
		del.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
			
				
						Log.i(TAG,"submit button clicked");
						  new MyAsyncTask().execute(CommonThing.code,null,URL);
						// TODO Auto-generated method stub
					
			}		});
	}
	
	private  class MyAsyncTask extends AsyncTask<String,Void,String>
    {

        ProgressDialog mProgressDialog;
       
        protected  void onPostExecute(String ans) {
            mProgressDialog.dismiss();
            
            Log.e(TAG,ans);
       
            
            Intent aIntent = new Intent (DeleteDevice.this,DeleteResult.class);
 			aIntent.putExtra("secretcode", CommonThing.code);
 			aIntent.putExtra("answer", ans);
 			startActivity(aIntent);
            
            
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(DeleteDevice.this, "Loading...", "Data is Loading...");
        }
       
      
        @Override
        protected String doInBackground(String...json)  {
           
        	final EditText device = (EditText) findViewById(R.id.edit_device);
        	final String device_name = device.getText().toString();
        	// your network operation
        	String j2= new String(json[0]);
        	HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
    		HttpResponse response;
    		JSONObject j1 = new JSONObject();
    		 try {
				j1.put("secretkey", j2);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             try {
				j1.put("devicename",device_name);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		HttpPost post = new HttpPost(URL);
    	
    		StringEntity se;
    		try {
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
