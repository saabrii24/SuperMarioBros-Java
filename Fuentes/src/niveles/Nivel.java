package niveles;

/**
 * Esta clase modela un nivel del juego. Un nivel incluye información sobre su número, la posición
 * inicial del jugador, los objetivos, el límite de movimientos, el tiempo límite, y contadores para
 * los diferentes elementos del juego (caramelos, gelatinas, glaseados, etc.). También almacena el
 * puntaje inicial del nivel.
 * 
 */
public class Nivel {
	
	// Atributos del nivel
	protected final double posicion_inicial_jugador_x;
	protected final double posicion_inicial_jugador_y;
	protected final int tiempo;
	protected final int numero_de_nivel;

    /**
     * Constructor para la clase Builder, que permite construir instancias de Nivel con
     * configuraciones específicas.
     * 
     */
    public Nivel(Builder builder) {
        this.posicion_inicial_jugador_x = builder.posicion_inicial_jugador_x;
        this.posicion_inicial_jugador_y = builder.posicion_inicial_jugador_y;
        this.tiempo = builder.tiempo;
        this.numero_de_nivel = builder.numero_de_nivel;
    }

    /**
     * Obtiene la posicion inicial del jugador en x.
     * 
     * @return La posicion inicial del jugador en x.
     * 
     */
    public double get_posicion_inicial_jugador_x() {
        return posicion_inicial_jugador_x;
    }

    /**
     * Obtiene la posicion inicial del jugador en y.
     * 
     * @return La posicion inicial del jugador en y.
     * 
     */
    public double get_posicion_inicial_jugador_y() {
        return posicion_inicial_jugador_y;
    }

    /**
     * Obtiene el límite de tiempo en segundos.
     * 
     * @return El límite de tiempo en segundos.
     * 
     */
    public int get_tiempo_restante() {
        return tiempo;
    }
    
    /**
     * Obtiene el número del nivel.
     * 
     * @return El número del nivel.
     * 
     */
    public int get_numero_de_nivel() {
        return numero_de_nivel;
    }
    
    @Override
    public String toString() {
        return "Nivel {" +
               "Numero de Nivel=" + numero_de_nivel +
               ", Posicion Inicial X=" + posicion_inicial_jugador_x +
               ", Posicion Inicial Y=" + posicion_inicial_jugador_y +
               ", Tiempo=" + tiempo +
               '}';
    }

    /**
     * Clase interna que implementa el patrón Builder para construir instancias de Nivel con
     * configuraciones específicas.
     * 
     */
    public static class Builder {
    	
    	// Atributos de configuración del nivel
        private double posicion_inicial_jugador_x;
        private double posicion_inicial_jugador_y;
        private int tiempo;
        private int numero_de_nivel;

        public Nivel.Builder set_nivel_actual(int numero_de_nivel) {
            this.numero_de_nivel = numero_de_nivel;
            return this;
        }

        public Nivel.Builder set_posicion_en_x_inicial(double posicion_inicial_jugador_x) {
            this.posicion_inicial_jugador_x = posicion_inicial_jugador_x;
            return this;
        }

        public Nivel.Builder set_posicion_en_y_inicial(double posicion_inicial_jugador_y) {
            this.posicion_inicial_jugador_y = posicion_inicial_jugador_y;
            return this;
        }

        public Nivel.Builder set_tiempo_limite(int tiempo) {
            if (tiempo < 0) {
                throw new IllegalArgumentException("El tiempo no puede ser negativo.");
            }
            this.tiempo = tiempo;
            return this;
        }

        /**
         * Construye una instancia de Nivel con la configuración establecida.
         *
         * @return Una instancia de Nivel con la configuración proporcionada.
         * 
         */
        public Nivel build() {
            return new Nivel(this);
        }
    }
}


