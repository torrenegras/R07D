package com.xoaquin.r07d;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MuroActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_muro);
		
		
		ArrayList<CustomObject> objects = new ArrayList<CustomObject>();
		
		
		
		ParseQuery<ParseObject> q = ParseQuery.getQuery("Muro");
		q.orderByDescending("fecha");
		try {
			List<ParseObject> o=q.find();
			
			if(o.size()>0){
        		
				int i=0;
        		while(i<o.size()){
        		CustomObject co=new CustomObject(o.get(i).getString("correo"),o.get(i).getString("tipo"),o.get(i).getString("fecha"),o.get(i).getString("texto"));
        		objects.add(co);
        		i++;
        		
        		}
		
        		for(int f=0;f<21;f++){//chambonada set background listview
        			CustomObject co2=new CustomObject("","","","");
            		objects.add(co2);
        		}
        		
        		
        		CustomAdapter customAdapter = new CustomAdapter(this, objects);
        		setListAdapter(customAdapter);
        		
			
			}
        		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		   
	}

	
	
	
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    String item = (String) getListAdapter().getItem(position);
	    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	  }
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.muro, menu);
		return true;
	}

}
