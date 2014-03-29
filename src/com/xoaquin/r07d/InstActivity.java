
//ACTIVIDAD DE INSTRUCCIONES

package com.xoaquin.r07d;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.TextView;


@SuppressWarnings("unused")
public class InstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inst);
		
		//COLOR AL ACTIONBAR
		ActionBar ab=getActionBar();
		Drawable drw = getResources().getDrawable( R.drawable.b1 );
		ab.setBackgroundDrawable(drw);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inst, menu);
		return true;
	}

}
