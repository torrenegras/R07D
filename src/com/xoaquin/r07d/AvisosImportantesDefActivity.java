
//ACTIVIDAD DE WEBVIEW PARA NOTIFICAR AVISO IMPORTANTE POR PUSH


package com.xoaquin.r07d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class AvisosImportantesDefActivity extends Activity {
	private static WebView wb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avisosimportantesdef_activity);
		
		//NOTIFICACION EN AREA DE NOTIFICACIONES
				Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				
				NotificationCompat.Builder mBuilder =
				     new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(getString(R.string.app_name))
				.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.impnot)))
				.setAutoCancel(false)
				.setSound(alarmSound)
				.setContentText(getString(R.string.impnot));
				
				Intent resultIntent = new Intent(this, AvisosImportantesDefActivity.class);
				resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				
				PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
				mBuilder.setContentIntent(resultPendingIntent);
				
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.notify(1, mBuilder.build());
				
		 wb=(WebView) findViewById(R.id.webView1);
		 wb.loadUrl("http://avisor07d.torrenegra.co");
		 wb.getSettings().setJavaScriptEnabled(true);
		 wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
         wb.setWebViewClient(new WebViewClient());
         wb.setInitialScale(230);
         
         wb.getSettings().setBuiltInZoomControls(true);
		
	}
}
