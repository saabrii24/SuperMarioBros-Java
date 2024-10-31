package entidades.enemigos;

import logica.Juego;
import logica.ResultadoColision;

public class ProjectileKoopaState implements KoopaTroopa.KoopaState {
    private final KoopaTroopa koopa;

    public ProjectileKoopaState(KoopaTroopa koopa) { this.koopa = koopa; }

    @Override
    public ResultadoColision cambiar_estado() {
    	return ResultadoColision.MARIO_MUERE;
    }

    @Override
    public void actualizar_sprite() {
        koopa.cambiar_sprite(Juego.get_instancia().get_fabrica_sprites().get_koopa_proyectil());
    }

    @Override
    public void mover() {
        if (koopa.get_direccion() == 1) mover_derecha();
        else if (koopa.get_direccion() == -1) mover_izquierda();
        else koopa.detener_movimiento();
    }

    private void mover_izquierda() { koopa.set_velocidad_en_x(-10); koopa.set_movimiento_derecha(false); }

    private void mover_derecha() { koopa.set_velocidad_en_x(10); koopa.set_movimiento_derecha(true); }

    @Override
    public boolean en_movimiento() { return true; }

    @Override
    public boolean mata_tocando() { return true; }
}