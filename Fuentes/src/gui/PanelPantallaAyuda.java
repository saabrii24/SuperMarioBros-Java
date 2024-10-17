package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PanelPantallaAyuda extends JPanel {

    private static final long serialVersionUID = 1L;
    private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;
    private JButton boton_volver;

    public PanelPantallaAyuda(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        setLayout(null);
        agregar_imagen_fondo();
        agregar_boton_volver();
        volver_con_esc();
    }

    protected void agregar_imagen_fondo() {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-ayuda.png"));
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo.setIcon(icono_imagen_escalado);
        setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        imagen_fondo.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        add(imagen_fondo);
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
    
    private void volver_con_esc() {
        // Acción para volver a la pantalla inicial
        Action volverAction = new AbstractAction() {
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
