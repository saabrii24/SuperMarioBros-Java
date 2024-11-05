package entidades.enemigos;

import entidades.Entidad;
import entidades.interfaces.EnemigoVisitorEnemigo;
import entidades.interfaces.EnemigosVisitor;
import entidades.EntidadMovible;
import entidades.plataformas.*;
import fabricas.Sprite;
import logica.Mapa;

public class Goomba extends Enemigo {
    public Goomba(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    //Actualizar
    public void actualizar() {
        if (esta_cayendo()) {
            set_velocidad_en_y(get_velocidad_en_y() + GRAVEDAD);
            set_posicion_en_y(get_posicion_en_y() - get_velocidad_en_y());
        }
        set_posicion_en_x(get_posicion_en_x() + get_velocidad_en_x());
    }
    
    //Destruir del mapa
	public void destruir(Mapa mapa) {
        if (!destruida) {
            destruida = true;           
            mapa.eliminar_enemigo(this);
            eliminar_del_mapa();
        }
    }

	//Puntajes
    public int calcular_puntaje() { return 60; }
    public int calcular_penalizacion() { return 30; }

    //Movimientos
    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }
    private void mover_izquierda() {
    	set_velocidad_en_x(-1);
        movimiento_derecha = false;
    }
    private void mover_derecha() {
        set_velocidad_en_x(1);
        set_movimiento_derecha(true);
        movimiento_derecha = true;
    }
    private void detener_movimiento() {
        set_velocidad_en_x(0);
    }
   
    //Colisiones
    
    //Colision mario y goomba
    public boolean aceptar(EnemigosVisitor visitante) {
		return visitante.visitar(this);
	}
    
    //colision goomba y plataforma
	public void visitar(Vacio vacio) {}
	public void visitar(BloqueSolido bloque_solido) {ajustar_posicion_enemigo_sobre_plataforma(this,bloque_solido);}
	public void visitar(BloqueDePregunta bloque_de_pregunta) {ajustar_posicion_enemigo_sobre_plataforma(this,bloque_de_pregunta);}
	public void visitar(Tuberia tuberia) {ajustar_posicion_enemigo_sobre_plataforma(this,tuberia);}
	public void visitar(LadrilloSolido ladrillo_solido) {ajustar_posicion_enemigo_sobre_plataforma(this,ladrillo_solido);}
	private void ajustar_posicion_enemigo_sobre_plataforma(EntidadMovible enemigo, Entidad plataforma) {
	    enemigo.set_velocidad_en_y(0);
	    enemigo.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
	}	
	 
	//colision entre enemigos
	public void visitar_enemigo(Goomba goomba) {
		goomba.set_direccion(goomba.get_direccion()*-1);
		this.set_direccion(direccion);
	}
	public void visitar_enemigo(BuzzyBeetle buzzy) {}
	public void visitar_enemigo(KoopaTroopa koopa) {}
	public void visitar_enemigo(Lakitu lakitu) {}
	public void visitar_enemigo(PiranhaPlant piranha) {}
	public void visitar_enemigo(Spiny spiny) {}
	public void aceptar(EnemigoVisitorEnemigo visitante) {
		visitante.visitar_enemigo(this);
	}	
}