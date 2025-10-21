package repositoriosModelo;

import modelo.Producto;
import repositorio.Repositorio; // Asegúrate de que esta importación sea correcta

/**
 * Interfaz para el repositorio de la entidad Producto.
 * Hereda las operaciones CRUD genéricas.
 */
public interface IRepositorioProducto extends Repositorio<Producto, String> {
	// No se necesitan métodos específicos adicionales por ahora.
	// Métodos heredados: add, update, delete, getById, getAll
}