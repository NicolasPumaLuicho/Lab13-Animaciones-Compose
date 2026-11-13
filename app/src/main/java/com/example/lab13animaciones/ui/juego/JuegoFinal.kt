package com.example.lab13animaciones.ui.juego

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13animaciones.ui.BotonPrincipal
import com.example.lab13animaciones.ui.theme.AmbarNeon
import com.example.lab13animaciones.ui.theme.AzulNeon
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.FondoTarjeta
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario
import com.example.lab13animaciones.ui.theme.VerdeNeon
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private enum class TipoEntidad { Estrella, Meteoro }

private data class Entidad(
    val id: Long,
    val tipo: TipoEntidad,
    val x: Float,
    var y: Float,
    val velocidad: Float
)

private enum class EstadoJuego { Inicio, Jugando, Fin }

private data class EstrellaFondo(val x: Float, val y: Float, val radio: Float)

@Composable
fun PantallaJuego(onVolver: () -> Unit) {
    var estado by remember { mutableStateOf(EstadoJuego.Inicio) }
    var puntaje by remember { mutableIntStateOf(0) }
    var vidas by remember { mutableIntStateOf(3) }
    var objetivoX by rememberSaveable { mutableFloatStateOf(0.5f) }
    var tick by remember { mutableIntStateOf(0) }
    var siguienteId by remember { mutableLongStateOf(0L) }

    val entidades = remember { mutableStateListOf<Entidad>() }
    val estrellasFondo = remember {
        List(55) {
            EstrellaFondo(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radio = 0.6f + Random.nextFloat() * 1.8f
            )
        }
    }

    val jugadorX by animateFloatAsState(
        targetValue = objetivoX,
        animationSpec = spring(dampingRatio = 0.62f, stiffness = Spring.StiffnessMediumLow),
        label = "naveX"
    )

    val destello = remember { Animatable(0f) }
    val explosion = remember { Animatable(0f) }
    var explosionPos by remember { mutableStateOf(Offset(0.5f, 0.5f)) }

    val pulso = rememberInfiniteTransition(label = "pulsoJuego")
    val centelleo by pulso.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(1100), RepeatMode.Reverse),
        label = "centelleo"
    )
    val llama by pulso.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(tween(180), RepeatMode.Reverse),
        label = "llama"
    )

    val coloresFondo = listOf(
        Color(0xFF07102A),
        Color(0xFF1A0B2E),
        Color(0xFF06222B),
        Color(0xFF2A0F1D)
    )
    val fondoJuego by animateColorAsState(
        targetValue = coloresFondo[(puntaje / 100) % coloresFondo.size],
        animationSpec = tween(1200),
        label = "fondoJuego"
    )

    LaunchedEffect(estado) {
        if (estado != EstadoJuego.Jugando) return@LaunchedEffect
        val alcance = this
        var ultimo = 0L
        var acumuladoSpawn = 0f

        while (estado == EstadoJuego.Jugando) {
            withFrameMillis { ahora ->
                val delta = if (ultimo == 0L) 0f else ((ahora - ultimo) / 1000f).coerceAtMost(0.05f)
                ultimo = ahora
                acumuladoSpawn += delta

                if (acumuladoSpawn >= 0.75f) {
                    acumuladoSpawn = 0f
                    val esMeteoro = Random.nextFloat() < if (puntaje >= 40) 0.34f else 0.22f
                    entidades.add(
                        Entidad(
                            id = siguienteId++,
                            tipo = if (esMeteoro) TipoEntidad.Meteoro else TipoEntidad.Estrella,
                            x = 0.1f + Random.nextFloat() * 0.8f,
                            y = 0f,
                            velocidad = if (esMeteoro) 0.34f + Random.nextFloat() * 0.18f
                            else 0.22f + Random.nextFloat() * 0.16f
                        )
                    )
                }

                val iterador = entidades.iterator()
                while (iterador.hasNext()) {
                    val entidad = iterador.next()
                    entidad.y += entidad.velocidad * delta

                    val alcanzada = entidad.y in 0.78f..0.98f &&
                        abs(entidad.x - jugadorX) < 0.13f

                    when {
                        alcanzada && entidad.tipo == TipoEntidad.Estrella -> {
                            iterador.remove()
                            puntaje += 10
                            explosionPos = Offset(entidad.x, entidad.y)
                            alcance.launch {
                                explosion.snapTo(0f)
                                explosion.animateTo(1f, tween(450))
                            }
                        }
                        alcanzada && entidad.tipo == TipoEntidad.Meteoro -> {
                            iterador.remove()
                            vidas -= 1
                            alcance.launch {
                                destello.snapTo(0.5f)
                                destello.animateTo(0f, tween(400))
                            }
                            if (vidas <= 0) {
                                estado = EstadoJuego.Fin
                            }
                        }
                        entidad.y >= 1.05f -> iterador.remove()
                    }
                }
                tick++
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(FondoTarjeta)
                    .border(1.dp, Color(0xFFFF7AB6).copy(alpha = 0.35f), CircleShape)
                    .clickable(onClick = onVolver),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFFFF7AB6),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = "★ Salto Estelar",
                    color = TextoClaro,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Prototipo de juego · animaciones combinadas",
                    color = TextoSecundario,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "PUNTOS",
                    color = TextoSecundario,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
                Spacer(Modifier.width(8.dp))
                AnimatedContent(
                    targetState = puntaje,
                    transitionSpec = {
                        (slideInVertically { -it } + fadeIn(tween(220))) togetherWith
                            (slideOutVertically { it } + fadeOut(tween(180)))
                    },
                    label = "puntaje"
                ) { valor ->
                    Text(
                        text = "$valor",
                        color = AmbarNeon,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "VIDAS",
                    color = TextoSecundario,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
                Spacer(Modifier.width(8.dp))
                AnimatedContent(
                    targetState = vidas,
                    transitionSpec = { fadeIn(tween(180)) togetherWith fadeOut(tween(180)) },
                    label = "vidas"
                ) { cantidad ->
                    Text(
                        text = if (cantidad > 0) "♥".repeat(cantidad) else "—",
                        color = Color(0xFFFF6B81),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(fondoJuego)
                .border(1.dp, AzulNeon.copy(alpha = 0.25f), RoundedCornerShape(24.dp))
                .pointerInput(estado) {
                    detectTapGestures { posicion ->
                        if (estado == EstadoJuego.Jugando) {
                            objetivoX = (posicion.x / size.width).coerceIn(0.08f, 0.92f)
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                tick
                val ancho = size.width
                val alto = size.height

                estrellasFondo.forEach { estrella ->
                    drawCircle(
                        color = Color.White.copy(alpha = 0.12f + 0.28f * centelleo * estrella.radio / 2f),
                        radius = estrella.radio.dp.toPx(),
                        center = Offset(estrella.x * ancho, estrella.y * alto)
                    )
                }

                entidades.forEach { entidad ->
                    val centro = Offset(entidad.x * ancho, entidad.y * alto)
                    if (entidad.tipo == TipoEntidad.Estrella) {
                        drawCircle(AmbarNeon.copy(alpha = 0.22f), 17.dp.toPx(), centro)
                        drawCircle(AmbarNeon, 7.dp.toPx(), centro)
                        drawCircle(Color.White.copy(alpha = 0.9f), 2.5.dp.toPx(), centro)
                    } else {
                        drawCircle(Color(0xFFFF6B81).copy(alpha = 0.22f), 19.dp.toPx(), centro)
                        drawCircle(Color(0xFFFF6B81), 10.dp.toPx(), centro)
                        drawCircle(Color(0xFF7A1E2C), 4.5.dp.toPx(), centro)
                    }
                }

                val naveX = jugadorX * ancho
                val naveY = alto - 52.dp.toPx()
                val tamano = 26.dp.toPx()

                val llamarada = Path().apply {
                    moveTo(naveX - 9.dp.toPx(), naveY + 14.dp.toPx())
                    lineTo(naveX, naveY + 14.dp.toPx() + 18.dp.toPx() * llama)
                    lineTo(naveX + 9.dp.toPx(), naveY + 14.dp.toPx())
                    close()
                }
                drawPath(llamarada, Color(0xFFFFC857).copy(alpha = 0.85f))

                val nave = Path().apply {
                    moveTo(naveX, naveY - tamano)
                    lineTo(naveX - tamano * 0.78f, naveY + 14.dp.toPx())
                    lineTo(naveX + tamano * 0.78f, naveY + 14.dp.toPx())
                    close()
                }
                drawPath(nave, AzulNeon)
                drawCircle(FondoOscuro, 5.dp.toPx(), Offset(naveX, naveY - 1.dp.toPx()))
                drawCircle(VerdeNeon, 2.5.dp.toPx(), Offset(naveX, naveY - 1.dp.toPx()))

                val progreso = explosion.value
                if (progreso in 0.001f..0.999f) {
                    val centro = Offset(explosionPos.x * ancho, explosionPos.y * alto)
                    repeat(9) { indice ->
                        val angulo = indice * (Math.PI * 2 / 9)
                        val distancia = 8.dp.toPx() + 46.dp.toPx() * progreso
                        drawCircle(
                            color = AmbarNeon.copy(alpha = 1f - progreso),
                            radius = 4.dp.toPx(),
                            center = Offset(
                                centro.x + (cos(angulo) * distancia).toFloat(),
                                centro.y + (sin(angulo) * distancia).toFloat()
                            )
                        )
                    }
                }

                if (destello.value > 0.01f) {
                    drawRect(Color(0xFFFF6B81).copy(alpha = destello.value))
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = estado != EstadoJuego.Jugando,
                enter = fadeIn(tween(350)) + scaleIn(tween(350), initialScale = 0.9f),
                exit = fadeOut(tween(200)) + scaleOut(tween(200), targetScale = 0.92f),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(FondoOscuro.copy(alpha = 0.94f))
                        .border(1.dp, Color(0xFFFF7AB6).copy(alpha = 0.4f), RoundedCornerShape(22.dp))
                        .padding(horizontal = 26.dp, vertical = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (estado == EstadoJuego.Inicio) {
                        Text(
                            text = "SALTO ESTELAR",
                            color = Color(0xFFFF7AB6),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            letterSpacing = 1.5.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Atrapa las estrellas ★ y esquiva los meteoros.",
                            color = TextoSecundario,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Toca la pantalla o usa los botones para mover la nave.",
                            color = TextoSecundario,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                        Spacer(Modifier.height(18.dp))
                        BotonPrincipal(texto = "JUGAR", color = Color(0xFFFF7AB6)) {
                            puntaje = 0
                            vidas = 3
                            entidades.clear()
                            objetivoX = 0.5f
                            estado = EstadoJuego.Jugando
                        }
                    } else {
                        Text(
                            text = "GAME OVER",
                            color = Color(0xFFFF6B81),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            letterSpacing = 1.5.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Puntaje final: $puntaje",
                            color = TextoClaro,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = if (puntaje >= 100) "¡Buen piloto!" else "Las estrellas te esperan.",
                            color = TextoSecundario,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(18.dp))
                        BotonPrincipal(texto = "REINTENTAR", color = Color(0xFFFF7AB6)) {
                            puntaje = 0
                            vidas = 3
                            entidades.clear()
                            objetivoX = 0.5f
                            estado = EstadoJuego.Jugando
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotonFlecha(texto = "◀") {
                objetivoX = (objetivoX - 0.16f).coerceIn(0.08f, 0.92f)
            }
            Text(
                text = "Recoge ★ (+10) · evita los meteoros",
                color = TextoSecundario,
                fontSize = 11.sp
            )
            BotonFlecha(texto = "▶") {
                objetivoX = (objetivoX + 0.16f).coerceIn(0.08f, 0.92f)
            }
        }
    }
}

@Composable
private fun BotonFlecha(texto: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(FondoTarjeta)
            .border(1.dp, AzulNeon.copy(alpha = 0.35f), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = texto, color = AzulNeon, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}
