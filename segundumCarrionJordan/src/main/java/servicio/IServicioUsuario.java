package servicio;

import java.time.LocalDate;

import repositorio.RepositorioException;

public interface IServicioUsuario {
	
	//Funcionalidad: Registrar un nuevo usuario (donde telefono es opcional)	
	String registrarUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNacimiento, String telefono) throws RepositorioException;
}
