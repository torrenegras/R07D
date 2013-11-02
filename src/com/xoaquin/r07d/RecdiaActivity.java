package com.xoaquin.r07d;

import java.util.Calendar;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



//Actividad principal de registro diario

public class RecdiaActivity extends Activity {

public static Button b1,b2,b3;  //declarando variables globales para los 2 botones de las horas
public static String shorafin="SD",shoraini="SD",objectid; //declarando variables globales para text en botones de horas y objectid de los queries

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recdia);
	
		//inicializando variables
		b1=(Button) findViewById(R.id.button1); 
		b2=(Button) findViewById(R.id.button2);
		b3=(Button) findViewById(R.id.button3);
		
		
		CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7;
		TextView fechatv = (TextView) findViewById(R.id.textView9);
		TextView diatv = (TextView) findViewById(R.id.textView8);
		
		
		//cuadrando fonts y themes  //Luego Aprendí que todo se puede hacer en layout, excepto tipo de letra externa CUSTOM Typeface
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	TextView t1=(TextView) findViewById(R.id.textView1);
		t1.setTypeface(kepf);
		t1.setTextColor(Color.rgb(141, 102, 95));
		fechatv.setTypeface(kepf);
		fechatv.setTextColor(Color.rgb(141, 102, 95));
		diatv.setTypeface(kepf);
		diatv.setTextColor(Color.rgb(141, 102, 95));
		TextView t4=(TextView) findViewById(R.id.textView4);
		t4.setTypeface(kepf);
		t4.setTextColor(Color.rgb(141, 102, 95));
		EditText et1=(EditText) findViewById(R.id.editText1);
		et1.setTypeface(kepf);
		et1.setTextColor(Color.rgb(141, 102, 95));
		et1.setHintTextColor(Color.rgb(141, 102, 95));
		EditText et2=(EditText) findViewById(R.id.editText2);
		et2.setTypeface(kepf);
		et2.setTextColor(Color.rgb(141, 102, 95));
		b1.setTypeface(kepf);
		//b1.setTextColor(Color.rgb(141, 102, 95));
		b2.setTypeface(kepf);
		//b2.setTextColor(Color.rgb(141, 102, 95));
		b3.setTypeface(kepf);
		//b3.setTextColor(Color.rgb(141, 102, 95));
		EditText et3=(EditText) findViewById(R.id.editText3);
		et3.setTypeface(kepf);
		et3.setTextColor(Color.rgb(141, 102, 95));
		EditText et4=(EditText) findViewById(R.id.editText4);
		et4.setTypeface(kepf);
		et4.setTextColor(Color.rgb(141, 102, 95));
		EditText et5=(EditText) findViewById(R.id.editText5);
		et5.setTypeface(kepf);
		et5.setTextColor(Color.rgb(141, 102, 95));
		TextView t5=(TextView) findViewById(R.id.textView5);
		t5.setTypeface(kepf);
		t5.setTextColor(Color.rgb(141, 102, 95));
		TextView t6=(TextView) findViewById(R.id.textView6);
		t6.setTypeface(kepf);
		t6.setTextColor(Color.rgb(141, 102, 95));
		TextView t7=(TextView) findViewById(R.id.textView7);
		t7.setTypeface(kepf);
		t7.setTextColor(Color.rgb(141, 102, 95));
		cb1 = (CheckBox) findViewById(R.id.checkBox1); 
		cb1.setTypeface(kepf);
		cb1.setTextColor(Color.rgb(141, 102, 95));
		cb2 = (CheckBox) findViewById(R.id.checkBox2); 
		cb2.setTypeface(kepf);
		cb2.setTextColor(Color.rgb(141, 102, 95));
		cb3 = (CheckBox) findViewById(R.id.checkBox3); 
		cb3.setTypeface(kepf);
		cb3.setTextColor(Color.rgb(141, 102, 95));
		cb4 = (CheckBox) findViewById(R.id.checkBox4); 
		cb4.setTypeface(kepf);
		cb4.setTextColor(Color.rgb(141, 102, 95));
		cb5 = (CheckBox) findViewById(R.id.checkBox5); 
		cb5.setTypeface(kepf);
		cb5.setTextColor(Color.rgb(141, 102, 95));
		
		
		LinearLayout l1 = (LinearLayout) findViewById(R.id.LinearLayout01); //background del linear layout tambien se puede hacer en layout
		l1.setBackgroundColor(Color.rgb(255, 226, 216));
		
		
		//Declarando fecha y dia traidos de Calendario
		String fecha= CalendarioActivity2.fecha;
		String nomdia= CalendarioActivity2.nombredia;
		
		//Poniendo fecha y dia en textviews onset
		fechatv.setText(fecha);
		diatv.setText(nomdia);
		
		//Habilitando checkbox de oracion solo martes y jueves
		cb6 = (CheckBox) findViewById(R.id.checkBox6); 
		cb7 = (CheckBox) findViewById(R.id.checkBox7); 
		cb6.setEnabled(false);
		cb7.setEnabled(false);
		cb6.setTypeface(kepf);
		cb6.setTextColor(Color.rgb(141, 102, 95));
		cb7.setTypeface(kepf);
		cb7.setTextColor(Color.rgb(141, 102, 95));
		
		if(nomdia==null){}else{
		
		if(nomdia.equals("Martes")||nomdia.equals("Jueves")){ //habilitando checkbox de oracion 
		cb6.setEnabled(true);
		cb7.setEnabled(true);
		}
	
		}
		
		
		
		Boolean b;
	     b=isNetworkAvailable();  //true si hay internet,  false si no hay.
	    
	    if(!b){
	    	Toast.makeText(RecdiaActivity.this, "Sin Conexion...", Toast.LENGTH_LONG).show(); 
			 
	    	 int secondsDelayed = 2;
		        new Handler().postDelayed(new Runnable() {
		                public void run() {
		                        
		                	 startActivity(new Intent(RecdiaActivity.this, CalendarioActivity2.class));
		                        finish();
		                }
		        }, secondsDelayed * 1000);
	    	
	    }else{
	    
	    
		
		
		
		
		//Buscando registro en FECHA para llenar formulario si existe  //DOBLE QUERY SUJETO A ESTUDIO Y OPTIMIZACION
		
		//Query para buscar object ID segun fecha
		String nombretablausuario=MainActivity.correoglobal;
		nombretablausuario=nombretablausuario.replaceAll("\\.", "");
		nombretablausuario=nombretablausuario.replaceAll("@", "");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario);
        query.whereEqualTo("fechadbp", fecha);
        query.findInBackground(new FindCallback<ParseObject>() {
      	
        	//@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
			//si existe un registro con esa fecha->
        	if(objects.size()>0){
        		
        	    objectid = objects.get(0).getObjectId(); //Se obtiene el ID de ese registro
        	         //objects.get(0).getString(key)	****OJO CLAVE DE OPTIMIZACION PARA EVITAR DOBLE QUERY
        	    
        	    String nombretablausuario=MainActivity.correoglobal;
        		nombretablausuario=nombretablausuario.replaceAll("\\.", "");
        		nombretablausuario=nombretablausuario.replaceAll("@", "");
        	    
        		//Query especifico con ese ID
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery(nombretablausuario);
                query2.getInBackground(objectid, new GetCallback<ParseObject>() {
                  public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                                          	
                    	//trayendo los edittexts	
                        final EditText e1=(EditText) findViewById(R.id.editText1);
                    	final EditText e2=(EditText) findViewById(R.id.editText2);
                    	final EditText e3=(EditText) findViewById(R.id.editText3);
                    	final EditText e4=(EditText) findViewById(R.id.editText4);
                    	final EditText e5=(EditText) findViewById(R.id.editText5);
                    	//trayendo checkboxes
                    	CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1); 
        	      		CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2); 
        	      		CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3); 
        	      		CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4); 
        	      		CheckBox cb5 = (CheckBox) findViewById(R.id.checkBox5); 
        	      		CheckBox cb6 = (CheckBox) findViewById(R.id.checkBox6); 
        	    		CheckBox cb7 = (CheckBox) findViewById(R.id.checkBox7);
              			
              			//trayendo las variables del record del dia en PARSE para edittexts
        		    	String horainicio=object.getString("horaidbp");
        		    	String horafin=object.getString("horafdbp");
        		    	String lecturab=object.getString("lbdbp");
        		    	String habloD=object.getString("qmhDdbp");
        		    	String acciondg=object.getString("adgdbp");
        		    	String nuevos=object.getString("ldndbp");
        		    	String peti=object.getString("peidbp");
        		    	
        		    	//trayendo las variables del record del dia en PARSE para checkboxes
        		    	String oracionp=object.getString("aopdbp");
        		    	String oracionpi=object.getString("aopidbp");
        		    	String oracionpl=object.getString("opldbp");
        		    	String ocoord=object.getString("coodbp");
        		    	String mgc=object.getString("mgcdbp");
        		    	String discipulos=object.getString("opddbp");
        		    	String cosecha=object.getString("oplcodbp");
        		    	
        		    	
        		    	//llenando el formulario visible en base a lo encontrado en PARSE
        		    	if(oracionp.equals("true")){Boolean oracionpb=true;	cb6.setChecked(oracionpb);}else{Boolean oracionpb=false;cb6.setChecked(oracionpb);}							    	
        		    	if(oracionpi.equals("true")){Boolean oracionpib=true;cb7.setChecked(oracionpib);}else{Boolean oracionpib=false;cb7.setChecked(oracionpib);}	
        		       	if(oracionpl.equals("true")){Boolean oracionplb=true;cb1.setChecked(oracionplb);}else{Boolean oracionplb=false;cb1.setChecked(oracionplb);}	
        		    	if(ocoord.equals("true")){Boolean ocoordb=true;cb2.setChecked(ocoordb);}else{Boolean ocoordb=false;cb2.setChecked(ocoordb);}	
        		    	if(mgc.equals("true")){Boolean mgcb=true;cb3.setChecked(mgcb);}else{Boolean mgcb=false;cb3.setChecked(mgcb);}	
        		    	if(discipulos.equals("true")){Boolean discipulosb=true;cb4.setChecked(discipulosb);}else{Boolean discipulosb=false;cb4.setChecked(discipulosb);}	
        		    	if(cosecha.equals("true")){Boolean cosechab=true;cb5.setChecked(cosechab);}else{Boolean cosechab=false;cb5.setChecked(cosechab);}	
        		    	
        		    	b1.setText(horainicio);							      
        		    	b2.setText(horafin);			
        		    	e1.setText(lecturab);
        		    	e2.setText(habloD);
        		    	e3.setText(acciondg);
        		    	e4.setText(nuevos);
        		    	e5.setText(peti);
        		    
                    	
                    } else {
                      // Error en el query del objectid
                    }
                  }
                });
        		
        	
        	
        	
        	}else{
        		//si no encuentra resultado al query de fecha no existe registro->no hace nada
         	}
       
        }	
        });	

	    }
	}
      	

	
	
	
	public void onclickguardar(View v) { //guardando datos recdia a DB PARSE  GUARDA NUEVOS O ACTUALIZA EXISTENTES
		
		final ProgressBar pb= (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.VISIBLE);
		
		b3.setEnabled(false);//desabilita boton de guardar mientras hace la operacion, evita dobles
		
		final CheckBox cb1;
		final CheckBox cb2;
		final CheckBox cb3;
		final CheckBox cb4;
		final CheckBox cb5;
		final CheckBox cb6;
		final CheckBox cb7;
		final EditText et1;
		final EditText et2;
		final EditText et3;
		final EditText et4;
		final EditText et5;
		
		//acomodando nombre para la tabla de usuario
		String nombretablausuario=MainActivity.correoglobal;
		String fecha= CalendarioActivity2.fecha;
		nombretablausuario=nombretablausuario.replaceAll("\\.", "");
		nombretablausuario=nombretablausuario.replaceAll("@", "");
				
		//trayendo los edittexts
		et1= (EditText) findViewById(R.id.editText1);
		et2= (EditText) findViewById(R.id.editText2);
		et3= (EditText) findViewById(R.id.editText3);
		et4= (EditText) findViewById(R.id.editText4);
		et5= (EditText) findViewById(R.id.editText5);
		
		//trayendo los checkbox del layout
		cb1 = (CheckBox) findViewById(R.id.checkBox1); 
		cb2 = (CheckBox) findViewById(R.id.checkBox2); 
		cb3 = (CheckBox) findViewById(R.id.checkBox3); 
		cb4 = (CheckBox) findViewById(R.id.checkBox4); 
		cb5 = (CheckBox) findViewById(R.id.checkBox5); 
		cb6 = (CheckBox) findViewById(R.id.checkBox6); 
		cb7 = (CheckBox) findViewById(R.id.checkBox7); 
		
		
		//haciendo query segun fecha actual dada
		ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario);
        query.whereEqualTo("fechadbp", fecha);
        query.findInBackground(new FindCallback<ParseObject>() {
      	
        	@Override
			public void done(List<ParseObject> objects, ParseException e) {
        	
			//si existe registro con esa fecha
        	if(objects.size()>0){
        		Toast.makeText(RecdiaActivity.this, "Actualizando...", Toast.LENGTH_LONG).show();
	        	 
        		//al actualizar no existe inicializacion de shorani y shorafin, por eso se hace aqui
        		shoraini=b1.getText().toString();
            	shorafin=b2.getText().toString();
            	Log.e("ini2",String.valueOf(shoraini.length()));
            	Log.e("fin2",String.valueOf(shorafin.length()));
            	
        		objectid = objects.get(0).getObjectId(); //obtiene objectid del registro con esa fecha
        	
        	    String nombretablausuario=MainActivity.correoglobal;
        		nombretablausuario=nombretablausuario.replaceAll("\\.", "");
        		nombretablausuario=nombretablausuario.replaceAll("@", "");
        	    
        		//Query segun el objectID correspondiente para actualizar el record de esa fecha
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery(nombretablausuario);
                query2.getInBackground(objectid, new GetCallback<ParseObject>() {
                  public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                  
        	    		object.put("fechadbp", CalendarioActivity2.fecha); 
        		        object.put("diadbp", CalendarioActivity2.dia);
        		        object.put("mesdbp", CalendarioActivity2.mes);
        		        object.put("aniodbp", CalendarioActivity2.anio);
        		        object.put("horaidbp", shoraini); 
        		        object.put("horafdbp", shorafin); 
        		        object.put("lbdbp", et1.getText().toString());
        		        object.put("qmhDdbp", et2.getText().toString()); 
        		        object.put("adgdbp", et3.getText().toString());
        		        object.put("ldndbp", et4.getText().toString());
        		        object.put("peidbp", et5.getText().toString()); 
        		        object.put("opldbp", String.valueOf(cb1.isChecked())); 
        		        object.put("coodbp", String.valueOf(cb2.isChecked()));
        		        object.put("mgcdbp", String.valueOf(cb3.isChecked())); 
        		        object.put("opddbp", String.valueOf(cb4.isChecked())); 
        		        object.put("oplcodbp", String.valueOf(cb5.isChecked()));
        		        object.put("aopdbp", String.valueOf(cb6.isChecked())); 
        		        object.put("aopidbp", String.valueOf(cb7.isChecked())); 
        		        
        		        
        		        
        		        
        	    		//guarda el objeto y activa el boton cuando finaliza correctamente
        		        object.saveInBackground(new SaveCallback() {
        		        	   public void done(ParseException e) {
        		        	     if (e == null) {
        		        	    	 b3.setEnabled(true);//success
        		        	    	 pb.setVisibility(View.INVISIBLE);
        		        	     } else {
        		        	       //fail
        		        	     }
        		        	   }
        		        	 });

        		        
        		        
        		        
        		  
                    } else {
                      // Si algo sale mal con query por objectid
                    }
                  
                  }
                });
        		
        
        	
        	}else{ //se crea un nuevo record
        		
        		Toast.makeText(RecdiaActivity.this, "Guardando...", Toast.LENGTH_LONG).show();
        		
        		
            	
    			String nombretablausuario=MainActivity.correoglobal;
    			nombretablausuario=nombretablausuario.replaceAll("\\.", "");
    			nombretablausuario=nombretablausuario.replaceAll("@", "");
    			
    			ParseObject TablaUsr = new ParseObject(nombretablausuario);//se crea un nuevo elemento para introducir nuevo record en PARSE
    	        
    			
    			TablaUsr.put("fechadbp", CalendarioActivity2.fecha); 
    	        TablaUsr.put("diadbp", CalendarioActivity2.dia);
    	        TablaUsr.put("mesdbp", CalendarioActivity2.mes);
    	        TablaUsr.put("aniodbp", CalendarioActivity2.anio);
    	        TablaUsr.put("horaidbp", shoraini); 
    	        TablaUsr.put("horafdbp", shorafin); 
    	        TablaUsr.put("lbdbp", et1.getText().toString());
    	        TablaUsr.put("qmhDdbp", et2.getText().toString()); 
    	        TablaUsr.put("adgdbp", et3.getText().toString());
    	        TablaUsr.put("ldndbp", et4.getText().toString());
    	        TablaUsr.put("peidbp", et5.getText().toString()); 
    	        TablaUsr.put("opldbp", String.valueOf(cb1.isChecked())); 
    	        TablaUsr.put("coodbp", String.valueOf(cb2.isChecked()));
    	        TablaUsr.put("mgcdbp", String.valueOf(cb3.isChecked())); 
    	        TablaUsr.put("opddbp", String.valueOf(cb4.isChecked())); 
    	        TablaUsr.put("oplcodbp", String.valueOf(cb5.isChecked()));
    	        TablaUsr.put("aopdbp", String.valueOf(cb6.isChecked())); 
    	        TablaUsr.put("aopidbp", String.valueOf(cb7.isChecked())); 
    	        
    	        
    	        
    	    	//guarda el objeto y activa el boton cuando finaliza correctamente
    	        TablaUsr.saveInBackground(new SaveCallback() {
		        	   public void done(ParseException e) {
		        	     if (e == null) {
		        	    	 b3.setEnabled(true);//success
		        	    	 pb.setVisibility(View.INVISIBLE);
		        	     } else {
		        	       //fail
		        	     }
		        	   }
		        	 });
    	 
        	}
        
       
          }	//done
       
      	});	//query externa 1
	
 
	}//onclickguardar
	
	
        
        
        
	
	
	
	public void onclickhorainiciob(View v) {//seleccionando hora inicio
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	    
	}

	
