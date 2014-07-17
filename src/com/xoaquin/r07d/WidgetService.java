
//SERVICIO QUE LLAMA VIEWS FACTORY DE WIDGET qmhD

package com.xoaquin.r07d;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService
{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {   
    	
        return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
    }

}