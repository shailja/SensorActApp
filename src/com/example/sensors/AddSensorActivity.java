package com.example.sensors;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddSensorActivity extends Activity{
	public String TAG="AddSensorActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_sensor);
		Bundle extras = getIntent().getExtras(); 
        final String js = extras.getString("json");
     
        Log.i(TAG,js);
		final EditText dev_name = (EditText) findViewById(R.id.editText_sensorName);
		final EditText dev_id = (EditText) findViewById(R.id.edit_id);
		Button b= (Button) findViewById(R.id.button_senNext);
	    b.setOnClickListener(new OnClickListener() {
		
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Thread t = new Thread(){
						
						public void run() {
						try {
							
							
							JSONObject jos = new JSONObject(js);
							JSONObject dev_obj=jos.getJSONObject("deviceprofile");
							JSONArray ja_sen=dev_obj.getJSONArray("sensors");
							String name=dev_name.getText().toString();
							String s_id=dev_id.getText().toString();
							
					
							
							JSONObject jo_se = new JSONObject();
							jo_se.put(CommonThing.TAG_NAME, name);
							jo_se.put("sid", Integer.parseInt(s_id));
							JSONArray ja_ch= new JSONArray();
							jo_se.put("channels", ja_ch);
							ja_sen.put(jo_se);
							dev_obj.put("sensors", ja_sen);
							jos.put("deviceprofile",dev_obj);
							
							IntentActivity(jos.toString());
							
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
	public void IntentActivity(String j)
	{
				
		 Intent newIntent = new Intent (AddSensorActivity.this,AddChannelActivity.class);
		 newIntent.putExtra("json",j);
		 newIntent.putExtra("secretcode",CommonThing.code);
	     startActivity(newIntent);
	}
	

}