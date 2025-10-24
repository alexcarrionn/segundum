package repositoriosAdHoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import modelo.Categoria;
import modelo.EstadoProducto;
import modelo.Producto;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositoriosModelo.IRepositorioCategorias;
import repositoriosModelo.RepositorioProductosJPA;
import utils.EntityManagerHelper;

public class RepositorioProductoAdHocJPA extends RepositorioProductosJPA implements RepositorioProductoAdHoc {

	/**
	 * Busca productos según criterios opcionales (Historia 7).
	 * Construye una consulta JPQL dinámica.
	 */
	@Override
	public List<Producto> findProductosByCriteria(String idCategoriaRaiz, String textoDescripcion,
	                                            EstadoProducto estadoMinimo, Double precioMax)
	                                            throws RepositorioException {

		EntityManager em = null;
		try {
			em = EntityManagerHelper.getEntityManager();
			StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");
			Map<String, Object> parameters = new HashMap<>();

			if (idCategoriaRaiz != null && !idCategoriaRaiz.trim().isEmpty()) {
				List<String> idsCategorias = obtenerIdsCategoriasDescendientes(idCategoriaRaiz);
				if (idsCategorias != null && !idsCategorias.isEmpty()) {
					jpql.append(" AND p.categoria.id IN :listaIdsCategorias");
					parameters.put("listaIdsCategorias", idsCategorias);
				} else {
				    return new ArrayList<>();
				}
			}

			if (textoDescripcion != null && !textoDescripcion.trim().isEmpty()) {
				jpql.append(" AND LOWER(p.descripcion) LIKE LOWER(:textoDesc)");
				parameters.put("textoDesc", "%" + textoDescripcion + "%");
			}

			if (estadoMinimo != null) {
				List<EstadoProducto> estadosValidos = obtenerEstadosIgualesOMejores(estadoMinimo);
				if (!estadosValidos.isEmpty()) {
					jpql.append(" AND p.estado IN :listaEstados");
					parameters.put("listaEstados", estadosValidos);
				} else {
					return new ArrayList<>();
				}
			}

			if (precioMax != null && precioMax >= 0) {
				jpql.append(" AND p.precio <= :precioMax");
				parameters.put("precioMax", precioMax);
			}

			TypedQuery<Producto> query = em.createQuery(jpql.toString(), Producto.class);
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			return query.getResultList();

		} catch (RepositorioException re) {
		    throw re; // Relanzar
		} catch (Exception e) {
			throw new RepositorioException("Error al buscar productos por criterios", e);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	/**
	 * Busca productos publicados en un mes y año específicos, ordenados por visualizaciones DESC.
	 */
	@Override
	public List<Producto> findProductosByMonthAndYearOrderedByVisualizaciones(int mes, int ano)
	                                            throws RepositorioException {
		EntityManager em = null;
		try {
			em = EntityManagerHelper.getEntityManager();
			final String queryString = "SELECT p FROM Producto p " +
			                         "WHERE FUNCTION('YEAR', p.fechaPublicacion) = :ano " +
			                         "AND FUNCTION('MONTH', p.fechaPublicacion) = :mes " +
			                         "ORDER BY p.visualizaciones DESC";
			TypedQuery<Producto> query = em.createQuery(queryString, Producto.class);
			query.setParameter("ano", ano);
			query.setParameter("mes", mes);
			return query.getResultList();
		} catch (Exception e) {
			throw new RepositorioException("Error al buscar historial de productos para " + mes + "/" + ano, e);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	// --- MÉTODOS AUXILIARES ---

	/**
	 * Obtiene los IDs de una categoría y todas sus descendientes.
	 * TODO: Implementar obtención real de IDs descendientes llamando al servicio/repositorio de Categorías.
	 */
	private List<String> obtenerIdsCategoriasDescendientes(String idCategoriaRaiz)
	        throws RepositorioException, EntidadNoEncontrada {
	    // Placeholder actual - necesita ser reemplazado
	    System.out.println("TODO: Llamar a lógica real de Categorías para obtener descendientes ID: " + idCategoriaRaiz);
	    List<String> ids = new ArrayList<>();
	    IRepositorioCategorias repoCat = FactoriaRepositorios.getRepositorio(Categoria.class);
	    Categoria catRaiz = repoCat.getById(idCategoriaRaiz);
	    if (catRaiz != null) {
	        ids.add(catRaiz.getId());
	        // Añadir aquí la lógica real para obtener IDs descendientes
	    }
	    if (ids.isEmpty() && catRaiz == null) {
	         System.err.println("Advertencia: No se encontró la categoría raíz con ID: " + idCategoriaRaiz);
	    }
	    return ids;
	}

	/**
	 * Devuelve una lista de Estados de Producto iguales o mejores que el estado dado.
	 */
	private List<EstadoProducto> obtenerEstadosIgualesOMejores(EstadoProducto estadoMinimo) {
		return Arrays.stream(EstadoProducto.values())
		                .filter(e -> e.ordinal() <= estadoMinimo.ordinal())
		                .collect(Collectors.toList());
	}

}