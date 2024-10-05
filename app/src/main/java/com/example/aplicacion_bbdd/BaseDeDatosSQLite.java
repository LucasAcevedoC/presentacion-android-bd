package com.example.aplicacion_bbdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaseDeDatosSQLite extends SQLiteOpenHelper {
    private  static String NombreBase = "mibase.db";
    private  static int version = 1;

    public BaseDeDatosSQLite(@Nullable Context context) {
        super(context, NombreBase, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE animales(nombre varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void agregarAnimal(String nombre){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO animales VALUES('"+nombre+"')";
        db.execSQL(sql);
    }

    public ArrayList verAnimales(){
        ArrayList<String> animales = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * from animales";
        Cursor cursor = db.rawQuery(sql,null);
        try{
            while (cursor.moveToNext()){
                animales.add(cursor.getString(0));
                System.out.println("Se ha listado: " + cursor.getString(0));
            }

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return animales;
    }

    public void eliminarAnimales() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM animales";
        db.execSQL(sql);
    }
}
