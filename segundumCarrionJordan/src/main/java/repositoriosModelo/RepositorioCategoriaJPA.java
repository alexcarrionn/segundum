package repositoriosModelo;

import modelo.Categoria;
import repositorio.RepositorioJPA;

public class RepositorioCategoriaJPA  extends RepositorioJPA<Categoria> implements IRepositorioCategorias {

	@Override
	public Class<Categoria> getClase() {
		return Categoria.class;
	}

}
