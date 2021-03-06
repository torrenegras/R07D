
//ACTIVIDAD SPLASH SCREEN INICIAL

package com.xoaquin.r07d;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseUser;



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
        
	//Ejemplo de animacion en view
     b1.setAlpha(0f);
	 ObjectAnimator animation1 = ObjectAnimator.ofFloat(b1,"alpha", 1f);
     animation1.setDuration(5000);
	 animation1.start();
		      
    //Estadisticas en PARSE
      ParseAnalytics.trackAppOpenedInBackground(getIntent());
    
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

			
			Boolean b=isNetworkAvailable();
			if(!b){
			Toast.makeText(MainActivity.this, getString(R.string.offmode), Toast.LENGTH_LONG).show(); 
			}
	
					
			ParseUser currentUser = ParseUser.getCurrentUser();
					
					
					if (currentUser != null) { //HAY USER EN CACHE
						 
						 ParseInstallation installation = ParseInstallation.getCurrentInstallation();
						 String locale = getResources().getConfiguration().locale.getDisplayName();
			   		     currentUser.put("locale", locale);
			   		     if(installation.getString("appVersion")!=null){
			   		     currentUser.put("version", installation.getString("appVersion"));
			   		     }
			   		     currentUser.saveEventually();
			   		     
						final String correocu=currentUser.getEmail();
						
						// Timer que cambia de actividad a Calendario si match de claves
						int secondsDelayed = 1;
						new Handler().postDelayed(new Runnable() {
						public void run() {
						Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
						i.putExtra("correog",correocu);//pasando la variable correo a la siguiente actividad
						
						startActivity(new Intent(i));
						finish();
						}
						}, secondsDelayed * 6000);
						
					} else {  //NO HAY USER EN CACHE
					  
						 int secondsDelayed = 1;
					        new Handler().postDelayed(new Runnable() {
					                public void run() {
					                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
					                        finish();
					                }
					        }, secondsDelayed * 6000);
						
						
					}
					
   
 }//fin oncreate


private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager 
          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}




    
}