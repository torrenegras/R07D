package com.xoaquin.r07d;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ReporteActivity extends Activity {

	String mes,anio,strFile;
int sf,sif;

static final int DATE_DIALOG_ID = 1;
private int mYear;
private int mMonth;
private int mDay;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reporte);
	
		String nombre="",nombreli="",mdl="";
		EditText etnombre,etnombreli,etmdl;
		
		//poniendo tipo de letra especial
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	TextView t1f=(TextView) findViewById(R.id.textView1);
		t1f.setTypeface(kepf);
		TextView t2f=(TextView) findViewById(R.id.textView2);
		t2f.setTypeface(kepf);
		TextView t3=(TextView) findViewById(R.id.textView3);
		t3.setTypeface(kepf);
		TextView t4=(TextView) findViewById(R.id.textView4);
		t4.setTypeface(kepf);
		TextView t5=(TextView) findViewById(R.id.textView5);
		t5.setTypeface(kepf);
		TextView t6=(TextView) findViewById(R.id.textView6);
		t6.setTypeface(kepf);
		
		EditText et1=(EditText) findViewById(R.id.editText1);
		et1.setTypeface(kepf);
		EditText et2=(EditText) findViewById(R.id.editText2);
		et2.setTypeface(kepf);
		EditText et5=(EditText) findViewById(R.id.editText5);
		et5.setTypeface(kepf);
		
		RadioButton rb1=(RadioButton) findViewById(R.id.radioButton1);
		rb1.setTypeface(kepf);
		
		RadioButton rb2=(RadioButton) findViewById(R.id.radioButton2);
		rb2.setTypeface(kepf);
		
		Button b1=(Button) findViewById(R.id.button1); 
		b1.setTypeface(kepf);
		Button b2=(Button) findViewById(R.id.button2); 
		b2.setTypeface(kepf);
		
		//trayendo valores guardados anteriormente a nivel DB local SQLite
		
		final DBAdapter2 db2 = new DBAdapter2(this);  
	     db2.open(); 
	      
	   
	      int nr=db2.getTitle(1).getCount();
	      long nri = db2.getAllTitles().getCount();
	      
	      if (nr>0){
	      nombre=db2.getTitle(nri).getString(1);
	      nombreli=db2.getTitle(nri).getString(2);
	      mdl=db2.getTitle(nri).getString(3);
	      }else{
	    	  
	      }
		
	      etnombre=(EditText) findViewById(R.id.editText1);
	       etnombreli=(EditText) findViewById (R.id.editText2);
	       etmdl=(EditText) findViewById (R.id.editText5);
	       
	       etnombre.setText(nombre);
	       etnombreli.setText(nombreli); 
	       etmdl.setText(mdl);
	      
	      
	       final Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH);
			m=m+1;
			
			TextView t1= (TextView) findViewById(R.id.textView3);//cambiando textviews segun mes y año actual
			TextView t2= (TextView) findViewById(R.id.textView4);
			
			mes=String.valueOf(m); //variable para query
			if(m<10){mes="0"+String.valueOf(m);}
			anio=String.valueOf(y); //variable para query
	
			t1.setText("MES: "+mes);
			t2.setText("AÑO: "+anio);
	
	
			final Calendar c2 = Calendar.getInstance();
			mYear = c2.get(Calendar.YEAR);
			mMonth = c2.get(Calendar.MONTH);
	       mMonth=mMonth+1;
	
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void onclickma(View v){

		showDialog(DATE_DIALOG_ID);  //llamando el picker CUSTOM
	
		
	}
	
    
	
	
	
	DatePickerDialog.OnDateSetListener mDateSetListner = new OnDateSetListener() {  //funcion para CUSTOM PICKER

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
		int dayOfMonth) {

		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		updateDate(); //ESTA FUNCION y var claves
		}
		};

		@Override
		protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		/*
		* return new DatePickerDialog(this, mDateSetListner, mYear, mMonth,
		* mDay);
		*/
		DatePickerDialog datePickerDialog = this.customDatePicker();
		return datePickerDialog;
		}
		return null;
		}


		@SuppressWarnings("deprecation")
		protected void updateDate() { //funcion para CUSTOM PICKER
		int localMonth = (mMonth + 1);
		String monthString = localMonth < 10 ? "0" + localMonth : Integer
		.toString(localMonth);
		String localYear = Integer.toString(mYear);
		
		TextView t1= (TextView) findViewById(R.id.textView3);//cambiando textviews segun seleccion de usuario
		TextView t2= (TextView) findViewById(R.id.textView4);
		
		t1.setText("MES: "+monthString); //DONDE SE PONEN EN LAYOUT
		t2.setText("AÑO: "+localYear);   //DONDE SE PONEN EN LAYOUT
		mes=monthString;
		anio=localYear;
		
		showDialog(DATE_DIALOG_ID);
		}



		private DatePickerDialog customDatePicker() {  //funcion para CUSTOM PICKER
		DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
		mYear, mMonth, mDay);
		try {

		Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
		for (Field datePickerDialogField : datePickerDialogFields) {
		if (datePickerDialogField.getName().equals("mDatePicker")) {
		datePickerDialogField.setAccessible(true);
		DatePicker datePicker = (DatePicker) datePickerDialogField
		.get(dpd);
		Field datePickerFields[] = datePickerDialogField.getType()
		.getDeclaredFields();
		for (Field datePickerField : datePickerFields) {
		if ("mDayPicker".equals(datePickerField.getName())
		|| "mDaySpinner".equals(datePickerField
		.getName())) {
		datePickerField.setAccessible(true);
		Object dayPicker = new Object();
		dayPicker = datePickerField.get(datePicker);
		((View) dayPicker).setVisibility(View.GONE);
		}
		}
		}

		}
		} catch (Exception ex) {
		}
		return dpd;
		}


	
	
	public void onclickcreandorep(View v) { //funcion de generacion de reporte mensual
		
		
	
		
		//trayendo los editexts
		EditText et1=(EditText) findViewById(R.id.editText1);
		EditText et2=(EditText) findViewById(R.id.editText2);
		EditText et3=(EditText) findViewById(R.id.editText5);
		
		
		//guardando campos en DB para nombres
		final DBAdapter2 db2 = new DBAdapter2(this);
		db2.open(); 
		db2.insertTitle(et1.getText().toString(),et2.getText().toString(),et3.getText().toString());
     
		
		
		
		//Creando el reporte en HTML
		
		
		
		 String nombretablausuario=MainActivity.correoglobal;
 		nombretablausuario=nombretablausuario.replaceAll("\\.", "");
 		nombretablausuario=nombretablausuario.replaceAll("@", "");
 		
 		
 		 Boolean b;
	     b=isNetworkAvailable();  //true si hay internet,  false si no hay.
	    
	    if(!b){
	    	Toast.makeText(ReporteActivity.this, "Sin Conexion...", Toast.LENGTH_LONG).show(); 
			 
	    	 
	    }else{
 		
	    	Toast.makeText(ReporteActivity.this, "Generando Reporte...", Toast.LENGTH_LONG).show(); 
			
 		
 		ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario); //query para buscar records de ese mes y año en orden ascendente
	        query.whereEqualTo("mesdbp", mes);
	        query.whereEqualTo("aniodbp", anio);
	        query.orderByAscending("diadbp");
	        
	        query.findInBackground(new FindCallback<ParseObject>() {
	          public void done(List<ParseObject> objects, ParseException e) {
	          	  int os=objects.size();//  es 3
	        	  Log.e("os",String.valueOf(os));
	        	
	        	int i = 0; //contadores de booleans en parse
	      		int s=0;
	      		int si=0;
	      		String cuerpocorreo = null;
	      		
	      	//trayendo los editexts
	    		EditText et1=(EditText) findViewById(R.id.editText1);
	    		EditText et2=(EditText) findViewById(R.id.editText2);
	    		EditText et3=(EditText) findViewById(R.id.editText5);
	    		
	    		//TRAYENDO LOS RADIO
	    		RadioButton b1=(RadioButton) findViewById(R.id.radioButton1);
	    		
	      	    
	    		//Strings pre-TABLA en formato HTML
	      		String fecha = "<b>"+"REPORTE: "+mes+"/"+anio+"</b>";
	    		String nombre = "<b>"+"NOMBRE: "+et1.getText().toString()+"</b>";
	    		String nombrecg = "<b>"+"NOMBRE COORDINADOR DE GRUPO CONEXION: "+et2.getText().toString()+"</b>";
	    		String mdl = "<b>"+"META DE LECTURA: "+et3.getText().toString()+"</b>";
	    		String cmdl = "<b>"+"¿CUMPLI LA META DE LECTURA?: NO"+"</b>";
	    		String sraop=" ";
	    		String sraopi=" ";
	    		String srpl="",srco="",srmdg="",srpd="",srplco="";
	    		
	    		//Comprobando Boton 
	    		if(b1.isChecked()){
	    			cmdl = "<b>"+"¿CUMPLI LA META DE LECTURA?: SI"+"</b>";
	    		}
	    		
	        	  if(os>0){ //si el query es exitoso
	        		
	        		if (e == null) {
					
	        			while( i < os  ) { //recorriendo la lista resultante del Query
             		    	 String saop=objects.get(i).getString("aopdbp"); //sacando el string de oracion presencial por cada record diario
             		    	 String saopi=objects.get(i).getString("aopidbp"); //sacando el string de oracion por internet por cada record diario 
             		    	 
             		    	String sopl=objects.get(i).getString("opldbp"); //sacando el string de oracion por pastores y lideres por cada record diario 
             		    	String scoo=objects.get(i).getString("coodbp"); //sacando el string de oracion por coordinadores por cada record diario 
             		    	String smgc=objects.get(i).getString("mgcdbp"); //sacando el string de oracion por miembros grupo conex por cada record diario 
             		    	String sopd=objects.get(i).getString("opddbp"); //sacando el string de oracion por discipulos por cada record diario 
             		    	String soplco=objects.get(i).getString("oplcodbp"); //sacando el string de oracion por la cosecha por cada record diario 
            		    	   
             		    	String sdia=objects.get(i).getString("diadbp"); //sacando String del dia para DEBUG nada mas
             		    	 
             		    	 if(saop.equals("true")){
             		    		s++;  //si encuentra algun true para asistencia a oracion presencial aumenta este contador
             		    	 
             		    	 }
             		    	 
             		    	if(saopi.equals("true")){
             		    		si++;  //si encuentra algun true para asistencia a oracion por internet aumenta este contador
             		    	 }
             		    	 
             		    	if(sopl.equals("true")){
             		    		  //si encuentra algun true para oracion por pastores y lideres se infla String resultante en HTML
             		    		srpl = "<b>"+"| Pastores y Líderes | "+"</b>";
             		    	 }
             		    	 
             		    	if(scoo.equals("true")){
             		    		  //si encuentra algun true para oracion por coordinadores se infla String resultante en HTML
             		    	   srco = "<b>"+"| Coordinadores | "+"</b>";
             		    	 }
             		    	 
             		    	if(smgc.equals("true")){
             		    		  //si encuentra algun true para oracion por miembros grupo conexion se infla String resultante en HTML
             		    		srmdg = "<b>"+"| Miembros de Grupo de Conexión | "+"</b>";
             		    	 }
             		    	 
             		    	if(sopd.equals("true")){
             		    		 //si encuentra algun true para oracion por discipulos se infla String resutlante en HTML
             		    		srpd = "<b>"+"| Discipulos | "+"</b>";
             		    	 }
             		    	
             		    	if(soplco.equals("true")){
             		    		  //si encuentra algun true para oracion por la cosecha se infla String resultante en HTML
             		    	   srplco = "<b>"+"| La Cosecha | "+"</b>";
             		    	 }
             		    	 
             		    	Log.e("dia",sdia); 
             		    	
             		    	i++;
             		    }//  While de booleans en PARSE
             		      
             		      //Strings con variables que inflan asistencia a oracion
	        			    sraop="<b>"+"¿CUANTAS VECES ASISTI A ORACION PRESENCIAL?: "+String.valueOf(s)+"</b>";
	        	   		    sraopi="<b>"+"¿CUANTAS VECES ASISTI A ORACION POR INTERNET?: "+String.valueOf(si)+"</b>";
	        		       
	        	   		   
	        		  
					
					} else { //error tipo excepcion en query
			           			           
			        }
	        	
	        	}else{ // si el query no es exitoso en PARSE
	        	
	        	}
	        	
	        	  
	        	 
	        	  try {

	        		
	        		  //Guarda el archivo en el directorio de SDCARD
	                  File sdCard = Environment.getExternalStorageDirectory();
	                  File directory = new File (sdCard.getAbsolutePath() + "/R07D");
	                  directory.mkdirs();

	                  
	                  File file = new File(directory, "REPR07D-"+mes+"-"+anio+".html");
	                  FileOutputStream fOut = new FileOutputStream(file);
	                  OutputStreamWriter osw = new OutputStreamWriter(fOut,"latin1");   //ESTAR PENDIENDTE TEMA UTF-8 ISO NOSEQUE!!!  ENCODING
	                  
	                  
	                  //Creando bloque encabezado reporte HTML
	                  cuerpocorreo= "<!DOCTYPE html>\n"+"<html>\n"+"<body>\n"+"<p>\n"+fecha+"<br />"+nombre+"<br />"+nombrecg+"<br /><br />"+mdl+"<br />"+cmdl+"<br /><br />"+sraop+"<br />"+sraopi+"<br /><br />"+"<b>ORACION POR:</B> "+srpl+srco+srmdg+srpd+srplco+"<br /><br /><br /><br />"+"</p>\n";
      	    		
	                  osw.write(cuerpocorreo);
	                  
	                  //Creando encabezado de tabla, segundo bloque HTML	                  
	                  osw.write("<table border=\"1\">\n");
	                  osw.write("<tr>\n");
	                  osw.write("<th>FECHA</th>\n");
	                  osw.write("<th>HORA INICIO</th>\n");
	                  osw.write("<th>HORA FIN</th>\n");
	                  osw.write("<th>LECTURA BIBLICA</th>\n");
	                  osw.write("<th>¿QUE ME HABLO DIOS?</th>\n");
	                  osw.write("<th>ACCION DE GRACIAS</th>\n");
	                  osw.write("<th>LISTA DE NUEVOS</th>\n");
	                  osw.write("<th>PETICIONES/INTERCESION</th>\n");
	                  osw.write("</tr>\n");
	                  
	                  
	                  //creando tabla HTML
	                  i=0;
	                  while(i<os){
	                	  String sfecha=objects.get(i).getString("fechadbp");
	                	  String hi=objects.get(i).getString("horaidbp");
	                	  String hf=objects.get(i).getString("horafdbp");
	                	  String slb=objects.get(i).getString("lbdbp");
	                	  String sqmhD=objects.get(i).getString("qmhDdbp");
	                	  String sadg=objects.get(i).getString("adgdbp");
	                	  String sldn=objects.get(i).getString("ldndbp");
	                	  String spei=objects.get(i).getString("peidbp");
	                	 
	                	Log.e("ini3",String.valueOf(hi.length()));
	                  	Log.e("fin3",String.valueOf(hf.length()));
	                	  
	                	  osw.write("<tr>\n");
	                	  osw.write("<td width=\"100\" style=\"text-align:center\" >"+  sfecha   +" </td>\n");
	                	  osw.write("<td width=\"120\" style=\"text-align:center\">"+  hi   +" </td>\n");
	                	  osw.write("<td width=\"100\" style=\"text-align:center\" >"+  hf   +" </td>\n");
	                	  osw.write("<td width=\"200\" >"+  slb   +" </td>\n");
	                	  osw.write("<td>"+  sqmhD   +" </td>\n");
	                	  osw.write("<td>"+  sadg   +" </td>\n");
	                	  osw.write("<td>"+  sldn   +" </td>\n");
	                	  osw.write("<td>"+  spei   +" </td>\n");
	                	  osw.write(" </tr>\n");
	                	  
	                	  i++;
	                  }
	                  
	                 
	                 //cerrando tabla y doc HTML 
	                  osw.write(" </table> \n");
	               
	                 
	                  
	                  osw.write("\n\n <p>Reporte Generado por R07D</p>\n");
	                  osw.write(" </body>\n");
	                  osw.write(" </html>\n");
	         
	                  
	                  osw.flush();
	                  osw.close();
	         
	                  
	                } 

	    catch (java.io.IOException e1) {

	                }
	      
	        	
	        	
	        	//Creando el EMAIL intent 
		      		
	        	  String strFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/R07D"+"/REPR07D-"+mes+"-"+anio+".html";
	        		
	        	  
	        	  //Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));
	        	     Intent emailIntent = new Intent(Intent.ACTION_SEND);
	        	    emailIntent.setType("application/image");
		      	    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "REPORTE MENSUAL R07D: "+et1.getText().toString()+" "+mes+"/"+anio);
		      	    emailIntent.putExtra(Intent.EXTRA_TEXT, "REPORTE R07D ADJUNTO");
		      	    emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file://" + strFile)); 
		      			 
		      	    startActivity(Intent.createChooser(emailIntent, "Send email..."));
		    
		      	//Html.fromHtml(yourHtml)  para poner en EXTRA_TEXT html sencillo
		      	    
		    
	          
	          
	          
	          }//done
	   
	        }); //cierra Query
	
	    }//cierre else check internet
	    
	    
	    
	    }//cierre onclick
	
	
	
	
	
	private boolean isNetworkAvailable() {  //FUNCION DE CHECK RED
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reporte, menu);
		return true;
	}

}
