package fabricas;

import entidades.BolaDeFuego;
import entidades.enemigos.*;
import entidades.plataformas.*;
import entidades.powerups.*;

public class EntidadesFactory {
    private SpritesFactory fabrica_sprites;
    
    public EntidadesFactory(SpritesFactory fabrica_sprites) { this.fabrica_sprites = fabrica_sprites; }

    // Enemigos
    public Goomba get_goomba(int x, int y) { return new Goomba(x, y, fabrica_sprites.get_goomba()); }
    public KoopaTroopa get_koopa_troopa(int x, int y) { return new KoopaTroopa(x, y, fabrica_sprites.get_koopa_movimiento_izquierda()); }
    public PiranhaPlant get_piranha_plant(int x, int y) { return new PiranhaPlant(x, y, fabrica_sprites.get_piranha_plant()); }
    public Lakitu get_lakitu(int x, int y) { return new Lakitu(x, y, fabrica_sprites.get_lakitu()); }
    public Spiny get_spiny(int x, int y) { return new Spiny(x, y, fabrica_sprites.get_spiny_movimiento_izquierda()); }
    public BuzzyBeetle get_buzzy_beetle(int x, int y) { return new BuzzyBeetle(x, y, fabrica_sprites.get_buzzy_movimiento_izquierda()); }

    // Power-ups y coleccionables
    public SuperChampi get_super_champi(int x, int y) { return new SuperChampi(x, y, fabrica_sprites.get_super_champi()); }
    public FlorDeFuego get_flor_de_fuego(int x, int y) { return new FlorDeFuego(x, y, fabrica_sprites.get_flor_de_fuego()); }
    public Estrella get_estrella(int x, int y) { return new Estrella(x, y, fabrica_sprites.get_estrella()); }
    public ChampiVerde get_champi_verde(int x, int y) { return new ChampiVerde(x, y, fabrica_sprites.get_champi_verde()); }
    public Moneda get_moneda(int x, int y) { return new Moneda(x, y, fabrica_sprites.get_moneda()); }

    // Plataformas
    public BloqueSolido get_bloque_solido(int x, int y) { return new BloqueSolido(x, y, fabrica_sprites.get_bloque_solido()); }
    public LadrilloSolido get_ladrillo_solido(int x, int y) { return new LadrilloSolido(x, y, fabrica_sprites.get_ladrillo_solido()); }
    public BloqueDePregunta get_bloque_de_pregunta(int x, int y, PowerUp powerup) { return new BloqueDePregunta(x, y, fabrica_sprites.get_bloque_de_pregunta(), powerup); }
    public Tuberias get_tuberias(int x, int y) { return new Tuberias(x, y, fabrica_sprites.get_tuberias()); }
    public Vacio get_vacio(int x, int y) { return new Vacio(x, y, fabrica_sprites.get_vacio()); }

    // Otros
    public BolaDeFuego get_bola_de_fuego(int x, int y) { return new BolaDeFuego(x, y, fabrica_sprites.get_bola_de_fuego()); }
}