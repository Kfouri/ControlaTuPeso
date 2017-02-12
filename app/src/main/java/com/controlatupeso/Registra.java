package com.controlatupeso;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.util.List;



public class Registra extends Activity
{

	private HistoricoDAO datasourceh;
	private PerfilDAO perdao;
	private TextView pesoactual;
	private TextView pesofecha;
	private TextView imcactual;
	private TextView imcactual_texto;
	private TextView objetivo_actual;
	private TextView fecha_objetivo_actual;
	
	
	private ListView lv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registra);

		pesoactual = (TextView) findViewById(R.id.peso_actual);
		pesofecha = (TextView) findViewById(R.id.peso_fecha);
		imcactual = (TextView) findViewById(R.id.imc_actual);
		imcactual_texto = (TextView) findViewById(R.id.imc_actual_texto);
		
		objetivo_actual = (TextView) findViewById(R.id.objetivo_actual);
		fecha_objetivo_actual = (TextView) findViewById(R.id.objetivo_fecha);
		
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


        
        lv1 = (ListView) findViewById(R.id.listView1);
                
        Button btregistrarpeso = (Button) findViewById(R.id.registrarpeso);
        
        btregistrarpeso.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
				Intent intent = new Intent(Registra.this,NuevoPeso.class);
				startActivity(intent);
            }
        
        });
        
        Button btregistrarobjetivo = (Button) findViewById(R.id.registrarobjetivo);
        
        btregistrarobjetivo.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
				Intent intent = new Intent(Registra.this,NuevoObjetivo.class);
				startActivity(intent);
            }
        
        });
	}

	@Override
	protected void onResume() 
	{
	    super.onResume();

		double xaltura=0;
		String ximc="0";
		double dimc;
		
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Registra.this);

        datasourceh = new HistoricoDAO(this);
        datasourceh.open();
        
        Historico his = datasourceh.getUltimoHistoricos(prefs.getInt("id_perfil",0));
        pesoactual.setText(""+his.getPeso());
        String fechatemp = datasourceh.getUltimaFecha(prefs.getInt("id_perfil",0));
        
        final Util util = ((Util) getApplicationContext());
                
        String fechaTempTemp = util.Convertir_a_DDMMYYYY(fechatemp,"/");
        pesofecha.setText(""+fechaTempTemp.substring(0, 10));
        
        datasourceh.close();

    
        ObjetivoDAO datasourceo = new ObjetivoDAO(this);
        datasourceo.open();
        Objetivo obj = datasourceo.getProximoObjetivo(prefs.getInt("id_perfil",0));
        
        if (obj==null)
        {
            objetivo_actual.setText("");
            fecha_objetivo_actual.setText("");
        }
        else
        {
           
           objetivo_actual.setText(""+obj.getPeso());
           
//	       String strfecha = obj.getFecha();
//	       String xanio = strfecha.substring(0,strfecha.indexOf("-"));
//	       String xmes = strfecha.substring(strfecha.indexOf("-")+1, strfecha.indexOf("-", strfecha.indexOf("-")+1));
//	       String xdia = strfecha.substring(strfecha.indexOf("-", strfecha.indexOf("-")+1)+1);
//	       String nuevafecha = xanio.substring(0,4)+"-"+xmes+"-"+xdia;
        	        
           //fecha_objetivo_actual.setText(nuevafecha);
           String feobe = util.Convertir_a_DDMMYYYY(obj.getFecha()+" 00:00:00","/");
	       fecha_objetivo_actual.setText(feobe.substring(0,10));
        }
        datasourceo.close();
        

        perdao = new PerfilDAO(this);
        perdao.open();
        Perfil per = perdao.getPerfil(prefs.getInt("id_perfil",0));
        perdao.close();
                        
        dimc = util.calcularIMC(Float.parseFloat(his.getPeso()), per.getAltura());
        
        //imcactual_texto.setText(util.TextoIMC(dimc));
        
        DecimalFormat formateador = new DecimalFormat("00.00");
        		
        imcactual.setText(""+formateador.format (dimc));
        
        datasourceh = new HistoricoDAO(this);
        datasourceh.open();
        List<Historico> listadoHistoricos = datasourceh.getTodosHistoricos(per.getId(),"S");

        lv1.setAdapter(new ItemListBaseAdapter(this, listadoHistoricos));
        //Toast.makeText(MenuPrincipal.this, "Cnt historico "+listadoHistoricos.size(),Toast.LENGTH_LONG).show();
        datasourceh.close();
	}
}
