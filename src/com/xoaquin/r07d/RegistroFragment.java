
//**** FRAGMENT CORE REGISTRO ****

package com.xoaquin.r07d;

import java.util.Calendar;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;


public class RegistroFragment extends Fragment {
    
	//DECLARANDO VARS GLOBALES FRAGMENTO
	private static Button bhi,bhf,bg;
	private static String shoraini,shorafin,ntu="",fca="",ndca="",dca="",mca="",aca="";
	private static ToggleButton tb;
	private static TextView t1,t4,t5,t6,t7,t8,t9,tdrc;
	private static EditText et1,et2,et3,et4,et5;
	private static CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8;
	private static ProgressBar pb;
	private static ParseUser cu = ParseUser.getCurrentUser();
 	private Boolean b;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        
		// INFLANDO VIEW DEL FRAGMENTO
        View V= inflater.inflate(R.layout.fragment_registro, container, false);
        

        //INICIALIZANDO VARIABLES
        bhi=(Button) V.findViewById(R.id.button1);
        bhf=(Button) V.findViewById(R.id.button2);
        bg=(Button) V.findViewById(R.id.button3);
        tb=(ToggleButton) V.findViewById(R.id.toggleButton1);
        t1=(TextView) V.findViewById(R.id.textViewt);
        t4=(TextView) V.findViewById(R.id.textViewv2);
        t5=(TextView) V.findViewById(R.id.textViewv3);
        t6=(TextView) V.findViewById(R.id.textViewv4);
        t7=(TextView) V.findViewById(R.id.textViewv5);
        t8=(TextView) V.findViewById(R.id.textViewv6);//dia
        t9=(TextView) V.findViewById(R.id.textViewv7);//fecha
        et1=(EditText) V.findViewById(R.id.editText1);
        et2=(EditText) V.findViewById(R.id.editText2);
        et3=(EditText) V.findViewById(R.id.editText3);
        et4=(EditText) V.findViewById(R.id.editText4);
        et5=(EditText) V.findViewById(R.id.editText5);
        cb1=(CheckBox) V.findViewById(R.id.checkBox1);
        cb2=(CheckBox) V.findViewById(R.id.checkBox2);
        cb3=(CheckBox) V.findViewById(R.id.checkBox3);
        cb4=(CheckBox) V.findViewById(R.id.checkBox4);
        cb5=(CheckBox) V.findViewById(R.id.checkBox5);
        cb8=(CheckBox) V.findViewById(R.id.checkBox8);
        cb6=(CheckBox) V.findViewById(R.id.checkBox6);
        cb7=(CheckBox) V.findViewById(R.id.checkBox7);
        pb=(ProgressBar) V.findViewById(R.id.progressBar1);
        
        tdrc=(TextView) getActivity().findViewById(R.id.textViewv9);//textview de activity
        tdrc.setClickable(false);
        pb.setVisibility(View.VISIBLE);
        
        //TRAYENDO VARIABLES DE ACTIVIDAD 
        Bundle extras = getArguments();
		if (extras != null) {
		    ntu = extras.getString("correog");
		    fca = extras.getString("fca");
		    ndca = extras.getString("ndca");
		    
		    dca = extras.getString("dca");
		    mca = extras.getString("mca");
		    aca = extras.getString("aca");
		    
		}
        
		//PONIENDO TIPO DE LETRA A CADA WIDGET
        Typeface kepf = Typeface.createFromAsset(V.getContext().getAssets(),"Kepler-Std-Black_26074.ttf");
    	
        bhi.setTypeface(kepf);bhf.setTypeface(kepf);bg.setTypeface(kepf);tb.setTypeface(kepf);t1.setTypeface(kepf);t4.setTypeface(kepf);
        t5.setTypeface(kepf);t6.setTypeface(kepf);t7.setTypeface(kepf);t8.setTypeface(kepf);t9.setTypeface(kepf);et1.setTypeface(kepf);
        et2.setTypeface(kepf);et3.setTypeface(kepf);et4.setTypeface(kepf);et5.setTypeface(kepf);cb1.setTypeface(kepf);cb2.setTypeface(kepf);
        cb3.setTypeface(kepf);cb4.setTypeface(kepf);cb5.setTypeface(kepf);cb8.setTypeface(kepf);cb6.setTypeface(kepf);cb7.setTypeface(kepf);
               
        
        
