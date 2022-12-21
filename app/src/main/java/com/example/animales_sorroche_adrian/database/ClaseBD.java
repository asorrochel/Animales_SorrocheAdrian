package com.example.animales_sorroche_adrian.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.animales_sorroche_adrian.entities.Animal;

import java.util.ArrayList;

//La clase SQLiteOpenHelper contiene todos los métodos
public class ClaseBD extends SQLiteOpenHelper {

    public ClaseBD(@Nullable Context context) {
        super(context, ConstantesBD.DB_NAME, null, ConstantesBD.DB_VERSION);
    }

    //Crea las tablas correspondientes de la BD ejecutando con execSQL código SQL, en este caso una tabla con el String creado en ConstantesBD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstantesBD.CREATE_TABLE);

    }

    //Este método sirve para actualizar la BD, le ponemos la versión anterior y la nueva y se volverá a crear
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //lo primero que haremos en el caso de una actualización de la BD, es borrar la tabla si existe
        db.execSQL("DROP TABLE IF EXISTS " + ConstantesBD.TABLE_NAME);

        //después la volveremos a crear
        onCreate(db);

    }

    //Creamos un método para insertar datos a la BD
    public long insertarDatos(String nombre, String imagen, String patas, String tipo, String descripcion){

        //Creamos una SQLiteDatabase con getWritableDatabase porque queremos escribir datos
        SQLiteDatabase db = this.getWritableDatabase();

        //Creamos un ContentValues para poner y almacenar los datos que queremos insertar
        ContentValues valores = new ContentValues();
        valores.put(ConstantesBD.C_NOMBRE, nombre);
        valores.put(ConstantesBD.C_IMAGEN, imagen);
        valores.put(ConstantesBD.C_DESCRIPCION, descripcion);
        valores.put(ConstantesBD.C_PATAS, patas);
        valores.put(ConstantesBD.C_TIPO, tipo);

        //Insertamos la fila en la BD que devolverá el long correspondiente a la identificación del registro guardado
        long idregistro = db.insert(ConstantesBD.TABLE_NAME, null, valores);

        //Cerramos la conexión con la BD
        db.close();

        //Devolvemos la identificación del registro insertado
        return idregistro;

    }

    //Creamos un método para actualizar los datos de un animal
    public long actualizarDatos(String id, String nombre, String imagen, String patas, String tipo, String descripcion){

        //Creamos una SQLiteDatabase con getWritableDatabase porque queremos escribir datos
        SQLiteDatabase db = this.getWritableDatabase();

        //Creamos un ContentValues para poner y almacenar los datos que queremos actualizar
        ContentValues valores = new ContentValues();
        valores.put(ConstantesBD.C_NOMBRE, nombre);
        valores.put(ConstantesBD.C_IMAGEN, imagen);
        valores.put(ConstantesBD.C_DESCRIPCION, descripcion);
        valores.put(ConstantesBD.C_PATAS, patas);
        valores.put(ConstantesBD.C_TIPO, tipo);

        //Actualizamos la fila en la BD que devolverá el long correspondiente a la identificación del registro guardado
        long idregistro = db.update(ConstantesBD.TABLE_NAME, valores, ConstantesBD.C_ID + "= ?", new String[]{id});

        //Cerramos la conexión con la BD
        db.close();

        //Devolvemos la identificación del registro insertado
        return idregistro;

    }

    //Obtener todos los datos metiendo el orden en el que los deseamos (de más nuevo a más antiguo, ordenado alfabéticamente por nombre...)
    @SuppressLint("Range")
    public ArrayList<Animal> getAnimal(String orderBy){
        //creamos un ArrrayList donde estarán todos los animales, será el que devolvamos
        ArrayList<Animal> animales = new ArrayList<>();

        //Creamos la consulta para seleccionar todos los datos de la BD
        String consulta = "SELECT * FROM " + ConstantesBD.TABLE_NAME + " ORDER BY " + orderBy;

        //Hacemos BD modificable y un cursos para poder movernos por los registros
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        //Recorremos todos los registros y los añadimos a la lista
        if(cursor.moveToFirst()){
            do{
                Log.i("ANIMALES ID", cursor.getString(cursor.getColumnIndex(ConstantesBD.C_NOMBRE)) +":" + cursor.getInt(cursor.getColumnIndex(ConstantesBD.C_ID)));
                Animal animal = new Animal(
                        "" + cursor.getInt(cursor.getColumnIndex(ConstantesBD.C_ID)),
                        cursor.getString(cursor.getColumnIndex(ConstantesBD.C_IMAGEN)),
                        cursor.getString(cursor.getColumnIndex(ConstantesBD.C_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(ConstantesBD.C_PATAS)),
                        cursor.getString(cursor.getColumnIndex(ConstantesBD.C_TIPO)),
                        cursor.getString(cursor.getColumnIndex(ConstantesBD.C_DESCRIPCION))
                );

                //Añadimos registro a la lista
                animales.add(animal);
            }while(cursor.moveToNext());

        }

        //cerramos la conexión de la BD

        db.close();

        //devolvemos la lista de animales
        return animales;

    }

    //Buscamos animales pasando una condicion
    public ArrayList<Animal> searchAnimales(String constante ,String condicion){
        //creamos un ArrrayList donde estarán todos los animales, será el que devolvamos
        ArrayList<Animal> animales = new ArrayList<>();

        //Creamos la consulta para seleccionar todos los datos de la BD
        String consulta = "SELECT * FROM " + ConstantesBD.TABLE_NAME + " WHERE " + constante  + " LIKE '%" + condicion + "%'";

        //Hacemos BD modificable y un cursos para poder movernos por los registros
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            Cursor cursor = db.rawQuery(consulta, null);

            //Recorremos todos los registros y los añadimos a la lista
            if (cursor.moveToFirst()) {
                do {
                     Animal animal = new Animal(
                            "" + cursor.getInt(cursor.getColumnIndex(ConstantesBD.C_ID)),
                            cursor.getString(cursor.getColumnIndex(ConstantesBD.C_IMAGEN)),
                            cursor.getString(cursor.getColumnIndex(ConstantesBD.C_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(ConstantesBD.C_PATAS)),
                            cursor.getString(cursor.getColumnIndex(ConstantesBD.C_TIPO)),
                            cursor.getString(cursor.getColumnIndex(ConstantesBD.C_DESCRIPCION))
                    );

                    //Añadimos registro a la lista
                    animales.add(animal);
                } while (cursor.moveToNext());

            }

            //cerramos la conexión de la BD
            db.close();
        }

        //devolvemos la lista de animales
        return animales;

    }

    //Devuelve un animal pasando su id
    @SuppressLint("Range")
    public Animal getAnimalById(String id){
        //Creamos el animal que devolveremos
        Animal animal = null;

        //Creamos la consulta para seleccionar todos los datos de la BD
        String consulta = "SELECT * FROM " + ConstantesBD.TABLE_NAME + " WHERE " + ConstantesBD.C_ID + " =\"" + id + "\"";
        //String consulta = "SELECT * FROM " + ConstantesBD.TABLE_NAME + " WHERE " + ConstantesBD.C_ID + " = " + id;
        Log.i("CONSULTA", consulta);

        //Hacemos BD modificable y un cursos para poder movernos por los registros
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);



        //Recorremos todos los registros y los añadimos a la lista
        if(cursor.moveToFirst()){
            do{
                cursor.moveToFirst();
                Log.i("NOMBRE", cursor.getString(cursor.getColumnIndex(ConstantesBD.C_NOMBRE)));

                        animal = new Animal(
                                id,
                                cursor.getString(cursor.getColumnIndex(ConstantesBD.C_IMAGEN)),
                                cursor.getString(cursor.getColumnIndex(ConstantesBD.C_NOMBRE)),
                                cursor.getString(cursor.getColumnIndex(ConstantesBD.C_PATAS)),
                                cursor.getString(cursor.getColumnIndex(ConstantesBD.C_TIPO)),
                                cursor.getString(cursor.getColumnIndex(ConstantesBD.C_DESCRIPCION))
                        );
            }while(cursor.moveToNext());

        }

        //cerramos la conexión de la BD
        db.close();

        //devolvemos la lista de animales
        return animal;

    }

    //Borrar un animal por un id
    public void eliminarAnimal(String id){
        //Creamos una SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //Borramos el registro que tiene por identificador ese id
        db.delete(ConstantesBD.TABLE_NAME, ConstantesBD.C_ID + "= ?", new String[]{id});

        //Cerramos la conexión con la BD
        db.close();
    }

    //Borrar todos los animales
    public void eliminarTodosAnimales(){
        //Creamos una SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //Ejecutamos la consulta de eliminación de todos los registros
        String consulta = "DELETE FROM " + ConstantesBD.TABLE_NAME;
        db.execSQL(consulta);

        //Cerramos la conexión con la BD
        db.close();
    }

    //Obtener número de registros de la BD
    public int getItemsCount(){
      String consulta = "SELECT * FROM " + ConstantesBD.TABLE_NAME;
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery(consulta, null);

      int registros = cursor.getCount();
      cursor.close();

      return registros;
    }
}
