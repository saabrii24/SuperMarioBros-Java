package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

import fabricas.*;
import logica.Juego;

public class PanelPantallaModoDeJuego extends JPanel {

    private static final long serialVersionUID = 1L;
    private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;
    private JLabel icono_seleccion;
    private JButton boton_mario;
    private JButton boton_luigi; 
    private JButton boton_volver; 
    private MenuOpciones opcion_actual;

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
        configurar_tecla_volver();
    }

    private void agregar_imagen_fondo() {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-modo-de-juego.png")); // Reemplaza con tu imagen
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo.setIcon(icono_imagen_escalado);
        setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        imagen_fondo.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        add(imagen_fondo);
    }

    private void agregar_icono_seleccion() {
        ImageIcon icono_original = new ImageIcon(this.getClass().getResource("/assets/imagenes/icono-seleccion.png")); // Reemplaza con la ruta correcta
        Image imagen_escalada = icono_original.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        icono_seleccion = new JLabel(new ImageIcon(imagen_escalada));
        icono_seleccion.setBounds(0, 0, 25, 25);
        add(icono_seleccion);
    }

    private void agregar_botones_opcion() {
        boton_mario = new JButton(" ");
        boton_luigi = new JButton(" ");

        configurar_boton_transparente(boton_mario);
        configurar_boton_transparente(boton_luigi);

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

    private void configurar_boton_transparente(JButton boton) {
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
    }

    private void mantener_seleccion(MenuOpciones opcion) {
        this.opcion_actual = opcion;
        actualizar_icono_seleccion(); 
    }

    private void actualizar_icono_seleccion() {
        int x_offset = (ConstantesVistas.PANEL_ANCHO / 2) - 190; // Ajustar posición en X
        int y_offset = (opcion_actual == MenuOpciones.MARIO) ? (ConstantesVistas.PANEL_ALTO / 2 - 170) : (ConstantesVistas.PANEL_ALTO / 2 - 110); // Ajustar según la opción

        icono_seleccion.setLocation(x_offset, y_offset);
    }

    private void agregar_boton_volver() {
        boton_volver = new JButton(" ");
        configurar_boton_transparente(boton_volver);
        boton_volver.setBounds(ConstantesVistas.PANEL_ANCHO - 180, 15, 150, 50);
        boton_volver.addActionListener(e -> controlador_vistas.accionar_pantalla_inicial());
        add(boton_volver);
    }

    private void configurar_tecla_volver() {
        Action volver_action = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                controlador_vistas.accionar_pantalla_inicial();
            }
        };
        
        InputMap input_map = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        input_map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "volver");
        getActionMap().put("volver", volver_action);
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
        SpritesFactory fabrica_sprites;
        switch (opcion_actual) {
            case MARIO:
                fabrica_sprites = new Dominio1Factory();
                break;
            case LUIGI:
                fabrica_sprites = new Dominio2Factory();
                break;
            default:
                return;
        }
        // Usar el nuevo método para cambiar el dominio
        Juego.get_instancia().cambiar_dominio(fabrica_sprites);
        controlador_vistas.notificar_eleccion(fabrica_sprites);
        controlador_vistas.accionar_pantalla_mapa();
    }
}
