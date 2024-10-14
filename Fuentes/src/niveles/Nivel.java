package niveles;

/**
 * La clase {@code Nivel} modela un nivel del juego. Un nivel incluye información sobre su número,
 * la posición inicial del jugador, el tiempo límite y otros atributos relevantes para su configuración.
 * Esta clase utiliza el patrón de diseño Builder para facilitar la creación de instancias de Nivel.
 */
public class Nivel {

    // Atributos del nivel
    protected final double posicion_inicial_jugador_x;
    protected final double posicion_inicial_jugador_y;
    protected final int tiempo;
    protected final int numero_de_nivel;

    /**
     * Constructor para la clase {@code Nivel}, que utiliza un objeto {@code Builder} para
     * configurar los atributos del nivel.
     *
     * @param builder El objeto {@code Builder} que contiene las configuraciones para el nivel.
     */
    public Nivel(Builder builder) {
        this.posicion_inicial_jugador_x = builder.posicion_inicial_jugador_x;
        this.posicion_inicial_jugador_y = builder.posicion_inicial_jugador_y;
        this.tiempo = builder.tiempo;
        this.numero_de_nivel = builder.numero_de_nivel;
    }

    /**
     * Obtiene la posición inicial del jugador en el eje X.
     *
     * @return La posición inicial del jugador en X.
     */
    public double get_posicion_inicial_jugador_x() {
        return posicion_inicial_jugador_x;
    }

    /**
     * Obtiene la posición inicial del jugador en el eje Y.
     *
     * @return La posición inicial del jugador en Y.
     */
    public double get_posicion_inicial_jugador_y() {
        return posicion_inicial_jugador_y;
    }

    /**
     * Obtiene el límite de tiempo en segundos para completar el nivel.
     *
     * @return El límite de tiempo en segundos.
     */
    public int get_tiempo_restante() {
        return tiempo;
    }

    /**
     * Obtiene el número del nivel.
     *
     * @return El número del nivel.
     */
    public int get_numero_de_nivel() {
        return numero_de_nivel;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Nivel}.
     *
     * @return Una cadena que representa el estado del nivel, incluyendo el número, las posiciones
     * iniciales y el tiempo límite.
     */
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
     * Clase interna {@code Builder} que implementa el patrón Builder para construir instancias de
     * {@code Nivel} con configuraciones específicas.
     */
    public static class Builder {

        // Atributos de configuración del nivel
        private double posicion_inicial_jugador_x;
        private double posicion_inicial_jugador_y;
        private int tiempo;
        private int numero_de_nivel;

        /**
         * Establece el número del nivel.
         *
         * @param numero_de_nivel El número del nivel en el juego.
         * @return La instancia actual de {@code Builder} para encadenar métodos.
         */
        public Nivel.Builder set_nivel_actual(int numero_de_nivel) {
            this.numero_de_nivel = numero_de_nivel;
            return this;
        }

        /**
         * Establece la posición inicial del jugador en el eje X.
         *
         * @param posicion_inicial_jugador_x La posición inicial del jugador en X.
         * @return La instancia actual de {@code Builder} para encadenar métodos.
         */
        public Nivel.Builder set_posicion_en_x_inicial(double posicion_inicial_jugador_x) {
            this.posicion_inicial_jugador_x = posicion_inicial_jugador_x;
            return this;
        }

        /**
         * Establece la posición inicial del jugador en el eje Y.
         *
         * @param posicion_inicial_jugador_y La posición inicial del jugador en Y.
         * @return La instancia actual de {@code Builder} para encadenar métodos.
         */
        public Nivel.Builder set_posicion_en_y_inicial(double posicion_inicial_jugador_y) {
            this.posicion_inicial_jugador_y = posicion_inicial_jugador_y;
            return this;
        }

        /**
         * Establece el límite de tiempo en segundos para completar el nivel.
         *
         * @param tiempo El límite de tiempo en segundos.
         * @return La instancia actual de {@code Builder} para encadenar métodos.
         * @throws IllegalArgumentException si el tiempo es negativo.
         */
        public Nivel.Builder set_tiempo_limite(int tiempo) {
            if (tiempo < 0) {
                throw new IllegalArgumentException("El tiempo no puede ser negativo.");
            }
            this.tiempo = tiempo;
            return this;
        }

        /**
         * Construye una instancia de {@code Nivel} con la configuración establecida.
         *
         * @return Una instancia de {@code Nivel} con la configuración proporcionada.
         */
        public Nivel build() {
            return new Nivel(this);
        }
    }

}
