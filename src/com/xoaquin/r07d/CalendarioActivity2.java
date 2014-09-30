
//***  ACTIVIDAD CORE APP  ****

package com.xoaquin.r07d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CalendarioActivity2 extends Activity implements OnGesturePerformedListener,GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener {
 
 
	public static String fecha,nombredia,dia,mes,anio; //variables globales para toda la app
	private TextView tvmes,tvanio,diatv; //variables globales dentro de esta actividad 
    private GridView gv;
    public int diacal,mescal,aniocal,diatmp,diafm;
    public static int mescalt,aniocalt,voltereta;
	private String nombretablausuario="",m="";
	private MonthAdapter2 mgva;
	public static String[] dcomp= new String[31], dcompp= new String[31];
	private static ProgressBar pb;
	private Boolean b;
	private GestureLibrary gestureLib;
	
	Location mCurrentLocation;
	LocationClient mLocationClient;
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	
	
	
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
	    View inflate = getLayoutInflater().inflate(R.layout.activity_calendario_activity2, null);
	    gestureOverlayView.addView(inflate);
	    gestureOverlayView.addOnGesturePerformedListener(this);
	    gestureOverlayView.setGestureColor(Color.TRANSPARENT);
	    gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
	    if (!gestureLib.load()) {
	      finish();
	    }
	    setContentView(gestureOverlayView);
	    ParseAnalytics.trackAppOpened(getIntent());
	    
		//setContentView(R.layout.activity_calendario_activity2); //original
	    

//Bloque suscripcion a canales y guardado de instalacion actual en PARSE**********************************************************      		
	    
	    String manufacturer = Build.MANUFACTURER;
        String brand        = Build.BRAND;
        String product      = Build.PRODUCT;
        String model        = Build.MODEL;
        
        PushService.subscribe(this, "todos", MainActivity.class); //Suscripcion a canal todos
    	
    		ParseUser cuc = ParseUser.getCurrentUser(); 
    		if(cuc!=null){
    		
    		String nombretablausuario=cuc.getUsername();  
    		
    		if(nombretablausuario!=null){
    		
    			if (nombretablausuario.contains("\\.")||nombretablausuario.contains("@")){
    		              nombretablausuario=nombretablausuario.replaceAll("\\.", "");
			              nombretablausuario=nombretablausuario.replaceAll("@", "");	
    		          }
    		
    		PushService.subscribe(this, nombretablausuario,MainActivity.class); //Suscripcion a canal dedicado unico de usuario, para cada instalacion.
    		}
						
    		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
    		installation.put("actualuser",cuc.getUsername());
    		installation.put("manufacturer", manufacturer);
		    installation.put("brand", brand);
		    installation.put("product", product);
		    installation.put("model", model);
    		installation.saveEventually();  //explorar temas con saveinbackground()/saveeventually  ****************************
    		
    		}
		
    		String locale = getResources().getConfiguration().locale.getDisplayName();
    		     		
    		if(locale.contains("espa\u00F1ol")){ 
    			PushService.subscribe(this, "jalert", JalertActivity.class); //Suscripcion canal para push target Español
    		}else{
    			PushService.subscribe(this, "jalerting", JalertActivity.class); //Suscripcion canal para push target Ingles
    	   		}
    		Log.e("bloque","fin");
//FIN Bloque suscripcion a canales y guardado de instalacion actual en PARSE**********************************************************
    		
    		

         AppRater.app_launched(this); //LLAMANDO DIALOG PARA RATE APP
		//AppRater.showRateDialog(this, null);  MOSTRAR EL DIALOG PARA PRUEBAS
	    //JalertActivity.showRateDialog(this, null); MOSTRAR DIALOG PARA PRUEBAS
	    
	    b=isNetworkAvailable();
	    
		//SETTING DEL CLIENTE DE POSICIONAMIENTO
	    mLocationClient= new LocationClient(this, this, this);

		
		//COLOR AL ACTIONBAR
		ActionBar ab=getActionBar();
		Drawable drw = getResources().getDrawable( R.drawable.b1 );
		ab.setBackgroundDrawable(drw);
		
		
		
		//trayendo correo de la anterior actividad usando intent y no variable global.. manera correcta. 
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    
		    nombretablausuario = extras.getString("correog");
		    
		    if(nombretablausuario!=null){
	      		
      			if (nombretablausuario.contains("\\.")||nombretablausuario.contains("@")){
      		              nombretablausuario=nombretablausuario.replaceAll("\\.", "");
 			              nombretablausuario=nombretablausuario.replaceAll("@", "");	
      		              }
      		
      		    }else{ //Casos raros de usuarios donde no se tiene un correo electronico, se tiene como null. Se saca nombretablausuario de username 
      		    	
      		  ParseUser cu = ParseUser.getCurrentUser(); 
      	      		
      		   if(cu!=null){
      	      		 
      		    nombretablausuario=cu.getUsername();
      	      		
      	         if(nombretablausuario!=null){
      	      		
      	      			                 if (nombretablausuario.contains("\\.")||nombretablausuario.contains("@")){
      	      		                                nombretablausuario=nombretablausuario.replaceAll("\\.", "");
      	 			                                nombretablausuario=nombretablausuario.replaceAll("@", "");	
      	      		                               }
      	      		         		         }	
      		    	          }
		  
		              }
		                     }
		
		
		//cargando variables
		tvmes = (TextView) findViewById(R.id.textViewt);
		tvanio = (TextView) findViewById(R.id.textViewtxt);
		Button brep=(Button) findViewById(R.id.button1);
		gv= (GridView) findViewById(R.id.gridView1);
		pb=(ProgressBar) findViewById(R.id.progressBar1);
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    		        	 
		//iniciando calendario en fecha de hoy
		 Calendar now = Calendar.getInstance(); //calendario, trayendo fecha de hoy
         diacal = now.get(Calendar.DAY_OF_MONTH);
         mescal=now.get(Calendar.MONTH);
         aniocal=now.get(Calendar.YEAR);

       //inicializando variables 
 		String nmes= nombremes(mescal);	
 		tvmes.setText(nmes);
 		tvanio.setText(String.valueOf(aniocal));
 		tvmes.setTypeface(kepf);
 		tvanio.setTypeface(kepf);
 		brep.setTypeface(kepf);
 		
         
         //dia fin de mes para activar push de envio de reporte
         Calendar finmes= Calendar.getInstance();
         finmes.add(Calendar.MONTH, 1);  
         finmes.set(Calendar.DAY_OF_MONTH, 1);  
         finmes.add(Calendar.DATE, -1);  

         diafm=finmes.get(Calendar.DAY_OF_MONTH);
         
         m=Integer.toString(mescal+1);
    	 if(Integer.valueOf(m)<10){
    		 m="0"+m;
    	 } 
        
    
            
   //EJECUCION DE TASK PRINCIPAL Y MANEJO DE CAMBIO DE ORIENTACION
            @SuppressWarnings("deprecation")
			final Object data = getLastNonConfigurationInstance();  //variable de chequeo de cambio de orientacion

	         if (data == null) { //Solo entra en la primera entrada al oncreate() 
      
	     	       
	         voltereta=1;
	         AsyncTaskRunner runner = new AsyncTaskRunner();
             runner.execute(mescal,aniocal);
             
         
             }else{ //Entra cuando hay cambio en configuracion, rotacion, etc..
             
        	 mescal=mescalt; //asignacion de variables persistentes al cambio el mes en el que estaba cuando se efectuo el cambio
        	 aniocal=aniocalt; //anio en que estaba cuando se hizo cambio
        	 inflandogridview(dcompp,mescal,aniocal); //inflando con ultimos datos que habian al hacer cambio
      	 
                 }
	
 	 
 //ONCLICK GRIDVIEW
		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   
				 voltereta=0;
			     diatv=(TextView) v;
			     dia=diatv.getText().toString();
			     diatmp=Integer.valueOf(dia);
			     
			     if(diatmp<10){dia="0"+dia;}
		    	 			     
		    	 mes=Integer.toString(mescal+1);
		    	 anio=Integer.toString(aniocal);
		    	 
		    	 if(Integer.valueOf(mes)<10){ mes="0"+mes; }
		    	 
		    	 if(aniocal<10){ dia="0"+dia; }
				
		    	 fecha=dia+"-"+mes+"-"+anio;
		    			    	 
		    	 Calendar calendar = new GregorianCalendar(aniocal,mescal,diatmp); // Note that Month value is 0-based. e.g., 0 for January.
		    	 int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
		    	 
		    	 nombredia=nomdia(dayofweek);
		    			    	 
		    	 Intent i = new Intent(getApplicationContext(), RecdiaActivity2.class);
             	i.putExtra("correog",nombretablausuario);
             	i.putExtra("fca",fecha);
               	i.putExtra("ndca",nombredia);
            	i.putExtra("dca",dia);
               	i.putExtra("mca",mes);
             	i.putExtra("aca",anio);
             	 startActivity(new Intent(i));  
			}
		});
	   	
} 
	

	
	//IMPLEMENTACION ASYNCTASK, FETCH PARSE POR FUERA DE MAIN THREAD
	
