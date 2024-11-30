package com.api.fanquicias.apiFranquicias.controllers;

import com.api.fanquicias.apiFranquicias.entity.Franquicia;
import com.api.fanquicias.apiFranquicias.entity.Producto;
import com.api.fanquicias.apiFranquicias.entity.Sucursal;
import com.api.fanquicias.apiFranquicias.models.ProductoModel;
import com.api.fanquicias.apiFranquicias.models.SucursalModel;
import com.api.fanquicias.apiFranquicias.repositories.ProductoRepository;
import com.api.fanquicias.apiFranquicias.repositories.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductoController {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    SucursalRepository sucursalRepository;

    @GetMapping("/productos")
    public List<Producto> getAllProductos(){
        List<Producto>lista= productoRepository.findAll();
        return lista;
    }

    @GetMapping({"/productos/mayor/stock/{franquicia}", "/productos/mayor/stock/"})
    public List<ProductoModel> getProductosMayorStock(@PathVariable int franquicia){
        List<Producto> lista = null;
        List<ProductoModel> listaRetorno=new ArrayList<ProductoModel>();
        if(franquicia>0) {

            try {
                lista = productoRepository.getProductosMayorStockPorSucursalPorFranquicia(franquicia);
            } catch (Exception e) {
                lista = null;
            }
        }

        if(lista!=null){


            ProductoModel producto=null;
            for(int i=0; i<lista.size(); i++){
                producto=new ProductoModel();
                producto.setId(lista.get(i).getId());
                producto.setCantidad(lista.get(i).getCantidad());
                producto.setNombre(lista.get(i).getNombre());
                producto.setSucursalId(lista.get(i).getSucursal().getId());
                listaRetorno.add(producto);

            }
        }
        return listaRetorno;
    }

    @PostMapping("/productos/add")
    public Producto crearFranquicia(@RequestBody Producto producto){
        productoRepository.save(producto);
        return producto;
    }




    //Agrega un nuevo producto a una sucursal
    @PostMapping({ "/producto/add" }  )
    public String AgregarSucursalFranquicia(@RequestBody @Validated ProductoModel producto) {

        Producto productoNuevo = new Producto();
        Sucursal sucursalBuscar = new Sucursal();
        int idProductoBuscar = 0;

        if (producto != null) {
            idProductoBuscar = producto.getId();
            if (producto.getSucursalId() < 0) {

                return "Se esperaba el id de la sucursal";

            } else {
                 sucursalBuscar= buscarSucursalPorID(producto.getSucursalId());

                if (sucursalBuscar == null) {
                    return "No se encuentra la sucursal " + producto.getSucursalId();
                } else {//Si la franquicia  existe

                    if (idProductoBuscar > 0) {//Si es un id valido de producto

                        try {
                            productoNuevo = buscarProductoPorID(idProductoBuscar);


                        } catch (Exception e) {
                            productoNuevo = null;
                        }


                        if (productoNuevo != null) {
                            productoNuevo.setSucursal(sucursalBuscar);
                        } else {
                            productoNuevo = new Producto();
                            productoNuevo.setNombre(producto.getNombre());
                            productoNuevo.setCantidad(producto.getCantidad());
                            productoNuevo.setSucursal(sucursalBuscar);
                        }
                    } else {
                        productoNuevo = new Producto();
                        productoNuevo.setNombre(producto.getNombre());
                        productoNuevo.setCantidad(producto.getCantidad());
                        productoNuevo.setSucursal(sucursalBuscar);

                    }

                    productoRepository.save(productoNuevo);
                    return "El producto  " + producto.getNombre() + " se creo correctamente en la sucursal " + producto.getSucursalId();


                }


            }

        } else {
            return "Se esperaban datos de la sucursal";
        }
    }


    //Elimina un producto de la sucursal
    @PostMapping({ "/producto/delete/" }  )
    public String eliminarProductoDeSucursal(@RequestBody @Validated ProductoModel producto) {
        Producto productoEliminar=null;
        if(producto!=null){
            productoEliminar=buscarProductoPorID(producto.getId());
        }
        if(productoEliminar!=null){
            this.productoRepository.delete(productoEliminar);
            return "Se elimino el producto "+productoEliminar.getNombre()+" de la sucursal "+productoEliminar.getSucursal().getId();

        }else{
            return "No se encontro el producto";

        }
    }


    @PostMapping({ "/producto/update/" }  )
    public String actualizarProductoDeSucursal(@RequestBody @Validated ProductoModel producto) {
        Producto productoActualizar=null;
        int cantidadAnterior=0;
        if(producto!=null){
            productoActualizar=buscarProductoPorID(producto.getId());
        }
        if(productoActualizar!=null){
            cantidadAnterior=productoActualizar.getCantidad();
            productoActualizar.setCantidad(producto.getCantidad());
            this.productoRepository.save(productoActualizar);
            return "Se actualizo la cantidad del producto "+productoActualizar.getNombre()
                    +"; CANTIDAD ANTERIOR: "+cantidadAnterior+
                    "; NUEVA CANTIDAD: "+producto.getCantidad();

        }else{
            return "No se encontro el producto";

        }
    }



    public Producto buscarProductoPorID(int id){

        Producto producto=null;

        try {
            producto = (Producto) productoRepository.findById(id).get();

        } catch (Exception e) {
            producto = null;
        }
       return producto;


    }


    public Sucursal buscarSucursalPorID(int id){

        Sucursal sucursalBuscar=null;

        try {
            sucursalBuscar = (Sucursal) sucursalRepository.findById(id).get();
           } catch (Exception e) {
            sucursalBuscar = null;
        }
       return sucursalBuscar;


    }



    @PostMapping({ "/producto/update/nombre" }  )
    public String actualizarProductoNombre(@RequestBody @Validated ProductoModel producto) {
        Producto ProductoActualizar=null;
        String nombreAnterior="";
        if(producto!=null){
            ProductoActualizar=buscarProductoPorID(producto.getId());
        }
        if(ProductoActualizar!=null){
            nombreAnterior=ProductoActualizar.getNombre();
            ProductoActualizar.setNombre(producto.getNombre());
            this.productoRepository.save(ProductoActualizar);
            return "Se actualizo el nombre del producto  "+ProductoActualizar.getId()
                    +"; NOMBRE ANTERIOR: "+nombreAnterior+
                    "; NUEVO NOMBRE: "+producto.getNombre();

        }else{
            return "No se encontro el producto";

        }
    }





    }
