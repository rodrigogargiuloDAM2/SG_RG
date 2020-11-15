package com.example.approdri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PerfilOpciones extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_opciones);


    }
    public void InfoCat(View view){
        Intent i= new Intent(getApplicationContext(), Info_insertarCategoria.class);
        startActivity(i);
    }

    public void InfoInsertArt(View view){
        Intent i= new Intent(getApplicationContext(), InfoInsertarArt.class);
        startActivity(i);
    }

    public void InfoEco(View view){
        Intent i= new Intent(getApplicationContext(), InfoEconomia.class);
        startActivity(i);
    }


    public void InfoLart(View view){
        Intent i= new Intent(getApplicationContext(), InfoListadoArt.class);
        startActivity(i);
    }


    public void InfoPerf(View view){
        Intent i= new Intent(getApplicationContext(), InfoPerfil.class);
        startActivity(i);
    }

    public void InfoApp(View view){
        Intent i= new Intent(getApplicationContext(), InfoApp.class);
        startActivity(i);
    }
}
