package com.example.sensors;



import java.util.ArrayList;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActuatorActivity extends ListActivity{
	String code;
	
	String[] devicetype = new String[]{"Sensor", "Actuator"};

			   /** Called when the activity is first created. */
			   @Override
			   public void onCreate(Bundle savedInstanceState) {
				 
			       super.onCreate(savedInstanceState);
			      
			       setContentView(R.layout.list);
			      
			       setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,devicetype));
			  
			   }
			       protected void onListItemClick(ListView l, View v, int position,
							long id) {
			    	   Bundle extras = getIntent().getExtras(); 
				        String code = extras.getString("secretcode");
				        final  String j = extras.getString("json");
						// TODO Auto-generated method stub
						super.onListItemClick(l, v, position, id);
						Object o=this.getListAdapter().getItem(position);
						String value=o.toString();
						
						if(value.equals("Sensor"))
						{
							Intent aIntent = new Intent (SensorActuatorActivity.this, AddSensorActivity.class);
							
							aIntent.putExtra("json",j);
							aIntent.putExtra("secretcode", code);
		         			startActivity(aIntent);
						}
						else if(value.equals("Actuator"))
						{
							Toast.makeText(this,"This device"+value+"not implemented yet!",Toast.LENGTH_LONG).show();
						}
						}  
			   
			   
			   }

		//	@Override
			
			
			   
			   
			   
			