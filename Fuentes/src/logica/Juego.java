package logica;

import entidades.Entidad;
import entidades.mario.Mario;
import fabricas.*;
import gui.ConstantesVistas;
import gui.ControladorDeVistas;
import gui.PanelPantallaPrincipal; // Importa tu clase PanelPantallaPrincipal
import niveles.GeneradorNivel;
import niveles.Nivel;

import javax.swing.*;
import java.util.List;

public class Juego implements Runnable {

    private static final int ANCHO = ConstantesVistas.VENTANA_ANCHO; // Usar la constante
    private static final int ALTO = ConstantesVistas.VENTANA_ALTO; // Usar la constante
    protected ControladorDeVistas controlador_vistas;
    protected Nivel nivel_actual;
    protected SpritesFactory fabrica_sprites;
    protected EntidadesFactory fabrica_entidades;
    protected Mapa mapa_nivel_actual;
    protected int tiempo_restante;
    protected int contador_puntos;
    public int vidas = 3;
    private boolean estaEjecutando;
    private Thread hilo;
    private JFrame ventanaJuego;
    private PanelPantallaPrincipal panelPantallaPrincipal; // Nueva variable para el panel principal

    public Juego() {
        iniciar();
    }

    private void iniciar() {
        mapa_nivel_actual = new Mapa(this);
        fabrica_sprites = new Dominio1Factory();
        fabrica_entidades = new EntidadesFactory(fabrica_sprites);
        //cargar_datos(fabrica_entidades);
        
        // Inicializa el controlador de vistas pasando el objeto Juego actual
        controlador_vistas = new ControladorDeVistas(this);
        mostrar_ventana_juego();
        if (estaEjecutando) return;
        estaEjecutando = true;
        hilo = new Thread(this);
        hilo.start();
    }

    private void mostrar_ventana_juego() {
        ventanaJuego = new JFrame("Super Mario Bros.");
        ventanaJuego.setSize(ANCHO, ALTO);
        ventanaJuego.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventanaJuego.setResizable(false);
        ventanaJuego.setLocationRelativeTo(null);

        // Crear el panel principal del juego y añadirlo al JFrame
        panelPantallaPrincipal = new PanelPantallaPrincipal(controlador_vistas);
        ventanaJuego.add(panelPantallaPrincipal);
        
        ventanaJuego.pack(); // Empaqueta los componentes
        ventanaJuego.setVisible(true); // Finalmente muestra la ventana
        panelPantallaPrincipal.revalidate(); // Asegura que los componentes se actualicen correctamente
        panelPantallaPrincipal.repaint(); // Fuerza un repintado del panel
    }


    public void cargar_datos(EntidadesFactory generador) {
        nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
                getClass().getResourceAsStream("/niveles/nivel-1.txt"),
                generador,
                mapa_nivel_actual
        );
        fabrica_entidades = generador;
    }

    public void set_controlador_vistas(ControladorDeVistas controlador_vistas) {
        this.controlador_vistas = controlador_vistas;
    }

    protected void registrar_observers() {
        registrar_observer_jugador(mapa_nivel_actual.get_mario());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades());
    }

    protected void registrar_observer_jugador(Mario jugador_mario) {
        Observer observer_jugador = controlador_vistas.registrar_entidad(jugador_mario);
        jugador_mario.registrar_observer(observer_jugador);
    }

    protected void registrar_observers_para_entidades(List<? extends Entidad> entidades) {
        for (Entidad entidad : entidades) {
            Observer observer = controlador_vistas.registrar_entidad(entidad);
            entidad.registrar_observer(observer);
        }
    }

    @Override
    public void run() {
        long ultimoTiempo = System.nanoTime();
        double cantidadTicks = 60.0;
        double ns = 1000000000 / cantidadTicks;
        double delta = 0;

        while (estaEjecutando) {
            long ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo) / ns;
            ultimoTiempo = ahora;

            while (delta >= 1) {
                actualizarLogica();
                delta--;
            }
            renderizar();
        }
    }

    private void actualizarLogica() {
        // Actualiza la lógica del juego aquí (por ejemplo, mover entidades, detectar colisiones)
    }

    private void renderizar() {
        // Actualiza la visualización del juego aquí (por ejemplo, repintar el panel)
        ventanaJuego.repaint();
    }

    public static void main(String[] args) {
        new Juego();
    }
}
