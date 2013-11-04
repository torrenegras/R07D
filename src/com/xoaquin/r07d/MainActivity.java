package com.xoaquin.r07d;

import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;



public class MainActivity extends Activity {

	//private List<ParseObject> todos;
	public static String correoglobal; 
	
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        
        Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
	    Button b1=(Button) findViewById(R.id.button1);
	    TextView t= (TextView) findViewById(R.id.textView4);
		b1.setTypeface(kepf);
		t.setTypeface(kepf);
        
        
        
        //Inicializacion de Parse DB
    Parse.initialize(this, "KfBj6ivkLAaLTXhssMZkjp0MTp5DWhezdpprtYqo", "ePZ6T7RmvGGW87nNO0Oe9Th23H0Je7dwLyTOY4w3"); 
    ParseAnalytics.trackAppOpened(getIntent());
    
   
     Boolean b;
     b=isNetworkAvailable();  //true si hay internet,  false si no hay.
    
    if(!b){
    	Toast.makeText(MainActivity.this, "Sin Conexion...", Toast.LENGTH_LONG).show(); 
		 
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
         correoglobal=correo;
          
      //Query para buscar correo electronico y retornar clave DESDE PARSE
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TablaAut");
        query.whereEqualTo("correodb", correo);
   	
        query.findInBackground(new FindCallback<ParseObject>() {
          public void done(List<ParseObject> objects, ParseException e) {
        	
        	if(objects.size()>0){ //si el query es exitoso
        		
        		if (e == null) {
					
					String clavedb=objects.get(0).getString("clavedb"); //sacando el string de la clave desde PARSE 
					String clave=new String(db.getTitle(nri).getString(2)); //sacando string de clave desde SQLITE
					
					if(clavedb.equals(clave)){ //comparando claves
						
						// Timer que cambia de actividad  a Calendario si match de claves
						       int secondsDelayed = 1;
						        new Handler().postDelayed(new Runnable() {
						                public void run() {
						                        startActivity(new Intent(MainActivity.this, CalendarioActivity2.class));
						                        finish();
						                }
						        }, secondsDelayed * 1500);
		
						
					}else{  //si clave falla, muy raro ya que existe porque ha habido un registro previo de un usuario
						
						// Timer que cambia de actividad a Login si no match
					       int secondsDelayed = 1;
					        new Handler().postDelayed(new Runnable() {
					                public void run() {
					                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
					                        finish();
					                }
					        }, secondsDelayed * 1500);
	
					}
					
					    
				} else { //error tipo excepcion en query
		            Log.e("score", "Error: " + e.getMessage());
		           
		        }
        		
       
        	}else{ // si el query no es exitoso en PARSE
        		
        		// Timer que cambia de actividad a Login si no hay resultado de query
			       int secondsDelayed = 1;
			        new Handler().postDelayed(new Runnable() {
			                public void run() {
			                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
			                        finish();
			                }
			        }, secondsDelayed * 1500);

        	}
       
        	}//done
   
        });
   
 
   	}    else{ //si SQLITE vacia, uso primera vez...
   	
   		int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                }
        }, secondsDelayed * 1500);

   	}
        
 
    }
	
	
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
