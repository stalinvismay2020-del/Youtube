package ai.creatorpilot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF0A0D16)
private val Panel = Color(0xFF151B2B)
private val Accent = Color(0xFF61D7F5)
private val Muted = Color(0xFF9AA8BE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(
                background = Ink, surface = Panel, primary = Accent,
                onBackground = Color.White, onSurface = Color.White
            )) { CreatorPilotApp() }
        }
    }
}

@Composable
private fun CreatorPilotApp() {
    var tab by rememberSaveable { mutableStateOf("Home") }
    Surface(Modifier.fillMaxSize(), color = Ink) {
        Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(Modifier.height(28.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("CREATORPILOT", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text("Your creator command centre", color = Muted, fontSize = 13.sp)
                }
                Text("AI", color = Accent, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.height(22.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp), modifier = Modifier.fillMaxWidth()) {
                listOf("Home", "Audit", "Ideas", "Tools").forEach { item ->
                    Button(
                        onClick = { tab = item },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tab == item) Accent else Panel,
                            contentColor = if (tab == item) Ink else Color.White
                        )
                    ) { Text(item, fontSize = 10.sp, fontWeight = FontWeight.SemiBold) }
                }
            }
            Spacer(Modifier.height(18.dp))
            when (tab) {
                "Audit" -> AuditScreen()
                "Ideas" -> IdeasScreen()
                "Tools" -> ToolsScreen()
                else -> HomeScreen(
                    onAudit = { tab = "Audit" },
                    onIdeas = { tab = "Ideas" }
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(onAudit: () -> Unit, onIdeas: () -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(22.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text("YOUR NEXT LEVEL STARTS HERE", color = Accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(Modifier.height(10.dp))
                    Text("Build a channel with a plan.", fontSize = 25.sp, fontWeight = FontWeight.Bold, lineHeight = 31.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Plan smarter content, understand your audience, and improve one upload at a time.", color = Muted, fontSize = 14.sp)
                    Spacer(Modifier.height(18.dp))
                    Button(onClick = onAudit, modifier = Modifier.fillMaxWidth()) { Text("Start channel audit") }
                }
            }
        }
        item { SectionTitle("Creator workspace") }
        item { FeatureCard("Channel insights", "Connect YouTube to view metrics available to your account.", "Connection not set up") }
        item { FeatureCard("Growth roadmap", "Turn channel goals into clear, manageable next steps.", "Create your first plan") }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(18.dp)) {
                    Text("CONTENT PLANNER", color = Accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("Never start with a blank page.", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Explore video prompts and organise your next ideas.", color = Muted, fontSize = 13.sp)
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = onIdeas) { Text("Explore ideas") }
                }
            }
        }
        item { Text("No live channel data yet. Connect YouTube after secure OAuth is configured.", color = Muted, fontSize = 11.sp) }
    }
}

@Composable
private fun AuditScreen() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SectionTitle("Channel audit") }
        item { Text("Start with these fundamentals. Automated scoring will require verified channel data.", color = Muted, fontSize = 14.sp) }
        item { FeatureCard("Channel promise", "Can a new viewer understand who your videos are for?", "Review your niche") }
        item { FeatureCard("Packaging", "Make titles and thumbnails communicate one clear idea.", "Review recent uploads") }
        item { FeatureCard("Consistency", "Choose a realistic publishing rhythm you can maintain.", "Set a schedule") }
        item { FeatureCard("Viewer value", "Give viewers a clear reason to watch and return.", "Define your audience") }
    }
}

@Composable
private fun IdeasScreen() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SectionTitle("Content ideas") }
        item { Text("Starter formats to adapt to your own niche:", color = Muted, fontSize = 14.sp) }
        item { FeatureCard("Beginner's guide", "Explain one useful skill or topic from the ground up.", "Educational format") }
        item { FeatureCard("Challenge with a lesson", "Try a safe, realistic challenge and share what you learned.", "Story format") }
        item { FeatureCard("Myth vs fact", "Compare a common belief with reliable evidence.", "Discovery format") }
        item { FeatureCard("Behind the scenes", "Show the planning and process behind something you create.", "Community format") }
    }
}

@Composable
private fun ToolsScreen() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SectionTitle("Creator tools") }
        item { FeatureCard("Title workshop", "Draft accurate titles that match the actual video.", "Coming in next build") }
        item { FeatureCard("Thumbnail brief", "Plan a readable thumbnail concept before designing it.", "Coming in next build") }
        item { FeatureCard("Description helper", "Organise context, useful links, and chapters.", "Coming in next build") }
        item { FeatureCard("Weekly review", "Reflect on what worked and choose one improvement.", "Coming in next build") }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun FeatureCard(title: String, description: String, status: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.fillMaxWidth().padding(17.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(description, color = Muted, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))
            Text(status.uppercase(), color = Accent, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}
