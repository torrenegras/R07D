
//CLASE PARA ONCREATE DE APP EN GENERAL..  

package com.xoaquin.r07d;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.PushService;

import android.app.Application;
import android.content.Context;

public class DefaultApplication extends Application{ 
		
	private static Context context; //iniciando variable context para toda app
	
	@Override
    public void onCreate() {
        super.onCreate();
      
        Parse.enableLocalDatastore(this); //Inicializacion de LDS antes de inicializacion de PARSE
        Parse.initialize(this, "KfBj6ivkLAaLTXhssMZkjp0MTp5DWhezdpprtYqo", "ePZ6T7RmvGGW87nNO0Oe9Th23H0Je7dwLyTOY4w3");  //Inicializacion de Parse DB  y demas para toda la app
        ParseFacebookUtils.initialize("757717534254589");  //Inicializacion de FB
        PushService.setDefaultPushCallback(this, MainActivity.class); //Accion default para mensajes PUSH
   
        context=getApplicationContext();//para tener context en toda la app y llamarlo desde cualquier class
		
	}

	public static Context getCustomAppContext(){ //llamar context en todas partes de la app  
         return context;
    }
  

}