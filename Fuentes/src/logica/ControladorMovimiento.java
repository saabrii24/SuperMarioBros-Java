package logica;

public class ControladorMovimiento {
    private final Juego juego;
    private Thread hilo_mario_movimiento;
    private Thread hilo_enemigos_movimiento;
    private volatile boolean esta_ejecutando;

    public ControladorMovimiento(Juego juego) {
        this.juego = juego;
    }

    public synchronized void iniciar_hilos() {
        if (esta_ejecutando) return;
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
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
