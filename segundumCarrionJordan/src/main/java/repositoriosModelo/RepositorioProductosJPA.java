package repositoriosModelo;


import modelo.Producto;
import repositorio.RepositorioJPA;

// Esta es la implementación concreta que usa JPA
public class RepositorioProductosJPA extends RepositorioJPA<Producto> implements IRepositorioProducto {

    @Override
    public Class<Producto> getClase() {
        return Producto.class;
    }
}