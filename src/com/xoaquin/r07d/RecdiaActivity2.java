
//****  ACTIVIDAD CORE2 PARA GRABAR RECORD DIARIO ****

package com.xoaquin.r07d;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


public class RecdiaActivity2 extends FragmentActivity {
	
	private TextView t10,t11;
	private int ff=0;

	
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
		
		SpannableString contentUnderline = new SpannableString(getString(R.string.reg));
	    contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);
	    t10.setText(contentUnderline);
			
		//INFLANDO FRAGMENTO REGISTRO		
		RegistroFragment rf=new RegistroFragment();
		rf.setArguments(getIntent().getExtras()); //pasando los extras como argumentos al fragmento...
		getFragmentManager().beginTransaction().add(R.id.fragments_container, rf).commit();
		ff=1;
	
	}

	
	
	public void onclickdevo(View v) {
		
	  
	   
		//LLAMANDO FUNCION AUTOGUARDADO PARA CUANDO CAMBIO DE TAB
		RegistroFragment rf = (RegistroFragment) getFragmentManager().findFragmentById(R.id.fragments_container);
		rf.guardar();
		
		Toast.makeText(RecdiaActivity2.this, getString(R.string.augu)+"...", Toast.LENGTH_LONG).show();
	
		SpannableString contentUnderline = new SpannableString(getString(R.string.devr));
	    contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);
	    t11.setText(contentUnderline);
	    
	    t10.setText(getString(R.string.reg));
		
		
		//INFLANDO FRAGMENTO DEVOCIONAL 
		DevocionalFragment df=new DevocionalFragment();
    	FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragments_container, df);
		transaction.addToBackStack(null);
    	transaction.commit();
    	ff=2;
    	
    	
    	String locale = getResources().getConfiguration().locale.getDisplayName();
   		if(locale.contains("espa\u00F1ol")){ 
  		
  		}else{
  		
  			//DIALOG PARA RECLUTAR DEVOCIONALES
  	    	int secondsDelayed = 5;
  	        new Handler().postDelayed(new Runnable() {
  	                public void run() {         
  	                	
  	               showRateDialog(RecdiaActivity2.this,null); 	
  	    
  	                }
  	              }, secondsDelayed * 1000);
  		}
    	
    	
		
	     
    }
	
	
	
	public void onclickreg(View v) {
		
		SpannableString contentUnderline = new SpannableString(getString(R.string.reg));
	    contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);
	    t10.setText(contentUnderline);
	    
	    t11.setText(getString(R.string.devr));
		
		//INFLANDO FRAGMENTO REGISTRO
		RegistroFragment rf=new RegistroFragment();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragments_container, rf);
		transaction.addToBackStack(null);
    	transaction.commit();
		ff=1;
	}
	
	
	//CONTROL DE BOTON BACK PARA NAVEGACION E INTEGRACION WEBVIEW
	@Override
	public void onBackPressed() {
		
		if(ff==2){
		DevocionalFragment fragment = (DevocionalFragment) getFragmentManager().findFragmentById(R.id.fragments_container);
		int i= fragment.onBackPressed();
		if(i==0){}else{finish();}
		}else{
			finish();
		}
	
	
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
        tv.setText(mContext.getString(R.string.ucons)+"...\n\n"+mContext.getString(R.string.caa));
        tv.setTypeface(kepf);
        tv.setLayoutParams(params);
        tv.setTextColor(Color.rgb(141, 102, 95));
        tv.setBackgroundColor(Color.rgb(255, 226, 216));
        tv.setKeyListener(null);
        ll.addView(tv);
              
               
        Button b1 = new Button(mContext);
        b1.setText(mContext.getString(R.string.wtm));
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	dialog.dismiss();
            	Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
 			            "mailto","xoaquin@torrenegra.co", null));
 			emailIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getString(R.string.ikso));
 			
 			mContext.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            
            }
        });        
        b1.setTypeface(kepf);
        b1.setBackgroundColor(Color.rgb(141, 102, 95));
        b1.setLayoutParams(params);
        b1.setTextColor(Color.WHITE);
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText(mContext.getString(R.string.nodev));
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	dialog.dismiss();
            	dialog.cancel();
            
                
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
    public void onStop() {
        super.onStop();
        
        //refresh de widget 
	    Intent intent = new Intent(this,MyWidgetProvider.class);
	    intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
	    int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), MyWidgetProvider.class));
	    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
	    sendBroadcast(intent);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recdia_activity2, menu);
		return true;
	}

}
