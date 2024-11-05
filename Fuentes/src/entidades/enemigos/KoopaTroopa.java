package entidades.enemigos;

import entidades.interfaces.EnemigoVisitorEnemigo;
import entidades.Entidad;
import entidades.EntidadMovible;
import entidades.interfaces.EnemigosVisitor;
import entidades.plataformas.*;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

/*
 * Consideramos el comportamiento de Koopa Troopa como en el juego original,
 * pero solo suma el puntaje en caso de matarla con una bola de fuego o en estado de estrella,
 * - Se puede derribar saltando 1 vez (Pasa a estado Hidden)
 * - Con el segundo salto pasa a ser proyectil (Pasa a estado Projectile)
 */
public class KoopaTroopa extends Enemigo {
    protected KoopaState estado;

    public KoopaTroopa(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        estado = new NormalKoopaState(this);
    }

    public interface KoopaState {
        void actualizar_sprite();
        void mover();
        boolean cambiar_estado();
        boolean en_movimiento();
        boolean mata_tocando();
        void visitar_enemigo(Enemigo enemigo);
    }
    
    //Puntajes del enemigo
    public int calcular_puntaje() { return 90; }
    public int calcular_penalizacion() { return 45; }

    //Movimientos
    public void mover() { estado.mover(); }
    protected void mover_izquierda() { velocidad_en_x = -3; movimiento_derecha = false; }
    protected void mover_derecha() { velocidad_en_x = 3; movimiento_derecha = true; }
    protected void detener_movimiento() { velocidad_en_x = 0; }

    //Destuir del mapa
    public void destruir(Mapa mapa) {
        if (!destruida) {
    		Juego.get_instancia().get_mapa_nivel_actual().animacion_puntaje_obtenido(
    				(int) this.get_posicion_en_x(), 
    				(int) this.get_posicion_en_y(), 
    				"+"+this.calcular_puntaje()
    				);
            destruida = true;
            mapa.eliminar_enemigo(this);
            eliminar_del_mapa();
        }
    }

    //Actualizaciones
    private void actualizar_sprite() { estado.actualizar_sprite(); }
    public void actualizar() {
        if (cayendo) velocidad_en_y += GRAVEDAD;
        set_posicion_en_y(get_posicion_en_y() - velocidad_en_y);
        set_posicion_en_x(get_posicion_en_x() + velocidad_en_x);
        actualizar_sprite();
    }

    //setters
    public void cambiar_sprite(Sprite sprite) { this.sprite = sprite; }
    public void set_estado(KoopaState estado) { this.estado = estado; }
    public boolean cambiar_estado() { return estado.cambiar_estado(); }

    //getters
    public KoopaState get_estado() { return estado; }
    public boolean get_movimiento_derecha() { return movimiento_derecha; }
    public boolean en_movimiento() { return estado.en_movimiento(); }
    public boolean mata_tocando() { return estado.mata_tocando(); }

    //Colisiones
    
    //Colision entre mario y koopa
    public boolean aceptar(EnemigosVisitor visitante) {return visitante.visitar(this);}
    
    //Colisiones koopa y plataformas
	public void visitar(Vacio vacio) {this.eliminar_del_mapa();}
	public void visitar(BloqueSolido bloque_solido) {ajustar_posicion_enemigo_sobre_plataforma(this,bloque_solido);}
	public void visitar(BloqueDePregunta bloque_de_pregunta) {ajustar_posicion_enemigo_sobre_plataforma(this,bloque_de_pregunta);}
	public void visitar(Tuberia tuberia) {ajustar_posicion_enemigo_sobre_plataforma(this,tuberia);}
	public void visitar(LadrilloSolido ladrillo_solido) {ajustar_posicion_enemigo_sobre_plataforma(this,ladrillo_solido);}
	private void ajustar_posicion_enemigo_sobre_plataforma(EntidadMovible enemigo, Entidad plataforma) {
	        enemigo.set_velocidad_en_y(0);
	        enemigo.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
	 }
	
	//Colision entre enemigos
	public void visitar_enemigo(Goomba goomba) {estado.visitar_enemigo(goomba);}
	public void visitar_enemigo(BuzzyBeetle buzzy) {estado.visitar_enemigo(buzzy);}
	public void visitar_enemigo(KoopaTroopa koopa) {estado.visitar_enemigo(koopa);}
	public void visitar_enemigo(Lakitu lakitu) {estado.visitar_enemigo(lakitu);}
	public void visitar_enemigo(PiranhaPlant piranha) {estado.visitar_enemigo(piranha);}
	public void visitar_enemigo(Spiny spiny) {estado.visitar_enemigo(spiny);}
	public void aceptar(EnemigoVisitorEnemigo visitante) {visitante.visitar_enemigo(this);}	
}