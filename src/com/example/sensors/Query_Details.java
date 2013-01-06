package com.example.sensors;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Query_Details extends Activity{
	String TAG = null;
	String code;
	JSONObject new_j;
	String  res_j;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicedetails);
		Bundle extras = getIntent().getExtras(); 
        final String details = extras.getString("deletedevice");
        
      
	
		 res_j=response_parsing(details);
		
		Button back = new Button(this);
        back.setClickable(true);
        back.setText("Plot");

		 LinearLayout layout = (LinearLayout) findViewById(R.id.tableLayout1);
	        layout.addView(back);
	    	back.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			 
			
			// TODO Auto-generated method stub
			 Intent aIntent = new Intent (Query_Details.this,QueryDevice.class);
	 		aIntent.putExtra("secretcode", CommonThing.code);
	 		aIntent.putExtra("data", res_j);
	 			
	 			startActivity(aIntent);
	        
		}
	});
	    	
	    	
	
		}
	
	String response_parsing(String s){
    	try {
    		System.out.println("we reached display info");
    		TextView t1= (TextView) findViewById(R.id.txt_uname);
    		String Susername="karishma";
    		JSONObject jobj = new JSONObject(s);
    	
			t1.setText(Susername);
			TextView t2= (TextView) findViewById(R.id.txt_dname);
			t2.setText(jobj.getString("devicename"));
			String device_name=jobj.getString("devicename");
			
			JSONArray j_array=jobj.getJSONArray("sensors");
			JSONObject jobj1=j_array.getJSONObject(0);
			TextView t3= (TextView) findViewById(R.id.txt_name);
			t3.setText(jobj1.getString("name"));
			TextView t4= (TextView) findViewById(R.id.txt_id);
			t4.setText(jobj1.getString("sid"));
			String sen_name=jobj1.getString("name");
			String sen_id=jobj1.getString("sid");
			JSONArray j_array1=jobj1.getJSONArray("channels");
			JSONObject jobj2=j_array1.getJSONObject(0);
			String ch_name=jobj2.getString("name");
			
			TextView t5= (TextView) findViewById(R.id.txt_channel);
			t5.setText(jobj2.getString("name"));
			new_j=new JSONObject();
			JSONObject new_j_c=new JSONObject();
			new_j.put("devicename",device_name);
			new_j.put("sensorname",sen_name);
			new_j.put("sensorid",Integer.parseInt(sen_id));
			new_j.put("channelname",ch_name);
			new_j.put("username",Susername);
			new_j.put("conditions",new_j_c );
			
		Log.i(TAG,new_j.toString());
			
			
    	}catch(JSONException e){
    		Log.i(TAG,e.toString());
    	}
    	return new_j.toString();
    	}
	
	
}			
		




