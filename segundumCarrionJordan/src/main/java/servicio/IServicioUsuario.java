package servicio;

import java.time.LocalDate;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioUsuario {
	
	//Funcionalidad: Registrar un nuevo usuario (donde telefono es opcional)	
	String registrarUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNacimiento, String telefono) throws RepositorioException;
	
	//Funcionalidad: Permitir que un usuario existente pueda cambiar sus datos para poder mantener su informacion actualizada
	void actualizarDatosUsuario(String idUsuario, String nombre, String apellidos, String email, String clave, LocalDate fechaNacimiento, String telefono) throws RepositorioException, EntidadNoEncontrada;
	
}
