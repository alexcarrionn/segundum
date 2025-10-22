package servicio;

import java.time.LocalDate;

import modelo.Usuario;

import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioUsuarioAdHoc;


public class ServicioUsuario implements IServicioUsuario {
	
	//Definimos el repositorio de usuarios
	private RepositorioUsuarioAdHoc repositorio = FactoriaRepositorios.getRepositorio(Usuario.class);
	
	/**s
	 * Funcionalidad: Registrar un nuevo usuario (donde telefono es opcional)
	 * Con la informacion del usuario, se crea un usuario nuevo y se almacena en el repositorio. La aplicacion genera el id y lo retorna. Al ser un registro
	 * el usuario no es admininistrador.
	 */
	@Override
	public String registrarUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNacimiento, String telefono) throws RepositorioException {
		
		//hacemos un control de integridad de los datos
		if (nombre == null || nombre.isEmpty()) {

			throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
		}
		
		if (apellidos == null || apellidos.isEmpty()) {
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
		
		//Como telefono es opcional, no hacemos control de integridad
		
		//Comprobamos que no existe ya un uusario con ese email 
		if (repositorio.buscarPorEmail(email) != null) {
			throw new RepositorioException("Ya existe un usuario registrado con ese email");
		}
		
		//Creamos el nuevo usuario
		Usuario nuevoUsuario = new Usuario(email, nombre, apellidos, clave, fechaNacimiento, telefono, false);
		
		
	
		//lo almacenamos en el repositorio y devolvemos el id generado
		String id = repositorio.add(nuevoUsuario);
		
		return id; 


	}

	
}