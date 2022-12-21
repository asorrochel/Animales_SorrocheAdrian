package com.example.animales_sorroche_adrian.entities;

public class Animal {

    //Declaramos las variables
    private String id;
    private String imagen;
    private String nombre;
    private String patas;
    private String tipo;
    private String descripcion;

    //Constructor
    public Animal(String id, String imagen, String nombre, String patas, String tipo, String descripcion) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.patas = patas;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    //Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPatas() {
        return patas;
    }

    public void setPatas(String patas) {
        this.patas = patas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
