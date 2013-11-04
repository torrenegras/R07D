package com.xoaquin.r07d;

// ADAPTADOR PRESTADO EN INVESTIGACION  MODIFICADO CUSTOM MADE...


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
 
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
class MonthAdapter extends BaseAdapter {
        private GregorianCalendar mCalendar;
        private Calendar mCalendarToday;
        private Context mContext;
        private DisplayMetrics mDisplayMetrics;
        private List<String> mItems;
        private int mMonth;
        private int mYear;
        private int mDaysShown;
        private int mDaysLastMonth;
        private int mDaysNextMonth;
        private int mTitleHeight, mDayHeight;
        private final String[] mDays = { "LUN", "MAR", "MIE", "JUE", "VIE", "SAB", "DOM" };
        private final int[] mDaysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        
        
               
        public MonthAdapter(Context c, int month, int year, DisplayMetrics metrics) {
                mContext = c;
                mMonth = month;
                mYear = year;
                mCalendar = new GregorianCalendar(mYear, mMonth, 1);
                mCalendarToday = Calendar.getInstance();
                mDisplayMetrics = metrics;
                populateMonth();
        }
       
        
        //LLENADO DE ELEMENTOS EN LISTA PARA INFLAR EL GRIDVIEW
        
        private void populateMonth() {
                mItems = new ArrayList<String>();              
                for (String day : mDays) {
                        mItems.add(day);
                        mDaysShown++;
                }
               
                int firstDay = getDay(mCalendar.get(Calendar.DAY_OF_WEEK));
                int prevDay;
                if (mMonth == 0)
                        prevDay = daysInMonth(11) - firstDay + 1;
                else
                        prevDay = daysInMonth(mMonth - 1) - firstDay + 1;
                for (int i = 0; i < firstDay; i++) {
                        mItems.add(String.valueOf(prevDay + i));
                        mDaysLastMonth++;
                        mDaysShown++;
                }
               
                int daysInMonth = daysInMonth(mMonth); 
                for (int i = 1; i <= daysInMonth; i++) {
                        mItems.add(String.valueOf(i));
                        mDaysShown++;
                }
               
                mDaysNextMonth = 1;
                while (mDaysShown % 7 != 0) {
                        mItems.add(String.valueOf(mDaysNextMonth));
                        mDaysShown++;
                        mDaysNextMonth++;
                }
               
                mTitleHeight = 30;
                //int rows = (mDaysShown / 7);
                //mDayHeight = (mDisplayMetrics.heightPixels - mTitleHeight
                    //            - (rows * 3) - getBarHeight())/ (rows - 1);
                //mDayHeight=mDayHeight-100;
                mDayHeight=120;  //modificaion de separacion o altura de filas 
        }
       
        private int daysInMonth(int month) {
                int daysInMonth = mDaysInMonth[month];
                if (month == 1 && mCalendar.isLeapYear(mYear))
                        daysInMonth++;
                return daysInMonth;
        }
       
        @SuppressWarnings("unused")
		private int getBarHeight() {
                switch (mDisplayMetrics.densityDpi) {
                case DisplayMetrics.DENSITY_HIGH:
                        return 48;
                case DisplayMetrics.DENSITY_MEDIUM:
                        return 32;
                case DisplayMetrics.DENSITY_LOW:
                        return 24;
                default:
                        return 48;
                }
        }
       
        /**
         * For week starting Monday
         * @param day from Calendar.DAY_OF_WEEK
         * @return day week starting Monday
         */
        private int getDay(int day) {
                switch (day) {
                case Calendar.MONDAY:
                        return 0;
                case Calendar.TUESDAY:
                        return 1;
                case Calendar.WEDNESDAY:
                        return 2;
                case Calendar.THURSDAY:
                        return 3;
                case Calendar.FRIDAY:
                        return 4;
                case Calendar.SATURDAY:
                        return 5;
                case Calendar.SUNDAY:
                        return 6;
                default:
                        return 0;
                }
        }
       
        
        //funcion para verificar si fecha es HOY
        
        private boolean isToday(int day, int month, int year) {
                if (mCalendarToday.get(Calendar.MONTH) == month
                                && mCalendarToday.get(Calendar.YEAR) == year
                                && mCalendarToday.get(Calendar.DAY_OF_MONTH) == day) {
                        return true;
                }
                return false;
        }
       
        
        
