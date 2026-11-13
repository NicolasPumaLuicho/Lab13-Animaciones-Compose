package com.example.lab13animaciones.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13animaciones.ui.theme.FondoOscuro
import com.example.lab13animaciones.ui.theme.FondoTarjeta
import com.example.lab13animaciones.ui.theme.TextoClaro
import com.example.lab13animaciones.ui.theme.TextoSecundario

@Composable
fun TarjetaDemo(
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = FondoTarjeta.copy(alpha = 0.9f),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, color.copy(alpha = 0.22f), RoundedCornerShape(24.dp))
                .padding(18.dp),
            content = content
        )
    }
}

@Composable
fun Etiqueta(texto: String, color: Color) {
    Text(
        text = texto,
        color = color,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.1.sp
    )
}

@Composable
fun BotonPrincipal(
    texto: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = FondoOscuro
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(text = texto, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ChipOpcion(
    texto: String,
    seleccionado: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(50),
        color = if (seleccionado) color.copy(alpha = 0.2f) else Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (seleccionado) color else TextoSecundario.copy(alpha = 0.4f)
        )
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = texto,
                color = if (seleccionado) color else TextoSecundario,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun Nota(texto: String) {
    Text(
        text = texto,
        color = TextoSecundario,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun Punto(descripcion: String, color: Color) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(6.dp)
                .background(color, androidx.compose.foundation.shape.CircleShape)
        )
        Text(
            text = descripcion,
            color = TextoClaro,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}
