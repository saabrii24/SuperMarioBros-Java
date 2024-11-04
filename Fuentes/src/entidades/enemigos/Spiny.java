package entidades.enemigos;

import entidades.Entidad;
import entidades.EntidadMovible;
import entidades.plataformas.*;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;
public class Spiny extends Enemigo{

	public Spiny(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		set_velocidad_en_x(3);
	}

	public void actualizar() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
        }

        posicion_en_x += velocidad_en_x;
        actualizar_sprite();
    }
	
    public int calcular_puntaje() {
        return 60;
    }
    
    public int calcular_penalizacion() {
        return 30;
    }

    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }

    protected void mover_izquierda() {
        this.velocidad_en_x = -2;
        movimiento_derecha = false;
    }

    protected void mover_derecha() {
        this.velocidad_en_x = 2;
        movimiento_derecha = true;
    }

    protected void detener_movimiento() {
        this.velocidad_en_x = 0;
    }
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_spiny(this);
            eliminar_del_mapa();
        }
    }

	private void actualizar_sprite() {
		if(velocidad_en_y > 0.4) set_sprite(Juego.get_instancia().get_fabrica_sprites().get_spiny_cayendo());
		else if (velocidad_en_x != 0) {
            set_sprite(movimiento_derecha ? 
            	Juego.get_instancia().get_fabrica_sprites().get_spiny_movimiento_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_spiny_movimiento_izquierda());
		}
	}

	public boolean aceptar(EnemigosVisitor visitador) {
		return visitador.visitar(this);
	}

	@Override
	public void visitar(Vacio vacio) {
		this.eliminar_del_mapa();
	}


	@Override
	public void visitar(BloqueSolido bloque_solido) {
		ajustar_posicion_enemigo_sobre_plataforma(this,bloque_solido);
	}


	@Override
	public void visitar(BloqueDePregunta bloque_de_pregunta) {
		ajustar_posicion_enemigo_sobre_plataforma(this,bloque_de_pregunta);
	}


	@Override
	public void visitar(Tuberias tuberia) {
		ajustar_posicion_enemigo_sobre_plataforma(this,tuberia);
	}


	@Override
	public void visitar(LadrilloSolido ladrillo_solido) {
		ajustar_posicion_enemigo_sobre_plataforma(this,ladrillo_solido);
	}
	
	 private void ajustar_posicion_enemigo_sobre_plataforma(EntidadMovible enemigo, Entidad plataforma) {
	        enemigo.set_velocidad_en_y(0);
	        enemigo.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
	 }
	 
	 @Override
		public void visitar_enemigo(Goomba goomba) {}


		@Override
		public void visitar_enemigo(BuzzyBeetle buzzy) {}


		@Override
		public void visitar_enemigo(KoopaTroopa koopa) {
		}


		@Override
		public void visitar_enemigo(Lakitu lakitu) {}


		@Override
		public void visitar_enemigo(PiranhaPlant piranha) {}


		@Override
		public void visitar_enemigo(Spiny spiny) {
			spiny.set_direccion(spiny.get_direccion()*-1);
			this.set_direccion(direccion);	
		}


		@Override
		public void aceptar(EnemigoVisitorEnemigo visitador) {
			visitador.visitar_enemigo(this);
		}	
}