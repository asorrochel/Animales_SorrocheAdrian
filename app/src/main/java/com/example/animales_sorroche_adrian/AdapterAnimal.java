package com.example.animales_sorroche_adrian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animales_sorroche_adrian.database.ClaseBD;
import com.example.animales_sorroche_adrian.entities.Animal;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AdapterAnimal extends RecyclerView.Adapter<AdapterAnimal.HolderAnimal> {
    //Variables
    private Context contexto;
    private ArrayList<Animal> animales;

    //Declaramos un objeto de tipo BD que hemos creado
    ClaseBD clasebd;

    //Constructor
    public AdapterAnimal(Context contexto, ArrayList<Animal> animales) {
        this.contexto = contexto;
        this.animales = animales;
        clasebd = new ClaseBD(contexto);
    }

    @NonNull
    @Override
    public AdapterAnimal.HolderAnimal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos el layout con el de list_animales
        View view = LayoutInflater.from(contexto).inflate(R.layout.list_animales, parent, false);

        //Devolvemos el HolderAlumno con todas las vistas de lista_alumno inicializadas, es donde pondremos los datos de los alumnos
        return new HolderAnimal(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnimal.HolderAnimal holder, int position) {

        //Primero obtenemos los datos de cada animal por la posición
        Animal animal = animales.get(position);
        final String id = animal.getId();
        String nombre = animal.getNombre();

        String imagen = animal.getImagen();
        String descripcion = animal.getDescripcion();
        String patas = animal.getPatas();
        String tipo = animal.getTipo();

        //Estos datos los mostramos en las vistas correspondientes de animal que están recogidas en el holder
        holder.nombre.setText(nombre);
        holder.tipo.setText(tipo);
        holder.patas.setText(patas);
        holder.imagen.setImageURI(Uri.parse(imagen));
        holder.descripcion.setText(descripcion);

        //Si clickamos en el botón de opciones
        holder.btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpcionesDialogo(""+position, id, nombre,tipo, patas,descripcion,imagen);
            }
        });
    }

    //Hacemos un método para mostrar el diálogo del botón de editar y borrar
    public void mostrarOpcionesDialogo(String posicion, String id, String nombre, String tipo, String patas, String descripcion, String imagen){
        //Creamos un array de Strings con las opciones que van a aparecer en el diálogo
        String[] opciones = {"Editar", "Eliminar"};

        //Creamos el AlertDialog con las opciones
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Selecciona una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //El which a 0 indica la primera opción que es Editar
                if(which == 0){
                    //Si clickamos en Editar vamos a la Actividad de AddAnimal para poder editar los datos
                    //le tenemos que mandar todos los datos que tiene ese animal para que los muestre
                    Intent intent = new Intent(contexto, AddAnimal.class);
                    intent.putExtra("id", id);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("patas", patas);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("descripcion", descripcion);
                    intent.putExtra("imagen", imagen);
                    //Añadimos otro dato para saber si viene de editar
                    intent.putExtra("REQUEST_EDICION_ANIMAL", true);
                    contexto.startActivity(intent);
                }
                //Si which es 1 ha clickado en eliminar
                else if(which == 1){
                    //Creamos otro diálogo para ver si estamos seguros de borrarlo
                    AlertDialog.Builder eliminarDialogo = new AlertDialog.Builder(contexto);
                    eliminarDialogo.setTitle("Eliminarás un animal");
                    eliminarDialogo.setMessage("¿Estás seguro de eliminarlo?");
                    eliminarDialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //en este caso borramos al animal que queremos
                            clasebd.eliminarAnimal(id);
                            //((listarTodosAnimales)contexto).onResume();
                        }
                    });
                    eliminarDialogo.setNegativeButton("No", null);
                    eliminarDialogo.create().show();
                }


            }
        });

        //Creamos y mostramos el diálogo
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        //Devuelve el número de animales almacenados
        return animales.size();
    }

    public class HolderAnimal extends RecyclerView.ViewHolder{
        //En esta clase cogemos todos los elementos de list_animal para poder utilizarlos en AdapterAnimal y rellenar el recyclerview posteriormente
        ShapeableImageView imagen;
        TextView nombre, patas, tipo, descripcion;
        ImageButton btn_mas;

        public HolderAnimal(@NonNull View itemView) {
            super(itemView);

            //Inicializamos los elementos de la vista
            imagen = itemView.findViewById(R.id.foto);
            nombre = itemView.findViewById(R.id.lt_nombre);
            patas =  itemView.findViewById(R.id.lt_patas);
            tipo =  itemView.findViewById(R.id.lt_tipo);
            descripcion =  itemView.findViewById(R.id.lt_desc);
            btn_mas = itemView.findViewById(R.id.btn_opciones);
        }
    }
}
