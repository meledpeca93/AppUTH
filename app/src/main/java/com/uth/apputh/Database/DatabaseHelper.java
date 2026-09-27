package com.uth.apputh.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.uth.apputh.Models.Personas;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConfig.CREATE_TABLE_PERSONAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBConfig.DROP_TABLE_PERSONAS);
        onCreate(db);
    }

    public long agregarPersona(Personas persona) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConfig.COLUMN_NOMBRES, persona.getNombres());
        values.put(DBConfig.COLUMN_APELLIDOS, persona.getApellidos());
        values.put(DBConfig.COLUMN_FECHANAC, persona.getFechaNac());
        values.put(DBConfig.COLUMN_DIRECCION, persona.getDireccion());
        values.put(DBConfig.COLUMN_TELEFONO, persona.getTelefono());
        values.put(DBConfig.COLUMN_CORREO, persona.getCorreo());

        long result = db.insert(DBConfig.TABLE_PERSONAS, null, values);
        db.close();
        return result;
    }

    public List<Personas> obtenerTodasLasPersonas() {
        List<Personas> listaPersonas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(DBConfig.SELECT_TABLE_PERSONAS, null);

        if (cursor.moveToFirst()) {
            do {
                Personas persona = new Personas(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBConfig.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBConfig.COLUMN_NOMBRES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBConfig.COLUMN_APELLIDOS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBConfig.COLUMN_FECHANAC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBConfig.COLUMN_DIRECCION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBConfig.COLUMN_TELEFONO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBConfig.COLUMN_CORREO))
                );
                listaPersonas.add(persona);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaPersonas;
    }

    public int obtenerCantidadPersonas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DBConfig.TABLE_PERSONAS, null);
        int cantidad = 0;
        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return cantidad;
    }

    public boolean eliminarPersona(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasAfectadas = db.delete(DBConfig.TABLE_PERSONAS, DBConfig.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return filasAfectadas > 0;
    }
}
