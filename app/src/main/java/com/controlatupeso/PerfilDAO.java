package com.controlatupeso;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PerfilDAO {
	 // Database fields
	  private SQLiteDatabase database;
	  private BaseDatos dbHelper;
	  private String[] allColumns = {BaseDatos.COLUMNA_ID, BaseDatos.COLUMNA_ALIAS,BaseDatos.COLUMNA_ALTURA,BaseDatos.COLUMNA_PESO,BaseDatos.COLUMNA_EDAD,BaseDatos.COLUMNA_SEXO,BaseDatos.COLUMNA_PROXIMO};

	  public PerfilDAO(Context context) 
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

	  public Perfil crearPerfil(String alias,int altura,int peso,int edad,String sexo,String proximo) 
	  {
	    ContentValues values = new ContentValues();
	    values.put(BaseDatos.COLUMNA_ALIAS, alias);
	    values.put(BaseDatos.COLUMNA_ALTURA, altura);
	    values.put(BaseDatos.COLUMNA_PESO, peso);
	    values.put(BaseDatos.COLUMNA_EDAD, edad);
	    values.put(BaseDatos.COLUMNA_SEXO, sexo);
	    values.put(BaseDatos.COLUMNA_PROXIMO, proximo);
	    
	    long insertId = database.insert(BaseDatos.TABLA_PERFIL, null, values);
	    
	    Cursor cursor = database.query(BaseDatos.TABLA_PERFIL,
	                                  allColumns, 
	                                  BaseDatos.COLUMNA_ID + " = " + insertId,
	                                  null,
	                                  null, 
	                                  null, 
	                                  null);
	    cursor.moveToFirst();
	    Perfil newPerfil = cursorToPerfil(cursor);
	    cursor.close();
	    return newPerfil;
	  }

	  public void deletePerfil(Perfil perfil) 
	  {
	    int id = perfil.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(BaseDatos.TABLA_PERFIL, BaseDatos.COLUMNA_ID + " = " + id, null);
	  }

	  public Perfil getPerfil(int id)
	  {
		  Cursor cursor = database.query(BaseDatos.TABLA_PERFIL,
                    allColumns, 
                    BaseDatos.COLUMNA_ID + " = " + id, 
                    null, 
                    null, 
                    null, 
                    null);
		  if (cursor.getCount()==0)
		  {
			  return null;
		  }
		  else
		  {
			  cursor.moveToFirst();
	  	      Perfil newPerfil = cursorToPerfil(cursor);
		      cursor.close();
			  return newPerfil;			  
		  }

	  }
	  
	  public List<Perfil> getTodosPerfiles() 
	  {
	    List<Perfil> perfiles = new ArrayList<Perfil>();

	    Cursor cursor = database.query(BaseDatos.TABLA_PERFIL,
	                                   allColumns, 
	                                   null, 
	                                   null, 
	                                   null, 
	                                   null, 
	                                   null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Perfil perfil = cursorToPerfil(cursor);
	      perfiles.add(perfil);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return perfiles;
	  }

	  
	  public void ModificarPerfil(Perfil per) 
	  {
		
		String mysql="update perfiles set alias='"+per.getAlias()+"',altura="+per.getAltura()+",edad="+per.getEdad()+" where id="+per.getId();
		
		Cursor cursor = database.rawQuery(mysql, null);
	    cursor.moveToFirst();   
	    cursor.close();
	  }
	  
	  private Perfil cursorToPerfil(Cursor cursor) {
	    Perfil perfil = new Perfil();
	    perfil.setId(cursor.getInt(0));
	    perfil.setAlias(cursor.getString(1));
	    perfil.setAltura(cursor.getInt(2));
	    perfil.setPeso(cursor.getInt(3));
	    perfil.setEdad(cursor.getInt(4));
	    perfil.setSexo(cursor.getString(5));
	    perfil.setProximo(cursor.getString(6));
	    
	    return perfil;
	  }
	} 