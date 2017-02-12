package com.controlatupeso;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarPerfil extends Activity
{

    private EditText alias;
	private EditText altura;
	private Button btnModificarPerfil;
	private Button btnCancelarPerfil;
	
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editarperfil);

		btnModificarPerfil = (Button) findViewById(R.id.guardar);
		btnCancelarPerfil = (Button) findViewById(R.id.cancelar);
		
        alias = (EditText) findViewById(R.id.alias);
        altura = (EditText) findViewById(R.id.altura);

        prefs = PreferenceManager.getDefaultSharedPreferences(EditarPerfil.this);
        
        PerfilDAO datasource = new PerfilDAO(this);
        datasource.open();
        
        Perfil per = new Perfil();
        
        per = datasource.getPerfil(prefs.getInt("id_perfil",0));
        
        datasource.close();
        
        alias.setText(per.getAlias());
        altura.setText(""+per.getAltura());

        btnModificarPerfil.setOnClickListener(new OnClickListener()
        {
		    @Override
		    public void onClick(View arg0) 
		    {

            	if (alias.getText().toString().length()==0||alias.getText().toString().equals(""))
            	{
            		Toast.makeText(EditarPerfil.this,getString(R.string.error_alias),Toast.LENGTH_LONG).show();
            	}
            	else
            	{
				        PerfilDAO datasource = new PerfilDAO(EditarPerfil.this);
				        datasource.open();
				        Perfil per = new Perfil();
				        per = datasource.getPerfil(prefs.getInt("id_perfil",0));
				        datasource.close();
		
				        per.setAlias(alias.getText().toString());
				        per.setAltura(Integer.parseInt(altura.getText().toString()));

				        PerfilDAO perdao = new PerfilDAO(EditarPerfil.this);
				        perdao.open();
				        perdao.ModificarPerfil(per);
				        perdao.close();
				        
				        EditarPerfil.this.finish();
            	}
		    }
        });
        
        btnCancelarPerfil.setOnClickListener(new OnClickListener()
        {
		    @Override
		    public void onClick(View arg0) 
		    {
		    	EditarPerfil.this.finish();
		    }
        });
        
	}
	
}

