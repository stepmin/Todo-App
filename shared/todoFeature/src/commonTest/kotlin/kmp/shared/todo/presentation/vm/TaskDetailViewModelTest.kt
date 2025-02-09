@file:OptIn(ExperimentalCoroutinesApi::class)

package kmp.shared.todo.presentation.vm

import dev.mokkery.answering.calls
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.todo.data.source.TodoSource
import kmp.shared.todo.di.sharedTasksModule
import kmp.shared.todo.di.taskDetailModule
import kmp.shared.todo.domain.model.DetailRequest
import kmp.shared.todo.domain.model.TaskDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    internal val sourceMock = mock<TodoSource> {}

    private val testModule = module {
        single<TodoSource> { sourceMock }
    }

    @BeforeTest
    fun setUp() {
        // Start Koin
        startKoin {
            modules(
                sharedTasksModule,
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
            title = "title",
            completed = false,
            name = "Full Name",
            username = "Username",
            email = "username@zzz.cz",
        )

        everySuspend { sourceMock.getTaskDetail(DetailRequest(5, 5)) } calls {
            Result.Success(data)
        }

        val viewModel = get<TaskDetailViewModel>()

        viewModel.onIntent(TaskDetailIntent.OnAppeared)

        advanceUntilIdle()

        var uiState = viewModel.state.value
        assertTrue(uiState.taskDetail == data, "value is: $uiState")

        val expectedError = CommonError.NetworkConnectionError(RuntimeException(""))

        everySuspend { sourceMock.getTaskDetail(DetailRequest(5, 5)) } calls {
            Result.Error(expectedError)
        }

        viewModel.onIntent(TaskDetailIntent.OnAppeared)

        advanceUntilIdle()

        uiState = viewModel.state.value
        assertTrue(uiState.error == expectedError, "value is: $uiState")
    }

    @AfterTest
    fun tearDown() {
        // Stop Koin
        stopKoin()
        Dispatchers.resetMain()
    }

}