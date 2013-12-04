package com.xoaquin.r07d;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;



public class RegistroFragment extends Fragment {
    
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V= inflater.inflate(R.layout.fragment_registro, container, false);
        
        final Button b=(Button) V.findViewById(R.id.button1);
        
        b.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v) 
            {
            	Log.e("ddd","Resuelto");
            	b.setText("ggg");
            }       
        }
        );
        
        
        
        return V;
	}
	
		
	
}
