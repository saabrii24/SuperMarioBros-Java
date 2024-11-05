package gui;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import entidades.BolaDeFuego;
import entidades.interfaces.EntidadJugador;
import entidades.interfaces.EntidadLogica;
import entidades.mario.Mario;
import fabricas.EntidadesFactory;
import fabricas.SpritesFactory;
import logica.*;

public class ControladorDeVistas implements ControladorJuegoVistaEntidades, ControladorJuegoVistaPaneles {
    protected JFrame ventana;
    protected PanelPantallaPrincipal panel_pantalla_principal;
    protected PanelPantallaMapa panel_pantalla_mapa;
    protected PanelPantallaAyuda panel_pantalla_ayuda; 
    protected PanelPantallaRanking panel_pantalla_ranking;
    protected PanelPantallaModoDeJuego panel_pantalla_modo_de_juego;
    protected PanelPantallaDerrota panel_pantalla_derrota;
    protected PanelPantallaVictoria panel_pantalla_victoria;
    protected Juego mi_juego;
    protected EntidadesFactory generador;
    protected Sonido sonido_juego;
    protected boolean sonido_activo = true;
    private Set<Integer> teclas_presionadas;
    private Timer timer_juego;
    private static final int FRAME_DELAY = 16;
    private boolean juego_pausado = false;
    
    public ControladorDeVistas(Juego juego) {
        mi_juego = juego;
        panel_pantalla_principal = new PanelPantallaPrincipal(this);
        panel_pantalla_mapa = new PanelPantallaMapa(null);
        panel_pantalla_ayuda = new PanelPantallaAyuda(this); 
        panel_pantalla_ranking = new PanelPantallaRanking(this, juego.get_controlador_ranking()); 
        panel_pantalla_modo_de_juego = new PanelPantallaModoDeJuego(this);
        panel_pantalla_derrota = new PanelPantallaDerrota(this);
        panel_pantalla_victoria = new PanelPantallaVictoria(this);
        sonido_juego = new Sonido();
        teclas_presionadas = new HashSet<>();
        inicializar_game_loop();
        configurar_ventana();
        accionar_pantalla_inicial();
        registrar_oyente_panel_principal();
        registrar_oyente_panel_modo_de_juego();
        registrar_oyente_panel_mapa();
    }

