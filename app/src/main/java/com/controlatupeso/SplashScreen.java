package com.controlatupeso;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class SplashScreen extends Activity 
{
	
   private final int SPLASH_TIME = 4000;
   
   private TextView xVersion;
   private TextView xFraseTitulo;
   private TextView xFrase;
   private TextView xNombreApp;
   
   @Override
   public void onCreate(Bundle icicle) 
   {
      super.onCreate(icicle);
      setContentView(R.layout.splash_layout);
      int resultado;
            
      xVersion = (TextView) findViewById(R.id.version);
      xNombreApp = (TextView) findViewById(R.id.nombre_app);

	  PackageInfo packageInfo = null;

	  try 
	  {
			packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
	  } 
	  catch (NameNotFoundException e) 
	  {
			e.printStackTrace();
	  }
		
	  xNombreApp.setText(getString(R.string.name_app));
      xVersion.setText("V "+packageInfo.versionName);

      new Handler().postDelayed(new Runnable()
      {
         @Override
         public void run() 
         {
            Intent miIntent = new Intent(SplashScreen.this,ListadoPerfiles.class);
            SplashScreen.this.startActivity(miIntent);
            SplashScreen.this.finish();
         }
       }, SPLASH_TIME);
   }
}
 