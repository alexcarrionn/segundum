# SEGUNDUM
Entrega del proyecto de la asignatura **Aplicaciones Distribuidas**, 4º curso del Grado en Ingeniería Informática (UMU).  

**Integrantes:**  
- Alejandro Carrión Jordán (a.carrionjorda@um.es)  
- Borja Sancho Fernández (borja.s.f@um.es)

**Profesora:**  
María del Carmen Legáz García

**Repositorio del proyecto:** https://github.com/alexcarrionn/segundum.git

---

# Descripción del proyecto
SEGUNDUM es una aplicación web diseñada para facilitar la compra-venta de productos de segunda mano entre usuarios.  
Permite publicar artículos, filtrarlos por distintos criterios y adquirirlos de forma sencilla.

Tecnologías utilizadas:
- JAXB (XML) para cargar jerarquías de categorías.  
- JPA con MySQL para la persistencia de entidades.  
- PrimeFaces y librerías de FSF para la interfaz.

---

# Ejecución del proyecto

Requisitos previos:
- Java 8 o superior  
- Maven  
- Base de datos MySQL activa y configurada  

## 1. Clonar el repositorio
Comando:  
`git clone https://github.com/alexcarrionn/segundum.git`

## 2. Acceder al directorio del proyecto
Comando:  
`cd segundum`

## 3. Configurar conexión a MySQL
Editar el archivo:  
`src/main/resources/META-INF/persistence.xml` 

Modificar las propiedades:  
`javax.persistence.jdbc.user` → tu usuario  
`javax.persistence.jdbc.password` → tu contraseña  

## 4. Construir y ejecutar
Comandos:  
`mvn clean install`  
`mvn jetty:run`

Aplicación disponible en: http://localhost:8080/index.xhtml

## 5. Acceso desde navegador
Escribir la URL anterior en cualquier navegador.

---

# Manual de uso de la aplicación

- Al entrar en la web, se muestra la página principal desde la que se pueden buscar productos o iniciar sesión.

<image src="imagenes/pagina de inicio.png" style="width:80%; margin-bottom:25px;">

---

## Buscar productos
1) Para realizar una búsqueda, seleccionar **Buscar productos**.  
No es necesario estar registrado para acceder a esta ventana.

<image src="imagenes/filtros_productos.png" style="width:80%; margin-bottom:25px;">

2) Tras elegir filtros *(Palabras Clave, Precio, Estado, Categoría)*, seleccionar **Buscar**.

<image src="imagenes/buscar productos.png" style="width:80%; margin-bottom:25px;">

---

## Registro de usuarios
- Para vender o gestionar productos es necesario tener una cuenta.  
Si no la tienes, selecciona **Registrarse**.

<image src="imagenes/registro.png">

- Rellena el formulario con los datos correspondientes y confirma con **Registrarse**.

<image src="imagenes/formulario_registro.png" style="width:80%; margin-bottom:25px;">

---

## Inicio de sesión
- Si ya tienes cuenta, selecciona **Iniciar Sesión**.

<image src="imagenes/inicioSesion.png">

- Escribe correo y contraseña:

<image src="imagenes/formulario_inicio_sesion.png" style="width:80%; margin-bottom:25px;">

- Tras iniciar sesión podrás, además de buscar los distintos productos, administrar tus productos o publicar nuevos.

<image src="imagenes/administrarYvender.png" style="width:80%; margin-bottom:25px;">

---

## Vender productos
- Para añadir un nuevo producto, pulsa **Vender Producto** y completa el formulario.

<image src="imagenes/venderProducto.png" style="width:80%; margin-bottom:25px;">

- Una vez creado, aparecerá en tu lista de artículos:

<image src="imagenes/producto_creado.png">

---

## Gestionar mis productos
- Para acceder a tus productos, selecciona **Gestionar Mis Productos**.  
Otra forma de acceder a esta pantalla es seleccionando **Ver Mis Productos** en la ventana emergente al crear uno de éstos.

<image src="imagenes/tabla_mis_productos.png" style="width:80%; margin-bottom:25px;">

- Para editar un producto, pulsa el icono del *lápiz*:

<image src="imagenes/lapiz.png">

- En la pantalla de edición, podrás modificar *Precio* y/o *Descripción* del producto:  
<image src="imagenes/editar_producto.png" style="width:80%; margin-bottom:25px;">

- Tras realizar cambios, pulsa **Guardar Cambios**.

---

# Decisiones de diseño

El proyecto sigue las directrices del proyecto `encuestas`, utilizado en las prácticas de la asignatura.  
Se ha mantenido el estilo y estructura para facilitar el desarrollo.  
Además, se han empleado componentes de *PrimeFaces* para mejorar la experiencia y usabilidad de la aplicación.
