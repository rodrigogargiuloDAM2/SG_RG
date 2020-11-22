package com.example.approdri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;


import java.util.ArrayList;

public class PruebaReciclerView extends AppCompatActivity {

    private RecyclerView recyclerViewUsuario;

    ArrayList<DatosUsuarios> datosUsuariosList;

    SQLiteDatabase db;
    AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"administracion",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_recicler_view);

        datosUsuariosList=new ArrayList<>();

        recyclerViewUsuario=findViewById(R.id.recyclerUsuarios);
        recyclerViewUsuario.setLayoutManager(new LinearLayoutManager(this));
        
        llenarUsuarios();

       RecyclerViewAdaptador adaptador = new RecyclerViewAdaptador(datosUsuariosList);
       recyclerViewUsuario.setAdapter(adaptador);


    }

    private void llenarUsuarios() {



        db = admin.getReadableDatabase();
        Cursor datosUsuarios = db.rawQuery("Select email from usuarios", null);
        int numero_registro =datosUsuarios.getCount();

        while (datosUsuarios.moveToNext()){

            datosUsuariosList.add(new DatosUsuarios(datosUsuarios.getString(0)));
        }
        Toast.makeText(this, "Usuarios: " + numero_registro, Toast.LENGTH_SHORT).show();
    }

}
