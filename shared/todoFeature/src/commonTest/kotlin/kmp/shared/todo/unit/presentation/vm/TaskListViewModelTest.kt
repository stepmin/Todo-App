@file:OptIn(ExperimentalCoroutinesApi::class)

package kmp.shared.todo.unit.presentation.vm

import androidx.lifecycle.SavedStateHandle
import dev.mokkery.answering.calls
import dev.mokkery.every
import dev.mokkery.mock
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.todo.di.sharedTodoModule
import kmp.shared.todo.di.taskListModule
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.repository.TasksRepository
import kmp.shared.todo.presentation.vm.TaskListViewModel
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

class TaskListViewModelTest : KoinTest {

    internal val taskRepositoryMock = mock<TasksRepository> {}

    private val testModule = module {
        single<SavedStateHandle> { SavedStateHandle() }
        single<TasksRepository> { taskRepositoryMock }
    }

    @BeforeTest
    fun setUp() {
        // Start Koin
        startKoin {
            modules(
                sharedTodoModule,
                taskListModule,
                testModule,
            )
        }
        // Set the main dispatcher for testing
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `data are successfully transformed to state`() = runTest {
        val data = listOf<Task>(
            Task(id = 1, userId = 5, title = "title", completed = false),
            Task(id = 2, userId = 5, title = "title", completed = false),
            Task(id = 3, userId = 5, title = "title", completed = false),
        )
        every { taskRepositoryMock.observeTasks() } calls {
            flowOf(Result.Success(data))
        }

        val viewModel = get<TaskListViewModel>()

        advanceUntilIdle()

        assertTrue(viewModel.state.value.tasks?.size == 3)
        assertTrue(viewModel.state.value.tasks == data)
    }

    @Test
    fun `error response is presented with appropriate state`() = runTest {
        every { taskRepositoryMock.observeTasks() } calls {
            flowOf(
                Result.Error(CommonError.NoNetworkConnection(Exception(""))),
            )
        }

        val viewModel = get<TaskListViewModel>()

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