
//CLASE PARA ONCREATE DE APP EN GENERAL..  

package com.xoaquin.r07d;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Application;
import android.content.Context;


public class DefaultApplication extends Application{ 
	
	
	private static Context context; //iniciando variable context para toda app
	
	@Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "KfBj6ivkLAaLTXhssMZkjp0MTp5DWhezdpprtYqo", "ePZ6T7RmvGGW87nNO0Oe9Th23H0Je7dwLyTOY4w3"); 
       
        //Inicializacion de Parse DB  y demas para toda la app
        
        
      //comandos para notificaciones push activados en esta clase
      		
            PushService.setDefaultPushCallback(this, MainActivity.class);
      		ParseInstallation.getCurrentInstallation().saveEventually();
      		
      		PushService.subscribe(this, "todos", MainActivity.class);
      	
      		
      		ParseUser cu = ParseUser.getCurrentUser(); 
      		if(cu!=null){
      		String nombretablausuario=cu.getEmail();
      		nombretablausuario=nombretablausuario.replaceAll("\\.", "");
 			nombretablausuario=nombretablausuario.replaceAll("@", "");	
 			PushService.subscribe(this, nombretablausuario,MainActivity.class); //suscripcion a canal dedicado para cada instalacion.
      		}
 			
      		
      		String locale = getResources().getConfiguration().locale.getDisplayName();
      		     		
      		if(locale.contains("espa\u00F1ol")){ 
      		
      			PushService.subscribe(this, "jalert", JalertActivity.class);
      			PushService.unsubscribe(this, "jalerting");
      		}else{
      			PushService.subscribe(this, "jalerting", JalertActivity.class);
      			PushService.unsubscribe(this, "jalert");
      		}
      		
      		 context=getApplicationContext();//para tener context en toda la app y llamarlo desde cualquier class
	
	}


    public static Context getCustomAppContext(){ //llamar context en todas partes de la app  
      return context;
    }
  

}