private class AsyncTaskRunner extends AsyncTask<Integer, Integer, Integer> {	
	
	protected void onPreExecute() {
		pb.getIndeterminateDrawable().setColorFilter(0xFF8d665f, android.graphics.PorterDuff.Mode.MULTIPLY);
		pb.setVisibility(View.VISIBLE);
	}
	

    protected Integer doInBackground(Integer... params) {
		
		dcomp=listadiascompletados(params[0],params[1]); //Recuperando desde PARSE un listado de dias completados, poniendo en arreglo local solo pasa en Oncreate()
   	    dcompp=dcomp; //asignando arreglo persistente para cada vez que hay un cambio
       
   	    int u=1; //variable dummy de retorno para ingresar a onPostExecute
   	    return u;
    }
	
	
    protected void onPostExecute(Integer x) {
		 
		 inflandogridview(dcomp,mescal,aniocal); //funcion para llamar adaptador e inflar gridview con datos
		 pb.setVisibility(View.GONE);
	  }
	

}
	

 //ONRESUME() de actividad
		@Override
		public void onResume()
		    {  
		    super.onResume();
		   
		    if(voltereta==1){}else{
		    
			 AsyncTaskRunner runner2 = new AsyncTaskRunner();
             runner2.execute(mescal,aniocal);
		    }
		     
		   
		    }
		
	
