package com.api.fanquicias.apiFranquicias.controllers;

import com.api.fanquicias.apiFranquicias.entity.Franquicia;
import com.api.fanquicias.apiFranquicias.entity.Sucursal;
import com.api.fanquicias.apiFranquicias.models.FranquiciaModel;
import com.api.fanquicias.apiFranquicias.models.SucursalModel;
import com.api.fanquicias.apiFranquicias.repositories.FranquiciaRepository;
import com.api.fanquicias.apiFranquicias.repositories.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SucursalController {
    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    FranquiciaRepository franquiciaRepository;

    @PostMapping("/sucursal/add")
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal){
        sucursalRepository.save(sucursal);
        return sucursal;
    }

    @GetMapping("/sucursales")
    public List<Sucursal> getAllSucursales(){
        List<Sucursal>lista= sucursalRepository.findAll();
        return lista;
    }


    @PostMapping({ "/sucursal/add/franquicia" }  )
    public String AgregarSucursalFranquicia(@RequestBody @Validated SucursalModel sucursal){

        Sucursal sucursalNueva=new Sucursal();
        Franquicia franquiciaBuscar=new Franquicia();
        int idSucursalBuscar=0;

        if(sucursal!=null){
            idSucursalBuscar=sucursal.getId();
            if(sucursal.getIdFranquicia()<0){

                return "Se esperaba el id de la franquicia";

            }else{

                try{
                    franquiciaBuscar= (Franquicia)franquiciaRepository.findById(sucursal.getIdFranquicia()).get();
                } catch (Exception e) {
                    franquiciaBuscar=null;
                }


                if(franquiciaBuscar==null){
                     return "No se encuentra la franquicia "+sucursal.getIdFranquicia();
                }else{//Si la franquicia  existe

                       if(idSucursalBuscar>0) {//Si es un id valido de sucursal

                           try{
                               sucursalNueva = (Sucursal) sucursalRepository.findById(idSucursalBuscar).get();

                           } catch (Exception e) {
                               sucursalNueva=null;
                           }



                           if(sucursalNueva!=null){
                              sucursalNueva.setFranquicia(franquiciaBuscar);
                           }else{
                               sucursalNueva=new Sucursal();
                               sucursalNueva.setNombre(sucursal.getNombre());
                               sucursalNueva.setFranquicia(franquiciaBuscar);
                           }
                       }else{
                           sucursalNueva=new Sucursal();
                           sucursalNueva.setNombre(sucursal.getNombre());
                           sucursalNueva.setFranquicia(franquiciaBuscar);

                       }

                           sucursalRepository.save(sucursalNueva);
                           return "Asociacion correcta entre la sucursal "+sucursal.getNombre() +" y la franquicia "+sucursal.getIdFranquicia();



                }



            }

        }else{

            return "Se esperaban datos de la sucursal";
        }


    }




    @PostMapping({ "/sucursal/update/nombre" }  )
    public String actualizarSucursalNombre(@RequestBody @Validated SucursalModel sucursal) {
        Sucursal SucursalActualizar=null;
        String nombreAnterior="";
        if(sucursal!=null){
            SucursalActualizar=buscarSucursalPorID(sucursal.getId());
        }
        if(SucursalActualizar!=null){
            nombreAnterior=SucursalActualizar.getNombre();
            SucursalActualizar.setNombre(sucursal.getNombre());
            this.sucursalRepository.save(SucursalActualizar);
            return "Se actualizo el nombre de la sucursal  "+SucursalActualizar.getId()
                    +"; NOMBRE ANTERIOR: "+nombreAnterior+
                    "; NUEVO NOMBRE: "+sucursal.getNombre();

        }else{
            return "No se encontro la franquicia";

        }
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





}
