@file:OptIn(ExperimentalCoroutinesApi::class, DelicateMokkeryApi::class)

package kmp.shared.todo.unit.domain.usecase

import dev.mokkery.annotations.DelicateMokkeryApi
import dev.mokkery.mock
import kmp.shared.todo.data.source.TasksLocalSource
import kmp.shared.todo.data.source.TasksRemoteSource
import kmp.shared.todo.di.sharedTodoModule
import kmp.shared.todo.di.taskListModule
import kmp.shared.todo.domain.repository.TasksRepository
import kmp.shared.todo.domain.usecase.ChangeTaskStateUseCase
import kmp.shared.todo.infrastructure.remote.TasksService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ChangeTaskStateUseCaseTest : KoinTest {

    internal val tasksService = mock<TasksService> {}
    internal val tasksLocalSource = mock<TasksLocalSource> {}
    internal val tasksRemoteSource = mock<TasksRemoteSource> {}
    internal val repositoryMock = mock<TasksRepository> {}
    internal val changeTaskStateUseCase = mock<ChangeTaskStateUseCase> {}

    private val testModule = module {
        single<TasksService> { tasksService }
        single<TasksLocalSource> { tasksLocalSource }
        single<TasksRemoteSource> { tasksRemoteSource }
        single<TasksRepository> { repositoryMock }
        single<ChangeTaskStateUseCase> { changeTaskStateUseCase }
    }

    @BeforeTest
    fun setUp() {
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
    fun `api method is called when user changes task state`() = runTest {
        //TODO-doesent work
//        val tasksRemoteSource : TasksRemoteSource = get()
//        val changeTaskStateUseCase : ChangeTaskStateUseCase = get()

//        val changeTaskStateUseCase: ChangeTaskStateUseCase = get()

/*        everySuspend {
            changeTaskStateUseCase.invoke(Task(id = 1, userId = 5, title = "title", completed = false))
//            tasksRemoteSource.changeTaskState(5)
        } returns Result.Success(true)

//        everySuspend { changeTaskStateUseCase.invoke(5) } calls {
//            Result.Success(true)
//        }

        changeTaskStateUseCase.invoke(5)

        verifySuspend(mode = exactly(1)) {
            tasksRemoteSource.changeTaskState(5)
        }

        verifySuspend(mode = exactly(1)) {
            tasksRemoteSource.changeTaskState(5)
        }*/
    }
}