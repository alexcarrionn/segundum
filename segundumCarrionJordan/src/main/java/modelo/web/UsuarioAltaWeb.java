package modelo.web;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import servicio.FactoriaServicios;
import servicio.IServicioUsuario;
@SuppressWarnings("serial")
@Named
@ViewScoped
public class UsuarioAltaWeb implements Serializable {
	
	private String idUsuario; 
	private String nombre;
	private String apellidos;
	private String fechaNacimiento;
	private String email;
	private String password;
	private String telefono; 
	private IServicioUsuario servicioUsuarios;
	
	private boolean error;
	@Inject
	private FacesContext facesContext;
	
	public UsuarioAltaWeb() {
		servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuario.class);
	}
	
	public void altaUsuario() {
		//comprobacion de campos 
		try {
			idUsuario = servicioUsuarios.registrarUsuario(nombre, apellidos, email, apellidos, null, telefono);
			facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario " + idUsuario + " creado correctamente"));
			error = false;
		}catch(Exception e) {
			error = true;
			facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public IServicioUsuario getServicioUsuarios() {
		return servicioUsuarios;
	}

	public void setServicioUsuarios(IServicioUsuario servicioUsuarios) {
		this.servicioUsuarios = servicioUsuarios;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}
	
	

}
