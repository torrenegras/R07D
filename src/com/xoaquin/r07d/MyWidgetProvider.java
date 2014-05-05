package com.xoaquin.r07d;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {


	  @Override
	  public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {

	    
		  for(int i=0;i<appWidgetIds.length;i++) {
	     

		  RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_qmhd);
		  
		  	  
		  remoteViews.setTextViewText(R.id.textView1, context.getString(R.string.widgettit));
		  
		   
	     
	    
	   // Register an onClickListener
	        Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


	      
	      remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
	      
	      //intent2 para llamar servicio para listview del widget
	      Intent intent2 = new Intent(context, WidgetService.class);
          intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                  appWidgetIds[i]);
          intent2.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

          remoteViews.setRemoteAdapter(R.id.listView1, intent2);
	      
	      
	      
	      appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
	          
	      
	      }
	    super.onUpdate(context, appWidgetManager, appWidgetIds);


	  
	  }


} 