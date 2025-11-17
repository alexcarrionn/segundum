package modelo.web;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import servicio.FactoriaServicios;
import servicio.IServicioUsuario;
import modelo.Usuario;
@SuppressWarnings("serial")
@Named
//con @RequestScoped lo que se have es que cada vez que se recarga la pagina se crea un nuevo bean
@RequestScoped
public class LoginBean implements Serializable{

    private String email; 
    private String password;
    private IServicioUsuario servicioUsuarios;
    @Inject
    private FacesContext facesContext;

    @Inject
    private ControlAccesoBean controlAccesoBean;

    public LoginBean(){
        servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuario.class);
    }

    public String login(){
        try{
            Usuario usuario = servicioUsuarios.iniciarSesion(email, password);
            controlAccesoBean.setUsuarioLogueado(usuario);
            // Redirección tras login correcto (barra correcta sin backslash de escape)
            return "/index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            facesContext.addMessage(null,
                new FacesMessage("Error de login: contraseña o usuario incorrecto"));
            e.printStackTrace();
            return null;
        }
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
}
