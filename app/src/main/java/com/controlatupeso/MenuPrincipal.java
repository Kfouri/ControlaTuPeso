package com.controlatupeso;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MenuPrincipal extends Activity {
	
	private static final int RESULT_SETTINGS = 1;
	
	private PerfilDAO datasource;
	private HistoricoDAO datasourceh;

	private InterstitialAd interstitial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuprincipal);
		       
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
		    
		    
		    // Create the interstitial.
		    interstitial = new InterstitialAd(this);
		    interstitial.setAdUnitId("ca-app-pub-8178489486911148/5018061510");

		    // Create ad request.
		    AdRequest adRequest2 = new AdRequest.Builder().build();

		    // Begin loading your interstitial.
		    interstitial.loadAd(adRequest2);
		    
		    
		   
        }
        
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this); 
				
        datasource = new PerfilDAO(this);
        datasource.open();

        Perfil per = new Perfil();
        
       
        if (prefs.getString("perfil","").equals("1"))
        {
        	per = datasource.getPerfil(prefs.getInt("id1",0));        	
        }
        else
        {
        	if (prefs.getString("perfil","").equals("2"))
            {
            	per = datasource.getPerfil(prefs.getInt("id2",0));        	
            }
            else
            {
            	if (prefs.getString("perfil","").equals("3"))
                {
                	per = datasource.getPerfil(prefs.getInt("id3",0));        	
                }
            }
        }
        
        
        datasource.close();


//        datasourceh = new HistoricoDAO(this);
//        datasourceh.open();
//        List<Historico> listadoHistoricos = datasourceh.getTodosHistoricos(per.getId());
//        datasourceh.close();
        
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("id_perfil", per.getId());
        editor.commit();
        
		ImageView img = (ImageView) findViewById(R.id.imagePrincipal);
		img.setOnClickListener(new OnClickListener() 
		{
		    public void onClick(View v) 
		    {
		    	displayInterstitial();
		    	Intent intent = new Intent(MenuPrincipal.this,Registra.class);
				
				startActivity(intent);

		    }
		});
		
		ImageView img2 = (ImageView) findViewById(R.id.ImageGrafico);
		img2.setOnClickListener(new OnClickListener() 
		{
		    public void onClick(View v) 
		    {

		    	displayInterstitial();
				Intent intent1 = new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pub:MAKP+Team"));
				startActivity(intent1);
						
		    }
		});
		
		

		ImageView img3 = (ImageView) findViewById(R.id.ImageLogros);
		img3.setOnClickListener(new OnClickListener() 
		{
		    public void onClick(View v) 
		    {
		    	displayInterstitial();
		    	Intent intent = new Intent(MenuPrincipal.this,ListadoObjetivos.class);
				startActivity(intent);
				
		    }
		});
		
		ImageView img4 = (ImageView) findViewById(R.id.ImagePreferencias);
		img4.setOnClickListener(new OnClickListener() 
		{
		    public void onClick(View v) 
		    {
		    	displayInterstitial();
		    	Intent i = new Intent(MenuPrincipal.this, Preferencias.class);
		        startActivityForResult(i, RESULT_SETTINGS);

		    }
		});

		
		ImageView img5 = (ImageView) findViewById(R.id.ImagePerfil);
		img5.setOnClickListener(new OnClickListener() 
		{
		    public void onClick(View v) 
		    {
		    	displayInterstitial();
		    	Intent i = new Intent(MenuPrincipal.this, EditarPerfil.class);
		        startActivity(i);

		    }
		});
		

		ImageView img6 = (ImageView) findViewById(R.id.historico);
		img6.setOnClickListener(new OnClickListener() 
		{
		    public void onClick(View v) 
		    {
		    	displayInterstitial();
		    	Intent i = new Intent(MenuPrincipal.this, ListadoPeso.class);
		        startActivity(i);

		    }
		});
		
		
		AppRater.app_launched(this);
	}

	  // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

}
