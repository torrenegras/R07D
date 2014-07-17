
//PROVIDER MOTOR DE WIDGETS

package com.xoaquin.r07d;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

	public static int number = (new Random().nextInt(100));
	
	  @Override
	  public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {

			    
	     appWidgetManager = AppWidgetManager.getInstance(context);
         appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MyWidgetProvider.class));
         appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView1);
			  
			    
	    for(int i=0;i<appWidgetIds.length;i++) {

	     RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_qmhd);
		 remoteViews.setTextViewText(R.id.textView1, context.getString(R.string.widgettit));
		   
		
		  
	   // Register an onClickListener
	      Intent intent = new Intent(context, MainActivity.class);
          PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
	      remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
	    
	      //intent2 para llamar servicio para listview del widget
	      Intent intent2 = new Intent(context, WidgetService.class);
          intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetIds[i]);
          //intent2.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));  //original
          intent2.setData(Uri.fromParts("content", String.valueOf(appWidgetIds[i]+number), null)); //para engañar y refresh listview
          remoteViews.setRemoteAdapter(R.id.listView1, intent2);
      
	      appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
	    
	      }
	    super.onUpdate(context, appWidgetManager, appWidgetIds);


	  
	  }


} 