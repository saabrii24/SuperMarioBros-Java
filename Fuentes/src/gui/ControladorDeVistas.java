package gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import fabricas.Dominio1Factory;
import fabricas.Dominio2Factory;
import fabricas.EntidadesFactory;
import fabricas.SpritesFactory;
import logica.EntidadJugador;
import logica.EntidadLogica;
import logica.Juego;
import logica.Observer;

public class ControladorDeVistas implements ControladorEntreJuegoVista, ControladorJuegoVista {
    protected JFrame ventana;
    protected Font tipografia;
    protected JComboBox<SpritesFactory> combo_box;
    protected PanelPantallaPrincipal panel_pantalla_principal;
    protected PanelPantallaMapa panel_pantalla_mapa;
    protected PanelPantallaAyuda panel_pantalla_ayuda; 
    protected PanelPantallaRanking panel_pantalla_ranking;
    protected PanelPantallaModoDeJuego panel_pantalla_modo_de_juego; 
    protected Juego juego;
    protected EntidadesFactory generador;

    public ControladorDeVistas(Juego juego) {
        this.juego = juego;
        panel_pantalla_principal = new PanelPantallaPrincipal(this);
        panel_pantalla_mapa = new PanelPantallaMapa();
        panel_pantalla_ayuda = new PanelPantallaAyuda(this); 
        panel_pantalla_ranking = new PanelPantallaRanking(this); 
        panel_pantalla_modo_de_juego = new PanelPantallaModoDeJuego(this); 
        configurar_ventana();
        mostrar_pantalla_inicial();
        registrar_oyente_panel_principal();
        registrar_oyente_panel_modo_de_juego();
    }

    protected void configurar_ventana() {
        ventana = new JFrame("Super Mario Bros");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setSize(ConstantesVistas.VENTANA_ANCHO, ConstantesVistas.VENTANA_ALTO);
        ventana.setLocationRelativeTo(null);
        Image icono;
        try {
            icono = ImageIO.read(getClass().getResourceAsStream("/assets/imagenes/sprites/dominio1/mario_ocioso_derecha.png"));
            ventana.setIconImage(icono);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ventana.setVisible(true);
    }

    protected void registrar_oyente_panel_principal() {
        panel_pantalla_principal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                switch (evento.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        panel_pantalla_principal.mover_seleccion(true);
                        break;
                    case KeyEvent.VK_DOWN:
                        panel_pantalla_principal.mover_seleccion(false);
                        break;
                    case KeyEvent.VK_ENTER:
                        panel_pantalla_principal.ejecutar_accion_seleccionada();
                        break;
                }
            }
        });
    }
    
    protected void registrar_oyente_panel_modo_de_juego() {
    	panel_pantalla_modo_de_juego.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                switch (evento.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    	panel_pantalla_modo_de_juego.mover_seleccion(true);
                        break;
                    case KeyEvent.VK_DOWN:
                    	panel_pantalla_modo_de_juego.mover_seleccion(false);
                        break;
                    case KeyEvent.VK_ENTER:
                    	panel_pantalla_modo_de_juego.ejecutar_accion_seleccionada();
                        break;
                }
            }
        });
    }
    
    public void mostrar_pantalla_inicial() {
        ventana.setContentPane(panel_pantalla_principal);
        panel_pantalla_principal.setFocusable(true);
        panel_pantalla_principal.requestFocusInWindow(); 
        refrescar();
    }

    public void accionar_inicio_juego() {
        // juego.cargar_datos(new EntidadesFactory(new Dominio1Factory())); // Cargar los datos
        
    }

    public void mostrar_pantalla_mapa() {
        ventana.setContentPane(panel_pantalla_mapa);
        refrescar();
    }
    
    public void mostrar_pantalla_ayuda() {
        ventana.setContentPane(panel_pantalla_ayuda);
        refrescar();
    }

    @Override
    public void accionar_pantalla_ranking() {
        ventana.setContentPane(panel_pantalla_ranking);
        refrescar();
    }

    @Override
    public void accionar_pantalla_modo_juego() {
        ventana.setContentPane(panel_pantalla_modo_de_juego);
        panel_pantalla_modo_de_juego.setFocusable(true);
        panel_pantalla_modo_de_juego.requestFocusInWindow(); // Solicita el foco
        refrescar();        
        }
    

    protected void refrescar() {
        ventana.revalidate();
        ventana.repaint();
    }

    @Override
    public void cambiar_modo_juego(int modo) {
        // TODO Auto-generated method stub
    }

    @Override
    public Observer registrar_entidad(EntidadLogica entidad_logica) {
    	Observer observer_entidad = panel_pantalla_mapa.incorporar_entidad(entidad_logica);
    	System.out.println("aaa");
		refrescar();
		return observer_entidad;
    }

    @Override
    public Observer registrar_entidad(EntidadJugador entidad_jugador) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void mostrar_pantalla_ranking() {
        // Este método no es necesario, ya que se maneja a través de accionar_pantalla_ranking <- REVISAR
    }

    @Override
    public void mostrar_pantalla_modo_de_juego() {
        // Este método no es necesario, ya que se maneja a través de accionar_pantalla_modo_juego <- REVISAR
    }

    @Override
    public void mostrar_pantalla_victoria() {
        // TODO Auto-generated method stub
    }

    @Override
    public void mostrar_pantalla_derrota() {
        // TODO Auto-generated method stub
    }

	@Override
	public void accionar_pantalla_victoria() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accionar_pantalla_derrota() {
		// TODO Auto-generated method stub
		
	}

	public void notificar_eleccion(EntidadesFactory generador) {
		juego.cargar_datos(generador);		
	}

}
