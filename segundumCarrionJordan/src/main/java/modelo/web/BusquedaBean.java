package modelo.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import modelo.EstadoProducto;
import modelo.Producto;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositoriosModelo.IRepositorioProducto;
import servicio.FactoriaServicios;
import servicio.IServiciosProductos;


@SuppressWarnings("serial")
@Named("busquedaBean")
@ViewScoped
public class BusquedaBean implements Serializable {


    private List<Producto> productos;
    private Producto seleccionado;
    private IServiciosProductos servicioProducto;
    private IRepositorioProducto repoProducto;

    private String filtroIdCategoria;
    private String filtroDescripcion;
    private Double filtroMaxPrecio;
    private EstadoProducto filtroEstado;

    //private FacesContext facesContext;

    @PostConstruct
    public void init() {
        //facesContext = FacesContext.getCurrentInstance();
        repoProducto = FactoriaRepositorios.getRepositorio(Producto.class);
        servicioProducto = FactoriaServicios.getServicio(IServiciosProductos.class);
        cargarTodos();
    }

    public void cargarTodos() {
        try {
            productos = repoProducto.getAll();
        } catch (RepositorioException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error cargando productos: " + e.getMessage()));
            //if (facesContext != null) {
            //    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error cargando productos: " + e.getMessage()));
            //}
        }
    }

    public void buscar() {
        try {
            if ((filtroDescripcion == null )
                    && (filtroMaxPrecio == null)
                    && (filtroIdCategoria == null)
                    && (filtroEstado == null)) {
                cargarTodos();
                return;
            }

            productos = servicioProducto.buscarProductos(filtroIdCategoria, filtroDescripcion, filtroEstado, filtroMaxPrecio);
            if (productos == null || productos.isEmpty()) {
                //dejamos la tabla vacia
                productos = List.of();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "No se encontraron productos para los criterios indicados"));
            }

        } catch (EntidadNoEncontrada e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Categoría no encontrada: " + e.getMessage()));
        } catch (RepositorioException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error de repositorio: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }

    public void onRowSelect(SelectEvent<Producto> event) {
        this.seleccionado = event.getObject();
    }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
    public Producto getSeleccionado() { return seleccionado; }
    public void setSeleccionado(Producto seleccionado) { this.seleccionado = seleccionado; }

    public String getFiltroDescripcion() { return filtroDescripcion; }
    public void setFiltroDescripcion(String filtroDescripcion) { this.filtroDescripcion = filtroDescripcion; }
    public Double getFiltroMaxPrecio() { return filtroMaxPrecio; }
    public void setFiltroMaxPrecio(Double filtroMaxPrecio) { this.filtroMaxPrecio = filtroMaxPrecio; }
    public EstadoProducto getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(EstadoProducto filtroEstado) { this.filtroEstado = filtroEstado; }
    public String getFiltroIdCategoria() { return filtroIdCategoria; }
    public void setFiltroIdCategoria(String filtroIdCategoria) { this.filtroIdCategoria = filtroIdCategoria; }
}
