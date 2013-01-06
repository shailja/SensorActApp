package com.example.sensors;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddDeviceActivity extends Activity{
	private String TAG="AddDeviceAtivivty";
	private static String dName=null;
	private static String dIP=null;
	private static String dLocation=null;
	private static String dTag=null;
	private static String dLong=null;
	private static String dLat=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
	
		final EditText dev_name = (EditText) findViewById(R.id.dev_name);
        final EditText dev_ip = (EditText) findViewById(R.id.dev_ip);
        final EditText dev_location = (EditText) findViewById(R.id.dev_loc);
        final EditText dev_tag = (EditText) findViewById(R.id.dev_tag);
        final EditText dev_longitude = (EditText) findViewById(R.id.dev_long);
        final EditText dev_latitude = (EditText) findViewById(R.id.dev_lat);
        Button b= (Button) findViewById(R.id.btn_next);
        b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread t = new Thread(){
			
						public void run() {
							dName =dev_name.getText().toString();
							dIP=dev_ip.getText().toString();
							dLocation=dev_location.getText().toString();
							dTag=dev_tag.getText().toString();
							dLong= dev_longitude.getText().toString();
							dLat= dev_latitude.getText().toString();
							try {
								createJson();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					t.start();
				}
			
		});
		
	}
void createJson() throws JSONException
{
	Looper.prepare();
	JSONObject obj_deviceprofile = new JSONObject();
	

	JSONObject device = new JSONObject ();
	obj_deviceprofile.put("secretkey",CommonThing.code);
	obj_deviceprofile.put("deviceprofile", device);
	JSONArray ja= new JSONArray();
			try {
					
			device.put(CommonThing.TAG_DEVNAME,dName);
			device.put(CommonThing.TAG_IP,dIP);
			device.put(CommonThing.TAG_LOACTION, dLocation);
			device.put(CommonThing.TAG_TAG,dTag);
			device.put(CommonThing.TAG_LATITUDE, Double.parseDouble(dLat));
			device.put(CommonThing.TAG_LONGITUDE,Double.parseDouble(dLong));
			device.put(CommonThing.TAG_SENSORs, ja);
			Log.i(TAG,obj_deviceprofile.toString());
			IntentActivity(obj_deviceprofile.toString());
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
}
	private void IntentActivity(String j)
	{
		 Intent newIntent = new Intent (AddDeviceActivity.this,SensorActuatorActivity.class);
		 newIntent.putExtra("json",j);
		 newIntent.putExtra("secretcode",CommonThing.code);
	     startActivity(newIntent);
	}

}
