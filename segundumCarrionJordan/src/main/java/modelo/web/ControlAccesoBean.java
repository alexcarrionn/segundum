package modelo.web;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import modelo.Usuario;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class ControlAccesoBean implements Serializable{

    private Usuario usuarioLogueado = null; 
    
    @Inject
    private FacesContext facesContext;
    
    public boolean isUsuarioLogueado() {
		return this.usuarioLogueado != null;
	}
    
    public Usuario getUsuarioLogueado() {
        return this.usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public String logout() {
        this.usuarioLogueado = null;
        facesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

}
    