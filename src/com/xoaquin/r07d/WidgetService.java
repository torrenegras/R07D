
//SERVICIO QUE LLAMA VIEWS FACTORY DE WIDGET qmhD

package com.xoaquin.r07d;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService
{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {   
    	
    	/*
    	//SUJETO A OPTIMIZACION 3
    	 RemoteViews remoteViews = new RemoteViews(this.getPackageName(),R.layout.widget_qmhd); //probando de todo
    	 ComponentName thisWidget = new ComponentName(this,MyWidgetProvider.class); //probando de todo
    	 AppWidgetManager.getInstance( this ).updateAppWidget( thisWidget, remoteViews ); //probando de todo
    	 */
        return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
    }

}