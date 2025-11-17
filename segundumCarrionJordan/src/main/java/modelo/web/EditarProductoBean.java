package modelo.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dto.ProductoDTO;
import servicio.IServiciosProductos;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class EditarProductoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ControlAccesoBean controlAccesoBean; 

    @Inject
    private FacesContext facesContext;

    private IServiciosProductos servicioProductos;

    // --- Atributos del formulario ---
    private String idProducto; 
    private ProductoDTO producto; 
    
    // Usamos campos separados para la edición
    private Double nuevoPrecio;
    private String nuevaDescripcion;

    public EditarProductoBean() {
        servicioProductos = FactoriaServicios.getServicio(IServiciosProductos.class);
    }

    /**
     * Carga los datos del producto ANTES de que la página se muestre.
     */
    public void load() {
        if (!controlAccesoBean.isUsuarioLogueado()) {
            redirectLogin();
            return;
        }

        try {
            this.producto = servicioProductos.getProductoDTO(idProducto);
            
            
            String nombreVendedor = producto.getNombreVendedor();
            String nombreUsuarioActual = controlAccesoBean.getUsuarioLogueado().getNombre();
            
            if (nombreVendedor == null || !nombreVendedor.equals(nombreUsuarioActual)) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    "Acceso denegado", "No puedes editar un producto que no es tuyo."));
                redirectMisProductos();
                return;
            }

            this.nuevoPrecio = producto.getPrecio();
            this.nuevaDescripcion = producto.getDescripcion();

        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error", "No se pudo cargar el producto para editar."));
            e.printStackTrace();
            redirectMisProductos(); 
        }
    }

    /**
     * Acción del botón "Guardar Cambios".
     */
    public String guardarCambios() {
        try {
            // Llamamos al servicio de la Práctica 1
            servicioProductos.modificarProducto(idProducto, nuevoPrecio, nuevaDescripcion);
            
            // Añadimos un mensaje de éxito que se mostrará en la OTRA página
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Éxito", "Producto '" + producto.getTitulo() + "' actualizado correctamente."));
            
            // Navegamos de vuelta al listado
            return "mis-productos?faces-redirect=true";

        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error al guardar", "No se pudieron guardar los cambios: " + e.getMessage()));
            e.printStackTrace();
            return null; 
        }
    }

    // --- Métodos privados de redirección ---
    private void redirectLogin() {
        try {
            facesContext.getExternalContext().redirect(
                facesContext.getExternalContext().getRequestContextPath() + "/login.xhtml?faces-redirect=true"
            );
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    private void redirectMisProductos() {
        try {
            // Asumiendo que está en /producto/mis-productos.xhtml
            facesContext.getExternalContext().redirect(
                facesContext.getExternalContext().getRequestContextPath() + "/producto/mis-productos.xhtml?faces-redirect=true"
            );
        } catch (Exception e) { e.printStackTrace(); }
    }


    // --- Getters y Setters ---

    public String getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public Double getNuevoPrecio() {
        return nuevoPrecio;
    }
    public void setNuevoPrecio(Double nuevoPrecio) {
        this.nuevoPrecio = nuevoPrecio;
    }

    public String getNuevaDescripcion() {
        return nuevaDescripcion;
    }
    public void setNuevaDescripcion(String nuevaDescripcion) {
        this.nuevaDescripcion = nuevaDescripcion;
    }
}