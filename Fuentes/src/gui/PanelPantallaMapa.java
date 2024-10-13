package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import entidades.Entidad;
import logica.*;

/**
 * La clase {@code PanelPantallaMapa} representa el panel que muestra el mapa del juego,
 * incluyendo información sobre el puntaje, monedas, nivel, tiempo y vidas del jugador.
 */
public class PanelPantallaMapa extends JPanel {

    private static final long serialVersionUID = -1366756178696496543L;
    private JLabel imagen_fondo;
    private JPanel panel_informacion;
    private JPanel panel_mapa;
    private JLabel label_puntaje;
    private JLabel label_monedas;
    private JLabel label_nivel;
    private JLabel label_tiempo; 
    private JLabel label_vidas;  
    private Font tipografia;

    /**
     * Constructor que inicializa el panel de pantalla del mapa.
     * Configura el panel y agrega los subpaneles de información y mapa.
     */
    public PanelPantallaMapa() {
        setLayout(new BorderLayout()); // Usar OverlayLayout para superponer componentes
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        cargar_tipografia("src/assets/tipografia/mario-font.ttf");
        panel_informacion = new JPanel();
        panel_mapa = new JPanel();
        agregar_panel_mapa_con_fondo(panel_mapa);
        agregar_scroll_al_mapa(layeredPane, panel_mapa);
        agregar_panel_informacion(layeredPane, panel_informacion);
        
        add(layeredPane, BorderLayout.CENTER);
        
    }

	// Operaciones para ControladorVistas

    public Observer incorporar_entidad(EntidadLogica entidad_logica) {
        if (entidad_logica == null || entidad_logica.get_sprite() == null) {
            System.err.println("Error: La entidad o su sprite son nulos.");
            return null; // Salimos si la entidad o el sprite es nulo
        }
        ObserverEntidades observer_entidad = new ObserverEntidades(entidad_logica);
        imagen_fondo.add(observer_entidad);
        return observer_entidad;
    }

    public Observer incorporar_entidad_jugador(EntidadJugador entidad_jugador) {
        ObserverJugador observer_jugador = new ObserverJugador(this, entidad_jugador);
        imagen_fondo.add(observer_jugador); // Cambié de imagen_fondo a mapaPanel
        actualizar_info_jugador(entidad_jugador);
        return observer_jugador;
    }

    protected void actualizar_info_jugador(EntidadJugador jugador) {
        actualizar_labels_informacion(jugador);
        actualizar_scroll_hacia_jugador(jugador);
    }

    protected void actualizar_labels_informacion(EntidadJugador jugador) {
        label_puntaje.setText(texto_con_cantidad_digitos(jugador.get_puntaje(), 5));
        label_monedas.setText(texto_con_cantidad_digitos(jugador.get_monedas(), 5));
        label_nivel.setText(texto_con_cantidad_digitos(jugador.get_nivel(), 5));
        label_tiempo.setText(texto_con_cantidad_digitos(jugador.get_tiempo(), 5)); 
        label_vidas.setText(texto_con_cantidad_digitos(jugador.get_vidas(), 5));
    }

    protected String texto_con_cantidad_digitos(int numero, int digitos) {
        String texto_autocompletado = "";
        if (en_rango(numero, 0, 9)) {
            texto_autocompletado = "0000" + numero;
        } else if (en_rango(numero, 10, 99)) {
            texto_autocompletado = "000" + numero;
        } else if (en_rango(numero, 100, 999)) {
            texto_autocompletado = "00" + numero;
        } else if (en_rango(numero, 1000, 9999)) {
            texto_autocompletado = "0" + numero;
        } else {
            texto_autocompletado += numero;
        }
        return texto_autocompletado;
    }

    protected boolean en_rango(int numero, int piso, int techo) {
        return numero >= piso && numero <= techo;
    }

    protected void actualizar_scroll_hacia_jugador(EntidadJugador jugador) {
        // Implementar lógica para desplazar el JScrollPane hacia el jugador
    }

