package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.File;
import logica.ControladorRanking;
import ranking.Jugador;

public class PanelPantallaRanking extends JPanel {

    private static final long serialVersionUID = 1L;
    private ControladorDeVistas controlador_vistas;
    private ControladorRanking controlador_ranking;
    private JLabel imagen_fondo;
    private JButton boton_volver;
    private Font tipografia;

    public PanelPantallaRanking(ControladorDeVistas controlador_vistas, ControladorRanking controlador_ranking) {
        this.controlador_vistas = controlador_vistas;
        this.controlador_ranking = controlador_ranking;
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        setLayout(null);
        cargar_tipografia("src/assets/tipografia/mario-font.ttf");
        mostrar_ranking();
        agregar_imagen_fondo();
        agregar_boton_volver();
        volver_con_esc();
    }
    
    public void actualizar_ranking() {
        // Removemos todos los componentes excepto el fondo
        Component fondo = null;
        for (Component comp : getComponents()) {
            if (comp == imagen_fondo) {
                fondo = comp;
            }
        }
        removeAll();
        
        // Volvemos a agregar los elementos en el orden correcto
        mostrar_ranking();
        agregar_boton_volver();
        
        // Si teníamos un fondo, lo agregamos al final para que quede detrás
        if (fondo != null) {
            add(imagen_fondo);
        } else {
            agregar_imagen_fondo();
        }
        
        revalidate();
        repaint();
    }

    public void mostrar_ranking() {
        controlador_ranking.cargar_ranking();

        int yPosition = 118;
        for (int i = 0; i < Math.min(5, controlador_ranking.get_ranking().size()); i++) {
            Jugador jugador = controlador_ranking.get_ranking().get_jugador_posicion(i);

            JLabel nombre = crear_label(jugador.get_nombre(), 445, yPosition, 252, 38);
            JLabel puntos = crear_label(jugador.get_puntos().toString(), 750, yPosition, 142, 38);

            add(nombre);
            add(puntos);

            yPosition += 46;
        }
    }

    private JLabel crear_label(String texto, int x, int y, int ancho, int alto) {
        JLabel label = new JLabel(texto);
        label.setFont(tipografia);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, ancho, alto);
        label.setOpaque(false);
        return label;
    }

    protected void agregar_imagen_fondo() {
        if (imagen_fondo == null) {
            imagen_fondo = new JLabel();
        }

        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-ranking.png"));
        if (icono_imagen.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image imagen_escalada = icono_imagen.getImage().getScaledInstance(
                ConstantesVistas.PANEL_ANCHO, 
                ConstantesVistas.PANEL_ALTO, 
                Image.SCALE_SMOOTH
            );
            imagen_fondo.setIcon(new ImageIcon(imagen_escalada));
            imagen_fondo.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
            add(imagen_fondo);
        } else {
            System.err.println("Error al cargar la imagen de fondo del ranking");
        }
    }

    protected void agregar_boton_volver() {
        boton_volver = new JButton(" ");
        boton_volver = new JButton(" ");
        boton_volver.setOpaque(false);
        boton_volver.setContentAreaFilled(false);
        boton_volver.setBorderPainted(false);
        boton_volver.setBounds(ConstantesVistas.PANEL_ANCHO - 180, 15, 150, 50);
        boton_volver.addActionListener(e -> controlador_vistas.accionar_pantalla_inicial());
        add(boton_volver);
    }
    
    private void volver_con_esc() {
        Action volver_action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                controlador_vistas.accionar_pantalla_inicial();
            }
        };
        
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "volver");
        getActionMap().put("volver", volver_action);
    }
    
    private void cargar_tipografia(String ruta_archivo) {
        try {
            tipografia = Font.createFont(Font.TRUETYPE_FONT, new File(ruta_archivo)).deriveFont(23f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            tipografia = new Font("Arial", Font.PLAIN, 23);
        }
    }
}
