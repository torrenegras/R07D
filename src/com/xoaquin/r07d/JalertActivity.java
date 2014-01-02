package com.xoaquin.r07d;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


//ACTIVIDAD ALERTA FIN DE MES SOLO ES UN ALERTDIALOG 


public class JalertActivity extends Activity{     
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jalert);
		
		
        showRateDialog(this,null);
        
		
	}

	
	
	public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mContext);

       
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        Typeface kepf = Typeface.createFromAsset(mContext.getAssets(),"Kepler-Std-Black_26074.ttf");
        
        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(Color.rgb(255, 226, 216));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 30);
        EditText tv= new EditText (mContext);
        tv.setText("Recordemos que este es el d\u00EDa que hizo el Se\u00F1or para que envi\u00E1ramos el R07D al l\u00EDder!\n\nBendiciones!");
        tv.setTypeface(kepf);
        tv.setLayoutParams(params);
        tv.setTextColor(Color.rgb(141, 102, 95));
        tv.setBackgroundColor(Color.rgb(255, 226, 216));
        tv.setKeyListener(null);
        ll.addView(tv);
              
               
        Button b1 = new Button(mContext);
        b1.setText("OK" );
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	dialog.dismiss();
            	Intent i = new Intent(mContext, MainActivity.class);
            	mContext.startActivity(new Intent(i));
            
            }
        });        
        b1.setTypeface(kepf);
        b1.setBackgroundColor(Color.rgb(141, 102, 95));
        b1.setLayoutParams(params);
        b1.setTextColor(Color.WHITE);
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("DESPUES");
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	dialog.dismiss();
            	System.exit(0);            
                
            }
        });
        b2.setTypeface(kepf);
        b2.setBackgroundColor(Color.rgb(141, 102, 95));
        b2.setLayoutParams(params);
        b2.setTextColor(Color.WHITE);
        ll.addView(b2);

        

        dialog.setContentView(ll);        
        dialog.show();        
    }
	
	 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jalert, menu);
		return true;
	}

}
