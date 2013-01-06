package com.example.sensors;

import java.util.jar.Attributes.Name;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sensors.CommonThing;
public class SharedPreference extends Activity{
	String TAG="LoginActivity";
	static JSONObject jObj = null;
	String response=null;
	ProgressDialog progressBar; 
	String URL1=":9000/user/login";
	String URL=CommonThing.TAG_URL+URL1;
	//String URL ="http://192.168.48.61:9000/user/login";
	public static final String PREFS_NAME = "MyPrefsFile";    //unique identifier for our Preferences
	private static final String PREF_USERNAME = "karishma"; //fields to be saved
	private static final String PREF_PASSWORD = "password";
	//private static final String PREF_CHECKED = "checked";
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shared);
	        
	        
	        //final CheckBox checkBox=(CheckBox)findViewById(R.id.checkBox1);//remeber to name in your .xml file,
	      
	        final EditText uname= (EditText) findViewById(R.id.edit_usr);
	        final EditText pswd =(EditText) findViewById(R.id.edit_pass);
	        System.out.println(URL);
	        Button reg = (Button) findViewById(R.id.btnLogin);
	        
	        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	        String pUsername = pref.getString(PREF_USERNAME, "" );
	        String pPassword = pref.getString(PREF_PASSWORD, "" );
	        
	       // Toast.makeText(getApplicationContext(), pUsername + ":" + pPassword, Toast.LENGTH_LONG).show();
	    		
	      //  Log.i("login shared pref", pUsername + ":" + pPassword);
	        uname.setText(pUsername);
	        pswd.setText(pPassword);
	            		
	  	
	      reg.setOnClickListener(new View.OnClickListener(){
	    	  
	  		public void onClick(View v) {
	  			
	  			  			
				final String username= uname.getText().toString();
	     		final String password= pswd.getText().toString();
	     		SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);   
	     		pref
	    	        .edit()
	    	        .putString(PREF_USERNAME, username)
	    	        .putString(PREF_PASSWORD, password)
	    	        .commit();
	     		 
	    	    final String   username1 = pref.getString(PREF_USERNAME,username);
	    	        final String passwordshare = pref.getString(PREF_PASSWORD, password);
	    	      // String checked=pref.getString(PREF_CHECKED, "FALSE");
	     		  
	    	  
	     		pswd.setText(passwordshare);
	     		 uname.setText(username1);
	     		progressBar =new ProgressDialog(v.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Attempting to register...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressBar.setMax(100);
				progressBar.show();
	     		
	    	  
	    	       
	     	    Thread t = new Thread(){
	     	        public void run() {
	     	                Looper.prepare(); //For Preparing Message Pool for the child Thread
	     	                JSONObject json = new JSONObject();
	     	                try{
	     	                	    json.put("username", username);
		     	                    json.put("password", password);
	     	                
	     	                  
	     	                    String response=CommonThing.sendHttpRequest(URL, json.toString());
	     	                    /*Checking response */
	     	                    if(response!=null){
	     	                        Log.i("TAG",response);
	     	                        JSONObject jObj =new JSONObject(response);
	     	                        
	 	       	         			String statuscode = jObj.getString(CommonThing.TAG_STATUSCODE);
	 	       	         			String message =jObj.getString(CommonThing.TAG_MESSAGE);
	 	       	         			String login_success ="0";      
	 	       	         			if(statuscode.equals(login_success))
		       	         			{
		       	         				progressBar.dismiss();
		       	         				Intent aIntent = new Intent (SharedPreference.this, MenuActivity.class);
		       	         				aIntent.putExtra("secretcode", message);
		       	         				startActivity(aIntent);
		       	         		
		       	         			}
		       	         			else
		       	         			{
		       	         				progressBar.dismiss();
		       	       	         		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();	 Looper.loop();
		       	       	         		
		       	         			}
	     	                      
	     	                       
	     	                    }
	     	                            
	     	                    
	     	                    else
	     	                    {  	
	     	                    	 progressBar.dismiss();
	 	       	                    Log.e(TAG,"No response");
	 	       	                    Toast.makeText(getApplicationContext(),"Login failed (due to some network problem)", Toast.LENGTH_LONG).show();
	     	                    }
	     	                }
	     	                
	     	                catch(Exception e){
	     	                    e.printStackTrace();
	     	                
	     	                }
	     	                Looper.loop(); //Loop in the message queue
	     	            }
	     	        };
	     	        t.start();  
	  			
	  			
	  		}});
	 }}


