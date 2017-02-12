package com.controlatupeso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class HistoricoDAO {

	  private SQLiteDatabase database;
	  private BaseDatos dbHelper;
	  private String[] allColumns = {BaseDatos.COLUMNA_HIST_ID, BaseDatos.COLUMNA_HIST_ID_PERFIL,BaseDatos.COLUMNA_HIST_PESO,BaseDatos.COLUMNA_HIST_FECHA,BaseDatos.COLUMNA_HIST_COMENTARIO,BaseDatos.COLUMNA_HIST_IMC,BaseDatos.COLUMNA_HIST_DIF,BaseDatos.COLUMNA_HIST_OBJETIVO};

	  public HistoricoDAO(Context context) 
	  {
	    dbHelper = new BaseDatos(context);
	  }

	  public void open() throws SQLException 
	  {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() 
	  {
	    dbHelper.close();
	  }
	  
	  public Historico crearHistorico(int id_perfil,float peso,String imc)
	  {
		  
		Date hoy = new Date();
		DateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	    ContentValues values = new ContentValues();
	    values.put(BaseDatos.COLUMNA_HIST_ID_PERFIL, id_perfil);
	    values.put(BaseDatos.COLUMNA_HIST_PESO,peso);
	    values.put(BaseDatos.COLUMNA_HIST_FECHA,hourFormat.format(hoy));
	    values.put(BaseDatos.COLUMNA_HIST_COMENTARIO,"");
	    values.put(BaseDatos.COLUMNA_HIST_IMC,imc);
	    values.put(BaseDatos.COLUMNA_HIST_DIF,0);
	    values.put(BaseDatos.COLUMNA_HIST_OBJETIVO,"");
	    	    
	    long insertId = database.insert(BaseDatos.TABLA_HISTORICO, null, values);
	    
	    Cursor cursor = database.query(BaseDatos.TABLA_HISTORICO,
	                                  allColumns, 
	                                  BaseDatos.COLUMNA_HIST_ID + " = " + insertId,
	                                  null,
	                                  null, 
	                                  null, 
	                                  null);
	    cursor.moveToFirst();
	    Historico newHistorico = cursorToHistorico(cursor);
	    cursor.close();
	    return newHistorico;
	  }

	  public Historico crearHistoricoPrueba(int id_perfil,int peso,String comentario,String imc,int dif,String peso_objetivo,Date hoy) 
	  {
		  
		//Date hoy = new Date();
		DateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	    ContentValues values = new ContentValues();
	    values.put(BaseDatos.COLUMNA_HIST_ID_PERFIL, id_perfil);
	    values.put(BaseDatos.COLUMNA_HIST_PESO,peso);
	    values.put(BaseDatos.COLUMNA_HIST_FECHA,hourFormat.format(hoy));
	    values.put(BaseDatos.COLUMNA_HIST_COMENTARIO,comentario);
	    values.put(BaseDatos.COLUMNA_HIST_IMC,imc);
	    values.put(BaseDatos.COLUMNA_HIST_DIF,dif);
	    values.put(BaseDatos.COLUMNA_HIST_OBJETIVO,peso_objetivo);
	    	    
	    long insertId = database.insert(BaseDatos.TABLA_HISTORICO, null, values);
	    
	    Cursor cursor = database.query(BaseDatos.TABLA_HISTORICO,
	                                  allColumns, 
	                                  BaseDatos.COLUMNA_HIST_ID + " = " + insertId,
	                                  null,
	                                  null, 
	                                  null, 
	                                  null);
	    cursor.moveToFirst();
	    Historico newHistorico = cursorToHistorico(cursor);
	    cursor.close();
	    return newHistorico;
	  }
	  
	  public List<Historico> getTodosHistoricos(int id_perfil,String conTitulo) 
	  {
	    List<Historico> historicos = new ArrayList<Historico>();

		String mysql="select id,id_perfil,peso,strftime('%d/%m/%Y %H:%M:%S',fecha) fecha,comentario,imc,dif,peso_objetivo from historico where id_perfil="+id_perfil+" order by fecha";
		
		Cursor cursor = database.rawQuery(mysql, null);
		
	    if (conTitulo.equals("S"))
	    {
	        Historico his2 = new Historico();
	        his2.setFecha("Fecha de Registro");
	        his2.setPeso("   Peso");
	        his2.setImc(" IMC");
	        his2.setDif(1);
	        his2.setPeso_objetivo("Objetivo");
	        historicos.add(his2);
	    }
	    
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Historico his = cursorToHistorico(cursor);
	      historicos.add(his);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return historicos;
	  }

	  public List<Historico> getTodosHistoricosGrafico(int id_perfil) 
	  {
	    List<Historico> historicos = new ArrayList<Historico>();

		String mysql="select max(peso),fecha "
				   + "from (select id,id_perfil,peso,strftime('%d-%m-%Y',fecha) fecha,comentario,imc,dif,peso_objetivo "
				         + "from historico "
				         + "where id_perfil="+id_perfil+") "
				   + "group by fecha "
				   + "order by fecha";
		
		Cursor cursor = database.rawQuery(mysql, null);
	    
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Historico his = cursorToHistoricoGrafico(cursor);
	      historicos.add(his);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return historicos;
	  }
	 
	  public List<Historico> getTodosHistoricosGraficoObjetivo(int id_perfil) 
	  {
	    List<Historico> historicos = new ArrayList<Historico>();

		String mysql="select max(peso_objetivo),fecha "
				   + "from (select id,id_perfil,peso,strftime('%d-%m-%Y',fecha) fecha,comentario,imc,dif,peso_objetivo "
				         + "from historico "
				         + "where id_perfil="+id_perfil+") "
				   + "group by fecha "
				   + "order by fecha desc";
		
		Cursor cursor = database.rawQuery(mysql, null);
	    
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Historico his = cursorToHistoricoGraficoObjetivo(cursor);
	      historicos.add(his);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return historicos;
	  }
	  
	  public Historico getUltimoHistoricos(int id_perfil) 
	  {
		
		Historico his = null;
		Cursor cursor = database.query(BaseDatos.TABLA_HISTORICO,
	                                   allColumns, 
	                                   BaseDatos.COLUMNA_HIST_ID + " = (select max(id) from historico where id_perfil="+id_perfil+")", 
	                                   null, 
	                                   null, 
	                                   null, 
	                                   null);
		
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      his = cursorToHistorico(cursor);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return his;
	  }

	  public int getMaximoPeso(int id_perfil) 
	  {
		
		String mysql="select max(peso) from historico where id_perfil="+id_perfil;
		
		Cursor cursor = database.rawQuery(mysql, null);
		int sal=0;

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      sal = cursor.getInt(0);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return sal;
	  }

	  public int getMinipoPeso(int id_perfil) 
	  {
		
		String mysql="select min(peso) from historico where id_perfil="+id_perfil;
		
		Cursor cursor = database.rawQuery(mysql, null);
		int sal=0;

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      sal = cursor.getInt(0);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return sal;
	  }

	  public List<double[]> getTodosHistoricosPrueba(int id_perfil) 
	  {
		List<double[]> historicos = new ArrayList<double[]>();
		  
	    String mysql="select peso from historico where id_perfil="+id_perfil+" order by fecha";
	    Cursor cursor = database.rawQuery(mysql, null);
	    
	    double[] nums = new double[12];
	    int i=0;
	    
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      nums[i]  = cursor.getDouble(0);
	      cursor.moveToNext();
	      i = i+1;
	    }
	    
	    historicos.add(nums);
	    // make sure to close the cursor
	    cursor.close();
	    return historicos;
	  }
	  
	  private Historico cursorToHistorico(Cursor cursor) 
	  {
		    Historico his = new Historico();
		    his.setId(cursor.getInt(0));
		    his.setId_perfil(cursor.getInt(1));
		    his.setPeso(cursor.getString(2));
          his.setFecha(cursor.getString(3));
          his.setComentario(cursor.getString(4));
		    his.setImc(cursor.getString(5));
		    his.setDif(cursor.getInt(6));
		    his.setPeso_objetivo(cursor.getString(7));
		    return his;
		  }

	  private Historico cursorToHistoricoGrafico(Cursor cursor) 
	  {
		    Historico his = new Historico();
		    his.setPeso(cursor.getString(0));
          his.setFecha(cursor.getString(1));
		    return his;
		  }

	  private Historico cursorToHistoricoGraficoObjetivo(Cursor cursor) 
	  {
		    Historico his = new Historico();
		    his.setPeso_objetivo(cursor.getString(0));
          his.setFecha(cursor.getString(1));
		    return his;
		  }
	  
	public String getUltimaFecha(int id_perfil) 
	{
		// TODO Auto-generated method stub
		String mysql="select max(fecha) from historico where id_perfil="+id_perfil;
		
		Cursor cursor = database.rawQuery(mysql, null);
		String sal="";

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      sal = cursor.getString(0);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return sal;
	}
}
