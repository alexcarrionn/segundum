# SEGUNDUM
Entrega del proyecto de la asignatura de Aplicaciones distribuidas del 4º curso de la carrera de Ingeniería Informática de la UMU.  
Alumnos: 
- Alejandro Carrión Jordán (a.carrionjorda@um.es)
- Borja Sancho Fernández (borja.s.f@um.es)  
- Profesora:
MARIA DEL CARMEN LEGAZ GARCIA

**URL del repositorio:** https://github.com/alexcarrionn/segundum.git

# En que consiste este proyecto
Este proyecto consiste en el desarrollo de una aplicación que facilite a los usuarios la compra-venta de productos de segunda mano. Esta plataforma permite a los usuarios publicar productos para su venta y a otros usuarios adquirirlos de forma sencilla.  
Este proyecto utiliza tecnologías como el uso de la API JAXB de XML para la carga de jerarquías de categorías, el uso de JPA para la persistencia de las entidades en MySQL y el uso de 
la librería de componentes PrimeFaces y de las libreías de FSF.  

# Ejecución del proyecto

Para poder ejecutar el proyecto es necesario tener instalado Java 8 o superior y Maven.
Además, es necesario tener una base de datos MySQL configurada y en funcionamiento.

1) Clonar el repositorio del proyecto desde GitHub:
```bash
git clone https://github.com/alexcarrionn/segundum.git
```

2) Aceder al repositorio clonado:
```bash
cd segundum
```

3) Configurar la conexión a la base de datos, situada en el archivo `persistence.xml`, ubicado en el proyecto clonado en la carpeta `src/main/resources/META-INF/`. Modificar los parámetros de conexión (usuario y contraseña) según la configuración de tu base de datos MySQL.
```java
<property name="javax.persistence.jdbc.user" value="root" />
<property name="javax.persistence.jdbc.password"  value="practicas" />
```

En root y practicas se deben poner el usuario y la contraseña de la base de datos MySQL.

4) Construir el proyecto utilizando Maven:
Primero asegurarse de tener Maven instalado. Luego, ejecutar el siguiente comando en la terminal desde la raíz del proyecto:
```bash
mvn clean install
mvn jetty:run
```
con ello pondremos a ejecutar nuestra aplicación en un servidor Jetty embebido, situado en http://localhost:8080/index.xhtml

5) Acceder a la aplicación web a través de un navegador web utilizando la URL anterior. 


# Manual de uso de la aplicación 

Nada más acceder a la aplicación, se nos redirigirá a la página principal donde podremos buscar los productos disponibles en la plataforma y donde podremos iniciar sesión. 

<image src ="imagenes/pagina de inicio.png">

Para que el usuario pueda buscar un producto, deberá pinchar en el botón de "Buscar productos" en la parte media de la página. Esto le llevará a una página donde podrá filtrar los productos por categoría, precio y palabras clave. El usuario para poder buscar productos no necesita estar registrado en la plataforma.

<image src ="imagenes/filtros_productos.png">

Al pinchar en el botón de "Buscar", se mostrarán los productos que coincidan con los filtros aplicados en la tabla.

<image src ="imagenes/buscar productos.png">

Si el usuario desea vender sus propios productos o administrar sus productos, deberá iniciar de sesión en la plataforma. En caso de no estar registrado, podrá hacerlo pinchando en el botón de "Registrarse" en la parte superior derecha de la página principal.

<image src ="imagenes/registro.png">

En la página de registro, el usuario deberá rellenar el formulario que se le presenta con sus datos personales y de acceso. Una vez que se haya rellenado el formulario, deberá pinchar en el botón de "Registrarse" para completar el proceso de registro.

<image src ="imagenes/formulario_registro.png">

Si el usuario ya está registrado en la aplicación, podrá iniciar sesión desde la página principal. Para ello selecciona el botón de "Iniciar Sesión" en la parte superior izqquierda. 

<image src ="imagenes/inicioSesion.png">

En la página de inicio de sesión, el usuario deberá introducir su correo y su contraseña. 

<image src ="imagenes/formulario_inicio_sesion.png">

Una vez iniciada la sesión, el usuario ya podrá vender nuevos productos y administrar sus productos.

<image src="imagenes/administrarYvender.png">

Para vender un nuevo producto, el usuario deberá pinchar en el botón de "Vender Producto". Esto le llevará a una página donde podrá rellenar un formulario con los datos del producto que desea vender.

<image src ="imagenes/venderProducto.png">

<image src = "imagenes/producto_creado.png">

Una vez que el usuario le de a "Crear Producto", este se añadirá a la lista de productos del usuario y estará disponible para hacerle modificaciones o para los otros usuarios.

Para poder administrar los productos del usuario, este debe pinchar en el botón de "Gestionar Mis Prductos" en la página de inicio o a "Ver mis productos" al crear uno nuevo. 
Esto le llevará a una página donde podrá ver una tabla con todos los productos que ha creado. 

<image src ="imagenes/tabla_mis_productos.png">

Desde esta página, el usuario podrá editar sus productos dandole al botón representado con un lápiz. 

<image src ="imagenes/lapiz.png">

<image src ="imagenes/editar_producto.png">

El usuario al modificar los datos del producto, deberá pinchar en el botón "Guardar Cambios" para que se apliquen las modificaciones.


# Decisión de diseño tomada 

Para poder desarrollar este proyecto, se han tomado varias decisiones de diseño importantes. Todas ellas han sido sacadas del proyecto `encuestas`, proyecto que se ha desarrollado en la asignatura durante el curso en las clases de prácticas. Hemos intentado que fuera lo más parecido posible a dicho proyecto para facilitar el desarrollo y la comprensión del mismo. Además se ha intentado que la aplicación fuera lo más usable posible, utilizando componentes de PrimeFaces para mejorar la experiencia del usuario.


