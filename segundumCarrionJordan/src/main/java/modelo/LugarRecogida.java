package modelo;

import javax.persistence.*;

@Entity
public class LugarRecogida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	
	@Lob
	private String descripcion;
	
	private double longitud;
	
	private double latitud;
	
	public LugarRecogida() {
	}
	
	public LugarRecogida(String descripcion, double longitud, double latitud) {
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.latitud = latitud;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public double getLongitud() {
		return longitud;
	}
	
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	public double getLatitud() {
		return latitud;
	}
	
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	
}
