package com.xoaquin.r07d;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;

public class MuroActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//Creando lista de objetos CUSTOM
		ArrayList<CustomObject> objects = new ArrayList<CustomObject>();
		
		
		//Query de toda la clase en Parse
		ParseQuery<ParseObject> q = ParseQuery.getQuery("Muro");
		q.orderByDescending("fecha");
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
        		CustomAdapter customAdapter = new CustomAdapter(this, objects);
        		setListAdapter(customAdapter);
        		
			
			}
        		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		   
	}

	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.muro, menu);
		return true;
	}

}
