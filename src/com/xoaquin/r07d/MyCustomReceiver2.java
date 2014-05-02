package com.xoaquin.r07d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyCustomReceiver2 extends BroadcastReceiver {

 
  @Override
  public void onReceive(Context context, Intent intent) {
    context=DefaultApplication.getCustomAppContext();
     
	Intent i = new Intent(context,AvisosImportantesDefActivity.class);
	
	
	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	context.startActivity(i);
  }
}