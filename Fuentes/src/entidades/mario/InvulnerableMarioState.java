package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
import logica.Juego;

public class InvulnerableMarioState implements Mario.MarioState {
    private Mario mario;
    private Mario.MarioState estado_anterior;
    private long tiempo_inicio;
    private static final long DURACION_INVULNERABLE = 1000; // 2 segundos

    public InvulnerableMarioState(Mario mario) {
        this.mario = mario;
        this.estado_anterior = new NormalMarioState(mario);
        this.tiempo_inicio = System.currentTimeMillis();
        mario.set_estado(this);
    }

    public void actualizar_sprite() {
        if (mario.esta_saltando()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_saltando_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_saltando_izquierda());
        } else if (mario.esta_en_movimiento()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_movimiento_derecha() : 
                Juego.get_instancia().get_fabrica_sprites().get_mario_star_movimiento_izquierda());
        } else {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_ocioso_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_ocioso_izquierda());
        }
    }

    public boolean colision_con_enemigo(Enemigo enemigo) {
        // Durante el estado invulnerable, no se daña al enemigo ni recibe daño Mario.
        return false;
    }

    public void finalizar_invulnerabilidad() {
        // Verifica si pasaron los 2 segundos de invulnerabilidad
        if (System.currentTimeMillis() - tiempo_inicio >= DURACION_INVULNERABLE) {
            // Cambia el estado de Mario de nuevo al estado anterior
            mario.cambiar_estado(estado_anterior);
        }
    }

    public boolean rompe_bloque() { return estado_anterior.rompe_bloque(); }
    public boolean mata_tocando() { return false; }
    public BolaDeFuego disparar() { return estado_anterior.disparar(); }

    public void consumir_estrella() { estado_anterior.consumir_estrella(); }
    public void consumir_super_champi() { estado_anterior.consumir_super_champi(); }
    public void consumir_flor_de_fuego() { estado_anterior.consumir_flor_de_fuego(); }
}
