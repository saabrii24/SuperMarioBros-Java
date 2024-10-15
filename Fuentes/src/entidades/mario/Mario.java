package entidades.mario;

import entidades.Entidad;
import entidades.enemigos.Enemigo;
import entidades.powerups.PowerUp;
import fabricas.Sprite;
import fabricas.SpritesFactory;
import logica.EntidadJugador;

public class Mario extends Entidad implements EntidadJugador {
    private MarioState estado;
    private static Mario instancia_mario;
    private int puntaje_nivel_actual;
    private int puntaje_acumulado;
    private int monedas;
    private int vidas;
    private double velocidad_en_x;
    private double velocidad_en_y;
    private double gravedad = 0.38;
    private boolean saltando;
    private boolean cayendo;
    private boolean movimiento_derecha;
    private SpritesFactory spritesFactory; 
    
    private Mario(int x, int y, Sprite sprite, SpritesFactory factory) {
        super(x, y, sprite);
        this.spritesFactory = factory; // Inicializar la fábrica de sprites
        this.puntaje_acumulado = 0;
        this.monedas = 0;
        this.vidas = 3;
        this.velocidad_en_x = 0;
        this.velocidad_en_y = 0;
        this.saltando = false;
        this.cayendo = true;
        this.movimiento_derecha = true;
    }

    // Mario Singleton
    public static Mario get_instancia(SpritesFactory factory) {
        if (instancia_mario == null) instancia_mario = new Mario(150, 150, factory.get_mario_ocioso_derecha(), factory);
        return instancia_mario;
    }

    // Mario State
    public interface MarioState {
        void consumir(PowerUp powerup);
        boolean matar_si_hay_colision(Enemigo enemigo);
    }

    public void actualizar_posicion() {
        if (saltando && velocidad_en_y <= 0) {
            saltando = false;
            cayendo = true;
        } else if (saltando) {
            velocidad_en_y -= gravedad;
            posicion_en_y -= velocidad_en_y;
        }

        posicion_en_x += velocidad_en_x;
        if (velocidad_en_x < 0) {
            cambiar_sprite(spritesFactory.get_mario_movimiento_izquierda());  
        } else if (velocidad_en_x > 0) {
            cambiar_sprite(spritesFactory.get_mario_movimiento_derecha()); 
        } else {
            if (movimiento_derecha) {  
            	cambiar_sprite(spritesFactory.get_mario_ocioso_derecha()); 
                
            } else {
            	cambiar_sprite(spritesFactory.get_mario_ocioso_izquierda()); 
            }
        }
    }


    public void mover_a_izquierda() {
        this.velocidad_en_x = -5;
        movimiento_derecha = false;
        actualizar_posicion();
    }

    public void mover_a_derecha() {
        this.velocidad_en_x = 5;
        movimiento_derecha = true;
        actualizar_posicion();
    }

    public void detener_movimiento() {
        this.velocidad_en_x = 0; 
        actualizar_posicion();
    }

    public void saltar() {
        if (!saltando && !cayendo) { // Solo salta si no está saltando o cayendo
            saltando = true;
            velocidad_en_y = 10; // Velocidad inicial del salto
        }
    }

    public void disparar() {
        System.out.println("Mario dispara un proyectil.");
        // instanciar un proyectil y añadirlo al mapa ¿?
    }

    public void consumir(PowerUp powerup) {
        estado.consumir(powerup);
    }

    public void cambiar_estado(MarioState nuevo_estado) {
        this.estado = nuevo_estado;
    }

    public boolean matar_si_hay_colision(Enemigo enemigo) {
        return estado.matar_si_hay_colision(enemigo);
    }

    public int get_puntaje_acumulado() {
        return puntaje_acumulado;
    }

    public int get_monedas() {
        return monedas;
    }

    public int get_vidas() {
        return vidas;
    }

    public void set_puntaje_acumulado(int puntaje) {
        this.puntaje_acumulado = puntaje;
    }

    public void set_monedas(int monedas) {
        this.monedas = monedas;
    }

    public void actualizar_puntaje_nivel_actual(int puntos) {
        this.puntaje_nivel_actual = puntos;
    }

    public void sumar_puntaje_acumulado(int puntos) {
        this.puntaje_acumulado += puntos;
    }

    public void sumar_moneda() {
        this.monedas++;
    }

    public void resetear_puntaje_nivel_actual() {
        this.puntaje_nivel_actual = 0;
    }

    public void resetear_monedas() {
        this.monedas = 0;
    }

    public void quitar_vida() {
        if (vidas > 0) vidas -= 1;
    }

    @Override
    public int get_puntaje() {
        return puntaje_acumulado + puntaje_nivel_actual;
    }
    
    private void cambiar_sprite(Sprite nuevo_sprite) {
        this.sprite = nuevo_sprite; // Cambia el sprite actual por el nuevo
    }

}
