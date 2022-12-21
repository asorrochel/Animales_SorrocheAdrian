package com.example.animales_sorroche_adrian.database;

public class ConstantesBD {

    //Nombre de la BD
    public static final String DB_NAME = "CLASE";

    //Versión de la BD
    public static final int DB_VERSION = 1;

    //Nombre de la tabla
    public static final String TABLE_NAME = "ANIMAL";

    //Campos de la tabla
    public static final String C_ID = "ID";
    public static final String C_NOMBRE = "NOMBRE";
    public static final String C_IMAGEN = "IMAGEN";
    public static final String C_DESCRIPCION = "DESCRIPCIÓN";
    public static final String C_PATAS = "PATAS";
    public static final String C_TIPO = "TIPO";

    //Código de creación de la tabla
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NOMBRE + " TEXT, "
            + C_IMAGEN + " TEXT, "
            + C_DESCRIPCION + " TEXT, "
            + C_PATAS + " TEXT, "
            + C_TIPO + " TEXT "
            + ")";
}
