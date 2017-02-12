package com.controlatupeso;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevoPeso extends Activity
{

	private HistoricoDAO datasourceH;
	private ObjetivoDAO datasourceO;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevopeso);
		 
        Button btcontinuar = (Button) findViewById(R.id.continuar);
        
		btcontinuar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

    			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(NuevoPeso.this);
    			
            	Double dimc=0.0;
            	int dif;
            	
            	EditText peso = (EditText) findViewById(R.id.peso);
            	EditText observacion = (EditText) findViewById(R.id.descripcion);
            	
    			datasourceH = new HistoricoDAO(NuevoPeso.this);
    			datasourceH.open();
    			
    			datasourceO = new ObjetivoDAO(NuevoPeso.this);
    			datasourceO.open();
    			Objetivo obj = new Objetivo();
    			
    			obj = datasourceO.getProximoObjetivo(prefs.getInt("id_perfil",0));
    			
    			String objPeso="";
    			if (obj==null)
    			{
    			   objPeso = "---";
    			}
    			else
    			{
    				objPeso = obj.getPeso();
    			}
    			
    			datasourceO.close();
    			
    			PerfilDAO perdao = new PerfilDAO(NuevoPeso.this);
    	        perdao.open();
    	        Perfil per = perdao.getPerfil(prefs.getInt("id_perfil",0));
    	        perdao.close();
    	        
    	        final Util util = ((Util) getApplicationContext());
    	        
    	        dimc = util.calcularIMC(Float.parseFloat(peso.getText().toString()), per.getAltura());
    	        
    	        Historico his1 = null;
    			his1 = datasourceH.crearHistorico(prefs.getInt("id_perfil",0), 
    					                         Float.parseFloat(peso.getText().toString()),
    					                         dimc.toString());
    			
    			datasourceH.close();

    			
            	NuevoPeso.this.finish();
            }
        });
		
        Button btcancelar = (Button) findViewById(R.id.cancelar);
		
        btcancelar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
            	NuevoPeso.this.finish();
            }
        });
	}
	
}
