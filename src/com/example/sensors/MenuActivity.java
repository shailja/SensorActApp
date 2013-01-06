package com.example.sensors;



import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class MenuActivity extends ListActivity{
	
static final String[] options = new String[] { "Add", "Delete", "List","Get"};
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       // setContentView(R.layout.menu);
	        Bundle extras = getIntent().getExtras(); 
	        final String code = extras.getString("secretcode");
	        setListAdapter(new ArrayAdapter<String>(this, R.layout.menu,options));
			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					String value = (String) getListAdapter().getItem(position);
					if(value.equals("Add"))
					{
						Intent aIntent = new Intent (MenuActivity.this, AddDeviceActivity.class);
	         			aIntent.putExtra("secretcode", code);
	         			startActivity(aIntent);
					}
					else if(value.equals("Delete"))
					{
						Intent deleteIntent = new Intent (MenuActivity.this, DeleteDevice.class);
         				deleteIntent.putExtra("secretcode", code);
         				startActivity(deleteIntent);
					}
					else if(value.equals("List"))
					{
						Intent listIntent = new Intent (MenuActivity.this, ListDeviceActivity.class);
         				listIntent.putExtra("secretcode", code);
         				startActivity(listIntent);
					}
					else if(value.equals("Get"))
					{
						Intent getIntent = new Intent (MenuActivity.this, GetDevice.class);
         				getIntent.putExtra("secretcode", code);
         				startActivity(getIntent);
					}
				/*	else if(value.equals("Query"))
					{
						Intent getIntent = new Intent (MenuActivity.this, QueryDevice.class);
         				getIntent.putExtra("secretcode", code);
         				startActivity(getIntent);
					}
*/

				}

				
			});
	 }

}