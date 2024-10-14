package gui;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelPantallaPrincipal extends JPanel {

    private static final long serialVersionUID = -3884090044789061650L;
    private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;
    private JLabel icono_seleccion;
    private JButton boton_iniciar;
    private JButton boton_puntajes;
    private JButton boton_ayuda; 
    private MenuOpciones opcion_actual;

    public enum MenuOpciones {
        COMENZAR_JUEGO,
        RANKING;

        public MenuOpciones siguiente() {
            return values()[(ordinal() + 1) % values().length];
        }

        public MenuOpciones anterior() {
            return values()[(ordinal() - 1 + values().length) % values().length];
        }
    }

    public PanelPantallaPrincipal(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
        this.opcion_actual = MenuOpciones.COMENZAR_JUEGO;
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        setLayout(null);
        
        agregar_icono_seleccion();
        agregar_boton_iniciar();
        agregar_boton_puntaje();
        agregar_boton_ayuda();
        agregar_imagen_fondo();
        actualizar_icono_seleccion();
        
    }

    protected void agregar_imagen_fondo() {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-inicial.png"));
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo.setIcon(icono_imagen_escalado);
        setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        imagen_fondo.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        add(imagen_fondo);
    }

    protected void agregar_icono_seleccion() {
        ImageIcon icono_original = new ImageIcon(this.getClass().getResource("/assets/imagenes/icono-seleccion.png"));
        Image imagen_escalada = icono_original.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        icono_seleccion = new JLabel(new ImageIcon(imagen_escalada));
        icono_seleccion.setBounds(0, 0, 25, 25);
        add(icono_seleccion);
    }

    protected void actualizar_icono_seleccion() {
        int xOffset = (ConstantesVistas.PANEL_ANCHO / 2) - 240;
        int yOffset = 0;

        switch (opcion_actual) {
            case COMENZAR_JUEGO:
                yOffset = ConstantesVistas.PANEL_ALTO - 320 + 10;
                break;
            case RANKING:
                yOffset = ConstantesVistas.PANEL_ALTO - 255 + 10;
                break;
        }
        icono_seleccion.setLocation(xOffset, yOffset);
    }

    protected void agregar_boton_iniciar() {
        boton_iniciar = new JButton(" ");
        transparentar_boton(boton_iniciar);
        boton_iniciar.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 200, ConstantesVistas.PANEL_ALTO - 320, 400, 50);
        registrar_oyente_boton_iniciar();
        add(boton_iniciar);
    }

    protected void agregar_boton_puntaje() {
        boton_puntajes = new JButton(" ");
        transparentar_boton(boton_puntajes);
        boton_puntajes.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 180, ConstantesVistas.PANEL_ALTO - 255, 360, 50);
        registrar_oyente_boton_puntajes();
        add(boton_puntajes);
    }

    protected void agregar_boton_ayuda() {
        boton_ayuda = new JButton(" ");
        transparentar_boton(boton_ayuda);
        boton_ayuda.setBounds(ConstantesVistas.PANEL_ANCHO - 180, 15, 150, 50);
        boton_ayuda.addActionListener(e -> controlador_vistas.mostrar_pantalla_ayuda());
        add(boton_ayuda);
    }

    protected void registrar_oyente_boton_iniciar() {
    	boton_iniciar.addActionListener(e -> {
    		mantener_seleccion(MenuOpciones.COMENZAR_JUEGO);
    		controlador_vistas.accionar_inicio_juego();
    	});
        
    }

    protected void registrar_oyente_boton_puntajes() {
    	boton_puntajes.addActionListener(e -> {
    		mantener_seleccion(MenuOpciones.RANKING);
    		controlador_vistas.accionar_pantalla_ranking();
    	});
    }

    protected void transparentar_boton(JButton boton) {
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
    }

    protected void mover_seleccion(boolean hacia_arriba) {
        if (hacia_arriba) {
        	opcion_actual = opcion_actual.anterior();
        } else {
        	opcion_actual = opcion_actual.siguiente();
        }
        actualizar_icono_seleccion();
    }

    protected void ejecutar_accion_seleccionada() {
        switch (opcion_actual) {
            case COMENZAR_JUEGO:
            	controlador_vistas.accionar_pantalla_modo_juego();
                break;
            case RANKING:
                controlador_vistas.accionar_pantalla_ranking();
                break;
        }
    }
    
    protected void mantener_seleccion(MenuOpciones opcion) {
        this.opcion_actual = opcion;
        actualizar_icono_seleccion(); // Actualizar el icono de selección al seleccionar un personaje
        
    }
}