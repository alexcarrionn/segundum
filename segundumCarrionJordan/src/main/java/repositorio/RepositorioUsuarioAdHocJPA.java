package repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import modelo.Usuario;
import repositoriosModelo.RepositorioUsuarioJPA;
import utils.EntityManagerHelper;


public class RepositorioUsuarioAdHocJPA extends RepositorioUsuarioJPA implements RepositorioUsuarioAdHoc {

	//Aqui implementamos los metodos ad-hoc especificos para Usuario
	//Por ejemplo, buscar usuarios por rol, estado, etc.
	
	@Override
	public List<Usuario> buscarPorEmail(String Email) throws RepositorioException {
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			String queryString = "SELECT u FROM Usuario u WHERE u.email = :emailParam";
			
			TypedQuery<Usuario> query = em.createQuery(queryString, Usuario.class);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			
			return query.getResultList();
			
		} catch (Exception e) {
			throw new RepositorioException("Error al buscar usuarios por email: " + e.getMessage(), e);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
	}
		

}
