package gui;

import logica.*;

public interface ControladorEntreJuegoVista {
	public Observer registrar_entidad(EntidadLogica entidad_logica);
	public Observer registrar_entidad(EntidadJugador entidad_jugador);
	public Observer registrar_silueta(EntidadLogica silueta);
	public void mostrar_pantalla_carrera();
	public void mostrar_pantalla_fin_juego();
}
