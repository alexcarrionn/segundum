package modelo.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import modelo.EstadoProducto;
import servicio.FactoriaServicios;
import servicio.IServiciosProductos;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
@SuppressWarnings("serial")
@Named
@ViewScoped
public class ProductoAltaWeb implements Serializable {

    private String titulo;
    private String descripcion;
    private Double precio;
    private EstadoProducto estado;
    private String idCategoria;
    private String idVendedor;
    private boolean envioDisponible;

    private String idCreado;
    private boolean error;

    private IServiciosProductos servicioProductos;

    @Inject
    private FacesContext facesContext;

    public ProductoAltaWeb() {
        servicioProductos = FactoriaServicios.getServicio(IServiciosProductos.class);
    }

    public void altaProducto() {
        try {
            idCreado = servicioProductos.altaProducto(titulo, descripcion, precio != null? precio:0.0, estado, idCategoria, envioDisponible, idVendedor);
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Producto " + idCreado + " creado correctamente"));
            error = false;
            limpiarFormulario();
        } catch (IllegalArgumentException e) {
            error = true;
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        } catch (EntidadNoEncontrada | RepositorioException e) {
            error = true;
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error persistiendo: " + e.getMessage()));
        }
    }

    private void limpiarFormulario() {
        titulo = null; descripcion = null; precio = null; estado = null; idCategoria = null; idVendedor = null; envioDisponible = false;
    }

    // Helpers para la vista
    public List<EstadoProducto> getEstados() { return Arrays.asList(EstadoProducto.values()); }

    // Getters y setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public EstadoProducto getEstado() { return estado; }
    public void setEstado(EstadoProducto estado) { this.estado = estado; }

    public String getIdCategoria() { return idCategoria; }
    public void setIdCategoria(String idCategoria) { this.idCategoria = idCategoria; }

    public String getIdVendedor() { return idVendedor; }
    public void setIdVendedor(String idVendedor) { this.idVendedor = idVendedor; }

    public boolean isEnvioDisponible() { return envioDisponible; }
    public void setEnvioDisponible(boolean envioDisponible) { this.envioDisponible = envioDisponible; }

    public String getIdCreado() { return idCreado; }
    public boolean isError() { return error; }
}
