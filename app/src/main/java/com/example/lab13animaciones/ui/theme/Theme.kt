package com.example.lab13animaciones.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TarjetaVariante = Color(0xFF1A2542)

private val EsquemaOscuro = darkColorScheme(
    primary = AzulNeon,
    onPrimary = FondoOscuro,
    secondary = VerdeNeon,
    onSecondary = FondoOscuro,
    tertiary = VioletaNeon,
    onTertiary = FondoOscuro,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = FondoTarjeta,
    onSurface = TextoClaro,
    surfaceVariant = TarjetaVariante,
    onSurfaceVariant = TextoSecundario
)

@Composable
fun Lab13Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaOscuro,
        typography = Typography,
        content = content
    )
}
