package ranking;

import java.io.Serializable;
import java.util.*;

/**
 * La clase {@code Ranking} gestiona el sistema de clasificación de jugadores
 * y mantiene el top 5 de puntajes más altos registrados en el juego.
 */
public class Ranking implements Serializable {

	private static final long serialVersionUID = -8353261000686192762L;
	protected List<Jugador> ranking;

	public Ranking() {
        this.ranking = new ArrayList<>(5);
    }

    public void agregar_jugador(String nombre, int puntos) {
        ranking.add(new Jugador(nombre, puntos));
        ranking.sort((o1, o2) -> o1.compareTo(o2));
        ranking.sort(Collections.reverseOrder());
    }

    public List<Jugador> get_ranking() { return ranking; }

    public int size() { return ranking.size(); }

    public Jugador get_jugador_posicion(int i) {
        if (i >= 0 && i < ranking.size()) {
            return ranking.get(i);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + i);
        }
    }
    
}
