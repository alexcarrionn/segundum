package servicio.test;

import java.time.LocalDate;
import java.util.List;

import dto.ProductoDTO;
import modelo.Categoria;
import modelo.EstadoProducto; 
import modelo.Producto; 
import repositorio.FactoriaRepositorios;
import repositoriosAdHoc.*;
import servicio.FactoriaServicios;
import servicio.IServicioUsuario;
import servicio.IServiciosCategorias;
import servicio.IServiciosProductos;

/**
 * Programa principal para probar la funcionalidad del proyecto segundum.
 */
public class Programa {
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Programa de prueba de servicios iniciado.");
		// Aquí se pueden agregar llamadas a los servicios para probar su funcionalidad.
		
		//IDs declarados aquí para acceso global en el main (BORJA)
		String idUsuario = null;
		String idUsuario2 = null;
		String idProductoPrueba = null;
		String idCategoria="5709";
		
		//Obtenemos todos los servicios necesarios
		IServicioUsuario servicioUsuario = FactoriaServicios.getServicio(IServicioUsuario.class);
		IServiciosProductos servicioProductos = FactoriaServicios.getServicio(IServiciosProductos.class);
		IServiciosCategorias servicioCategorias = FactoriaServicios.getServicio(IServiciosCategorias.class);
		
		//--HECHO POR ALEJANDRO--
		//Creamos el usuario de prueba 
		String nombre = "Pepe";
		String apellidos = "Pérez";
		String email = "pruebas@um.es"; 
		String clave = "password123";
		LocalDate fechaNacimiento = LocalDate.now().minusYears(20); //(BORJA): correción para que sea una fecha válida

		//Creamos el usuario INCIALIZADO ARRIBA POR BORJA
		
		idUsuario = servicioUsuario.registrarUsuario(nombre, apellidos, email, clave, fechaNacimiento, null, false);
		
		System.out.println("Usuario creado con ID: " + idUsuario + " con nombre: " + nombre + " " + apellidos+ ", email: " 
		+ email + ", clave: " + clave + " y  fechaNacimiento: " + fechaNacimiento);
		
		//Ahora el usuario quiere cambiar su email!! 
		String nuevoEmail = "asyyujd@gmail.com"; 
		
		//BORJA: añado if por si fallo la creación del usuario
		if (idUsuario != null) {

		servicioUsuario.actualizarDatosUsuario(idUsuario, nombre, apellidos, nuevoEmail, clave, fechaNacimiento, null);
		
		System.out.println("Usuario con ID: " + idUsuario + " ha cambiado su email a: " + nuevoEmail);
		}
		//Vamos a comprobar las categorias 
	    //Hacemos un nuevo usuario que sea admin 
		//Creamos el usuario de prueba 
				String nombre2 = "admin";
				String apellidos2 = "Admin";
				String email2 = "adasmin@um.es"; 
				String clave2 = "password123";
				LocalDate fechaNacimiento2 = LocalDate.now();

				//Creamos el usuario
				
				idUsuario2 = servicioUsuario.registrarUsuario(nombre2, apellidos2, email2, clave2, fechaNacimiento2, null, false);
				
				//Asignamos el rol de admin
				servicioUsuario.asignarRolAdmin(idUsuario2);
				
				//una vez el usuario es admin, cargamos la categoria 
								
				servicioCategorias.cargarCategorias("src/main/java/META-INF/categoriasXML/Arte_y_ocio.xml");
				
				//Categorias cargadas
				System.out.println("Categorias cargadas");
				
				//ahora modificamos una categoria
				
				//obtenemos el id de la categoria a modificar (CATEGORIA INICIALIZADA ARRIBA POR BORJA)
				servicioUsuario.modificarCategoria(idUsuario2, idCategoria , "Categoria de Arte y Ocio modificada por admin");
				//Tambien se podría hacer con categoría:
				//servicioCategorias.modificarCategoria(idCategoria , "Categoria de Arte y Ocio modificada por admin");
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
				
				// --- HECHO POR BORJA: PRUEBAS DE PRODUCTOS ---
				System.out.println("\n\n--- INICIO PRUEBAS PRODUCTOS ---");

