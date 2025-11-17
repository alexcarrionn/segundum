package modelo.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import modelo.Categoria; // Importa tu entidad Categoria
import modelo.EstadoProducto; // Importa tu enum
import servicio.IServiciosCategorias;
import servicio.IServiciosProductos;
import servicio.FactoriaServicios; // Asumo que tenéis esta factoría, igual que en encuestas

@Named
@ViewScoped
public class CrearProductoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Atributos para el formulario ---
    private String titulo;
    private String descripcion;
    private Double precio;
    private EstadoProducto estado;
    private String idCategoria; // Guardará el ID de la categoría seleccionada
    private boolean envioDisponible = false; // Valor por defecto

    // --- Listas para los desplegables ---
    private List<Categoria> categoriasDisponibles;
    private EstadoProducto[] estadosDisponibles;
    
    // --- Beans y Servicios Inyectados ---
    @Inject
    private ControlAccesoBean controlAccesoBean; // Para saber quién es el vendedor

    @Inject
    private FacesContext facesContext;

    private IServiciosProductos servicioProductos;
    private IServiciosCategorias servicioCategorias;
    
    // --- Para el diálogo de resultado (Semana 10) ---
    private boolean errorAlCrear = false;
    private String idProductoCreado;

    public CrearProductoBean() {
        // Obtenemos los servicios usando la factoría
        servicioProductos = FactoriaServicios.getServicio(IServiciosProductos.class);
        servicioCategorias = FactoriaServicios.getServicio(IServiciosCategorias.class);
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

        // 2. Cargar listas para los desplegables
        try {
            estadosDisponibles = EstadoProducto.values(); // Carga los valores del enum
            categoriasDisponibles = servicioCategorias.obtenerTodasLasCategorias(); // Usa el método que añadimos
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron cargar los datos para el formulario."));
            e.printStackTrace();
        }
    }

    public void crearProducto() {
        try {
            // 1. Obtener el ID del vendedor (el usuario logueado)
            String idVendedor = controlAccesoBean.getUsuarioLogueado().getId();

            // 2. Llamar al servicio de alta (el que ya teníais de la Práctica 1)
            idProductoCreado = servicioProductos.altaProducto(
                titulo, descripcion, precio, estado, 
                idCategoria, envioDisponible, idVendedor
            );

            // 3. Preparar diálogo de éxito
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Producto creado correctamente."));
            errorAlCrear = false;
            limpiarFormulario(); // Opcional: limpiar campos tras el éxito

        } catch (Exception e) {
            // 4. Preparar diálogo de error
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el producto: " + e.getMessage()));
            errorAlCrear = true;
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        this.titulo = null;
        this.descripcion = null;
        this.precio = null;
        this.estado = null;
        this.idCategoria = null;
        this.envioDisponible = false;
    }

    // --- Getters y Setters (Necesarios para que JSF conecte el bean con la vista) ---

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

    public boolean isEnvioDisponible() { return envioDisponible; }
    public void setEnvioDisponible(boolean envioDisponible) { this.envioDisponible = envioDisponible; }

    // --- Getters para las listas de desplegables ---
    
    public EstadoProducto[] getEstadosDisponibles() {
        return estadosDisponibles;
    }

    public List<Categoria> getCategoriasDisponibles() {
        return categoriasDisponibles;
    }

    // --- Getters para el diálogo de resultado ---

    public boolean isErrorAlCrear() {
        return errorAlCrear;
    }

    public String getIdProductoCreado() {
        return idProductoCreado;
    }
}