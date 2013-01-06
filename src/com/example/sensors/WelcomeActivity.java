package com.example.sensors;



import org.json.JSONException;
import org.json.JSONObject;



import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

public class WelcomeActivity extends Activity {
	
	//public static String code;
	String TAG="LoginActivity";
	static JSONObject jObj = null;
	String response=null;
	ProgressDialog progressBar; 
	String URL1=":9000/user/login";
	String URL=CommonThing.TAG_URL+URL1;
	public static final String PREFS_NAME = "MyPrefsFile";    
	private static final String PREF_USERNAME = "username"; 
	private static final String PREF_PASSWORD = "password";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(i);
            }
        });
        
        final EditText uname= (EditText) findViewById(R.id.edit_usr);
        final EditText pswd =(EditText) findViewById(R.id.edit_pass);
        
        Button reg = (Button) findViewById(R.id.btnLogin);
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String pUsername = pref.getString(PREF_USERNAME, "" );
        String pPassword = pref.getString(PREF_PASSWORD, "" );
        uname.setText(pUsername);
        pswd.setText(pPassword);
       
        reg.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				progressBar =new ProgressDialog(v.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Trying to login ...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressBar.setMax(100);
				progressBar.show();
				// TODO Auto-generated method stub
				
			final String username = uname.getText().toString();
			final String password = pswd.getText().toString();
				
			SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);   
	     		pref
	    	        .edit()
	    	        .putString(PREF_USERNAME, username)
	    	        .putString(PREF_PASSWORD, password)
	    	        .commit();
	     		 
	    	final String   username1 = pref.getString(PREF_USERNAME,username);
	    	final String passwordshare = pref.getString(PREF_PASSWORD, password);
	    	pswd.setText(passwordshare);
		    uname.setText(username1);

			Thread t = new Thread(){
					public void run(){
						Looper.prepare(); //For Preparing Message Pool for the child Thread
       	                JSONObject json = new JSONObject();
       	                try{
       	                

       	                	 
	       	                    json.put("username", username);
	       	                    json.put("password", password);
	       	                    response=CommonThing.sendHttpRequest(URL, json.toString());
	       	                    Log.i(TAG,"request sent");
	       	                    /*Checking response */
	       	                    if(response!=null)
	       	                    {
	       	                        Log.i(TAG,response);
	       	       	         		try {
		       	       	         			jObj = new JSONObject(response);
		       	       	         			
		       	       	         			String statuscode = jObj.getString(CommonThing.TAG_STATUSCODE);
		       	       	         			String message =jObj.getString(CommonThing.TAG_MESSAGE);
		       	       	         			String login_success ="0";       	       	         			
		       	       	         			if(statuscode.equals(login_success))
		       	       	         			{
		       	       	         				progressBar.dismiss();
		       	       	         				Intent aIntent = new Intent (WelcomeActivity.this, MenuActivity.class);
		       	       	         				aIntent.putExtra("secretcode", CommonThing.code);
		       	       	         				startActivity(aIntent);
		       	       	         		
		       	       	         			}
		       	       	         			else
		       	       	         			{
		       	       	         				progressBar.dismiss();
		       	       	       	         		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();	 Looper.loop();
		       	       	       	         		
		       	       	         			}
	       	       	         			}
	       	       	         			catch (JSONException e) {
	       	       	         			Log.e(TAG, "Error parsing data " + e.toString());
	       	       	         			}
	       	                    }
	       	                    else
	       	                    {  	
		       	                    progressBar.dismiss();
		       	                    Log.e(TAG,"No response");
		       	                    Toast.makeText(getApplicationContext(),"Login failed (due to some network problem)", Toast.LENGTH_LONG).show();	 Looper.loop();
	       	                    }
	       	            
	       	         		
	       	                }
       	                
       	            
       	                catch(Exception e){
       	                    e.printStackTrace();
       	                 
       	                }
       	                Looper.loop(); //Loop in the message queue
					}
				};
				
				t.start();
			}
		});
    }

   
}

