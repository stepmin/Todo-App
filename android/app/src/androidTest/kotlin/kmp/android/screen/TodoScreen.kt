package kmp.android.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import kmp.android.extension.ShortDuration
import kmp.android.extension.hasTestTag
import kmp.android.extension.waitUntilExactlyOneExists
import kmp.shared.samplecomposenavigation.presentation.ui.test.TestTags

internal interface TodoScreen : Screen {
    fun checkThatLoaderIsVisible()
    fun checkThatTodoListIsVisible()
}

private class TodoScreenImpl<A : ComponentActivity>(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
) : TodoScreen {

    override fun checkThatLoaderIsVisible() {
        with(testRule) {
            hasTestTag(TestTags.TodoListScreen.Loader)
        }
    }

    override fun checkThatTodoListIsVisible() {
        with(testRule) {
            waitUntilExactlyOneExists(hasTestTag(TestTags.TodoListScreen.TodoList), ShortDuration)
        }
    }
}

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onSampleSharedViewModelScreen(
    action: TodoScreen.() -> Unit,
) = onScreen(TodoScreenImpl(this), action)

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.performNavigationToSampleSharedViewModel() {
    onNodeWithText("Shared VMs")
        .performClick()
}
