package kmp.android.test

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kmp.android.MainActivity
import kmp.android.screen.onSampleSharedViewModelScreen
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

internal class TodoListScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    // you can specify what should happen before every test
    fun navigateToSampleSharedViewModel() {

    }

    @Test
    fun basicTests() {
        with(composeTestRule) {
            onSampleSharedViewModelScreen {
                checkThatLoaderIsVisible()
            }
        }
    }

    @Test
    fun testListDisplayed() {
        with(composeTestRule) {
            onSampleSharedViewModelScreen {
                checkTodoList()
            }
        }
    }

    companion object {
        @JvmStatic
        @BeforeClass
        // can be renamed to describe what it does
        fun beforeTest() {
            runBlocking {
                // here comes code that needs to be executed before the test even starts (e.g. logout)
            }
        }
    }
}