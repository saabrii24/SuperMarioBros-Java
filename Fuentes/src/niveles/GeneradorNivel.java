package niveles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import fabricas.EntidadesFactory;
import logica.Mapa;

/**
 * Permite parsear el contenido de un archivo de texto para generar un nivel de Super Mario Bros.
 */
public class GeneradorNivel {

    public static Nivel cargar_nivel_y_mapa(InputStream nombreArchivo, EntidadesFactory generador, Mapa mapa) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(nombreArchivo))) {

            // Variables para las características del nivel
            int nivel = 1;
            int posicion_en_x_inicial = 0;
            int posicion_en_y_inicial = 0;
            int tiempo = 0;
            int mario_posicion_en_x = 0;
            int mario_posicion_en_y = 0;
            
            
            String linea;
            while ((linea = br.readLine()) != null) {
                // Ignorar líneas de comentario
                if (linea.startsWith("#")) {
                    continue;
                }

                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    String clave = partes[0].trim();
                    String valor = partes[1].trim();

                    switch (clave) {
                        case "Nivel":
                            nivel = Integer.parseInt(valor);
                            break;
                        case "XInicial":
                        	posicion_en_x_inicial = Integer.parseInt(valor);
                            break;
                        case "Yinicial":
                        	posicion_en_y_inicial = Integer.parseInt(valor);
                            break;
                        case "Tiempo":
                            tiempo = Integer.parseInt(valor);
                            break;
                        case "Mario":
                            String[] marioPos = valor.split(",");
                            mario_posicion_en_x = Integer.parseInt(marioPos[0].trim());
                            mario_posicion_en_y = Integer.parseInt(marioPos[1].trim());
                            mapa.agregar_entidad(generador.get_mario(mario_posicion_en_x, mario_posicion_en_y));
                            break;
                        case "Mapa":
                            // Implementar la lógica para la creación del mapa si es necesario
                            break;
                        default:
                            // Procesar otras entidades como enemigos, power-ups y plataformas
                            procesar_entidad(clave, valor, generador, mapa);
                            break;
                    }
                }
            }

            // Crear y devolver el nivel con las características y el mapa cargados
            return new Nivel.Builder()
                    .set_nivel_actual(nivel)
                    .set_posicion_en_x_inicial(posicion_en_x_inicial)
                    .set_posicion_en_y_inicial(posicion_en_y_inicial)
                    .set_tiempo_limite(tiempo)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Procesa la creación de una entidad basada en su tipo y su posición.
     *
     * @param tipoEntidad Tipo de entidad a crear (ej. Goomba, Koopa, Estrella, etc.).
     * @param valor Coordenadas de la entidad en formato "x, y".
     * @param generador Generador de entidades (SpritesFactory).
     * @param mapa Mapa del nivel.
     */
    private static void procesar_entidad(String tipo_de_entidad, String valor, EntidadesFactory generador, Mapa mapa) {
        String[] coordenadas = valor.split(",");
        int x = Integer.parseInt(coordenadas[0].trim());
        int y = Integer.parseInt(coordenadas[1].trim());
        /**
        for (int x = 0; x < fondo.get_ancho(); x++) {
            for (int y = 0; y < fondo.get_alto(); y++) {
         **/
		        switch (tipo_de_entidad) {
		            case "Goomba":
		                mapa.agregar_entidad(generador.get_goomba(x, y));
		                break;
		            case "Koopa":
		                mapa.agregar_entidad(generador.get_koopa_troopa(x, y));
		                break;
		            case "Spiny":
		                mapa.agregar_entidad(generador.get_spiny(x, y));
		                break;
		            case "Lakitu":
		                mapa.agregar_entidad(generador.get_lakitu(x, y));
		                break;
		            case "Piranha":
		                mapa.agregar_entidad(generador.get_piranha_plant(x, y));
		                break;
		            case "Buzzy":
		                mapa.agregar_entidad(generador.get_buzzy_beetle(x, y));
		                break;
		            case "Estrella":
		                mapa.agregar_entidad(generador.get_estrella(x, y));
		                break;
		            case "BloqueSolido":
		                mapa.agregar_entidad(generador.get_bloque_solido(x, y));
		                break;
		            case "LadrilloSolido":
		                mapa.agregar_entidad(generador.get_ladrillo_solido(x, y));
		                break;
		            case "BloqueDePregunta":
		                mapa.agregar_entidad(generador.get_bloque_de_pregunta(x, y));
		                break;
		            default:
		                
		                break;
		        }
            }
        //}
    //}
}
