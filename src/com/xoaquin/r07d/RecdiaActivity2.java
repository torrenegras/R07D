package com.xoaquin.r07d;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RecdiaActivity2 extends FragmentActivity {
	
	private TextView t10,t11;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recdia_activity2);
		
		//COLOR AL ACTIONBAR
		ActionBar ab=getActionBar();
		Drawable drw = getResources().getDrawable( R.drawable.b1 );
		ab.setBackgroundDrawable(drw);
		
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	t10=(TextView) findViewById(R.id.textViewv8);
    	t11=(TextView) findViewById(R.id.textViewv9);
		t10.setTypeface(kepf);
		t11.setTypeface(kepf);
		
		SpannableString contentUnderline = new SpannableString("REGISTRO");
	    contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);
	    t10.setText(contentUnderline);
			
		//INFLANDO FRAGMENTO REGISTRO		
		RegistroFragment rf=new RegistroFragment();
		rf.setArguments(getIntent().getExtras()); //pasando los extras como argumentos al fragmento...
		getFragmentManager().beginTransaction().add(R.id.fragments_container, rf).commit();
		
	
	}

	
	
	public void onclickdevo(View v) {
		
		//LLAMANDO FUNCION AUTOGUARDADO PARA CUANDO CAMBIO DE TAB
		RegistroFragment rf=new RegistroFragment();
		rf.guardar();
		
		Toast.makeText(RecdiaActivity2.this, "Auto-Guardando...", Toast.LENGTH_LONG).show();
	
		SpannableString contentUnderline = new SpannableString("DEVOCIONAL");
	    contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);
	    t11.setText(contentUnderline);
	    
	    t10.setText("REGISTRO");
		
		
		//INFLANDO FRAGMENTO DEVOCIONAL 
		DevocionalFragment df=new DevocionalFragment();
    	FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragments_container, df);
		transaction.addToBackStack(null);
    	transaction.commit();
    	
    	
    	
    	
    	//DIALOG PARA RECLUTAR DEVOCIONALES
    	int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
                public void run() {
                	  	
    	
    	    	AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(RecdiaActivity2.this);
		 
		// Setting Dialog Title
		alertDialog2.setTitle("En Construcci�n...");
		 
		// Setting Dialog Message
		alertDialog2.setMessage("Conoces a alguien que pueda aportar un devocional diario para esta secci�n?");
		 
		// Setting Icon to Dialog
		alertDialog2.setIcon(R.drawable.uc);
		 
		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("Escr�beme",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	 
		            	Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
		 			            "mailto","xoaquin@outlook.com", null));
		 			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CONOZCO A ALGUIEN PARA APORTAR DEVOCIONAL A R07D");
		 			
		 			startActivity(Intent.createChooser(emailIntent, "Send email..."));
		 	        	  
		 			
		            }
		        });
		// Setting Negative "NO" Btn
		alertDialog2.setNegativeButton("No",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                
		                dialog.cancel();
		                
		            }
		        });
		 
		// Showing Alert Dialog
		alertDialog2.show();
		
		
                }
              }, secondsDelayed * 1000);
		
		
    }
	
	
	
	public void onclickreg(View v) {
		
		SpannableString contentUnderline = new SpannableString("REGISTRO");
	    contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);
	    t10.setText(contentUnderline);
	    
	    t11.setText("DEVOCIONAL");
		
		//INFLANDO FRAGMENTO REGISTRO
		RegistroFragment rf=new RegistroFragment();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragments_container, rf);
		transaction.addToBackStack(null);
    	transaction.commit();
		
	}
	
	
	//CONTROL DE BOTON BACK PARA NAVEGACION E INTEGRACION WEBVIEW
	@Override
	public void onBackPressed() {
	    DevocionalFragment f= new DevocionalFragment();
	    f.onBackPressed();
	    int ret=f.onBackPressed();
	    if(ret==1){finish();}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recdia_activity2, menu);
		return true;
	}

}
