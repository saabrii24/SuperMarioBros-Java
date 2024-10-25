package gui;

import javax.swing.*;

import ranking.Ranking;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PanelPantallaRanking extends JPanel {

    private static final long serialVersionUID = 1L;
    private ControladorDeVistas controlador_vistas;
    private JLabel imagen_fondo;
    private JButton boton_volver;
    private Ranking ranking;

    public PanelPantallaRanking(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
        setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        setLayout(null);
        mostrar_ranking();
        agregar_imagen_fondo();
        agregar_boton_volver();
        volver_con_esc();
    }

    private void mostrar_ranking() {
    	ranking = new Ranking();
    	
    	// Recuperar ranking
    	try {
    		FileInputStream file_input_stream = new FileInputStream("./ranking.tdp");
    		ObjectInputStream object_input_stream = new ObjectInputStream(file_input_stream);
			ranking = (Ranking) object_input_stream.readObject();
			object_input_stream.close(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      
    	// Jugador 1
    	if(ranking.get_ranking().size()>0) {
	    	JTextField Nombre1 = new JTextField(ranking.get_ranking().get(0).get_nombre());
	        Nombre1.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Nombre1.setColumns(10);
	        Nombre1.setBackground(new Color(236, 77, 0));
	        Nombre1.setForeground(new Color(255, 255, 255));
	        Nombre1.setBounds(438, 118, 252, 38);
	        add(Nombre1);
	        
	        JTextField Puntos1 = new JTextField(ranking.get_ranking().get(0).get_puntos().toString());
	        Puntos1.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Puntos1.setBackground(new Color(236, 77, 0));
	        Puntos1.setForeground(new Color(255, 255, 255));
	        Puntos1.setBounds(743, 118, 142, 38);
	        add(Puntos1);
	        Puntos1.setColumns(10);
    	}
        
        // Jugador 2
    	if(ranking.get_ranking().size()>1) {
	        JTextField Nombre2 = new JTextField(ranking.get_ranking().get(1).get_nombre());
	        Nombre2.setForeground(Color.WHITE);
	        Nombre2.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Nombre2.setColumns(10);
	        Nombre2.setBackground(new Color(236, 77, 0));
	        Nombre2.setBounds(438, 164, 252, 38);
	        add(Nombre2);
	        
	        JTextField Puntos2 = new JTextField(ranking.get_ranking().get(1).get_puntos().toString());
	        Puntos2.setForeground(Color.WHITE);
	        Puntos2.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Puntos2.setColumns(10);
	        Puntos2.setBackground(new Color(236, 77, 0));
	        Puntos2.setBounds(743, 164, 142, 38);
	        add(Puntos2);
    	}
        
        // Jugador 3
    	if(ranking.get_ranking().size()>2) {
	        JTextField Nombre3 = new JTextField(ranking.get_ranking().get(2).get_nombre());
	        Nombre3.setForeground(Color.WHITE);
	        Nombre3.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Nombre3.setColumns(10);
	        Nombre3.setBackground(new Color(236, 77, 0));
	        Nombre3.setBounds(438, 210, 252, 38);
	        add(Nombre3);
	        
	        JTextField Puntos3 = new JTextField(ranking.get_ranking().get(2).get_puntos().toString());
	        Puntos3.setForeground(Color.WHITE);
	        Puntos3.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Puntos3.setColumns(10);
	        Puntos3.setBackground(new Color(236, 77, 0));
	        Puntos3.setBounds(743, 210, 142, 38);
	        add(Puntos3);
    	}
	        
        // Jugador 4
    	if(ranking.get_ranking().size()>3) {
	        JTextField Nombre4 = new JTextField(ranking.get_ranking().get(3).get_nombre());
	        Nombre4.setForeground(Color.WHITE);
	        Nombre4.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Nombre4.setColumns(10);
	        Nombre4.setBackground(new Color(236, 77, 0));
	        Nombre4.setBounds(438, 256, 252, 38);
	        add(Nombre4);
	        
	        JTextField Puntos4 = new JTextField(ranking.get_ranking().get(3).get_puntos().toString());
	        Puntos4.setForeground(Color.WHITE);
	        Puntos4.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Puntos4.setColumns(10);
	        Puntos4.setBackground(new Color(236, 77, 0));
	        Puntos4.setBounds(743, 256, 142, 38);
	        add(Puntos4);
    	}
        
        // Jugador 5
    	if(ranking.get_ranking().size()>4) {
	        JTextField Nombre5 = new JTextField(ranking.get_ranking().get(4).get_nombre());
	        Nombre5.setForeground(Color.WHITE);
	        Nombre5.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Nombre5.setColumns(10);
	        Nombre5.setBackground(new Color(236, 77, 0));
	        Nombre5.setBounds(438, 302, 252, 38);
	        add(Nombre5);
	        
	        JTextField Puntos5 = new JTextField(ranking.get_ranking().get(4).get_puntos().toString());
	        Puntos5.setForeground(Color.WHITE);
	        Puntos5.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
	        Puntos5.setColumns(10);
	        Puntos5.setBackground(new Color(236, 77, 0));
	        Puntos5.setBounds(743, 302, 142, 38);
	        add(Puntos5);
    	}
	}

	protected void agregar_imagen_fondo() {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-ranking.png")); // Reemplaza con tu imagen
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo.setIcon(icono_imagen_escalado);
        imagen_fondo.setBounds(0, 0, 1280, 720);
        add(imagen_fondo);
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
        // Acción para volver a la pantalla inicial
        Action volver_action = new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                controlador_vistas.accionar_pantalla_inicial();
            }
        };
        
        // Asigna la acción a la tecla 'Esc'
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "volver");
        getActionMap().put("volver", volver_action);
    }
}
