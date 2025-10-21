package repositorio; // O el paquete que decidáis para las interfaces AdHoc

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
	 * Debe manejar la búsqueda por categoría y sus descendientes,
	 * el texto en la descripción, el estado mínimo (considerando la jerarquía)
	 * y el precio máximo.
	 */
	List<Producto> findProductosByCriteria(String idCategoriaRaiz, String textoDescripcion,
	                                     EstadoProducto estadoMinimo, Double precioMax)
	                                     throws RepositorioException; // <-- Añadido throws
}