package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelPantallaPrincipal extends JPanel {

    private static final long serialVersionUID = -3884090044789061650L;
    private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;
    private JButton boton_iniciar;
    private JButton boton_puntajes;
    private MenuOption opcionActual;

    public enum MenuOption {
        START_GAME,
        VIEW_SCORES;

        public MenuOption next() {
            return values()[(ordinal() + 1) % values().length];
        }

        public MenuOption previous() {
            return values()[(ordinal() - 1 + values().length) % values().length];
        }
    }

    public PanelPantallaPrincipal(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
        this.opcionActual = MenuOption.START_GAME;
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        
        setLayout(null);
        agregar_imagen_fondo();
        agregar_boton_iniciar();
        agregar_boton_puntaje();
        actualizarSeleccion();
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

    protected void agregar_boton_iniciar() {
        boton_iniciar = new JButton("Iniciar Juego");
        decorar_boton_iniciar();
        registrar_oyente_boton_iniciar();
        add(boton_iniciar);
    }

    protected void agregar_boton_puntaje() {
        boton_puntajes = new JButton("Ver Puntajes");
        decorar_boton_puntajes();
        registrar_oyente_boton_puntajes();
        add(boton_puntajes);
    }

    protected void decorar_boton_iniciar() {
        transparentar_boton(boton_iniciar);
        boton_iniciar.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 100, ConstantesVistas.PANEL_ALTO - 150, 200, 50);
    }

    protected void decorar_boton_puntajes() {
        transparentar_boton(boton_puntajes);
        boton_puntajes.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 130, ConstantesVistas.PANEL_ALTO - 90, 260, 50);
    }

    protected void registrar_oyente_boton_iniciar() {
        boton_iniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador_vistas.accionar_inicio_juego();
            }
        });
    }

    protected void registrar_oyente_boton_puntajes() {
        boton_puntajes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador_vistas.accionar_pantalla_puntajes();
            }
        });
    }

    protected void transparentar_boton(JButton boton) {
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
    }

    public void moverSeleccion(boolean haciaArriba) {
        if (haciaArriba) {
            opcionActual = opcionActual.previous();
        } else {
            opcionActual = opcionActual.next();
        }
        actualizarSeleccion();
    }

    private void actualizarSeleccion() {
        boton_iniciar.setBorderPainted(opcionActual == MenuOption.START_GAME);
        boton_puntajes.setBorderPainted(opcionActual == MenuOption.VIEW_SCORES);
    }
}
