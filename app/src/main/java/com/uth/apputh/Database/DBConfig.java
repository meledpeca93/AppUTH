package com.uth.apputh.Database;

public class DBConfig {

    public static final String DATABASE_NAME = "personas.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_PERSONAS = "personas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_DIRECCION = "direccion";
    public static final String COLUMN_FECHANAC = "fechanac";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_CORREO = "correo";

    public static final String CREATE_TABLE_PERSONAS = "CREATE TABLE " + TABLE_PERSONAS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOMBRES + " TEXT NOT NULL, " +
            COLUMN_APELLIDOS + " TEXT NOT NULL, " +
            COLUMN_DIRECCION + " TEXT NOT NULL, " +
            COLUMN_FECHANAC + " TEXT, " +
            COLUMN_TELEFONO + " TEXT, " +
            COLUMN_CORREO + " TEXT )";

    public static final String DROP_TABLE_PERSONAS = "DROP TABLE IF EXISTS " + TABLE_PERSONAS;
    public static final String SELECT_TABLE_PERSONAS = "SELECT * FROM " + TABLE_PERSONAS;
}
