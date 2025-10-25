package repositoriosAdHoc;

import java.util.List;

import modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface RepositorioCategoriasAdHoc  extends RepositorioString<Categoria>{
	List<Categoria> buscarCategoriasRaiz() throws RepositorioException, EntidadNoEncontrada; 
	List<Categoria> buscarDescendientes(String idCategoriaPadre) throws RepositorioException, EntidadNoEncontrada;
}