				// Comprobar si se pudo obtener/crear el idUsuario de la parte anterior
				if (idUsuario == null) {
					System.err.println("ID de usuario de prueba no disponible. Omitiendo pruebas de productos.");
				} else {
					// --- Prueba Historia 3: Alta Producto ---
					System.out.println("\n[Prueba H3] Alta Producto...");
					idProductoPrueba = servicioProductos.altaProducto(
						"Set Acuarelas Van Gogh", "Caja 12 pastillas + pincel. Poco uso.", 18.50,
						EstadoProducto.COMO_NUEVO, idCategoria, true, idUsuario
					);
					System.out.println("--> Producto creado con ID: " + idProductoPrueba);

					// --- Prueba Historia 5: Asignar Lugar Recogida ---
					// Solo se ejecuta si el alta fue exitosa (idProductoPrueba no es null)
					if (idProductoPrueba != null) {
						System.out.println("\n[Prueba H5] Asignar Lugar Recogida...");
						servicioProductos.asignarLugarRecogida(idProductoPrueba, "Junto a estatua Plaza Héroes de Cavite", -0.985, 37.598);
						System.out.println("--> Lugar de recogida asignado.");

						// --- Prueba Historia 4: Modificar Producto ---
						System.out.println("\n[Prueba H4] Modificar Producto...");
						servicioProductos.modificarProducto(idProductoPrueba, 17.0, "Caja 12 pastillas + pincel. Poco uso. Algún color gastado.");
						System.out.println("--> Producto modificado.");

						// --- Prueba Añadir Visualización ---
						System.out.println("\n[Prueba] Añadir Visualización...");
						servicioProductos.anadirVisualizacion(idProductoPrueba);
						System.out.println("--> Visualización añadida.");

						// --- Prueba Historia 6: Historial del Mes ---
						System.out.println("\n[Prueba H6] Historial del Mes...");
						int mesActual = LocalDate.now().getMonthValue();
						int anoActual = LocalDate.now().getYear();
						List<ProductoDTO> historial = servicioProductos.historialDelMes(mesActual, anoActual);
						System.out.println("--> Historial obtenido para " + mesActual + "/" + anoActual + ". Productos: " + historial.size());
						historial.forEach(p -> System.out.println("    - " + p.getTitulo() + " (ID: "+ p.getId() + ", Vis: " + p.getVisualizaciones() + ")"));

						// --- Prueba Historia 7: Buscar Productos ---
						System.out.println("\n[Prueba H7] Buscar Productos...");
						System.out.println("   Buscando 'pincel'...");
						List<Producto> encontrados1 = servicioProductos.buscarProductos(null, "pincel", null, null);
						System.out.println("   --> Encontrados por 'pincel': " + encontrados1.size());
						encontrados1.forEach(p -> System.out.println("       - " + p.getTitulo() + " (ID: "+p.getId()+")"));

						System.out.println("\n   Buscando en categoría '" + idCategoria + "' (y desc.) <= 20 EUR...");
						List<Producto> encontrados2 = servicioProductos.buscarProductos(idCategoria, null, null, 20.0);
						System.out.println("   --> Encontrados por categoría '" + idCategoria + "' y precio: " + encontrados2.size());
						encontrados2.forEach(p -> System.out.println("       - " + p.getTitulo() + " (ID: "+p.getId()+", €" + p.getPrecio() + ")"));

						System.out.println("\n   Buscando con estado mínimo COMO_NUEVO...");
						List<Producto> encontrados3 = servicioProductos.buscarProductos(null, null, EstadoProducto.COMO_NUEVO, null);
						System.out.println("   --> Encontrados por estado >= COMO_NUEVO: " + encontrados3.size());
						encontrados3.forEach(p -> System.out.println("       - " + p.getTitulo() + " (ID: "+p.getId()+", Estado: " + p.getEstado() + ")"));

					} 
				}
				
				System.out.println("\n--- FIN PRUEBAS PRODUCTOS ---");
				
				
	
	}
}