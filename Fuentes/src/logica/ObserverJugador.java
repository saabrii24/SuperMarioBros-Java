package logica;

import entidades.interfaces.EntidadJugador;
import gui.PanelPantallaMapa;

@SuppressWarnings("serial")
public class ObserverJugador extends ObserverGrafico{
	
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
	
    public void notificar_eliminar_del_mapa() {
        setIcon(null); 
        setVisible(false); 
    }
	
}
