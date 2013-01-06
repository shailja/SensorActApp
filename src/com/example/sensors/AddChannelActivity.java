package com.example.sensors;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddChannelActivity extends Activity{
	Context context=this;
	String TAG="AddChannelActivity";
	String name;
	String type;
	String unit;
	String sp;
	String js;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_channel);
		Bundle extras = getIntent().getExtras(); 
        js = extras.getString("json");
   

        Log.i(TAG,js);
        final EditText ch_name = (EditText) findViewById(R.id.editText_chName);
        final EditText ch_type = (EditText) findViewById(R.id.editText_type);
        final EditText ch_unit = (EditText) findViewById(R.id.editText_unit);
        final EditText ch_sp = (EditText) findViewById(R.id.editText_SP);
		 Button b= (Button) findViewById(R.id.button_nextCh);
		 b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name=ch_name.getText().toString();
				type=ch_type.getText().toString();
				unit=ch_unit.getText().toString();
				sp=ch_sp.getText().toString();
				Thread t = new Thread(){
					
					public void run() {
						Looper.prepare();
						addToJson(js);
					}
				};
				t.start();
			}
		});
	}

	void addToJson(String jo)
	{
		
		try {
			
			JSONObject jos = new JSONObject(jo);
			JSONObject dev_obj=jos.getJSONObject("deviceprofile");
			JSONArray ja_sen=dev_obj.getJSONArray("sensors");
			JSONObject job_ch=new JSONObject();
			
				job_ch.put(CommonThing.TAG_NAME, name);
				job_ch.put(CommonThing.TAG_TYPE, type);
				job_ch.put(CommonThing.TAG_UNIT, unit);
				job_ch.put(CommonThing.TAG_SAMPLINGPERIOD,Integer.parseInt(sp));
			JSONObject jo_se=ja_sen.getJSONObject(CommonThing.ch_index);
			
			JSONArray ja_ch=jo_se.getJSONArray("channels");
			
			
			ja_ch.put(job_ch);
			
			jo_se.put("channels",ja_ch);
			dev_obj.put("sensors",ja_sen);
			jos.put("deviceprofile", dev_obj);
			Log.i(TAG,jos.toString());
		
			choiceDialogue(jos.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void choiceDialogue(final String j){
		final CharSequence[] choice = {"Add channel","Add sensor","Proceed to submit"};
	    AlertDialog.Builder alert = new AlertDialog.Builder(context);
	    alert.setTitle("Select choice");
	    alert.setSingleChoiceItems(choice,-1, new DialogInterface.OnClickListener()
	    {
	        public void onClick(DialogInterface dialog, int which) 
	        {
	            if(choice[which]=="Add channel")
	            {
	            	dialog.dismiss();
	            	Intent newIntent = new Intent (AddChannelActivity.this,AddChannelActivity.class);
					newIntent.putExtra("json",j);
					newIntent.putExtra("secretcode", CommonThing.code);
				    startActivity(newIntent);
	            }
	            else if (choice[which]=="Add sensor")
	            {
	            	dialog.dismiss();
	            	Intent in=new Intent (AddChannelActivity.this,AddSensorActivity.class);
					in.putExtra("json",j);
					in.putExtra("secretcode",CommonThing.code);
				    startActivity(in);
	            }
	            else if (choice[which]=="Proceed to submit")
	            {
	            	dialog.dismiss();
	            	Intent nIntent = new Intent (AddChannelActivity.this,SubmittDeviceActivity.class);
					CommonThing.ch_index=0;
					nIntent.putExtra("json",j);
					nIntent.putExtra("secretcode",CommonThing.code);
				    startActivity(nIntent);
	            }
	        }
	    });
	    alert.show();
		Looper.loop();
	}
	
}