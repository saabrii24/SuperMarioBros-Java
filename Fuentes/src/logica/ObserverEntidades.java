package logica;

import entidades.interfaces.EntidadLogica;

@SuppressWarnings("serial")
public class ObserverEntidades extends ObserverGrafico {
	
	public ObserverEntidades(EntidadLogica entidad_observada) {
		super(entidad_observada);
		actualizar();
	}
	
}
