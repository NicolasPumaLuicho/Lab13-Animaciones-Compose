package com.example.lab13animaciones.ui.ejercicios

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13animaciones.ui.BotonPrincipal
import com.example.lab13animaciones.ui.ChipOpcion
import com.example.lab13animaciones.ui.Etiqueta
import com.example.lab13animaciones.ui.PantallaBase
import com.example.lab13animaciones.ui.TarjetaDemo
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario
import com.example.lab13animaciones.ui.theme.VerdeNeon
import com.example.lab13animaciones.ui.theme.VioletaNeon
import kotlinx.coroutines.delay

private enum class EstadoContenido(val etiqueta: String) {
    Cargando("Cargando"),
    Contenido("Contenido"),
    Error("Error")
}

@Composable
fun PantallaEjercicio4(onVolver: () -> Unit) {
    var estado by rememberSaveable { mutableStateOf(EstadoContenido.Cargando) }
    var recarga by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(recarga) {
        if (recarga > 0) {
            estado = EstadoContenido.Cargando
            delay(1600)
            estado = EstadoContenido.Contenido
        }
    }

    PantallaBase(
        titulo = "04 · AnimatedContent",
        subtitulo = "Tres estados con transiciones personalizadas",
        color = VioletaNeon,
        onVolver = onVolver
    ) {
        TarjetaDemo(color = VioletaNeon) {
            Etiqueta("PANTALLA DE ESTADOS", VioletaNeon)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "El contenido cambia con fade y deslizamiento. Cada estado tiene su propio tiempo de entrada y salida.",
                color = TextoSecundario,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Brush.verticalGradient(listOf(FondoOscuro, Color(0xFF1B1233))))
                    .padding(16.dp)
            ) {
                AnimatedContent(
                    targetState = estado,
                    transitionSpec = {
                        (fadeIn(tween(500)) + slideInHorizontally(tween(500)) { ancho -> ancho / 3 }) togetherWith
                            (fadeOut(tween(250)) + slideOutHorizontally(tween(250)) { ancho -> -ancho / 3 })
                    },
                    label = "estadoContenido"
                ) { actual ->
                    when (actual) {
                        EstadoContenido.Cargando -> EstadoCargando()
                        EstadoContenido.Contenido -> EstadoConContenido()
                        EstadoContenido.Error -> EstadoError()
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ChipOpcion(
                    texto = "Cargando",
                    seleccionado = estado == EstadoContenido.Cargando,
                    color = VioletaNeon,
                    onClick = { estado = EstadoContenido.Cargando }
                )
                ChipOpcion(
                    texto = "Contenido",
                    seleccionado = estado == EstadoContenido.Contenido,
                    color = VioletaNeon,
                    onClick = { estado = EstadoContenido.Contenido }
                )
                ChipOpcion(
                    texto = "Error",
                    seleccionado = estado == EstadoContenido.Error,
                    color = VioletaNeon,
                    onClick = { estado = EstadoContenido.Error }
                )
            }

            Spacer(Modifier.height(14.dp))
            BotonPrincipal(
                texto = "Simular carga automática",
                color = VioletaNeon,
                modifier = Modifier.fillMaxWidth()
            ) {
                recarga += 1
            }
        }

        Spacer(Modifier.height(16.dp))
        Etiqueta("CÓMO FUNCIONA", VioletaNeon)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "AnimatedContent recibe un estado y anima el reemplazo del contenido dentro del mismo espacio. " +
                "En transitionSpec se define qué entra y qué sale: aquí se combinó fadeIn con slideInHorizontally " +
                "en 500 ms para la entrada, y fadeOut con slideOutHorizontally en 250 ms para la salida. Así el " +
                "usuario percibe que la pantalla fluye hacia el nuevo estado en lugar de parpadear.",
            color = TextoSecundario,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun EstadoCargando() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = VioletaNeon,
            strokeWidth = 4.dp,
            modifier = Modifier.size(54.dp)
        )
        Spacer(Modifier.height(18.dp))
        Text(
            text = "Cargando datos...",
            color = TextoClaro,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        )
        Text(
            text = "Conectando con el servidor",
            color = TextoSecundario,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun EstadoConContenido() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(VerdeNeon),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✓", color = FondoOscuro, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Datos cargados",
                color = TextoClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.height(14.dp))
        listOf(
            "Promedio de notas" to "17.5",
            "Cursos activos" to "06",
            "Entregas pendientes" to "02"
        ).forEach { (titulo, valor) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(VioletaNeon.copy(alpha = 0.08f))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = titulo, color = TextoSecundario, fontSize = 13.sp)
                Text(text = valor, color = TextoClaro, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun EstadoError() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF6B81).copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "!",
                color = Color(0xFFFF6B81),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
        Spacer(Modifier.height(14.dp))
        Text(
            text = "No se pudo cargar",
            color = TextoClaro,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = "Revisa tu conexión e inténtalo otra vez.",
            color = TextoSecundario,
            fontSize = 12.sp
        )
    }
}
