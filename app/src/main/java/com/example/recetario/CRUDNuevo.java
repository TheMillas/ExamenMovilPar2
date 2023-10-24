package com.example.recetario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CRUDNuevo extends SQLiteOpenHelper {
    private static final String BD_NOMBRE = "otrabd.db";
    private static final int BD_VERSION = 1;
    private static final String TABLA_SQL = "CREATE TABLE recetitas " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descripcion TEXT)";

    public CRUDNuevo(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(TABLA_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {

    }

    public void insertarReceta(String titulo, String descripcion) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registros = new ContentValues();
        registros.put("titulo", titulo);
        registros.put("descripcion", descripcion);
        bd.insert("recetitas", null, registros);
    }

    public Cursor mostrarRecetas() {
        SQLiteDatabase bd = this.getReadableDatabase();
        String consultaSQL = "SELECT * FROM recetitas";
        Cursor listaRegistros = bd.rawQuery(consultaSQL, null);
        return listaRegistros;
    }

    public void actualizarReceta(int id, String titulo, String descripcion) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registros = new ContentValues();
        registros.put("titulo", titulo);
        registros.put("descripcion", descripcion);
        bd.update("recetitas", registros, "id=?", new String[]{String.valueOf(id)});
    }

    public void eliminarReceta(int id) {
        SQLiteDatabase bd = this.getWritableDatabase();
        bd.delete("recetitas", "id=?", new String[]{String.valueOf(id)});
    }



}