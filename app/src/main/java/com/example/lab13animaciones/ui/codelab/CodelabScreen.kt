package com.example.lab13animaciones.ui.codelab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13animaciones.ui.Etiqueta
import com.example.lab13animaciones.ui.PantallaBase
import com.example.lab13animaciones.ui.theme.AzulNeon
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.RosaNeon
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario
import com.example.lab13animaciones.ui.theme.VerdeNeon
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

private enum class PestanaCodelab(val titulo: String) { Home("HOME"), Work("WORK") }

@Composable
fun PantallaCodelab(onVolver: () -> Unit) {
    var pestana by rememberSaveable { mutableStateOf(PestanaCodelab.Home) }
    var expandido by rememberSaveable { mutableStateOf(false) }
    var mostrarError by rememberSaveable { mutableStateOf(false) }
    var fabExtendido by rememberSaveable { mutableStateOf(false) }
    val pendientes = remember {
        mutableStateListOf("Estudiar animaciones", "Entregar laboratorio", "Ordenar apuntes")
    }

    val fondo by animateColorAsState(
        targetValue = if (pestana == PestanaCodelab.Home) Color(0xFF10233A) else Color(0xFF0F2B26),
        animationSpec = tween(700),
        label = "fondoCodelab"
    )

    PantallaBase(
        titulo = "+3 · Codelab de animaciones",
        subtitulo = "AnimationCodelab: Home / Work",
        color = AzulNeon,
        onVolver = onVolver
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(fondo)
                .border(1.dp, AzulNeon.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val anchoPestana = maxWidth / 2
                val transicion = updateTransition(targetState = pestana, label = "pestanas")
                val izquierda by transicion.animateDp(label = "indicadorIzquierda") { actual ->
                    if (actual == PestanaCodelab.Home) 0.dp else anchoPestana
                }
                val colorIndicador by transicion.animateColor(label = "indicadorColor") { actual ->
                    if (actual == PestanaCodelab.Home) AzulNeon else VerdeNeon
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        PestanaCodelab.entries.forEach { opcion ->
                            val activa = opcion == pestana
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { pestana = opcion }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = opcion.titulo,
                                    color = if (activa) colorIndicador else TextoSecundario,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    letterSpacing = 1.1.sp
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .offset(x = izquierda)
                            .width(anchoPestana)
                            .height(3.dp)
                            .clip(RoundedCornerShape(50))
                            .background(colorIndicador)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = if (pestana == PestanaCodelab.Home) {
                    "Home · Tu resumen de hoy"
                } else {
                    "Work · Pendientes de la oficina"
                },
                color = TextoClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "El fondo cambió con animateColorAsState y el indicador se movió con updateTransition.",
                color = TextoSecundario,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.06f))
                    .clickable { expandido = !expandido }
                    .animateContentSize(animationSpec = spring(stiffness = Spring.StiffnessMediumLow))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tema de la semana",
                        color = TextoClaro,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (expandido) "−" else "+",
                        color = if (pestana == PestanaCodelab.Home) AzulNeon else VerdeNeon,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                if (expandido) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "animateContentSize anima la altura del contenedor cuando aparece contenido " +
                            "nuevo, sin cálculos manuales. Es la misma técnica que usan las tarjetas " +
                            "expandibles de Material.",
                        color = TextoSecundario,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            FilaCargando()

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = mostrarError,
                    enter = slideInVertically(tween(300)) { altura -> -altura } + fadeIn(tween(300)),
                    exit = slideOutVertically(tween(200)) { altura -> -altura } + fadeOut(tween(200))
                ) {
                    Text(
                        text = "La función Edit no está disponible",
                        color = RosaNeon,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(RosaNeon)
                        .clickable {
                            fabExtendido = !fabExtendido
                            mostrarError = true
                        }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "✎", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    AnimatedVisibility(visible = fabExtendido) {
                        Text(
                            text = "  EDIT",
                            color = FondoOscuro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))
            Etiqueta("DESLIZA PARA ELIMINAR", TextoSecundario)
            Spacer(Modifier.height(8.dp))

            if (pendientes.isEmpty()) {
                Text(
                    text = "Todo al día ✓",
                    color = VerdeNeon,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                pendientes.forEach { tarea ->
                    key(tarea) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .deslizarParaDescartar { pendientes.remove(tarea) }
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.06f))
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (pestana == PestanaCodelab.Home) AzulNeon else VerdeNeon)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(text = tarea, color = TextoClaro, fontSize = 13.sp)
                            Spacer(Modifier.weight(1f))
                            Text(text = "→", color = TextoSecundario, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Este bloque replica las animaciones del codelab oficial: color de fondo animado, " +
                "indicador con updateTransition, tarjeta con animateContentSize, mensaje con " +
                "slideInVertically, carga con rememberInfiniteTransition y descarte con Animatable.",
            color = TextoSecundario,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
private fun FilaCargando() {
    val infinito = rememberInfiniteTransition(label = "cargando")
    val alfa by infinito.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "alfaCargando"
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(AzulNeon.copy(alpha = alfa))
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = "Actualizando el clima...",
            color = TextoSecundario,
            fontSize = 12.sp
        )
    }
}

private fun Modifier.deslizarParaDescartar(onDescartar: () -> Unit): Modifier = composed {
    val desplazamiento = remember { Animatable(0f) }
    val alcance = rememberCoroutineScope()

    this
        .offset { IntOffset(desplazamiento.value.roundToInt(), 0) }
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    alcance.launch {
                        if (abs(desplazamiento.value) > 140.dp.toPx()) {
                            val destino = if (desplazamiento.value > 0) size.width.toFloat() else -size.width.toFloat()
                            desplazamiento.animateTo(destino, tween(250))
                            onDescartar()
                        } else {
                            desplazamiento.animateTo(0f, spring(dampingRatio = 0.5f))
                        }
                    }
                },
                onHorizontalDrag = { cambio, delta ->
                    cambio.consume()
                    alcance.launch {
                        desplazamiento.snapTo(desplazamiento.value + delta)
                    }
                }
            )
        }
}
