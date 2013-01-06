package com.example.sensors;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class ShowResult extends Activity{
	Context context=this;
	String TAG=null;
	String code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras(); 
        final String response = extras.getString("answer");
        code = extras.getString("secretcode");
		setContentView(R.layout.add_activity);
		 Log.e(TAG,response);
    		try {
    			JSONObject jObj = new JSONObject(response);
    		
    			String statuscode = jObj.getString(CommonThing.TAG_STATUSCODE);
    			String message =jObj.getString(CommonThing.TAG_MESSAGE);
    			String success ="0";
    			if(statuscode.equals(success))
    			{
    			
    				showDialogue("Added successfully");
    				        		
    			}
    			else
    			{
    				
    				showDialogue(message);
  	         	
  	         		
    			}
    		}catch(Exception e){
    			Log.e(TAG,e.toString());
    		}
		
		
		
}
	
	private void showDialogue(String j)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Add status");
		alertDialogBuilder
			.setMessage(j)
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() { 
        		public void onClick(DialogInterface dialog, int whichButton) { 
        			Intent aIntent = new Intent (ShowResult.this,MenuActivity.class);
         			aIntent.putExtra("secretcode", code);
         			startActivity(aIntent);
        		}
			  });
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();Looper.loop();
	}
}