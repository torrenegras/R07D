package com.xoaquin.r07d;

import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Application;
import android.content.Context;


//Clase para OnCreate de la aplicacion en general....   

public class DefaultApplication extends Application{ 
	
	
	private static Context context;
	
	@Override
    public void onCreate() {
        super.onCreate();

      //comandos para notificaciones push activados en esta clase
      		PushService.setDefaultPushCallback(this, MainActivity.class);
      		ParseInstallation.getCurrentInstallation().saveInBackground();
      		//Intent I = new Intent() ;
      	
      		//ParseAnalytics.trackAppOpened(I);//  interrogante lo del intent aqui..........
      		PushService.subscribe(this, "todos", MainActivity.class);
      		//ParseInstallation installation = ParseInstallation.getCurrentInstallation();  //lo quite por ahora...?

   
	
      		 context=getApplicationContext();
	
	}







    public static Context getCustomAppContext(){
      return context;
    }
  

}