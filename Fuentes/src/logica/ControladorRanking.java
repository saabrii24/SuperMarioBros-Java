package logica;

import java.awt.Image;
import java.io.*;
import javax.swing.ImageIcon; // Importa ImageIcon para los iconos personalizados
import javax.swing.JOptionPane; // Importa JOptionPane para las entradas gráficas

import ranking.Jugador;
import ranking.Ranking;

public class ControladorRanking implements Serializable {

    private static final long serialVersionUID = -6203851574103194125L;
    private final String ruta_archivo;
    protected Ranking ranking;

    public ControladorRanking() {
        this("./ranking.tdp"); // Ruta por defecto
    }

    public ControladorRanking(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
        cargar_ranking(); // Deserializa el archivo (carga el ranking guardado)

        if (this.ranking == null) { // Si no se pudo cargar, crea un nuevo ranking
            this.ranking = new Ranking();
        }
    }

    public Ranking get_ranking() {
        return ranking;
    }

    public void set_ranking(Ranking ranking) {
        this.ranking = ranking;
    }

    public void actualizar_ranking(int puntaje) {
        if (ranking == null) {
            ranking = new Ranking();
        }

        if (puede_entrar_en_ranking(puntaje)) {
            String nombre_jugador = solicitar_nombre_jugador();
            ranking.agregar_jugador(nombre_jugador, puntaje);
            guardar_ranking();
        }
    }

    // Método para verificar si el puntaje puede entrar en el ranking
    private boolean puede_entrar_en_ranking(int puntaje) {
        if (ranking.get_ranking().size() < 5) { // Si el ranking tiene menos de 5 jugadores, siempre puede entrar
            return true;
        }
        
        // Obtener el puntaje más bajo en el ranking
        Jugador jugador_menor = ranking.get_ranking().get(ranking.get_ranking().size() - 1);
        return puntaje > jugador_menor.get_puntos(); // Comparar el puntaje
    }


    // Método para deserializar el ranking (cargar desde archivo)
    public void cargar_ranking() {
        try (FileInputStream file_input_stream = new FileInputStream(ruta_archivo);
             ObjectInputStream object_input_stream = new ObjectInputStream(file_input_stream)) {

            set_ranking((Ranking) object_input_stream.readObject());

        } catch (FileNotFoundException e) {
            //Si archivo no encontrado, se crea un nuevo ranking.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para serializar el ranking (guardar en archivo)
    private void guardar_ranking() {
        try (FileOutputStream file_output_stream = new FileOutputStream(ruta_archivo);
             ObjectOutputStream object_output_stream = new ObjectOutputStream(file_output_stream)) {
        	object_output_stream.writeObject(ranking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String solicitar_nombre_jugador() {
        ImageIcon icono_mario = new ImageIcon(this.getClass().getResource("/assets/imagenes/icon.png"));
        Image imagen_escalada = icono_mario.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon icono_mario_chiquito = new ImageIcon(imagen_escalada);

        String nombre_jugador = (String) JOptionPane.showInputDialog(
            null, 
            "Ingrese su nombre:", 
            "Nombre del Jugador", 
            JOptionPane.PLAIN_MESSAGE, 
            icono_mario_chiquito, 
            null, 
            null 
        );

        if (nombre_jugador == null || nombre_jugador.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            return solicitar_nombre_jugador(); // Pide el nombre nuevamente
        }

        return nombre_jugador;
    }


}
