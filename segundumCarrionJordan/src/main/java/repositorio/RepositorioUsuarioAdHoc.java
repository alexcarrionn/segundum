package repositorio;

import java.util.List;

import modelo.Usuario;

public interface RepositorioUsuarioAdHoc extends RepositorioString<Usuario>{
	
	//Aqui vamos a implementar metodos ad-hoc especificos para Usuario
	//Por ejemplo, buscar usuarios por rol, estado, etc.
	
	//Esta fucion nos servira para buscar usuarios por su email para poder saber si ya existe un usuario registrado con ese email
	List<Usuario> buscarPorEmail(String email) throws RepositorioException;

}
