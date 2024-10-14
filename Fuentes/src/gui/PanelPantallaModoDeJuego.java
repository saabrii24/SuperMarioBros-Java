package gui;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fabricas.Dominio1Factory;
import fabricas.Dominio2Factory;
import fabricas.EntidadesFactory;
import fabricas.SpritesFactory;
import gui.PanelPantallaPrincipal.MenuOpciones;

public class PanelPantallaModoDeJuego extends JPanel {

    private static final long serialVersionUID = 1L;
    private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;
    private JLabel icono_seleccion;
    private JButton boton_mario;
    private JButton boton_luigi; 
    private JButton boton_volver; 
    private MenuOpciones opcion_actual;
    private EntidadesFactory generador;

    public enum MenuOpciones {
        MARIO,
        LUIGI;

        public MenuOpciones siguiente() {
            return values()[(ordinal() + 1) % values().length];
        }

        public MenuOpciones anterior() {
            return values()[(ordinal() - 1 + values().length) % values().length];
        }
    }

    public PanelPantallaModoDeJuego(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
        this.opcion_actual = MenuOpciones.MARIO; // Opción inicial
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        setLayout(null);
               
        agregar_icono_seleccion();
        agregar_botones_opcion();
        agregar_boton_volver();
        agregar_imagen_fondo();
        actualizar_icono_seleccion();
    }

    protected void agregar_imagen_fondo() {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-modo-de-juego.png")); // Reemplaza con tu imagen
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo.setIcon(icono_imagen_escalado);
        setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        imagen_fondo.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        add(imagen_fondo);
    }

    protected void agregar_icono_seleccion() {
        ImageIcon icono_original = new ImageIcon(this.getClass().getResource("/assets/imagenes/icono-seleccion.png")); // Reemplaza con la ruta correcta
        Image imagen_escalada = icono_original.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        icono_seleccion = new JLabel(new ImageIcon(imagen_escalada));
        icono_seleccion.setBounds(0, 0, 25, 25);
        add(icono_seleccion);
    }

    protected void agregar_botones_opcion() {
        boton_mario = new JButton(" ");
        boton_luigi = new JButton(" ");

        // Configurar botones
        transparentar_boton(boton_mario);
        transparentar_boton(boton_luigi);

        boton_mario.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 150, ConstantesVistas.PANEL_ALTO / 2 - 180, 300, 50);
        boton_luigi.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 150, ConstantesVistas.PANEL_ALTO / 2 - 120, 300, 50);

        boton_mario.addActionListener(e -> {
            mantener_seleccion(MenuOpciones.MARIO);
            ejecutar_accion_seleccionada();
        });

        boton_luigi.addActionListener(e -> {
            mantener_seleccion(MenuOpciones.LUIGI);
            ejecutar_accion_seleccionada();
        });

        add(boton_mario);
        add(boton_luigi);
    }


    protected void mantener_seleccion(MenuOpciones opcion) {
        this.opcion_actual = opcion;
        actualizar_icono_seleccion(); 
    }

	protected void transparentar_boton(JButton boton) {
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
    }

    protected void actualizar_icono_seleccion() {
        int xOffset = (ConstantesVistas.PANEL_ANCHO / 2) - 190; // Ajustar posición en X
        int yOffset = (opcion_actual == MenuOpciones.MARIO) ? (ConstantesVistas.PANEL_ALTO / 2 - 170) : (ConstantesVistas.PANEL_ALTO / 2 - 110); // Ajustar según la opción

        icono_seleccion.setLocation(xOffset, yOffset);
    }
    
    protected void agregar_boton_volver() {
    	boton_volver = new JButton(" ");
        boton_volver.setOpaque(false);
        boton_volver.setContentAreaFilled(false);
        boton_volver.setBorderPainted(false);
        boton_volver.setBounds(ConstantesVistas.PANEL_ANCHO - 180, 15, 150, 50);
        boton_volver.addActionListener(e -> controlador_vistas.mostrar_pantalla_inicial());
        add(boton_volver);
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
	        case MARIO:
	        	SpritesFactory fabrica_mario = new Dominio1Factory();
	        	generador = new EntidadesFactory(fabrica_mario);	        	
	        	controlador_vistas.notificar_eleccion(generador);
	        	controlador_vistas.mostrar_pantalla_mapa();
	            break;
	        case LUIGI:
	            SpritesFactory fabrica_luigi = new Dominio2Factory();
	            generador = new EntidadesFactory(fabrica_luigi);
	            controlador_vistas.notificar_eleccion(generador);
	            controlador_vistas.mostrar_pantalla_mapa();
	            break;
        }
	}
}
