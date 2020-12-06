package com.example.approdri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Economia extends AppCompatActivity {

    EditText edt_Presupuesto;
    TextView tv_PrecioTotal, tv_Beneficios, tv_productosAlmacenados;



    AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(this,"administracion", null, 1);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economia);

        edt_Presupuesto = findViewById(R.id.edtPresupuesto);
        tv_PrecioTotal = findViewById(R.id.tv_precioTotal);
        tv_Beneficios = findViewById(R.id.tv_beneficio);
        tv_productosAlmacenados = findViewById(R.id.tv_productosAlmacenados);

        //Mostrar lista
        SQLiteDatabase db = conn.getWritableDatabase();
        Bundle miBundle2=this.getIntent().getExtras();
        String DatoEmail=miBundle2.getString("emailEcon");
        if (db!=null){
            Cursor c = db.rawQuery("Select id_econ, presupuesto, precioTotal, beneficios, productosAlmacenados from economia where email='"+DatoEmail+"'",null);
            int cantidad = c.getCount();
            int i = 0;
            String[] arreglo = new String[cantidad];
            if (c.moveToFirst()){
                do {
                    String linea = "ID: "+c.getInt(0) +" \nPresupuesto: "+c.getInt(1) + " \nPrecio Total: " +c.getInt(2) + " \nBeneficios: " + c.getInt(3)+ " \nNúmero de Productos: " + c.getString(4);

                    arreglo[i] = linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arreglo);
            ListView lista = findViewById(R.id.lv_economia);
            lista.setAdapter(adapter);

        }



    }



    public void Calcular(View view){
        if (!edt_Presupuesto.getText().toString().isEmpty()){
            SQLiteDatabase db = conn.getWritableDatabase();

            Bundle miBundle2=this.getIntent().getExtras();
            String DatoEmail=miBundle2.getString("emailEcon");



            String presupuesto = edt_Presupuesto.getText().toString();
            Double precioTotal=0.0;
            String numeroProductos ;

            Cursor pTotal = db.rawQuery("select sum(precio*cantidad) from productos where id_email='"+ DatoEmail+"'",null);
            pTotal.moveToFirst();
            precioTotal=pTotal.getDouble(0);
            tv_PrecioTotal.setText("Precio Total: " + precioTotal);



            //Calcular Beneficio
            Double beneficio = Double.valueOf(edt_Presupuesto.getText().toString()) - pTotal.getDouble(0);
            tv_Beneficios.setText("Beneficio: " + beneficio);


            //Numero productos
            Cursor CnumeroProductos = db.rawQuery("select count(cantidad) from productos Where id_email='"+DatoEmail+"';",null);
            CnumeroProductos.moveToFirst();
            numeroProductos= CnumeroProductos.getString(0);
            tv_productosAlmacenados.setText("Productos Almacenados: " + CnumeroProductos.getInt(0));




            // Guardar en BBDD los calores
            ContentValues Econ = new ContentValues();

            Econ.put("presupuesto",presupuesto);
            Econ.put("precioTotal",precioTotal);
            Econ.put("beneficios",beneficio);
            Econ.put("productosAlmacenados",numeroProductos);
            Econ.put("email",DatoEmail);


            //Guardar en tabla
            db.insert("economia",null,Econ);



            if (db!=null){
                Cursor c = db.rawQuery("Select id_econ, presupuesto, precioTotal, beneficios, productosAlmacenados from economia where email='"+DatoEmail+"'",null);
                int cantidad = c.getCount();
                int i = 0;
                String[] arreglo = new String[cantidad];
                if (c.moveToFirst()){
                    do {
                        String linea = "ID: "+c.getInt(0) +" \nPresupuesto: "+c.getDouble(1) + " \nPrecio Total: " +c.getDouble(2) + " \nBeneficios: " + c.getDouble(3)+ " \nNúmero de Productos: " + c.getString(4);

                        arreglo[i] = linea;
                        i++;
                    }while (c.moveToNext());
                }
                ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arreglo);
                ListView lista = findViewById(R.id.lv_economia);
                lista.setAdapter(adapter);

            }


            db.close();




            Toast.makeText(this, "Calculo exitoso!!", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Debes introducir el presupuesto", Toast.LENGTH_SHORT).show();
        }

    }


    public void EliminarDatosEcoList(View view){

        Bundle miBundle2=this.getIntent().getExtras();
        String DatoEmail=miBundle2.getString("emailEcon");

        SQLiteDatabase db = conn.getWritableDatabase(); //abrimos BBDD

        int cantidad = db.delete("economia","email='" + DatoEmail+"'", null);  //cantidad de articulos borrados ,, metodo delete

        db.close();



        if (cantidad >= 1 ){
            Toast.makeText(this, "Lista Vaciada, Usuario: " + DatoEmail, Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, "Error y/o no hay elementos en la lista", Toast.LENGTH_SHORT).show();
        }


    }
}