        //funcion principal para cambios en layout de gridview
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                final TextView view = new TextView(mContext);
                view.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                view.setText(mItems.get(position));
                Typeface kepf = Typeface.createFromAsset(view.getContext().getAssets(),"Kepler-Std-Black_26074.ttf");
 
                
                if (position <= 6) {
                        // names
                        view.setBackgroundColor(Color.rgb(141, 102, 95));
                        view.setTextColor(Color.WHITE);
                        view.setHeight(mTitleHeight+2);
                        view.setClickable(true);
                    	view.setTypeface(kepf);
                      
    	
                } else if (position <= mDaysLastMonth + 6) {
                        // previous month
                	 view.setBackgroundColor(Color.rgb(255, 241, 231));
                        view.setTextColor(Color.rgb(221, 182, 175));
                        view.setHeight(mDayHeight);
                        view.setClickable(true);
                        view.setTypeface(kepf);
                        
                        
                } else if (position <= mDaysShown - mDaysNextMonth  ) {
                        // current month
                        view.setHeight(mDayHeight);
                        view.setTextColor(Color.rgb(141, 102, 95));
                        view.setTypeface(kepf);
                        
                        
                        int day = position - (mDaysLastMonth + 6);
                        if (isToday(day, mMonth, mYear)) {
                                view.setTextColor(Color.WHITE);
                                view.setBackgroundColor(Color.rgb(141, 102, 95));
                        }
                    
                      
                        
                        //Color Rojo y Verde con chequeo de base de datos
                      
                        ConnectivityManager connMgr = (ConnectivityManager) 
                                view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                           
                            if (networkInfo != null && networkInfo.isConnected()) { //chequeo RED
                                                  
                       
                            	   
           	        		 //snippet coloreado de rojo dias sin hacer R07 
                                  Calendar now = Calendar.getInstance();
                                  int hoy = now.get(Calendar.DAY_OF_MONTH);
                                  int mesrojo=now.get(Calendar.MONTH);
                                  int aniorojo=now.get(Calendar.YEAR);
                                  if(mYear<aniorojo){view.setTextColor(Color.RED);}
                                  if(mMonth<mesrojo && mYear<=aniorojo){view.setTextColor(Color.RED);}
                                  if(mMonth==mesrojo && mYear==aniorojo && Integer.valueOf(view.getText().toString())<=hoy){view.setTextColor(Color.RED);}
                                  
           	        	      
                      
                    
                        
                        String nmcomp=String.valueOf(mMonth+1);
                        if((mMonth+1)<10){nmcomp="0"+nmcomp;}
                        
                        String nycomp=String.valueOf(mYear);
                        
                        String nombretablausuario=MainActivity.correoglobal;
                 		nombretablausuario=nombretablausuario.replaceAll("\\.", "");
                 		nombretablausuario=nombretablausuario.replaceAll("@", "");
                 		
                 		ParseQuery<ParseObject> query = ParseQuery.getQuery(nombretablausuario); //query para buscar records de ese mes y año en orden ascendente
                 		
                 		query.whereEqualTo("mesdbp", nmcomp);
            	        query.whereEqualTo("aniodbp", nycomp);
              	        query.orderByAscending("diadbp");
                        
            	        query.findInBackground(new FindCallback<ParseObject>() {
            	            public void done(List<ParseObject> objects, ParseException e) {
            	                  	             	
            	           int i=0;
            	           
            	           while(i<objects.size()){
            	        	   
            	        	   if(objects.get(i).getString("lbdbp").length()>0)   //si se ha hecho lectura biblica
            	        	   
            	        	   {
            	        	   
            	        	               	        		   
            	        	   String obj=objects.get(i).getString("diadbp");
            	        	  
            	        	   if(Integer.valueOf(obj)<10){obj=obj.replaceAll("0", "");} //quitandole los 0's iniciales a dias PARSE
            	        	   
            	        	   if(obj.equals(view.getText().toString()) ){view.setTextColor(Color.rgb(47, 143, 54));}
            	        	   
            	        	   
            	        	   }
            	        		   
            	        	   
            	        	   i++;
            	           }
            	            
            	            }

							
            	        });
   
               	    
            	       
                            }
                
                            
                            
                            
                            
                               
              
                } else {
                        // next month
                        view.setHeight(mDayHeight);
                        view.setBackgroundColor(Color.rgb(255, 241, 231));
                        view.setTextColor(Color.rgb(221, 182, 175));
                        view.setClickable(true);
                        view.setTypeface(kepf);
                }
                return view;
        }
       
      

		@Override
        public int getCount() {
                return mItems.size();
        }
 
        @Override
        public Object getItem(int position) {
                return mItems.get(position);
        }
 
        @Override
        public long getItemId(int position) {
                return position;
        }
       
        
        
       
}