//FUNCION LLAMADA AL EFECTUARSE CAMBIO EN CONFIGURACION, ROTACION, ETC..  
	@Override
	public Object onRetainNonConfigurationInstance() {
		
		mescalt=mescal;  //mes que hay cuando se hace cambio , persiste
		aniocalt=aniocal; //anio que hay cuando se hace cambio, persiste
		voltereta=1;
		return nombretablausuario;   //solo variable dummy de Retorno, no se usa...
	    
	}


	
//FLECHA DERECHA
  public void clickfleder(View v) { //boton flecha derecha calendario
	 
	  Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	  // Vibrate for x milliseconds
	   vib.vibrate(100);
	
	if(mescal==11){
		aniocal++;
		tvanio.setText(String.valueOf(aniocal));
	}
		
	if(mescal<11){
	mescal++;}else{mescal=0;}
	
	String nmes= nombremes(mescal);	
	tvmes.setText(nmes);
	
	 AsyncTaskRunner runner3 = new AsyncTaskRunner();
     runner3.execute(mescal,aniocal);
	
	}
	
	
//FLECHA IZQUIERDA
  public void clickfleizq(View v) { //boton flecha derecha calendario
	
	  Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	  // Vibrate for x milliseconds
	   vib.vibrate(100);
	   
	if(mescal==0){
		aniocal--;
		tvanio.setText(String.valueOf(aniocal));
	}
		
	if(mescal>0){
	mescal--;}else{mescal=11;}
	
	String nmes= nombremes(mescal);	
	tvmes.setText(nmes);
	
	 AsyncTaskRunner runner4 = new AsyncTaskRunner();
     runner4.execute(mescal,aniocal);
     
	}
		
	
