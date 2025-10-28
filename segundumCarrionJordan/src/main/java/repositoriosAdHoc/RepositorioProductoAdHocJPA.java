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
import servicio.FactoriaServicios;
import servicio.IServiciosCategorias;
import utils.EntityManagerHelper;

public class RepositorioProductoAdHocJPA extends RepositorioProductosJPA implements RepositorioProductoAdHoc {

	/**
	 * Busca productos según criterios opcionales (Historia 7).
	 * Construye una consulta JPQL dinámica.
	 */
	@Override
	public List<Producto> findProductosByCriteria(List<String> idsCategorias, String textoDescripcion,
	                                            EstadoProducto estadoMinimo, Double precioMax)
	                                            throws RepositorioException {

		EntityManager em = null;
		try {
			em = EntityManagerHelper.getEntityManager();
			StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");
			Map<String, Object> parameters = new HashMap<>();

			if (idsCategorias != null && !idsCategorias.isEmpty()) {
				//List<String> idsCategorias = obtenerIdsCategoriasDescendientes(idCategoriaRaiz);
				//if (idsCategorias != null && !idsCategorias.isEmpty()) {
					jpql.append(" AND p.categoria.id IN :listaIdsCategorias");
					parameters.put("listaIdsCategorias", idsCategorias);
				//} else {
				  //  return new ArrayList<>();
				//}
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

		//} catch (RepositorioException re) {
		  //  throw re; // Relanzar
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
	 * Llama al servicio de Categorías.
	 */
	/** NO USADO PARA CERRAR BIEN EL ENTITY MANAGER
	private List<String> obtenerIdsCategoriasDescendientes(String idCategoriaRaiz)
	        throws RepositorioException, EntidadNoEncontrada {

	    List<String> ids = new ArrayList<>();
	    IServiciosCategorias servicioCategorias = null;
        IRepositorioCategorias repoCat = null;

	    try {
	        servicioCategorias = FactoriaServicios.getServicio(IServiciosCategorias.class);
            repoCat = FactoriaRepositorios.getRepositorio(Categoria.class);

            Categoria catRaiz = repoCat.getById(idCategoriaRaiz);
            ids.add(catRaiz.getId());

	        List<Categoria> descendientes = servicioCategorias.recuperarTodosDescendientes(idCategoriaRaiz);

	        if (descendientes != null) {
	            for (Categoria cat : descendientes) {
                    if (!ids.contains(cat.getId())) {
	                    ids.add(cat.getId());
                    }
	            }
	        }

	    } catch (EntidadNoEncontrada | RepositorioException e) {
	        throw e;
	    } catch (RuntimeException e) {
	        throw new RepositorioException("Error al obtener servicio/repositorio de categorías", e);
	    } catch (Exception e) {
	        throw new RepositorioException("Error inesperado al obtener IDs de categorías descendientes", e);
	    }

        if (ids.isEmpty()) {
             throw new EntidadNoEncontrada("No se pudo obtener información para la categoría raíz ID: " + idCategoriaRaiz);
        }
	    return ids;
	}
	*/
	/**
	 * Devuelve una lista de Estados de Producto iguales o mejores que el estado dado.
	 */
	private List<EstadoProducto> obtenerEstadosIgualesOMejores(EstadoProducto estadoMinimo) {
		return Arrays.stream(EstadoProducto.values())
		                .filter(e -> e.ordinal() <= estadoMinimo.ordinal())
		                .collect(Collectors.toList());
	}

}