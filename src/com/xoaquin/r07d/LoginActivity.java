
//ACTIVIDAD DE LOGIN, ETC.

package com.xoaquin.r07d;

import java.util.Arrays;
import java.util.HashMap;
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {

	String FBemail="";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//COLOR AL ACTIONBAR
		ActionBar ab=getActionBar();
		Drawable drw = getResources().getDrawable( R.drawable.b1 );
		ab.setBackgroundDrawable(drw);
	
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
		Button b2a=(Button) findViewById(R.id.button2a); 
		Button b3=(Button) findViewById(R.id.button3); 
		b3.setTypeface(kepf);
		TextView t3=(TextView) findViewById(R.id.textViewv1);
		t3.setTypeface(kepf);
		
		//cambiando background de boton FB
		String locale = getResources().getConfiguration().locale.getDisplayName();
   		if(locale.contains("espa\u00F1ol")){ 
  		b2a.setBackgroundResource(R.drawable.iniciafbesp);
  		}else{
  				
  		}
		
		
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
	      db.close();
	
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
						    
			    MainActivity.correoglobal=etcorreo;  // se usa???  optimizacion?
			    
			                
			                
			                ParseUser.logInInBackground(etcorreo, etclave, new LogInCallback() {
			                	  public void done(ParseUser user, ParseException e) {
			                	    if (user != null) {
			                	      
			                	    	ParseInstallation installation = ParseInstallation.getCurrentInstallation();
			   						    String locale = getResources().getConfiguration().locale.getDisplayName();
			   			   		        user.put("locale", locale);
			   			   		        if (installation.getString("appVersion")!=null){
			   			   		        user.put("version", installation.getString("appVersion"));
			   			   		        }
			   			   		        user.saveInBackground();
			   			   		     
			                	    	
			                	    	//insertando en DB LOC SQLITE
						        		db.open(); 
						                db.insertTitle(etcorreo, etclave);
									    db.close();
						                
						                Toast.makeText(LoginActivity.this, getString(R.string.conn)+".......", Toast.LENGTH_LONG).show(); 
						                
						                
						                //llamando actividad calendario principal
										int secondsDelayed = 1;
									        new Handler().postDelayed(new Runnable() {
									                public void run() {
									                	Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
									                	i.putExtra("correog",etcorreo);//pasando la variable correo a la siguiente actividad
									                   	startActivity(new Intent(i));
									                    finish();
									                }
									        }, secondsDelayed * 1000);
						          
			                	    	
			                	    } else {
			                	    	
			            			    Boolean b;
			            			     b=isNetworkAvailable();  //true si hay internet,  false si no hay.
			            			    
			            			    if(!b){
			            			    	Toast.makeText(LoginActivity.this, getString(R.string.ncon)+"...", Toast.LENGTH_LONG).show(); 
			            					pb.setVisibility(View.INVISIBLE); 
			            			    }else{
			            			   
			                	    	Toast.makeText(LoginActivity.this, getString(R.string.uce)+"...."+getString(R.string.uce2)+"..", Toast.LENGTH_LONG).show(); 					
										pb.setVisibility(View.INVISIBLE); 
			            			    }
			                	    }
			                	  }
			                	
			                
			                
			                });
			        
			
			    
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
			    	Toast.makeText(LoginActivity.this, getString(R.string.ncon)+"...", Toast.LENGTH_LONG).show(); 
			    	 pb.setVisibility(View.INVISIBLE);
			    	 b2.setEnabled(true);
			    	 
			    }else{
			    
			    
		if(etcorreo.length()>0 && etclave.length()>=4){  //si hay un correo valido y clave(mas de 4 char) validos
		
			
                        //creacion de objeto con nuevo usuario para PARSE
                        ParseUser user = new ParseUser();
    	                user.setUsername(etcorreo);
    	                user.setPassword(etclave);
    	                user.setEmail(etcorreo);
    	                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
						String locale = getResources().getConfiguration().locale.getDisplayName();
			   		    user.put("locale", locale);
			   		 if (installation.getString("appVersion")!=null){
			   		    user.put("version", installation.getString("appVersion"));
			   		 }
    	                
    	                user.signUpInBackground(new SignUpCallback() {
    	                 
    	                	public void done(ParseException e) {
    	                    if (e == null) {
    	                    	
    	                    	
    	                    	//insertando en DB LOC SQLITE
    	    		              db.open(); 
    	                          db.insertTitle(etcorreo, etclave);
    	                          db.close();
    	                          
    	                    	//llamando funcion Cloud email nuevo usuario y cuenta usuarios totales
    	    	                HashMap<String, Object> params = new HashMap<String, Object>();
    	    	                params.put("correo", etcorreo);
    	    	                
    	    	                ParseCloud.callFunctionInBackground("infonewusers", params, new FunctionCallback<String>() {
    	    	                   public void done(String response, ParseException e) {
    	    	                       if (e == null) {
    	    	                          // respuesta de la funcion en response si se requiere
    	    	                       }
    	    	                   }
    	    	                }); 
    	    	                
    	    	                //llamando funcion cloud de envio de email de bienvenida al usuario
    	    	                ParseCloud.callFunctionInBackground("bienvenidausr", params, new FunctionCallback<String>() {
     	    	                   public void done(String response, ParseException e) {
     	    	                       if (e == null) {
     	    	                          // respuesta de la funcion en response si se requiere
     	    	                       }
     	    	                   }
     	    	                }); 
    	    	                
    	    	                
    	                    	
    	    	                //cambiando de actividad dado signup exitoso
    	    	                int secondsDelayed = 1;
     		 	                new Handler().postDelayed(new Runnable() {
     		 	                        public void run() {
     		 	                        	Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
 						                	i.putExtra("correog",etcorreo);//pasando la variable correo a la siguiente actividad
 						                	
 						                	    startActivity(new Intent(i));
     		 	                                finish();
     		 	                        }
     		 	                }, secondsDelayed * 1000);
     		 	                
     		 	                Toast.makeText(LoginActivity.this, getString(R.string.rex), Toast.LENGTH_LONG).show();
     		 	                //b2.setEnabled(true); no se necesita
   		        	    	    pb.setVisibility(View.INVISIBLE);
   		        	    	 
   		        	    	 
    	                    } else {
    	                    	 Toast.makeText(LoginActivity.this, getString(R.string.uec)+"..."+getString(R.string.uec2), Toast.LENGTH_LONG).show();
    	                    	 b2.setEnabled(true);//success
       		        	    	 pb.setVisibility(View.INVISIBLE);
    	                    }
    	                  }
    	                });
            	    	
          
		}else{//si no hay correo o clave validos proporcionados por usuario
			Toast.makeText(LoginActivity.this, getString(R.string.iecv)+"..", Toast.LENGTH_LONG).show();			
			 b2.setEnabled(true);//success
   	    	 pb.setVisibility(View.INVISIBLE);
		}
		
		
		
	}
		
		
}//registrarse
	
	
public void onclickconectarseFB(View view) {
	 final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
	 pb.setVisibility(View.VISIBLE);
	  
	 
	 Boolean b=isNetworkAvailable();
	 if(b){
		 
	
		 
/*Comentando FB antiguo		 
	ParseFacebookUtils.logIn(Arrays.asList("email", Permissions.Friends.ABOUT_ME),this, new LogInCallback() {
		  @Override
		  public void done(ParseUser user, ParseException err) {
		    if (user == null) {
		      Log.e("MyApp", "Uh oh. The user cancelled the Facebook login.");
		      pb.setVisibility(View.INVISIBLE);
		    
		    } else if (user.isNew()) {
		      Log.e("MyApp", "User signed up and logged in through Facebook!");
		    	
		            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
				    String locale = getResources().getConfiguration().locale.getDisplayName();
	   		        user.put("locale", locale);
	   		        user.put("version", installation.getString("appVersion"));
	   		        user.saveInBackground();
	   		        
		    	final Session session=Session.getActiveSession();
		    	Request request=Request.newMeRequest(session, 
		                new Request.GraphUserCallback() {
		            @Override
		            public void onCompleted(GraphUser userg, Response response) {
		                // If the response is successful
		                if (session == Session.getActiveSession()) {
		                    if (userg != null) {
		                    	
		                    	 final String fbemail=response.getGraphObject().getProperty("email").toString();
		                    
		                    	ParseUser cu= ParseUser.getCurrentUser();
		                    	cu.setEmail(fbemail);
		                    	cu.saveEventually();
		                    	
		                    	
		                     	//llamando funcion Cloud email nuevo usuario y cuenta usuarios totales
    	    	                HashMap<String, Object> params = new HashMap<String, Object>();
    	    	                params.put("correo", fbemail);
    	    	                
    	    	                ParseCloud.callFunctionInBackground("infonewusers", params, new FunctionCallback<String>() {
    	    	                   public void done(String response, ParseException e) {
    	    	                       if (e == null) {
    	    	                          // respuesta de la funcion en response si se requiere
    	    	                       }
    	    	                   }
    	    	                }); 
    	    	                
    	    	                //llamando funcion cloud de envio de email de bienvenida al usuario
    	    	                ParseCloud.callFunctionInBackground("bienvenidausr", params, new FunctionCallback<String>() {
     	    	                   public void done(String response, ParseException e) {
     	    	                       if (e == null) {
     	    	                          // respuesta de la funcion en response si se requiere
     	    	                       }
     	    	                   }
     	    	                }); 
    	    	                
    	    	                
    	                    	
    	    	                //cambiando de actividad dado signup exitoso
    	    	                int secondsDelayed = 1;
     		 	                new Handler().postDelayed(new Runnable() {
     		 	                        public void run() {
     		 	                        	Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
 						                	i.putExtra("correog",fbemail);//pasando la variable correo a la siguiente actividad
 						                	
 						                	    startActivity(new Intent(i));
     		 	                                finish();
     		 	                        }
     		 	                }, secondsDelayed * 1000);
		                    	
		                    	
		                    	
		                    	
		                    	
		                    	
		                    }
		                }
		                if (response.getError() != null) {
		                    // Handle errors, will do so later.
		                }
		            }

				
		        });
		        request.executeAsync();
		        
		     
		    } else {
		      Log.e("MyApp", "User logged in through Facebook!");
		      
		     
	    	final Session session=Session.getActiveSession();
	    	Request request=Request.newMeRequest(session, 
	                new Request.GraphUserCallback() {
	            @Override
	            public void onCompleted(GraphUser userg, Response response) {
	                // If the response is successful
	                if (session == Session.getActiveSession()) {
	                    if (userg != null) {
	                    
	                    	ParseUser cu= ParseUser.getCurrentUser();
	                    	ParseInstallation installation = ParseInstallation.getCurrentInstallation();
	    				    String locale = getResources().getConfiguration().locale.getDisplayName();
	    	   		        cu.put("locale", locale);
	    	   		        cu.put("version", installation.getString("appVersion"));
	                    	cu.saveInBackground();
	                    	
	                     final String fbemail=response.getGraphObject().getProperty("email").toString(); //solo se usa para pasarlo en el intent de llamado calendario
	                    
	    	                //cambiando de actividad dado signup exitoso
	    	                int secondsDelayed = 1;
		 	                new Handler().postDelayed(new Runnable() {
		 	                        public void run() {
		 	                        	Intent i = new Intent(getApplicationContext(), CalendarioActivity2.class);
					                	i.putExtra("correog",fbemail);//pasando la variable correo a la siguiente actividad
					                	
					                	    startActivity(new Intent(i));
		 	                                finish();
		 	                        }
		 	                }, secondsDelayed * 1000);
	                    	
	                   
	                    	
	                    }
	                }
	                if (response.getError() != null) {
	                    // Handle errors, will do so later.
	                }
	            }

			
	        });
	        request.executeAsync();
		      
		      
		      
		    	
		    }
		  }
		});
	*/
	 
		 ParseFacebookUtils.logInWithReadPermissionsInBackground(this, /*permissions*/Arrays.asList("email", "dd"), new LogInCallback() {
			  @Override
			  public void done(ParseUser user, ParseException err) {
			    if (user == null) {
			      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
			    } else if (user.isNew()) {
			      Log.d("MyApp", "User signed up and logged in through Facebook!");
			    } else {
			      Log.d("MyApp", "User logged in through Facebook!");
			    }
			  }
			});
	 
	 
	 }else{ // si no hay internet
		 Toast.makeText(LoginActivity.this, getString(R.string.ncon)+"...", Toast.LENGTH_LONG).show(); 
    	 pb.setVisibility(View.INVISIBLE);
	 }
		 

}




public void onclickdesc(View view) {
		
		
		ParseUser.logOut();//logout usuario
        
        
        //Para llamar el HOME y salir de APP
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
		
		
	
	
	}
	


	
	@Override
	public void onResume(){
       super.onResume();
	    
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
                                          	  
                    	  Toast.makeText(LoginActivity.this, getString(R.string.esrp)+"..", Toast.LENGTH_LONG).show();
                    	  pb.setVisibility(View.INVISIBLE);
                  		tv3.setEnabled(true);
                  		  
                          } else {
                        	  Toast.makeText(LoginActivity.this, getString(R.string.ncon)+"...", Toast.LENGTH_LONG).show();	
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
	
	/*Comentando FB antiguo
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	 
	}
	*/
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
