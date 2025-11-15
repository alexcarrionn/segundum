package servicio;

import java.util.List;
import modelo.EstadoProducto;
import modelo.Producto;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import dto.ProductoDTO;

/**
 * Interfaz que define los servicios relacionados con la gestión de Productos.
 */
public interface IServiciosProductos {

	 //Da de alta un nuevo producto en el sistema. (Historia 3)
	
	String altaProducto(String titulo, String descripcion, double precio, EstadoProducto estado,
	                    String idCategoria, boolean envioDisponible, String idVendedor)
	                    throws EntidadNoEncontrada, IllegalArgumentException, RepositorioException;

	// Asigna o actualiza el lugar de recogida para un producto existente. (Historia 5)
	void asignarLugarRecogida(String idProducto, String descripcionLugar, double longitud, double latitud)
	                    throws EntidadNoEncontrada, RepositorioException;

	// Modifica el precio y/o la descripción de un producto existente. (Historia 4)
	void modificarProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
	                    throws EntidadNoEncontrada, IllegalArgumentException, RepositorioException;

	// Incrementa en uno el contador de visualizaciones de un producto.
	
	void anadirVisualizacion(String idProducto)
	                    throws EntidadNoEncontrada, RepositorioException;

	// Obtiene un resumen de los productos publicados en un mes y año específicos,
	List<Producto> historialDelMes(int mes, int ano) throws RepositorioException;

	// Busca productos a la venta según criterios opcionales. (Historia 7)
	List<Producto> buscarProductos(String idCategoria, String textoDescripcion, EstadoProducto estadoMinimo, Double precioMax)
	                    throws RepositorioException, EntidadNoEncontrada;

	ProductoDTO getProductoDTO(String idProducto) throws EntidadNoEncontrada, RepositorioException; 

}