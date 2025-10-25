package servicio;

import java.util.List;

import modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServiciosCategorias {
	
	void modificarCategoria(String idCategoria, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	
	//Funcionalidad: a partir de un fichero XML con la estructura de categorias, cargar las categorias en el repositorio
	void cargarCategorias(String ruta) throws RepositorioException, Exception;

	List<Categoria> obtenerCategoriasRaiz() throws RepositorioException, EntidadNoEncontrada;

	List<Categoria> obtenerDescendientes(String idCategoriaPadre) throws RepositorioException, EntidadNoEncontrada;
	
}
