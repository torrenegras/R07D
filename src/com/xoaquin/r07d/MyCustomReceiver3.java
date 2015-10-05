//RECEPTOR DE MENSAJES PUSH QUE ACCIONA LA APERTURA DE LA APP SI SE PRESIONA EN LA NOTIFICACION, NO SE SI FUNCIONA POR NO HABER COMENTADO CUANDO ERA

package com.xoaquin.r07d;

import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;



public class MyCustomReceiver3 extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {
        
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}