package logica;

import java.awt.EventQueue;

import entidades.mario.Mario;
import niveles.Nivel;

public class ControladorMovimiento {
    private final Juego juego;
    private Thread hilo_mario_movimiento;
    private Thread hilo_enemigos_movimiento;
    private Thread hilo_informacion_y_tiempo;
    private volatile boolean esta_ejecutando;

    public ControladorMovimiento(Juego juego) {
        this.juego = juego;
    }

    public synchronized void iniciar_hilos() {
        if (esta_ejecutando) return;
        esta_ejecutando = true;

        iniciar_hilo_movimiento_mario();
        iniciar_hilo_movimiento_enemigos();
        iniciar_hilo_informacion_y_tiempo();
    }

    private void iniciar_hilo_movimiento_mario() {
        hilo_mario_movimiento = new Thread(this::bucle_movimiento_jugador);
        hilo_mario_movimiento.start();
    }

    private void iniciar_hilo_movimiento_enemigos() {
        hilo_enemigos_movimiento = new Thread(this::bucle_movimiento_entidades);
        hilo_enemigos_movimiento.start();
    }
    
    private void iniciar_hilo_informacion_y_tiempo() {
        hilo_informacion_y_tiempo = new Thread(this::bucle_informacion_y_tiempo);
        hilo_informacion_y_tiempo.start();
    }

    private void bucle_movimiento_jugador() {
        final double tiempo_por_frame = 1_000_000_000.0 / 60;

        while (esta_ejecutando) {
            long tiempo_actual = System.nanoTime();
            
            juego.controlador_entidades.mover_mario();
            juego.controlador_entidades.notificar_observadores_mario();
            juego.controlador_vistas.get_pantalla_mapa().repaint();

            controlar_fps(tiempo_actual, tiempo_por_frame);
        }
    }

    private void bucle_movimiento_entidades() {
        final double tiempo_por_frame = 1_000_000_000.0 / 60;

        while (esta_ejecutando) {
            long tiempo_actual = System.nanoTime();

            juego.controlador_entidades.mover_enemigos();
            juego.controlador_entidades.mover_proyectiles();
            juego.controlador_entidades.notificar_observadores_enemigos();
            juego.controlador_vistas.get_pantalla_mapa().repaint();

            controlar_fps(tiempo_actual, tiempo_por_frame);
        }
    }

    private void bucle_informacion_y_tiempo() {
        long ultimo_segundo = System.currentTimeMillis();
        
        while (esta_ejecutando) {
            try {
                Nivel nivel_actual = juego.get_nivel_actual();
                Mario mario = Mario.get_instancia();
                
                long tiempo_actual = System.currentTimeMillis();
                if (tiempo_actual - ultimo_segundo >= 1000) {
                    if (nivel_actual != null) {
                        ultimo_segundo = tiempo_actual;
                        boolean tiempo_vigente = nivel_actual.decrementar_tiempo();
                        if (!tiempo_vigente) {
                            reiniciar_nivel_y_timer();
                            continue; 
                        }
                    }
                }

                // Actualizar la interfaz con toda la información
                EventQueue.invokeLater(() -> {
                    juego.get_controlador_vistas().get_pantalla_mapa()
                        .actualizar_labels_informacion(mario, nivel_actual);
                });

                Thread.sleep(100); // Actualización cada 100ms
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void reiniciar_nivel_y_timer() {
        EventQueue.invokeLater(() -> {
        	Mario.get_instancia().get_sistema_vidas().quitar_vida();
            juego.get_controlador_nivel().reiniciar_nivel();
            if (juego.get_nivel_actual() != null) {
                juego.get_nivel_actual().set_tiempo_restante(
                    juego.get_nivel_actual().get_tiempo_inicial()
                );
            }
        });
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

    public void detener_hilos() {
        esta_ejecutando = false;
        
        try {
            if (hilo_mario_movimiento != null) {
                hilo_mario_movimiento.join();
            }
            if (hilo_enemigos_movimiento != null) {
                hilo_enemigos_movimiento.join();
            }
            if (hilo_informacion_y_tiempo != null) {
                hilo_informacion_y_tiempo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}