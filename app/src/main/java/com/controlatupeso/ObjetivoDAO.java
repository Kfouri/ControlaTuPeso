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

public class ObjetivoDAO 
{

	  private SQLiteDatabase database;
	  private BaseDatos dbHelper;
	  private String[] allColumns = {BaseDatos.COLUMNA_OBJETIVO_ID, BaseDatos.COLUMNA_OBJETIVO_ID_PERFIL,BaseDatos.COLUMNA_OBJETIVO_PESO,BaseDatos.COLUMNA_OBJETIVO_FECHA};

	  public ObjetivoDAO(Context context) 
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
	  
	  public Objetivo crearObjetivo(int id_perfil,int peso,String fecha) 
	  {
		  	
	    ContentValues values = new ContentValues();
	    values.put(BaseDatos.COLUMNA_OBJETIVO_ID_PERFIL, id_perfil);
	    values.put(BaseDatos.COLUMNA_OBJETIVO_PESO,peso);
	    values.put(BaseDatos.COLUMNA_OBJETIVO_FECHA,fecha);
	    
	    long insertId = database.insert(BaseDatos.TABLA_OBJETIVO, null, values);
	    
	    Cursor cursor = database.query(BaseDatos.TABLA_OBJETIVO,
	                                  allColumns, 
	                                  BaseDatos.COLUMNA_OBJETIVO_ID + " = " + insertId,
	                                  null,
	                                  null, 
	                                  null, 
	                                  null);
	    cursor.moveToFirst();
	    Objetivo newObjetivo = cursorToObjetivo(cursor);
	    cursor.close();
	    return newObjetivo;
	  }

	  
	  public List<Objetivo> getTodosObjetivo(int id_perfil) 
	  {
	    List<Objetivo> objetivos = new ArrayList<Objetivo>();

	    Cursor cursor = database.query(BaseDatos.TABLA_OBJETIVO,
	                                   allColumns, 
	                                   BaseDatos.COLUMNA_OBJETIVO_ID_PERFIL + " = " + id_perfil, 
	                                   null, 
	                                   null, 
	                                   null, 
	                                   BaseDatos.COLUMNA_OBJETIVO_FECHA + " desc");
     
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Objetivo his = cursorToObjetivo(cursor);
	      objetivos.add(his);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return objetivos;
	  }

	  
	  public Objetivo getUltimoObjetivo(int id_perfil) 
	  {
		
		Objetivo obj = null;
		Cursor cursor = database.query(BaseDatos.TABLA_OBJETIVO,
	                                   allColumns, 
	                                   BaseDatos.COLUMNA_HIST_ID + " = (select max(id) from objetivos where id_perfil="+id_perfil+")", 
	                                   null, 
	                                   null, 
	                                   null, 
	                                   null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      obj = cursorToObjetivo(cursor);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return obj;
	  }

	  public Objetivo getProximoObjetivo(int id_perfil) 
	  {
		
		Objetivo obj = null;
		Cursor cursor = database.query(BaseDatos.TABLA_OBJETIVO,
	                                   allColumns, 
	                                   BaseDatos.COLUMNA_HIST_ID + " = (select min(id) from objetivos where id_perfil="+id_perfil+" and fecha>=date('now'))", 
	                                   null, 
	                                   null, 
	                                   null, 
	                                   null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      obj = cursorToObjetivo(cursor);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return obj;
	  }
  
	  public String getPesoFecha(int id_perfil,String p_fecha) 
	  {
		
		String mysql="select peso "+
				     "from historico " +
				     "where id_perfil="+id_perfil+
				     "  and fecha = (select max(fecha) from historico where id_perfil="+id_perfil+" and fecha<='"+p_fecha+"') " +
				     "order by fecha desc";
		
		Cursor cursor = database.rawQuery(mysql, null);
		int sal=0;

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      sal = cursor.getInt(0);
	      cursor.moveToNext();
	      break;
	    }
	    
	    cursor.close();
	    return Integer.toString(sal);
	  }
	  
	  private Objetivo cursorToObjetivo(Cursor cursor) 
	  {
		    Objetivo obj = new Objetivo();
		    obj.setId(cursor.getInt(0));
		    obj.setId_perfil(cursor.getInt(1));
		    obj.setPeso(cursor.getString(2));
          obj.setFecha(cursor.getString(3));
		    return obj;
		  }


}
