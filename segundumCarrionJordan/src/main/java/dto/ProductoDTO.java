package dto;
import java.io.Serializable;
import java.time.LocalDateTime;

import modelo.Categoria;
import modelo.EstadoProducto;
import modelo.LugarRecogida;
import modelo.Usuario;

public class ProductoDTO implements Serializable {
	private String id;
	private String titulo; 
	private String descripcion;
	private double precio;
	private EstadoProducto estado;
	private LocalDateTime fechaPublicacion;
	// private Categoria categoria; //Se cambia para evitar LazyInitializationException
	private int visualizaciones;
	private boolean envioDisponible;
    private LugarRecogida lugarRecogida;
	//private Usuario vendedor; //Se cambia para evitar LazyInitializationException
    
    //Se añaden los siguientes campos para evitar LazyInitializationException
    private String nombreCategoria;
    private String nombreVendedor;
    
    /* CAMBIAMOS EL CONSTRUCTOR PARA INICIALIZAR LOS NUEVOS CAMPOS Y EVITAR LazyInitializationException 
   	public ProductoDTO(String id,String titulo, String descripcion, double precio, EstadoProducto estado, LocalDateTime fechaPublicacion,
			Categoria categoria, int visualizaciones, boolean envioDisponible, LugarRecogida lugarRecogida,
			Usuario vendedor) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
		this.fechaPublicacion = fechaPublicacion;
		this.categoria = categoria;
		this.visualizaciones = visualizaciones;
		this.envioDisponible = envioDisponible;
		this.lugarRecogida = lugarRecogida;
		this.vendedor = vendedor;
	}
	*/
	public ProductoDTO(String id, String titulo, String descripcion, double precio, EstadoProducto estado,
			LocalDateTime fechaPublicacion, int visualizaciones, boolean envioDisponible,
			modelo.LugarRecogida lugarRecogida, String nombreCategoria, String nombreVendedor) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
		this.fechaPublicacion = fechaPublicacion;
		this.visualizaciones = visualizaciones;
		this.envioDisponible = envioDisponible;
		this.lugarRecogida = lugarRecogida;
		this.nombreCategoria = nombreCategoria;
		this.nombreVendedor = nombreVendedor;
	}
    	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public EstadoProducto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProducto estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	/*public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}*/

	public int getVisualizaciones() {
		return visualizaciones;
	}

	public void setVisualizaciones(int visualizaciones) {
		this.visualizaciones = visualizaciones;
	}

	public boolean isEnvioDisponible() {
		return envioDisponible;
	}

	public void setEnvioDisponible(boolean envioDisponible) {
		this.envioDisponible = envioDisponible;
	}

	public LugarRecogida getLugarRecogida() {
		return lugarRecogida;
	}

	public void setLugarRecogida(LugarRecogida lugarRecogida) {
		this.lugarRecogida = lugarRecogida;
	}

	/*public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}*/
	
	public String getNombreCategoria() {
        return nombreCategoria;
    }
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }
    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }
	
}