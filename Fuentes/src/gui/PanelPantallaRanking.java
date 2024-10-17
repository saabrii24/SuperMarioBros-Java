package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PanelPantallaRanking extends JPanel {

    private static final long serialVersionUID = 1L;
    private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;
    private JButton boton_volver;

    public PanelPantallaRanking(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        setLayout(null);
        
        agregar_imagen_fondo();
        mostrar_ranking(); // Llamar a un método que muestra los puntajes
        agregar_boton_volver();
        volver_con_esc();
    }

    protected void agregar_imagen_fondo() {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-ranking.png")); // Reemplaza con tu imagen
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo.setIcon(icono_imagen_escalado);
        imagen_fondo.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        add(imagen_fondo);
    }

    protected void mostrar_ranking() {
        // lógica para mostrar el ranking
        JLabel puntajesLabel = new JLabel("<html>1. Jugador1: 100<br>2. Jugador2: 90<br>3. Jugador3: 80</html>");
        puntajesLabel.setBounds(50, 50, 300, 200); // Ajustar las coordenadas y tamaño según sea necesario
        add(puntajesLabel);
    }

    protected void agregar_boton_volver() {
        boton_volver = new JButton(" ");
        boton_volver = new JButton(" ");
        boton_volver.setOpaque(false);
        boton_volver.setContentAreaFilled(false);
        boton_volver.setBorderPainted(false);
        boton_volver.setBounds(ConstantesVistas.PANEL_ANCHO - 180, 15, 150, 50);
        boton_volver.addActionListener(e -> controlador_vistas.mostrar_pantalla_inicial());
        add(boton_volver);
    }
    
    private void volver_con_esc() {
        // Acción para volver a la pantalla inicial
        Action volverAction = new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                controlador_vistas.mostrar_pantalla_inicial();
            }
        };
        
        // Asigna la acción a la tecla 'Esc'
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "volver");
        getActionMap().put("volver", volverAction);
    }
}
