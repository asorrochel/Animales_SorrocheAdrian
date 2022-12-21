package com.example.animales_sorroche_adrian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.animales_sorroche_adrian.database.ClaseBD;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton addAnimal;
    ImageButton todosAnimales, voladores, terrestres, acuaticos;
    MaterialButton filtroPatasbtn;
    ClaseBD clasebd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clasebd = new ClaseBD(this);
        toolbar = findViewById(R.id.main_toolbar);
        addAnimal = findViewById(R.id.boton_add_animal);
        todosAnimales = findViewById(R.id.animalesTodos_img);
        voladores = findViewById(R.id.voladores_btn);
        acuaticos = findViewById(R.id.acuaticos_btn);
        terrestres = findViewById(R.id.terrestres_btn);
        filtroPatasbtn = findViewById(R.id.filtroPatas_btn);
        setToolbar(toolbar);

        //PARA CARGAR EN LA BD LOS ANIMALES| INICIALIZAR UNA VEZ Y LUEGO COMENTAR
        //cargarAnimales();

        //Al hacer click en el botón que vaya a otra actividad con la vista de añadir otro registro
        addAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAnimal.class);
                intent.putExtra("REQUEST_EDICION_ANIMAL",false);
                startActivity(intent);

            }
        });

        //Al hacer click en el botón que vaya a otra actividad con la vista de listar todos los animales
        todosAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, listarTodosAnimales.class);
                startActivity(intent2);

            }
        });

        voladores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, listarAnimalesVoladores.class);
                startActivity(intent3);

            }
        });

        acuaticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, listarAnimalesAcuaticos.class);
                startActivity(intent4);

            }
        });

        terrestres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(MainActivity.this, listarAnimalesTerrestres.class);
                startActivity(intent5);

            }
        });

        filtroPatasbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creamos las opciones del diálogo
                String[] opciones = {"sin patas", "2 patas", "4 patas"};

                //Creamos el diálogo con las opciones
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Filtrar por número de patas");
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //si which es 0 es que se ha marcado sin patas
                        if(which == 0){
                            startActivity(new Intent(MainActivity.this, listaSinPatas.class));
                        }
                        //si which es 1 es que se ha marcado 2 patas
                        else if(which == 1){
                            startActivity(new Intent(MainActivity.this, lista2Patas.class));
                        }
                        //si which es 2 es que se ha marcado 4 patas
                        else if(which == 2){
                            startActivity(new Intent(MainActivity.this, lista4Patas.class));
                        }
                    }
                });
                //Lo creamos y lo mostramos
                builder.create().show();
            }
        });


    }
    /**
     * Método que añade a la activity un Toolbar.
     * @param toolbar - ToolBar que queremos añadir a la activity.
     */
    private void setToolbar(androidx.appcompat.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("¡Bienvenido al Zoo de DAM!");
    }

    private void cargarAnimales(){
        clasebd.insertarDatos("Adrian el calamar","@drawable/pez","0","acuático","Calamar azul con la tinta roja");
        clasebd.insertarDatos("Alicia la gacela","@drawable/conejo","4","terrestre","Gacela con la barriga multicolor");
        clasebd.insertarDatos("Juán el pulpo","@drawable/pez","0","acuático","Pulpo que adivina e futuro");
        clasebd.insertarDatos("Oscar el rino","@drawable/conejo","4","terrestre","Rinoceronte con el cuerno al reves");
        clasebd.insertarDatos("Paloma la paloma","@drawable/pajaro","2","aéreo","Paloma que sabe cantar");
        clasebd.insertarDatos("Rosa la jirafa ","@drawable/conejo","4","terrestre","jirafa azul con el cuello corto");
        clasebd.insertarDatos("Alberto el buitre ","@drawable/pajaro","0","aéreo","Buitre amarillo que come hierba");

    }


}