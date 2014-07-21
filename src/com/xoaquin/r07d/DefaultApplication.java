
//CLASE PARA ONCREATE DE APP EN GENERAL..  

package com.xoaquin.r07d;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Application;
import android.content.Context;
import android.os.Build;

public class DefaultApplication extends Application{ 
	
	
	private static Context context; //iniciando variable context para toda app
	
	@Override
    public void onCreate() {
        super.onCreate();
       
        String manufacturer = Build.MANUFACTURER;
        String brand        = Build.BRAND;
        String product      = Build.PRODUCT;
        String model        = Build.MODEL;
        
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "KfBj6ivkLAaLTXhssMZkjp0MTp5DWhezdpprtYqo", "ePZ6T7RmvGGW87nNO0Oe9Th23H0Je7dwLyTOY4w3"); 
       
        //Inicializacion de Parse DB  y demas para toda la app
        
        
      //comandos para notificaciones push activados en esta clase
      		
            PushService.setDefaultPushCallback(this, MainActivity.class);
      		
      		ParseFacebookUtils.initialize("757717534254589");
      		
      		PushService.subscribe(this, "todos", MainActivity.class);
      	
      		ParseUser cu = ParseUser.getCurrentUser(); 
      		if(cu!=null){
      		//String nombretablausuario=cu.getEmail();  //original channel personalizado desde email
      		String nombretablausuario=cu.getUsername();  //channel personalizado desde username para incluir usuarios FB sin email...raro pero pasó
      		
      		if(nombretablausuario!=null){
      		
      			if (nombretablausuario.contains("\\.")||nombretablausuario.contains("@")){
      		              nombretablausuario=nombretablausuario.replaceAll("\\.", "");
 			              nombretablausuario=nombretablausuario.replaceAll("@", "");	
      		          }
      		PushService.subscribe(this, nombretablausuario,MainActivity.class); //suscripcion a canal dedicado para cada instalacion.
      	
      		}
 						
      		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
      		installation.put("actualuser",cu.getUsername());
      		installation.put("manufacturer", manufacturer);
  		    installation.put("brand", brand);
  		    installation.put("product", product);
  		    installation.put("model", model);
      		installation.saveEventually();
      		
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