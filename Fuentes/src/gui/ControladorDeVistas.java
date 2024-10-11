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
        mostrar_pantalla_inicial(); // Mostrar la pantalla inicial al crear el controlador
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

    public void mostrar_pantalla_inicial() {
        ventana.setContentPane(panel_pantalla_principal);
        refrescar();
    }

    public void accionar_inicio_juego() {
        //juego.cargar_datos(new EntidadesFactory(new Dominio1Factory())); // Cargar los datos necesarios
        mostrar_pantalla_mapa();
    }

    public void mostrar_pantalla_mapa() {
    	ventana.getContentPane().removeAll();
        ventana.setContentPane(panel_pantalla_mapa);
        refrescar();
    }

    protected void refrescar() {
        ventana.revalidate();
        ventana.repaint();
    }

	@Override
	public void accionar_pantalla_puntajes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accionar_pantalla_modo_juego() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cambiar_modo_juego(int modo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Observer registrar_entidad(EntidadLogica entidad_logica) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Observer registrar_entidad(EntidadJugador entidad_jugador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mostrar_pantalla_fin_juego() {
		// TODO Auto-generated method stub
		
	}

    // Otros métodos de la interfaz
}