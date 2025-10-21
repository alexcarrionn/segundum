package servicio;

import java.time.LocalDate;
import modelo.Usuario;
import repositorio.EntidadNoEncontrada; // Importar si buscarPorEmail la lanza
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioUsuarioAdHoc;
import repositoriosModelo.IRepositorioUsuario;

public class ServicioUsuario implements IServicioUsuario {

	private IRepositorioUsuario repoUsuario;
	private RepositorioUsuarioAdHoc repoUsuarioAdHoc;

	// Constructor para inicializar repositorios
	public ServicioUsuario() {
		this.repoUsuario = FactoriaRepositorios.<Usuario, String, IRepositorioUsuario>getRepositorio(Usuario.class);
		this.repoUsuarioAdHoc = FactoriaRepositorios.getRepositorio(Usuario.class, RepositorioUsuarioAdHoc.class);
	}

	/**
	 * Funcionalidad: Registrar un nuevo usuario (donde telefono es opcional).
	 * Con la informacion del usuario, se crea un usuario nuevo y se almacena en el repositorio.
	 * La aplicacion genera el id y lo retorna. Al ser un registro el usuario no es admininistrador.
	 * @throws RepositorioException Si ya existe un usuario con ese email o hay un error de persistencia.
	 * @throws IllegalArgumentException Si los datos de entrada no son válidos.
	 */
	@Override
	public String registrarUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNacimiento, String telefono)
			throws RepositorioException, IllegalArgumentException {

		// Validaciones de integridad de datos
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
		}
		if (apellidos == null || apellidos.trim().isEmpty()) {
			throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
		}
		if (email == null || email.isEmpty() || !email.contains("@")) {
			throw new IllegalArgumentException("El email no es válido");
		}
		if (clave == null || clave.isEmpty() || clave.length() < 6) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres y no puede ser nula o vacía");
		}
		if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("La fecha de nacimiento no es válida");
		}

		// Comprobar existencia previa por email
		// Asume que buscarPorEmail devuelve null si no lo encuentra.
		Usuario existente = repoUsuarioAdHoc.buscarPorEmail(email);
		if (existente != null) {
			throw new RepositorioException("Ya existe un usuario registrado con ese email");
		}

		// Crear nueva entidad
		Usuario nuevoUsuario = new Usuario(email, nombre, apellidos, clave, fechaNacimiento, telefono, false);

		// Persistir entidad
		repoUsuario.add(nuevoUsuario);

		// Devolver ID (puede requerir flush dependiendo de la estrategia JPA y BD)
		if (nuevoUsuario.getId() == null) {
			System.err.println("Advertencia: El ID del usuario no se generó inmediatamente después de add().");
		}
		return nuevoUsuario.getId();
	}

	// TODO: Implementar el resto de métodos definidos en IServicioUsuario (modificarUsuario)

}