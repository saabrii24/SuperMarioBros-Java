package logica;

import entidades.Entidad;
import entidades.mario.Mario;
import fabricas.*;
import gui.ControladorDeVistas;
import niveles.GeneradorNivel;
import niveles.Nivel;
import java.util.List;

public class Juego implements Runnable {

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

    public Juego() {
        iniciar();
    }

    private void iniciar() {
        mapa_nivel_actual = new Mapa(this);
        fabrica_sprites = new Dominio1Factory();
        fabrica_entidades = new EntidadesFactory(fabrica_sprites);
        
        // Inicializa el controlador de vistas pasando el objeto Juego actual
        controlador_vistas = new ControladorDeVistas(this);
        
        // Mostrar la pantalla inicial a través del controlador de vistas
        controlador_vistas.mostrar_pantalla_inicial();

        if (estaEjecutando) return;
        estaEjecutando = true;
        hilo = new Thread(this);
        hilo.start();
    }

    public void cargar_datos(EntidadesFactory generador) {
        nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
                getClass().getResourceAsStream("/niveles/nivel-1.txt"),
                generador,
                mapa_nivel_actual
        );
        fabrica_entidades = generador;
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
        // Aquí puedes llamar a un método del controlador para actualizar la vista, si es necesario
    }

    public static void main(String[] args) {
        new Juego();
    }
}