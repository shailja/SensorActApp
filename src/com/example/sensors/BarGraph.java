package com.example.sensors;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarGraph{
	JSONObject j_res;
	CategorySeries series = new CategorySeries(" Bar Graph ");
	public Intent getIntent(Context context) 
	{	
		
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
				series.add("Bar " + j, l);
						
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		

		// This is how the "Graph" itself will look like
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Demo Graph Title");
		mRenderer.setXTitle("X VALUES");
		mRenderer.setYTitle("Readings");
		mRenderer.setAxesColor(Color.GREEN);
	    mRenderer.setLabelsColor(Color.RED);
	    mRenderer.setBarSpacing(0.5);
	    // Customize bar 
		XYSeriesRenderer renderer = new XYSeriesRenderer();
	    renderer.setDisplayChartValues(true);
	    renderer.setChartValuesSpacing((float) 0.5);
	    mRenderer.addSeriesRenderer(renderer);
	   
		Intent intent = ChartFactory.getBarChartIntent(context, dataset,mRenderer, Type.DEFAULT);
		return intent;
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
