@file:OptIn(ExperimentalCoroutinesApi::class)

package kmp.shared.samplecomposemultiplatform.presentation.vm

import dev.mokkery.answering.calls
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kmp.shared.base.Result
import kmp.shared.samplecomposemultiplatform.data.source.TaskSource
import kmp.shared.samplecomposemultiplatform.di.sampleComposeMultiplatformModule
import kmp.shared.samplecomposemultiplatform.domain.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TodoListViewModelTest : KoinTest {

    internal val sourceMock = mock<TaskSource> {}

    private val testModule = module {
        single<TaskSource> { sourceMock }
    }

    @BeforeTest
    fun setUp() {
        // Start Koin
        startKoin {
            modules(
                sampleComposeMultiplatformModule,
                testModule
            )
        }
        // Set the main dispatcher for testing
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `data are successfully transformed to state`() = runTest {
        everySuspend { sourceMock.getTasks() } calls {
            Result.Success(listOf<Task>(
                Task(id = 1, userId = 5, title = "title", completed = false),
                Task(id = 2, userId = 5, title = "title", completed = false),
                Task(id = 3, userId = 5, title = "title", completed = false),
            ))
        }

        val viewModel = get<TodoListViewModel>()

        viewModel.onIntent(TodoListIntent.OnAppeared)

        runCurrent()

        assertTrue(viewModel.state.value.task?.isNotEmpty() == true)
    }

    @AfterTest
    fun tearDown() {
        // Stop Koin
        stopKoin()
        Dispatchers.resetMain()
    }

}