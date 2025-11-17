package repositoriosAdHoc; // O el paquete que decidáis para las interfaces AdHoc

import java.util.List;
import modelo.EstadoProducto;
import modelo.Producto;
import repositorio.RepositorioException; // Importar RepositorioException

/**
 * Interfaz para operaciones de consulta AdHoc sobre Productos,
 * específicamente para búsquedas con criterios complejos.
 */
public interface RepositorioProductoAdHoc {

	/**
	 * Busca productos según criterios opcionales (Historia 7).
	 */
	List<Producto> findProductosByCriteria(List<String> idsCategorias, String textoDescripcion,
	                                     EstadoProducto estadoMinimo, Double precioMax)
	                                     throws RepositorioException;
	/**
	 * Busca productos según mes y año ordenado por visualizaciones (Historia 6).
	 */
	List<Producto> findProductosByMonthAndYearOrderedByVisualizaciones(int mes, int ano)
            throws RepositorioException;
	/**
	 * Busca productos por el ID del vendedor, ordenados por fecha de publicación descendente.
	 */
	List<Producto> findProductosByVendedorId(String idVendedor) throws RepositorioException;
}