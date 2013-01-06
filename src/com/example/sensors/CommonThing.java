package com.example.sensors;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

//import android.R.integer;
import android.app.Application;
import android.util.Log;

public class CommonThing extends Application{
	private static String TAG="CommonThing";
	public static final String TAG_APINAME = "apiname";
	public static final String TAG_STATUSCODE ="statuscode";
	public static final String TAG_MESSAGE ="message";
	public static String TAG_DEVNAME="devicename";
	public static String TAG_NAME="name";
	public static String TAG_IP="IP";
	public static String TAG_LATITUDE="latitude";
	public static String TAG_LONGITUDE="longitude";
	public static String TAG_LOACTION="location";
	public static String TAG_TAG="tags";
	public static String TAG_SENSORs="sensors";
	public static String TAG_CHANNELS="channels";
	public static String TAG_SAMPLINGPERIOD="samplingperiod";
	public static String TAG_TYPE="type";
	public static String TAG_UNIT="unit";
	public static int ch_index=0;
	public static String add="http://";
    public static String TAG_URL=add+"192.168.1.33";
	public static String code;
	public static String TAG_username;
	//public static String username;
	public static String convertStreamToString(InputStream is) 
	{
		//int s = is.available();
		//byte buf[] = new byte[s];
		//is.read(buf);
				
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	public static String sendHttpRequest(String url,String json)
	{ 
		
		HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		HttpPost post = new HttpPost(url);
		StringEntity se;
		JSONObject jsent;
		try {
			jsent = new JSONObject(json);
			TAG_username=jsent.getString("username");
			Log.i(TAG,TAG_username);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			se = new StringEntity(json);
			se.setContentType((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			
	        post.setEntity(se);
	        
	        response = client.execute(post);
	        System.out.println("ENTERE SEND----");
	       
	       
	        if(response!=null){
                 InputStream in = response.getEntity().getContent(); //Get the data in the entity
                 String ans = CommonThing.convertStreamToString(in);
                 try {
         			JSONObject j=new JSONObject(ans);
         			code=j.getString("message");
         			Log.i(TAG,code);
         		} catch (JSONException e1) {
         			// TODO Auto-generated catch block
         			e1.printStackTrace();
         		}
                 return ans;
	        }
	        else
	        {
	        	Log.e(TAG,"no response");
	        }
	     
		}
		 catch(ConnectTimeoutException e){
			 e.printStackTrace();
            }catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  
        
	}
	
	
	
	
	
	
}


