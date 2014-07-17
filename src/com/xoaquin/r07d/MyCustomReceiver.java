
//RECEPTOR DE JSON DE MENSAJES PUSH NIVELES Y PUNTAJES PERSONALES

package com.xoaquin.r07d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyCustomReceiver extends BroadcastReceiver {

 
  @Override
  public void onReceive(Context context, Intent intent) {
   
      
      
                        context=DefaultApplication.getCustomAppContext();
      
	                	Intent i = new Intent(context,NivelesActivity.class);
	                	
	                	
	                	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	                	context.startActivity(i);
	                   
	 
  
  }
}