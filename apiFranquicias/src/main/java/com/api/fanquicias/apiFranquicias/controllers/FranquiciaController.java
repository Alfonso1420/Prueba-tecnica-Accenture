package com.api.fanquicias.apiFranquicias.controllers;

import com.api.fanquicias.apiFranquicias.entity.Franquicia;
import com.api.fanquicias.apiFranquicias.entity.Producto;
import com.api.fanquicias.apiFranquicias.models.FranquiciaModel;
import com.api.fanquicias.apiFranquicias.models.ProductoModel;
import com.api.fanquicias.apiFranquicias.repositories.FranquiciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class FranquiciaController {

    @Autowired
    FranquiciaRepository franquiciaRepository;


    //get all franquicias
    @GetMapping("/franquicias")
    public List<Franquicia> getAllFranquicias(){
        List<Franquicia>lista= franquiciaRepository.findAll();
        return lista;
    }

    @GetMapping("/franquicias/{id}")
    public Franquicia getFranquicia(@PathVariable int id){
    return (Franquicia)franquiciaRepository.findById(id).get();
    }


    @PostMapping("/franquicia/add/{nombre}")
    public String crearFranquicia(@PathVariable String nombre){
        if(nombre!=null && !nombre.equals("")){
            Franquicia franquicia=new Franquicia();
            franquicia.setNombre(nombre);
            franquiciaRepository.save(franquicia);
            return  "Franquicia "+nombre+" guardada correctamente";

        }else{

            return "Debe ingresar el nombre de la franquicia ";
        }

    }



    @PostMapping({ "/franquicia/update/nombre" }  )
    public String actualizarProductoDeSucursal(@RequestBody @Validated FranquiciaModel franquicia) {
        Franquicia FranquiciaActualizar=null;
        String nombreAnterior="";
        if(franquicia!=null){
            FranquiciaActualizar=buscarFranquiciaPorID(franquicia.getId());
        }
        if(FranquiciaActualizar!=null){
            nombreAnterior=FranquiciaActualizar.getNombre();
            FranquiciaActualizar.setNombre(franquicia.getName());
            this.franquiciaRepository.save(FranquiciaActualizar);
            return "Se actualizo el nombre de la franquicia  "+FranquiciaActualizar.getId()
                    +"; NOMBRE ANTERIOR: "+nombreAnterior+
                    "; NUEVO NOMBRE: "+franquicia.getName();

        }else{
            return "No se encontro la franquicia";

        }
    }



    public Franquicia buscarFranquiciaPorID(int id){

        Franquicia franquicia=null;

        try {
            franquicia = (Franquicia) franquiciaRepository.findById(id).get();

        } catch (Exception e) {
            franquicia = null;
        }
        return franquicia;


    }















}
