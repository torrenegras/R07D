package com.xoaquin.r07d;


import java.util.Calendar;
import java.util.Date;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


//NECESARIO INVESTIGACION Y ESTUDIO PARA MEJORA DEL TEMA CALENDARIO....  V2.0.


public class CalendarioActivity extends Activity {
	
	public static String fecha,nombredia,dia,mes,anio;  //declarando variables globales 
	private CalendarPickerView calendar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendario);
			
	
		//INICIALIZANDO CALENDARIO DE LIBRERIA EXTERNA TIMESQUARE  *****
		final Calendar nextYear = Calendar.getInstance();
	    nextYear.add(Calendar.MONTH,1 );

	    final Calendar lastYear = Calendar.getInstance();
	    lastYear.add(Calendar.MONTH, -2);

	    calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
	    calendar.init(lastYear.getTime(), nextYear.getTime()).inMode(SelectionMode.SINGLE).withSelectedDate(new Date());
	
	    LinearLayout l1 = (LinearLayout) findViewById(R.id.LinearLayout01);
		l1.setBackgroundColor(Color.rgb(255, 226, 216));
		
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	
	    
		Button b1=(Button) findViewById(R.id.button1); 
		Button b2=(Button) findViewById(R.id.button2);
		b1.setTypeface(kepf);
		b2.setTypeface(kepf);
		
		/* ANTIGUO CODIO CON CALENDARVIEW   SE DEJA DE EJEMPLO, VARIABLES ETC...
		
		CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView1); 
		calendarView.setShowWeekNumber(false);
		
       
		
      	calendarView.setOnDateChangeListener(new OnDateChangeListener() { 
            
        	@Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                    int dayOfMonth) {
                 
        		//pasando la fecha, nombre del dia de seleccion de usuario a actividia de registro de datos.
        		month=month+1;//offset inexplicable...
        		 //String dia,mes,anio;
            	 dia=Integer.toString(dayOfMonth);
            	 mes=Integer.toString(month);
            	 anio=Integer.toString(year);
            	 fecha=dia+"-"+"0"+mes+"-"+anio;
            	
            	 
            	//obteniendo nombre del dia segun fecha
            	 SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
         		Date date = null;
 				try {
 					date = inFormat.parse(fecha);
 				} catch (ParseException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
         		SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
         		nombredia= outFormat.format(date);
            	 
         		//traduciendo el tema
       
            	 if(nombredia.equals("Monday")){nombredia="Lunes";}
            	 if(nombredia.equals("Tuesday")){nombredia="Martes";}
            	 if(nombredia.equals("Wednesday")){nombredia="Miercoles";}
            	 if(nombredia.equals("Thursday")){nombredia="Jueves";}
            	 if(nombredia.equals("Friday")){nombredia="Viernes";}
            	 if(nombredia.equals("Saturday")){nombredia="Sabado";}
            	 if(nombredia.equals("Sunday")){nombredia="Domingo";}
            	

                 startActivity(new Intent(CalendarioActivity.this, RecdiaActivity.class));  //llamando la actividad de registro
                 
                
                 
            }
        });
		
*/
	
	
}
	
	
	

	public void onclicksfecha(View v){
		
		
		//Obteniendo Fecha y Nombre de Dia a partir de seleccion en calendario
		
		CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
		//String f=calendar.getSelectedDate().toString();
				
		Date date=calendar.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day= cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		month=month+1;
		int year= cal.get(Calendar.YEAR);
		int dayofweek= cal.get(Calendar.DAY_OF_WEEK);
		
		 dia=Integer.toString(day);
    	 mes=Integer.toString(month);
    	 anio=Integer.toString(year);
    	 
    	 if(month<10){
    		 mes="0"+mes;
    	 }
    	 
    	 if(day<10){
    		 dia="0"+dia;
    	 }
		
    	 fecha=dia+"-"+mes+"-"+anio;
    	 
    	 if(dayofweek==1){nombredia="Domingo";}
    	 if(dayofweek==2){nombredia="Lunes";}
    	 if(dayofweek==3){nombredia="Martes";}
    	 if(dayofweek==4){nombredia="Miercoles";}
    	 if(dayofweek==5){nombredia="Jueves";}
    	 if(dayofweek==6){nombredia="Viernes";}
    	 if(dayofweek==7){nombredia="Sabado";}
    	 
    	 
    	 
    	 startActivity(new Intent(CalendarioActivity.this, RecdiaActivity.class));  //llamando la actividad de registro acorde a fecha seleccionada
    	 
		
		
	}
	
	
	public void onclickgenrepo(View v) { //boton inicio actividad MENU de generacion reportes
		
		
		startActivity(new Intent(CalendarioActivity.this, ReporteActivity.class));
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendario, menu);
		
		
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
	
	
	
	

