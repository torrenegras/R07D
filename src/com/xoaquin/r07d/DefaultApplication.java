package com.xoaquin.r07d;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Application;
import android.content.Context;


//Clase para OnCreate de la aplicacion en general....   

public class DefaultApplication extends Application{ 
	
	
	private static Context context; //iniciando variable context para toda app
	
	@Override
    public void onCreate() {
        super.onCreate();
        
        
        //Inicializacion de Parse DB  y demas para toda la app
        
        Parse.initialize(this, "KfBj6ivkLAaLTXhssMZkjp0MTp5DWhezdpprtYqo", "ePZ6T7RmvGGW87nNO0Oe9Th23H0Je7dwLyTOY4w3"); 
        
        
      //comandos para notificaciones push activados en esta clase
      		PushService.setDefaultPushCallback(this, MainActivity.class);
      		ParseInstallation.getCurrentInstallation().saveInBackground();
      		//Intent I = new Intent() ;
      	
      		//ParseAnalytics.trackAppOpened(I);//  interrogante lo del intent aqui..........
      		PushService.subscribe(this, "todos", MainActivity.class);
      		//ParseInstallation installation = ParseInstallation.getCurrentInstallation();  //lo quite por ahora...?

   
	
      		 context=getApplicationContext();//para tener context en toda la app y llamarlo desde cualquier class
	
	}







    public static Context getCustomAppContext(){ //llamar context en todas partes de la app  
      return context;
    }
  

}