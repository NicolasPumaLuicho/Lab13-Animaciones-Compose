package com.example.lab13animaciones.ui.ejercicios

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.lab13animaciones.ui.theme.AmbarNeon
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario

@Composable
fun PantallaEjercicio3(onVolver: () -> Unit) {
    var expandido by rememberSaveable { mutableStateOf(false) }
    var fondoPrimero by rememberSaveable { mutableStateOf(false) }

    val tamano by animateDpAsState(
        targetValue = if (expandido) 150.dp else 80.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMediumLow),
        label = "tamano"
    )
    val desplazamientoX by animateDpAsState(
        targetValue = if (expandido) 110.dp else 0.dp,
        animationSpec = tween(durationMillis = 800),
        label = "offsetX"
    )
    val desplazamientoY by animateDpAsState(
        targetValue = if (expandido) 100.dp else 0.dp,
        animationSpec = tween(durationMillis = 800),
        label = "offsetY"
    )

    PantallaBase(
        titulo = "03 · Tamaño y posición",
        subtitulo = "animateDpAsState + Modifier.offset",
        color = AmbarNeon,
        onVolver = onVolver
    ) {
        TarjetaDemo(color = AmbarNeon) {
            Etiqueta("ESCENARIO ANIMADO", AmbarNeon)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "El cuadro cambia de tamaño con un spring y se desplaza con un tween. Cambia el orden " +
                    "de los modificadores para ver la diferencia.",
                color = TextoSecundario,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Brush.verticalGradient(listOf(FondoOscuro, Color(0xFF1A1608))))
                    .padding(16.dp)
            ) {
                val fondo = Brush.linearGradient(listOf(AmbarNeon, Color(0xFFFFE29A)))
                val forma = RoundedCornerShape(22.dp)
                val modificador = if (fondoPrimero) {
                    Modifier
                        .size(tamano)
                        .background(fondo, forma)
                        .offset(desplazamientoX, desplazamientoY)
                } else {
                    Modifier
                        .offset(desplazamientoX, desplazamientoY)
                        .size(tamano)
                        .background(fondo, forma)
                }

                Box(
                    modifier = modificador,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${tamano.value.toInt()} dp",
                        color = FondoOscuro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ChipOpcion(
                    texto = "offset → fondo",
                    seleccionado = !fondoPrimero,
                    color = AmbarNeon,
                    onClick = { fondoPrimero = false }
                )
                ChipOpcion(
                    texto = "fondo → offset",
                    seleccionado = fondoPrimero,
                    color = AmbarNeon,
                    onClick = { fondoPrimero = true }
                )
            }

            Spacer(Modifier.height(14.dp))
            BotonPrincipal(
                texto = if (expandido) "Volver al origen" else "Mover y escalar",
                color = AmbarNeon,
                modifier = Modifier.fillMaxWidth()
            ) {
                expandido = !expandido
            }
        }

        Spacer(Modifier.height(16.dp))
        Etiqueta("CÓMO FUNCIONA", AmbarNeon)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "animateDpAsState interpola valores en dp, por eso funciona para tamaño y desplazamiento. " +
                "Modifier.offset mueve el componente sin alterar el espacio que ocupa en el layout. El orden " +
                "importa: si background va antes que offset, el fondo se dibuja en la posición original y solo " +
                "el contenido se desplaza; si offset va primero, el bloque completo se mueve junto.",
            color = TextoSecundario,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
