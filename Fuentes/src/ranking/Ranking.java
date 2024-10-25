package ranking;

import java.io.Serializable;
import java.util.*;

/**
 * La clase {@code Ranking} gestiona el sistema de clasificación de jugadores
 * y mantiene el top 5 de puntajes más altos registrados en el juego.
 */
@SuppressWarnings("serial")
public class Ranking implements Serializable {

    /**
     * ArrayList que almacenan los jugadores.
     */
    protected final List<Jugador> ranking;

    /**
     * Crea una instancia de {@code Ranking} con un ArrayList vacío para almacenar
     * los puntajes de los jugadores.
     */
	public Ranking() {
        this.ranking = new ArrayList<>(5);
    }

    /**
     * Agrega un jugador al ranking
     *
     * @param nombre El nombre del jugador.
     * @param puntos El puntaje obtenido por el jugador.
     */
    public void agregar_jugador(String nombre, int puntos) {
        ranking.add(new Jugador(nombre, puntos));
        // Para ordenar
        ranking.sort((o1, o2) -> o1.compareTo(o2));
        ranking.sort(Collections.reverseOrder());
    }

    /**
     * Obtiene el ranking ordenado de jugadores con sus puntajes.
     * El ranking está limitado a los 5 puntajes más altos.
     *
     * @return Una lista ordenado con el top 5 de jugadores.
     */
    public List<Jugador> get_ranking() {
        return ranking;
    }
    
}
