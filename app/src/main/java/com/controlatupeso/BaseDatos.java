package com.controlatupeso;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreatePerfil = "CREATE TABLE perfiles (id integer primary key autoincrement, alias TEXT,altura integer,peso integer,edad integer,sexo TEXT,proximo text)";
    String sqlCreateHistorico = "CREATE TABLE historico (id integer primary key autoincrement, id_perfil integer,peso REAL,fecha text,comentario text,imc text,dif integer,peso_objetivo text)";
    String sqlCreateObjetivo = "CREATE TABLE objetivos (id integer primary key autoincrement, id_perfil integer,peso integer,fecha text)";
 
    public static final String TABLA_PERFIL = "perfiles";
    public static final String COLUMNA_ID = "id";
    public static final String COLUMNA_ALIAS = "alias";
    public static final String COLUMNA_ALTURA = "altura";
    public static final String COLUMNA_PESO = "peso";
    public static final String COLUMNA_EDAD = "edad";
    public static final String COLUMNA_SEXO = "sexo";
    public static final String COLUMNA_PROXIMO = "proximo";

    public static final String TABLA_HISTORICO = "historico";
    public static final String COLUMNA_HIST_ID = "id";
    public static final String COLUMNA_HIST_ID_PERFIL = "id_perfil";
    public static final String COLUMNA_HIST_FECHA = "fecha";
    public static final String COLUMNA_HIST_PESO = "peso";
    public static final String COLUMNA_HIST_COMENTARIO = "comentario";
    public static final String COLUMNA_HIST_IMC = "imc";
    public static final String COLUMNA_HIST_DIF = "dif";
    public static final String COLUMNA_HIST_OBJETIVO = "peso_objetivo";

    public static final String TABLA_OBJETIVO = "objetivos";
    public static final String COLUMNA_OBJETIVO_ID = "id";
    public static final String COLUMNA_OBJETIVO_ID_PERFIL = "id_perfil";
    public static final String COLUMNA_OBJETIVO_FECHA = "fecha";
    public static final String COLUMNA_OBJETIVO_PESO = "peso";

    
    private static final String DATABASE_NAME = "controlatupeso.db";
    private static final int DATABASE_VERSION = 1;
    
    public BaseDatos(Context contexto, String nombre, CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    public BaseDatos(Context context) 
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreatePerfil);
        db.execSQL(sqlCreateHistorico);
        db.execSQL(sqlCreateObjetivo);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {


        db.execSQL("DROP TABLE IF EXISTS perfiles");
        db.execSQL("DROP TABLE IF EXISTS historico");
        db.execSQL("DROP TABLE IF EXISTS objetivos");

        db.execSQL(sqlCreatePerfil);
        db.execSQL(sqlCreateHistorico);
        db.execSQL(sqlCreateObjetivo);
    }
}