package com.xoaquin.r07d;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

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
    	TextView t1=(TextView) findViewById(R.id.textViewt);
		t1.setTypeface(kepf);
		TextView t2=(TextView) findViewById(R.id.textViewtxt);
		t2.setTypeface(kepf);
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
		TextView t3=(TextView) findViewById(R.id.textViewv1);
		t3.setTypeface(kepf);
		
		
		
		
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
			        
			
			                //loggeando usuario existente
			                String email= etcorreo; //creando username con primera parte de email
			                String usr[]=email.split("@");
			                
			                ParseUser.logInInBackground(usr[0], etclave, new LogInCallback() {
			                	  public void done(ParseUser user, ParseException e) {
			                	    if (user != null) {
			                	      
			                	    	//insertando en DB LOC SQLITE
						        		db.open(); 
						                db.insertTitle(etcorreo, etclave);
									
						                
						                Toast.makeText(LoginActivity.this, "Conectando.......", Toast.LENGTH_LONG).show(); 
						                
						                
						                //llamando actividad calendario principal
										int secondsDelayed = 1;
									        new Handler().postDelayed(new Runnable() {
									                public void run() {
									                	Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
									                	i.putExtra("correog",etcorreo);//pasando la variable correo a la siguiente actividad
									                	Log.e("cglacon",etcorreo);
									                	startActivity(new Intent(i));
									                    finish();
									                }
									        }, secondsDelayed * 1000);
						          
			                	    	
			                	    } else {
			                	    	Toast.makeText(LoginActivity.this, "Usuario/Clave Errados....Intente de Nuevo..", Toast.LENGTH_LONG).show(); 					
										pb.setVisibility(View.INVISIBLE); 
			                	    }
			                	  }
			                	});
			        
			    }//else conexion
			    
	}//conectarse
	
	
	
	
	
	
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
		
			
			
			//check usuario existente
            String email= etcorreo; //creando username con primera parte de email
            String usr[]=email.split("@");
            
            ParseUser.logInInBackground(usr[0], etclave, new LogInCallback() {
            	  public void done(ParseUser userl, ParseException e) {
            	    if (userl != null) {
            	      
            	    	Toast.makeText(LoginActivity.this, "Usuario Existente...Conectarse", Toast.LENGTH_LONG).show();
            	    	 b2.setEnabled(true);//success
		        	    	 pb.setVisibility(View.INVISIBLE);
            	    	
            	    } else {
            	    	
            	    	//insertando en DB LOC SQLITE
    	        		db.open(); 
    	                db.insertTitle(etcorreo, etclave);
    	                
    	                //Creacion de Usuario en PARSE 
    	                String email= etcorreo; //creando username con primera parte de email
    	                String usr[]=email.split("@");
    	                            
    	               
    	                
    	                ParseUser user = new ParseUser();
    	                user.setUsername(usr[0]);
    	                user.setPassword(etclave);
    	                user.setEmail(etcorreo);
    	                user.put("puntaje", 0);
    	                
    	               
    	                
    	                user.signUpInBackground(new SignUpCallback() {
    	                 
    	                	public void done(ParseException e) {
    	                    if (e == null) {
    	                    	 
    	                    	int secondsDelayed = 1;
     		 	                new Handler().postDelayed(new Runnable() {
     		 	                        public void run() {
     		 	                        	Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
 						                	i.putExtra("correog",etcorreo);//pasando la variable correo a la siguiente actividad
 						                	
 						                	    startActivity(new Intent(i));
     		 	                                finish();
     		 	                        }
     		 	                }, secondsDelayed * 1000);
     		 	                
     		 	                Toast.makeText(LoginActivity.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
     		 	               b2.setEnabled(true);//success
   		        	    	 pb.setVisibility(View.INVISIBLE);
   		        	    	 
   		        	    	 
    	                    } else {
    	                    	 Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
    	                    	 b2.setEnabled(true);//success
       		        	    	 pb.setVisibility(View.INVISIBLE);
    	                    }
    	                  }
    	                });
            	    	
            	    	
            	    
            	    }
            	  }
            	});
			
	
		}else{//si no hay correo valido proporcionado por usuario
			Toast.makeText(LoginActivity.this, "Ingrese Email y Contraseña Válidos..", Toast.LENGTH_LONG).show();			
			 b2.setEnabled(true);//success
   	    	 pb.setVisibility(View.INVISIBLE);
		}
		
		
		
	}
		
		
}//registrarse
	
	
	
	
	public void onclickdesc(View view) {
		
		 final DBAdapter db = new DBAdapter(this);  
		db.open(); 
        db.insertTitle("", "desc");
        
        ParseUser.logOut();//logout usuario
        
        
        //Para llamar el HOME y salir de APP
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
		
	}
	
	public void olvidoclave(View view) {

		
		
		final TextView tv3=(TextView) findViewById (R.id.textViewv1);
		final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		
		pb.setVisibility(View.VISIBLE);
		tv3.setEnabled(false);
		  
		
		EditText correo=(EditText) findViewById(R.id.editText1);
				
		ParseUser.requestPasswordResetInBackground(correo.getText().toString(),
                new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                      if (e == null) {
                                          	  
                    	  Toast.makeText(LoginActivity.this, "Se ha enviado correo para resetear la clave..", Toast.LENGTH_LONG).show();
                    	  pb.setVisibility(View.INVISIBLE);
                  		tv3.setEnabled(true);
                  		  
                          } else {
                        	  Toast.makeText(LoginActivity.this, "Ups! Algo pasó: "+e.getMessage(), Toast.LENGTH_LONG).show();	
                        	  pb.setVisibility(View.INVISIBLE);
                        		tv3.setEnabled(true);
                           }
                        }
                        });
		
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
