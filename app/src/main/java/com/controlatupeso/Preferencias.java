package com.controlatupeso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Preferencias extends PreferenceActivity {
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.preferences);
 
        
        Preference preComp = (Preference) findPreference("preCompartir");
        preComp.setOnPreferenceClickListener(new OnPreferenceClickListener(){
            public boolean onPreferenceClick(Preference preference)
            {
            	
		    	String texto;
		    	texto = getString(R.string.prin_comp_titulo)+"\n";
		    	texto = texto + getString(R.string.pre_compartir_apli)+"\n";
		    	texto = texto +"https://play.google.com/store/apps/details?id=com.controlatupeso";
		    	
			    Intent sendIntent = new Intent();
			    sendIntent.setAction(Intent.ACTION_SEND);
			    sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
			    sendIntent.setType("text/plain");
			    //sendIntent.setType("text/html");
			    startActivity(sendIntent);
            	
                return false;
            }
      });
        
    }
}