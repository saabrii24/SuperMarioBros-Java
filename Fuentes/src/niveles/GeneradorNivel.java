package niveles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import entidades.mario.Mario;
import fabricas.EntidadesFactory;
import logica.Mapa;

/**
 * Permite parsear el contenido de un archivo de texto para generar un nivel del Super Mario Bros.
 */
public class GeneradorNivel {

	public static Nivel cargar_nivel_y_mapa(InputStream nombreArchivo, EntidadesFactory generador, Mapa mapa) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(nombreArchivo))) {

			// Variables para las características del nivel
			int nivel = 1;
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
					case "Tiempo":
						tiempo = Integer.parseInt(valor);
						break;
					case "Mario":
						String[] marioPos = valor.split(",");
						mario_posicion_en_x = Integer.parseInt(marioPos[0].trim());
						mario_posicion_en_y = Integer.parseInt(marioPos[1].trim());
						mapa.agregar_mario(Mario.get_instancia());
						Mario.get_instancia().set_posicion_en_x(mario_posicion_en_x);
						Mario.get_instancia().set_posicion_en_y(mario_posicion_en_y);
						break;
					case "Mapa":
						break;
					default:
						procesar_entidades(clave, valor, generador, mapa);
						break;
					}
				}
			}

			// Crear y devolver el nivel con las características y el mapa cargados
			return new Nivel.Builder().set_nivel_actual(nivel).set_posicion_en_x_inicial(mario_posicion_en_x)
					.set_posicion_en_y_inicial(mario_posicion_en_y).set_tiempo_limite(tiempo).build();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Procesa la creación de una entidad basada en su tipo y su posición.
	 *
	 * @param tipoEntidad Tipo de entidad a crear (ej. Goomba, Koopa, Estrella, etc.).
	 * @param valor       Coordenadas de la entidad en formato "x, y".
	 * @param generador   Generador de entidades (SpritesFactory).
	 * @param mapa        Mapa del nivel.
	 */
	private static void procesar_entidades(String tipo_de_entidad, String valor, EntidadesFactory generador, Mapa mapa) {
		String[] coordenadas = valor.split(",");
		if (coordenadas.length < 2) {
			System.err.println("Error: Las coordenadas para " + tipo_de_entidad + " no están bien formadas: " + valor);
			return;
		}
		int x = Integer.parseInt(coordenadas[0].trim());
		int y = Integer.parseInt(coordenadas[1].trim());

		switch (tipo_de_entidad) {
		//ENEMIGOS
			case "Goomba":
				mapa.agregar_goomba(generador.get_goomba(x, y));
				break;
			case "Koopa":
				mapa.agregar_koopa_troopa(generador.get_koopa_troopa(x, y));
				break;
			case "Spiny":
				mapa.agregar_spiny(generador.get_spiny(x, y));
				break;
			case "Lakitu":
				mapa.agregar_lakitu(generador.get_lakitu(x, y));
				break;
			case "Piranha":
				mapa.agregar_piranha_plant(generador.get_piranha_plant(x, y));
				break;
			case "Buzzy":
				mapa.agregar_buzzy_beetle(generador.get_buzzy_beetle(x, y));
				break;
				
		//PLATAFORMAS
			case "BloqueSolido":
				mapa.agregar_bloque_solido(generador.get_bloque_solido(x, y));
				break;
			case "LadrilloSolido":
				mapa.agregar_ladrillo_solido(generador.get_ladrillo_solido(x, y));
				break;
			case "BloqueDePreguntaConMoneda":
				mapa.agregar_bloque_de_pregunta(generador.get_bloque_de_pregunta(x, y, generador.get_moneda(x, y)));
				break;
			case "BloqueDePreguntaConChampiVerde" :
				mapa.agregar_bloque_de_pregunta(generador.get_bloque_de_pregunta(x, y, generador.get_champi_verde(x, y)));
				break;
			case "BloqueDePreguntaConFlorDeFuego" :
				mapa.agregar_bloque_de_pregunta(generador.get_bloque_de_pregunta(x, y, generador.get_flor_de_fuego(x, y)));
				break;
			case "BloqueDePreguntaConSuperChampi" : 
				mapa.agregar_bloque_de_pregunta(generador.get_bloque_de_pregunta(x, y, generador.get_super_champi(x, y)));
				break;
			case "BloqueDePreguntaConEstrella" :
				mapa.agregar_bloque_de_pregunta(generador.get_bloque_de_pregunta(x, y, generador.get_estrella(x, y)));
				break;
			case "Vacio":
				mapa.agregar_vacio(generador.get_vacio(x, y));
				break;
			case "Tuberia":
				mapa.agregar_tuberia(generador.get_tuberias(x, y));
				break;
				
		//POWERUPS
			case "Moneda":
				mapa.agregar_powerup(generador.get_moneda(x, y));
				break;
			case "Estrella":
				mapa.agregar_powerup(generador.get_estrella(x, y));
				break;
			case "ChampiVerde":
				mapa.agregar_powerup(generador.get_champi_verde(x, y));
				break;
			case "SuperChampi":
				mapa.agregar_powerup(generador.get_super_champi(x, y));
				break;	
			case "FlorDeFuego":
				mapa.agregar_powerup(generador.get_flor_de_fuego(x, y));
				break;
			default:
				break;
		}
	}
}