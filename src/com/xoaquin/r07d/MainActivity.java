package com.xoaquin.r07d;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;



public class MainActivity extends Activity {

	
	public static String correoglobal; 
	
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        
           
        Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
	    Button b1=(Button) findViewById(R.id.button1);
	    TextView t= (TextView) findViewById(R.id.textViewv2);
		b1.setTypeface(kepf);
		t.setTypeface(kepf);
        
        
        
        //Inicializacion de Parse DB
    Parse.initialize(this, "KfBj6ivkLAaLTXhssMZkjp0MTp5DWhezdpprtYqo", "ePZ6T7RmvGGW87nNO0Oe9Th23H0Je7dwLyTOY4w3"); 
    ParseAnalytics.trackAppOpened(getIntent());
    
    PushService.setDefaultPushCallback(this, MainActivity.class);
	ParseInstallation.getCurrentInstallation().saveInBackground();
	ParseAnalytics.trackAppOpened(getIntent());
	PushService.subscribe(this, "todos", MainActivity.class);
	@SuppressWarnings("unused")
	ParseInstallation installation = ParseInstallation.getCurrentInstallation();
	
	
	//NOTIFICACION EN AREA DE NOTIFICACIONES 
	
	NotificationCompat.Builder mBuilder =
    	     new NotificationCompat.Builder(this)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setContentTitle(getString(R.string.app_name))
	        .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.lampp)))
	        .setAutoCancel(false)
	        .setContentText(getString(R.string.lampp));

	Intent resultIntent = new Intent(this, NotiActivity.class);
	resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

	PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	mBuilder.setContentIntent(resultPendingIntent);
	
	NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	mNotificationManager.notify(0, mBuilder.build());

	
	
	
	
	
	
	//CHEQUEO INTERNET PRESENTE
    Boolean b;
     b=isNetworkAvailable();  //true si hay internet,  false si no hay.
    
    if(!b){
    	Toast.makeText(MainActivity.this, getString(R.string.ncon)+"...", Toast.LENGTH_LONG).show(); 
		 
    	 int secondsDelayed = 2;
	        new Handler().postDelayed(new Runnable() {
	                public void run() {
	                        
	                	System.exit(0);
	                }
	        }, secondsDelayed * 1000);
    	
    }
    
    
        final DBAdapter db = new DBAdapter(this); //creando handler SQLITE 
        db.open(); 
        int nr=db.getTitle(1).getCount();  //variable que indica si existe algun dato en SQLITE
   	    final long nri = db.getAllTitles().getCount(); //variable que da el ultimo indice de la tabla SQLITE
        
   	if(nr>0){  //si SQLITE no esta vacia
         String correo= new String (db.getTitle(nri).getString(1)); //saca el correo de ultima fila SQLITE   
         String clave=new String(db.getTitle(nri).getString(2)); //sacando string de clave desde SQLITE
         correoglobal=correo; 
     					
						//loggeando usuario existente {
						 String email= correo; //creando username con primera parte de email
			             String usr[]=email.split("@");
												
		                ParseUser.logInInBackground(usr[0], clave, new LogInCallback() {

		                		 public void done(ParseUser user, ParseException e) {
		                			 
		                			 if (user != null) {//si login exitoso
		                
		                				// Timer que cambia de actividad  a Calendario si match de claves
		  						       int secondsDelayed = 1;
		  						        new Handler().postDelayed(new Runnable() {
		  						                public void run() {
		  						                   	Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
		  						                	i.putExtra("correog",correoglobal);//pasando la variable correo a la siguiente actividad
		  						                	
		  						                	    startActivity(new Intent(i));
		  						                        finish();
		  						                }
		  						        }, secondsDelayed * 1500);
		                				 
		                	    
		                	    } else {//si falla login
		                	    	
		                	    	int secondsDelayed = 1;
		                	        new Handler().postDelayed(new Runnable() {
		                	                public void run() {
		                	                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
		                	                        finish();
		                	                }
		                	        }, secondsDelayed * 1500);

		                	      
		                	    }
		                	  }
		                	});//   }  logincallback
	
					
   
 
   	}    else{ //si SQLITE vacia, uso primera vez...
   	
   		int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                }
        }, secondsDelayed * 1500);

   	}
        
 
   	
 }//oncreate
	
	
	
	
	//funcion conectividad de red
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
