package com.xoaquin.r07d;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarioActivity2 extends Activity {

	
	
	public int diacal,mescal,aniocal,diatmp;   //falta el Static???  ojo!!  
	public static String fecha,nombredia,dia,mes,anio;
	
	
	private TextView tvmes,tvanio,diatv; //variables globales dentro de la actividad 
    
	private GridView gv;
    
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendario_activity2);
		setTitle("CALENDARIO");
		
		//comandos para notificaciones push
		PushService.setDefaultPushCallback(this, CalendarioActivity2.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseAnalytics.trackAppOpened(getIntent());
		
		
		tvmes = (TextView) findViewById(R.id.textView1);
		tvanio = (TextView) findViewById(R.id.textView2);
		
		gv= (GridView) findViewById(R.id.gridView1);
		
	
		 Calendar now = Calendar.getInstance(); //calendario, trayendo fecha de hoy
         
         diacal = now.get(Calendar.DAY_OF_MONTH);
         mescal=now.get(Calendar.MONTH);
         aniocal=now.get(Calendar.YEAR);

        final DisplayMetrics metrics = new DisplayMetrics();  //contruyendo el adaptador 
     	getWindowManager().getDefaultDisplay().getMetrics(metrics);
     	
     	MonthAdapter mgva= new MonthAdapter(this,mescal,aniocal,metrics ); 
		gv.setAdapter(mgva);
		
		String nmes= nombremes(mescal);	
		tvmes.setText(nmes);
		tvanio.setText(String.valueOf(aniocal));
	
		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   //Toast.makeText(getApplicationContext(),
				//((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			
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
		    	 
		    	 if(dayofweek==1){nombredia="Domingo";}
		    	 if(dayofweek==2){nombredia="Lunes";}
		    	 if(dayofweek==3){nombredia="Martes";}
		    	 if(dayofweek==4){nombredia="Miercoles";}
		    	 if(dayofweek==5){nombredia="Jueves";}
		    	 if(dayofweek==6){nombredia="Viernes";}
		    	 if(dayofweek==7){nombredia="Sabado";}
		    	 
		    	 
		    	 
		    	 startActivity(new Intent(CalendarioActivity2.this, RecdiaActivity.class));  //llamando la actividad de registro acorde a fecha seleccionada
		    	 
				
			
			
			
			
			
			}
		});
	
		
	
	
	
	}

	
	
public void onclickgenrepo(View v) { //boton inicio actividad MENU de generacion reportes
		
		
		startActivity(new Intent(CalendarioActivity2.this, ReporteActivity.class));
	}
	
	
	
	
	
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
	
public void onclickFB(View view) {
	Intent intent = new Intent(Intent.ACTION_SEND);
	intent.setType("text/plain");
	intent.putExtra(Intent.EXTRA_TEXT, "R07 On The Go! Haz tu R07 en cualquier parte! Descarga la aplicación R07D en: https://play.google.com/store/apps/details?id=com.xoaquin.r07d");
	startActivity(Intent.createChooser(intent, "Share with"));
}      

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
		   
	    
	    
	    
	    default:
	    return super.onOptionsItemSelected(item);
	}
		
		
		
	}

	
	

}
