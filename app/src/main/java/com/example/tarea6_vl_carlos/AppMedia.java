package com.example.tarea6_vl_carlos;

import android.graphics.Bitmap;

import java.io.Serializable;

public class AppMedia implements Serializable {

    private String nombre;
    private String descripcion;
    private String tipo;
    private Bitmap typeimg;
    private String uri;
    private String imagen;
    private Bitmap photo;

    public AppMedia(String nombre, String descripcion, String tipo, String URI, String imagen, Bitmap photo, Bitmap typeimg) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.uri = URI;
        this.imagen = imagen;
        this.photo = photo;
        this.typeimg = typeimg;
    }

    public AppMedia(String nombre, String descripcion, String tipo, String URI, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.uri = URI;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getTypeimg() {
        return typeimg;
    }

    public void setTypeimg(Bitmap typeimg) {
        this.typeimg = typeimg;
    }


}

