package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout; // Importa OverlayLayout
import logica.*;

/**
 * La clase {@code PanelPantallaMapa} representa el panel que muestra el mapa del juego,
 * incluyendo información sobre el puntaje, monedas, nivel, tiempo y vidas del jugador.
 */
public class PanelPantallaMapa extends JPanel {

    private static final long serialVersionUID = -1366756178696496543L;
    private JLabel imagen_fondo;
    private JPanel panel_informacion;
    private JLabel label_puntaje;
    private JLabel label_monedas;
    private JLabel label_nivel;
    private JLabel label_tiempo; 
    private JLabel label_vidas;  

    /**
     * Constructor que inicializa el panel de pantalla del mapa.
     * Configura el panel y agrega los subpaneles de información y mapa.
     */
    public PanelPantallaMapa() {
        setLayout(new OverlayLayout(this)); // Usar OverlayLayout para superponer componentes

        // Crear y agregar el panel del mapa
        JPanel mapaPanel = new JPanel();
        agregar_panel_mapa_con_fondo(mapaPanel);

        // Crear un JScrollPane para permitir el desplazamiento
        JScrollPane scrollPane = new JScrollPane(mapaPanel);
        scrollPane.setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Agregar el JScrollPane al panel principal
        add(scrollPane);

        // Crear el panel de información y agregar los labels
        panel_informacion = new JPanel();
        panel_informacion.setLayout(new OverlayLayout(panel_informacion)); // OverlayLayout para superposición
        panel_informacion.setOpaque(false); // Hacerlo transparente para que el mapa se vea detrás
        agregar_labels_editables_informacion(); // Asegúrate de agregar las etiquetas de información

        // Agregar el panel de información al panel principal
        add(panel_informacion);
    }

    // Operaciones para ControladorVistas

    public Observer incorporar_entidad(EntidadLogica entidad_logica) {
        ObserverEntidades observer_entidad = new ObserverEntidades(entidad_logica);
        imagen_fondo.add(observer_entidad); // Cambié de imagen_fondo a mapaPanel
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
        label_tiempo.setText(texto_con_cantidad_digitos(jugador.get_tiempo(), 5)); // Actualiza el tiempo
        label_vidas.setText(texto_con_cantidad_digitos(jugador.get_vidas(), 5));   // Actualiza las vidas
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
        // Aquí puedes implementar lógica para desplazar el JScrollPane hacia el jugador
    }

    protected void agregar_panel_mapa_con_fondo(JPanel mapaPanel) {
        imagen_fondo = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/imagenes/pantalla-mapa.png"));

        // No escalamos la imagen, mantendremos su tamaño original
        imagen_fondo.setIcon(icono_imagen);

        mapaPanel.add(imagen_fondo);
    }

    protected void agregar_labels_editables_informacion() {
        label_puntaje = new JLabel("Puntos: 00000");
        label_monedas = new JLabel("Monedas: 00000");
        label_nivel = new JLabel("Nivel: 00000");
        label_tiempo = new JLabel("Tiempo: 00:00"); // Nueva etiqueta para tiempo
        label_vidas = new JLabel("Vidas: 00");     // Nueva etiqueta para vidas

        decorar_labels_informacion(); // Aplica estilos a los labels

        // Agregar labels al panel de información
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

        label_puntaje.setFont(new Font(label_puntaje.getFont().getName(), Font.BOLD, 24));
        label_monedas.setFont(new Font(label_monedas.getFont().getName(), Font.BOLD, 24));
        label_nivel.setFont(new Font(label_nivel.getFont().getName(), Font.BOLD, 24));
        label_tiempo.setFont(new Font(label_tiempo.getFont().getName(), Font.BOLD, 24));
        label_vidas.setFont(new Font(label_vidas.getFont().getName(), Font.BOLD, 24));  
    }
}