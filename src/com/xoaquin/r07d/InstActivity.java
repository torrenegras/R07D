package com.xoaquin.r07d;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

@SuppressWarnings("unused")
public class InstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inst);
		
		/*
		Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
    	TextView t1=(TextView) findViewById(R.id.textView1);
		t1.setTypeface(kepf);
		
		TextView t2=(TextView) findViewById(R.id.textView2);
		t2.setTypeface(kepf);
		
		TextView t3=(TextView) findViewById(R.id.textView3);
		t3.setTypeface(kepf);
		
		TextView t4=(TextView) findViewById(R.id.textView4);
		t4.setTypeface(kepf);
		
		TextView t5=(TextView) findViewById(R.id.textView5);
		t5.setTypeface(kepf);
		
		TextView t6=(TextView) findViewById(R.id.textView6);
		t6.setTypeface(kepf);
		
		TextView t7=(TextView) findViewById(R.id.textView7);
		t7.setTypeface(kepf);
		
		TextView t8=(TextView) findViewById(R.id.textView8);
		t8.setTypeface(kepf);
		
		*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inst, menu);
		return true;
	}

}
