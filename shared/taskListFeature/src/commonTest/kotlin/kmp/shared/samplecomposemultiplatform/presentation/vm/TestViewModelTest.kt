@file:OptIn(ExperimentalCoroutinesApi::class)

package kmp.shared.samplecomposemultiplatform.presentation.vm

import dev.mokkery.answering.calls
import dev.mokkery.every
import dev.mokkery.mock
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.taskList.data.source.TaskSource
import kmp.shared.taskList.di.todoModuleModule
import kmp.shared.taskList.domain.model.Task
import kmp.shared.taskList.presentation.vm.TodoListIntent
import kmp.shared.taskList.presentation.vm.TodoListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
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
                todoModuleModule,
                testModule,
            )
        }
        // Set the main dispatcher for testing
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `data are successfully transformed to state`() = runTest {
        every { sourceMock.observeTasks() } calls {
            flowOf(
                Result.Success(
                    listOf<Task>(
                        Task(id = 1, userId = 5, title = "title", completed = false),
                        Task(id = 2, userId = 5, title = "title", completed = false),
                        Task(id = 3, userId = 5, title = "title", completed = false),
                    ),
                ),
            )
        }

        val viewModel = get<TodoListViewModel>()

        viewModel.onIntent(TodoListIntent.OnAppeared)

        advanceUntilIdle()

        assertTrue(viewModel.state.value.tasks?.size == 3)
    }

    @Test
    fun `error response is presented with appropriate state`() = runTest {
        every { sourceMock.observeTasks() } calls {
            flowOf(
                Result.Error(CommonError.NoNetworkConnection(Exception(""))),
            )
        }

        val viewModel = get<TodoListViewModel>()

        viewModel.onIntent(TodoListIntent.OnAppeared)

        advanceUntilIdle()

        assertTrue(viewModel.state.value.error is ErrorResult)
    }

    @AfterTest
    fun tearDown() {
        // Stop Koin
        stopKoin()
        Dispatchers.resetMain()
    }

}