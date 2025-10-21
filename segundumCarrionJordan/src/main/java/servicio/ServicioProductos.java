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
	// Podrías necesitar inyectar el Repositorio AdHoc también si no lo obtienes de la factoría
	// private RepositorioProductoAdHoc repositorioProductoAdHoc;

	// Constructor para inyección de dependencias
	public ServicioProductos(IRepositorioProducto repositorioProducto,
							 IRepositorioUsuario repositorioUsuario,
							 IRepositorioCategorias repositorioCategoria) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioCategoria = repositorioCategoria;
		// Si inyectas el AdHoc:
		// this.repositorioProductoAdHoc = FactoriaRepositorios.getRepositorio(Producto.class, RepositorioProductoAdHoc.class);
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
		// TODO: Implementar lógica Historia 5
		// Buscar Producto, crear LugarRecogida, setearlo y hacer update.
		throw new UnsupportedOperationException("Método asignarLugarRecogida no implementado todavía");
	}

	@Override
	public void modificarProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
	                    throws EntidadNoEncontrada, IllegalArgumentException, RepositorioException {
		// TODO: Implementar lógica Historia 4
		// Buscar Producto, validar precio si viene, setear campos no nulos y hacer update.
		throw new UnsupportedOperationException("Método modificarProducto no implementado todavía");
	}

	@Override
	public void anadirVisualizacion(String idProducto)
	                    throws EntidadNoEncontrada, RepositorioException {
		// TODO: Implementar lógica
		// Buscar Producto, incrementar visualizaciones y hacer update.
		throw new UnsupportedOperationException("Método anadirVisualizacion no implementado todavía");
	}

	@Override
	public List<Producto> historialDelMes(int mes, int ano) throws RepositorioException {
		// TODO: Implementar lógica Historia 6
		// Necesitará una consulta específica (posiblemente AdHoc o Criteria API).
		throw new UnsupportedOperationException("Método historialDelMes no implementado todavía");
	}

	@Override
	public List<Producto> buscarProductos(String idCategoria, String textoDescripcion, EstadoProducto estadoMinimo, Double precioMax)
	                    throws RepositorioException {
		// TODO: Implementar lógica Historia 7
		// Obtener RepositorioProductoAdHoc (vía factoría o inyección)
		// Si idCategoria != null, obtener descendientes (posiblemente vía IServiciosCategorias)
		// Llamar a repositorioProductoAdHoc.findProductosByCriteria(...)
		System.out.println("TODO: Implementar búsqueda de productos..."); // Mensaje temporal
		// Devolver resultado temporal para que compile:
		RepositorioProductoAdHoc repoAdHoc = FactoriaRepositorios.getRepositorio(Producto.class, RepositorioProductoAdHoc.class);
		if (repoAdHoc != null) {
		    // Llamada de ejemplo (aún no implementada en el repo adhoc)
		    return repoAdHoc.findProductosByCriteria(idCategoria, textoDescripcion, estadoMinimo, precioMax);
		} else {
		    System.err.println("Error: No se pudo obtener RepositorioProductoAdHoc desde FactoriaRepositorios.");
		    return new ArrayList<>(); // Devolver lista vacía en caso de error al obtener el repo
		}
		// throw new UnsupportedOperationException("Método buscarProductos no implementado todavía"); // Quitar esto al implementar
	}
}