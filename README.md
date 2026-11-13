# Laboratorio 13 — Animaciones en Jetpack Compose

Aplicación Android desarrollada para el Laboratorio 13 del curso **Programación en Móviles** (Tecsup). Reúne los cuatro ejercicios de animaciones, un prototipo de videojuego y una pantalla con las animaciones del codelab oficial.

## Ejercicios

| Pantalla | Tema | APIs principales |
| --- | --- | --- |
| 01 | Visibilidad animada | `AnimatedVisibility`, `fadeIn`, `fadeOut`, `expandVertically` |
| 02 | Color animado | `animateColorAsState`, `tween`, `spring` |
| 03 | Tamaño y posición | `animateDpAsState`, `Modifier.offset`, orden de modificadores |
| 04 | Contenido animado | `AnimatedContent`, transiciones con fade y slide |
| Juego | Salto Estelar | animaciones combinadas: nave, estrellas, puntaje, vidas y game over |
| Codelab | AnimationCodelab | `updateTransition`, `animateContentSize`, `rememberInfiniteTransition`, `Animatable` + swipe |

## Estructura

```
app/src/main/java/com/example/lab13animaciones/
├── MainActivity.kt
├── ui/
│   ├── Lab13App.kt          # navegación y pantalla de inicio
│   ├── Controles.kt         # componentes reutilizables
│   ├── ejercicios/          # ejercicios 1 a 4
│   ├── juego/               # prototipo Salto Estelar
│   └── codelab/             # pantalla del codelab
└── ui/theme/                # tema oscuro de la app
```

## Cómo ejecutar

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle (AGP 9.4.1, Kotlin 2.2.10, Compose BOM 2026.02.01).
3. Ejecutar el módulo `app` en un emulador o dispositivo con Android 7.0 (API 24) o superior.

Compilación por consola:

```bash
./gradlew assembleDebug
```

## Evidencias

Las capturas de cada pantalla y los videos de funcionamiento se encuentran en la carpeta de entrega:

- `capturas/` — 16 capturas del emulador.
- `videos/` — video por ejercicio, del juego y del codelab.

## Autor

Nicolás Puma Luicho — Tecsup
