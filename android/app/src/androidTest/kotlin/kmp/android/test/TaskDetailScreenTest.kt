@file:OptIn(ExperimentalTestApi::class)

package kmp.android.test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kmp.android.MainActivity
import kmp.android.screen.onTaskDetailScreen
import kmp.android.screen.performNavigationToSampleSharedViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaskDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    // you can specify what should happen before every test
    fun navigateToSampleSharedViewModel() {
    }

    @Test
    fun basicTests() {
        with(composeTestRule) {
            onTaskDetailScreen {
                checkThatLoaderIsVisible()
                waitForList()
                performNavigationToSampleSharedViewModel()
                checkTaskDetail()
            }
        }
    }
}