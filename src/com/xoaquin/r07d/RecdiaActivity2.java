package com.xoaquin.r07d;

import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class RecdiaActivity2 extends FragmentActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recdia_activity2);
			
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	TextView t10=(TextView) findViewById(R.id.textView10);
    	TextView t11=(TextView) findViewById(R.id.textView11);
		t10.setTypeface(kepf);
		t11.setTypeface(kepf);
		
		RegistroFragment rf=new RegistroFragment();
		rf.setArguments(getIntent().getExtras()); //pasando los extras como argumentos al fragmento...
		getFragmentManager().beginTransaction().add(R.id.fragments_container, rf).commit();
		
	
	}

	
	
	public void onclickdevo(View v) {
		DevocionalFragment df=new DevocionalFragment();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragments_container, df);
		transaction.addToBackStack(null);
    	transaction.commit();
	
		
	}
	
	
	public void onclickreg(View v) {
		RegistroFragment rf=new RegistroFragment();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragments_container, rf);
		transaction.addToBackStack(null);
    	transaction.commit();
		
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recdia_activity2, menu);
		return true;
	}

}
