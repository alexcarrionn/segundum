package repositorio;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import modelo.EstadoProducto;
import modelo.Producto;
import repositoriosModelo.RepositorioProductosJPA;
import utils.EntityManagerHelper;


public class RepositorioProductoAdHocJPA extends RepositorioProductosJPA implements RepositorioProductoAdHoc {

	
	@Override
	public List<Producto> findProductosByCriteria(String idCategoriaRaiz, String textoDescripcion,
	                                            EstadoProducto estadoMinimo, Double precioMax) {

		// **TODO:** Implementar la lógica de búsqueda real aquí usando JPA.

		EntityManager em = EntityManagerHelper.getEntityManager();

		System.out.println("TODO: Implementar búsqueda de productos por criterios...");
		return new ArrayList<>(); 

	}

	

}