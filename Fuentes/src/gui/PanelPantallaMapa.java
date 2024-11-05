package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import entidades.interfaces.EntidadJugador;
import entidades.interfaces.EntidadLogica;
import fabricas.Sprite;
import fabricas.SpritesFactory;
import logica.*;
import niveles.Nivel;

/**
 * La clase {@code PanelPantallaMapa} representa el panel que muestra el mapa del juego,
 * incluyendo información sobre el puntaje, monedas, nivel, tiempo y vidas del jugador.
 */
public class PanelPantallaMapa extends JPanel {

    private static final long serialVersionUID = -1366756178696496543L;
    
    // Componentes principales
    private JLayeredPane layered_pane;
    private JScrollPane scroll_pane;
    private JPanel panel_mapa;
    private JPanel panel_informacion;
    private JLabel imagen_fondo;
    
    // Labels de información
    private JLabel label_puntaje;
    private JLabel label_monedas;
    private JLabel label_nivel;
    private JLabel label_tiempo; 
    private JLabel label_vidas;
    private JLabel label_pausa;
    
    // Recursos
    private Font tipografia;
    private SpritesFactory sprites_factory;

    public PanelPantallaMapa(SpritesFactory sprites_factory) {
        this.sprites_factory = sprites_factory;
        inicializar_componentes();
    }
    
    private void inicializar_componentes() {
        setLayout(new BorderLayout());
        establecer_dimensiones_base();
        cargar_tipografia("src/assets/tipografia/mario-font.ttf");
        
        panel_informacion = new JPanel();
        panel_mapa = new JPanel();
        panel_mapa.setMinimumSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        panel_mapa.setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        

        
        label_pausa = new JLabel();
        try {
            ImageIcon icono_pausa = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/assets/imagenes/pausa.png")));
            label_pausa.setIcon(icono_pausa);
        } catch (IOException e) {
            // Si no encuentra la imagen, usar texto
            label_pausa.setText("PAUSA");
            label_pausa.setFont(new Font("Arial", Font.BOLD, 48));
            label_pausa.setForeground(Color.WHITE);
        }
        label_pausa.setHorizontalAlignment(SwingConstants.CENTER);
        label_pausa.setVisible(false);
        
        actualizar_panel_mapa(layered_pane);
    }
    
    private void establecer_dimensiones_base() {
        setMinimumSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        
        layered_pane = new JLayeredPane();
        layered_pane.setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
    }

    public void actualizar_panel_mapa(JLayeredPane layered_pane) {
        layered_pane.removeAll();
        
        panel_mapa = new JPanel(new BorderLayout());
        panel_mapa.setMinimumSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        panel_mapa.setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        panel_mapa.setOpaque(true);
        
        agregar_panel_mapa_con_fondo(panel_mapa);
        agregar_scroll_al_mapa(layered_pane, panel_mapa);
        
        panel_informacion = new JPanel();
        agregar_panel_informacion(layered_pane, panel_informacion);
        
        layered_pane.setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        
        add(layered_pane, BorderLayout.CENTER);
        
        layered_pane.revalidate();
        layered_pane.repaint();
        
        label_pausa.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
            layered_pane.add(label_pausa, Integer.valueOf(2));
        }

