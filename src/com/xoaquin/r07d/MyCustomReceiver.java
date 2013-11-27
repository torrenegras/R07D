package com.xoaquin.r07d;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



//receptor de JSON de Mensajes Push


public class MyCustomReceiver extends BroadcastReceiver {
private static final String TAG = "MyCustomReceiver";
 
  @Override
  public void onReceive(Context context, Intent intent) {
    try {
      
      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
      
      
      context=DefaultApplication.getCustomAppContext();
      
	                	Intent i = new Intent(context,NivelesActivity.class);
	                	
	                	i.putExtra("msj",json.getString("mensaje"));//pasando la variable correo a la siguiente actividad
	                	 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	                	context.startActivity(i);
	                   
	        
      
      //Log.e("ggg",json.getString("mensaje"));
      
 
     
    } catch (JSONException e) {
      Log.e(TAG, "JSONException: " + e.getMessage());
    }
  }
}