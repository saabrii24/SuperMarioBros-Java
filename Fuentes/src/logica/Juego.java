package logica;

import entidades.BolaDeFuego;
import entidades.Entidad;
import entidades.enemigos.Enemigo;
import entidades.mario.Mario;
import fabricas.*;
import gui.ControladorDeVistas;
import niveles.GeneradorNivel;
import niveles.Nivel;
import java.util.List;

public class Juego {

    public static final int SALTAR = 15000;
    public static final int IZQUIERDA = 15001;
    public static final int DERECHA = 15003;
    public static final int DISPARAR = 15004;

    protected ControladorDeVistas controlador_vistas;
    protected Nivel nivel_actual;
    protected SpritesFactory fabrica_sprites;
    protected EntidadesFactory fabrica_entidades;
    protected Mapa mapa_nivel_actual;
    protected int tiempo_restante;
    protected int contador_puntos;
    protected int vidas = 3;
    protected boolean esta_ejecutando;
    protected Thread hilo_mario_movimiento;
    protected Thread hilo_enemigos_movimiento;
    protected volatile int direccion_mario;
    protected boolean observer_registrado = false;
    protected Colisionador controlador_colisiones;

    public Juego() {
        iniciar();
    }

    private void iniciar() {
        mapa_nivel_actual = new Mapa(this);
        fabrica_sprites = new Dominio1Factory();
        Mario.get_instancia().set_fabrica_sprites(fabrica_sprites);
        fabrica_entidades = new EntidadesFactory(fabrica_sprites);
        controlador_vistas = new ControladorDeVistas(this);
        controlador_colisiones = new Colisionador(mapa_nivel_actual);
        iniciar_hilos_movimiento();
    }

    private synchronized void iniciar_hilos_movimiento() {
        if (esta_ejecutando) {
            return;
        }
        esta_ejecutando = true;

        iniciar_hilo_movimiento_mario();
        iniciar_hilo_movimiento_enemigos();
    }

    private void iniciar_hilo_movimiento_mario() {
        hilo_mario_movimiento = new Thread(this::bucle_movimiento_jugador);
        hilo_mario_movimiento.start();
    }

    private void iniciar_hilo_movimiento_enemigos() {
        hilo_enemigos_movimiento = new Thread(this::bucle_movimiento_entidades);
        hilo_enemigos_movimiento.start();
    }

    private void bucle_movimiento_jugador() {
        long tiempo_anterior = System.nanoTime();
        final double tiempo_por_frame = 1_000_000_000.0 / 60; // 60 FPS

        while (esta_ejecutando) {
            long tiempo_actual = System.nanoTime();
            double delta = (tiempo_actual - tiempo_anterior) / tiempo_por_frame;
            tiempo_anterior = tiempo_actual;

            mover_mario();
            
            notificar_observadores_mario();
            controlador_vistas.get_pantalla_mapa().repaint();

            controlar_fps(tiempo_actual, tiempo_por_frame);
        }
    }

    private void bucle_movimiento_entidades() {
        long tiempo_anterior = System.nanoTime();
        final double tiempo_por_frame = 1_000_000_000.0 / 60; // 60 FPS

        while (esta_ejecutando) {
            long tiempo_actual = System.nanoTime();
            double delta = (tiempo_actual - tiempo_anterior) / tiempo_por_frame;
            tiempo_anterior = tiempo_actual;

            mover_enemigos();
            mover_proyectiles();
            notificar_observadores_entidades(mapa_nivel_actual.get_entidades_enemigo());
            controlador_vistas.get_pantalla_mapa().repaint();

            controlar_fps(tiempo_actual, tiempo_por_frame);
        }
    }

    private void controlar_fps(long tiempo_actual, double tiempo_por_frame) {
        long tiempo_restante = (long) tiempo_por_frame - (System.nanoTime() - tiempo_actual);
        if (tiempo_restante > 0) {
            try {
                Thread.sleep(tiempo_restante / 1_000_000, (int) (tiempo_restante % 1_000_000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void mover_mario() {
        Mario.get_instancia().mover();
        if(Mario.get_instancia().get_dimension() != null) { 
        	controlador_colisiones.verificar_colision_mario(Mario.get_instancia()); 
        }
    }

    private void mover_enemigos() {
        for (Enemigo enemigo : mapa_nivel_actual.get_entidades_enemigo()) {
            enemigo.mover();
            controlador_colisiones.verificar_colision_enemigo(enemigo);
        }
    }
    
    private void mover_proyectiles() {
        for (BolaDeFuego proyectil : mapa_nivel_actual.get_entidades_proyectiles()) {
            proyectil.mover();
            controlador_colisiones.verificar_colision_proyectil(proyectil); 
        }
    }

    public void notificar_observadores() {
        notificar_observadores_mario();
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_enemigo());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_powerup());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_proyectiles());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_plataforma());
    }

    private void notificar_observadores_mario() {
        Mario mario = Mario.get_instancia();
        if (mario != null) {
            mario.notificar_observer();
        }
    }

    private void notificar_observadores_entidades(List<? extends Entidad> entidades) {
        for (Entidad entidad : entidades) {
            entidad.notificar_observer();
        }
    }

    public void cargar_datos(EntidadesFactory generador) {
        nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
            getClass().getResourceAsStream("/niveles/nivel-1.txt"),
            generador,
            mapa_nivel_actual
        );
        fabrica_entidades = generador;
        registrar_observers(); 
    }

    private void registrar_observers() {
        registrar_observer_jugador(Mario.get_instancia());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_enemigo());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_proyectiles());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_powerup());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_plataforma());
    }

    protected void registrar_observer_jugador(Mario jugador_mario) {
        Observer observer_jugador = controlador_vistas.registrar_entidad(jugador_mario);
        jugador_mario.registrar_observer(observer_jugador);
        observer_registrado = true;
    }

    protected void registrar_observers_para_entidades(List<? extends Entidad> entidades) {
        for (Entidad entidad : entidades) {
            Observer observer = controlador_vistas.registrar_entidad(entidad);
            entidad.registrar_observer(observer);
        }
    }

    public Nivel get_nivel_actual() {
        return nivel_actual;
    }

    public Mapa get_mapa_nivel_actual() {
        return mapa_nivel_actual;
    }

    public void reproducir_efecto(String efecto) {
        controlador_vistas.reproducir_efecto(efecto);
    }

    public static void main(String[] args) {
        new Juego();
    }
}