    protected void agregar_panel_mapa_con_fondo(JPanel panel) {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-mapa.png"));

        // No escalamos la imagen, mantenemos el tamaño original
        imagen_fondo.setIcon(icono_imagen);
        
        panel.add(imagen_fondo);
    }
    
    
    protected void agregar_scroll_al_mapa(JLayeredPane panel_con_capas, JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBounds(0, 0, ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Agregar el JScrollPane al JLayeredPane en una capa inferior
        panel_con_capas.add(scroll, Integer.valueOf(0)); // Capa base para el mapa
        
        configurar_desplazamiento_con_teclado(scroll);
    }
    
    protected void agregar_panel_informacion(JLayeredPane panel_con_capas, JPanel panel) {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false); // Hacer el panel transparente
        agregar_labels_editables_informacion();
        
        // Colocar el panel de información en una posición fija sobre el mapa
        panel.setBounds(10, 10, ConstantesVistas.PANEL_ANCHO, 50); // Ajustar la posición y tamaño del panel
        panel_con_capas.add(panel, Integer.valueOf(1)); // Capa superior para el panel de información
    }

    protected void agregar_labels_editables_informacion() {
        label_puntaje = new JLabel("  Puntos: 00000  ");
        label_monedas = new JLabel("Monedas: 000  ");
        label_nivel = new JLabel("Nivel: 1-3   ");
        label_tiempo = new JLabel("Tiempo: 000  "); 
        label_vidas = new JLabel("Vidas: 0");

        decorar_labels_informacion(); // Aplica estilos a los labels

        // Agregar labels al panel de información en el orden correcto
        panel_informacion.add(label_puntaje);
        panel_informacion.add(label_monedas);
        panel_informacion.add(label_nivel);
        panel_informacion.add(label_tiempo);
        panel_informacion.add(label_vidas);
    }

    protected void decorar_labels_informacion() {
        label_puntaje.setForeground(Color.WHITE);
        label_monedas.setForeground(Color.WHITE);
        label_nivel.setForeground(Color.WHITE);
        label_tiempo.setForeground(Color.WHITE);
        label_vidas.setForeground(Color.WHITE);   
        
        label_puntaje.setFont(tipografia);
        label_monedas.setFont(tipografia);
        label_nivel.setFont(tipografia);
        label_tiempo.setFont(tipografia);
        label_vidas.setFont(tipografia);

    }
    
    private void configurar_desplazamiento_con_teclado(JScrollPane scroll) {
        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moverArriba");
        scroll.getActionMap().put("moverArriba", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollBar verticalBar = scroll.getVerticalScrollBar();
                verticalBar.setValue(verticalBar.getValue() - verticalBar.getUnitIncrement(-1));
            }
        });

        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "moverAbajo");
        scroll.getActionMap().put("moverAbajo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollBar verticalBar = scroll.getVerticalScrollBar();
                verticalBar.setValue(verticalBar.getValue() + verticalBar.getUnitIncrement(1));
            }
        });

        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "moverIzquierda");
        scroll.getActionMap().put("moverIzquierda", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollBar horizontalBar = scroll.getHorizontalScrollBar();
                horizontalBar.setValue(horizontalBar.getValue() - horizontalBar.getUnitIncrement(-1));
            }
        });

        scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "moverDerecha");
        scroll.getActionMap().put("moverDerecha", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollBar horizontalBar = scroll.getHorizontalScrollBar();
                horizontalBar.setValue(horizontalBar.getValue() + horizontalBar.getUnitIncrement(1));
            }
        });
    }
    
    /**
     * Método para cargar la fuente personalizada desde un archivo.
     * @param rutaArchivo La ruta completa al archivo de la fuente TTF.
     */
    private void cargar_tipografia(String ruta_archivo) {
        try {
            tipografia = Font.createFont(Font.TRUETYPE_FONT, new File(ruta_archivo)).deriveFont(18f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar la fuente personalizada, se usará la fuente predeterminada.");
            tipografia = new Font("SansSerif", Font.BOLD, 30); // Fuente alternativa en caso de error
        }
    }

}