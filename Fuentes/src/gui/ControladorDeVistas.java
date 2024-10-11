package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import logica.EntidadJugador;
import logica.EntidadLogica;
import logica.Juego;
import logica.Observer;

public class ControladorDeVistas implements ControladorEntreJuegoVista, ControladorJuegoVista {
    protected JFrame ventana;
    protected Font tipografia;
    protected PanelPantallaPrincipal panel_pantalla_principal;
    protected PanelPantallaMapa panel_pantalla_mapa;
    protected PanelPantallaAyuda panel_pantalla_ayuda; 

    protected Juego juego;

    public ControladorDeVistas(Juego juego) {
        this.juego = juego;
        panel_pantalla_principal = new PanelPantallaPrincipal(this);
        panel_pantalla_mapa = new PanelPantallaMapa();
        panel_pantalla_ayuda = new PanelPantallaAyuda(this); 
        configurar_ventana();
        mostrar_pantalla_inicial();
        registrar_oyente_ventana();
        try {
            InputStream in = getClass().getResourceAsStream("/assets/tipografia/mario-font.ttf");
            tipografia = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            tipografia = new Font("Verdana", Font.PLAIN, 12);
            e.printStackTrace();
        }
    }

    protected void configurar_ventana() {
        ventana = new JFrame("Super Mario Bros");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setSize(ConstantesVistas.VENTANA_ANCHO, ConstantesVistas.VENTANA_ALTO);
        ventana.setLocationRelativeTo(null);
        Image icono;
		try {
			icono = ImageIO.read(getClass().getResourceAsStream("/assets/imagenes/sprites/dominio1/mario_ocioso_derecha.png"));
			ventana.setIconImage(icono);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        ventana.setVisible(true);

    }

    protected void registrar_oyente_ventana() {
        ventana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                int keyCode = evento.getKeyCode();

                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        panel_pantalla_principal.mover_seleccion(true);
                        break;
                    case KeyEvent.VK_DOWN:
                        panel_pantalla_principal.mover_seleccion(false);
                        break;
                    case KeyEvent.VK_ENTER:
                        panel_pantalla_principal.ejecutar_accion_seleccionada();
                        break;
                }
            }
        });
    }

    public void mostrar_pantalla_inicial() {
        ventana.setContentPane(panel_pantalla_principal);

        refrescar();
    }

    public void accionar_inicio_juego() {
        // juego.cargar_datos(new EntidadesFactory(new Dominio1Factory())); // Cargar los datos
        mostrar_pantalla_mapa();
    }

    public void mostrar_pantalla_mapa() {
       // ventana.getContentPane().removeAll();
        ventana.setContentPane(panel_pantalla_mapa);
        refrescar();
    }

    // Método para mostrar la pantalla de ayuda
    public void accionar_pantalla_ayuda() {
        ventana.setContentPane(panel_pantalla_ayuda);
        refrescar();
    }

    protected void refrescar() {
        ventana.revalidate();
        ventana.repaint();
    }

    @Override
    public void accionar_pantalla_puntajes() {
        // TODO Auto-generated method stub
    }

    @Override
    public void accionar_pantalla_modo_juego() {
        // TODO Auto-generated method stub
    }

    @Override
    public void cambiar_modo_juego(int modo) {
        // TODO Auto-generated method stub
    }

    @Override
    public Observer registrar_entidad(EntidadLogica entidad_logica) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Observer registrar_entidad(EntidadJugador entidad_jugador) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void mostrar_pantalla_fin_juego() {
        // TODO Auto-generated method stub
    }

    // Otros métodos de la interfaz
}
