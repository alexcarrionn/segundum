package servicio;

import java.time.LocalDate;

import modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import repositoriosAdHoc.RepositorioUsuarioAdHoc;


public class ServicioUsuario implements IServicioUsuario {
	
	//Definimos el repositorio de usuarios
	private RepositorioUsuarioAdHoc repositorioAdHoc = FactoriaRepositorios.getRepositorio(RepositorioUsuarioAdHoc.class);
	private Repositorio<Usuario, String> repositorio = FactoriaRepositorios.getRepositorio(Usuario.class);
	
	//definimos el servicio de categorias para usar
	private IServiciosCategorias servicioCategorias = new ServicioCategorias();
	
	
	/**
	 * Funcionalidad: Registrar un nuevo usuario (donde telefono es opcional)
	 * Con la informacion del usuario, se crea un usuario nuevo y se almacena en el repositorio. La aplicacion genera el id y lo retorna. Al ser un registro
	 * el usuario no es admininistrador.
	 */
	@Override
	public String registrarUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNacimiento, String telefono, boolean admin) throws RepositorioException {
		
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
		if (repositorioAdHoc.buscarPorEmail(email) != null) {
			throw new RepositorioException("Ya existe un usuario registrado con ese email");
		}
		
		//Creamos el nuevo usuario
		Usuario nuevoUsuario = new Usuario(email, nombre, apellidos, clave, fechaNacimiento, telefono, admin);

		//lo almacenamos en el repositorio y devolvemos el id generado
		 
		
		return repositorio.add(nuevoUsuario);


	}
	
	
	/**
	 * Funcionalidad: Permitir que un usuario existente pueda cambiar sus datos para poder mantener su informacion actualizada
	 *
	 */
	
	@Override
	public void actualizarDatosUsuario(String idUsuario, String nombre, String apellidos, String email, String clave, LocalDate fechaNacimiento, String telefono) throws RepositorioException, EntidadNoEncontrada {
		
		if(email == null || email.isEmpty() || !email.contains("@")) {
			throw new IllegalArgumentException("El email no es válido");
		}
		if (nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
		}
		if (apellidos == null || apellidos.isEmpty()) {
			throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
		}
		if (clave == null || clave.isEmpty() || clave.length() < 6) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres y no puede ser nula o vacía");
		}
		if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("La fecha de nacimiento no es válida");
		}
		
		Usuario usuarioExistente = repositorio.getById(idUsuario);
		
		if (usuarioExistente == null) {
			throw new EntidadNoEncontrada("No se encontró el usuario con ID: " + idUsuario);
		}
		
		//ahora comprobamos si el email que quiere poner ya lo tiene otro usuario
		if( !usuarioExistente.getEmail().equals(email)) {
			Usuario usuarioConEmail = repositorioAdHoc.buscarPorEmail(email);
			if (usuarioConEmail != null) {
				throw new RepositorioException("Ya existe un usuario registrado con ese email");
			}
		}
		
		//Actualizamos los datos del usuario
		usuarioExistente.setNombre(nombre);
		usuarioExistente.setApellidos(apellidos);
		usuarioExistente.setEmail(email);
		usuarioExistente.setClave(clave);
		usuarioExistente.setFechaNacimiento(fechaNacimiento);
		usuarioExistente.setTelefono(telefono);
		
		//Guardamos los cambios en el repositorio
		repositorio.update(usuarioExistente);	
	}
	
	
	
	//Metodo para un administrador, para poder modificar una categoria existente
	@Override
	public void modificarCategoria (String idUsuario, String idCategoria, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		
		//Obetenemos el usuario de la base de datos
		Usuario usuario = repositorio.getById(idUsuario);	
		
		//primero comprobamos que somos administradores 
		if (usuario.isAdmin()) {
			servicioCategorias.modificarCategoria(idCategoria, descripcion);
		} else {
			throw new RepositorioException("No tienes permisos para modificar categorias");
		}
	
	}


	@Override
	public void asignarRolAdmin(String idUsuario2) throws RepositorioException, EntidadNoEncontrada {
		
		//Obetenemos el usuario de la base de datos
		Usuario usuario = repositorio.getById(idUsuario2);
		
		//le cambiamos el rol a admin 
		usuario.setAdmin(true);
		
		//Guardamos los cambios en el repositorio
		repositorio.update(usuario);
		
		
	}

	@Override
	public Usuario iniciarSesion(String email, String clave) throws RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repositorioAdHoc.buscarPorEmail(email);
		
		if (usuario == null) {
			throw new EntidadNoEncontrada("No se encontró el usuario con email: " + email);
		}
		if (!usuario.getClave().equals(clave)) {
			throw new RepositorioException("Contraseña incorrecta");
		}
		return usuario;	
	}

	
}