package servicio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import repositoriosAdHoc.RepositorioCategoriasAdHoc;

public class ServicioCategorias implements IServiciosCategorias {

	//Definimos el repositorio de categorias ue vamos a usar 
	private Repositorio<Categoria, String> repositorioCategoria = FactoriaRepositorios.getRepositorio(Categoria.class);
	
	@Override
	public void modificarCategoria(String idCategoria, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		
		//Primero buscamos en el repositorio la categoria con el id proporcionado, si esta no existe se lanza una excepcion
		Categoria categoria = repositorioCategoria.getById(idCategoria);

		//Si existe modificamos la categoria 
		categoria.setDescripcion(descripcion);
		
		//Y la almacenamos de nuevo en el repositorio
		repositorioCategoria.update(categoria);
		
	}

	@Override
	public void cargarCategorias(String ruta) throws RepositorioException, Exception{
		
			JAXBContext jaxbContext = JAXBContext.newInstance(Categoria.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Categoria categoria = (Categoria) unmarshaller.unmarshal(new File(ruta));
			
			//compruebo que no existe ya una categoria con el mismo id
			try {
				repositorioCategoria.getById(categoria.getId());
				throw new RepositorioException("Ya existe una categoria con el id: " + categoria.getId() + " en el sistema.");
			} catch (EntidadNoEncontrada e) {
				// La categoria no existe, podemos proceder a añadirla
				
				//Guardamos la categoria en el repositorio
				repositorioCategoria.add(categoria);
				
				//si se añade correctamente lanzamos un mensaje por consola
				System.out.println("Categoria con id: " + categoria.getId() + " añadida correctamente.");
			}
			
	}
	
	@Override
	public List<Categoria> obtenerCategoriasRaiz() throws RepositorioException, EntidadNoEncontrada {
		RepositorioCategoriasAdHoc repositorioAdHoc = FactoriaRepositorios.getRepositorio(RepositorioCategoriasAdHoc.class);
		return repositorioAdHoc.buscarCategoriasRaiz();
	}
	
	
	@Override
	public List<Categoria> obtenerDescendientes(String idCategoriaPadre) throws RepositorioException, EntidadNoEncontrada {
		RepositorioCategoriasAdHoc repositorioAdHoc = FactoriaRepositorios.getRepositorio(RepositorioCategoriasAdHoc.class);
		return repositorioAdHoc.buscarDescendientes(idCategoriaPadre);
	}
	
	//A PARTIR DE AQUI HECHO POR BORJA, LO NECESITO PARA LA HISTORIA 7
	@Override
	public List<Categoria> recuperarTodosDescendientes(String idCategoriaPadre) throws RepositorioException, EntidadNoEncontrada {
	    // Verificar que el padre existe
	    repositorioCategoria.getById(idCategoriaPadre); // Lanza si no existe

	    List<Categoria> todosLosDescendientes = new ArrayList<>();
	    RepositorioCategoriasAdHoc repoAdHoc = FactoriaRepositorios.getRepositorio(RepositorioCategoriasAdHoc.class);
	    // Llamar al auxiliar recursivo
	    buscarDescendientesRecursivo(idCategoriaPadre, todosLosDescendientes, repoAdHoc);
	    return todosLosDescendientes;
	}

	// ---> NUEVO MÉTODO AUXILIAR RECURSIVO <---
	/**
	 * Busca recursivamente los descendientes y los añade a la lista acumulada.
	 */
	private void buscarDescendientesRecursivo(String idPadreActual, List<Categoria> acumulados, RepositorioCategoriasAdHoc repoAdHoc)
	        throws RepositorioException, EntidadNoEncontrada { // Declara excepciones checked

	    // Busca los hijos directos del nodo actual usando el repo AdHoc
	    List<Categoria> hijosDirectos = repoAdHoc.buscarDescendientes(idPadreActual); // Lanza checked

	    if (hijosDirectos != null && !hijosDirectos.isEmpty()) {
	        // Añade los hijos encontrados a la lista general
	        acumulados.addAll(hijosDirectos);
	        // Para cada hijo, busca sus propios descendientes
	        for (Categoria hijo : hijosDirectos) {
	            // Pasa el repo AdHoc para evitar obtenerlo múltiples veces
	            buscarDescendientesRecursivo(hijo.getId(), acumulados, repoAdHoc);
	        }
	    }
	}
	
}
