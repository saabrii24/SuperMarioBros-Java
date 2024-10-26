package niveles;

/**
 * La clase {@code Nivel} modela un nivel del juego. Un nivel incluye información sobre su número,
 * la posición inicial del jugador, el tiempo límite y otros atributos relevantes para su configuración.
 * Esta clase utiliza el patrón de diseño Builder para facilitar la creación de instancias de Nivel.
 */
public class Nivel {
    protected final int posicion_inicial_jugador_x;
    protected final int posicion_inicial_jugador_y;
    protected final int tiempo_inicial;
    protected final int numero_de_nivel;
    protected volatile int tiempo_restante;

    /**
     * Constructor para la clase {@code Nivel}, que utiliza un objeto {@code Builder} para
     * configurar los atributos del nivel.
     */
    public Nivel(Builder builder) {
        this.posicion_inicial_jugador_x = builder.posicion_inicial_jugador_x;
        this.posicion_inicial_jugador_y = builder.posicion_inicial_jugador_y;
        this.tiempo_inicial = builder.tiempo;
        this.tiempo_restante = builder.tiempo;
        this.numero_de_nivel = builder.numero_de_nivel;
    }

    // Getters
    public int get_posicion_inicial_jugador_x() { return posicion_inicial_jugador_x; }
    public int get_posicion_inicial_jugador_y() { return posicion_inicial_jugador_y; }
    public int get_tiempo_inicial() { return tiempo_inicial; }
    public int get_tiempo_restante() { return tiempo_restante; }
    public int get_numero_de_nivel() { return numero_de_nivel; }
    public synchronized void set_tiempo_restante(int tiempo) {
        this.tiempo_restante = tiempo;
    }

    public synchronized boolean decrementar_tiempo() {
        if (tiempo_restante > 0) {
            tiempo_restante--;
            return true;
        }
        return false;
    }
    
    /**
     * Clase interna {@code Builder} que implementa el patrón Builder para construir instancias de
     * {@code Nivel} con configuraciones específicas.
     */
    public static class Builder {
        private int posicion_inicial_jugador_x;
        private int posicion_inicial_jugador_y;
        private int tiempo;
        private int numero_de_nivel;

        public Nivel.Builder set_nivel_actual(int numero_de_nivel) { 
            this.numero_de_nivel = numero_de_nivel; 
            return this; 
        }

        public Nivel.Builder set_posicion_en_x_inicial(int posicion_inicial_jugador_x) { 
            this.posicion_inicial_jugador_x = posicion_inicial_jugador_x; 
            return this; 
        }

        public Nivel.Builder set_posicion_en_y_inicial(int posicion_inicial_jugador_y) { 
            this.posicion_inicial_jugador_y = posicion_inicial_jugador_y; 
            return this; 
        }

        public Nivel.Builder set_tiempo_limite(int tiempo) {
            if (tiempo < 0) { throw new IllegalArgumentException("El tiempo no puede ser negativo."); }
            this.tiempo = tiempo;
            return this;
        }

        /** @return Una instancia de {@code Nivel} con la configuración proporcionada. */
        public Nivel build() { return new Nivel(this); }
    }
}