package com.example.lab13animaciones.ui.ejercicios

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13animaciones.ui.BotonPrincipal
import com.example.lab13animaciones.ui.ChipOpcion
import com.example.lab13animaciones.ui.Etiqueta
import com.example.lab13animaciones.ui.PantallaBase
import com.example.lab13animaciones.ui.TarjetaDemo
import com.example.lab13animaciones.ui.theme.AzulNeon
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario
import com.example.lab13animaciones.ui.theme.VerdeNeon
import com.example.lab13animaciones.ui.theme.VioletaNeon

@Composable
fun PantallaEjercicio2(onVolver: () -> Unit) {
    var esVerde by rememberSaveable { mutableStateOf(false) }
    var usarSpring by rememberSaveable { mutableStateOf(false) }
    var animar by rememberSaveable { mutableStateOf(true) }

    val colorObjetivo = if (esVerde) VerdeNeon else AzulNeon
    val nombreColor = if (esVerde) "Verde neón" else "Azul neón"

    val colorAnimado by animateColorAsState(
        targetValue = colorObjetivo,
        animationSpec = if (usarSpring) {
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        } else {
            tween(durationMillis = 900)
        },
        label = "colorFondo"
    )
    val colorMostrado = if (animar) colorAnimado else colorObjetivo

    PantallaBase(
        titulo = "02 · animateColorAsState",
        subtitulo = "Transición de color con tween y spring",
        color = VerdeNeon,
        onVolver = onVolver
    ) {
        TarjetaDemo(color = VerdeNeon) {
            Etiqueta("FONDO ANIMADO", VerdeNeon)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Cambia entre azul y verde y compara la curva de animación tween con la de spring.",
                color = TextoSecundario,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            Spacer(Modifier.height(18.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(colorMostrado),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = nombreColor,
                            color = FondoOscuro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = if (animar) "transición suave" else "cambio instantáneo",
                            color = FondoOscuro.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ChipOpcion(
                    texto = "Tween 900 ms",
                    seleccionado = !usarSpring,
                    color = VerdeNeon,
                    onClick = { usarSpring = false }
                )
                ChipOpcion(
                    texto = "Spring",
                    seleccionado = usarSpring,
                    color = VerdeNeon,
                    onClick = { usarSpring = true }
                )
            }

            Spacer(Modifier.height(16.dp))
            BotonPrincipal(
                texto = if (esVerde) "Cambiar a azul" else "Cambiar a verde",
                color = VerdeNeon,
                modifier = Modifier.fillMaxWidth()
            ) {
                esVerde = !esVerde
            }

            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Usar animación",
                        color = TextoClaro,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (animar) "animateColorAsState activo" else "color aplicado de inmediato",
                        color = TextoSecundario,
                        fontSize = 11.sp
                    )
                }
                Switch(
                    checked = animar,
                    onCheckedChange = { animar = it },
                    colors = SwitchDefaults.colors(checkedTrackColor = VerdeNeon)
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Etiqueta("CÓMO FUNCIONA", VerdeNeon)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "animateColorAsState devuelve un State<Color> que se interpola entre el color anterior y el " +
                "nuevo. Con tween el cambio dura un tiempo fijo y avanza con una curva (por defecto FastOutSlowIn). " +
                "Con spring el color se acerca al destino con una leve oscilación, lo que da una sensación más " +
                "orgánica. Si se apaga la animación, el mismo valor se asigna directamente y el cambio es seco.",
            color = TextoSecundario,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MuestraColor(color = AzulNeon, texto = "Azul")
            MuestraColor(color = VerdeNeon, texto = "Verde")
            MuestraColor(color = VioletaNeon, texto = "Violeta")
        }
    }
}

@Composable
private fun MuestraColor(color: Color, texto: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(color)
        )
        Spacer(Modifier.height(4.dp))
        Text(text = texto, color = TextoSecundario, fontSize = 10.sp)
    }
}
