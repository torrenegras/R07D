package com.xoaquin.r07d;

import java.util.Calendar;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	
	public static String qmhDdbp;

	  @Override
	  public void onUpdate(final Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {

	    
		  // Get all ids
	    ComponentName thisWidget = new ComponentName(context,MyWidgetProvider.class);
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	    
	    for (int widgetId : allWidgetIds) {
	     

		  RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_qmhd);
		  
		  remoteViews.setTextViewText(R.id.textView1, context.getString(R.string.widgettit));
		  
		    String nombretablausuario = ParseUser.getCurrentUser().getEmail();
		    nombretablausuario=nombretablausuario.replaceAll("\\.", "");
			nombretablausuario=nombretablausuario.replaceAll("@", "");
	
			 Calendar now = Calendar.getInstance(); //calendario, trayendo fecha de hoy
	         int diacal = now.get(Calendar.DAY_OF_MONTH);
	         int mescal=now.get(Calendar.MONTH);
	         int aniocal=now.get(Calendar.YEAR);
	         
			String dia=Integer.toString(diacal);
	        String mes=Integer.toString(mescal+1);
	    	String anio=Integer.toString(aniocal);
	    	 
	    	 if(Integer.valueOf(dia)<10){ dia="0"+dia; }
	    	 if(Integer.valueOf(mes)<10){ mes="0"+mes; }
			
	    	 String fecha=dia+"-"+mes+"-"+anio;
	    	 
	    	 ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario);
	        query.whereEqualTo("fechadbp", fecha);
	        query.fromLocalDatastore();
		  
	        try {
				List<ParseObject> objects= query.find();
				if(objects.size()>0){
				ParseObject o= objects.get(0);
				qmhDdbp=o.getString("qmhDdbp");
			    remoteViews.setTextViewText(R.id.textView2, qmhDdbp);
				}else{
					remoteViews.setTextViewText(R.id.textView2, context.getString(R.string.mtl));
				}
			
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	          
	     
	    
	   // Register an onClickListener
	      Intent intent = new Intent(context, MyWidgetProvider.class);

	      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

	      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
	          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	      remoteViews.setOnClickPendingIntent(R.id.textView2, pendingIntent);
	      appWidgetManager.updateAppWidget(widgetId, remoteViews);
	      
	      
	      
	      
	      
	      }
	    

	  
	  }


} 