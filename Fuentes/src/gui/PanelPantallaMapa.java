package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.File;
import javax.swing.*;

import entidades.interfaces.EntidadJugador;
import entidades.interfaces.EntidadLogica;
import logica.*;
import niveles.Nivel;

/**
 * La clase {@code PanelPantallaMapa} representa el panel que muestra el mapa del juego,
 * incluyendo información sobre el puntaje, monedas, nivel, tiempo y vidas del jugador.
 */
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
    private JScrollPane scroll_pane;

    public PanelPantallaMapa() {
        setLayout(new BorderLayout());
        JLayeredPane layered_pane = new JLayeredPane();
        layered_pane.setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        cargar_tipografia("src/assets/tipografia/mario-font.ttf");
        panel_informacion = new JPanel();
        panel_mapa = new JPanel();

        agregar_panel_mapa_con_fondo(panel_mapa);
        agregar_scroll_al_mapa(layered_pane, panel_mapa);
        agregar_panel_informacion(layered_pane, panel_informacion);
        
        add(layered_pane, BorderLayout.CENTER);
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
        label_puntaje.setText("  Puntos: " + texto_con_cantidad_digitos(jugador.get_puntaje(), 4) + "  ");
        label_monedas.setText("Monedas: " + texto_con_cantidad_digitos(jugador.get_monedas(), 3) + "  ");
        label_nivel.setText("Nivel: " + nivel.get_numero_de_nivel() + "-3   ");
        label_tiempo.setText("Tiempo: " + texto_con_cantidad_digitos(nivel.get_tiempo_restante(), 3) + "  ");
        label_vidas.setText("Vidas: " + jugador.get_vidas());
        
        panel_informacion.revalidate();
        panel_informacion.repaint();
    }


    protected String texto_con_cantidad_digitos(int numero, int digitos) {
        String formato = "%0" + digitos + "d";
        String texto_autocompletado = String.format(formato, numero);

        if (texto_autocompletado.length() > digitos) {
            texto_autocompletado = texto_autocompletado.substring(0, digitos);
        }

        return texto_autocompletado;
    }

    public void actualizar_scroll_hacia_jugador(EntidadJugador jugador) {
        if (scroll_pane != null) {
            JScrollBar vertical_bar = scroll_pane.getVerticalScrollBar();
            JScrollBar horizontal_bar = scroll_pane.getHorizontalScrollBar();

            int nueva_posicion_x = (int) (jugador.get_posicion_en_x() - (scroll_pane.getViewport().getWidth() / 2));
            int nueva_posicion_y = (int) (jugador.get_posicion_en_y() - (scroll_pane.getViewport().getHeight() / 2));

            horizontal_bar.setValue(Math.max(0, nueva_posicion_x));
            vertical_bar.setValue(Math.max(0, nueva_posicion_y));
        }
    }

    protected void agregar_panel_mapa_con_fondo(JPanel panel) {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/mario-pantalla-mapa.png"));
        imagen_fondo.setIcon(icono_imagen);
        panel.removeAll(); 
        panel.add(imagen_fondo);
        panel.revalidate();
        panel.repaint();
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
}
