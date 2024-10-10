package ranking;

import java.util.*;

/**
 * La clase {@code Ranking} gestiona el sistema de clasificación de jugadores
 * y mantiene el top 5 de puntajes más altos registrados en el juego.
 */
public class Ranking {

    /**
     * Mapa que almacena el nombre de los jugadores y sus puntajes asociados.
     */
    protected final Map<String, Integer> ranking;

    /**
     * Crea una instancia de {@code Ranking} con un mapa vacío para almacenar
     * los puntajes de los jugadores.
     */
    public Ranking() {
        this.ranking = new LinkedHashMap<>();
    }

    /**
     * Agrega un jugador al ranking o actualiza su puntaje si ya existe.
     * Luego, ordena el ranking en orden descendente según los puntajes y
     * mantiene solo el top 5 de jugadores con los puntajes más altos.
     *
     * @param nombre El nombre del jugador.
     * @param puntos El puntaje obtenido por el jugador.
     */
    public void agregar_jugador(String nombre, int puntos) {
        // Agregar o actualizar el puntaje del jugador
        ranking.put(nombre, puntos);

        // Ordenar el ranking de mayor a menor puntaje
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(ranking.entrySet());
        listaOrdenada.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Limitar el ranking solo al top 5
        ranking.clear();
        for (int i = 0; i < Math.min(5, listaOrdenada.size()); i++) {
            Map.Entry<String, Integer> entry = listaOrdenada.get(i);
            ranking.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Obtiene el ranking ordenado de jugadores con sus puntajes.
     * El ranking está limitado a los 5 puntajes más altos.
     *
     * @return Un mapa ordenado con el top 5 de jugadores y sus puntajes.
     */
    public Map<String, Integer> get_sort_ranking() {
        return new LinkedHashMap<>(ranking);
    }
}
