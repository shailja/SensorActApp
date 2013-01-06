package com.example.sensors;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class ScatterGraph{
	JSONObject j_res;
	XYSeries series = new XYSeries("Series 1"); 
	public Intent getIntent(Context context) {
		
		
		
		// Data 1
		int[] x = {1, 2, 3, 4, 5, 6 ,7, 8 ,9, 10};
		
		
		try {
			JSONArray jarry=j_res.getJSONArray("wavesegmentArray");
			JSONObject j_wav=jarry.getJSONObject(0);
			JSONObject j_data=j_wav.getJSONObject("data");
			JSONArray j_arry_ch=j_data.getJSONArray("channels");
			JSONObject j_ch=j_arry_ch.getJSONObject(0);
			JSONArray j_readings=j_ch.getJSONArray("readings");
			for(int j=0;j<j_readings.length();j++)
			{
			
				
				double l=j_readings.getDouble(j);
				series.add(x[j], l);
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	 	dataset.addSeries(series);
	
	 	
	 	
	    // Customization  for data 
	    XYSeriesRenderer renderer = new XYSeriesRenderer();
	    renderer.setColor(Color.BLUE);
	    renderer.setPointStyle(PointStyle.DIAMOND);
	    renderer.setLineWidth(6);
	   
	    // Customization
	    XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	    mRenderer.addSeriesRenderer(renderer);
	   
	    
	    return ChartFactory.getScatterChartIntent(context, dataset, mRenderer);
	}
	public void getValue(String j) {
		// TODO Auto-generated method stub
		
		System.out.println(j);
		try {
			j_res=new JSONObject(j);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
