package com.xoaquin.r07d;

import java.util.Calendar;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
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
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

//Actividad principal de registro diario

public class RecdiaActivity extends Activity {

public static Button b1,b2,b3,b5;  //declarando variables globales para los 2 botones de las horas
public static String shorafin="SD",shoraini="SD",objectid; //declarando variables globales para text en botones de horas y objectid de los queries

private String ntu="",fca="",ndca="",dca="",mca="",aca="";

private CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8;

private TextView t5,t6,t7;

private EditText et3,et4,et5;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recdia);
	
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    ntu = extras.getString("correog");
		    fca = extras.getString("fca");
		    ndca = extras.getString("ndca");
		    
		    dca = extras.getString("dca");
		    mca = extras.getString("mca");
		    aca = extras.getString("aca");
		    
		}

		//declarando e inicializando variables
		b1=(Button) findViewById(R.id.button1); 
		b2=(Button) findViewById(R.id.button2);
		b3=(Button) findViewById(R.id.button3);
		b5=(Button) findViewById(R.id.button5);
		
		
		TextView fechatv = (TextView) findViewById(R.id.textView9);
		TextView diatv = (TextView) findViewById(R.id.textView8);
		TextView t1=(TextView) findViewById(R.id.textView1);
		TextView t4=(TextView) findViewById(R.id.textView4);
		EditText et1=(EditText) findViewById(R.id.editText1);
		EditText et2=(EditText) findViewById(R.id.editText2);
		et3=(EditText) findViewById(R.id.editText3);
		et4=(EditText) findViewById(R.id.editText4);
		et5=(EditText) findViewById(R.id.editText5);
		t5=(TextView) findViewById(R.id.textView5);
		t6=(TextView) findViewById(R.id.textView6);
		t7=(TextView) findViewById(R.id.textView7);
		cb1 = (CheckBox) findViewById(R.id.checkBox1);
		cb2 = (CheckBox) findViewById(R.id.checkBox2); 
		cb3 = (CheckBox) findViewById(R.id.checkBox3); 
		cb4 = (CheckBox) findViewById(R.id.checkBox4); 
		cb6 = (CheckBox) findViewById(R.id.checkBox6); 
		cb7 = (CheckBox) findViewById(R.id.checkBox7); 
		cb5 = (CheckBox) findViewById(R.id.checkBox5); 
		cb8 = (CheckBox) findViewById(R.id.checkBox8); 
		
		ToggleButton tb= (ToggleButton) findViewById(R.id.toggleButton1);
		
		//cuadrando fonts y themes  //Luego Aprendí que todo se puede hacer en layout, excepto tipo de letra externa CUSTOM Typeface
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	
		t1.setTypeface(kepf);fechatv.setTypeface(kepf);diatv.setTypeface(kepf);t4.setTypeface(kepf);et1.setTypeface(kepf);
		et2.setTypeface(kepf);b1.setTypeface(kepf);	b2.setTypeface(kepf);b3.setTypeface(kepf);	b5.setTypeface(kepf);
		et3.setTypeface(kepf);	et4.setTypeface(kepf);	et5.setTypeface(kepf);	t5.setTypeface(kepf);	t6.setTypeface(kepf);
		t7.setTypeface(kepf);	cb1.setTypeface(kepf);	cb2.setTypeface(kepf);	cb3.setTypeface(kepf);	cb4.setTypeface(kepf);
		cb5.setTypeface(kepf); cb8.setTypeface(kepf);     cb6.setTypeface(kepf);cb7.setTypeface(kepf);tb.setTypeface(kepf);	
		
		//Declarando fecha y dia traidos de Calendario
		String fecha= fca;
		String nomdia= ndca;
		
		//Poniendo fecha y dia en textviews onset
		fechatv.setText(fecha);
		diatv.setText(nomdia);
		
		
		//Habilitando checkbox de oracion solo martes y jueves
		
	
				
		if(nomdia==null){}else{
		
		if(nomdia.equals("Martes")||nomdia.equals("Jueves")){ //habilitando checkbox de oracion 
		cb6.setVisibility(0);
		cb7.setVisibility(0);
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
		String nombretablausuario=ntu;
		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario);
        query.whereEqualTo("fechadbp", fecha);
        query.findInBackground(new FindCallback<ParseObject>() {
      	
        	//@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
			//si existe un registro con esa fecha->
        	if(objects.size()>0){
        		
        	    objectid = objects.get(0).getObjectId(); //Se obtiene el ID de ese registro
        	         //objects.get(0).getString(key)	****OJO CLAVE DE OPTIMIZACION PARA EVITAR DOBLE QUERY
        	    
        	    String nombretablausuario=ntu;
        		
        	    
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
		String nombretablausuario=ntu;
		String fecha= fca;   
		
				
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
            	
            	
        		objectid = objects.get(0).getObjectId(); //obtiene objectid del registro con esa fecha
        	
        	    String nombretablausuario=ntu;
        		
        	    
        		//Query segun el objectID correspondiente para actualizar el record de esa fecha
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery(nombretablausuario);
                query2.getInBackground(objectid, new GetCallback<ParseObject>() {
                  public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                  
        	    		object.put("fechadbp", fca); 
        		        object.put("diadbp", dca);
        		        object.put("mesdbp", mca);
        		        object.put("aniodbp", aca);
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
        		
        		
            	
    			String nombretablausuario=ntu;
    			
    			
    			ParseObject TablaUsr = new ParseObject(nombretablausuario);//se crea un nuevo elemento para introducir nuevo record en PARSE
    	        
    			
    			TablaUsr.put("fechadbp", fca); 
    	        TablaUsr.put("diadbp", dca);
    	        TablaUsr.put("mesdbp", mca);
    	        TablaUsr.put("aniodbp", aca);
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
	
 
       
        //para publicacion en muro
        CheckBox cb8 = (CheckBox) findViewById(R.id.checkBox8); 
        final ParseUser cu = ParseUser.getCurrentUser();
        
             
        if(cb8.isChecked()){
        	
        	//si no esta vacio 
        	if(!et3.getText().toString().isEmpty()){
        		
        		
        		ParseQuery<ParseObject> q = ParseQuery.getQuery("Muro");
        		q.whereEqualTo("correo", cu.getEmail().toString());
        		q.whereEqualTo("fecha", fca);
        		q.whereEqualTo("tipo", "(AG)");
        		q.findInBackground(new FindCallback<ParseObject>() {
        		    public void done(List<ParseObject> o, ParseException e) {
        		        if (e == null) {
        		            
        		        	//si existe actualiza
        		        	if(o.size()>0){
        		        		o.get(0).put("texto", et3.getText().toString());
        		        		o.get(0).saveInBackground();
        		        	//o crea uno nuevo
        		        	}else{
        		        		ParseObject ag = new ParseObject("Muro");
        		        		ag.put("fecha",fca);
        		        		ag.put("correo", cu.getEmail().toString());
        		            	ag.put("tipo", "(AG)");
        		            	ag.put("texto", et3.getText().toString());
        		            	ag.saveInBackground();	
        		        	}
        		        	
        		        	
        		        } else {
        		            
        		        }
        		    }
        		});
        		
           		
        	}
        	//si no esta vacio
        	if(!et5.getText().toString().isEmpty()){
        	
        	
        		ParseQuery<ParseObject> q = ParseQuery.getQuery("Muro");
        		q.whereEqualTo("correo", cu.getEmail().toString());
        		q.whereEqualTo("fecha", fca);
        		q.whereEqualTo("tipo", "(P)");
        		q.findInBackground(new FindCallback<ParseObject>() {
        		    public void done(List<ParseObject> o, ParseException e) {
        		        if (e == null) {
        		            
        		        	if(o.size()>0){
        		        		o.get(0).put("texto", et5.getText().toString());
        		        		o.get(0).saveInBackground();
        		        	}else{
        		        		ParseObject pet = new ParseObject("Muro");
        		            	pet.put("fecha",fca);
        		            	pet.put("correo", cu.getEmail().toString());
        		            	pet.put("tipo", "(P)");
        		            	pet.put("texto", et5.getText().toString());
        		               	pet.saveInBackground();	
        		        	}
        		        	
        		        	
        		        } else {
        		            
        		        }
        		    }
        		});
        		
        
        	
        	}
           	
        }
        
        
        
      
        
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
	
}


}
	
	

	
	
	public void onclickdev(View v) {
		
		Intent i = new Intent(getApplicationContext(), RecdiaActivity2.class);
    	
    	startActivity(new Intent(i));
        finish();
		
	}
	
	
	public void onclicktb(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	        
	    	t5.setVisibility(View.VISIBLE);
	    	t6.setVisibility(View.VISIBLE);
	    	t7.setVisibility(View.VISIBLE);
	    	
	    	et3.setVisibility(View.VISIBLE);
	    	et4.setVisibility(View.VISIBLE);
	    	et5.setVisibility(View.VISIBLE);
	    	
	    	cb1.setVisibility(View.VISIBLE);
	        cb2.setVisibility(View.VISIBLE);
	        cb3.setVisibility(View.VISIBLE);
	        cb4.setVisibility(View.VISIBLE);
	        cb5.setVisibility(View.VISIBLE);
	        cb8.setVisibility(View.VISIBLE);
	        view.setBackgroundResource(0x7f020002);
	        
	    } else {
	    	t5.setVisibility(View.GONE);
	    	t6.setVisibility(View.GONE);
	    	t7.setVisibility(View.GONE);
	    	
	    	et3.setVisibility(View.GONE);
	    	et4.setVisibility(View.GONE);
	    	et5.setVisibility(View.GONE);
	    	
	    	cb1.setVisibility(View.GONE);
	        cb2.setVisibility(View.GONE);
	        cb3.setVisibility(View.GONE);
	        cb4.setVisibility(View.GONE);
	        cb5.setVisibility(View.GONE);
	        cb8.setVisibility(View.GONE);
	        view.setBackgroundResource(0x7f020001);
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
