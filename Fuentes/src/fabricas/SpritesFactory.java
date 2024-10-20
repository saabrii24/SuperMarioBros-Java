package fabricas;

public abstract class SpritesFactory {
    private String ruta_a_carpeta;

    public SpritesFactory(String ruta_a_carpeta) {
        this.ruta_a_carpeta = ruta_a_carpeta;
    }

    private Sprite cargar_sprite(String nombreArchivo) {
        return new Sprite(ruta_a_carpeta + nombreArchivo);
    }

    public Sprite get_mario_ocioso_derecha() {
        return cargar_sprite("ocioso_derecha.png");
    }
    
    public Sprite get_mario_ocioso_izquierda() {
        return cargar_sprite("ocioso_izquierda.png");
    }

    public Sprite get_mario_movimiento_derecha() {
        return cargar_sprite("movimiento_derecha.gif");
    }

    public Sprite get_mario_movimiento_izquierda() {
        return cargar_sprite("movimiento_izquierda.gif");
    }

	public Sprite get_mario_saltando_derecha() {
		return cargar_sprite("saltando_derecha.png");
	}
	
	public Sprite get_mario_saltando_izquierda() {
		return cargar_sprite("saltando_izquierda.png");
	}

    public Sprite get_goomba() {
        return cargar_sprite("goomba.gif");
    }

    public Sprite get_koopa_movimiento_derecha() {
        return cargar_sprite("koopa_derecha.gif");
    }
    
    public Sprite get_koopa_movimiento_izquierda() {
        return cargar_sprite("koopa_izquierda.gif");
    }

    public Sprite get_piranha_plant() {
        return cargar_sprite("pirania.png");
    }

    public Sprite get_lakitu() {
        return cargar_sprite("lakitu.png");
    }

    public Sprite get_spiny_movimiento_derecha() {
        return cargar_sprite("spiny.png");
    }
    public Sprite get_spiny_movimiento_izquierda() {
        return cargar_sprite("spiny.png");
    }

    public Sprite get_buzzy_movimiento_derecha() {
        return cargar_sprite("buzzy_izquierda.png");
    }
    
    public Sprite get_buzzy_movimiento_izquierda() {
        return cargar_sprite("buzzy_izquierda.png");
    }

    public Sprite get_super_champi() {
        return cargar_sprite("super_champi.png");
    }

    public Sprite get_flor_de_fuego() {
        return cargar_sprite("flor_de_fuego.png");
    }

    public Sprite get_estrella() {
        return cargar_sprite("estrella.gif");
    }

    public Sprite get_champi_verde() {
        return cargar_sprite("champi_verde.png");
    }

    public Sprite get_moneda() {
        return cargar_sprite("moneda.png");
    }

    public Sprite get_bloque_solido() {
        return cargar_sprite("bloque_solido.png");
    }

    public Sprite get_ladrillo_solido() {
        return cargar_sprite("ladrillo_solido.png");
    }

    public Sprite get_bloque_de_pregunta() {
        return cargar_sprite("bloque_de_pregunta.gif");
    }

    public Sprite get_tuberias() {
        return cargar_sprite("tuberia.png");
    }

    public Sprite get_vacio() {
        return cargar_sprite("vacio.png");
    }

    public Sprite get_bola_de_fuego() {
        return cargar_sprite("bola_de_fuego.gif");
    }

}