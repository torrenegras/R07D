package com.xoaquin.r07d;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;


//ACTIVIDAD DUMMY PARA USAR EN TRAER AL FRENTE APP CON NOTIFICACION

public class NotiActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		 if (isTaskRoot())
	        {
	            // Start the app before finishing
	            Intent startAppIntent = new Intent(getApplicationContext(), MainActivity.class);
	            startAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            startActivity(startAppIntent);
	        }
		
		finish();
		
	}

	
}
