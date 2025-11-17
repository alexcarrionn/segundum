package modelo.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dto.ProductoDTO; // Importamos el DTO (corregido)
import servicio.IServiciosProductos;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class MisProductosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ControlAccesoBean controlAccesoBean; // Para saber quién soy

    @Inject
    private FacesContext facesContext;

    private IServiciosProductos servicioProductos;
    
    private List<ProductoDTO> misProductos; // La lista para la tabla

    public MisProductosBean() {
        servicioProductos = FactoriaServicios.getServicio(IServiciosProductos.class);
    }

    @PostConstruct
    public void init() {
        // 1. Comprobación de seguridad
        if (!controlAccesoBean.isUsuarioLogueado()) {
            try {
                // Si no está logueado, lo mandamos al login
                facesContext.getExternalContext().redirect(
                    facesContext.getExternalContext().getRequestContextPath() + "/login.xhtml?faces-redirect=true"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        // 2. Cargar los datos
        cargarProductos();
    }

    public void cargarProductos() {
        try {
            // Obtenemos el ID del usuario actual
            String idVendedor = controlAccesoBean.getUsuarioLogueado().getId();
            
            // Llamamos al método del servicio que creamos en la Tarea 1
            misProductos = servicioProductos.getProductosPorVendedor(idVendedor);
            
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error", "No se pudieron cargar tus productos: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    // --- Getter para la vista ---
    
    public List<ProductoDTO> getMisProductos() {
        return misProductos;
    }
}