package servicio;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServiciosCategorias {
	
	void modificarCategoria(String idCategoria, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	
	//Funcionalidad: a partir de un fichero XML con la estructura de categorias, cargar las categorias en el repositorio
	void cargarCategorias(String ruta) throws RepositorioException, Exception;
	
}
