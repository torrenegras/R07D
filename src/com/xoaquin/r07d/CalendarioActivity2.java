package com.xoaquin.r07d;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
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

public class CalendarioActivity2 extends Activity {
 
	public static String fecha,nombredia,dia,mes,anio; //variables globales para toda la app
	private TextView tvmes,tvanio,diatv; //variables globales dentro de esta actividad 
    private GridView gv;
    public int diacal,mescal,aniocal,diatmp,diafm;  
	private String nombretablausuario="",m="";
	private ParseInstallation installation;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendario_activity2);
		setTitle("CALENDARIO");
		
		AppRater.app_launched(this); //LLAMANDO DIALOG PARA RATE APP
		
		
		//COLOR AL ACTIONBAR
		ActionBar ab=getActionBar();
		Drawable drw = getResources().getDrawable( R.drawable.b1 );
		ab.setBackgroundDrawable(drw);
		
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseAnalytics.trackAppOpened(getIntent());
		PushService.subscribe(this, "todos", MainActivity.class);
		installation = ParseInstallation.getCurrentInstallation();
		
		
		//trayendo correo de la anterior actividad usando intent y no variable global.. manera correcta. 
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    nombretablausuario = extras.getString("correog");
		    nombretablausuario=nombretablausuario.replaceAll("\\.", "");
			nombretablausuario=nombretablausuario.replaceAll("@", "");
	 		PushService.subscribe(this, nombretablausuario,MainActivity.class); //suscripcion a canal dedicado para cada instalacion.
		}
		
					
		//cargando variables
		tvmes = (TextView) findViewById(R.id.textViewt);
		tvanio = (TextView) findViewById(R.id.textViewtxt);
		Button brep=(Button) findViewById(R.id.button1);
		gv= (GridView) findViewById(R.id.gridView1);
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
         
        //push progreso fin de mes informe de nivel
         if(diacal==diafm||diacal==diafm-1||diacal==diafm-2||diacal==diafm-3){
        	
        	String chk=installation.getString("mesaniochk"); 
        	
        	  	if(chk==null){// en caso de ser la primera vez para el usuario que se lanza un mensaje push de este tipo
        		
        	  	   	pushnivel();  
        		
        	}else{
        	
        	
        	if(chk.equals(m+Integer.toString(aniocal))){ }//nada ya se lanzo en este mes el push message  
        		else{
        	 
                     pushnivel();
 	              
        	        }//CIERRE ELSE para check de que ya este mes habia lanzado una vez el mensaje push
 	       
               }//CIERRE ELSE, por si no es la primera vez que en la historia que se lanza el mensaje push

            } //CIERRE IF dias finales del mes lance mensaje push      
        
      
           
         //INFLANDO GRIDVIEW
        final DisplayMetrics metrics = new DisplayMetrics();  //contruyendo el adaptador 
     	getWindowManager().getDefaultDisplay().getMetrics(metrics);
     	
     	MonthAdapter mgva= new MonthAdapter(this,mescal,aniocal,metrics ); 
		
     	//CALCULANDO Y SETTEANDO ALTURA TOTAL DEL GRIDVIEW
     	//int numfilas= mgva.getCount()/7;
		int gridviewtot =                        (metrics.heightPixels  		//tama–o de toda la pantalla segun dispositivo
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
			
		
		//onclick gridview
	
		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   
			     diatv=(TextView) v;
			     dia=diatv.getText().toString();
			     diatmp=Integer.valueOf(dia);
			     
			     if(diatmp<10){
		    		 dia="0"+dia;
		    	 }
		    	 
			     
		    	 mes=Integer.toString(mescal+1);
		    	 anio=Integer.toString(aniocal);
		    	 
		    	 if(Integer.valueOf(mes)<10){
		    		 mes="0"+mes;
		    	 }
		    	 
		    	 if(aniocal<10){
		    		 dia="0"+dia;
		    	 }
				
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


	//FUNCION MENSAJES PUSH NIVELES
public void pushnivel(){
			 	 
		ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario); 
  		query.whereEqualTo("mesdbp", m);
	    query.whereEqualTo("aniodbp", Integer.toString(aniocal)); 
    	
	       try {
		List<ParseObject> objects= query.find();
		
		if(objects.size()>=5 && objects.size()<15){
			ParsePush push = new ParsePush();
            push.setChannel(nombretablausuario);
       	    push.setMessage("Puntaje Mes: +5 PUNTOS (R07D Principiante)");
       	    push.sendInBackground();
       	    installation.put("mesaniochk",m+Integer.toString(aniocal));
         	installation.saveInBackground();	
         	
         	
         	try {
        		 JSONObject data = new JSONObject("{\"action\": \"com.xoaquin.r07d.NIVEL\",\"mensaje\": \"Usted ha hecho un buen esfuerzo, siga avanzando! Bendiciones! (R07D Nivel Principiante) +5 PUNTOS \",\"puntos\": \"5\"    }");
				
        		ParsePush pushj = new ParsePush();
 	            pushj.setChannel(nombretablausuario);
 	       	    pushj.setData(data);
 	       	    pushj.sendInBackground();	 
				
        	 } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
         	
		}
		
		if(objects.size()>=15 && objects.size()<24){
		ParsePush push = new ParsePush();
        push.setChannel(nombretablausuario);
   	    push.setMessage("Puntaje Mes: +15 PUNTOS (R07D Intermedio)");
   	    push.sendInBackground();
   	    installation.put("mesaniochk",m+Integer.toString(aniocal));
  	    installation.saveInBackground();	
  	    
  	    
  	  try {
 		 JSONObject data = new JSONObject("{\"action\": \"com.xoaquin.r07d.NIVEL\",\"mensaje\": \"Usted ha hecho un buen trabajo, siga avanzando! Bendiciones! (R07D Nivel Intermedio) +15 PUNTOS \",\"puntos\": \"15\"    }");
			
 		ParsePush pushj = new ParsePush();
            pushj.setChannel(nombretablausuario);
       	    pushj.setData(data);
       	    pushj.sendInBackground();	 
			
 	 } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		}
		
		if(objects.size()>=24 && objects.size()<=diafm){
			ParsePush push = new ParsePush();
            push.setChannel(nombretablausuario);
       	    push.setMessage("Puntaje Mes: +25 PUNTOS (R07D Avanzado)");
       	    push.sendInBackground();
            installation.put("mesaniochk",m+Integer.toString(aniocal));
         	installation.saveInBackground();	
         	
         	
         	
         	try {
	        		 JSONObject data = new JSONObject("{\"action\": \"com.xoaquin.r07d.NIVEL\",\"mensaje\": \"Usted ha hecho un excelente trabajo, siga avanzando! Bendiciones! (R07D Nivel Avanzado) +25 PUNTOS \",\"puntos\": \"25\"    }");
					
	        		ParsePush pushj = new ParsePush();
	 	            pushj.setChannel(nombretablausuario);
	 	       	    pushj.setData(data);
	 	       	    pushj.sendInBackground();	 
					
	        	 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
         	
         	
			}
   	 
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	       
		
}
	
	
	
//onresume() de actividad reinflate
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    ProgressBar pb=(ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);

	  //inflando gridview 
        final DisplayMetrics metrics = new DisplayMetrics();  //contruyendo el adaptador 
     	getWindowManager().getDefaultDisplay().getMetrics(metrics);
     	
     	MonthAdapter mgva= new MonthAdapter(this,mescal,aniocal,metrics ); 
		gv.setAdapter(mgva);
		
	     }
	

//onclick para boton reportes
	
public void onclickgenrepo(View v) { //boton inicio actividad MENU de generacion reportes
		
		
	 Intent i = new Intent(getApplicationContext(), ReporteActivity.class);
 	 i.putExtra("correog",nombretablausuario);//pasando la variable correo a la siguiente actividad
	 
 	 startActivity(new Intent(i));  //llamando la actividad de registro acorde a fecha seleccionada
	}




	
	//felcha derecha
	
public void clickfleder(View v) { //boton flecha derecha calendario
		
	if(mescal==11){
		aniocal++;
		tvanio.setText(String.valueOf(aniocal));
	}
		
	if(mescal<11){
	mescal++;}else{mescal=0;}
	
	String nmes= nombremes(mescal);	
	tvmes.setText(nmes);
	
	final DisplayMetrics metrics = new DisplayMetrics();  //contruyendo el adaptador 
 	getWindowManager().getDefaultDisplay().getMetrics(metrics);
 	
 	MonthAdapter mgva= new MonthAdapter(this,mescal,aniocal,metrics ); 
	gv.setAdapter(mgva);
	
	}
	
	
//flecha izquierda

public void clickfleizq(View v) { //boton flecha derecha calendario
	
	if(mescal==0){
		aniocal--;
		tvanio.setText(String.valueOf(aniocal));
	}
		
	if(mescal>0){
	mescal--;}else{mescal=11;}
	
	String nmes= nombremes(mescal);	
	tvmes.setText(nmes);
	
	final DisplayMetrics metrics = new DisplayMetrics();  //contruyendo el adaptador 
 	getWindowManager().getDefaultDisplay().getMetrics(metrics);
 	
 	MonthAdapter mgva= new MonthAdapter(this,mescal,aniocal,metrics ); 
	gv.setAdapter(mgva);
	
	
	
	}
	

	//funcion para obtener nombre del mes

public String nombremes(int nummes){
		
		String nommes="";
		
		if(nummes==0){nommes="Enero";}
		if(nummes==1){nommes="Febrero";}
		if(nummes==2){nommes="Marzo";}
		if(nummes==3){nommes="Abril";}
		if(nummes==4){nommes="Mayo";}
		if(nummes==5){nommes="Junio";}
		if(nummes==6){nommes="Julio";}
		if(nummes==7){nommes="Agosto";}
		if(nummes==8){nommes="Septiembre";}
		if(nummes==9){nommes="Octubre";}
		if(nummes==10){nommes="Noviembre";}
		if(nummes==11){nommes="Diciembre";}
	
		return nommes;
	}


//funcion obtener nombre de dia

public String nomdia(int dayofweek){
	
	String nomdia="";
	
	 if(dayofweek==1){nomdia="Domingo";}
	 if(dayofweek==2){nomdia="Lunes";}
	 if(dayofweek==3){nomdia="Martes";}
	 if(dayofweek==4){nomdia="Miercoles";}
	 if(dayofweek==5){nomdia="Jueves";}
	 if(dayofweek==6){nomdia="Viernes";}
	 if(dayofweek==7){nomdia="Sabado";}
	
	
	
	return nomdia;
}
	
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

	

//inflado del menu
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.calendario_activity2, menu);
		
		return true;
	}
	
	
	
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
	    	ProgressBar pb=(ProgressBar) findViewById(R.id.progressBar1);
	    	pb.setVisibility(View.VISIBLE);
	    	 Intent in = new Intent(getApplicationContext(), MuroActivity.class);
	    	 startActivity(new Intent(in));  
			return true;
		 	
			
	    case R.id.shr:
	    	Intent intent = new Intent(Intent.ACTION_SEND);
	    	intent.setType("text/plain");
	    	intent.putExtra(Intent.EXTRA_TEXT, "R07 On The Go! Haz tu R07 en cualquier parte! Descarga la aplicación R07D en: https://play.google.com/store/apps/details?id=com.xoaquin.r07d");
	    	startActivity(Intent.createChooser(intent, "Share with"));
			return true;
		 	
		
	    
	    default:
	    return super.onOptionsItemSelected(item);
	}
		
		
		
	}

	
	

}
