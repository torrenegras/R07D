package com.xoaquin.r07d;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;


//ACTIVIDAD ALERTA FIN DE MES SOLO ES UN ALERTDIALOG 


public class JalertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jalert);
		
		
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
		        JalertActivity.this);
		 
		// Setting Dialog Title
		alertDialog2.setTitle("Recordatorio Env\u00EDo R07D");
		 
		// Setting Dialog Message
		alertDialog2.setMessage("Recordemos que este es el d\u00EDa que hizo el Señor para que envi\u00E1ramos el R07D al l\u00EDder!  Bendiciones!");
		 
		// Setting Icon to Dialog
		alertDialog2.setIcon(R.drawable.ic_launcher);
		
		alertDialog2.setCancelable(false);
		 
		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("OK",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		         
		            	Intent i = new Intent(getApplicationContext(), MainActivity.class);
	                	startActivity(new Intent(i));
	                    finish();
	                    
		            }
		        });
		// Setting Negative "NO" Btn
		alertDialog2.setNegativeButton("DESPUES",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	
		            	dialog.cancel();
		                finish();
		                
		            }
		        });
		 
		// Showing Alert Dialog
		alertDialog2.show();
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jalert, menu);
		return true;
	}

}
