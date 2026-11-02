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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF080B12)
private val Panel = Color(0xFF131A27)
private val PanelAlt = Color(0xFF1A2434)
private val Cyan = Color(0xFF62D9F5)
private val TextMuted = Color(0xFF9BAAC0)
private val Green = Color(0xFF83D6B0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Cyan, onPrimary = Background, background = Background,
                    onBackground = Color.White, surface = Panel, onSurface = Color.White,
                    secondary = Green
                )
            ) { CreatorPilotApp() }
        }
    }
}

private val tabs = listOf("Home", "Audit", "Studio", "Plan")

@Composable
private fun CreatorPilotApp() {
    var selectedTab by rememberSaveable { mutableStateOf("Home") }
    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar(containerColor = Panel) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Text(tabIcon(tab), fontSize = 18.sp) },
                        label = { Text(tab, fontSize = 11.sp) }
                    )
                }
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 18.dp)) {
            Spacer(Modifier.height(14.dp))
            Header()
            Spacer(Modifier.height(18.dp))
            when (selectedTab) {
                "Audit" -> AuditScreen()
                "Studio" -> StudioScreen()
                "Plan" -> PlanScreen()
                else -> HomeScreen(
                    onAudit = { selectedTab = "Audit" },
                    onStudio = { selectedTab = "Studio" },
                    onPlan = { selectedTab = "Plan" }
                )
            }
        }
    }
}

private fun tabIcon(tab: String): String = when (tab) {
    "Home" -> "⌂"
    "Audit" -> "◈"
    "Studio" -> "✦"
    else -> "▦"
}

@Composable
private fun Header() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text("CREATORPILOT AI", color = Cyan, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
            Text("Your creator command centre", color = TextMuted, fontSize = 12.sp)
        }
        Surface(color = PanelAlt, shape = CircleShape) {
            Text("CP", color = Cyan, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 13.dp, vertical = 9.dp))
        }
    }
}

@Composable
private fun HomeScreen(onAudit: () -> Unit, onStudio: () -> Unit, onPlan: () -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text("MAKE EVERY UPLOAD COUNT", color = Cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                    Spacer(Modifier.height(10.dp))
                    Text("Create with a plan. Grow with purpose.", fontSize = 25.sp, lineHeight = 31.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("A practical workspace for planning videos, reviewing your channel and improving your next upload.", color = TextMuted, fontSize = 13.sp)
                    Spacer(Modifier.height(17.dp))
                    Button(onClick = onAudit, modifier = Modifier.fillMaxWidth()) { Text("Run my channel checklist") }
                }
            }
        }
        item { SectionHeading("Your workspace") }
        item { ActionCard("Channel audit", "Review the fundamentals that make a channel clear and watchable.", "OPEN AUDIT", onAudit) }
        item { ActionCard("Idea & title studio", "Turn a topic into video angles and title drafts.", "OPEN STUDIO", onStudio) }
        item { ActionCard("Weekly action plan", "Build a manageable routine instead of guessing what to do next.", "VIEW PLAN", onPlan) }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = PanelAlt), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("CONNECTION STATUS", color = Cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("YouTube is not connected", fontWeight = FontWeight.Bold)
                    Text("This build does not read channel data yet. Secure Google sign-in and official YouTube API access are still required.", color = TextMuted, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun AuditScreen() {
    val checks = listOf(
        "A new viewer can understand my channel in 5 seconds",
        "My recent videos serve a clear audience",
        "Titles accurately explain the value of each video",
        "Thumbnails are readable on a small phone screen",
        "The opening gets to the point quickly",
        "Each video has one clear viewer takeaway",
        "I can maintain my upload schedule"
    )
    val completed = remember { mutableStateListOf<Boolean>().apply { repeat(checks.size) { add(false) } } }
    val score = completed.count { it }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SectionHeading("Channel audit") }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("YOUR SELF-REVIEW", color = Cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(score.toString() + " of " + checks.size + " fundamentals reviewed", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("This is a checklist, not an analytics score or a judgement of your channel.", color = TextMuted, fontSize = 12.sp)
                }
            }
        }
        items(checks.size) { index ->
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(14.dp)) {
                Row(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = completed[index], onCheckedChange = { completed[index] = it })
                    Text(checks[index], color = Color.White, fontSize = 13.sp)
                }
            }
        }
        item { Text("Tip: choose one unchecked item and improve it in your next upload.", color = TextMuted, fontSize = 12.sp) }
    }
}

