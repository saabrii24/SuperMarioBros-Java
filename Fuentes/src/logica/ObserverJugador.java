package logica;

import entidades.interfaces.EntidadJugador;
import gui.PanelPantallaMapa;

public class ObserverJugador extends ObserverGrafico{

	private static final long serialVersionUID = 7017967195998406908L;
	private PanelPantallaMapa panel_pantalla_mapa;
	private EntidadJugador jugador_observado;
	
	public ObserverJugador(PanelPantallaMapa panel_pantalla_mapa, EntidadJugador jugador_observado) {
		super(jugador_observado);
		this.panel_pantalla_mapa = panel_pantalla_mapa;
		this.jugador_observado = jugador_observado;
		actualizar();
	}
	
	public void actualizar() {
		super.actualizar();
		panel_pantalla_mapa.actualizar_scroll_hacia_jugador(jugador_observado);
	}
	
}
