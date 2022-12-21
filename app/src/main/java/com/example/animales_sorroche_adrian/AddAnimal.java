package com.example.animales_sorroche_adrian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.animales_sorroche_adrian.database.ClaseBD;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddAnimal extends AppCompatActivity {

    Toolbar toolbar;

    EditText descripcion;
    EditText patas;
    EditText tipo;
    EditText nombre;
    FloatingActionButton aceptar;

    //declaramos variables para guardar datos
    Uri uri;
    String idAnimal, txt_nombre,txt_patas, txt_tipo, txt_descripcion;

    //Declaramos un objecto de tipo BD qaue hemos creado
    ClaseBD clasebd;

    //Creamos un booleano que será el que nos indique si viene de agregar animal o de editarlo
    boolean editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        //Cogemos los elementos de la vista
        toolbar = findViewById(R.id.add_animal_toolbar);
        nombre = findViewById(R.id.add_animal_nombre_EditText);
        patas = findViewById(R.id.add_animal_patas_EditText);
        tipo = findViewById(R.id.add_animal_tipo_editText);
        descripcion = findViewById(R.id.add_animal_desc_EditText);
        aceptar = findViewById(R.id.btn_check);

        if(tipo.equals("acuático")) {
            uri = Uri.parse("@drawable/pez");
        } else if (tipo.equals("terrestre")) {
            uri = Uri.parse("@drawable/conejo");
        } else if (tipo.equals("aéreo")) {
            uri = Uri.parse("@drawable/pajaro");
        }

        setToolbar(toolbar);

        clasebd = new ClaseBD(this);

        //Recojo información del intent para saber si se quiere agregar un animal o editarlo
        Bundle bundle = getIntent().getExtras();
        editar = bundle.getBoolean("REQUEST_EDICION_ANIMAL");
        Log.i("EDITAR", ""+editar);
        if(editar) {
            //si viene de editar cojo los datos de ese animal
            idAnimal = bundle.getString("id");
            idAnimal = bundle.getString("id");
            txt_nombre = bundle.getString("nombre");
            txt_descripcion = bundle.getString("descripcion");
            txt_tipo = bundle.getString("tipo");
            txt_patas = bundle.getString("patas");
            uri = Uri.parse(bundle.getString("imagen"));

            //y los muestro
            nombre.setText(txt_nombre);
            descripcion.setText(txt_descripcion);
            patas.setText(txt_patas);
            tipo.setText(txt_tipo);
        }
            //Cuando le damos a aceptar guardamos los datos y volvemos a la pantalla donde se muestran todos los registros
            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guardarDatos();
                    onBackPressed();
                }
            });

    }

    private void guardarDatos(){
        //cogemos los datos introducidos en los EditText y los almacenamos en las variables creadas en la clase anteriormente sin espacios
        txt_nombre = nombre.getText().toString().trim();
        txt_patas = patas.getText().toString().trim();
        txt_tipo = tipo.getText().toString().trim();
        txt_descripcion = descripcion.getText().toString().trim();
        long id;
        if(!editar) {
            id = clasebd.insertarDatos(txt_nombre,""+uri,txt_patas,txt_tipo,txt_descripcion);
        }
        else{
            id = clasebd.actualizarDatos(idAnimal, txt_nombre,""+uri,txt_patas,txt_tipo,txt_descripcion);
        }

    }


    /**
     * Método que añade a la activity un Toolbar.
     * @param toolbar - ToolBar que queremos añadir a la activity.
     */
    private void setToolbar(androidx.appcompat.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar animal al Zoo");
        // Añadimos la flecha de retroceso.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Método que le da funcionalidad a la flecha de retroceso del Toolbar.
     * @return - True.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}