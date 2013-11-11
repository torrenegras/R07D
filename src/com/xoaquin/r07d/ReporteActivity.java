package com.xoaquin.r07d;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.cete.dynamicpdf.Color;
import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageDimensions;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.UrlAction;
import com.cete.dynamicpdf.VAlign;
import com.cete.dynamicpdf.pageelements.Cell2;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.Link;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
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

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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


private static String FILE;
private String spdfop="",spdfopi="";

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
	        		       
	        	   		 spdfop="¿CUANTAS VECES ASISTI A ORACION PRESENCIAL?: "+String.valueOf(s);
	        	   	   	spdfopi="¿CUANTAS VECES ASISTI A ORACION POR INTERNET?: "+String.valueOf(si);
	        		  
	        	   		    
	        	   		    
	        	   		    
	        	   		    
					
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
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  //GENERACION REPORTE PDF
	          	    
	          	    FILE=Environment.getExternalStorageDirectory()	+ "/R07D/REPR07D-"+mes+"-"+anio+".pdf";
	          	    
	          	    RadioButton rb1=(RadioButton) findViewById(R.id.radioButton1);
	          	    
	          	      // Create a document and set it's properties
	                  Document objDocument = new Document();
	                  objDocument.setCreator("DynamicPDFHelloWorld.java");
	                  objDocument.setAuthor("XoaquinSoft");
	                  objDocument.setTitle("REPR07D-"+mes+"-"+anio);
	           
	                  // Create a page to add to the document
	                  Page objPage = new Page(1570,2000,30.0f);
	           
	                  // Create a strings para labels
	                  String tit= "REPORTE R07D";
	                  String sr = "REPORTE: "+mes+"/"+anio;
	                  String sn = "NOMBRE: "+et1.getText().toString();
	                  String snc= "NOMBRE COORDINADOR DE GRUPO CONEXION: "+et2.getText().toString();
	                  String sml= "META DE LECTURA: "+et3.getText().toString();
	                  String scm= "¿CUMPLI LA META DE LECTURA?: NO"; if(rb1.isChecked()){scm ="¿CUMPLI LA META DE LECTURA?: SI";}
	                  String sopre=spdfop;
	                  String sopoin=spdfopi;
	                  String sop= "ORACION POR: "+srpl+srco+srmdg+srpd+srplco;
	                  String rg= "Reporte Generado por R07D";
	                                    
	                  //crear labels iniciales
	                  Label objLabelt = new Label(tit, 635, 0, 1000, 100, Font.getCourierBoldOblique(),40, TextAlign.LEFT);
	                  Label objLabelr = new Label(sr, 0, 25, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  Label objLabeln = new Label(sn, 0, 50, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  Label objLabelnc = new Label(snc, 0, 75, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  Label objLabelml = new Label(sml, 0, 100, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  Label objLabelcm = new Label(scm, 0, 125, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  Label objLabelop = new Label(sopre, 0, 150, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  Label objLabeloi = new Label(sopoin, 0, 175, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  Label objLabelor = new Label(sop, 0, 200, 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                 
	           
	                  // Add labels to page
	                  objPage.getElements().add(objLabelt);
	                  objPage.getElements().add(objLabelr);
	                  objPage.getElements().add(objLabeln);
	                  objPage.getElements().add(objLabelnc);
	                  objPage.getElements().add(objLabelml);
	                  objPage.getElements().add(objLabelcm);
	                  objPage.getElements().add(objLabelop);
	                  objPage.getElements().add(objLabeloi);
	                  objPage.getElements().add(objLabelor);
	                  
	                  
	                  Table2 table2 = new Table2(0, 225, 1520, 2000);
	                  
	                  // Add columns to the table
	                  table2.getColumns().add(90);
	                  table2.getColumns().add(110);
	                  table2.getColumns().add(90);
	                  table2.getColumns().add(180);
	                  table2.getColumns().add(350);
	                  table2.getColumns().add(230);
	                  table2.getColumns().add(210);
	                  table2.getColumns().add(250);
	                  
	                  
	               // Add rows to the table and add cells to the rows
	                  Row2 row1 = table2.getRows().add(40, Font.getHelveticaBold(), 16, Color.a("Black"), Color.a("Gray"));
	                  
	                  
	                 
	                  Cell2 cell1=row1.getCells().add("FECHA");  cell1.setAlign(TextAlign.CENTER);  cell1.setVAlign(VAlign.CENTER);
	                  Cell2 cell2=row1.getCells().add("HORA INICIO");  cell2.setAlign(TextAlign.CENTER);  cell2.setVAlign(VAlign.CENTER);
	                  Cell2 cell3=row1.getCells().add("HORA FIN");  cell3.setAlign(TextAlign.CENTER);  cell3.setVAlign(VAlign.CENTER);
	                  Cell2 cell4=row1.getCells().add("LECTURA BIBLICA");  cell4.setAlign(TextAlign.CENTER);  cell4.setVAlign(VAlign.CENTER);
	                  Cell2 cell5=row1.getCells().add("¿QUE ME HABLO DIOS?");  cell5.setAlign(TextAlign.CENTER);  cell5.setVAlign(VAlign.CENTER);
	                  Cell2 cell6=row1.getCells().add("ACCION DE GRACIAS");  cell6.setAlign(TextAlign.CENTER);  cell6.setVAlign(VAlign.CENTER);
	                  Cell2 cell7=row1.getCells().add("LISTA DE NUEVOS");  cell7.setAlign(TextAlign.CENTER);  cell7.setVAlign(VAlign.CENTER);
	                  Cell2 cell8=row1.getCells().add("PETICIONES/INTERCESION");  cell8.setAlign(TextAlign.CENTER);  cell8.setVAlign(VAlign.CENTER);
	                  
	                  
	                  
	                //creando tabla PDF
	                  float aqmhD=0,atotmax=0;
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
	                	 
	                	  Row2 row = table2.getRows().add(30);
		                  
	                	  
	                	  Cell2 cell11=row.getCells().add(sfecha);  cell11.setAlign(TextAlign.CENTER);  cell11.setVAlign(VAlign.CENTER);
		                  Cell2 cell12=row.getCells().add(hi);  cell12.setAlign(TextAlign.CENTER);  cell12.setVAlign(VAlign.CENTER);
		                  Cell2 cell13=row.getCells().add(hf);  cell13.setAlign(TextAlign.CENTER);  cell13.setVAlign(VAlign.CENTER);
		                  Cell2 cell14=row.getCells().add(slb);  cell14.setAlign(TextAlign.JUSTIFY);  cell14.setVAlign(VAlign.TOP);
		                  Cell2 cell15=row.getCells().add(sqmhD);  cell15.setAlign(TextAlign.JUSTIFY);  cell15.setVAlign(VAlign.TOP);
		                  Cell2 cell16=row.getCells().add(sadg);  cell16.setAlign(TextAlign.JUSTIFY);  cell16.setVAlign(VAlign.TOP);
		                  Cell2 cell17=row.getCells().add(sldn);  cell17.setAlign(TextAlign.JUSTIFY);  cell17.setVAlign(VAlign.TOP);
		                  Cell2 cell18=row.getCells().add(spei);  cell18.setAlign(TextAlign.JUSTIFY);  cell18.setVAlign(VAlign.TOP);
		              
		                  
		                  aqmhD=aqmhD+cell15.getHeight();
  	                	 
	                	  atotmax= aqmhD;
		                  
	                	  
	                	  i++;
	                  }
	                  
	                  //label para link
	                  Label objLabelrg = new Label(rg, 1350, (atotmax+440), 1000, 100, Font.getHelvetica(), 12, TextAlign.LEFT);
	                  objLabelrg.setTextColor(Color.a("Blue"));
	                  objLabelrg.setUnderline(true);
	                  objPage.getElements().add(objLabelrg);
	                  
	                  //link
	                  Link objLink = new Link(1350,(atotmax+440),155,15, new UrlAction("https://play.google.com/store/apps/details?id=com.xoaquin.r07d"));
	                  objPage.getElements().add(objLink);
	                  
	                  Link objLink2 = new Link(1390,4,114,64, new UrlAction("https://play.google.com/store/apps/details?id=com.xoaquin.r07d"));
	                  objPage.getElements().add(objLink2);
	                  
	                  //cargando imagenes desde assets
	                  InputStream ims = getAssets().open("goo.jpg");
	                  Drawable d = Drawable.createFromStream(ims, null);
	                  Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
	                  ByteArrayOutputStream stream = new ByteArrayOutputStream();
	                  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	                  byte[] bitmapdata = stream.toByteArray();
	                  objPage.getElements().add(new Image(bitmapdata, 1390, 4, 0.24f));
	                  
	                  InputStream ims2 = getAssets().open("port.png");
	                  Drawable d2 = Drawable.createFromStream(ims2, null);
	                  Bitmap bitmap2 = ((BitmapDrawable)d2).getBitmap();
	                  ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
	                  bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
	                  byte[] bitmapdata2 = stream2.toByteArray();
	                  objPage.getElements().add(new Image(bitmapdata2, 1230, 0, 0.24f));
	                  
	                  
	                  table2.setHeight(atotmax+140);  //reset altura tabla
	                 	                   
	                  // Add the table to the page
	                  objPage.getElements().add(table2); 
	                  
	                  
	                  PageDimensions ps=new PageDimensions(1570,atotmax+140+(9*30)+100);  //reset dinamico altura pagina
	                  objPage.setDimensions(ps);
	                  
	                  
	                  // Add page to document
	                  objDocument.getPages().add(objPage);
	           
	                  // Outputs the document to file
                      objDocument.draw(FILE);
              	         
	                  
	                } 

	    catch (java.io.IOException e1) {

	                }
	      
	        	
	        	
	        	  String strFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/R07D"+"/REPR07D-"+mes+"-"+anio+".html";
	        	  String strFilepdf = Environment.getExternalStorageDirectory().getAbsolutePath() + "/R07D"+"/REPR07D-"+mes+"-"+anio+".pdf";
	        		
	        	  
	        	  final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
	        	  //emailIntent.setType("plain/text");
	        	  emailIntent.setType("application/image");


	        	  ArrayList<Uri> uris = new ArrayList<Uri>();

	        	  String[] filePaths = new String[] {strFile,strFilepdf};
	        	  for (String file : filePaths) {
	        	      File fileIn = new File(file);
	        	      Uri u = Uri.fromFile(fileIn);
	        	      uris.add(u);
	        	  }

	        	 // if ( !(app_preferences.getString("email", "") == null || app_preferences.getString("email", "").equals(""))) {
	        	   //   emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {app_preferences.getString("email", "")});    
	        	  //}

	        	  emailIntent.putExtra(Intent.EXTRA_SUBJECT,"REPORTE MENSUAL R07D: "+et1.getText().toString()+" "+mes+"/"+anio);
	        	  emailIntent.putExtra(Intent.EXTRA_TEXT, "\n\nREPORTE R07D ADJUNTO\n\n\n");
	        	  emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

	        	  startActivity(Intent.createChooser(emailIntent, "Email:"));
		    
	        	  
	        	  
	        	  /*
		        	//Creando el EMAIL intent  ANTIGUO, DEJADO POR REFERENCIA FUTURA 
			      		
		        	  String strFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/R07D"+"/REPR07D-"+mes+"-"+anio+".html";
		        	  String strFilepdf = Environment.getExternalStorageDirectory().getAbsolutePath() + "/R07D"+"/REPR07D-"+mes+"-"+anio+".pdf";
		        		
		        	  
		        	  //Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));
		        	     Intent emailIntent = new Intent(Intent.ACTION_SEND);
		        	    emailIntent.setType("application/image");
			      	    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "REPORTE MENSUAL R07D: "+et1.getText().toString()+" "+mes+"/"+anio);
			      	    emailIntent.putExtra(Intent.EXTRA_TEXT, "REPORTE R07D ADJUNTO");
			      	    emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file://" + strFile)); 
			      	    emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file://" + strFilepdf));
			      	  
			      	    startActivity(Intent.createChooser(emailIntent, "Send email..."));
			    
			      	//Html.fromHtml(yourHtml)  para poner en EXTRA_TEXT html sencillo
			      	  */
		        	  
	          
	          
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
