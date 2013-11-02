package com.xoaquin.r07d;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	
	
		   TextView link = (TextView) findViewById(R.id.textView2);
	       String linkText = " "+"<a href='https://twitter.com/AngelicaMaCruz'>@AngelicaMaCruz</a>";
	       link.setText(Html.fromHtml(linkText));
	       link.setMovementMethod(LinkMovementMethod.getInstance());
	
	       TextView link2 = (TextView) findViewById(R.id.textView1);
	       String linkText2 = " "+"<a href='https://twitter.com/torrenegras'>@torrenegras</a>";
	       link2.setText(Html.fromHtml(linkText2));
	       link2.setMovementMethod(LinkMovementMethod.getInstance());
	
	       Typeface kepf = Typeface.createFromAsset(getAssets(),"Kepler-Std-Black_26074.ttf");
	       
			link.setTypeface(kepf);
	       link2.setTypeface(kepf);
	
	       TextView ab = (TextView) findViewById(R.id.TextView01);
	       TextView aut = (TextView) findViewById(R.id.textView3);
	
	       ab.setTypeface(kepf);
	       aut.setTypeface(kepf);
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

}
