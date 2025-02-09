package kmp.android.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import kmp.android.extension.ShortDuration
import kmp.android.extension.hasTestTag
import kmp.android.extension.onAllNodesWithTag
import kmp.android.extension.onNodeWithTag
import kmp.android.extension.waitUntilExactlyOneExists
import kmp.shared.todo.presentation.ui.test.TestTags
import kotlin.test.assertTrue

internal interface TaskListScreen : Screen {
    fun checkThatLoaderIsVisible()
    fun checkTaskList()
}

private class TaskListScreenImpl<A : ComponentActivity>(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
) : TaskListScreen {

    override fun checkThatLoaderIsVisible() {
        with(testRule) {
            hasTestTag(TestTags.TaskListScreen.Loader)
        }
    }

    override fun checkTaskList() {
        with(testRule) {
            waitUntilExactlyOneExists(hasTestTag(TestTags.TaskListScreen.List), ShortDuration)
            val taskNodes = onAllNodes(hasTestTag(TestTags.TaskListScreen.Task)).fetchSemanticsNodes()
            //Assert
            assertTrue(taskNodes.isNotEmpty(), "number of items: ${taskNodes.size}")
            onNodeWithTag(testTag = TestTags.TaskListScreen.Loader).isNotDisplayed()
        }
    }
}

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onTaskListScreen(
    action: TaskListScreen.() -> Unit,
) = onScreen(TaskListScreenImpl(this), action)

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.performNavigationToSampleSharedViewModel() {
    onAllNodesWithTag(TestTags.TaskListScreen.Task).onFirst().performClick()
}
