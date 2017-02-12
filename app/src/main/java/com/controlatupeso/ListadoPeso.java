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


public class ListadoPeso extends Activity {
	/** Called when the activity is first created. */
	private HistoricoDAO datasourceh;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadopeso);

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ListadoPeso.this);
        
        datasourceh = new HistoricoDAO(this);
        datasourceh.open();
        List<Historico> listadoHistoricos = datasourceh.getTodosHistoricos(prefs.getInt("id_perfil",0),"N");
       
        ArrayList<ItemHistorico> results = new ArrayList<ItemHistorico>();
        
        final Util util = ((Util) getApplicationContext());
        
        for (int i=0;i<listadoHistoricos.size();i++)
        {
        	ItemHistorico item_details = new ItemHistorico();
        	
        	if (listadoHistoricos.get(i).getFecha().equals(""))
        	{
        		item_details.setFecha(listadoHistoricos.get(i).getFecha());
        	}
        	else
        	{
        		//item_details.setFecha(util.Convertir_a_DDMMYYYY(listadoHistoricos.get(i).getFecha(), "/"));
        		item_details.setFecha(listadoHistoricos.get(i).getFecha());
        	}
        	
        	item_details.setPeso(listadoHistoricos.get(i).getPeso()+" Kg");
        	//item_details.setImc(listadoHistoricos.get(i).getImc()+" ("+util.TextoIMC(Double.parseDouble(listadoHistoricos.get(i).getImc()))+")");
        	if (listadoHistoricos.get(i).getPeso_objetivo().equals("---"))
        	{
        		item_details.setPeso_objetivo(listadoHistoricos.get(i).getPeso_objetivo());
        	}
        	else
        	{
        		item_details.setPeso_objetivo(listadoHistoricos.get(i).getPeso_objetivo()+" Kg");
        	}
        	
        	item_details.setComentario(listadoHistoricos.get(i).getComentario());
        	
        	results.add(item_details);
        }
        
        
        //lv1.setAdapter(new ItemListBaseAdapter(this, listadoHistoricos));
        datasourceh.close();
        
        final ListView lv1 = (ListView) findViewById(R.id.listView);
        lv1.setAdapter(new ItemHistoricoBaseAdapter(this, results));
    }

}
