package gui;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import entidades.mario.Mario;
import fabricas.EntidadesFactory;
import fabricas.SpritesFactory;
import gui.sonido.Sonido;
import logica.*;

public class ControladorDeVistas implements ControladorEntreJuegoVista, ControladorJuegoVista {
    protected JFrame ventana;
    protected JComboBox<SpritesFactory> combo_box;
    protected PanelPantallaPrincipal panel_pantalla_principal;
    protected PanelPantallaMapa panel_pantalla_mapa;
    protected PanelPantallaAyuda panel_pantalla_ayuda; 
    protected PanelPantallaRanking panel_pantalla_ranking;
    protected PanelPantallaModoDeJuego panel_pantalla_modo_de_juego; 
    protected Juego mi_juego;
    protected EntidadesFactory generador;
    protected Sonido sonido_juego;
    protected boolean sonido_activo = true;
    private long tiempo_ultimo_proyectil = 0;
    private static final long PROYECTIL_COOLDOWN = 1000; 

    
    public ControladorDeVistas(Juego juego) {
        mi_juego = juego;
        panel_pantalla_principal = new PanelPantallaPrincipal(this);
        panel_pantalla_mapa = new PanelPantallaMapa();
        panel_pantalla_ayuda = new PanelPantallaAyuda(this); 
        panel_pantalla_ranking = new PanelPantallaRanking(this); 
        panel_pantalla_modo_de_juego = new PanelPantallaModoDeJuego(this); 
        sonido_juego = new Sonido();
        configurar_ventana();
        mostrar_pantalla_inicial();
        registrar_oyente_panel_principal();
        registrar_oyente_panel_modo_de_juego();
        registrar_oyente_panel_mapa();
    }

    protected void configurar_ventana() {
        ventana = new JFrame("Super Mario Bros");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setSize(ConstantesVistas.VENTANA_ANCHO, ConstantesVistas.VENTANA_ALTO);
        ventana.setLocationRelativeTo(null);
        Image icono;
        try {
            icono = ImageIO.read(getClass().getResourceAsStream("/assets/imagenes/icon.png"));
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
    
    protected void registrar_oyente_panel_mapa() {
        panel_pantalla_mapa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                switch (evento.getKeyCode()) {
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                       Mario.get_instancia().set_direccion_mario(-1);
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                    	Mario.get_instancia().set_direccion_mario(1);
                        break;
                    case KeyEvent.VK_SPACE:
                    	long currentTime = System.currentTimeMillis();
                    	 if (currentTime - tiempo_ultimo_proyectil >= PROYECTIL_COOLDOWN) {
                             mi_juego.get_mapa_nivel_actual().agregar_bola_de_fuego(Mario.get_instancia().disparar());
                             tiempo_ultimo_proyectil = currentTime;
                         }
                        break;
                    case KeyEvent.VK_M:
                    	if(!sonido_activo) {
	                    	sonido_juego.activar_sonido();
	                    	sonido_activo = true;
                    	} else {
                    		sonido_juego.detener_musica_de_fondo();
	                    	sonido_activo = false;
                    	}
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent evento) {
                switch (evento.getKeyCode()) {
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_D:
                    	Mario.get_instancia().set_direccion_mario(0);                       
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
    	accionar_pantalla_modo_juego();
    }

    public void mostrar_pantalla_mapa() {
        ventana.setContentPane(panel_pantalla_mapa);
        panel_pantalla_mapa.setFocusable(true);
        panel_pantalla_mapa.requestFocusInWindow(); 
        sonido_juego.desactivar_sonido();
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
        panel_pantalla_modo_de_juego.requestFocusInWindow();
        refrescar();        
    }
    
    protected void refrescar() {
        ventana.revalidate();
        ventana.repaint();
    }

    public Observer registrar_entidad(EntidadLogica entidad_logica) {
        Observer observer_entidad = panel_pantalla_mapa.incorporar_entidad(entidad_logica);
        refrescar();
        return observer_entidad;
    }


    @Override
    public Observer registrar_entidad(EntidadJugador entidad_jugador) {
    	Observer observer_jugador = panel_pantalla_mapa.incorporar_entidad_jugador(entidad_jugador, mi_juego.get_nivel_actual());
    	refrescar();
        return observer_jugador;
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
		mi_juego.cargar_datos(generador);
	}

	public PanelPantallaMapa get_pantalla_mapa() {
		return panel_pantalla_mapa;
	}

	public void reproducir_efecto(String efecto) {
		sonido_juego.reproducir_efecto(efecto);
	}
	

}