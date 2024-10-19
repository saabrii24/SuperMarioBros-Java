package gui;

import entidades.interfaces.EntidadJugador;
import entidades.interfaces.EntidadLogica;
import entidades.interfaces.Observer;
import logica.*;

public interface ControladorEntreJuegoVista {
	public Observer registrar_entidad(EntidadLogica entidad_logica);
	public Observer registrar_entidad(EntidadJugador entidad_jugador);
	public void mostrar_pantalla_mapa();
	public void mostrar_pantalla_ayuda();
}