//fragmento para seleccionador de HORA de inicio
public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current time as the default values for the picker
final Calendar c = Calendar.getInstance();
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);

// Create a new instance of TimePickerDialog and return it
return new TimePickerDialog(getActivity(), this, hour, minute,
DateFormat.is24HourFormat(getActivity()));
}

public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

	
	//Acomodando formato AMPM 
	String ampm="AM",sminute,shour;
	
	if(hourOfDay>=12){
		ampm="PM";
		hourOfDay=hourOfDay-12;
	
	}
	
	sminute=String.valueOf(minute);
	shour= String.valueOf(hourOfDay);
	
	if (minute<10){
		sminute="0"+String.valueOf(minute);
	}
	if (hourOfDay<10){
		shour="0"+String.valueOf(hourOfDay);
	}
	
	if(hourOfDay==0){
		shour="12";
	}
	
	//Cambiando Texto Boton con Hora Inicio
	shoraini=shour+":"+sminute+" "+ampm;
	b1.setText(shoraini);
	Log.e("ini",String.valueOf(shoraini.length()));
	
}
	}



public void onclickhorafinb(View v) { //seleccionando hora fin
    DialogFragment newFragment = new TimePickerFragment2();
    newFragment.show(getFragmentManager(), "timePicker");
}

	
	//fragmento para seleccionador de HORA de final
	public static class TimePickerFragment2 extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current time as the default values for the picker
final Calendar c = Calendar.getInstance();
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);

// Create a new instance of TimePickerDialog and return it
return new TimePickerDialog(getActivity(), this, hour, minute,
DateFormat.is24HourFormat(getActivity()));
}

public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

	// Acomodando formato AMPM
	
String ampm="AM",sminute,shour;
	
	if(hourOfDay>=12){
		ampm="PM";
		hourOfDay=hourOfDay-12;
	
	}
	
	sminute=String.valueOf(minute);
	shour= String.valueOf(hourOfDay);
	
	if (minute<10){
		sminute="0"+String.valueOf(minute);
	}
	if (hourOfDay<10){
		shour="0"+String.valueOf(hourOfDay);
	}
	
	if(hourOfDay==0){
		shour="12";
	}
	
	//texto boton con hora fin
	shorafin=shour+":"+sminute+" "+ampm;
	b2.setText(shorafin);
	Log.e("fin",String.valueOf(shorafin.length()));
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
		getMenuInflater().inflate(R.menu.recdia, menu);
		return true;
	}

}
