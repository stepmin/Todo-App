package kmp.shared.todo.di

import kmp.shared.todo.data.repository.TasksRepositoryImpl
import kmp.shared.todo.data.source.TasksLocalSource
import kmp.shared.todo.data.source.TasksRemoteSource
import kmp.shared.todo.domain.repository.TasksRepository
import kmp.shared.todo.domain.usecase.ChangeTaskStateUseCase
import kmp.shared.todo.domain.usecase.ChangeTaskStateUseCaseImpl
import kmp.shared.todo.domain.usecase.GetTaskDetailUseCase
import kmp.shared.todo.domain.usecase.GetTaskUseCaseImpl
import kmp.shared.todo.domain.usecase.GetTasksUseCase
import kmp.shared.todo.domain.usecase.GetTasksUseCaseImpl
import kmp.shared.todo.infrastructure.db.TasksLocalSourceImpl
import kmp.shared.todo.infrastructure.remote.TasksService
import kmp.shared.todo.infrastructure.remote.TasksServiceImpl
import kmp.shared.todo.infrastructure.source.TasksRemoteSourceImpl
import kmp.shared.todo.presentation.vm.TaskDetailViewModel
import kmp.shared.todo.presentation.vm.TaskListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedTodoModule = module {
    // Repositories
    singleOf(::TasksRepositoryImpl) bind TasksRepository::class

    // Sources
    singleOf(::TasksRemoteSourceImpl) bind TasksRemoteSource::class

    // Remote services
    singleOf(::TasksServiceImpl) bind TasksService::class

    // db
    singleOf(::TasksLocalSourceImpl) bind TasksLocalSource::class
}

val taskListModule = module {
    // View models
    viewModelOf(::TaskListViewModel)

    // Use cases
    factoryOf(::GetTasksUseCaseImpl) bind GetTasksUseCase::class
    factoryOf(::ChangeTaskStateUseCaseImpl) bind ChangeTaskStateUseCase::class
}

val taskDetailModule = module {
    viewModelOf(::TaskDetailViewModel)

    // Use cases
    factoryOf(::GetTaskUseCaseImpl) bind GetTaskDetailUseCase::class
}