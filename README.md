# Prueba-tecnica-Accenture  (Se envia documento con especificaciones en la respuesta de la prueba https://questionnaire.evalart.com/)
Api para administrar franquicias, sucursales y productos

Se desarrolla Api para gestion de franquicias mediante las siguientes especificaciones tecnicas: 

Tecnologia fuente: Proyecto maven Java 17 Spring boot api.

Base de datos: Base de datos alojada en Amazon RDS.
Endpoint: company.cx2cekwwm5l6.us-east-2.rds.amazonaws.com:3306

# Empoints desarrollados: 


# 1.Agregar una nueva Franquicia:
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/franquicia/add/{nombre_franquicia}
EJEMPLO DE LLAMADO: http://localhost:8080/franquicia/add/franquicia_prueba_api2
RESPUESTA: Franquicia {nombre_franquicia} guardada correctamente


# 2.Agregar una nueva nueva sucursal a una franquicia: 
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/sucursal/add/franquicia

EJEMPLO DE LLAMADO:
 http://localhost:8080/sucursal/add/franquicia
BODY: {
"nombre": "sucursal_api_create3",
"idFranquicia":1
}

RESPUESTA: Asociacion correcta entre la sucursal {nombre} y la franquicia {idFranquicia}.


# 3.Agregar un nuevo producto a una sucursal: 
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/producto/add
EJEMPLO DE LLAMADO:
 http://localhost:8080/producto/add
BODY: {
"cantidad": 250, 
"nombre": "producto_api3", 
"sucursalId": "5"
}
RESPUESTA: El producto {nombre} se creo correctamente en la sucursal {sucursalId}


# 4.Eliminar un producto de una sucursal: 
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/producto/delete/

EJEMPLO DE LLAMADO:
 http://localhost:8080/producto/delete/

BODY:
{"id": 24}

RESPUESTA:  Se elimino el producto {nombre producto } de la sucursal {id}


# 5.Modificar stock de un producto: 
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/producto/update/

EJEMPLO DE LLAMADO:
 http://localhost:8080/producto/update/

BODY:
{
"id": 23,
"cantidad":2000
}

RESPUESTA:  Se actualizo la cantidad del producto {nombre_producto}; CANTIDAD ANTERIOR: {cantidad_anterior}; NUEVA CANTIDAD: {nueva_cantidad}


# 6.Obtener listado de productos con mayor stock por sucursal, dado el id_franquicia por parametro: 
TIPO DE LLAMADO: GET
URL LOCAL: http://localhost:8080/productos/mayor/stock/{Id_franquicia}

EJEMPLO DE LLAMADO:
 http://localhost:8080/productos/mayor/stock/1

RESPUESTA:  Listado de productos con mayor cantidad por sucursal en la franquicia 1: 
[
    {
        "id": 2,
        "cantidad": 30,
        "nombre": "producto_1_2",
        "sucursalId": 1
    },
    {
        "id": 4,
        "cantidad": 90,
        "nombre": "producto_2_2",
        "sucursalId": 2


# PUNTOS EXTRAS: 

 # Modificar el nombre de una franquicia : 
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/franquicia/update/nombre

EJEMPLO DE LLAMADO:
 http://localhost:8080/franquicia/update/nombre

BODY: {"name":"franquicia_prueba_api2", "id": 5}


#  Modificar el nombre de una sucursal: 
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/sucursal/update/nombre

EJEMPLO DE LLAMADO:
 http://localhost:8080/franquicia/update/nombre

BODY: {"nombre":"sucursal_renombrada1", "id": 10}



#  Modificar el nombre de un producto: 
TIPO DE LLAMADO: POST
URL LOCAL: http://localhost:8080/producto/update/nombre

EJEMPLO DE LLAMADO:
 http://localhost:8080/producto/update/nombre

BODY: {"nombre":"producto_renombrado1", "id": 23}



