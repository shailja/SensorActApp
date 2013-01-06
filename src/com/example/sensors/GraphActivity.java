package com.example.sensors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GraphActivity extends Activity{
	public String j;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        Bundle extras = getIntent().getExtras();
         j = extras.getString("response");
        
    }
	
	
	
	public void lineGraphHandler (View view)
    {
		
		
    	LineGraph line = new LineGraph();
    	line.getValue(j);
    	Intent lineIntent = line.getIntent(this);
    
        startActivity(lineIntent);
    }
	
	 public void pieGraphHandler (View view)
	    {
	    	PieGraph pie = new PieGraph();
	    	pie.getValue(j);
	    	Intent lineIntent = pie.getIntent(this);
	        startActivity(lineIntent);
	    }    
	 public void barGraphHandler (View view)
	    {
	    	BarGraph bar = new BarGraph();
	    	bar.getValue(j);
	    	Intent lineIntent = bar.getIntent(this);
	        startActivity(lineIntent);
	    }
	 public void scatterGraphHandler (View view)
	    {
	    	ScatterGraph scatter = new ScatterGraph();
	    	scatter.getValue(j);
	    	Intent lineIntent = scatter.getIntent(this);
	        startActivity(lineIntent);
	    }    
	    
}
