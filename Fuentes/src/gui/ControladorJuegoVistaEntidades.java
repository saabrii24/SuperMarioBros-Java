package gui;

import entidades.interfaces.EntidadJugador;
import entidades.interfaces.EntidadLogica;
import logica.*;

public interface ControladorJuegoVistaEntidades {
	Observer registrar_entidad(EntidadLogica entidad_logica);
	Observer registrar_entidad(EntidadJugador entidad_jugador);
}