//ONCLICK REPORTES
    public void onclickgenrepo(View v) { //boton inicio actividad MENU de generacion reportes	
    
    
	 Intent i = new Intent(getApplicationContext(), ReporteActivity.class);
	 i.putExtra("correog",nombretablausuario);//pasando la variable correo a la siguiente actividad
	 
	 startActivity(new Intent(i));  //llamando la actividad de registro acorde a fecha seleccionada
	 
    
	}



//FUNCION LLAMADO PARSE PARA OBTENER LISTA DE DIAS COMPLETADOS EN ESE MES/Aï¿½O
    public String[] listadiascompletados(int mescalf,int aniocalf){
	  
    
    	
	 String nmcomp=String.valueOf(mescalf+1);
     if((mescalf+1)<10){nmcomp="0"+nmcomp;}
     String nycomp=String.valueOf(aniocalf);
    
     
     ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario); //query para buscar records de ese mes y aï¿½o en orden ascendente
	 query.whereEqualTo("mesdbp", nmcomp);
     query.whereEqualTo("aniodbp", nycomp);
     query.orderByAscending("diadbp");
   
     if(!b){
     query.fromLocalDatastore();
     }
     
     try {
    	 	List<ParseObject> objects=query.find();
    	 	   String[] res= new String[objects.size()];
	           int i=0;
               while(i<objects.size()){
            	   if(objects.get(i).getString("lbdbp").length()>0)   //si se ha hecho lectura biblica
            	   {            	        		   
            		   String obj=objects.get(i).getString("diadbp");
            		   if(Integer.valueOf(obj)<10){obj=obj.replaceAll("0", "");} //quitandole los 0's iniciales a dias PARSE
            		   res[i]=obj;   
            	   }
            	   i++;
                                      }
                
                 return res;
                 
	  
          } catch (ParseException e1 ) {  //falla query 
            	 
            	 String[] res2= new String[1];
            	 res2[0]= getString(R.string.errcon)+"...";
            	 return res2;
          }  
          
        
   }



//FUNCION INFLADO DE GRIDVIEW
public void inflandogridview(String[] dcompl,int mescall,int aniocall){
	List<String> lista = Arrays.asList(dcompl);
	boolean contains = lista.contains(getString(R.string.errcon));
	if(contains){
		Toast.makeText(this, getString(R.string.errcon), Toast.LENGTH_LONG).show();
	}
	
	String nmes= nombremes(mescall);	
	tvmes.setText(nmes);
	tvanio.setText(String.valueOf(aniocall));
	
	//INFLANDO GRIDVIEW
    final DisplayMetrics metrics = new DisplayMetrics();  //contruyendo el adaptador 
 	getWindowManager().getDefaultDisplay().getMetrics(metrics);
    
 	mgva= new MonthAdapter2(this,mescall,aniocall,metrics,dcompl);  
 	
 	//CALCULANDO Y SETTEANDO ALTURA TOTAL DEL GRIDVIEW
 	
	int gridviewtot =                        (metrics.heightPixels  		//tamaï¿½o de toda la pantalla segun dispositivo
            															//menos:
											- getBarHeight()             //espacio de actionbar segun densidad de pantalla 		      
											- 50                         //espacio flechas de control calendario
											- 40                         //espacio del boton REPORTES
											 - ( (metrics.heightPixels/100)*16 ) //offset
											) ; 
	
	LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) gv.getLayoutParams();
	lp.height = gridviewtot;
	gv.setLayoutParams(lp);
	gv.setAdapter(mgva);
}
	


