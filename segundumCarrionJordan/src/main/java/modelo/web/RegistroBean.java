package modelo.web;

import java.io.Serializable;
import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import servicio.FactoriaServicios;
import servicio.IServicioUsuario;

@SuppressWarnings("serial")
@Named("registroBean")
@RequestScoped
public class RegistroBean implements Serializable{

    private String nombre; 
    private String apellidos;
    private String telefono;
    private String fechaNacimiento;
    private String email; 
    private String password;
    private boolean admin;
    private IServicioUsuario servicioUsuarios;

    @Inject
    private FacesContext facesContext;

    public RegistroBean(){
        servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuario.class);
    }

    public String registrar(){
        try{
            LocalDate fechaNacimientoLocalDate = LocalDate.parse(fechaNacimiento);
            servicioUsuarios.registrarUsuario(nombre, apellidos, email, password, fechaNacimientoLocalDate, telefono, admin);
            facesContext.addMessage(null,
                new FacesMessage("Usuario registrado correctamente"));
            return "/usuario/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            facesContext.addMessage(null,
                new FacesMessage("Error al registrar el usuario: " + e.getMessage()));
            e.printStackTrace();
            return null;
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}