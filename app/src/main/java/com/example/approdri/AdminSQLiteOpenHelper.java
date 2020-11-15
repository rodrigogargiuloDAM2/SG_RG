package com.example.approdri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //Sintaxis de tablas

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TUsuarios ="create table if not exists usuarios(email VARCHAR(100) NOT NULL PRIMARY KEY UNIQUE , nombre VARCHAR(100),apellido VARCHAR(100),  telefono VARCHAR(100), contrasegna VARCHAR(100) NOT NULL, img BLOB);";
        String TProductos ="create table if not exists productos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, nombreProducto VARCHAR(100), descripcion VARCHAR(100), tienda VARCHAR(100), marca VARCHAR(100),precio DECIMAL(6,2) DEFAULT 0.00, cantidad int DEFAULT 0, img BLOB, id_email VARCHAR(100) NOT NULL, id_categoria VARCHAR(100) NOT NULL,  FOREIGN KEY (id_email) REFERENCES usuarios(email),FOREIGN KEY (id_categoria) REFERENCES categorias(categoria));";
        String TCategorias="create table if not exists categorias(categoria VARCHAR(100) NOT NULL PRIMARY KEY UNIQUE);";
        String TEconomia="create table if not exists economia(id_econ INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, presupuesto DECIMAL(8,2), precioTotal DECIMAL(8,2), beneficios DECIMAL(8,2), productosAlmacenados int, email VARCHAR(100), FOREIGN KEY (email) REFERENCES usuarios(email));";

        db.execSQL(TUsuarios);
        db.execSQL(TEconomia);
        db.execSQL(TCategorias);
        db.execSQL(TProductos);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //Método que permite abrir BBDD
    public void abrir(){
        this.getWritableDatabase();
    }

    //Método que permite cerrar BBDD
    public void cerrar(){
        this.close();
    }

    //Método que permite insertar registros en la tabla usuarios
    public void insertarReg(String nombre, String apellido, String email, String telefono, String contrasegna){
        ContentValues valores=new ContentValues();
        valores.put("nombre",nombre);
        valores.put("apellido",apellido);
        valores.put("email",email);
        valores.put("telefono",telefono);
        valores.put("contrasegna",contrasegna);

        this.getWritableDatabase().insert("usuarios",null,valores);
    }



    //Add Bitmap byte array to DB Tabla usuarios
    public void addBitmap( String email ,byte[] image)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email",email);
        cv.put("img",image);
        database.update("usuarios",cv,"email='"+ email+"'", null);
    }


    //Add Bitmap byte array to DB Tabla productos
    public void addBitmapProductos( String email ,byte[] image, String idProducto)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_email",email);
        cv.put("img",image);

        database.update("productos",cv,"id_email='"+ email+"' and id='"+idProducto+"'", null);
    }


    //Get Byte array bitmap tabla usuarios
    public byte[] getBitmapByName(String email)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] select = {"img"};

        qb.setTables("usuarios");

        Cursor c = qb.query(database,select,"email = ?", new String[]{email},null,null,null);
        byte[] result=null;
        if (c.moveToFirst())
        {
            do {
                result = c.getBlob(c.getColumnIndex("img"));
            }while (c.moveToNext());
        }
        return result;
    }


    //Get Byte array bitmap tabla productos
    public byte[] getBitmapByNameProductos(String email, String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] select = {"img"};

        qb.setTables("productos");

        Cursor c = qb.query(database,select,"id_email = ? and id = ?", new String[]{email, id},null,null,null);
        byte[] result=null;
        if (c.moveToFirst())
        {
            do {
                result = c.getBlob(c.getColumnIndex("img"));
            }while (c.moveToNext());
        }
        return result;
    }

    //Metodo que valida si el usuario existe
    public Cursor ConsultarUsuPas(String email, String contrasegna) throws SQLException {
        Cursor mcursor=null;
        mcursor=this.getReadableDatabase().query("usuarios",new String[]{
                "nombre","apellido","email","telefono","contrasegna"},"email like '"+email+"' " +
                "and contrasegna like '"+contrasegna+"'",null,null,null,null);
        return mcursor;
    }





}
