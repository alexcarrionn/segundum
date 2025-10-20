package repositorio;

import modelo.Producto;

/**
 * Repositorio ad-hoc de Producto Esta será como la interfaz de la clase RepositorioProductoAdHocJPA 
 *
 */ 

public interface RepositorioProductoAdHoc extends RepositorioString<Producto>{
	
	//Aqui vamos a implementar metodos ad-hoc especificos para Producto
	//Por ejemplo, buscar productos por categoria, precio, etc.
	//List<Producto> buscarPorCategoria(String categoria);

}