        //TRAYENDO INFO A TODOS LOS CAMPOS EN ONCREATE()
        t8.setText(ndca);
        t9.setText(fca);
        
        String nomdia=ndca;
        if(nomdia==null){}else{
    		
    		if(nomdia.equals("Martes")||nomdia.equals("Jueves")||nomdia.equals("Tuesday")||nomdia.equals("Thursday")){ //habilitando checkbox de oracion 
    		cb6.setVisibility(View.VISIBLE);
    		cb7.setVisibility(View.VISIBLE);
    		}
    	
    	}
        
    	    //POPULANDO REGISTRO EN ONCREATE  	
            ParseQuery<ParseObject> query = ParseQuery.getQuery(ntu);
	        query.whereEqualTo("fechadbp", fca);
	    
	        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
	        query.findInBackground(new FindCallback<ParseObject>() {
	            public void done(List<ParseObject> obs, ParseException e) {
	                if (e == null) {
	                   
	                	if (obs.size()>0)
	                   {   	                   
	                	bhi.setText(obs.get(0).getString("horaidbp"));
	                	bhf.setText(obs.get(0).getString("horafdbp"));   
	                	et1.setText(obs.get(0).getString("lbdbp"));   
	                	et2.setText(obs.get(0).getString("qmhDdbp"));   
	                	et3.setText(obs.get(0).getString("adgdbp"));   
	                	et4.setText(obs.get(0).getString("ldndbp"));   
	                	et5.setText(obs.get(0).getString("peidbp"));
	                	
	                	if(et3.getText().toString().length()>0||et4.getText().toString().length()>0||et5.getText().toString().length()>0){visibles();tb.setChecked(true);}
	                	
	                	if(obs.get(0).getString("aopdbp").equals("true")){cb6.setChecked(true);}else{cb6.setChecked(false);}  
	                	if(obs.get(0).getString("aopidbp").equals("true")){cb7.setChecked(true);}else{cb7.setChecked(false);}   
	                	if (obs.get(0).getString("opldbp").equals("true")){cb1.setChecked(true);visibles();tb.setChecked(true);}else{cb1.setChecked(false);}   
	                	if (obs.get(0).getString("coodbp").equals("true")){cb2.setChecked(true);visibles();tb.setChecked(true);}else{cb2.setChecked(false);}   
	                	if (obs.get(0).getString("mgcdbp").equals("true")){cb3.setChecked(true);visibles();tb.setChecked(true);}else{cb3.setChecked(false);}   
	                	if(obs.get(0).getString("opddbp").equals("true")){ cb4.setChecked(true);visibles();tb.setChecked(true);}else{cb4.setChecked(false);}
	                	if (obs.get(0).getString("oplcodbp").equals("true")){cb5.setChecked(true);visibles();tb.setChecked(true);}else{cb5.setChecked(false);}   
	                   
	                	
	                    pb.setVisibility(View.GONE);
	                    tdrc.setClickable(true);
	                   }else{
	                	pb.setVisibility(View.GONE);
		                tdrc.setClickable(true);
	                   }
	                   
	                } else {//modo offline sin poder hacer query, sin cache
	                	Toast.makeText(getActivity(), getString(R.string.offmode)+"...", Toast.LENGTH_LONG).show(); 
	                	pb.setVisibility(View.GONE);
		                tdrc.setClickable(true);
	                	
	                }
	            }

	        });
	           
	
	        //REPLICANDO QUERIES MURO PARA OBTENER CACHE PREVIOS EN CASOS DE PUBLICACION 
	       
