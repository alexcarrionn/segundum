package servicio;

import modelo.Producto;
import modelo.Usuario;
import modelo.Categoria;
import modelo.EstadoProducto;
import modelo.LugarRecogida;
import repositoriosModelo.IRepositorioProducto;
import repositoriosModelo.IRepositorioUsuario;
import repositoriosModelo.IRepositorioCategorias;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios; // Necesario si obtienes RepositorioAdHoc desde aquí
import repositorio.RepositorioException;
import repositorio.RepositorioProductoAdHoc; // Interfaz AdHoc
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList; // Para el return temporal en buscarProductos

public class ServicioProductos implements IServiciosProductos {

	private IRepositorioProducto repositorioProducto;
	private IRepositorioUsuario repositorioUsuario;
	private IRepositorioCategorias repositorioCategoria;


	// Constructor para inyección de dependencias
	public ServicioProductos(IRepositorioProducto repositorioProducto,
							 IRepositorioUsuario repositorioUsuario,
							 IRepositorioCategorias repositorioCategoria) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioCategoria = repositorioCategoria;
		
	}

	@Override
	public String altaProducto(String titulo, String descripcion, double precio, EstadoProducto estado,
							String idCategoria, boolean envioDisponible, String idVendedor)
							throws EntidadNoEncontrada, IllegalArgumentException, RepositorioException {

		if (titulo == null || titulo.trim().isEmpty() || precio < 0 || estado == null || idCategoria == null || idVendedor == null) {
			 throw new IllegalArgumentException("Faltan datos obligatorios o son inválidos para dar de alta el producto.");
		}

		Usuario vendedor = repositorioUsuario.getById(idVendedor);
		if (vendedor == null) {
			throw new EntidadNoEncontrada("No se encontró el usuario vendedor con ID: " + idVendedor);
		}

		Categoria categoria = repositorioCategoria.getById(idCategoria);
		if (categoria == null) {
			throw new EntidadNoEncontrada("No se encontró la categoría con ID: " + idCategoria);
		}

		Producto nuevoProducto = new Producto();
		nuevoProducto.setTitulo(titulo);
		nuevoProducto.setDescripcion(descripcion);
		nuevoProducto.setPrecio(precio);
		nuevoProducto.setEstado(estado);
		nuevoProducto.setCategoria(categoria);
		nuevoProducto.setEnvioDisponible(envioDisponible);
		nuevoProducto.setVendedor(vendedor);
		nuevoProducto.setFechaPublicacion(LocalDateTime.now());
		nuevoProducto.setVisualizaciones(0);
		// lugarRecogida se asigna después

		repositorioProducto.add(nuevoProducto);

		if (nuevoProducto.getId() == null) {
			 System.err.println("Advertencia: El ID del producto no se generó inmediatamente después de add(). Considerar flush.");
			 // Considerar lanzar una excepción o buscar el producto si el ID es estrictamente necesario aquí.
		}
		return nuevoProducto.getId();
	}

	@Override
	public void asignarLugarRecogida(String idProducto, String descripcionLugar, double longitud, double latitud)
						throws EntidadNoEncontrada, RepositorioException {

		Producto producto = repositorioProducto.getById(idProducto);

		if (producto == null) {
			throw new EntidadNoEncontrada("No se encontró el producto con ID: " + idProducto);
		}
		
		if (descripcionLugar == null || descripcionLugar.trim().isEmpty()) {
		    throw new IllegalArgumentException("La descripción del lugar de recogida no puede estar vacía.");
		}
		LugarRecogida nuevoLugar = new LugarRecogida(descripcionLugar, longitud, latitud);

		producto.setLugarRecogida(nuevoLugar);

		try {
			repositorioProducto.update(producto);
		} catch (RepositorioException e) {
			throw new RepositorioException("Error al actualizar el lugar de recogida para el producto ID: " + idProducto, e);
		}
	}

	@Override
	public void modificarProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
						throws EntidadNoEncontrada, IllegalArgumentException, RepositorioException {

		Producto producto = repositorioProducto.getById(idProducto);
		if (producto == null) {
			throw new EntidadNoEncontrada("No se encontró el producto con ID: " + idProducto);
		}

		boolean modificado = false; 

		if (nuevoPrecio != null) {
			if (nuevoPrecio < 0) {
				throw new IllegalArgumentException("El precio no puede ser negativo.");
			}
			producto.setPrecio(nuevoPrecio);
			modificado = true;
		}

		if (nuevaDescripcion != null) {
			producto.setDescripcion(nuevaDescripcion);
			modificado = true;
		}

		if (modificado) {
			try {
				repositorioProducto.update(producto);
			} catch (RepositorioException e) {
				throw new RepositorioException("Error al modificar el producto ID: " + idProducto, e);
			}
		} else {
			System.out.println("No se especificaron cambios para el producto ID: " + idProducto);
		}
	}
	
	//MÉTODO IMPLÍCITO PARA LA HISTORIA 6 Y 7
	@Override
	public void anadirVisualizacion(String idProducto)
						throws EntidadNoEncontrada, RepositorioException {

		Producto producto = repositorioProducto.getById(idProducto);
		if (producto == null) {
			throw new EntidadNoEncontrada("No se encontró el producto con ID: " + idProducto + " para añadir visualización.");
		}

		int visualizacionesActuales = producto.getVisualizaciones();
		producto.setVisualizaciones(visualizacionesActuales + 1);

		try {
			repositorioProducto.update(producto);
		} catch (RepositorioException e) {
			throw new RepositorioException("Error al añadir visualización al producto ID: " + idProducto, e);
		}
	}

	@Override
	public List<Producto> historialDelMes(int mes, int ano) throws RepositorioException {
		// TODO: Implementar lógica Historia 6
		throw new UnsupportedOperationException("Método historialDelMes no implementado todavía");
	}

	@Override
	public List<Producto> buscarProductos(String idCategoria, String textoDescripcion, EstadoProducto estadoMinimo, Double precioMax)
	                    throws RepositorioException {
		// TODO: Implementar lógica Historia 7
		
		System.out.println("TODO: Implementar búsqueda de productos..."); // Mensaje temporal

		RepositorioProductoAdHoc repoAdHoc = FactoriaRepositorios.getRepositorio(getClass());
		if (repoAdHoc != null) {
 		    return repoAdHoc.findProductosByCriteria(idCategoria, textoDescripcion, estadoMinimo, precioMax);
		} else {
		    System.err.println("Error: No se pudo obtener RepositorioProductoAdHoc desde FactoriaRepositorios.");
		    return new ArrayList<>(); // Devolver lista vacía en caso de error al obtener el repo
		}
		// throw new UnsupportedOperationException("Método buscarProductos no implementado todavía"); // Quitar esto al implementar
	}
}