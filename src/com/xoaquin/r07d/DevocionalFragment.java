package com.xoaquin.r07d;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class DevocionalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
       
    	View V=inflater.inflate(R.layout.fragment_devocional, container, false);
  	
    	
        return V;
    }
}
