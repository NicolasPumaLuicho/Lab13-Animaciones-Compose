package com.example.lab13animaciones.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13animaciones.ui.codelab.PantallaCodelab
import com.example.lab13animaciones.ui.ejercicios.PantallaEjercicio1
import com.example.lab13animaciones.ui.ejercicios.PantallaEjercicio2
import com.example.lab13animaciones.ui.ejercicios.PantallaEjercicio3
import com.example.lab13animaciones.ui.ejercicios.PantallaEjercicio4
import com.example.lab13animaciones.ui.juego.PantallaJuego
import com.example.lab13animaciones.ui.theme.AmbarNeon
import com.example.lab13animaciones.ui.theme.AzulNeon
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.FondoTarjeta
import com.example.lab13animaciones.ui.theme.RosaNeon
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario
import com.example.lab13animaciones.ui.theme.VerdeNeon
import com.example.lab13animaciones.ui.theme.VioletaNeon

enum class Pantalla(val titulo: String) {
    Inicio("Inicio"),
    Ejercicio1("Ejercicio 1"),
    Ejercicio2("Ejercicio 2"),
    Ejercicio3("Ejercicio 3"),
    Ejercicio4("Ejercicio 4"),
    Juego("Juego final"),
    Codelab("Codelab")
}

private data class ItemMenu(
    val pantalla: Pantalla,
    val numero: String,
    val titulo: String,
    val descripcion: String,
    val color: Color,
    val icono: ImageVector
)

private val ITEMS = listOf(
    ItemMenu(
        Pantalla.Ejercicio1,
        "01",
        "Visibilidad animada",
        "AnimatedVisibility con fadeIn y fadeOut",
        AzulNeon,
        Icons.Filled.ArrowForward
    ),
    ItemMenu(
        Pantalla.Ejercicio2,
        "02",
        "Color animado",
        "animateColorAsState con tween y spring",
        VerdeNeon,
        Icons.Filled.ArrowForward
    ),
    ItemMenu(
        Pantalla.Ejercicio3,
        "03",
        "Tamaño y posición",
        "animateDpAsState junto a Modifier.offset",
        AmbarNeon,
        Icons.Filled.ArrowForward
    ),
    ItemMenu(
        Pantalla.Ejercicio4,
        "04",
        "Contenido animado",
        "AnimatedContent entre tres estados",
        VioletaNeon,
        Icons.Filled.ArrowForward
    ),
    ItemMenu(
        Pantalla.Juego,
        "★",
        "Salto Estelar",
        "Prototipo de juego con animaciones combinadas",
        RosaNeon,
        Icons.Filled.ArrowForward
    ),
    ItemMenu(
        Pantalla.Codelab,
        "+3",
        "Codelab de animaciones",
        "updateTransition, animateContentSize y swipe",
        AzulNeon,
        Icons.Filled.ArrowForward
    )
)

@Composable
fun Lab13App() {
    var pantalla by rememberSaveable { mutableStateOf(Pantalla.Inicio) }

    BackHandler(enabled = pantalla != Pantalla.Inicio) {
        pantalla = Pantalla.Inicio
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(FondoOscuro, Color(0xFF0D1730), FondoOscuro)
                )
            )
    ) {
        AnimatedContent(
            targetState = pantalla,
            transitionSpec = { fadeIn(tween(280)) togetherWith fadeOut(tween(180)) },
            label = "navegacion"
        ) { destino ->
            when (destino) {
                Pantalla.Inicio -> PantallaInicio(onAbrir = { pantalla = it })
                Pantalla.Ejercicio1 -> PantallaEjercicio1(onVolver = { pantalla = Pantalla.Inicio })
                Pantalla.Ejercicio2 -> PantallaEjercicio2(onVolver = { pantalla = Pantalla.Inicio })
                Pantalla.Ejercicio3 -> PantallaEjercicio3(onVolver = { pantalla = Pantalla.Inicio })
                Pantalla.Ejercicio4 -> PantallaEjercicio4(onVolver = { pantalla = Pantalla.Inicio })
                Pantalla.Juego -> PantallaJuego(onVolver = { pantalla = Pantalla.Inicio })
                Pantalla.Codelab -> PantallaCodelab(onVolver = { pantalla = Pantalla.Inicio })
            }
        }
    }
}

@Composable
private fun PantallaInicio(onAbrir: (Pantalla) -> Unit) {
    val pulso = rememberInfiniteTransition(label = "pulso")
    val alfaPulso by pulso.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "alfaPulso"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 22.dp,
            end = 22.dp,
            top = 28.dp,
            bottom = 30.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(VerdeNeon.copy(alpha = alfaPulso))
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "PROGRAMACIÓN EN MÓVILES · S13",
                    color = VerdeNeon,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Animaciones",
                color = TextoClaro,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = "Jetpack Compose",
                color = AzulNeon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Laboratorio 13 · Elige un ejercicio para ver la animación en acción.",
                color = TextoSecundario,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(14.dp))
        }

        items(ITEMS, key = { it.pantalla.name }) { item ->
            TarjetaMenu(item = item, onClick = { onAbrir(item.pantalla) })
        }
    }
}

@Composable
private fun TarjetaMenu(item: ItemMenu, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        color = FondoTarjeta.copy(alpha = 0.92f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .border(1.dp, item.color.copy(alpha = 0.28f), RoundedCornerShape(20.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(item.color.copy(alpha = 0.14f))
                    .border(1.dp, item.color.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.numero,
                    color = item.color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.titulo,
                    color = TextoClaro,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = item.descripcion,
                    color = TextoSecundario,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
            Icon(
                imageVector = item.icono,
                contentDescription = "Abrir",
                tint = item.color,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun PantallaBase(
    titulo: String,
    subtitulo: String,
    color: Color,
    onVolver: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(FondoTarjeta)
                    .border(1.dp, color.copy(alpha = 0.35f), CircleShape)
                    .clickable(onClick = onVolver),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = titulo,
                    color = TextoClaro,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = subtitulo,
                    color = TextoSecundario,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(Modifier.height(22.dp))
        content()
        Spacer(Modifier.height(24.dp))
    }
}
