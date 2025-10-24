package servicio;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

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
	
	
	
}
