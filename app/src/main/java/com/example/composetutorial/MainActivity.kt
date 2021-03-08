package com.example.composetutorial

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomDialogLayout()
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun BottomDialogLayout() {
        val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()
        ModalBottomSheetLayout(
            sheetState = state,
            sheetShape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp
            ),
            sheetContent = {
                BottomDialog()
            }
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.HOME.name) {
                composable(Screen.HOME.name) { HomeScreen(state = state, navController = navController)}
                composable(Screen.SECOND.name) { SecondScreen()}
            }

        }
    }

    @ExperimentalMaterialApi
    @Composable
    private fun HomeScreen(
        state: ModalBottomSheetState,
        navController: NavController? = null
    ) {
        ComposeTutorialTheme() {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = { showDialog(state) }
                ) {
                    Text("Show dialog")
                }
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = { navController?.let { goToSecondScreen(it) } }
                ) {
                    Text("Go to second screen")
                }
            }
        }
    }

    @Composable
    private fun SecondScreen() {
        ComposeTutorialTheme() {
            Surface(
                color = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.primary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Second Screen!")
                }
            }
        }
    }

    @Composable
    fun BottomDialog() {
        ComposeTutorialTheme() {
            Surface() {
                Column(
                    modifier = Modifier
                        .height(128.dp)
                ) {
                    Text(
                        text = "Hello!",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }

    @ExperimentalMaterialApi
    private fun showDialog(state: ModalBottomSheetState) {
        lifecycleScope.launch {
            if (!state.isVisible) state.show()
        }
    }

    private fun goToSecondScreen(navController: NavController) {
        navController.navigate(Screen.SECOND.name) {
            popUpTo(Screen.HOME.name) {}
        }
    }

    @ExperimentalMaterialApi
    @Preview(showSystemUi = true)
    @Composable
    fun HomePreview() {
        HomeScreen(ModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden))
    }

    @ExperimentalMaterialApi
    @Preview(showSystemUi = true)
    @Composable
    fun SecondScreenPreview() {
        SecondScreen()
    }

    @ExperimentalMaterialApi
    @Preview()
    @Composable
    fun BottomDialogPreview() {
        BottomDialog()
    }

    enum class Screen {
        HOME,
        SECOND
    }
}