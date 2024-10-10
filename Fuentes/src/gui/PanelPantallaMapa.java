package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logica.*;

/**
 * La clase {@code PanelPantallaMapa} representa el panel que muestra el mapa del juego,
 * incluyendo información sobre el puntaje, monedas, nivel, tiempo y vidas del jugador.
 */
public class PanelPantallaMapa extends JPanel {

    private static final long serialVersionUID = -1366756178696496543L;
    private JPanel panel_mapa;
    private JPanel panel_informacion;
    private JLabel imagen_fondo_panel_mapa;
    private JLabel imagen_fondo_panel_informacion;
    private JLabel label_puntaje;
    private JLabel label_monedas;
    private JLabel label_nivel;
    private JLabel label_tiempo; // Nueva etiqueta para mostrar el tiempo
    private JLabel label_vidas;  // Nueva etiqueta para mostrar las vidas

    /**
     * Constructor que inicializa el panel de pantalla del mapa.
     * Configura el panel y agrega los subpaneles de información y mapa.
     */
    public PanelPantallaMapa() {
        setPreferredSize(new Dimension(ConstantesVistas.PANEL_ANCHO, ConstantesVistas.PANEL_ALTO));
        setLayout(new BorderLayout());
        agregar_panel_informacion();
        agregar_panel_mapa_con_fondo();
    }

    // Operaciones para ControladorVistas

    /**
     * Incorpora una entidad lógica en el panel del mapa.
     *
     * @param entidad_logica La entidad lógica a incorporar.
     * @return Un {@code Observer} que representa la entidad incorporada.
     */
    public Observer incorporar_entidad(EntidadLogica entidad_logica) {
        ObserverEntidades observer_entidad = new ObserverEntidades(entidad_logica);
        imagen_fondo_panel_mapa.add(observer_entidad);
        return observer_entidad;
    }

    /**
     * Incorpora al jugador en el panel del mapa y actualiza su información.
     *
     * @param entidad_jugador La entidad del jugador a incorporar.
     * @return Un {@code Observer} que representa al jugador incorporado.
     */
    public Observer incorporar_entidad_jugador(EntidadJugador entidad_jugador) {
        ObserverJugador observer_jugador = new ObserverJugador(this, entidad_jugador);
        imagen_fondo_panel_mapa.add(observer_jugador);
        actualizar_info_jugador(entidad_jugador);
        return observer_jugador;
    }

    /**
     * Actualiza la información del jugador en el panel.
     *
     * @param jugador La entidad del jugador cuya información se actualizará.
     */
    protected void actualizar_info_jugador(EntidadJugador jugador) {
        actualizar_labels_informacion(jugador);
        actualizar_scroll_hacia_jugador(jugador);
    }

    /**
     * Actualiza las etiquetas de información que muestran el puntaje, monedas, nivel, tiempo y vidas.
     *
     * @param jugador La entidad del jugador que contiene la información a mostrar.
     */
    protected void actualizar_labels_informacion(EntidadJugador jugador) {
        label_puntaje.setText(texto_con_cantidad_digitos(jugador.get_puntaje(), 5));
        label_monedas.setText(texto_con_cantidad_digitos(jugador.get_monedas(), 5));
        label_nivel.setText(texto_con_cantidad_digitos(jugador.get_nivel(), 5));
        label_tiempo.setText(texto_con_cantidad_digitos(jugador.get_tiempo(), 5)); // Actualiza el tiempo
        label_vidas.setText(texto_con_cantidad_digitos(jugador.get_monedas(), 5));   // Actualiza las vidas
    }

    /**
     * Formatea un número para que tenga un número específico de dígitos, añadiendo ceros a la izquierda si es necesario.
     *
     * @param numero El número a formatear.
     * @param digitos La cantidad de dígitos que debe tener el número.
     * @return El número formateado como {@code String}.
     */
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

    /**
     * Verifica si un número se encuentra dentro de un rango específico.
     *
     * @param numero El número a verificar.
     * @param piso El límite inferior del rango.
     * @param techo El límite superior del rango.
     * @return {@code true} si el número está dentro del rango, {@code false} en caso contrario.
     */
    protected boolean en_rango(int numero, int piso, int techo) {
        return numero >= piso && numero <= techo;
    }

    // Operación para observer de jugador

