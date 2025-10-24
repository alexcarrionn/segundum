package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import repositorio.Identificable;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Categoria implements Identificable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@XmlAttribute
	private String id;
	
	//Utilizamos XmlElement para definir el nombre de los elementos hijos en el XML
	@XmlElement
	private String nombre;
	
	@Lob
	private String descripcion;
	@XmlAttribute
	private String ruta; 
	
	
	//Utilizamos XmlElement para definir el nombre de los elementos hijos en el XML
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @XmlElement(name="categoria")
    private List<Categoria> subcategorias = new ArrayList<>();
    
    
	public Categoria() {
	}
	
	public Categoria(String nombre, String descripcion, String ruta) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ruta = ruta;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Categoria> subcategorias) {
		this.subcategorias = subcategorias;
	}
	
	
}
