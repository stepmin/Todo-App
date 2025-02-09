@file:OptIn(ExperimentalCoroutinesApi::class)

package kmp.shared.todo.unit.presentation.vm

import dev.mokkery.answering.calls
import dev.mokkery.every
import dev.mokkery.mock
import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.todo.di.sharedTodoModule
import kmp.shared.todo.di.taskDetailModule
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.repository.TasksRepository
import kmp.shared.todo.presentation.vm.TaskDetailIntent
import kmp.shared.todo.presentation.vm.TaskDetailViewModel
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

class TaskDetailViewModelTest : KoinTest {

    internal val taskRepositoryMock = mock<TasksRepository> {}

    private val testModule = module {
        single<TasksRepository> { taskRepositoryMock }
    }

    @BeforeTest
    fun setUp() {
        // Start Koin
        startKoin {
            modules(
                sharedTodoModule,
                taskDetailModule,
                testModule,
            )
        }
        // Set the main dispatcher for testing
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `data are successfully transformed to state`() = runTest {
        val data = TaskDetail(
            id = 1,
            userId = 5,
            title = "title",
            completed = false,
            name = "Full Name",
            username = "Username",
            email = "username@zzz.cz",
        )

        every { taskRepositoryMock.observeTaskDetail(TaskDetailRequest(5, 5)) } calls {
            flowOf(Result.Success(data))
        }

        val viewModel = get<TaskDetailViewModel>()

        advanceUntilIdle()

        assertTrue(viewModel.state.value.taskDetail != null)

        val expectedError = CommonError.NetworkConnectionError(RuntimeException(""))

        every { taskRepositoryMock.observeTaskDetail(TaskDetailRequest(5, 5)) } calls {
            flowOf(Result.Error(expectedError))
        }

        viewModel.onIntent(TaskDetailIntent.OnInit)

        advanceUntilIdle()

        val uiState = viewModel.state.value
        assertTrue(uiState.error == expectedError, "value is: $uiState")
    }

    @AfterTest
    fun tearDown() {
        // Stop Koin
        stopKoin()
        Dispatchers.resetMain()
    }

}