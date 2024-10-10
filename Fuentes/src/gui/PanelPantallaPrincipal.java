package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelPantallaPrincipal  extends JPanel {
	
	private static final long serialVersionUID = -3884090044789061650L;
	private ControladorDeVistas controlador_vistas;
	private JLabel imagen_fondo;
	private JButton boton_iniciar;
	private JButton boton_puntajes;

	public PanelPantallaPrincipal(ControladorDeVistas controlador_vistas) {
		this.controlador_vistas = controlador_vistas;
		setSize(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
		setLayout(null);
		agregar_imagen_fondo();
		agregar_boton_iniciar();
		agregar_boton_puntaje();
	}
	
	protected void agregar_imagen_fondo() {
		imagen_fondo = new JLabel();
		ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/imagenes/pantalla-inicial.png"));
		Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
		Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
		imagen_fondo.setIcon(icono_imagen_escalado);
		imagen_fondo.setBounds(0,0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
		add(imagen_fondo);
	}
	
	protected void agregar_boton_iniciar() {
		boton_iniciar = new JButton();
		decorar_boton_iniciar();
		registrar_oyente_boton_iniciar();
		add(boton_iniciar);
	}
	
	protected void agregar_boton_puntaje() {
		boton_puntajes = new JButton();
		decorar_boton_puntajes();
		registrar_oyente_boton_puntajes();
		add(boton_puntajes);
	}
	
	protected void decorar_boton_iniciar() {
		transparentar_boton(boton_iniciar);
		boton_iniciar.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 100 ,ConstantesVistas.PANEL_ALTO - 150, 200 , 50);
	}
	
	protected void decorar_boton_puntajes() {
		transparentar_boton(boton_puntajes);
		boton_puntajes.setBounds((ConstantesVistas.PANEL_ANCHO / 2) - 130 ,ConstantesVistas.PANEL_ALTO - 90, 260 , 50);
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
}