    protected void agregar_panel_mapa_con_fondo(JPanel panel) {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = null;
        
        try {
            if (sprites_factory != null) {
                Sprite mapa_segun_dominio = sprites_factory.get_imagen_mapa();
                icono_imagen = cargar_imagen(mapa_segun_dominio.get_ruta_imagen());
            }            
            configurar_panel_con_imagen(panel, icono_imagen);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        panel.removeAll();
        panel.add(imagen_fondo, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
    
    private ImageIcon cargar_imagen(String ruta) {
        java.net.URL url = getClass().getResource(ruta);
        if (url == null && ruta.startsWith("/")) {
            url = ClassLoader.getSystemResource(ruta.substring(1));
        }
        return url != null ? new ImageIcon(url) : null;
    }
    
    private void configurar_panel_con_imagen(JPanel panel, ImageIcon icono_imagen) {
        if (icono_imagen != null) {
            int imagen_ancho = icono_imagen.getIconWidth();
            int imagen_alto = icono_imagen.getIconHeight();
            int panel_alto = Math.max(imagen_alto, ConstantesVistas.PANEL_ALTO);
            
            panel.setPreferredSize(new Dimension(imagen_ancho, panel_alto));
            imagen_fondo.setIcon(icono_imagen);
        }
        
        panel.setLayout(new BorderLayout());
        imagen_fondo.setHorizontalAlignment(JLabel.LEFT);
        imagen_fondo.setVerticalAlignment(JLabel.TOP);
    }

    // Métodos públicos
    
    public JLayeredPane get_layered_pane() {
        return layered_pane;
    }

    public Observer incorporar_entidad(EntidadLogica entidad_logica) {
        ObserverEntidades observer_entidad = new ObserverEntidades(entidad_logica);
        imagen_fondo.add(observer_entidad);
        return observer_entidad;
    }

    public Observer incorporar_entidad_jugador(EntidadJugador entidad_jugador, Nivel nivel) {
        ObserverJugador observer_jugador = new ObserverJugador(this, entidad_jugador);
        imagen_fondo.add(observer_jugador);
        actualizar_labels_informacion(entidad_jugador, nivel);
        return observer_jugador;
    }

    public void actualizar_labels_informacion(EntidadJugador jugador, Nivel nivel) {
        label_puntaje.setText("  Puntos: " + texto_con_cantidad_digitos(jugador.get_puntaje(), 5) + "  ");
        label_monedas.setText("Monedas: " + texto_con_cantidad_digitos(jugador.get_monedas(), 3) + "  ");
        label_nivel.setText("Nivel: " + nivel.get_numero_de_nivel() + "-3   ");
        label_tiempo.setText("Tiempo: " + texto_con_cantidad_digitos(nivel.get_tiempo_restante(), 3) + "  ");
        label_vidas.setText("Vidas: " + jugador.get_vidas());
        
        panel_informacion.revalidate();
        panel_informacion.repaint();
    }

    public void actualizar_scroll_hacia_jugador(EntidadJugador jugador) {
        if (scroll_pane != null) {
            JScrollBar barra_vertical = scroll_pane.getVerticalScrollBar();
            JScrollBar barra_horizontal = scroll_pane.getHorizontalScrollBar();

            int nueva_posicion_x = (int) (jugador.get_posicion_en_x() - (scroll_pane.getViewport().getWidth() / 2));
            int nueva_posicion_y = (int) (jugador.get_posicion_en_y() - (scroll_pane.getViewport().getHeight() / 2));

            barra_horizontal.setValue(Math.max(0, nueva_posicion_x));
            barra_vertical.setValue(Math.max(0, nueva_posicion_y));
        }
    }

    public void set_dominio(SpritesFactory sprites_factory) {
        this.sprites_factory = sprites_factory;
    }

    // Métodos protegidos de configuración de GUI

    protected String texto_con_cantidad_digitos(int numero, int digitos) {
        String formato = "%0" + digitos + "d";
        String texto_autocompletado = String.format(formato, numero);

        if (texto_autocompletado.length() > digitos) {
            texto_autocompletado = texto_autocompletado.substring(0, digitos);
        }

        return texto_autocompletado;
    }

    protected void agregar_scroll_al_mapa(JLayeredPane layered_pane, JPanel panel) {
        scroll_pane = new JScrollPane(panel);
        scroll_pane.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        layered_pane.add(scroll_pane, Integer.valueOf(0));

        configurar_desplazamiento_con_teclado(scroll_pane);
    }

    protected void agregar_panel_informacion(JLayeredPane layered_pane, JPanel panel) {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        agregar_labels_editables_informacion();
        panel.setBounds(10, 10, ConstantesVistas.PANEL_ANCHO, 50);
        layered_pane.add(panel, Integer.valueOf(1));
    }

    protected void agregar_labels_editables_informacion() {
        label_puntaje = new JLabel("  Puntos: 00000  ");
        label_monedas = new JLabel("Monedas: 000  ");
        label_nivel = new JLabel("Nivel: 0-3   ");
        label_tiempo = new JLabel("Tiempo: 000  "); 
        label_vidas = new JLabel("Vidas: 0");

        decorar_labels_informacion();

        panel_informacion.add(label_puntaje);
        panel_informacion.add(label_monedas);
        panel_informacion.add(label_nivel);
        panel_informacion.add(label_tiempo);
        panel_informacion.add(label_vidas);
    }

    protected void decorar_labels_informacion() {
        Color color_texto = Color.WHITE;
        label_puntaje.setForeground(color_texto);
        label_monedas.setForeground(color_texto);
        label_nivel.setForeground(color_texto);
        label_tiempo.setForeground(color_texto);
        label_vidas.setForeground(color_texto);   
        
        label_puntaje.setFont(tipografia);
        label_monedas.setFont(tipografia);
        label_nivel.setFont(tipografia);
        label_tiempo.setFont(tipografia);
        label_vidas.setFont(tipografia);
    }

    // Métodos privados de configuración

    @SuppressWarnings("serial")
    private void configurar_desplazamiento_con_teclado(JScrollPane scroll) {
        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "mover_arriba");
        scroll.getActionMap().put("mover_arriba", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getValue() - scroll.getVerticalScrollBar().getUnitIncrement(-1));
            }
        });

        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "mover_abajo");
        scroll.getActionMap().put("mover_abajo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getValue() + scroll.getVerticalScrollBar().getUnitIncrement(1));
            }
        });

        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "mover_izquierda");
        scroll.getActionMap().put("mover_izquierda", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getValue() - scroll.getHorizontalScrollBar().getUnitIncrement(-1));
            }
        });

        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "mover_derecha");
        scroll.getActionMap().put("mover_derecha", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getValue() + scroll.getHorizontalScrollBar().getUnitIncrement(1));
            }
        });
    }

    private void cargar_tipografia(String ruta_archivo) {
        try {
            tipografia = Font.createFont(Font.TRUETYPE_FONT, new File(ruta_archivo)).deriveFont(18f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            tipografia = new Font("Arial", Font.PLAIN, 18);
        }
    }
    
    public void mostrar_pausa() {
        label_pausa.setVisible(true);
    }

    public void ocultar_pausa() {
        label_pausa.setVisible(false);
    }

	public Font get_tipografia() {
		return tipografia;
	}

	public JScrollPane get_scroll_pane() {
		return scroll_pane;
	}
}