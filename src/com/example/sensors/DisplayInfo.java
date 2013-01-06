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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class DisplayInfo extends Activity{
	String TAG="DisplayInfo";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayinfo);
		Bundle extras = getIntent().getExtras(); 
	
        final String details = extras.getString("getdevice");
		response_parsing(details);
		
		
		
		Button plot = new Button(this);
        plot.setClickable(true);
        plot.setText("Plot");

		 LinearLayout layout = (LinearLayout) findViewById(R.id.tableLayout1);
	        layout.addView(plot);
	    	plot.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			 
			
			// TODO Auto-generated method stub
			 Intent aIntent = new Intent (DisplayInfo.this,QueryDevice.class);
	 		aIntent.putExtra("secretcode", CommonThing.code);
	 			aIntent.putExtra("answer", details);
	 			startActivity(aIntent);
	        
		}
	});
		
		
		}
	
	void response_parsing(String s){
    	try {
    		System.out.println("we reached display info");
    		TextView t1= (TextView) findViewById(R.id.txt_name);
    		
    		JSONObject j = new JSONObject(s);
    	
			t1.setText(j.getString(CommonThing.TAG_DEVNAME));
			TextView t2= (TextView) findViewById(R.id.txt_ip);
			t2.setText(j.getString(CommonThing.TAG_IP));
			TextView t3= (TextView) findViewById(R.id.txt_location);
			t3.setText(j.getString(CommonThing.TAG_LOACTION));
			TextView t4= (TextView) findViewById(R.id.txt_tag);
			t4.setText(j.getString(CommonThing.TAG_TAG));
			Double latitude = j.getDouble(CommonThing.TAG_LATITUDE);
			String sLatitde = Double.toString(latitude);
			TextView t5= (TextView) findViewById(R.id.txt_latitude);
			t5.setText(sLatitde);
			Double longitude = j.getDouble(CommonThing.TAG_LONGITUDE);
			String sLongitude = Double.toString(longitude);
			TextView t6= (TextView) findViewById(R.id.txt_longitude);
			t6.setText(sLongitude);
    		
			
			
			JSONArray ar_sensors = j.getJSONArray(CommonThing.TAG_SENSORs);
			for (int i = 0; i<ar_sensors.length();i++){
				JSONObject senObj=ar_sensors.getJSONObject(i);
			    String sensor_name = senObj.getString(CommonThing.TAG_NAME);
			    createTableRowForSensorName(getCurrentFocus(), "\t\t\t\tSensor details");
			    createTableRowForSensorName(getCurrentFocus(), Integer.toString(i+1)+"."+"Name:"+sensor_name);
			    JSONArray ar_channels = senObj.getJSONArray(CommonThing.TAG_CHANNELS);
			    createTableRowForSensorName(getCurrentFocus(), "Channels:");
			    createTableRow(getCurrentFocus(), "Name", "Type","Unit", "SP");
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
		  tv4.setWidth(200 * dip);		  
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
}			
		




