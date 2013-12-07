package com.xoaquin.r07d;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class DevocionalFragment extends Fragment {
	
	private static TextView tt,ttx,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
       
    	View V=inflater.inflate(R.layout.fragment_devocional, container, false);
  	
    	 tt=(TextView) V.findViewById(R.id.textViewt);
    	 ttx=(TextView) V.findViewById(R.id.textViewtxt);
    	 t1=(TextView) V.findViewById(R.id.textViewv1);
    	 t2=(TextView) V.findViewById(R.id.textViewv2);
    	 t3=(TextView) V.findViewById(R.id.textViewv3);
    	 t4=(TextView) V.findViewById(R.id.textViewv4);
    	 t5=(TextView) V.findViewById(R.id.textViewv5);
    	 t6=(TextView) V.findViewById(R.id.textViewv6);
    	 t7=(TextView) V.findViewById(R.id.textViewv7);
    	 t8=(TextView) V.findViewById(R.id.textViewv8);
    	 t9=(TextView) V.findViewById(R.id.textViewv9);
    	 t10=(TextView) V.findViewById(R.id.textViewv10);
    	 
    	 Typeface kepf = Typeface.createFromAsset(V.getContext().getAssets(),"Kepler-Std-Black_26074.ttf");
    	 
    	 tt.setTypeface(kepf);
    	 
    	 
    	 tt.setText("La Biblia es el día a día");
    	 ttx.setText("Una vida cristiana fiel a Cristo no depende de respetar escrupulosamente ritos o costumbres religiosas,"
    	 		+ "sino que esta arraigada en la relacion viva y personal que cada cristiano debe tener con su Salvador"
    	 		+ ",con su Dios.Una de las bases de esta comunion es el conocimiento de la voluntad de Dios revelada mediante"
    	 		+ "su Palabra, la Biblia. La otra que experimentamos cada dia es la oración.\nLos versiculos de hoy muestran"
    	 		+ "el lugar que la Palabra de Dios debe ocupar en la vida cotidiana de un padre de familia creyente:\n"
    	 		+ "En primer lugar debe estar en su corazon, directamente ligada a su vida afectiva, fuente de su energia interior.\n"
    	 		+ "Luego debe estar en el centro de sus conversaciones con cada uno de los suyos a lo largo de sus ocupaciones diarias.\n"
    	 		+ "Tambien tiene que estar en sus pensamientos cuando trabaja(las manos).\nAdemas, en el mundo todo tipo de cosas se "
    	 		+ "presentan ante sus ojos:buenas obras que puede hacer o tentaciones de las cuales debe huir.La Palabra de Dios lo"
    	 		+ "orientara para tener la actitud correcta.\nPor ultimo, el hogar de un jefe de familia arraigado profundamente a las "
    	 		+ "Santas Escrituras sera un testimonio, bajo la luz y el amor divinos, para todos los que entren en su casa.\n"
    	 		+ "Lampara es a mis pies tu palabra, y lumbrera a mi camino (Salmo 119:105)");
    	 
    	 
    	 
    	 
    	 String linkText = "<a href='http://bible.com/127/deu.6.6-8.ntv'>Deuteronomio 6:6-8</a>";
         t1.setText(Html.fromHtml(linkText));
         t1.setMovementMethod(LinkMovementMethod.getInstance());
            	 
    	 
    	 t2.setText("Eclesiastés 2:12");
    	 t3.setText("Eclesiastés 3:12");
    	 t4.setText("Santiago 5");
    	 t5.setText("Salmo 138:6-8");
    	 t6.setText("Proverbios 29:9-10");
    	 t7.setText("");
    	 t8.setText("");
    	 t9.setText("");
    	 t10.setText("");
    	 
    	 
    	 
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
    
   
    
    
    
    
}
