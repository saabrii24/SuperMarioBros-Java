package gui;

import javax.swing.JFrame;

import logica.EntidadJugador;
import logica.EntidadLogica;
import logica.Juego;
import logica.Observer;

public class ControladorDeVistas implements ControladorEntreJuegoVista, ControladorJuegoVista {
	protected JFrame ventana;
	protected PanelPantallaPrincipal panel_pantalla_principal;
	protected PanelPantallaMapa panel_pantalla_mapa;
	
	protected Juego juego;
	
	public ControladorDeVistas(Juego juego) {
		this.juego = juego;
		panel_pantalla_principal = new PanelPantallaPrincipal(this);
		panel_pantalla_mapa = new PanelPantallaMapa();
		configurar_ventana();
		registrar_oyente_ventana();
	}
	
	protected void configurar_ventana() {
		ventana = new JFrame("Super Mario Bros");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setSize(ConstantesVistas.VENTANA_ANCHO, ConstantesVistas.VENTANA_ALTO);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	
	protected void registrar_oyente_ventana() {
		// To DO
	}
	
	// De interfaz para launcher
	
	public void mostrar_pantalla_inicial() {
		ventana.setContentPane(panel_pantalla_principal);
		refrescar();
	}
	
	// De interfaz ControladorDeVistas
	
	public void accionar_inicio_juego() {
		//juego.iniciar();
	}
	
	public void accionar_pantalla_puntajes() {
		// To Do
	}
	
	public void accionar_pantalla_modo_juego() {
		// To Do
	}
	
	public void cambiar_modo_juego(int modo) {
		// To Do
		mostrar_pantalla_inicial();
	}
	
	// De interfaz ComandosJuegoVista
	
	public Observer registrar_entidad(EntidadLogica entidad_logica) {
		Observer observer_entidad = panel_pantalla_mapa.incorporar_entidad(entidad_logica);
		refrescar();
		return observer_entidad;
	}
	
	public Observer registrar_entidad(EntidadJugador entidad_jugador) {
		Observer observer_jugador = panel_pantalla_mapa.incorporar_entidad_jugador(entidad_jugador);
		refrescar();
		return observer_jugador;
	}
	
	public void mostrar_pantalla_mapa() {
		ventana.setContentPane(panel_pantalla_mapa);
		refrescar();
	}

	public void mostrar_pantalla_fin_juego() {
		// To Do
	}
	
	protected void refrescar() {
		ventana.revalidate();
		ventana.repaint();
	}

	@Override
	public Observer registrar_silueta(EntidadLogica silueta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mostrar_pantalla_carrera() {
		// TODO Auto-generated method stub
		
	}
}
