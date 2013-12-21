package com.xoaquin.r07d;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

//Moded Snippet de un genio para calificar APP

public class AppRater {
    private final static String APP_TITLE = "R07D";
    private final static String APP_PNAME = "com.xoaquin.r07d";
    
    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;
    
    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }
        
        SharedPreferences.Editor editor = prefs.edit();
        
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }
        
        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }
        
        editor.commit();
    }   
    
    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mContext);

       
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        Typeface kepf = Typeface.createFromAsset(mContext.getAssets(),"Kepler-Std-Black_26074.ttf");
        
        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(Color.rgb(255, 226, 216));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 30);
        EditText tv= new EditText (mContext);
        tv.setText("Si te gusta " + APP_TITLE + ", por favor tomate un segundo para calificarla. Gracias por el apoyo!\n\nCompártela!");
        tv.setTypeface(kepf);
        tv.setLayoutParams(params);
        tv.setTextColor(Color.rgb(141, 102, 95));
        tv.setKeyListener(null);
        ll.addView(tv);
              
               
        Button b1 = new Button(mContext);
        b1.setText("Calificar " + APP_TITLE);
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                dialog.dismiss();
            }
        });        
        b1.setTypeface(kepf);
        b1.setBackgroundColor(Color.rgb(141, 102, 95));
        b1.setLayoutParams(params);
        b1.setTextColor(Color.WHITE);
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Recordar mas adelante..");
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        b2.setTypeface(kepf);
        b2.setBackgroundColor(Color.rgb(141, 102, 95));
        b2.setLayoutParams(params);
        b2.setTextColor(Color.WHITE);
        ll.addView(b2);

        Button b3 = new Button(mContext);
        b3.setText("No, gracias");
        b3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        b3.setTypeface(kepf);
        b3.setBackgroundColor(Color.rgb(141, 102, 95));
        b3.setTextColor(Color.WHITE);
        ll.addView(b3);

        dialog.setContentView(ll);        
        dialog.show();        
    }
}