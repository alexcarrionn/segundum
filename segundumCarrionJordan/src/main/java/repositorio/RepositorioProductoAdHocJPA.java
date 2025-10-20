package repositorio;

import repositoriosModelo.RepositorioProductosJPA;

/**
 * Implementacion JPA del repositorio ad-hoc de Producto
 * Esta clase nos va a permitir implementar metodos de busqueda que no se puedan implemmentar en el repositorio generico
 */

public class RepositorioProductoAdHocJPA extends RepositorioProductosJPA implements RepositorioProductoAdHoc {
	
	//Aqui implementamos los metodos ad-hoc especificos para Producto
	//Por ejemplo, buscar productos por categoria, precio, etc.
	/*Public List<Producto> buscarPorCategoria(String categoria){
	 * // Implementacion del metodo
	 * 
	 */

}
