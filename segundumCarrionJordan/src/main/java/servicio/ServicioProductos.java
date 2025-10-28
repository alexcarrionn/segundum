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
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio; 
import repositorio.RepositorioException;
import repositoriosAdHoc.RepositorioCategoriasAdHoc;
import repositoriosAdHoc.RepositorioProductoAdHoc;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ServicioProductos implements IServiciosProductos {

	private IRepositorioProducto repositorioProducto = FactoriaRepositorios.getRepositorio(Producto.class);
	private IRepositorioUsuario repositorioUsuario = FactoriaRepositorios.getRepositorio(Usuario.class);
	private IRepositorioCategorias repositorioCategoria = FactoriaRepositorios.getRepositorio(Categoria.class);

	public ServicioProductos() {
	}

	@Override
	public String altaProducto(String titulo, String descripcion, double precio, EstadoProducto estado,
							String idCategoria, boolean envioDisponible, String idVendedor)
							throws EntidadNoEncontrada, IllegalArgumentException, RepositorioException {

		if (titulo == null || titulo.trim().isEmpty() || precio < 0 || estado == null || idCategoria == null || idVendedor == null) {
			 throw new IllegalArgumentException("Faltan datos obligatorios o son inválidos para dar de alta el producto.");
		}
		Usuario vendedor = repositorioUsuario.getById(idVendedor);
		Categoria categoria = repositorioCategoria.getById(idCategoria);

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

		repositorioProducto.add(nuevoProducto);

		if (nuevoProducto.getId() == null) {
			 System.err.println("Advertencia: El ID del producto no se generó inmediatamente después de add().");
		}
		return nuevoProducto.getId();
	}

	@Override
	public void asignarLugarRecogida(String idProducto, String descripcionLugar, double longitud, double latitud)
						throws EntidadNoEncontrada, RepositorioException, IllegalArgumentException {

		Producto producto = repositorioProducto.getById(idProducto);

		if (descripcionLugar == null || descripcionLugar.trim().isEmpty()) {
		    throw new IllegalArgumentException("La descripción del lugar de recogida no puede estar vacía.");
		}
		LugarRecogida nuevoLugar = new LugarRecogida(descripcionLugar, longitud, latitud);
		producto.setLugarRecogida(nuevoLugar);

		repositorioProducto.update(producto);
	}

	@Override
	public void modificarProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
						throws EntidadNoEncontrada, IllegalArgumentException, RepositorioException {

		Producto producto = repositorioProducto.getById(idProducto);
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
			repositorioProducto.update(producto);
		}
	}

	@Override
	public void anadirVisualizacion(String idProducto)
						throws EntidadNoEncontrada, RepositorioException {

		Producto producto = repositorioProducto.getById(idProducto);
		int visualizacionesActuales = producto.getVisualizaciones();
		producto.setVisualizaciones(visualizacionesActuales + 1);
		repositorioProducto.update(producto);
	}

	@Override
	public List<Producto> historialDelMes(int mes, int ano) throws RepositorioException, IllegalArgumentException {
		if (mes < 1 || mes > 12 || ano <= 0) {
		    throw new IllegalArgumentException("Mes o año inválido.");
		}
		RepositorioProductoAdHoc repoAdHoc = FactoriaRepositorios.getRepositorio(RepositorioProductoAdHoc.class);
		return repoAdHoc.findProductosByMonthAndYearOrderedByVisualizaciones(mes, ano);
	}

	@Override
	public List<Producto> buscarProductos(String idCategoria, String textoDescripcion, EstadoProducto estadoMinimo, Double precioMax)
	                    throws RepositorioException, EntidadNoEncontrada { 

		List<String> idsCategoriasParaBuscar = null;

		if (idCategoria != null && !idCategoria.trim().isEmpty()) {
			idsCategoriasParaBuscar = new ArrayList<>();
			IServiciosCategorias servicioCategorias = null;
			IRepositorioCategorias repoCat = null;

			try {
				servicioCategorias = FactoriaServicios.getServicio(IServiciosCategorias.class);
				repoCat = FactoriaRepositorios.getRepositorio(Categoria.class);

				Categoria catRaiz = repoCat.getById(idCategoria); 
				idsCategoriasParaBuscar.add(catRaiz.getId());

				List<Categoria> descendientes = servicioCategorias.recuperarTodosDescendientes(idCategoria); 

				if (descendientes != null) {
					for (Categoria cat : descendientes) {
						if (!idsCategoriasParaBuscar.contains(cat.getId())) {
							idsCategoriasParaBuscar.add(cat.getId());
						}
					}
				}

			} catch (EntidadNoEncontrada | RepositorioException e) {
				throw e;
			} catch (RuntimeException e) { 
				throw new RepositorioException("Error al obtener servicio/repositorio de categorías para búsqueda", e); // Envolver en checked
			}
            if (idsCategoriasParaBuscar.isEmpty()) {
                 throw new EntidadNoEncontrada("No se pudo obtener información para la categoría raíz ID: " + idCategoria);
            }
		}

		RepositorioProductoAdHoc repoAdHoc = FactoriaRepositorios.getRepositorio(RepositorioProductoAdHoc.class);
		return repoAdHoc.findProductosByCriteria(idsCategoriasParaBuscar, textoDescripcion, estadoMinimo, precioMax); // Lanza checked RepositorioException
	}
}