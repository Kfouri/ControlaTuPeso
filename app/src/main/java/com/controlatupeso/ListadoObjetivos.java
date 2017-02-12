package com.controlatupeso;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class ListadoObjetivos extends Activity {
	/** Called when the activity is first created. */
	private ObjetivoDAO datasourceh;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadoobjetivos);

        if (getString(R.string.version).equals("LITE"))
        {
            AdView adView;
		    // Create an ad.
		    adView = new AdView(this);
		    adView.setAdSize(AdSize.BANNER);
		    adView.setAdUnitId("ca-app-pub-8178489486911148/2323256312");

		    // Add the AdView to the view hierarchy. The view will have no size
		    // until the ad is loaded.
		    RelativeLayout layout = (RelativeLayout) findViewById(R.id.ad);
		    layout.setGravity(Gravity.CENTER_HORIZONTAL);
		    layout.addView(adView);
		    
		    // Create an ad request. Check logcat output for the hashed device ID to
		    // get test ads on a physical device.
		    AdRequest adRequest = new AdRequest.Builder()
		        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		        .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
		        .build();

		    // Start loading the ad in the background.
		    adView.loadAd(adRequest);
        }

                
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ListadoObjetivos.this);
        
        datasourceh = new ObjetivoDAO(this);
        datasourceh.open();
        List<Objetivo> listadoObjetivos = datasourceh.getTodosObjetivo(prefs.getInt("id_perfil",0));
        
        ArrayList<ItemObjetivo> results = new ArrayList<ItemObjetivo>();
        
        final Util util = ((Util) getApplicationContext());
        
        String xfecha;
        String xpeso;
        String xpesoreal;
        
        for (int i=0;i<listadoObjetivos.size();i++)
        {
        	ItemObjetivo item_details = new ItemObjetivo();
        	xfecha = util.Convertir_a_DDMMYYYY(listadoObjetivos.get(i).getFecha()+" 00:00:00","/");
        	item_details.setFecha(xfecha.substring(0,10));
        	
        	xpeso=listadoObjetivos.get(i).getPeso();
        	item_details.setPeso(xpeso+" Kg");

        	xpesoreal=datasourceh.getPesoFecha(prefs.getInt("id_perfil",0),listadoObjetivos.get(i).getFecha());
        	item_details.setPesoReal(xpesoreal+" Kg");
        	
        	if (Integer.parseInt(xpeso)>Integer.parseInt(xpesoreal))
        	{
        		item_details.setEstado("Conseguido");
        	}
        	else
        	{
        		item_details.setEstado("No Conseguido");
        	}
        	
        	results.add(item_details);
        }
        
        
        //lv1.setAdapter(new ItemListBaseAdapter(this, listadoHistoricos));
        datasourceh.close();
        
        final ListView lv1 = (ListView) findViewById(R.id.listView);
        lv1.setAdapter(new ItemObjetivoBaseAdapter(this, results));
    }
    
}

