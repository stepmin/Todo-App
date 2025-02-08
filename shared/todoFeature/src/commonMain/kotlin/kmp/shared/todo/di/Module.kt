package kmp.shared.todo.di

import TaskDetailViewModel
import kmp.shared.todo.data.repository.TasksRepositoryImpl
import kmp.shared.todo.data.source.TodoSource
import kmp.shared.todo.domain.repository.TasksRepository
import kmp.shared.todo.domain.usecase.GetTaskDetailUseCase
import kmp.shared.todo.domain.usecase.GetTaskDetailUseCaseImpl
import kmp.shared.todo.domain.usecase.GetTasksUseCase
import kmp.shared.todo.domain.usecase.GetTasksUseCaseImpl
import kmp.shared.todo.infrastructure.db.TaskLocalSource
import kmp.shared.todo.infrastructure.remote.TaskService
import kmp.shared.todo.infrastructure.source.TodoSourceImpl
import kmp.shared.todo.presentation.vm.TaskListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedTasksModule = module {
    // Repositories
    singleOf(::TasksRepositoryImpl) bind TasksRepository::class

    // Sources
    singleOf(::TodoSourceImpl) bind TodoSource::class

    // Remote services
    singleOf(::TaskService)

    // db
    singleOf(::TaskLocalSource)
}

val taskListModule = module {
    // View models
    viewModelOf(::TaskListViewModel)

    // Use cases
    factoryOf(::GetTasksUseCaseImpl) bind GetTasksUseCase::class
}

val taskDetailModule = module {
    viewModelOf(::TaskDetailViewModel)

    // Use cases
    factoryOf(::GetTaskDetailUseCaseImpl) bind GetTaskDetailUseCase::class
}