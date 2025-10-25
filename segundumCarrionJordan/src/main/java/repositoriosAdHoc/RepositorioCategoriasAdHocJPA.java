package repositoriosAdHoc;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositoriosModelo.RepositorioCategoriaJPA;
import utils.EntityManagerHelper;

public class RepositorioCategoriasAdHocJPA  extends RepositorioCategoriaJPA implements RepositorioCategoriasAdHoc{

	@Override
	public List<Categoria> buscarCategoriasRaiz() throws RepositorioException, EntidadNoEncontrada{
		
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			
			//buscamos las categorias que no son no esten en la lista de subcategorias de ninguna otra categoria
			String queryString = "SELECT c FROM Categoria c WHERE c NOT IN "
					   + "(SELECT sub FROM Categoria parent JOIN parent.subcategorias sub)";
			
			TypedQuery<Categoria> query = em.createQuery(queryString, Categoria.class);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			
			return query.getResultList();
		}catch (Exception e) {
			throw new RepositorioException("Error al buscar categorias raiz: " + e.getMessage(), e);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
		
	}

	@Override
	public List<Categoria> buscarDescendientes(String idCategoriaPadre) throws RepositorioException, EntidadNoEncontrada{

		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			
			//buscamos las categorias que no son no esten en la lista de subcategorias de ninguna otra categoria
			String queryString = "SELECT sub FROM Categoria parent JOIN parent.subcategorias sub "
                    + "WHERE parent.id = :idPadre";
			
			TypedQuery<Categoria> query = em.createQuery(queryString, Categoria.class);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			query.setParameter("idPadre", idCategoriaPadre);
			
			return query.getResultList();
		}catch (Exception e) {
			throw new RepositorioException("Error al buscar categorias descendientes.  " + e.getMessage(), e);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
	}


	
	

}
