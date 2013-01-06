package com.example.sensors;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;




public class LineGraph{
	
	

	JSONObject j_res;
public Intent getIntent(Context context) {
		
	
	
	
	TimeSeries series = new TimeSeries("Channel Readings"); 
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
			series.add(j,l);
		
			
		}
		
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
		XYSeriesRenderer renderer = new XYSeriesRenderer(); 
		renderer.setDisplayChartValues(true);
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setXTitle("Count");
		mRenderer.setYTitle("Readings");
		mRenderer.setZoomButtonsVisible(true);
		// Customization time for line
		renderer.setColor(Color.BLUE);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setLineWidth(2);
		renderer.setFillPoints(true);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Line Graph ");
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
