package com.xoaquin.r07d;

import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class NivelesActivity extends Activity {

	private String mensaje=" ",puntos=" ";
	
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
		  cu.saveInBackground();
		} else {
		  // show the signup or login screen
		}
	
	}

	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.niveles, menu);
		return true;
	}

}
