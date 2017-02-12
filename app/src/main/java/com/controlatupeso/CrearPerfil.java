package com.controlatupeso;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.controlatupeso.nuevo.MainActivity;


public class CrearPerfil extends Activity 
{

    private PerfilDAO datasource;
    private HistoricoDAO datasourceH;
	
    private EditText alias;
	private EditText altura;
	private EditText peso;
	private EditText edad;
	private Spinner sexo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crearperfil);

        alias = (EditText) findViewById(R.id.alias);
        altura = (EditText) findViewById(R.id.altura);
        peso = (EditText) findViewById(R.id.peso);

		Bundle b = getIntent().getExtras();
		final String xPerfil = b.getString("perfil");
		
        Button btguardar = (Button) findViewById(R.id.guardar);
		
		btguardar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
            	if (alias.getText().toString().length()==0||alias.getText().toString().equals(""))
            	{
            		Toast.makeText(CrearPerfil.this,getString(R.string.error_alias),Toast.LENGTH_LONG).show();
            	}
            	else
            	{
            		if (altura.getText().toString().length()==0||altura.getText().toString().equals("0"))
	            	{
	            		Toast.makeText(CrearPerfil.this,getString(R.string.error_altura),Toast.LENGTH_LONG).show();
	            	}
	            	else
	            	{
	            		if (peso.getText().toString().length()==0||peso.getText().toString().equals("0"))
	            		{
	            			Toast.makeText(CrearPerfil.this,getString(R.string.error_peso),Toast.LENGTH_LONG).show();
	            		}
	            		else
	            		{
	                			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrearPerfil.this);
	                			SharedPreferences.Editor editor = preferences.edit();

	                			datasource = new PerfilDAO(CrearPerfil.this);
	                			datasource.open();

	                			Perfil per = null;
	                			per = datasource.crearPerfil(alias.getText().toString(),
	                					                     Integer.parseInt(altura.getText().toString()),
	                					                     Integer.parseInt(peso.getText().toString()),
	                					                     0,
	                					                     "",
	                					                     "SE");

	                			datasource.close();

	                			
	                	        final Util util = ((Util) getApplicationContext());
	                	        Double dimc=0.0;
	                	        
	                	        dimc = util.calcularIMC(Float.parseFloat(peso.getText().toString()), Integer.parseInt(altura.getText().toString()));
	                	        
	                	        
	                			datasourceH = new HistoricoDAO(CrearPerfil.this);
	                			datasourceH.open();
	                			
	                			Historico his = null;
	                			his = datasourceH.crearHistorico(per.getId(),
	                					                         Float.parseFloat(peso.getText().toString()),
	                					                         dimc.toString());
	                			
	                			datasourceH.close();

                                editor.putInt("id_perfil", per.getId());

	                			if (xPerfil.equals("1"))
	                			{
	                				editor.putString("alias1", alias.getText().toString());
	                				editor.putInt("id1", per.getId());
	                			}
	                			else
	                			{
			                		if (xPerfil.equals("2"))
			                		{
			                			editor.putString("alias2", alias.getText().toString());
			                			editor.putInt("id2", per.getId());
			                		}
			                		else
		                			{
				                		if (xPerfil.equals("3"))
				                		{
				                			editor.putString("alias3", alias.getText().toString());
				                			editor.putInt("id3", per.getId());
				                		}
		                			}
	                			}
	                				                			
	                			editor.commit();
	                			
								Intent intent = new Intent(CrearPerfil.this,MainActivity.class);
								startActivity(intent);
								CrearPerfil.this.finish();
	            		}
	            	}
            	}
            }
          
        });
	}

	  @Override
	  protected void onResume() {
	    //datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    //datasource.close();
	    super.onPause();
	  }
}
