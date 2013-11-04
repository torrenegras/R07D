package com.xoaquin.r07d;

import java.util.List;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//poniendo tipo de letra
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	TextView t1=(TextView) findViewById(R.id.textView1);
		t1.setTypeface(kepf);
		TextView t2=(TextView) findViewById(R.id.textView2);
		t2.setTypeface(kepf);
		//TextView t3=(TextView) findViewById(R.id.textView3);
		//t3.setTypeface(kepf);
		EditText et1=(EditText) findViewById(R.id.editText1);
		et1.setTypeface(kepf);
		EditText et2=(EditText) findViewById(R.id.editText2);
		et2.setTypeface(kepf);
		Button b1=(Button) findViewById(R.id.button1); 
		b1.setTypeface(kepf);
		Button b2=(Button) findViewById(R.id.button2); 
		b2.setTypeface(kepf);
		Button b3=(Button) findViewById(R.id.button3); 
		b3.setTypeface(kepf);
		
		
		
		
		//trayendo ultimo correo usado para loggin
		final DBAdapter db = new DBAdapter(this);  
	     db.open(); 
	      
	   
	      int nr=db.getTitle(1).getCount();
	      long nri = db.getAllTitles().getCount();
	      
	      if (nr>0){
	      String correodb=db.getTitle(nri).getString(1);
	      EditText correo=(EditText) findViewById(R.id.editText1);
	      
	      correo.setText(correodb);
	      
	      }else{
	    	  
	      }
	
	}

	public void onclickconectarse(View view) { //click boton conectarse
		
		final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.VISIBLE);
		  
			   
			    //Extraccion de correo y clave de edittext's
				EditText correo=(EditText) findViewById(R.id.editText1);
			    EditText clave=(EditText) findViewById (R.id.editText2);
			    final String etcorreo=correo.getText().toString();
			    final String etclave=clave.getText().toString();
			    final DBAdapter db = new DBAdapter(this);  
			    
			    
			    
			    
			    MainActivity.correoglobal=etcorreo;
			    
	        
			    Boolean b;
			     b=isNetworkAvailable();  //true si hay internet,  false si no hay.
			    
			    if(!b){
			    	Toast.makeText(LoginActivity.this, "Sin Conexion...", Toast.LENGTH_LONG).show(); 
					 
			    	pb.setVisibility(View.INVISIBLE); 
			    }else{
			        
			
			    //query basada en el correo dado por usuario
	        ParseQuery<ParseObject> query = ParseQuery.getQuery("TablaAut");
	        query.whereEqualTo("correodb", etcorreo);
	        query.findInBackground(new FindCallback<ParseObject>() {
	        @Override
				public void done(List<ParseObject> objects, ParseException e) {
					
				//si existe resultado positivo de query se compara clave dada por usuario y la de PARSE
	        	if(objects.size()>0){
	        	
					if (e == null) {
						String clavedb=objects.get(0).getString("clavedb"); //sacando el string de la clave 
						
						if(clavedb.equals(etclave)){ //si clave positiva
							
							Toast.makeText(LoginActivity.this, "Conectando.......", Toast.LENGTH_LONG).show(); 
							
							//insertando en DB LOC SQLITE
			        		db.open(); 
			                db.insertTitle(etcorreo, etclave);
							
							int secondsDelayed = 1;
						        new Handler().postDelayed(new Runnable() {
						                public void run() {
						                        startActivity(new Intent(LoginActivity.this, CalendarioActivity2.class));
						                        finish();
						                }
						        }, secondsDelayed * 1000);
							
						}else{//si clave negativa
							Toast.makeText(LoginActivity.this, "Clave Errada..Intente de Nuevo..", Toast.LENGTH_LONG).show(); 					
							pb.setVisibility(View.INVISIBLE); 
						}
						
						
			        
					
					
					} else {//si hay un problema con el query tipo excepcion
			            Log.d("score", "Error: " + e.getMessage());
			        }
			 
	        	}else{ //si no existe resulado de query porque no existe usuario
	        		Toast.makeText(LoginActivity.this, "Usuario No Existe..Intente de Nuevo", Toast.LENGTH_LONG).show();
	        		pb.setVisibility(View.INVISIBLE); 
	        	}
	        
	        }
				
	        });
	        
			    }
			    
	}
	
	
	
	public void onclickregistrarse(View view) {  //click boton registrarse
		
		final Button b2=(Button) findViewById(R.id.button2); 
		b2.setEnabled(false);
		
		final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.VISIBLE);
		
		
		       //Extraccion de correo y clave de edittext's
				EditText correo=(EditText) findViewById(R.id.editText1);
			    EditText clave=(EditText) findViewById (R.id.editText2);
			    final String etcorreo=correo.getText().toString();
			    final String etclave=clave.getText().toString();
			    final DBAdapter db = new DBAdapter(this);  
			    
			    MainActivity.correoglobal=etcorreo;
		
			    
			    Boolean b;
			     b=isNetworkAvailable();  //true si hay internet,  false si no hay.
			    
			    if(!b){
			    	Toast.makeText(LoginActivity.this, "Sin Conexion...", Toast.LENGTH_LONG).show(); 
					 
			    	 
			    }else{
			    
			    
		if(etcorreo.length()>0 && etclave.length()>=4){  //si hay un correo valido y clave(mas de 4 char) validos
		
			
		//query basada en el correo dado por usuario
	        ParseQuery<ParseObject> query = ParseQuery.getQuery("TablaAut");
	        query.whereEqualTo("correodb", etcorreo);
	        query.findInBackground(new FindCallback<ParseObject>() {
	      	@Override
				public void done(List<ParseObject> objects, ParseException e) {
					
				//si existe ya un usuario con ese correo en PARSE
	        	if(objects.size()>0){
	        		Toast.makeText(LoginActivity.this, "Usuario Existente...Conectarse", Toast.LENGTH_LONG).show();
	        	
	        	}else{ //si no existe el usuario en PARSE entonces se crea
	        		
	        		//insertando en DB LOC SQLITE
	        		db.open(); 
	                db.insertTitle(etcorreo, etclave);
	                
	                //Creacion de objeto en PARSE
	                ParseObject TablaAut = new ParseObject("TablaAut"); //Creando Class o tabla de autenticacion de usuarios
	                TablaAut.put("correodb", etcorreo); //creando campo o columna en clase
	                TablaAut.put("clavedb", etclave);//creando campo o columna en clase
	                
	                
	                TablaAut.saveInBackground();
	                
	                
	              //registra el usuario y activa el boton cuando finaliza correctamente
    		        TablaAut.saveInBackground(new SaveCallback() {
    		        	   public void done(ParseException e) {
    		        	     if (e == null) {
    		        	    	    		        	   
    		        	    	 int secondsDelayed = 1;
    		 	                new Handler().postDelayed(new Runnable() {
    		 	                        public void run() {
    		 	                                startActivity(new Intent(LoginActivity.this, CalendarioActivity2.class));
    		 	                                finish();
    		 	                        }
    		 	                }, secondsDelayed * 1000);
    		 	                
    		 	                Toast.makeText(LoginActivity.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
    		 	               b2.setEnabled(true);//success
  		        	    	 pb.setVisibility(View.INVISIBLE);

    		        	     
    		        	     } else {
    		        	       //fail
    		        	     }
    		        	   }
    		        	 });
	                
	    
	        	
	        	}
	       
	        }	
	        });	
	
		
		}else{//si no hay correo valido proporcionado por usuario
			Toast.makeText(LoginActivity.this, "Ingrese Email y Contraseña Válidos..", Toast.LENGTH_LONG).show();			
		}
		
		
		
			    }
		
		
	}
	
	
	
	
	public void onclickdesc(View view) {
		
		 final DBAdapter db = new DBAdapter(this);  
		db.open(); 
        db.insertTitle("", "desc");
        
        //Para llamar el HOME y salir de APP
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
		
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
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
