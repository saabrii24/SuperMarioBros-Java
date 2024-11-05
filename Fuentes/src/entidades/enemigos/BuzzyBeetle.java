package entidades.enemigos;

import entidades.Entidad;
import entidades.interfaces.EnemigoVisitorEnemigo;
import entidades.EntidadMovible;
import entidades.interfaces.EnemigosVisitor;
import entidades.plataformas.*;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

public class BuzzyBeetle extends Enemigo{

	public BuzzyBeetle(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		set_velocidad_en_x(3);
	}

	//Actualizar
    public void actualizar() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
        }

        posicion_en_x += velocidad_en_x;
        actualizar_sprite();
    }
    private void actualizar_sprite() {
		if (velocidad_en_x != 0) {
            set_sprite(movimiento_derecha ? 
            	Juego.get_instancia().get_fabrica_sprites().get_buzzy_movimiento_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_buzzy_movimiento_izquierda());
		}
	}

    //Puntajes
    public int calcular_puntaje() {return 30;}
    public int calcular_penalizacion() {return 15;}

    //Movimientos
    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }
    private void mover_izquierda() {
        this.velocidad_en_x = -1;
        movimiento_derecha = false;
    }
    private void mover_derecha() {
        this.velocidad_en_x = 1;
        movimiento_derecha = true;
    }
    private void detener_movimiento() {
        this.velocidad_en_x = 0;
    }

    //Destruir del mapa
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

	//Colisiones
	
	//Colision entre mario y buzzy
	public boolean aceptar(EnemigosVisitor visitante) {return visitante.visitar(this);}

	//Colisiones entre buzzy y plataformas
	public void visitar(Vacio vacio) {}
	public void visitar(BloqueSolido bloque_solido) {ajustar_posicion_enemigo_sobre_plataforma(this,bloque_solido);}
	public void visitar(BloqueDePregunta bloque_de_pregunta) {ajustar_posicion_enemigo_sobre_plataforma(this,bloque_de_pregunta);}
	public void visitar(Tuberia tuberia) {ajustar_posicion_enemigo_sobre_plataforma(this,tuberia);}
	public void visitar(LadrilloSolido ladrillo_solido) {ajustar_posicion_enemigo_sobre_plataforma(this,ladrillo_solido);}
	private void ajustar_posicion_enemigo_sobre_plataforma(EntidadMovible enemigo, Entidad plataforma) {
	   enemigo.set_velocidad_en_y(0);
	   enemigo.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
	}

	//Colisiones entre enemigos
	public void visitar_enemigo(Goomba goomba) {}
	public void visitar_enemigo(BuzzyBeetle buzzy) {
		buzzy.set_direccion(buzzy.get_direccion()*-1);
		this.set_direccion(direccion);
	}
	public void visitar_enemigo(KoopaTroopa koopa) {}
	public void visitar_enemigo(Lakitu lakitu) {}
	public void visitar_enemigo(PiranhaPlant piranha) {}
	public void visitar_enemigo(Spiny spiny) {}
	public void aceptar(EnemigoVisitorEnemigo visitante) {visitante.visitar_enemigo(this);}	
}