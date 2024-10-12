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
    protected int vidas = 3;
    protected boolean esta_ejecutando;
    protected Thread hilo;
    protected EntidadesFactory generador;

    public Juego() {
        iniciar();
    }

    private void iniciar() {
        mapa_nivel_actual = new Mapa(this);
        fabrica_sprites = new Dominio1Factory();
        fabrica_entidades = new EntidadesFactory(fabrica_sprites);
        controlador_vistas = new ControladorDeVistas(this);
 
        if (esta_ejecutando) return;
        esta_ejecutando = true;
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
    
    public void set_generador(EntidadesFactory generador) {
    	this.generador = generador;
    }

    @Override
    public void run() {
        long ultimoTiempo = System.nanoTime();
        double cantidadTicks = 60.0;
        double ns = 1000000000 / cantidadTicks;
        double delta = 0;

        while (esta_ejecutando) {
            long ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo) / ns;
            ultimoTiempo = ahora;

            while (delta >= 1) {
                delta--;
            }
        }
    }

    public static void main(String[] args) {
        new Juego();
    }
}