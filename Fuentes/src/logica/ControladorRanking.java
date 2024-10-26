package logica;

import java.io.*;
import java.util.Scanner;
import ranking.Ranking;

public class ControladorRanking implements Serializable {

    private static final long serialVersionUID = -6203851574103194125L;
    private final Juego juego;
    private final String ruta_archivo;
    protected Ranking ranking;

    public ControladorRanking(Juego juego) {
        this("./ranking.tdp", juego); // Ruta por defecto
    }

    public ControladorRanking(String ruta_archivo, Juego juego) {
        this.juego = juego;
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

        String nombre_jugador = solicitar_nombre_jugador();
        ranking.agregar_jugador(nombre_jugador, puntaje);
        guardar_ranking(); // Guarda el ranking actualizado
    }

    // Método para deserializar el ranking (cargar desde archivo)
    public void cargar_ranking() {
        try (FileInputStream fileInputStream = new FileInputStream(ruta_archivo);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            set_ranking((Ranking) objectInputStream.readObject());

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para serializar el ranking (guardar en archivo)
    private void guardar_ranking() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(ruta_archivo);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(ranking);
            System.out.println("Ranking guardado exitosamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String solicitar_nombre_jugador() {
        String nombre_jugador = "";
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese su nombre: ");
            nombre_jugador = scanner.next();
        }
        return nombre_jugador;
    }
}
