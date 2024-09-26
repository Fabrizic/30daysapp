package com.example.a30days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WellnessApp()
        }
    }
}

data class WellnessDay(val day: Int, val activity: String, val tip: String, val imageRes: Int)

@Composable
fun WellnessApp() {
    // List of 30 days with a photo, tip, and activity
    val days = listOf(
        WellnessDay(1, "Medita por 10 minutos", "Inicia tu día con una meditación pacifica.", R.drawable.meditacion),
        WellnessDay(2, "Sal a caminar", "Toma una caminata de 30 minutos a tu ritmo.", R.drawable.f3d9caminar),
        WellnessDay(3, "Bebe un vaso de agua", "Mantente hidratado con 8 vasos de agua diario.", R.drawable.mar),
        WellnessDay(4, "Medita por 20 minutos", "Inicia tu día con una meditación pacifica.", R.drawable.meditacion),
    )

    WellnessScreen(days)
}

@Composable
fun WellnessScreen(days: List<WellnessDay>) {
    var completedDays by remember { mutableStateOf(List(days.size) { false }) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(days) { day ->
            WellnessItem(
                day = day,
                isCompleted = completedDays[day.day - 1],
                onCheckedChange = { checked ->
                    completedDays = completedDays.toMutableList().also { it[day.day - 1] = checked }
                }
            )
        }
    }
}

@Composable
fun WellnessItem(day: WellnessDay, isCompleted: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Image for the day
            Image(
                painter = painterResource(id = day.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            // Day's activity and tip
            Text(text = "Día ${day.day}: ${day.activity}", style = MaterialTheme.typography.titleLarge)
            Text(text = day.tip, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

            // Checkbox to mark as completed
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = onCheckedChange
                )
                Text(text = if (isCompleted) "Completado" else "Incompleto")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WellnessApp()
}
