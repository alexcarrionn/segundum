package repositoriosAdHoc;

import java.util.List;

import modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface RepositorioCategoriasAdHoc {
	List<Categoria> buscarCategoriasRaiz() throws RepositorioException, EntidadNoEncontrada; 
	List<Categoria> buscarDescendientes(String idCategoriaPadre) throws RepositorioException, EntidadNoEncontrada;
}
