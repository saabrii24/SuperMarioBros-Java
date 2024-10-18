package logica;

import java.awt.Graphics;

public class ObserverEntidades extends ObserverGrafico {
	
	private static final long serialVersionUID = -331627302579244451L;
	
	public ObserverEntidades(EntidadLogica entidad_observada) {
		super(entidad_observada);
		actualizar();
	}
	
}
