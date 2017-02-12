package com.controlatupeso;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.controlatupeso.nuevo.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class ListadoPerfiles extends Activity  
{

	SharedPreferences.Editor editor;
	SharedPreferences prefs;

    Button btPerfil1;
    Button btPerfil2;
    Button btPerfil3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listadoperfiles);
		
		btPerfil1 = (Button) findViewById(R.id.btnperfil1);
		btPerfil2 = (Button) findViewById(R.id.btnperfil2);
		btPerfil3 = (Button) findViewById(R.id.btnperfil3);

		PerfilDAO datasource = new PerfilDAO(ListadoPerfiles.this);
        datasource.open();
        Perfil per1 = new Perfil();
        Perfil per2 = new Perfil();
        Perfil per3 = new Perfil();
        per1 = datasource.getPerfil(1);
        per2 = datasource.getPerfil(2);
        per3 = datasource.getPerfil(3);
        datasource.close();

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ListadoPerfiles.this);
		editor = preferences.edit();

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
        
		prefs = PreferenceManager.getDefaultSharedPreferences(ListadoPerfiles.this);
		
		if (per1 == null)
		{
			btPerfil1.setText(getString(R.string.crear_perfil));
		}
		else
		{
			btPerfil1.setText(per1.getAlias());
		}

		if (per2 == null)
		{
			btPerfil2.setText(getString(R.string.crear_perfil));
		}
		else
		{
			btPerfil2.setText(per2.getAlias());
		}

		if (per3 == null)
		{
			btPerfil3.setText(getString(R.string.crear_perfil));
		}
		else
		{
			btPerfil3.setText(per3.getAlias());
		}

        btPerfil1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
        		editor.putString("perfil", "1");
                editor.putInt("id_perfil", 1);
        		editor.commit();
        		Intent miIntent = null;

        		if (prefs.getString("alias1","").equals("") || btPerfil1.getText().equals(getString(R.string.crear_perfil)))
        		{
        			miIntent = new Intent(ListadoPerfiles.this,CrearPerfil.class);
        		}
        		else
        		{
        			miIntent = new Intent(ListadoPerfiles.this,MainActivity.class);
        		}

     	        Bundle b = new Bundle();
     	        b.putString("perfil", "1"); 
     	        miIntent.putExtras(b);
                ListadoPerfiles.this.startActivity(miIntent);
                ListadoPerfiles.this.finish();
            }
          
        });
		

        btPerfil2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
            	Intent miIntent = null;

            	   editor.putString("perfil", "2");
                editor.putInt("id_perfil", 2);
            	   editor.commit();

                   if (prefs.getString("alias2","").equals("") || btPerfil2.getText().equals(getString(R.string.crear_perfil)))
           		   {
           		      miIntent = new Intent(ListadoPerfiles.this,CrearPerfil.class);
           		   }
           		   else
           		   {
           		      miIntent = new Intent(ListadoPerfiles.this,MainActivity.class);
           		   }

         	       Bundle b = new Bundle();
         	       b.putString("perfil", "2"); 
         	       miIntent.putExtras(b);
                   ListadoPerfiles.this.startActivity(miIntent);
                   ListadoPerfiles.this.finish();

            }
          
        });
        
        btPerfil3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
            	Intent miIntent;
             	   editor.putString("perfil", "3");
                   editor.putInt("id_perfil", 3);
              	   editor.commit();

                   if (prefs.getString("alias3","").equals("") || btPerfil3.getText().equals(getString(R.string.crear_perfil)))
           		   {
           		      miIntent = new Intent(ListadoPerfiles.this,CrearPerfil.class);
           		   }
           		   else
           		   {
           			   
           		      miIntent = new Intent(ListadoPerfiles.this,MainActivity.class);
           		   }

           		   Bundle b = new Bundle();
         	       b.putString("perfil", "3"); 
         	       miIntent.putExtras(b);
         	      
                   ListadoPerfiles.this.startActivity(miIntent);
                   ListadoPerfiles.this.finish();

            }
          
        });
	}

}
