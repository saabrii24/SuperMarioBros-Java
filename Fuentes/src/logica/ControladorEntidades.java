package logica;

import java.util.ArrayList;
import java.util.List;

import entidades.BolaDeFuego;
import entidades.Entidad;
import entidades.EntidadMovible;
import entidades.mario.Mario;
import entidades.mario.NormalMarioState;

public class ControladorEntidades {
    private final Juego juego;
    
    public ControladorEntidades(Juego juego) {
        this.juego = juego;
    }

    public void mover_mario() {
        Mario mario = Mario.get_instancia();
        synchronized(mario) {
            if(mario.get_dimension() != null) {
                mario.actualizar();
                juego.get_mapa_nivel_actual().actualizar_entidades();
                mario.finalizar_invulnerabilidad();
                
                if (juego.get_mapa_nivel_actual().get_colisionador().get_murio_mario() && 
                    !juego.controlador_nivel.esta_reiniciando_nivel()) {
                    juego.controlador_nivel.reiniciar_nivel();
                }
            }
            
            mario.mover();
            verificar_estado_mario(mario);
        }
    }

    private void verificar_estado_mario(Mario mario) {
        if(mario.get_posicion_en_x() >= 4410) {
            manejar_cambio_nivel();
        }
        if(mario.get_posicion_en_y() < 0) {
            mario_cae_al_vacio(mario);
        }
        if(mario.get_vidas() == 0) {
            juego.controlador_vistas.accionar_pantalla_derrota();
            juego.detener_hilos();
        }
    }

    private void manejar_cambio_nivel() {
        juego.controlador_nivel.incrementar_nivel();
        if(juego.controlador_nivel.get_nivel_actual() > 3) {
            juego.controlador_vistas.accionar_pantalla_victoria();
            juego.controlador_ranking.actualizar_ranking(Mario.get_instancia().get_puntaje());
            juego.detener_hilos();
        } else {
            juego.controlador_nivel.cargar_datos(juego.fabrica_entidades);
            Mario.get_instancia().set_estado(new NormalMarioState(Mario.get_instancia()));
        }
    }

    private void mario_cae_al_vacio(Mario mario) {
        juego.get_mapa_nivel_actual().get_colisionador().set_murio_mario(true);
        mario.caer_en_vacio();
        mario.destruir();
    }

    public void mover_enemigos() {
        mover_lista_entidades(juego.get_mapa_nivel_actual().get_entidades_goomba());
        mover_lista_entidades(juego.get_mapa_nivel_actual().get_entidades_buzzy_beetle());
        mover_lista_entidades(juego.get_mapa_nivel_actual().get_entidades_koopa_troopa());
        mover_lista_entidades(juego.get_mapa_nivel_actual().get_entidades_lakitu());
        mover_lista_entidades(juego.get_mapa_nivel_actual().get_entidades_spiny());
    }

    private <T extends EntidadMovible> void mover_lista_entidades(List<T> entidades) {
        List<T> entidades_copia = new ArrayList<>(entidades);
        for (T entidad : entidades_copia) {
            synchronized(entidad) {
                try {
                    entidad.mover();
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    public void mover_proyectiles() {
        List<BolaDeFuego> proyectiles = new ArrayList<>(juego.get_mapa_nivel_actual().get_entidades_proyectiles());
        for (BolaDeFuego proyectil : proyectiles) {
            synchronized(proyectil) {
                try {
                    proyectil.mover();
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    public void notificar_observadores_mario() {
        Mario.get_instancia().notificar_observer();
    }

    public void notificar_observadores_enemigos() {
        Mapa mapa = juego.get_mapa_nivel_actual();
        notificar_lista_entidades(mapa.get_entidades_buzzy_beetle());
        notificar_lista_entidades(mapa.get_entidades_koopa_troopa());
        notificar_lista_entidades(mapa.get_entidades_goomba());
        notificar_lista_entidades(mapa.get_entidades_lakitu());
        notificar_lista_entidades(mapa.get_entidades_piranha_plant());
        notificar_lista_entidades(mapa.get_entidades_spiny());
    }
    
    private void notificar_lista_entidades(List<? extends Entidad> entidades) {
        // Crear una copia de la lista para evitar ConcurrentModificationException
        List<? extends Entidad> entidades_copia = new ArrayList<>(entidades);
        for (Entidad entidad : entidades_copia) {
            synchronized(entidad) {
                try {
                    entidad.notificar_observer();
                } catch (Exception e) {
                    // Si la entidad fue eliminada mientras iterábamos
                    continue;
                }
            }
        }
    }
}