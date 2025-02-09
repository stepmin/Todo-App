@file:OptIn(ExperimentalTestApi::class)

package kmp.android.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import kmp.android.extension.LongDuration
import kmp.android.extension.ShortDuration
import kmp.android.extension.hasTestTag
import kmp.android.extension.onNodeWithTag
import kmp.android.extension.waitUntilExactlyOneExists
import kmp.shared.todo.presentation.ui.test.TestTags

internal interface TaskDetailScreen : Screen {
    fun checkThatLoaderIsVisible()
    fun waitForList()
    fun checkTaskDetail()
}

private class TaskDetailScreenImpl<A : ComponentActivity>(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
) : TaskDetailScreen {

    override fun checkThatLoaderIsVisible() {
        with(testRule) {
            hasTestTag(TestTags.TaskDetailScreen.Loader)
        }
    }

    override fun waitForList() {
        with(testRule) {
            waitUntilAtLeastOneExists(hasTestTag(TestTags.TaskListScreen.List), LongDuration.inWholeMilliseconds)
        }
    }

    override fun checkTaskDetail() {
        with(testRule) {
            waitUntilExactlyOneExists(hasTestTag(TestTags.TaskDetailScreen.TaskDetail), ShortDuration)
            onNodeWithTag(testTag = TestTags.TaskDetailScreen.Loader).isNotDisplayed()
        }
    }
}

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onTaskDetailScreen(
    action: TaskDetailScreen.() -> Unit,
) = onScreen(TaskDetailScreenImpl(this), action)
