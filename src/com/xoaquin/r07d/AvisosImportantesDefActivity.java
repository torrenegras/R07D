package com.xoaquin.r07d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class AvisosImportantesDefActivity extends Activity {
	private static WebView wb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avisosimportantesdef_activity);
		
		 wb=(WebView) findViewById(R.id.webView1);
		 wb.loadUrl("http://avisor07d.torrenegra.co");
		 wb.getSettings().setJavaScriptEnabled(true);
         wb.setWebViewClient(new WebViewClient());
         wb.setInitialScale(230);
         
         wb.getSettings().setBuiltInZoomControls(true);
		
	}
}
