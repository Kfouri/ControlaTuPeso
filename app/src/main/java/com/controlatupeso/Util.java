package com.controlatupeso;

import java.text.DecimalFormat;

import android.app.Application;

public class Util extends Application
{

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

	public Double calcularIMC(float peso,int altura)
	{

		double xaltura=0;
		String ximc="0";
		
        xaltura = altura/100.00;
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        ximc = df.format(peso/(xaltura*xaltura));
        
		return  Double.parseDouble(ximc.replace(",","."));
		
	}
	
	public int posIMC(int dimc)
	{

        if (dimc <= 14 )
        {
        	return 0;
        }
        else
        {
        	if (dimc >= 15 && dimc <= 17)
        	{
                return 1;
        	}
        	else
        	{
            	if (dimc >= 18 && dimc <= 26)
            	{
                    return 2;
            	}
            	else
            	{
                	if (dimc >= 27 && dimc <= 32)
                	{
                        return 3;
                	}
                	else
                	{
                    	if (dimc >= 33 && dimc <= 39)
                    	{
                            return 4;
                    	}
                    	else
                    	{
                        	if (dimc >= 40)
                        	{
                                return 5;
                        	}
                    	}
                	}
            	}
        	}
        }
        return 0;
	}
	
	public String Convertir_a_DDMMYYYY(String p_fecha,String p_separador)
	{
		DecimalFormat formateador = new DecimalFormat("00");
		
        String strfecha = p_fecha;
        String xanio = strfecha.substring(0,strfecha.indexOf("-"));
        String xmes = formateador.format(Integer.parseInt(strfecha.substring(strfecha.indexOf("-")+1, strfecha.indexOf("-", strfecha.indexOf("-")+1))));
        String xdia = formateador.format(Integer.parseInt(strfecha.substring(strfecha.indexOf("-", strfecha.indexOf("-")+1)+1,10)));
        String nuevafecha = xdia+p_separador+xmes+p_separador+xanio.substring(0,4);
        String hora = p_fecha.substring(11,19);
        return nuevafecha+" "+hora;
		
	}
	
	public String Convertir_a_YYYYMMDD(String p_fecha,String p_separador)
	{
		DecimalFormat formateador = new DecimalFormat("00");
		
        String strfecha = p_fecha;
        String xdia = formateador.format(Integer.parseInt(strfecha.substring(0,strfecha.indexOf("-"))));
        String xmes = formateador.format(Integer.parseInt(strfecha.substring(strfecha.indexOf("-")+1, strfecha.indexOf("-", strfecha.indexOf("-")+1))));
        String xanio = strfecha.substring(strfecha.indexOf("-", strfecha.indexOf("-")+1)+1,10);
        String nuevafecha = xanio.substring(0,4)+p_separador+xmes+p_separador+xdia;
        String hora = p_fecha.substring(11,19);
        return nuevafecha+" "+hora;

	}
}