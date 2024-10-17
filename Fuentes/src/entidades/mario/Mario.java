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
    private int direccion;
    private double velocidad_en_x;
    private double velocidad_en_y;
    private double gravedad = 0.38;
    private boolean saltando;
    private boolean cayendo;
    private boolean movimiento_derecha;
    private SpritesFactory sprites_factory; 
    
    private Mario(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        this.sprites_factory = null; // Inicializar la fábrica de sprites
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
    public static Mario get_instancia() {
        if (instancia_mario == null) instancia_mario = new Mario(150, 150, null);
        return instancia_mario;
    }

    // Mario State
    public interface MarioState {
        void consumir(PowerUp powerup);
        boolean matar_si_hay_colision(Enemigo enemigo);
    }

    public void actualizar_posicion() {
        // Si está cayendo, aplicar gravedad
        if (cayendo) {
        	//System.out.println(velocidad_en_y);
            velocidad_en_y += gravedad; // Incrementar la velocidad en Y debido a la gravedad
            posicion_en_y -= velocidad_en_y; // Aplicar la velocidad en Y a la posición
        } 

        if (saltando) {
            velocidad_en_y -= gravedad; // Disminuir la velocidad en Y para simular el salto
            posicion_en_y -= velocidad_en_y; // Aplicar la velocidad en Y a la posición
            
            if (velocidad_en_y <= 0) { // Alcanza el pico del salto
                saltando = false; // Deja de saltar
                cayendo = true; // Comienza a caer
            }
        }

        // Movimiento en X
        posicion_en_x += velocidad_en_x;

        // Cambiar sprites según el movimiento
        if (velocidad_en_x < 0) {
            cambiar_sprite(sprites_factory.get_mario_movimiento_izquierda());
        } else if (velocidad_en_x > 0) {
            cambiar_sprite(sprites_factory.get_mario_movimiento_derecha());
        } else {
            cambiar_sprite(movimiento_derecha ? sprites_factory.get_mario_ocioso_derecha() : sprites_factory.get_mario_ocioso_izquierda());
        }
    }

    
    public void set_direccion_mario(int direccion_mario) {
    	direccion = direccion_mario;
    }
    public void mover() {
    	if (direccion == 0) {
    		detener_movimiento();
    	}
    	if (direccion == 1) {
    		mover_a_derecha();
    	}
    	if (direccion == -1) {
    		mover_a_izquierda();
    	}
    }
    private void mover_a_izquierda() {
        this.velocidad_en_x = -10;
        movimiento_derecha = false;
        actualizar_posicion();
    }

    private void mover_a_derecha() {
        this.velocidad_en_x = 10;
        movimiento_derecha = true;
        actualizar_posicion();
    }

    private void detener_movimiento() {
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
    
    public void set_fabrica_sprites(SpritesFactory sprites) {
    	this.sprites_factory = sprites;
    }

}