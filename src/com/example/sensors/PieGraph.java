package com.example.sensors;



import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieGraph {
	JSONObject j_res;
	CategorySeries series = new CategorySeries("Pie Graph");
	
	public Intent getIntent(Context context) {

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
				series.add("Section " + j, l);
		
				
			}

			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		int[] colors= new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN ,Color.RED,Color.GRAY,Color.BLACK,Color.MAGENTA,Color.DKGRAY};
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitle("Pie Chart Demo");
		renderer.setChartTitleTextSize(7);
		renderer.setZoomButtonsVisible(true);

		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Pie");
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
