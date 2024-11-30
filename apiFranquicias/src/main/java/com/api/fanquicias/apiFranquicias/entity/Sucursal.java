package com.api.fanquicias.apiFranquicias.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="sucursal")
public class Sucursal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nombre;

    @OneToMany(mappedBy = "sucursal" )
    private List<Producto> productos;

    @ManyToOne
    @JoinColumn(name="franquicia_id")
    private Franquicia franquicia;

    public Sucursal(){


    }
    public Sucursal(String nombre, List<Producto> productos, Franquicia franquicia) {
        this.nombre = nombre;
        this.productos = productos;
        this.franquicia = franquicia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Franquicia getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(Franquicia franquicia) {
        this.franquicia = franquicia;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", productos=" + productos +
                ", fanquicia=" + franquicia +
                '}';
    }
}
