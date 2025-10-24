package servicio;

import modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioCategorias implements IServiciosCategorias {

	//Definimos el repositorio de categorias ue vamos a usar 
	private Repositorio<modelo.Categoria, String> repositorioCategoria = FactoriaRepositorios.getRepositorio(Categoria.class);
	
	@Override
	public void modificarCategoria(String idCategoria, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		
		//Primero buscamos en el repositorio la categoria con el id proporcionado
		Categoria categoria = repositorioCategoria.getById(idCategoria);
		
		//Comprobamos que la categoria existe
		if (categoria == null) {
			throw new EntidadNoEncontrada("No se ha encontrado ninguna categoria con el id proporcionado");
		}
		
		//Si existe modificamos la categoria 
		categoria.setDescripcion(descripcion);
		
		//Y la almacenamos de nuevo en el repositorio
		repositorioCategoria.update(categoria);
		
	}

	@Override
	public void cargarCategorias(String ruta) throws RepositorioException, Exception{
		
			
	}
	
	
	
}
