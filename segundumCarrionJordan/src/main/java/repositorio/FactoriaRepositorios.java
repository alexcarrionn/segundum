package repositorio;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import utils.PropertiesReader;

public class FactoriaRepositorios {

	private static final String PROPERTIES_FILE = "repositorios.properties";
	private static PropertiesReader properties;
	private static Map<String, Object> repositorios = new HashMap<>();

	/**
	 * Obtiene una instancia del repositorio CRUD estándar para una entidad.
	 *
	 * @param <T> Tipo de la entidad.
	 * @param <K> Tipo de la clave primaria de la entidad.
	 * @param <R> Tipo del repositorio solicitado (debe extender Repositorio<T, K>).
	 * @param entityType La clase de la entidad (ej. Producto.class).
	 * @return Una instancia del repositorio estándar solicitado.
	 * @throws RepositorioException Si hay error al crear.
	 */
	@SuppressWarnings("unchecked")
	public static <T, K, R extends Repositorio<T, K>> R getRepositorio(Class<T> entityType) {
		String key = entityType.getName(); // Clave para el caché y properties

		if (repositorios.containsKey(key)) {
			// El cast a (R) debería funcionar si la interfaz solicitada (ej. IRepositorioProducto) extiende Repositorio<Producto, String>
			return (R) repositorios.get(key);
		}

		if (properties == null) {
			try {
				properties = new PropertiesReader(PROPERTIES_FILE);
			} catch (IOException e) {
				throw new RepositorioException("Error al leer el archivo de configuración de repositorios", e);
			}
		}

		String className = properties.getProperty(key);
		if (className == null) {
			throw new RepositorioException("No se encontró la propiedad de configuración para el repositorio: " + key);
		}

		try {
			R repo = (R) Class.forName(className).getConstructor().newInstance();
			repositorios.put(key, repo); 
			return repo;
		} catch (ClassCastException e) {
			// Error común si la clase en properties no implementa la interfaz correcta
			throw new RepositorioException("La clase '" + className + "' no es del tipo esperado para la entidad " + key, e);
		} catch (Exception e) {
			throw new RepositorioException("Error al crear el repositorio para " + key, e);
		}
	}

	/**
	 * Obtiene una instancia de un repositorio AdHoc específico.
	 *
	 * @param <T> La interfaz del repositorio AdHoc solicitada.
	 * @param entityType La clase de la entidad principal (puede usarse para construir la clave si es necesario).
	 * @param repositorioAdHocType La interfaz del repositorio AdHoc (ej. RepositorioProductoAdHoc.class).
	 * @return Una instancia del repositorio AdHoc.
	 * @throws RepositorioException Si no se encuentra la configuración o hay un error al instanciar.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRepositorio(Class<?> entityType, Class<T> repositorioAdHocType) {
		String key = repositorioAdHocType.getName(); // Clave basada en la interfaz AdHoc

		if (repositorios.containsKey(key)) {
			return (T) repositorios.get(key);
		}

		if (properties == null) {
			try {
				properties = new PropertiesReader(PROPERTIES_FILE);
			} catch (IOException e) {
				throw new RepositorioException("Error al leer el archivo de configuración de repositorios", e);
			}
		}

		String className = properties.getProperty(key);
		if (className == null) {
			String propertyKey = entityType.getName() + ".AdHoc";
			className = properties.getProperty(propertyKey);
			if (className == null) {
				throw new RepositorioException("No se encontró la propiedad de configuración para el repositorio AdHoc: " + key + " ni para " + propertyKey);
			}
		}

		try {
			T repoAdHoc = (T) Class.forName(className).getConstructor().newInstance();
			repositorios.put(key, repoAdHoc);
			return repoAdHoc;
		} catch (Exception e) {
			throw new RepositorioException("Error al crear el repositorio AdHoc para " + key, e);
		}
	}
}