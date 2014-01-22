package com.xoaquin.r07d;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MuroActivity extends ListActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//COLOR AL ACTIONBAR
		ActionBar ab=getActionBar();
		Drawable drw = getResources().getDrawable( R.drawable.b1 );
		ab.setBackgroundDrawable(drw);
		
		//trayendo listview y poniendo atributos
		ListView lv = getListView();
	
		
//FUERZA BRUTA PARA CAMBIAR BACKGROUND DE LISTVIEW (CHAMBONADA)
		lv.setDivider(null);
		lv.setDividerHeight(0);
		ArrayList<CustomObject> objects = new ArrayList<CustomObject>();
		
		for(int f=0;f<31;f++){//chambonada set background listview
			CustomObject co2=new CustomObject("","","","");
    		objects.add(co2);
		}
		
		CustomAdapter customAdapterCHAMBO = new CustomAdapter(this, objects);    	
		setListAdapter(customAdapterCHAMBO);
		

		
		
//LLENANDO LISTVIEW EN TAREA ASYNC
		//lv.setDivider(new ColorDrawable(getResources().getColor(R.color.marron)));
		//lv.setDividerHeight(1);
		Context ctx=this;
		AsyncTaskRunner2 runner = new AsyncTaskRunner2();
        runner.execute(ctx);
		
			
//implementacion de longclick en lista con intent para correo electronico al usuario
		  lv.setOnItemLongClickListener(new OnItemLongClickListener() {
		     

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				CustomObject item = (CustomObject) getListAdapter().getItem(arg2);
			    String c=item.getProp1();
			    String t=item.getProp2();
			    String f=item.getProp3();
			    String te=item.getProp4();
			  
			    
			    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
			            "mailto",c, null));
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "COMENTARIO ACERCA DE "+t+" EN MURO R07D ("+f+")");
			emailIntent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n\n\n"+t+"\n"+te);
			startActivity(Intent.createChooser(emailIntent, "Send email..."));
	        	  
			
	        	  

				return false;
			}
		  });
		
		   
	}



private class AsyncTaskRunner2 extends AsyncTask<Context, Void, CustomAdapter> {	
		
	private CustomAdapter customAdapter;	
	private ProgressDialog progressDialog;
	
	protected void onPreExecute() {
		
		progressDialog=ProgressDialog.show(MuroActivity.this,null,null);
        progressDialog.setContentView(new ProgressBar(MuroActivity.this));
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}
		

	    protected CustomAdapter doInBackground(Context...params) {
			
			
	    	//Creando lista de objetos CUSTOM
			ArrayList<CustomObject> objects = new ArrayList<CustomObject>();
			
			//Query de toda la clase en Parse
			ParseQuery<ParseObject> q = ParseQuery.getQuery("Muro");
			q.orderByDescending("createdAt");
			try {
				List<ParseObject> o=q.find();
				
				if(o.size()>0){
	        		
					//llenando la lista de objetos custom
					int i=0;
	        		while(i<o.size()){
	        		//declarando el objeto custom y usandolo para llenar lista custom
	        			CustomObject co=new CustomObject(o.get(i).getString("correo"),o.get(i).getString("tipo"),o.get(i).getString("fecha"),o.get(i).getString("texto"));
	        		objects.add(co);
	        		i++;
	        		
	        		}
			
	        		for(int f=0;f<21;f++){//chambonada set background listview
	        			CustomObject co2=new CustomObject("","","","");
	            		objects.add(co2);
	        		}
	        		
	        		//usando la lista para llamar el adaptador e inflar el listview
	        		customAdapter = new CustomAdapter(params[0], objects);    		
				
				}
	        		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	   	    return customAdapter;
	    }
		
		
	    
	    protected void onPostExecute(CustomAdapter ca) {
	    	ListView lv = getListView();
	    	lv.setDivider(new ColorDrawable(getResources().getColor(R.color.marron)));
			lv.setDividerHeight(1);
	    	setListAdapter(ca);
	    	progressDialog.dismiss();
		  }
	

	}
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.muro, menu);
		return true;
	}

}
