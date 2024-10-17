package fabricas;

public abstract class SpritesFactory {
    private String ruta_a_carpeta;

    public SpritesFactory(String ruta_a_carpeta) {
        this.ruta_a_carpeta = ruta_a_carpeta;
    }

    private Sprite cargarSprite(String nombreArchivo) {
        return new Sprite(ruta_a_carpeta + nombreArchivo);
    }

    public Sprite get_mario_ocioso_derecha() {
        return cargarSprite("ocioso_derecha.png");
    }
    
    public Sprite get_mario_ocioso_izquierda() {
        return cargarSprite("ocioso_izquierda.png");
    }

    public Sprite get_mario_movimiento_derecha() {
        return cargarSprite("movimiento_derecha.gif");
    }

    public Sprite get_mario_movimiento_izquierda() {
        return cargarSprite("movimiento_izquierda.gif");
    }

    public Sprite get_mario_saltando() {
        return cargarSprite("saltando_derecha.png");
    }

    public Sprite get_goomba() {
        return cargarSprite("goomba.gif");
    }

    public Sprite get_koopa_troopa() {
        return cargarSprite("koopa.gif");
    }

    public Sprite get_piranha_plant() {
        return cargarSprite("pirania.png");
    }

    public Sprite get_lakitu() {
        return cargarSprite("lakitu.png");
    }

    public Sprite get_spiny() {
        return cargarSprite("spiny.png");
    }

    public Sprite get_buzzy_beetle() {
        return cargarSprite("buzzy_beetle.png");
    }

    public Sprite get_super_champi() {
        return cargarSprite("super_champi.png");
    }

    public Sprite get_flor_de_fuego() {
        return cargarSprite("flor_de_fuego.png");
    }

    public Sprite get_estrella() {
        return cargarSprite("estrella.gif");
    }

    public Sprite get_champi_verde() {
        return cargarSprite("champi_verde.png");
    }

    public Sprite get_moneda() {
        return cargarSprite("moneda.png");
    }

    public Sprite get_bloque_solido() {
        return cargarSprite("bloque_solido.png");
    }

    public Sprite get_ladrillo_solido() {
        return cargarSprite("ladrillo_solido.png");
    }

    public Sprite get_bloque_de_pregunta() {
        return cargarSprite("bloque_de_pregunta.gif");
    }

    public Sprite get_tuberias() {
        return cargarSprite("tuberia.png");
    }

    public Sprite get_vacio() {
        return cargarSprite("vacio.png");
    }

    public Sprite get_bola_de_fuego() {
        return cargarSprite("bola_de_fuego.gif");
    }
}