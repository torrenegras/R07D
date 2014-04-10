
//ACTIVIDAD DE VISUALIZACION DE NIVELES Y PUNTAJES 

package com.xoaquin.r07d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.parse.ParseUser;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class NivelesActivity extends Activity {

	private String mensaje=" ",puntos=" ";
	
	LinearLayout L1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_niveles);
		
		
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    mensaje = extras.getString("msj");
		    puntos = extras.getString("pun");
		  
		}
	
		TextView tv=(TextView) findViewById(R.id.textViewt);
		TextView tv2=(TextView) findViewById(R.id.textViewtxt);
		Button b=(Button) findViewById(R.id.button1);
		L1 = (LinearLayout) findViewById(R.id.ll);
		
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
		
		tv.setTypeface(kepf);
		tv2.setTypeface(kepf);
		b.setTypeface(kepf);
		tv.setText(mensaje);
	
		
		
		ParseUser cu = ParseUser.getCurrentUser();
		if (cu != null) {
		  int pun= cu.getInt("puntaje");
		  pun=pun+Integer.valueOf(puntos);
		  b.setText(String.valueOf(pun));
		  cu.put("puntaje", pun);
		  cu.saveEventually();
		} else {
		  // show the signup or login screen
		}
	
	}//fin onCreate()

	
	//FUNCION COMPARTIR PUNTAJE REDES SOCIALES 
	public void onclicksharepoints(View v) { 
	 
		 View v1 = L1.getRootView();
         v1.setDrawingCacheEnabled(true);
         Bitmap bm = v1.getDrawingCache();
         
		 saveBitmap(bm);
	 }
	 
	 //FUNCION DE APOYO PARA GUARDAR IMAGEN A COMPARTIR
	 public void saveBitmap(Bitmap bitmap) {
	         String filePath = Environment.getExternalStorageDirectory()
	                 + File.separator + "Pictures/screenshot.png";
	         File imagePath = new File(filePath);
	         FileOutputStream fos;
	         try {
	             fos = new FileOutputStream(imagePath);
	             bitmap.compress(CompressFormat.PNG, 100, fos);
	             fos.flush();
	             fos.close();
	             shareimagesocial(filePath);
	         } catch (FileNotFoundException e) {
	             Log.e("GREC", e.getMessage(), e);
	         } catch (IOException e) {
	             Log.e("GREC", e.getMessage(), e);
	         }
	     }
	 
	 
	 //FUNCION PARA COMPARTIR LA IMAGEN EN SI
	 public	 void shareimagesocial(String path) {
	         Intent Intent = new Intent(android.content.Intent.ACTION_SEND);
	         Intent.putExtra(android.content.Intent.EXTRA_EMAIL,
	                 new String[] { "" });
	         Intent.putExtra(android.content.Intent.EXTRA_SUBJECT,
	                 getString(R.string.shrscoresub));
	         Intent.putExtra(android.content.Intent.EXTRA_TEXT,
	                 getString(R.string.shrscoretext));
	         Intent.setType("image/png");
	         
	         Uri myUri = Uri.parse("file://" + path);
	         Intent.putExtra(android.content.Intent.EXTRA_STREAM, myUri);
	         
	         startActivity(android.content.Intent.createChooser(Intent, getString(R.string.shrnoti)));
	     }
	 
	
}
