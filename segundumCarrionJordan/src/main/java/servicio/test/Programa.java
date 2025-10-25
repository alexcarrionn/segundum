package servicio.test;

import java.time.LocalDate;

import modelo.Categoria;
import repositorio.FactoriaRepositorios;
import repositoriosAdHoc.*;
import servicio.FactoriaServicios;
import servicio.IServicioUsuario;
import servicio.ServicioCategorias;

/**
 * Programa principal para probar la funcionalidad del proyecto segundum.
 */
public class Programa {
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Programa de prueba de servicios iniciado.");
		// Aquí se pueden agregar llamadas a los servicios para probar su funcionalidad.
		
		//primero creamos una instancia del servicio de usuario
		IServicioUsuario servicioUsuario = FactoriaServicios.getServicio(IServicioUsuario.class);
		
		
		//Creamos el usuario de prueba 
		String nombre = "Pepe";
		String apellidos = "Pérez";
		String email = "pruebas@um.es"; 
		String clave = "password123";
		LocalDate fechaNacimiento = LocalDate.now();

		//Creamos el usuario
		
		String idUsuario = servicioUsuario.registrarUsuario(nombre, apellidos, email, clave, fechaNacimiento, null);
		
		System.out.println("Usuario creado con ID: " + idUsuario + " con nombre: " + nombre + " " + apellidos+ ", email: " 
		+ email + ", clave: " + clave + " y  fechaNacimiento: " + fechaNacimiento);
		
		//Ahora el usuario quiere cambiar su email!! 
		String nuevoEmail = "asyyujd@gmail.com"; 
		
		servicioUsuario.actualizarDatosUsuario(idUsuario, nombre, apellidos, nuevoEmail, clave, fechaNacimiento, null);
		
		System.out.println("Usuario con ID: " + idUsuario + " ha cambiado su email a: " + nuevoEmail);

		//Vamos a comprobar las categorias 
	    //Hacemos un nuevo usuario ue se admin 
		//Creamos el usuario de prueba 
				String nombre2 = "admin";
				String apellidos2 = "Admin";
				String email2 = "adasmin@um.es"; 
				String clave2 = "password123";
				LocalDate fechaNacimiento2 = LocalDate.now();

				//Creamos el usuario
				
				String idUsuario2 = servicioUsuario.registrarUsuario(nombre2, apellidos2, email2, clave2, fechaNacimiento2, null);
				
				//Asignamos el rol de admin
				servicioUsuario.asignarRolAdmin(idUsuario2);
				
				//una vez el usuario es admin, cargamos la categoria 
				
				ServicioCategorias servicioCategorias = new ServicioCategorias();
				
				servicioCategorias.cargarCategorias("src/main/java/META-INF/categoriasXML/Arte_y_ocio.xml");
				
				//Categorias cargadas
				System.out.println("Categorias cargadas");
				
				//ahora modificamos una categoria
				
				//obtenemos el id de la categoria a modificar
				String idCategoria = "5709";
				servicioUsuario.modificarCategoria(idUsuario2, idCategoria , "Categoria de Arte y Ocio modificada por admin");
				
				System.out.println("Categoria con id: " + idCategoria + " modificada por el usuario admin con id: " + idUsuario2);
				
				//Ahora probamos el conseguir las categorias raiz 
				
				RepositorioCategoriasAdHoc repositorioAdHoc = FactoriaRepositorios.getRepositorio(RepositorioCategoriasAdHoc.class);
				
				servicioCategorias.obtenerCategoriasRaiz().forEach(categoria -> {
					System.out.println("Categoria raiz: " + categoria.getId() + " - " + categoria.getNombre());
				});
				
				//Probamos a buscar subcategorias de una categoria
				String idCategoriaPadre = "5709";
				//buscamos la categoria padre
				Categoria categoriaPadre = repositorioAdHoc.getById(idCategoriaPadre); 
				
				servicioCategorias.obtenerDescendientes(idCategoriaPadre).forEach(categoria -> {
					System.out.println("Subcategoria de " + idCategoriaPadre + " - " + categoriaPadre.getNombre() + ": " + categoria.getId() + " - " + categoria.getNombre());
				});
				
	
	}
}