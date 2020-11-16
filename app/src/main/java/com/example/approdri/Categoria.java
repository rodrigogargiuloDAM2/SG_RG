package com.example.approdri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Categoria extends AppCompatActivity {

    EditText et_categoria;
    TextView tv_ncategoria, textViewCC;
    Cursor numeroC;
    CardView cardCategoriaAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        et_categoria =findViewById(R.id.txt_categoria);
        tv_ncategoria = findViewById(R.id.tv_ncategoria);
        textViewCC=findViewById(R.id.textViewCC);
        cardCategoriaAnim= findViewById(R.id.cardCategoriaAnim);

        textViewCC.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_transition_animation));
        cardCategoriaAnim.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_transition_animation));



        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        numeroC = db.rawQuery("select count(categoria) from categorias", null);
        numeroC.moveToFirst();
        tv_ncategoria.setText("NºCategorias creadas: " + numeroC.getString(0));


        //ListView Categorías
        if (db!=null){
            Cursor c = db.rawQuery("Select categoria from categorias",null);
            int cantidad = c.getCount();
            int i = 0;
            String[] arreglo = new String[cantidad];
            if (c.moveToFirst()){
                do {
                    String linea = "Nombre Categoría: "+c.getString(0);

                    arreglo[i] = linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arreglo);
            ListView lista = findViewById(R.id.lv_categorias);
            lista.setAdapter(adapter);
        }

        db.close();
    }

    public void  Aceptar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String categoria = et_categoria.getText().toString();




        //Validar campos

        if(!categoria.isEmpty() && numeroC.getInt(0) <= 9){
            //Guardar en bbdd los valores
            ContentValues registro = new ContentValues();

            registro.put("categoria", categoria);


            //Guardar en tabla

            db.insert("categorias", null, registro);




            db.close();
            //limpiar campos
            et_categoria.setText("");



            db.close();
            numeroC.close();



            Toast.makeText(this, "Categoría agregada!!", Toast.LENGTH_SHORT).show();
            finish();
        }else if (categoria.isEmpty()){
            Toast.makeText(this, "Debes rellenar el campo categoria", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "No puedes añadir más de 10 categorías", Toast.LENGTH_SHORT).show();
        }



    }

//    public void Cancelar(View view){
//       finish();
//    }

    public void EliminarCategoria(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this,"administracion",null,1);

        SQLiteDatabase db = admin.getWritableDatabase();


        String categoria = et_categoria.getText().toString();

        if (!categoria.isEmpty()){
            int cantidad = db.delete("categorias","categoria='" + categoria+"';", null);  //cantidad de articulos borrados ,, metodo delete
            db.close();

            et_categoria.setSelection(0);



            if (cantidad == 1){
                Toast.makeText(this, "Categoría eliminada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "La categoría no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes introducir la categoría", Toast.LENGTH_SHORT).show();
        }

    }
}