	        //QUERY DUMMY 1 PARA CACHE ACCION DE GRACIAS
	        ParseQuery<ParseObject> q = ParseQuery.getQuery("Muro");
    		q.whereEqualTo("correo", cu.getEmail().toString());
    		q.whereEqualTo("fecha", fca);
    		q.whereEqualTo("tipo", getString(R.string.atg));
    		q.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
    		q.findInBackground(new FindCallback<ParseObject>() {
    		    public void done(List<ParseObject> o, ParseException e) {
    		        if (e == null) {
    		         } else {
    		        
    		        }
    		    }
    		});
	        
    		//QUERY DUMMY 2 PARA CACHE PETICIONES
    		ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Muro");
    		q2.whereEqualTo("correo", cu.getEmail().toString());
    		q2.whereEqualTo("fecha", fca);
    		q2.whereEqualTo("tipo", "(P)");
    		q2.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
    		q2.findInBackground(new FindCallback<ParseObject>() {
    		    public void done(List<ParseObject> o, ParseException e) {
    		        if (e == null) {
    		        } else {
    		        
    		        }
    		    }
    		});
	        
	 
    		
	        
	  //HORA INICIO
	    bhi.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
            	 DialogFragment newFragment = new TimePickerFragment();
         	     newFragment.show(getFragmentManager(), "timePicker");
            }       
        }
        );
        
	  //HORA FIN
        bhf.setOnClickListener(new OnClickListener()  
        {
            public void onClick(View v) 
            {
            	 DialogFragment newFragment = new TimePickerFragment2();
            	  newFragment.show(getFragmentManager(), "timePicker");
            }       
        }
        );
        
      //TOGGLE 
        tb.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View view) 
            {
            	boolean on = ((ToggleButton) view).isChecked();
        	    
        	    if (on) {
        	        
        	    	visibles();
        	        
        	    } else {
        	    	t5.setVisibility(View.GONE);t6.setVisibility(View.GONE);t7.setVisibility(View.GONE);et3.setVisibility(View.GONE);
        	    	et4.setVisibility(View.GONE);et5.setVisibility(View.GONE);cb1.setVisibility(View.GONE);cb2.setVisibility(View.GONE);
        	        cb3.setVisibility(View.GONE);cb4.setVisibility(View.GONE); cb5.setVisibility(View.GONE); cb8.setVisibility(View.GONE);
        	        view.setBackgroundResource(0x7f020001);
        	    } 
            }       
        }
        );
        
        
 
     // GUARDAR
        bg.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v) 
            
            {
            	guardar(); //LLAMANDO FUNCION GUARDAR DE ABAJO
            	   	        
            }
          });
        
        
       
        return V;
}//FIN ONCREATE()
	
	
	
	public void visibles(){
		t5.setVisibility(View.VISIBLE);	t6.setVisibility(View.VISIBLE);	t7.setVisibility(View.VISIBLE);	et3.setVisibility(View.VISIBLE);
    	et4.setVisibility(View.VISIBLE);et5.setVisibility(View.VISIBLE);cb1.setVisibility(View.VISIBLE); cb2.setVisibility(View.VISIBLE);
        cb3.setVisibility(View.VISIBLE);cb4.setVisibility(View.VISIBLE);cb5.setVisibility(View.VISIBLE); cb8.setVisibility(View.VISIBLE);
        tb.setBackgroundResource(0x7f020002);
    	
    }
	
	
   
	//FUNCION GUARDAR
	public void guardar(){
	    
       	pb.setVisibility(View.VISIBLE);
		bg.setEnabled(false);//desabilita boton de guardar mientras hace la operacion, evita dobles
		
		b=isNetworkAvailable();
    	
		//GUARDAR / ACTUALIZAR DATOS EN DB PARSE
		
    	ParseQuery<ParseObject> query = ParseQuery.getQuery(ntu);
        query.whereEqualTo("fechadbp", fca);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> obs, ParseException e) {
                if (e == null) {
                   if (obs.size()>0)
                   {   	                   
                	   
                ParseObject object=obs.get(0);
               
                actualizarRecordParse(object);   
              
		        Toast.makeText(getActivity(), getString(R.string.actu)+"...", Toast.LENGTH_LONG).show();
                   
                   }else{
                  
                 crearNuevoRecordParse();   
                  	        
           	     Toast.makeText(getActivity(), getString(R.string.guard)+"...", Toast.LENGTH_LONG).show();
                     	                   
                 }
                   
                } else {//error en query, algo salio mal.. sin net, sin cache...  PRIMERA VEZ QUE SE INTENTA QUERY SOBRE ELEMENTO, POR LO QUE ES NUEVO** OPTIMIZACION??**
                	
                 crearNuevoRecordParse();   
      			        
      			 Toast.makeText(getActivity(), getString(R.string.guard)+"...", Toast.LENGTH_LONG).show();
                
                }
            }

        });
         
        
        //PUBLICACION EN MURO R07D
        if(cb8.isChecked()){
        	
        	//si no esta vacio ACCION DE GRACIAS
        	if(!et3.getText().toString().isEmpty()){
        		
        		
        		ParseQuery<ParseObject> q = ParseQuery.getQuery("Muro");
        		q.whereEqualTo("correo", cu.getEmail().toString());
        		q.whereEqualTo("fecha", fca);
        		q.whereEqualTo("tipo", getString(R.string.atg));
        		q.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        		q.findInBackground(new FindCallback<ParseObject>() {
        		    public void done(List<ParseObject> o, ParseException e) {
        		        if (e == null) {
        		            
        		        	//si existe actualiza
        		        	if(o.size()>0){
        		        		o.get(0).put("texto", et3.getText().toString());
        		        		o.get(0).saveEventually();
        		        		
        		        	//o crea uno nuevo
        		        	}else{
        		        		ParseObject ag = new ParseObject("Muro");
        		        		ag.put("fecha",fca);
        		        		ag.put("correo", cu.getEmail().toString());
        		            	ag.put("tipo", getString(R.string.atg));
        		            	ag.put("texto", et3.getText().toString());
        		            	ag.saveEventually();
        	       		        	   
             		        	}
        		        	
        		        	
        		        } else {
        		        	//falla query sin cache , sin net.. NUNCA SE HA HECHO ANTES,ENTONCES ELEMENTO NUEVO  SUJETO A OPTIMIZACION****
        		        	ParseObject ag = new ParseObject("Muro");
    		        		ag.put("fecha",fca);
    		        		ag.put("correo", cu.getEmail().toString());
    		            	ag.put("tipo", getString(R.string.atg));
    		            	ag.put("texto", et3.getText().toString());
    		            	ag.saveEventually();
        		        }
        		    }
        		});
        		
           		
        	}
        	
        	//si no esta vacio PETICIONES/INTERCESION
        	if(!et5.getText().toString().isEmpty()){
        	
        	
        		ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Muro");
        		q2.whereEqualTo("correo", cu.getEmail().toString());
        		q2.whereEqualTo("fecha", fca);
        		q2.whereEqualTo("tipo", "(P)");
        		q2.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        		q2.findInBackground(new FindCallback<ParseObject>() {
        		    public void done(List<ParseObject> o, ParseException e) {
        		        if (e == null) {
        		            
        		        	if(o.size()>0){
        		        		o.get(0).put("texto", et5.getText().toString());
        		        		o.get(0).saveEventually();
        		        	}else{
        		        		ParseObject pet = new ParseObject("Muro");
        		            	pet.put("fecha",fca);
        		            	pet.put("correo", cu.getEmail().toString());
        		            	pet.put("tipo", "(P)");
        		            	pet.put("texto", et5.getText().toString());
        		               	pet.saveEventually();
        		        	}
        		        
        		        } else {
        		        	//falla query sin cache , sin net.. NUNCA SE HA HECHO ANTES,ENTONCES ELEMENTO NUEVO  SUJETO A OPTIMIZACION****
        		        	ParseObject pet = new ParseObject("Muro");
    		            	pet.put("fecha",fca);
    		            	pet.put("correo", cu.getEmail().toString());
    		            	pet.put("tipo", "(P)");
    		            	pet.put("texto", et5.getText().toString());
    		               	pet.saveEventually();
        		        }
        		    }
        		});
        	
        	
        	}
        
        }
  

	}//FIN GUARDAR()
	
    
	
	
	//ACTUALIZAR RECORD EXISTENTE EN PARSE
	 public void actualizarRecordParse(ParseObject object){
	        
		    object.put("fechadbp", fca); 
	        object.put("diadbp", dca);
	        object.put("mesdbp", mca);
	        object.put("aniodbp", aca);
	        object.put("horaidbp", bhi.getText().toString()); 
	        object.put("horafdbp", bhf.getText().toString()); 
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
	        
	       
	      //true si hay internet,  false si no hay.
	    if(!b){
	   //guarda el objeto y activa el boton cuando finaliza correctamente estando OFFLINE
		   	   object.saveEventually();
		   	   
	    	    int secondsDelayed = 2;
		        new Handler().postDelayed(new Runnable() {
		                public void run() {
		                 bg.setEnabled(true);//success
	        	    	 pb.setVisibility(View.INVISIBLE);
		                }
		        }, secondsDelayed * 1000);
	    }else{
	        
	   //guarda el objeto y activa el boton cuando finaliza correctamente estando ONLINE
	        object.saveInBackground(new SaveCallback() {
	        	   public void done(ParseException e) {
	        	     if (e == null) {
	        	    	 bg.setEnabled(true);//success
	        	    	 pb.setVisibility(View.INVISIBLE);
	        	     } else {
	        	       //fail
	        	     }
	        	   }
	        	 });
            
	    }
	 }   
	

	 //CREAR NUEVO RECORD EN PARSE
	 public void  crearNuevoRecordParse() {
		 
            ParseObject TablaUsr = new ParseObject(ntu);//se crea un nuevo elemento para introducir nuevo record en PARSE
   			
			TablaUsr.put("fechadbp", fca); 
	        TablaUsr.put("diadbp", dca);
	        TablaUsr.put("mesdbp", mca);
	        TablaUsr.put("aniodbp", aca);
	        TablaUsr.put("horaidbp", bhi.getText().toString()); 
	        TablaUsr.put("horafdbp", bhf.getText().toString()); 
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

	        
	      //true si hay internet,  false si no hay.   
	     if(!b){
  		   //guarda el objeto y activa el boton cuando finaliza correctamente estando OFFLINE
  			   	   TablaUsr.saveEventually();
  			   	   
  		    	    int secondsDelayed = 2;
  			        new Handler().postDelayed(new Runnable() {
  			                public void run() {
  			                 bg.setEnabled(true);//success
		        	    	 pb.setVisibility(View.INVISIBLE);
  			                }
  			        }, secondsDelayed * 1000);
  		    }else{
  		        
  		   //guarda el objeto y activa el boton cuando finaliza correctamente estando ONLINE
		        TablaUsr.saveInBackground(new SaveCallback() {
		        	   public void done(ParseException e) {
		        	     if (e == null) {
		        	    	 bg.setEnabled(true);//success
		        	    	 pb.setVisibility(View.INVISIBLE);
		        	     } else {
		        	       //fail
		        	     }
		        	   }
		        	 });
                  
  		    }
		 
	 }
	 
	
	 
	 //DIALOG HORA INICIO
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
			bhi.setText(shoraini);
			
			
		}
			}
	
	
	
	//DIALOG HORA FIN
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
			bhf.setText(shorafin);
			
		}


}
	
	
	//CHECK NETWORK
	private boolean isNetworkAvailable() { 
		
			ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	
	
}