@Composable
private fun StudioScreen() {
    var topic by rememberSaveable { mutableStateOf("") }
    var niche by rememberSaveable { mutableStateOf("") }
    var generatedIdeas by remember { mutableStateOf(listOf<String>()) }
    var generatedTitles by remember { mutableStateOf(listOf<String>()) }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SectionHeading("Idea & title studio") }
        item { Text("Enter a topic and niche. This offline starter creates editable templates; it is not connected to an AI service yet.", color = TextMuted, fontSize = 12.sp) }
        item {
            OutlinedTextField(value = topic, onValueChange = { topic = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Video topic") }, placeholder = { Text("Example: beginner Minecraft survival") }, singleLine = true)
        }
        item {
            OutlinedTextField(value = niche, onValueChange = { niche = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Your channel niche") }, placeholder = { Text("Example: gaming") }, singleLine = true)
        }
        item {
            Button(
                onClick = {
                    val t = topic.trim().ifBlank { "your next topic" }
                    generatedIdeas = listOf(
                        "Beginner's guide to " + t,
                        "5 mistakes people make with " + t,
                        "I tried " + t + ": what worked and what did not",
                        t + " explained in a simple step-by-step format"
                    )
                    generatedTitles = listOf(
                        t + ": A Beginner's Guide",
                        "5 Things I Wish I Knew About " + t,
                        "I Tried " + t + " — Here's What I Learned"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Generate starter drafts") }
        }
        if (generatedIdeas.isNotEmpty()) {
            item { SectionHeading("Video angles") }
            items(generatedIdeas.size) { index -> SimpleCard(generatedIdeas[index]) }
            item { SectionHeading("Title drafts") }
            items(generatedTitles.size) { index -> SimpleCard(generatedTitles[index]) }
            item { Text("Check every draft against your actual video. Do not use a title that promises something the video does not deliver.", color = TextMuted, fontSize = 12.sp) }
        }
    }
}

@Composable
private fun PlanScreen() {
    val tasks = listOf(
        "Choose one audience problem to solve",
        "Write down three video ideas",
        "Pick one idea and outline the first 30 seconds",
        "Draft a clear, accurate title",
        "Sketch a simple thumbnail concept",
        "Publish when the video is ready",
        "Review available results and note one lesson"
    )
    val done = remember { mutableStateListOf<Boolean>().apply { repeat(tasks.size) { add(false) } } }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SectionHeading("Your weekly action plan") }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("SMALL STEPS. REAL PROGRESS.", color = Cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(done.count { it }.toString() + " / " + tasks.size + " tasks complete", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    Text("Adjust the pace to your time and energy. Consistency should be sustainable.", color = TextMuted, fontSize = 12.sp)
                }
            }
        }
        items(tasks.size) { index ->
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(14.dp)) {
                Row(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = done[index], onCheckedChange = { done[index] = it })
                    Text(tasks[index], fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun SectionHeading(text: String) {
    Text(text, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun ActionCard(title: String, description: String, action: String, onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(description, color = TextMuted, fontSize = 12.sp)
            Spacer(Modifier.height(10.dp))
            Button(onClick = onClick) { Text(action, fontSize = 11.sp) }
        }
    }
}

@Composable
private fun SimpleCard(text: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(14.dp)) {
        Text(text, modifier = Modifier.fillMaxWidth().padding(15.dp), fontSize = 13.sp)
    }
}
