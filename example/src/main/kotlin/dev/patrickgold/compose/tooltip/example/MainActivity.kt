package dev.patrickgold.compose.tooltip.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.patrickgold.compose.tooltip.example.ui.theme.ComposeTooltipExampleTheme
import dev.patrickgold.compose.tooltip.example.ui.theme.Theme
import dev.patrickgold.compose.tooltip.tooltip

class MainActivity : ComponentActivity() {
    private var theme by mutableStateOf(Theme.AUTO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = when (theme) {
                Theme.AUTO -> isSystemInDarkTheme()
                Theme.LIGHT -> false
                Theme.DARK -> true
            }
            ComposeTooltipExampleTheme(isDarkTheme) {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(stringResource(R.string.app_name)) },
                                actions = {
                                    IconButton(
                                        onClick = { exampleActionCallback("info") },
                                        modifier = Modifier.tooltip("Show info")
                                    ) {
                                        Icon(Icons.Outlined.Info, "Info")
                                    }
                                    IconButton(
                                        onClick = { exampleActionCallback("mail") },
                                        modifier = Modifier.tooltip("Show mails")
                                    ) {
                                        Icon(Icons.Outlined.MailOutline, "Mail")
                                    }
                                }
                            )
                        },
                    ) { contentPadding ->
                        Column(Modifier.padding(contentPadding)) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .verticalScroll(rememberScrollState()),
                            ) {
                                ThemeChooserBox()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ThemeChooserBox() {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "App theme:",
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    onClick = { theme = Theme.AUTO },
                    colors = with (ButtonDefaults) {
                        if (theme == Theme.AUTO) buttonColors() else outlinedButtonColors()
                    },
                ) {
                    Text(text = "System")
                }
                Button(
                    onClick = { theme = Theme.LIGHT },
                    colors = with (ButtonDefaults) {
                        if (theme == Theme.LIGHT) buttonColors() else outlinedButtonColors()
                    },
                ) {
                    Text(text = "Light")
                }
                Button(
                    onClick = { theme = Theme.DARK },
                    colors = with (ButtonDefaults) {
                        if (theme == Theme.DARK) buttonColors() else outlinedButtonColors()
                    },
                ) {
                    Text(text = "Dark")
                }
            }
        }
    }

    private fun exampleActionCallback(text: String) {
        Toast.makeText(this, "Example button '$text'", Toast.LENGTH_SHORT).show()
    }
}
