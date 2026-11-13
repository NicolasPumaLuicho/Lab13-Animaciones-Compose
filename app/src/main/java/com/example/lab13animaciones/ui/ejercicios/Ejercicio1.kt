package com.example.lab13animaciones.ui.ejercicios

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13animaciones.ui.BotonPrincipal
import com.example.lab13animaciones.ui.Etiqueta
import com.example.lab13animaciones.ui.PantallaBase
import com.example.lab13animaciones.ui.TarjetaDemo
import com.example.lab13animaciones.ui.theme.AzulNeon
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario

@Composable
fun PantallaEjercicio1(onVolver: () -> Unit) {
    var visible by rememberSaveable { mutableStateOf(true) }

    PantallaBase(
        titulo = "01 · AnimatedVisibility",
        subtitulo = "Entrada y salida con fadeIn + fadeOut",
        color = AzulNeon,
        onVolver = onVolver
    ) {
        TarjetaDemo(color = AzulNeon) {
            Etiqueta("CUADRO DE NOTIFICACIÓN", AzulNeon)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Presiona el botón y observa cómo el cuadro aparece y desaparece con una transición suave.",
                color = TextoSecundario,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Brush.verticalGradient(listOf(FondoOscuro, androidx.compose.ui.graphics.Color(0xFF101B3A)))),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(700)) +
                        expandVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            expandFrom = Alignment.Top
                        ),
                    exit = fadeOut(animationSpec = tween(350)) +
                        shrinkVertically(
                            animationSpec = tween(350),
                            shrinkTowards = Alignment.Top
                        )
                ) {
                    TarjetaNotificacion()
                }
            }

            Spacer(Modifier.height(16.dp))
            BotonPrincipal(
                texto = if (visible) "Ocultar cuadro" else "Mostrar cuadro",
                color = AzulNeon,
                modifier = Modifier.fillMaxWidth()
            ) {
                visible = !visible
            }
        }

        Spacer(Modifier.height(16.dp))
        Etiqueta("CÓMO FUNCIONA", AzulNeon)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "AnimatedVisibility recibe un valor booleano. Cuando cambia, ejecuta la especificación de " +
                "entrada o de salida en lugar de quitar el elemento de golpe. fadeIn y fadeOut controlan la " +
                "opacidad, mientras que expandVertically y shrinkVertically animan la altura para que el " +
                "contenido de abajo se acomode poco a poco.",
            color = TextoSecundario,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun TarjetaNotificacion() {
    Row(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(ColorTarjetaNotificacion)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(AzulNeon),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "✓", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = "¡Animación completada!",
                color = TextoClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "El contenido apareció con fade + expansión.",
                color = TextoSecundario,
                fontSize = 12.sp
            )
        }
    }
}

private val ColorTarjetaNotificacion = androidx.compose.ui.graphics.Color(0xFF17274A)