//FUNCION OBTENER NOMBRE DE MES
public String nombremes(int nummes){
		
		String nommes="";
		
		if(nummes==0){nommes=getString(R.string.ene);}
		if(nummes==1){nommes=getString(R.string.feb);}
		if(nummes==2){nommes=getString(R.string.mar);}
		if(nummes==3){nommes=getString(R.string.abr);}
		if(nummes==4){nommes=getString(R.string.may);}
		if(nummes==5){nommes=getString(R.string.jun);}
		if(nummes==6){nommes=getString(R.string.jul);}
		if(nummes==7){nommes=getString(R.string.ago);}
		if(nummes==8){nommes=getString(R.string.sept);}
		if(nummes==9){nommes=getString(R.string.oct);}
		if(nummes==10){nommes=getString(R.string.nov);}
		if(nummes==11){nommes=getString(R.string.dic);}
	
		return nommes;
	}


//FUNCION OBTENER NOMBRE DE DIA
public String nomdia(int dayofweek){
	
	String nomdia="";
	
	 if(dayofweek==1){nomdia=getString(R.string.ndom);}
	 if(dayofweek==2){nomdia=getString(R.string.nlun);}
	 if(dayofweek==3){nomdia=getString(R.string.nmart);}
	 if(dayofweek==4){nomdia=getString(R.string.nmie);}
	 if(dayofweek==5){nomdia=getString(R.string.njue);}
	 if(dayofweek==6){nomdia=getString(R.string.nvie);}
	 if(dayofweek==7){nomdia=getString(R.string.nsab);}

	return nomdia;
}
	

//FUNCION OBTENER ALTURA BARRA DE PANTALLA
private int getBarHeight() {
  
	DisplayMetrics metrics=new DisplayMetrics();
	
	switch (metrics.densityDpi) {
  case DisplayMetrics.DENSITY_HIGH:
          return 48;
  case DisplayMetrics.DENSITY_MEDIUM:
          return 32;
  case DisplayMetrics.DENSITY_LOW:
          return 24;
  case DisplayMetrics.DENSITY_XHIGH:
      return 66;
 
  default:
          return 48;
  }
}




//INFLADO DE MENU
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.calendario_activity2, menu);
		
		return true;
	}
	

	
