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
import org.json.JSONArray;
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
import android.text.Editable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class QueryDevice extends Activity{
public static String TAG="QueryDevice";
Context context=this;
String response = null;

JSONObject new_j;
JSONObject new_j_c;
ProgressDialog progressBar; 
String URL1=":9000/device/query";
String URL=CommonThing.TAG_URL+URL1;

//String code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
       
		setContentView(R.layout.device_query);
        Bundle extras = getIntent().getExtras(); 
        final String j = extras.getString("answer");
  
    
      
        Button button_plot = (Button) findViewById(R.id.btnplot);
        try {
			JSONObject jobj=new JSONObject(j);
			String device_name=jobj.getString("devicename");
			
			TextView t1= (TextView) findViewById(R.id.textView6);
			t1.setText(device_name);
			
			JSONArray j_array=jobj.getJSONArray("sensors");
		
			JSONObject jobj1=j_array.getJSONObject(0);
			String sen_name=jobj1.getString("name");
			TextView t2= (TextView) findViewById(R.id.textView7);
			t2.setText(sen_name);
			
			String sen_id=jobj1.getString("sid");
			TextView t3= (TextView) findViewById(R.id.textView1);
			t3.setText(sen_id);
			JSONArray j_array1=jobj1.getJSONArray("channels");
		
			JSONObject jobj2=j_array1.getJSONObject(0);
			String ch_name=jobj2.getString("name");
			TextView t4= (TextView) findViewById(R.id.textView2);
			t4.setText(ch_name);
		
			
			
			String Susername=CommonThing.TAG_username;
			TextView t5= (TextView) findViewById(R.id.textView5);
			t5.setText(Susername);
			
			new_j=new JSONObject();
			 new_j_c=new JSONObject();
			new_j.put("devicename",device_name);
			new_j.put("sensorname",sen_name);
			new_j.put("sensorid",Integer.parseInt(sen_id));
			new_j.put("channelname",ch_name);
			new_j.put("username",Susername);
			new_j.put("conditions",new_j_c );
			
		Log.i(TAG,new_j.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        button_plot.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
				// TODO Auto-generated method stub
            	LayoutInflater li=LayoutInflater.from(context);
        		View promptsview=li.inflate(R.layout.prompt, null);
        		
        		 AlertDialog.Builder alert = new AlertDialog.Builder(context);
        		 alert.setView(promptsview);
        	        alert.setTitle("Plotting");
        	        alert.setMessage("Enter time");
        	      
        	    	
        	      final EditText qfromtime= (EditText) promptsview.findViewById(R.id.editText1);
        	        final EditText qtotime= (EditText) promptsview.findViewById(R.id.editText2);
        	        final Editable ftime = qfromtime.getText();
        	       
    		        final Editable ttime = qtotime.getText();
    		       
    		        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
    	        		public void onClick(DialogInterface dialog, int whichButton) { 
    	        			
    						try {
    							
    							long epoch = System.currentTimeMillis()/1000;
    						
    							
    							Long from= epoch-Long.parseLong(ftime.toString());
    							Long to= Long.parseLong(ttime.toString())+epoch;
								new_j_c.put("fromtime",from);
								new_j_c.put("totime",to);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    	        			 Intent newIntent = new Intent (QueryDevice.this,Query_response.class);
             				newIntent.putExtra("Qjson",new_j.toString());
             		
             				
             			    startActivity(newIntent); 
    	        		}              
    	        	});
    		        
        	       alert.show();
        	
        		
        		
				
				Log.i(TAG,"submit button clicked");
	
			
			}
		});
	}
	
	
	
	
	
	
	
}