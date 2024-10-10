package fabricas;

import entidades.BolaDeFuego;
import entidades.enemigos.*;
import entidades.mario.Mario;
import entidades.plataformas.*;
import entidades.powerups.*;

public class EntidadesFactory {

    private SpritesFactory fabrica_sprites;

    public EntidadesFactory(SpritesFactory fabrica_sprites) {
        this.fabrica_sprites = fabrica_sprites;
    }

    public Mario get_mario(int x, int y) {
        return Mario.get_instancia();
    }

    public Goomba get_goomba(int x, int y) {
        Sprite sprite_goomba = fabrica_sprites.get_goomba();
        return new Goomba(x, y, sprite_goomba);
    }

    public KoopaTroopa get_koopa_troopa(int x, int y) {
        Sprite sprite_koopa = fabrica_sprites.get_koopa_troopa();
        return new KoopaTroopa(x, y, sprite_koopa);
    }

    public PiranhaPlant get_piranha_plant(int x, int y) {
        Sprite sprite_piranha = fabrica_sprites.get_piranha_plant();
        return new PiranhaPlant(x, y, sprite_piranha);
    }

    public Lakitu get_lakitu(int x, int y) {
        Sprite sprite_lakitu = fabrica_sprites.get_lakitu();
        return new Lakitu(x, y, sprite_lakitu);
    }

    public Spiny get_spiny(int x, int y) {
        Sprite sprite_lpiny = fabrica_sprites.get_spiny();
        return new Spiny(x, y, sprite_lpiny);
    }

    public BuzzyBeetle get_buzzy_beetle(int x, int y) {
        Sprite spriteBuzzy = fabrica_sprites.get_buzzy_beetle();
        return new BuzzyBeetle(x, y, spriteBuzzy);
    }

    public SuperChampi get_super_champi(int x, int y) {
        Sprite sprite_champi = fabrica_sprites.get_super_champi();
        return new SuperChampi(x, y, sprite_champi);
    }

    public FlorDeFuego get_flor_de_fuego(int x, int y) {
        Sprite sprite_flor_de_fuego = fabrica_sprites.get_flor_de_fuego();
        return new FlorDeFuego(x, y, sprite_flor_de_fuego);
    }

    public Estrella get_estrella(int x, int y) {
        Sprite sprite_estrella = fabrica_sprites.get_estrella();
        return new Estrella(x, y, sprite_estrella);
    }

    public ChampiVerde get_champi_verde(int x, int y) {
        Sprite sprite_champi_verde = fabrica_sprites.get_champi_verde();
        return new ChampiVerde(x, y, sprite_champi_verde);
    }

    public Moneda get_moneda(int x, int y) {
        Sprite sprite_moneda = fabrica_sprites.get_moneda();
        return new Moneda(x, y, sprite_moneda);
    }

    public BloqueSolido get_bloque_solido(int x, int y) {
        Sprite sprite_bloque_solido = fabrica_sprites.get_bloque_solido();
        return new BloqueSolido(x, y, sprite_bloque_solido);
    }

    public LadrilloSolido get_ladrillo_solido(int x, int y) {
        Sprite sprite_ladrillo_solido = fabrica_sprites.get_ladrillo_solido();
        return new LadrilloSolido(x, y, sprite_ladrillo_solido);
    }

    public BloqueDePregunta get_bloque_de_pregunta(int x, int y) {
        Sprite sprite_bloque_de_pregunta = fabrica_sprites.get_bloque_de_pregunta();
        return new BloqueDePregunta(x, y, sprite_bloque_de_pregunta);
    }

    public Tuberias get_tuberias(int x, int y) {
        Sprite sprite_tuberias = fabrica_sprites.get_tuberias();
        return new Tuberias(x, y, sprite_tuberias);
    }

    public Vacio get_vacio(int x, int y) {
        Sprite sprite_vacio = fabrica_sprites.get_vacio();
        return new Vacio(x, y, sprite_vacio);
    }

    public BolaDeFuego get_bola_de_fuego(int x, int y) {
        Sprite sprite_bola_de_fuego = fabrica_sprites.get_bola_de_fuego();
        return new BolaDeFuego(x, y, sprite_bola_de_fuego);
    }
}
