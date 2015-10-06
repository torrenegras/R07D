
//ACTIVIDAD DE VISUALIZACION DE NIVELES , PUNTAJES Y GUARDADO DE PUNTOS EN PARSE ACTIVADO CON PUSH MENSUAL

package com.xoaquin.r07d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class NivelesActivity extends Activity {

	private String nombretablausuario="",m="",m2="";
	private int mescal,aniocal,puntajemespasado,objetos;
	private Boolean bn;
	Button b;
	LinearLayout L1;
	TextView tv,tv2,tv3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_niveles);
		
		if(savedInstanceState==null) {
			
		
		//NOTIFICACION EN AREA DE NOTIFICACIONES
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		
		NotificationCompat.Builder mBuilder =
		     new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(getString(R.string.app_name))
		.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.puntaje)))
		.setAutoCancel(false)
		.setSound(alarmSound)
		.setContentText(getString(R.string.puntaje));
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());
		
				
		bn=isNetworkAvailable();
		tv=(TextView) findViewById(R.id.textViewt);
		tv2=(TextView) findViewById(R.id.textViewtxt1NEW);
		tv3=(TextView) findViewById(R.id.textViewt2);
		b=(Button) findViewById(R.id.button1);
		L1 = (LinearLayout) findViewById(R.id.ll);
		
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
		
		tv.setTypeface(kepf);
		tv2.setTypeface(kepf);
		tv3.setTypeface(kepf);
		b.setTypeface(kepf);
		
				
		//iniciando calendario en fecha de hoy
		 Calendar now = Calendar.getInstance(); //calendario, trayendo fecha de hoy
       // diacal = now.get(Calendar.DAY_OF_MONTH);
        mescal=now.get(Calendar.MONTH);
        aniocal=now.get(Calendar.YEAR);
		
		
		ParseUser cu = ParseUser.getCurrentUser();
		if (cu != null) {
				  
		    nombretablausuario = cu.getEmail();
		    nombretablausuario=nombretablausuario.replaceAll("\\.", "");
			nombretablausuario=nombretablausuario.replaceAll("@", "");
			
		} else {
		  // show the signup or login screen
		}
		
		m=Integer.toString(mescal+1);
   	 if(Integer.valueOf(m)<10){
   		 m="0"+m;
   	 } 
   	 m2=m;
   
   	 if(m=="01"){ //primer mes del a�o
   		 m="12";
   		 aniocal=aniocal-1;
   	 }else{
   		
   		 m=Integer.toString(mescal); //busca en mes anterior bajo normalidad
      	 if(Integer.valueOf(m)<10){
      		 m="0"+m;
      	 }
   	 }
   	 
   	 
   	    //cambioschema
		ParseQuery<ParseObject> query = ParseQuery.getQuery("main"); 
		query.whereEqualTo("userdbp",nombretablausuario);
		query.whereEqualTo("mesdbp", m);
	    query.whereEqualTo("aniodbp", Integer.toString(aniocal)); 
	    
	    if (!bn){
        	query.fromLocalDatastore();
        }
	       
	
	    try {
			List<ParseObject> objects= query.find();
			
			
			objetos=objects.size();
			
			
			
			if(mescal==0){
				mescal=12;
			}
			
			
		
			GregorianCalendar mycal = new GregorianCalendar(aniocal, mescal-1, 1);
			int dem= mycal.getActualMaximum(Calendar.DAY_OF_MONTH);  
			
			
			if(dem==28 && objetos>=28){
				objetos=30;
				
			}
			
			if(dem==29 && objetos>=29){
				objetos=30;
				
			}
			
			if(objetos>=31){
				objetos=30;
			}
			
			
			puntajemespasado=objetos*5;
			
			if(puntajemespasado==0){
				puntajemespasado=2;
			}
			
			 
			ParseQuery<ParseObject> q = ParseQuery.getQuery("puntajes");
	        q.whereEqualTo("usuario", nombretablausuario);
	    
	        if (!bn){
	        	q.fromLocalDatastore();
	        	
	        }
	        q.findInBackground(new FindCallback<ParseObject>() {
	            public void done(List<ParseObject> obs, ParseException e) {
	                if (e == null) {
	                   if (obs.size()>0)
	                   {   	                   
	                	   
	                	   ParseObject o=obs.get(0);
	                	   //int pun= Integer.valueOf(o.get("puntaje").toString());
	                	   int pun = o.getInt("puntajeint");
	                	   pun=pun+puntajemespasado;
	         			   b.setText(String.valueOf(pun));
	         			   o.put("puntajeint", pun);
	        			   o.pinInBackground(m2,new SaveCallback(){ //guardando tambien en Local Datastore

	             				@Override
	             				public void done(ParseException e) {
	             					// TODO Auto-generated method stub
	             					
	             				}});
	        			   o.saveEventually();
	               
	                   }else{
	                	 
	                	  ParseObject o2=new ParseObject("puntajes"); 
	                	  int punnuevo=puntajemespasado;
	                	  b.setText(String.valueOf(punnuevo));
	         			  o2.put("usuario", nombretablausuario);
	         			  o2.put("puntajeint", punnuevo);
	         			  o2.pinInBackground(m2,new SaveCallback(){ //guardando tambien en Local Datastore

	              				@Override
	              				public void done(ParseException e) {
	              					// TODO Auto-generated method stub
	              					
	              				}});
	         			 o2.saveEventually();
	                     	                   
	                 }
	                   
	                } else {//error en query, algo salio mal.. sin net, sin cache...  PRIMERA VEZ QUE SE INTENTA QUERY SOBRE ELEMENTO, POR LO QUE ES NUEVO** OPTIMIZACION??**
	                	Toast.makeText(NivelesActivity.this, getString(R.string.errcon), Toast.LENGTH_LONG).show();
	                 
	                }
	            }

	        });
	        
		
	        if(objetos>=0 && objetos<15){
				  tv.setText(getString(R.string.punp));
			      tv3.setText(getString(R.string.punp2)+" +"+String.valueOf(puntajemespasado));
			  }
             if(objetos>=15 && objetos<24){
          	   tv.setText(getString(R.string.punm));
          	   tv3.setText(getString(R.string.punm2)+" +"+String.valueOf(puntajemespasado));
			  }
             if(objetos>=24 && objetos<=30){
          	   tv.setText(getString(R.string.puna));
          	   tv3.setText(getString(R.string.puna2)+" +"+String.valueOf(puntajemespasado));
			  }
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, getString(R.string.errcon), Toast.LENGTH_LONG).show();
		}
	
	    
	   
	    
		}else {//saveinstance
		
		}
	
	}//fin onCreate()

	
	
	
	
	
	//FUNCION COMPARTIR PUNTAJE REDES SOCIALES 
	public void onclicksharepoints(View v) { 
	 
		 View v1 = L1.getRootView();
         v1.setDrawingCacheEnabled(true);
         Bitmap bm = v1.getDrawingCache();
         
		 saveBitmap(bm);
	 }
	 
	 //FUNCION DE APOYO PARA GUARDAR IMAGEN A COMPARTIR
	 public void saveBitmap(Bitmap bitmap) {
	         String filePath = Environment.getExternalStorageDirectory()
	                 + File.separator + "Pictures/screenshot.png";
	         File imagePath = new File(filePath);
	         FileOutputStream fos;
	         try {
	             fos = new FileOutputStream(imagePath);
	             bitmap.compress(CompressFormat.PNG, 100, fos);
	             fos.flush();
	             fos.close();
	             shareimagesocial(filePath);
	         } catch (FileNotFoundException e) {
	             Log.e("GREC", e.getMessage(), e);
	         } catch (IOException e) {
	             Log.e("GREC", e.getMessage(), e);
	         }
	     }
	 
	 
	 //FUNCION PARA COMPARTIR LA IMAGEN EN SI
	 public	 void shareimagesocial(String path) {
	         Intent Intent = new Intent(android.content.Intent.ACTION_SEND);
	         Intent.putExtra(android.content.Intent.EXTRA_EMAIL,
	                 new String[] { "" });
	         Intent.putExtra(android.content.Intent.EXTRA_SUBJECT,
	                 getString(R.string.shrscoresub));
	         Intent.putExtra(android.content.Intent.EXTRA_TEXT,
	                 getString(R.string.shrscoretext));
	         Intent.setType("image/png");
	         
	         Uri myUri = Uri.parse("file://" + path);
	         Intent.putExtra(android.content.Intent.EXTRA_STREAM, myUri);
	         
	         startActivity(android.content.Intent.createChooser(Intent, getString(R.string.shrnoti)));
	     }
	 
	//CHECK NETWORK
			private boolean isNetworkAvailable() { 
				
					ConnectivityManager connectivityManager 
			          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
			}
			
			
			//CONTROL DE BOTON BACK 
			@Override
			public void onBackPressed() {
				
				
					this.finish();
				    NivelesActivity.this.finish();
			
			
			}
			
}
