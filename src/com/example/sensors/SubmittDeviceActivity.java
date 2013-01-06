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
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class SubmittDeviceActivity extends Activity{
public static String TAG="AddActivity";
Context context=this;
String response = null;
JSONObject jObj=null;
ProgressDialog progressBar; 
String URL1=":9000/device/add";
String URL=CommonThing.TAG_URL+URL1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
       
		setContentView(R.layout.add_activity);
        Bundle extras = getIntent().getExtras(); 
        final String j = extras.getString("json");
     
       response_parsing(j);
        Log.i("Value of code",CommonThing.code);
       
        Button myButton = new Button(this);
        myButton.setClickable(true);
        myButton.setText("Submit");
        LinearLayout layout = (LinearLayout) findViewById(R.id.tableLayout1);
        layout.addView(myButton);
        myButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				progressBar =new ProgressDialog(v.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Trying to add device...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressBar.setMax(100);
				progressBar.show();
				Log.i(TAG,"submit button clicked");
				  new MyAsyncTask().execute(j,null,URL);
				
		
			}
		});
	}
	
	void response_parsing(String s){
    	try {
    		TextView t1= (TextView) findViewById(R.id.txt_name);
    		JSONObject j = new JSONObject(s);
    		JSONObject devObj=j.getJSONObject("deviceprofile");
			t1.setText(devObj.getString(CommonThing.TAG_DEVNAME));
			TextView t2= (TextView) findViewById(R.id.txt_ip);
			t2.setText(devObj.getString(CommonThing.TAG_IP));
			TextView t3= (TextView) findViewById(R.id.txt_location);
			t3.setText(devObj.getString(CommonThing.TAG_LOACTION));
			TextView t4= (TextView) findViewById(R.id.txt_tag);
			t4.setText(devObj.getString(CommonThing.TAG_TAG));
			Double latitude = devObj.getDouble(CommonThing.TAG_LATITUDE);
			String sLatitde = Double.toString(latitude);
			TextView t5= (TextView) findViewById(R.id.txt_latitude);
			t5.setText(sLatitde);
			Double longitude = devObj.getDouble(CommonThing.TAG_LONGITUDE);
			String sLongitude = Double.toString(longitude);
			TextView t6= (TextView) findViewById(R.id.txt_longitude);
			t6.setText(sLongitude);
		
			
			JSONArray ar_sensors = devObj.getJSONArray(CommonThing.TAG_SENSORs);
			for (int i = 0; i<ar_sensors.length();i++){
				
				JSONObject senObj=ar_sensors.getJSONObject(i);
			    String sensor_name = senObj.getString(CommonThing.TAG_NAME);
			    createTableRowForSensorName(getCurrentFocus(), "\t\t\t\tSensor details");
			    createTableRowForSensorName(getCurrentFocus(), Integer.toString(i+1)+"."+"Name:"+sensor_name);
			    JSONArray ar_channels = senObj.getJSONArray(CommonThing.TAG_CHANNELS);
			    createTableRowForSensorName(getCurrentFocus(), "Channels:");
			    createTableRow(getCurrentFocus(), "Name", "Type","Unit","SP");
			    for(int x=0;x<ar_channels.length();x++)
			    {
			    	JSONObject ob_ch =ar_channels.getJSONObject(x);
			    	String ch_name=ob_ch.getString(CommonThing.TAG_NAME);
			    	String ch_type=ob_ch.getString(CommonThing.TAG_TYPE);
			    	String ch_unit=ob_ch.getString(CommonThing.TAG_UNIT);
			    	int ch_sampling_period=ob_ch.getInt(CommonThing.TAG_SAMPLINGPERIOD);
			    	createTableRow(getCurrentFocus(), ch_name, ch_type, ch_unit, Integer.toString(ch_sampling_period));
			    	
			    }
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("JSON Parser", "Error parsing data " + e.toString());
						
		}
    	}
	public void createTableRow(View v, String name,String type,String unit,String sp ) {
		  int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,(float) 1, getResources().getDisplayMetrics());
		  TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
		  TableRow tr = new TableRow(this);
		  LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		  tr.setLayoutParams(lp);
		  TextView tv1 = new TextView(this);
		  tv1.setLayoutParams(lp);
		  tv1.setText(name);
		  TextView tv2= new TextView(this);
		  tv2.setLayoutParams(lp);
		  tv2.setText(type);
		  TextView tv3= new TextView(this);
		  tv3.setLayoutParams(lp);
		  tv3.setText(unit);
		  TextView tv4= new TextView(this);
		  tv4.setLayoutParams(lp);
		  tv4.setText(sp);
		  tv1.setWidth(50 * dip);
		  tv2.setWidth(100 * dip);
		  tv3.setWidth(150 * dip);
		  tv4.setWidth(100 * dip);		  
		  tr.addView(tv1);
		  tr.addView(tv2);
    	  tr.addView(tv3);
		  tr.addView(tv4);
		  tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}	
	
	public void createTableRowForSensorName(View v, String s1) {
		  TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
		  TableRow tr = new TableRow(this);
		  LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		  tr.setLayoutParams(lp);

		  TextView tvLeft = new TextView(this);
		  tvLeft.setLayoutParams(lp);
		  tvLeft.setText(s1);
		  tr.addView(tvLeft);

		  tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}	
	
	

	
	private  class MyAsyncTask extends AsyncTask<String,Void,String>
    {

        ProgressDialog mProgressDialog;
       
        protected  void onPostExecute(String ans) {
            mProgressDialog.dismiss();
            Intent aIntent = new Intent (SubmittDeviceActivity.this,ShowResult.class);
 			aIntent.putExtra("secretcode", CommonThing.code);
 			aIntent.putExtra("answer", ans);
 			startActivity(aIntent);
        
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(SubmittDeviceActivity.this, "Loading...", "Data is Loading...");
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