    /**
     * Actualiza el scroll del panel hacia la posición del jugador.
     *
     * @param jugador La entidad del jugador cuya posición se utilizará para actualizar el scroll.
     */
    protected void actualizar_scroll_hacia_jugador(EntidadJugador jugador) {
        // To DO
        //panel_scroll_carrera.getVerticalScrollBar().setValue(panel_scroll_carrera.getVerticalScrollBar().getValue() + jugador.get_monedas());
    }

    // Operaciones propias para construcción de PanelPantallaCarrera

    /**
     * Agrega el panel del mapa con un fondo.
     */
    protected void agregar_panel_mapa_con_fondo() {
        imagen_fondo_panel_mapa = new JLabel();
        imagen_fondo_panel_mapa.setLayout(null);
        imagen_fondo_panel_mapa.setBounds(0, 0, ConstantesVistas.PANEL_MAPA_ANCHO, ConstantesVistas.PANEL_ALTO);

        panel_mapa = new JPanel(null);
        panel_mapa.setPreferredSize(new Dimension(ConstantesVistas.PANEL_MAPA_ANCHO, ConstantesVistas.PANEL_ALTO));
        panel_mapa.add(imagen_fondo_panel_mapa);
    }

    /**
     * Agrega el panel de información en el lado derecho de la pantalla.
     */
    protected void agregar_panel_informacion() {
        panel_informacion = new JPanel();
        panel_informacion.setLayout(null);
        panel_informacion.setPreferredSize(new Dimension(ConstantesVistas.PANEL_INFORMACION_ANCHO, ConstantesVistas.PANEL_ALTO));
        agregar_imagen_fondo_panel_informacion();
        agregar_labels_editables_informacion();
        add(panel_informacion, BorderLayout.EAST);
    }

    /**
     * Agrega una imagen de fondo al panel de información.
     */
    protected void agregar_imagen_fondo_panel_informacion() {
        imagen_fondo_panel_informacion = new JLabel();
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/imagenes/pantalla-informacion.png"));
        Image imagen_escalada = icono_imagen.getImage().getScaledInstance(ConstantesVistas.PANEL_INFORMACION_ANCHO, ConstantesVistas.PANEL_ALTO, Image.SCALE_SMOOTH);
        Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
        imagen_fondo_panel_informacion.setIcon(icono_imagen_escalado);
        imagen_fondo_panel_informacion.setBounds(0, 0, ConstantesVistas.PANEL_INFORMACION_ANCHO, ConstantesVistas.PANEL_ALTO);
        panel_informacion.add(imagen_fondo_panel_informacion);
    }

    /**
     * Agrega las etiquetas editables para mostrar la información del juego.
     */
    protected void agregar_labels_editables_informacion() {
        label_puntaje = new JLabel("00000");
        label_monedas = new JLabel("00000");
        label_nivel = new JLabel("00000");
        label_tiempo = new JLabel("00:00"); // Nueva etiqueta para tiempo
        label_vidas = new JLabel("00");     // Nueva etiqueta para vidas

        decorar_labels_informacion();
        imagen_fondo_panel_informacion.add(label_puntaje);
        imagen_fondo_panel_informacion.add(label_monedas);
        imagen_fondo_panel_informacion.add(label_nivel);
        imagen_fondo_panel_informacion.add(label_tiempo);
        imagen_fondo_panel_informacion.add(label_vidas);  
    }

    /**
     * Decora las etiquetas de información para la visualización en la pantalla.
     */
    protected void decorar_labels_informacion() {
        label_puntaje.setBounds(49, 120, 150, 50);
        label_monedas.setBounds(54, 290, 150, 50);
        label_nivel.setBounds(52, 450, 150, 50);
        label_tiempo.setBounds(52, 370, 150, 50); 
        label_vidas.setBounds(52, 520, 150, 50); 

        label_puntaje.setForeground(Color.WHITE);
        label_monedas.setForeground(Color.WHITE);
        label_nivel.setForeground(Color.WHITE);
        label_tiempo.setForeground(Color.WHITE);
        label_vidas.setForeground(Color.WHITE);   

        label_puntaje.setFont(new Font(label_puntaje.getFont().getName(), Font.BOLD, 24));
        label_monedas.setFont(new Font(label_puntaje.getFont().getName(), Font.BOLD, 24));
        label_nivel.setFont(new Font(label_puntaje.getFont().getName(), Font.BOLD, 24));
        label_tiempo.setFont(new Font(label_puntaje.getFont().getName(), Font.BOLD, 24));
        label_vidas.setFont(new Font(label_puntaje.getFont().getName(), Font.BOLD, 24));  
    }
}
