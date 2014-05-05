package com.xoaquin.r07d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class WidgetRemoteViewsFactory implements RemoteViewsFactory
{
private Context context = null;
private int appWidgetId;


public static String qmhDdbp;

private List<String> widgetList = new ArrayList<String>();
//private DBHelper dbhelper;

public WidgetRemoteViewsFactory(Context context, Intent intent)
{
    this.context = context;
    appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID);
    Log.d("AppWidgetId", String.valueOf(appWidgetId));
    //dbhelper = new DBHelper(this.context);
}

private void updateWidgetListView()
{
    
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
 	 //String fecha="09"+"-"+"10"+"-"+"2014";
 	 ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario);
     query.whereEqualTo("fechadbp", fecha);
     query.fromLocalDatastore();
	  
     try {
			List<ParseObject> objects= query.find();
			if(objects.size()>0){
			ParseObject o= objects.get(0);
			qmhDdbp=o.getString("qmhDdbp");
		   
			
			String[] widgetFruitsArray = {qmhDdbp};
		    List<String> convertedToList = new ArrayList<String>(Arrays.asList(widgetFruitsArray));
		    this.widgetList = convertedToList;
			
			}else{
			
				
				String[] widgetFruitsArray = {context.getString(R.string.mtl)};
			    List<String> convertedToList = new ArrayList<String>(Arrays.asList(widgetFruitsArray));
			    this.widgetList = convertedToList;
			}
		
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       
	
	
	
}

@Override
public int getCount()
{
    return widgetList.size();
}

@Override
public long getItemId(int position)
{
    return position;
}

@Override
public RemoteViews getLoadingView()
{
    // TODO Auto-generated method stub
    return null;
}

@Override
public RemoteViews getViewAt(int position)
{
    Log.d("WidgetCreatingView", "WidgetCreatingView");
    RemoteViews remoteView = new RemoteViews(context.getPackageName(),
            R.layout.widgetlistlayout);

    Log.d("Loading", widgetList.get(position));
    remoteView.setTextViewText(R.id.textView1, widgetList.get(position));

    return remoteView;
}

@Override
public int getViewTypeCount()
{
    // TODO Auto-generated method stub
    return 1;
}

@Override
public boolean hasStableIds()
{
    // TODO Auto-generated method stub
    return false;
}

@Override
public void onCreate()
{
    // TODO Auto-generated method stub
    updateWidgetListView();
}

@Override
public void onDataSetChanged()
{
    // TODO Auto-generated method stub
    updateWidgetListView();
}

@Override
public void onDestroy()
{
    // TODO Auto-generated method stub
    widgetList.clear();
    //dbhelper.close();
}
}