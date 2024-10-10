package logica;

import java.util.List;
import entidades.Entidad;
import entidades.mario.Mario;
import fabricas.*;
import gui.ControladorEntreJuegoVista;
import niveles.GeneradorNivel;
import niveles.Nivel;

public class Juego implements Runnable {
	 
	protected ControladorEntreJuegoVista controlador_vistas;
	protected Nivel nivel_actual;
	protected SpritesFactory fabrica_sprites;
	protected EntidadesFactory fabrica_entidades;
	protected Mapa mapa_nivel_actual;
	protected int tiempo_restante;
	protected int contador_puntos;
	public int vidas = 3;
	
	public Juego() {
		mapa_nivel_actual = new Mapa(this);
		fabrica_sprites = new Dominio1Factory();
		fabrica_entidades = new EntidadesFactory(fabrica_sprites);
	}
	
	public void cargarDatos(EntidadesFactory generador) {
		  nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(getClass().getResourceAsStream("/niveles/1-nivel.txt"), generador, mapa_nivel_actual);
		  fabrica_entidades = generador;
	}
	
	public void set_controlador_vistas(ControladorEntreJuegoVista controlador_vistas) {
		this.controlador_vistas = controlador_vistas;
	}
	
	public void iniciar() {
		registrar_observers();
		controlador_vistas.mostrar_pantalla_carrera();
	}
	
	protected void registrar_observers() {
		registrar_observer_jugador(mapa_nivel_actual.get_mario());
		registrar_observers_para_entidades(mapa_nivel_actual.get_entidades());
	}
	
	protected void registrar_observer_jugador(Mario jugador_mario) {
		Observer observer_jugador = controlador_vistas.registrar_entidad(jugador_mario);
		jugador_mario.registrar_observer(observer_jugador);
	}

	protected void registrar_observers_para_entidades(List<? extends Entidad> entidades) {
		for(Entidad entidad : entidades) {
			Observer observer = controlador_vistas.registrar_entidad(entidad);
			entidad.registrar_observer(observer);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
