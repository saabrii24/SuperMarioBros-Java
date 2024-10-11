package gui;

import logica.*;

public interface ControladorEntreJuegoVista {
	public Observer registrar_entidad(EntidadLogica entidad_logica);
	public Observer registrar_entidad(EntidadJugador entidad_jugador);
	public void mostrar_pantalla_mapa();
	public void mostrar_pantalla_ayuda();
	public void mostrar_pantalla_ranking();
	public void mostrar_pantalla_modo_de_juego();
	public void mostrar_pantalla_victoria();
	public void mostrar_pantalla_derrota();
}