//TRIGGERS MENU	
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    //respond to menu item selection
		
		switch (item.getItemId()) {
	    case R.id.item1:
	    startActivity(new Intent(this, LoginActivity.class));
	    return true;
	   
	    case R.id.item2:
	    startActivity(new Intent(this, InstActivity.class));
	    return true;
	   
	    
	    case R.id.item3:
		startActivity(new Intent(this, AboutActivity.class));
		return true;
	 
	   
	    case R.id.mur:
	    	 Intent in = new Intent(getApplicationContext(), MuroActivity.class);
	    	 startActivity(new Intent(in));  
		   	return true;
			
	    case R.id.shr:
	    	Intent intent = new Intent(Intent.ACTION_SEND);
	    	intent.setType("text/plain");
	    	intent.putExtra(Intent.EXTRA_SUBJECT, "R07D");
	    	intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shr));
	    	startActivity(Intent.createChooser(intent, "Share with"));
			return true;
		 	
	    case R.id.puntaje:
	    	startActivity(new Intent(this, NivelesMenuActivity.class));
	    	return true;
	    	
		    
	    default:
	    return super.onOptionsItemSelected(item);
	}
		
		
		
	}


	//TRES METODOS AUTOGENERADOS PARA LA IMPLEMENTACION DE LOCATION
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
    
	}


	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
		/*
		//RECIBIENDO POSICION ACTUALIZADA
	    mCurrentLocation = mLocationClient.getLastLocation();
       
	    if(mCurrentLocation!=null){
	    Log.e("lat",Double.toString(mCurrentLocation.getLatitude()));
	    Log.e("lon",Double.toString(Math.abs(mCurrentLocation.getLongitude())));
	    
	    Double lat=mCurrentLocation.getLatitude();
	    Double lon=Math.abs(mCurrentLocation.getLongitude());
	    
	    //Su Presencia GeoFence
	    if (lat>4.683319&&lat<4.686110){
	    	if(lon>74.059575&&lon<74.063798){
	    		Toast.makeText(this, "GEOFENCE SUPRESENCIA!!!", Toast.LENGTH_LONG).show();
	  		          
	    	}
	    }
	    
         }
		*/
	}


	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	
	//CONEXION AL CLIENTE DE POSICIONAMIENTO CUANDO LA ACTIVIDAD ES VISIBLE
	@Override
    protected void onStart() {
        super.onStart();
        
        if (checkPlayServices()) {
        	mLocationClient.connect();
          }
        
      
        
    }

    //DESCONEXION DEL CLIENTE AL DESAPARECER LA ACTIVIDAD
	@Override
    protected void onStop() {
		 super.onStop();
		 
		if (checkPlayServices()) {
	        	mLocationClient.disconnect();
	          }
       
		//PROCESO UNPIN LOCALDATASTORE DOS MESES ATRAS
		if(diacal==1){
			
			
			String mesunpin=String.valueOf(mescal-1);
			
			if((mescal+1)<10){
				mesunpin="0"+mesunpin;
			}
			
			if((mescal+1)==1){
				mesunpin="11";
			}
			
            if((mescal+1)==2){
            	mesunpin="12";
			}
			
			
			ParseObject.unpinAllInBackground(mesunpin);
			
			
		}
        
       
    }

	
	
	//3 METODOS COMPLEMENTARIOS PARA :  CHECK STATUS GOOGLE PLAY SERVICES PARA PODER USARLO EN GEOLOCALIZACION

	private boolean checkPlayServices() {
		  int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		  if (status != ConnectionResult.SUCCESS) {
		    if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
		      showErrorDialog(status);
		    } else {
		      Toast.makeText(this, getString(R.string.devnotsup), 
		          Toast.LENGTH_LONG).show();
		      finish();
		    }
		    return false;
		  }
		  return true;
		} 

	
	void showErrorDialog(int code) {
		  GooglePlayServicesUtil.getErrorDialog(code, this, 
		      REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
		}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  switch (requestCode) {
	    case REQUEST_CODE_RECOVER_PLAY_SERVICES:
	      if (resultCode == RESULT_CANCELED) {
	        Toast.makeText(this, getString(R.string.gpsmustbeins),
	            Toast.LENGTH_SHORT).show();
	        finish();
	      }
	      return;
	  }
	  super.onActivityResult(requestCode, resultCode, data);
	}
	
	//CHECK NETWORK
		private boolean isNetworkAvailable() { 
			
				ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}


		@Override
		public void onGesturePerformed(GestureOverlayView overlay,Gesture gesture) {
			// TODO Auto-generated method stub
			ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
		    for (Prediction prediction : predictions) {
		      if (prediction.score > 1.0) {
		        
		    	  
		        if(prediction.name.contains("right")){
		        	
		        	Log.e("predic","right");
		        	
		        	Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		       	  // Vibrate for x milliseconds
		       	   vib.vibrate(100);
		       	
		       	if(mescal==11){
		       		aniocal++;
		       		tvanio.setText(String.valueOf(aniocal));
		       	}
		       		
		       	if(mescal<11){
		       	mescal++;}else{mescal=0;}
		       	
		       	String nmes= nombremes(mescal);	
		       	tvmes.setText(nmes);
		       	
		       	 AsyncTaskRunner runner3 = new AsyncTaskRunner();
		            runner3.execute(mescal,aniocal);
		        }
		        
                 
		        if(prediction.name.contains("left")){
                	  Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                	  // Vibrate for x milliseconds
                	   vib.vibrate(100);
                	   
                	if(mescal==0){
                		aniocal--;
                		tvanio.setText(String.valueOf(aniocal));
                	}
                		
                	if(mescal>0){
                	mescal--;}else{mescal=11;}
                	
                	String nmes= nombremes(mescal);	
                	tvmes.setText(nmes);
                	
                	 AsyncTaskRunner runner4 = new AsyncTaskRunner();
                     runner4.execute(mescal,aniocal);
                     
		        }
		        
		        
		        
		      }
		    }
		}
		
		

}
