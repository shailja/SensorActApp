package com.example.sensors;


import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUserActivity extends Activity {
	ProgressDialog progressBar; 
	Context context=this;
	String message;
	String code;
	String URL1=":9000/user/register";
	String URL=CommonThing.TAG_URL+URL1;
	
	String TAG="RegisterUserActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		final EditText usrname = (EditText) findViewById(R.id.reg_username);
	    final EditText pswd = (EditText) findViewById(R.id.reg_password);
	    final EditText em = (EditText) findViewById(R.id.reg_email);
	   
	    TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
	    
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
	    Button reg = (Button) findViewById(R.id.btnRegister);
	    reg.setOnClickListener(new OnClickListener(){
	     	public void onClick(View v) {
	     		progressBar =new ProgressDialog(v.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Attempting to register...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressBar.setMax(100);
				progressBar.show();
	     		final String username= usrname.getText().toString();
	     		final String password= pswd.getText().toString();
	     		final String email= em.getText().toString();    
	     	    Thread t = new Thread(){
	     	        public void run() {
	     	                Looper.prepare(); //For Preparing Message Pool for the child Thread
	     	                JSONObject json = new JSONObject();
	     	                try{
	     	                    json.put("username", username);
	     	                    json.put("password", password);
	     	                    json.put("email", email);
	     	                    String response=CommonThing.sendHttpRequest(URL, json.toString());
	     	                    /*Checking response */
	     	                    if(response!=null){
	     	                        Log.i("TAG",response);
	     	                        JSONObject jObj =new JSONObject(response);
	     	                        
	 	       	         			String statuscode = jObj.getString(CommonThing.TAG_STATUSCODE);
	 	       	         			message =jObj.getString(CommonThing.TAG_MESSAGE);
	 	       	         			String login_success ="0";      
	 	       	         			if(statuscode.equals(login_success))
		       	         			{
		       	         				progressBar.dismiss();
		       	         				showDialogue("New User Registered Successfully");
		       	         				
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
	     	                 //   onCreateDialog("Error", "Cannot Estabilish Connection");
	     	                }
	     	                Looper.loop(); //Loop in the message queue
	     	            }
	     	        };
	     	        t.start();  
	     	}
	     	});

		
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
        			Intent aIntent = new Intent (RegisterUserActivity.this,WelcomeActivity.class);
         			aIntent.putExtra("secretcode", message);
         			startActivity(aIntent);
        		}
			  });
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();Looper.loop();
	}
}

