package modelo.web;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import org.primefaces.event.SelectEvent;

import modelo.EstadoProducto;
import modelo.Producto;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositoriosModelo.IRepositorioProducto;
import servicio.FactoriaServicios;
import servicio.IServiciosProductos;

public class BusquedaBean { 

    private List<Producto> productos;
    private Producto seleccionado;
    private IServiciosProductos servicioProducto; 
    private IRepositorioProducto repoProducto;

    //Ponemos los filtros que vamos a poder usar
    private String filtroIdCategoria; 
    private String filtroDescripcion;
    private Double filtroMaxPrecio;
    private EstadoProducto filtroEstado;
    
    private FacesContext facesContext;

    
    public void init() {
        // Inicializar la lista de productos (ejemplo estático)
    	facesContext = FacesContext.getCurrentInstance();
        repoProducto = FactoriaRepositorios.getRepositorio(Producto.class);
        servicioProducto = FactoriaServicios.getServicio(IServiciosProductos.class);
        cargarTodos();
    }

    public void cargarTodos() {
        try {
			productos = repoProducto.getAll();
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
    }

    //Metodo para poder hacer las busquedas segun unos filtros
    public void buscar(){
    	try {
    		
    		if((filtroDescripcion == null || filtroDescripcion.isBlank()) &&
			   (filtroMaxPrecio == null || filtroMaxPrecio.isNaN()) &&
			   (filtroIdCategoria == null || filtroIdCategoria.isBlank()) &&
			   (filtroEstado == null)) {
				cargarTodos();
				return;
			}
			
			productos = servicioProducto.buscarProductos(filtroIdCategoria, filtroDescripcion,filtroEstado, filtroMaxPrecio);
			if (productos == null || productos.isEmpty()) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "No se encontraron productos para los criterios indicados"));
			}
    		
    	} catch (EntidadNoEncontrada e) {
           facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Categoría no encontrada: " + e.getMessage()));
        } catch (RepositorioException e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error de repositorio: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    	
    }

    public void onRowSelect(SelectEvent<Producto> event) {
    }
    
    // Getters y Setters
    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    public Producto getSeleccionado() {
        return seleccionado;
    }
    public void setSeleccionado(Producto seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getFiltroTitulo() {
        return filtroDescripcion;
    }
    public void setFiltroTitulo(String filtroTitulo) {
        this.filtroDescripcion = filtroTitulo;
    }
    public Double getFiltroMaxPrecio() {
        return filtroMaxPrecio;
    }
    public void setFiltroMaxPrecio(Double filtroMaxPrecio) {
        this.filtroMaxPrecio = filtroMaxPrecio;
    }
    
    public EstadoProducto getFiltroEstado() {
        return filtroEstado;
    }
    public void setFiltroEstado(EstadoProducto filtroEstado) {
        this.filtroEstado = filtroEstado;
    }

	public String getFiltroIdCategoria() {
		return filtroIdCategoria;
	}

	public void setFiltroIdCategoria(String filtroIdCategoria) {
		this.filtroIdCategoria = filtroIdCategoria;
	}
    
}