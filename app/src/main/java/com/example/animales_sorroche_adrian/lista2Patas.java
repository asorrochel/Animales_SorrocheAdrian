package com.example.animales_sorroche_adrian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.animales_sorroche_adrian.database.ClaseBD;
import com.example.animales_sorroche_adrian.database.ConstantesBD;

public class lista2Patas extends AppCompatActivity {

    RecyclerView animales;
    Toolbar toolbar;
    //Declaramos Strings que serán las opciones para ordenar la lista de alumnos

    String ordenarPorNombreAsc = ConstantesBD.C_NOMBRE + " ASC";

    //Cuando refresquemos los registros en el onResume será con la opción escogida en el ordenar almacenada
    //en la siguiente variable condicionOrdenarActual, por defecto será por nuevo alumno
    String condicionOrdenarActual = ordenarPorNombreAsc;


    //Creamos una instancia de nuestra BD
    ClaseBD claseBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista2_patas);

        toolbar = findViewById(R.id.toolbar_2Patas);
        animales = findViewById(R.id.rcv_2Patas);

        //Inicializamos la BD
        claseBD = new ClaseBD(this);

        mostrarTodosAnimales(condicionOrdenarActual);
        setToolbar(toolbar);
    }

    //Este método nos mostrará todos los animales
    private void mostrarTodosAnimales(String condicionOrdenar) {
        //cambiamos la condición actual de ordenar por ésta
        condicionOrdenarActual = condicionOrdenar;

        //Utilizamos la clase AdapterAnimal para cargar los animales en el recyclerview
        AdapterAnimal adapter = new AdapterAnimal(this, claseBD.searchAnimales("PATAS","2"));

        //utilizamos el adaptador en el recyclerview
        animales.setAdapter(adapter);
    }

    /**
     * Método que añade a la activity un Toolbar.
     * @param toolbar - ToolBar que queremos añadir a la activity.
     */
    private void setToolbar(androidx.appcompat.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Animales con 2 patas");
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