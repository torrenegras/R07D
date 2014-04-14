
//FRAGMENT DEVOCIONAL

package com.xoaquin.r07d;

import java.util.Calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;



public class DevocionalFragment extends Fragment {
	
	
	private static WebView wb;
	private static ProgressBar pb;
	
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
       
    	View V=inflater.inflate(R.layout.fragment_devocional, container, false);
    	
    	 
    	 wb=(WebView) V.findViewById(R.id.webView1);
    	 pb=(ProgressBar) getActivity().findViewById(R.id.progressBar1);
    	 
    	 Calendar cal = Calendar.getInstance(); 
    	 int nd= cal.get(Calendar.DAY_OF_YEAR);
    	    	 
    	 nd=1;              //**********************Quitar cuando tengamos material!!!!!!
    	 
    	 wb.setBackgroundColor(0x00000000);
    	 wb.loadUrl("http://r07d.parseapp.com/"+nd+".htm");
    	   	 
    	 pb.setVisibility(View.VISIBLE);
    	 
         wb.getSettings().setJavaScriptEnabled(true);
         wb.setWebViewClient(new WebViewClient());
         wb.setInitialScale(230);
         
         wb.getSettings().setBuiltInZoomControls(true);
         
        
         wb.setWebViewClient(new WebViewClient() {

        	   public void onPageFinished(WebView view, String url) {
        		   pb.setVisibility(View.GONE);
        	    }
        	});
         
         
        
    	 /*INTENT YOUVERSION NATIVO
       	   
    	 
    	 t1.setOnClickListener(new OnClickListener()
         {
             public void onClick(View v) 
             
             {
     	            
            	Intent i = new Intent();
            	i.setAction(Intent.ACTION_VIEW);
             	i.setData(Uri.parse("youversion://bible?reference=deu.6.6"));  
               	startActivity(i);

             
             }
     	            
     	        });
    	 
    	 
    	 */
         
         
    	 
        return V;
    }
    
    
   
   //FUNCION CONTROL BACK BUTTON PARA WEBVIEW 
	public int onBackPressed() {
	    
	    	if (wb.canGoBack()) {
	        wb.goBack();
	        return 0;
	    	}
	    	
	    	else
	    	{
	    		return 1;
	    	}
	
	  
	}
	
	 
    
    
}
