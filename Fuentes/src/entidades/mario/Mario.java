package entidades.mario;

import entidades.BolaDeFuego;
import entidades.Entidad;
import entidades.enemigos.BuzzyBeetle;
import entidades.enemigos.Enemigo;
import entidades.enemigos.Goomba;
import entidades.enemigos.KoopaTroopa;
import entidades.enemigos.Lakitu;
import entidades.enemigos.PiranhaPlant;
import entidades.enemigos.Spiny;
import entidades.powerups.ChampiVerde;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.Moneda;
import entidades.powerups.SuperChampi;
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
    private MarioState estado_anterior;
    
    private Mario(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        this.sprites_factory = null;
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
        public boolean matar_si_hay_colision(Enemigo enemigo);
        public void consumir(SuperChampi powerup);
        public void consumir(FlorDeFuego powerup);	
        public void consumir(Estrella powerup);
		
    }

    public void actualizar_posicion() {
        // Si está cayendo, aplicar gravedad
        if (cayendo) {
            //velocidad_en_y += gravedad; // Incrementar la velocidad en Y debido a la gravedad
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

        posicion_en_x += velocidad_en_x;

        actualizar_sprite();
    }

    private void actualizar_sprite() {
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
        this.velocidad_en_x = -5;
        movimiento_derecha = false;
        actualizar_posicion();
    }

    private void mover_a_derecha() {
        this.velocidad_en_x = 5;
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

    public BolaDeFuego disparar() {
    	BolaDeFuego bola_de_fuego = new BolaDeFuego(Mario.get_instancia().get_posicion_en_x(), 
        		Mario.get_instancia().get_posicion_en_y(), 
        		sprites_factory.get_bola_de_fuego());
    	return bola_de_fuego;
    }
    
    public void consumir(Moneda moneda) {
    	sumar_moneda();
    	actualizar_puntaje_nivel_actual(5);
    }
    
    public void consumir(ChampiVerde champi) {
    	sumar_vida();
    	actualizar_puntaje_nivel_actual(100);
    }
    
    public void cambiar_estado(MarioState nuevo_estado) {
        this.estado_anterior = this.estado;
        this.estado = nuevo_estado;
    }

    public MarioState get_estado_anterior() {
        return estado_anterior;
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

    public void actualizar_puntaje_acumulado(int puntos) {
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
    
    public void sumar_vida() {
        if (vidas > 0) vidas += 1;
    }

    public int get_puntaje() {
        return puntaje_acumulado + puntaje_nivel_actual;
    }
    
    public int get_direccion() {
        return direccion;
    }
    
    private void cambiar_sprite(Sprite nuevo_sprite) {
        this.sprite = nuevo_sprite;
    }
    
    public void set_fabrica_sprites(SpritesFactory sprites) {
    	this.sprites_factory = sprites;
    }
    
    public void matar_si_hay_colision(Goomba goomba) {
        if (estado.matar_si_hay_colision(goomba)) {
        	actualizar_puntaje_nivel_actual(60); // +60 puntos por destruir un Goomba
        } else {
        	actualizar_puntaje_acumulado(-30); // -30 puntos por morir a manos de un Goomba
        	quitar_vida();
        }
    }

	public void matar_si_hay_colision(KoopaTroopa koopa) {
        if (estado.matar_si_hay_colision(koopa)) {
        	actualizar_puntaje_nivel_actual(90); // +90 puntos por destruir un Koopa Troopa
        } else {
        	actualizar_puntaje_acumulado(-45); // -45 puntos por morir a manos de un Koopa Troopa
        	quitar_vida();
        }
    }

    public void matar_si_hay_colision(PiranhaPlant piranhaPlant) {
        if (estado.matar_si_hay_colision(piranhaPlant)) {
        	actualizar_puntaje_nivel_actual(30); // +30 puntos por destruir una Piranha Plant
        } else {
        	actualizar_puntaje_acumulado(-30); // -30 puntos por morir a manos de una Piranha Plant
        	quitar_vida();
        }
    }

    public void matar_si_hay_colision(Lakitu lakitu) {
        if (estado.matar_si_hay_colision(lakitu)) {
        	actualizar_puntaje_nivel_actual(60); // +60 puntos por destruir un Lakitu
        }
    }

    public void matar_si_hay_colision(Spiny spiny) {
        if (estado.matar_si_hay_colision(spiny)) {
        	actualizar_puntaje_nivel_actual(60); // +60 puntos por destruir un Spiny
        } else {
        	actualizar_puntaje_acumulado(-30); // -30 puntos por morir a manos de un Spiny
        	quitar_vida();
        }
    }

    public void matar_si_hay_colision(BuzzyBeetle buzzyBeetle) {
        if (estado.matar_si_hay_colision(buzzyBeetle)) {
        	actualizar_puntaje_nivel_actual(30); // +30 puntos por destruir un Buzzy Beetle
        } else {
        	actualizar_puntaje_acumulado(-15); // -15 puntos por morir a manos de un Buzzy Beetle
        	quitar_vida();
        }
    }

    public void caer_en_vacio() {
    	actualizar_puntaje_acumulado(-15);
    	quitar_vida();
    }

    public int get_direccion_mario() {
        return movimiento_derecha ? 1 : -1;
    }

}