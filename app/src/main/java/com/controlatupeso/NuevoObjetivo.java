package com.controlatupeso;


import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class NuevoObjetivo extends Activity
{

	private TextView mDateDisplay;
	private Button mPickDate;
	static final int DATE_DIALOG_ID = 0;

	private int mYear;
	private int mMonth;
	private int mDay;

	private ObjetivoDAO datasourceO;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevo_objetivo);
		mDateDisplay = (TextView) findViewById(R.id.fecha);
		mPickDate = (Button) findViewById(R.id.set);
		
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });		

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        updateDisplay();
        
        
        
        Button btcancelar = (Button) findViewById(R.id.cancelar);
		
        btcancelar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
            	NuevoObjetivo.this.finish();
            }
        });
        

        
        Button btcontinuar = (Button) findViewById(R.id.continuar);
        
		btcontinuar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

    			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(NuevoObjetivo.this);
    			
            	Double dimc=0.0;
            	int dif;
            	
            	EditText peso = (EditText) findViewById(R.id.peso);
            	EditText fecha = (EditText) findViewById(R.id.fecha);
            	
    			datasourceO = new ObjetivoDAO(NuevoObjetivo.this);
    			datasourceO.open();
    			
    	        
    	        Objetivo obj = null;
    	        
    	        String strfecha = fecha.getText().toString();
    	        String xdia = strfecha.substring(0,strfecha.indexOf("-"));
    	        String xmes = strfecha.substring(strfecha.indexOf("-")+1, strfecha.indexOf("-", strfecha.indexOf("-")+1));
    	        String xanio = strfecha.substring(strfecha.indexOf("-", strfecha.indexOf("-")+1)+1);
    	        String nuevafecha = xanio.substring(0,4)+"-"+xmes+"-"+xdia;
    	           	        
    	        
    			obj = datasourceO.crearObjetivo(prefs.getInt("id_perfil",0), 
    					                         Integer.parseInt(peso.getText().toString()),
    					                         nuevafecha);
    			
    			datasourceO.close();

    			
            	NuevoObjetivo.this.finish();
            }
        });
		
	}
	
    private void updateDisplay() {
    	
    	DecimalFormat formateador = new DecimalFormat("00");
    	
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(formateador.format (mDay)).append("-")        
                    .append(formateador.format (mMonth + 1)).append("-")
                    .append(mYear).append(" "));
    }
    
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
            
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DATE_DIALOG_ID:
	        return new DatePickerDialog(this,
	                    mDateSetListener,
	                    mYear, mMonth, mDay);
	    }
	    return null;
	}
}
