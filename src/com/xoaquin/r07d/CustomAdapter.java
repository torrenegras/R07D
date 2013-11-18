package com.xoaquin.r07d;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	   private LayoutInflater inflater;
	  private ArrayList<CustomObject> objects;

	   private class ViewHolder {
	      TextView textView1;
	      TextView textView2;
	      TextView textView3;
	      TextView textView4;
	   }

	   public CustomAdapter(Context context, ArrayList<CustomObject> objects) {
	      inflater = LayoutInflater.from(context);
	      this.objects = objects;
	   }

	   public int getCount() {
	      return objects.size();
	   }

	   public CustomObject getItem(int position) {
	      return objects.get(position);
	   }

	   public long getItemId(int position) {
	      return position;
	   }

	   public View getView(int position, View convertView, ViewGroup parent) {
	      ViewHolder holder = null;
	      if(convertView == null) {
	         holder = new ViewHolder();
	         convertView = inflater.inflate(R.layout.rowlayout, null);
	         holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
	         holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
	         holder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
	         holder.textView4 = (TextView) convertView.findViewById(R.id.textView4);
	         
	         convertView.setTag(holder);
	      } else {
	         holder = (ViewHolder) convertView.getTag();
	      }
	      Typeface kepf = Typeface.createFromAsset(holder.textView1.getContext().getAssets(),"Kepler-Std-Black_26074.ttf");
	      holder.textView1.setTypeface(kepf);
	      holder.textView2.setTypeface(kepf);
	      holder.textView3.setTypeface(kepf);
	      holder.textView4.setTypeface(kepf);
	     
	      holder.textView1.setTextColor(Color.rgb(141, 102, 95));
	      holder.textView2.setTextColor(Color.rgb(141, 102, 95));
	      holder.textView3.setTextColor(Color.rgb(141, 102, 95));
	      holder.textView4.setTextColor(Color.rgb(141, 102, 95));
	      
	           
	      
	      holder.textView1.setText(objects.get(position).getProp1());
	      holder.textView2.setText(objects.get(position).getProp2());
	      holder.textView3.setText(objects.get(position).getProp3());
	      holder.textView4.setText(objects.get(position).getProp4());
	      return convertView;
	   }
	}