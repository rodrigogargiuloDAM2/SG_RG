package com.example.approdri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerArticulos extends AppCompatActivity {
    private RecyclerView recyclerViewArticulos;

    ArrayList<DatosArticulos> datosArticulos;

    SQLiteDatabase db;
    AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"administracion",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_articulos);


        datosArticulos=new ArrayList<>();

        recyclerViewArticulos=findViewById(R.id.recyclerArticulos);
        recyclerViewArticulos.setLayoutManager(new LinearLayoutManager(this));



        llenarArticulos();


        RecyclerViewAdaptadorArticulos adaptador = new RecyclerViewAdaptadorArticulos(datosArticulos);
        recyclerViewArticulos.setAdapter(adaptador);


    }

    private void llenarArticulos() {

        Bundle miBundle2=this.getIntent().getExtras();
        String DatoEmailR=miBundle2.getString("emailInsertarR");
        db = admin.getReadableDatabase();
        Cursor datosArticulosCursor = db.rawQuery("Select id ,nombreProducto, id_email from productos where id_email='"+DatoEmailR+"';", null);
        int numero_registro =datosArticulosCursor.getCount();
        while (datosArticulosCursor.moveToNext()){
            datosArticulos.add(new DatosArticulos(datosArticulosCursor.getString(0),datosArticulosCursor.getString(1),datosArticulosCursor.getString(2)));
        }
        Toast.makeText(this, "NºArticulos en Lista: " + numero_registro, Toast.LENGTH_SHORT).show();
    }


}