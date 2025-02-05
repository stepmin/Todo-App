package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kmp.shared.samplecomposemultiplatform.presentation.vm.TodoListViewModel
import kmp.shared.samplecomposemultiplatform.presentation.vm.TodoListIntent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SampleComposeMultiplatformScreen(
    viewModel: TodoListViewModel,
    onIntent: (TodoListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(key1 = viewModel) {
        viewModel.onIntent(TodoListIntent.OnAppeared)
    }

/*    LazyColumn {
        items(state.sampleText?.value ?: "") {

        }

    }*/
    /*Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = state.loading, label = "AnimatedLoading") { loading ->
            if (loading) {
                CircularProgressIndicator()
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(
                        text = "This is a sample with compose multiplatform UI and shared VM",
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = state.sampleText?.value ?: "",
                        modifier = Modifier.testTag(TestTags.SampleComposeMultiplatformScreen.SampleText),
                    )

                    var isChecked by remember { mutableStateOf(false) }
                    PlatformSpecificCheckboxView(
                        text = "This is a view implemented in Compose on Android and SwiftUI on iOS",
                        checked = isChecked,
                        onCheckedChanged = { isChecked = it },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                    )

                    StarterButton(onClick = { onIntent(SampleSharedIntent.OnNextButtonTapped) }) {
                        Text(text = "Go to next screen")
                    }
                }
            }
        }
    }*/
}

// Previews do not work for Fleet version 1.38.89 https://slack-chats.kotlinlang.org/t/22778734/are-there-specific-kotlin-ksp-version-requirements-for-getti
@Preview
@Composable
private fun SampleComposeMultiplatformScreen_Preview() {
/*    AppTheme {
        SampleComposeMultiplatformScreen(
            state = SampleSharedState(),
            onIntent = {},
        )
    }*/
}
