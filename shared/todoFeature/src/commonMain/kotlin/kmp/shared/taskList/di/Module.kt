package kmp.shared.taskList.di

import TaskDetailViewModel
import kmp.shared.taskList.data.repository.TasksRepositoryImpl
import kmp.shared.taskList.data.source.TaskSource
import kmp.shared.taskList.domain.repository.TasksRepository
import kmp.shared.taskList.domain.usecase.GetTasksUseCase
import kmp.shared.taskList.domain.usecase.GetTasksUseCaseImpl
import kmp.shared.taskList.infrastructure.db.TaskLocalSource
import kmp.shared.taskList.infrastructure.remote.TaskService
import kmp.shared.taskList.infrastructure.source.TasksSourceImpl
import kmp.shared.taskList.presentation.vm.TaskListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val taskListModule = module {
    // View models
    viewModelOf(::TaskListViewModel)

    // Use cases
    factoryOf(::GetTasksUseCaseImpl) bind GetTasksUseCase::class

    // Repositories
    singleOf(::TasksRepositoryImpl) bind TasksRepository::class

    // Sources
    singleOf(::TasksSourceImpl) bind TaskSource::class

    // Remote services
    singleOf(::TaskService)

    // db
    //TODO-DB
    singleOf(::TaskLocalSource)

}

val taskDetailModule = module {
    viewModelOf(::TaskDetailViewModel)
}