    protected void configurar_ventana() {
        ventana = new JFrame("Super Mario Bros");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
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
    
    private void inicializar_game_loop() {
        ActionListener game_loop = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar_estado_juego();
            }
        };
        timer_juego = new Timer(FRAME_DELAY, game_loop);
        timer_juego.start();
    }

    private void actualizar_estado_juego() {
        if (panel_pantalla_mapa.isFocusOwner()) {
            procesar_input();
        }
    }

    private void procesar_input() {
        // Procesar movimiento horizontal
        if (teclas_presionadas.contains(KeyEvent.VK_A) || teclas_presionadas.contains(KeyEvent.VK_LEFT)) {
            Mario.get_instancia().set_direccion(-1);
        } else if (teclas_presionadas.contains(KeyEvent.VK_D) || teclas_presionadas.contains(KeyEvent.VK_RIGHT)) {
            Mario.get_instancia().set_direccion(1);
        } else {
            Mario.get_instancia().set_direccion(0);
        }

        // Procesar salto
        if (teclas_presionadas.contains(KeyEvent.VK_W) || teclas_presionadas.contains(KeyEvent.VK_UP)) {
            Mario.get_instancia().saltar();
        }

        // Procesar disparo
        if (teclas_presionadas.contains(KeyEvent.VK_SPACE)) {
            BolaDeFuego proyectil = Mario.get_instancia().disparar();
            if (proyectil != null) {
                mi_juego.get_mapa_nivel_actual().agregar_bola_de_fuego(proyectil);
            }
        }
    }
    
    protected void registrar_oyente_panel_mapa() {
        panel_pantalla_mapa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                teclas_presionadas.add(evento.getKeyCode());
                
                if (evento.getKeyCode() == KeyEvent.VK_M) {
                    if (!sonido_activo) {
                        sonido_juego.activar_sonido();
                        sonido_activo = true;
                    } else {
                    	sonido_juego.pausar_musica_de_fondo();
                        sonido_activo = false;
                    }
                }
                if (evento.getKeyCode() == KeyEvent.VK_P) {
                    alternar_pausa();
                }
            }

            @Override
            public void keyReleased(KeyEvent evento) {
                teclas_presionadas.remove(evento.getKeyCode());
            }
        });
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

    public void accionar_pantalla_inicial() {
    	timer_juego.stop();
    	mi_juego.reiniciar_juego();
        ventana.setContentPane(panel_pantalla_principal);
        panel_pantalla_principal.setFocusable(true);
        panel_pantalla_principal.requestFocusInWindow(); 
        refrescar();
    }

    public void accionar_inicio_juego() {
    	accionar_pantalla_modo_juego();
    }

    public void accionar_pantalla_mapa() {
        ventana.setContentPane(panel_pantalla_mapa);
        panel_pantalla_mapa.setFocusable(true);
        panel_pantalla_mapa.requestFocusInWindow(); 
        sonido_juego.activar_sonido();
        timer_juego.start();
        refrescar();
    }
    
    public void accionar_pantalla_ayuda() {
        ventana.setContentPane(panel_pantalla_ayuda);
        refrescar();
    }

    public void accionar_pantalla_ranking() {
    	panel_pantalla_ranking.actualizar_ranking();
        ventana.setContentPane(panel_pantalla_ranking);
        refrescar();
    }

    public void accionar_pantalla_modo_juego() {
        ventana.setContentPane(panel_pantalla_modo_de_juego);
        panel_pantalla_modo_de_juego.setFocusable(true);
        panel_pantalla_modo_de_juego.requestFocusInWindow();
        refrescar();        
    }
    
    public void refrescar() {
        ventana.revalidate();
        ventana.repaint();
    }

    public Observer registrar_entidad(EntidadLogica entidad_logica) {
        Observer observer_entidad = panel_pantalla_mapa.incorporar_entidad(entidad_logica);
        refrescar();
        return observer_entidad;
    }

    public Observer registrar_entidad(EntidadJugador entidad_jugador) {
    	Observer observer_jugador = panel_pantalla_mapa.incorporar_entidad_jugador(entidad_jugador, mi_juego.get_nivel_actual());
    	refrescar();
        return observer_jugador;
    }

	public void accionar_pantalla_victoria() {
		timer_juego.stop();
		sonido_juego.reproducir_efecto("stage_clear");
		sonido_juego.detener_musica_de_fondo();
		ventana.setContentPane(panel_pantalla_victoria);
        refrescar();
	}

	public void accionar_pantalla_derrota() {
		timer_juego.stop();
		sonido_juego.detener_musica_de_fondo();
		ventana.setContentPane(panel_pantalla_derrota);
        refrescar();
	}

	public void notificar_eleccion(SpritesFactory fabrica_sprites) { 
		panel_pantalla_mapa.set_dominio(fabrica_sprites);
		panel_pantalla_mapa.actualizar_panel_mapa(panel_pantalla_mapa.get_layered_pane());
        generador = new EntidadesFactory(fabrica_sprites);
		mi_juego.cargar_datos(generador); 
	}
	
	public void alternar_pausa() {
	    juego_pausado = !juego_pausado;
	    if (juego_pausado) {
	        mi_juego.pausar_juego();
	        panel_pantalla_mapa.mostrar_pausa();
	        sonido_juego.pausar_musica_de_fondo();
	    } else {
	        mi_juego.reanudar_juego();
	        panel_pantalla_mapa.ocultar_pausa();
	        if (sonido_activo) {
	            sonido_juego.reproducir_musica_de_fondo();
	        }
	    }
	}

    public boolean esta_pausado() {
        return juego_pausado;
    }
	
	public PanelPantallaMapa get_pantalla_mapa() { return panel_pantalla_mapa; }
	public void reproducir_efecto(String efecto) { sonido_juego.reproducir_efecto(efecto); }

}