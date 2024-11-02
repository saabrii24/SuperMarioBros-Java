package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelPantallaVictoria extends JPanel {

    private static final long serialVersionUID = 1L;
	private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;

    public PanelPantallaVictoria(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        setLayout(null);
        agregar_imagen_fondo();
        configurar_tecla_volver();
    }

    protected void agregar_imagen_fondo() {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-victoria.png"));
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo.setIcon(icono_imagen_escalado);
        setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        imagen_fondo.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        add(imagen_fondo);
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
}
