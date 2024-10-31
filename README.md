# **Super Mario Bros - TDP 2024 - UNS**

## 1. Arquitectura General del Juego

### **Estructura General:**
Programado con:
- [Java](https://www.java.com/)

El juego sigue el patrón Modelo-Vista-Controlador (MVC), donde:
- **Modelo**: contiene todas las entidades del juego (`Mario`, enemigos, potenciadores, plataformas).
- **Vista**: se basa en una interfaz con paneles (como `PanelPantallaMapa`, `PanelPantallaPrincipal`, etc.).
- **Controlador**: hay varios controladores para diferentes aspectos del juego, como `ControladorDeVistas`, `ControladorMovimiento`, `ControladorEntidades`.

Además, se sigue un Modelo de 3 Capas:
- **Lógica**:
  - Abarca los componentes lógicos del juego (entidades, `Mapa`, `Juego`, etc.).
  - Maneja las mecánicas principales (movimiento, colisiones, cambios de estado de personajes, etc.).
  - Incluye el procesamiento de las interacciones entre entidades, como cuando Mario interactúa con enemigos o power-ups.
    
- **Vista**:
  - Responsable de la interfaz gráfica y de cómo se muestran los elementos en pantalla.
  - Incluye todos los paneles de usuario (`PanelPantallaMapa`, `PanelPantallaPrincipal`, `PanelPantallaRanking`, etc.).
  - Utiliza un sistema basado en sprites para la representación visual de las entidades del juego.
  - Contiene el control de animaciones, efectos de sonido y visualización de puntajes.
    
- **Datos**:
  - Gestiona la persistencia de información como puntajes y clasificaciones.
  - Implementa el almacenamiento de datos de manera eficiente para reducir el consumo de memoria.
  - Permite la carga y el guardado de niveles, configuración de usuario y resultados de partidas.

### **Componentes Principales:**
1. **`Juego`**: es una clase `singleton` que actúa como el controlador principal de la aplicación.
2. **`Mapa`**: gestiona y organiza todas las entidades del juego.
3. **`Nivel`**: guarda la configuración y el estado del nivel.
4. **Controladores**: varios controladores especializados en diferentes áreas del juego.

## 2. Patrones de Diseño Usados

### **Patrones Creacionales:**
- **Singleton**: 
  - Utilizado en las clases `Juego` y `Mario`
  - Asegura que haya una instancia única.
- **Factory**: 
  - `EntidadesFactory` para crear entidades del juego.
  - `SpritesFactory` para generar sprites.
  - Implementación de dos fábricas concretas: `Dominio1Factory` y `Dominio2Factory`.
- **Builder**: usado en `Nivel.Builder` para armar niveles con parámetros personalizados.

### **Patrones De Comportamiento:**
- **Observer**: 
  - Interfaz `Observer` con implementaciones específicas como `ObserverEntidades`, `ObserverGrafico`, y `ObserverJugador`.
  - Permite que las entidades y la interfaz se mantengan sincronizadas.
- **State**:
  - Estados de Mario: `NormalMarioState`, `SuperMarioState`, `FireMarioState`, `StarMarioState`, y `InvulnerableMarioState`.
  - Estados de Koopa: `NormalKoopaState`, `HiddenKoopaState`, `ProjectileKoopaState`.
- **Visitor**: 
  - `PowerUpVisitor` para manejar interacciones de Mario con los potenciadores.

## 3. Jerarquía de Entidades

### **Clases Base:**
- **Entidad**: clase base de todos los objetos del juego, con posición, sprite, y dimensiones.
- **EntidadMovible**: extiende `Entidad` y añade capacidades de movimiento; es usada por Mario, enemigos y proyectiles.

### **Tipos de Entidades:**
- **Personajes**: `Mario` (jugador) y enemigos (`Goomba`, `KoopaTroopa`, `PiranhaPlant`, `Lakitu`, `Spiny` y `BuzzyBeetle`).
- **Objetos**: potenciadores como `SuperChampi`, `FlorDeFuego`, `Estrella`, y `Moneda`.
- **Plataformas**: bloques como `BloqueSólido`, `LadrilloSólido`, `BloqueDePregunta`, `Tubería`, y `Vacío`.

## 4. Sistemas de Control

- **Control de Movimiento**:
  - `ControladorMovimiento`: administra los hilos de movimiento, controla los FPS, y maneja el movimiento de Mario y enemigos.
- **Sistema de Colisiones**:
  - `Colisionador`: gestiona las colisiones y las interacciones entre entidades, y maneja de forma especial las colisiones de Mario.
- **Control de Estado del Juego**:
  - `ControladorNivel`: controla la progresión del nivel.
  - `ControladorRanking`: maneja puntajes y clasificaciones.
  - `ControladorEntidades`: gestiona el comportamiento de las entidades.

## 5. Interfaz de Usuario

### **Gestión de Pantallas:**
- Paneles especializados como:
  - `PanelPantallaPrincipal`: menú principal.
  - `PanelPantallaMapa`: vista del juego.
  - `PanelPantallaRanking`: clasificación.
  - `PanelPantallaAyuda`: ayuda.
  - `PanelPantallaModoDeJuego`: selección donde se selecciona el modo de juego.

### **Componentes Visuales:**
- Renderizado con sprites, capas de paneles para UI compleja, visualización de puntajes y estado del juego.

## 6. Sistema de Sonido
La clase `Sonido` maneja la música de fondo, efectos de sonido y opciones para activar/desactivar el sonido.

## 7. Sistemas del Juego

- **Sistema de Puntaje**:
  - La interfaz `SistemaPuntuacion` y `ControladorPuntuacionMario` gestionan el puntaje y el conteo de monedas.
  
- **Sistema de Vidas**:
  - La interfaz `SistemaVidas` y `ControladorVidasMario` gestionan las vidas del jugador y condiciones para el fin del juego.

## 8. Características de Extensibilidad

- **Múltiples Temas**: soporte para diferentes temas visuales usando el patrón Factory y sprites extensibles.
- **Generación de Niveles**: un sistema de niveles flexible que permite diferentes configuraciones de niveles.
- **Sistema de Entidades**: una jerarquía extensible, lo que facilita agregar nuevos enemigos, potenciadores y plataformas.

## 9. Consideraciones Técnicas

- **Modelo de Hilos**: varios hilos para:
   - Movimiento de Mario
   - Movimiento de los enemigos 
   - Actualización de la información.
   - 
- **Gestión de Recursos**: carga y almacenamiento de sprites, gestión de sonido y un sistema de ranking persistente. 

