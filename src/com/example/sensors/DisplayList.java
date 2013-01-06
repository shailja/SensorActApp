package com.example.sensors;



import java.util.Collection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ListActivity;
public class DisplayList extends Activity{
	HashMap<String, Integer> h;
	JSONArray ar_deviceList;
	String TAG= null;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_list);
		Bundle extras = getIntent().getExtras(); 
        final String response = extras.getString("answer");
        
      
        try{
            
            
            /*Checking response */
            if(response!=null){
               Log.i(TAG,response);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
                ListView deviceList = (ListView) findViewById(R.id.listViewDevices);
                deviceList.setAdapter(arrayAdapter);
                Button myButton = new Button(this);
                myButton.setClickable(true);
                myButton.setText("Back");
               
	         		try {
	         			JSONObject jObj = new JSONObject(response);
	         			ar_deviceList= jObj.getJSONArray("devicelist");
	         		Log.i(TAG,"goning to enter for loop");
	         			h=new HashMap<String, Integer>();
	         			for(int i=0;i<ar_deviceList.length();i++)
	         			{
	         				JSONObject obj=ar_deviceList.getJSONObject(i);
	         				String device=obj.getString("devicename");
	         				h.put(device, i);
	         			
	         				Log.i(TAG,device);
	         				arrayAdapter.add(device);
	         				
	         			}
	                 
	         			
	         		} catch (JSONException e) {
	         		Log.e("JSON Parser", "Error parsing data " + e.toString());
	         			
	         		}
	         	 findViewById(R.id.listViewDevices).setVisibility(View.VISIBLE);
	         	 deviceList.setOnItemClickListener(mDeviceClickListener);
            }
            else
            {  	
            
            Log.e(TAG,"No response");
            }
        }
        catch(Exception e){
     	   Log.e(TAG,e.toString());
        }
          

	}
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
     public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
     
     // Get the device MAC address, which is the last 17 chars in the View
     final String info = ((TextView) v).getText().toString();;
     Log.i(TAG,info);
     Thread t = new Thread(new Runnable() {		
			public void run() {
				int x=h.get(info);
				try {
					JSONObject jo=ar_deviceList.getJSONObject(x);
					Log.i(TAG,jo.toString());
					 Intent aIntent = new Intent (DisplayList.this,DisplayDetails.class);
					 aIntent.putExtra("secretcode",CommonThing.code);
			 			aIntent.putExtra("deletedevice",jo.toString());
			 			startActivity(aIntent);
		
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},"Selection Thread");
		
		t.start(); 
 }
     };
		

		
		
		

}
