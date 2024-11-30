package com.api.fanquicias.apiFranquicias.repositories;

import com.api.fanquicias.apiFranquicias.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query(value="SELECT  p.*  " +
            "FROM    producto p  " +
            "inner JOIN  sucursal s on p.sucursal_id=s.id " +
            "inner JOIN  franquicia f on f.id= s.franquicia_id " +
            "  where  " +
            "(sucursal_id, cantidad ) in( " +
            "select sucursal_id, max(cantidad) from producto " +
            "where sucursal_id=p.sucursal_id  " +
            "group by  sucursal_id) " +
            "AND f.id= ?1", nativeQuery = true)
     List<Producto> getProductosMayorStockPorSucursalPorFranquicia(int franquicia);

}
