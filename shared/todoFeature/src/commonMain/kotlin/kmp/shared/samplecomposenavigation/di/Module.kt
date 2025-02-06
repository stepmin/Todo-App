package kmp.shared.samplecomposenavigation.di

import kmp.shared.samplecomposenavigation.data.repository.TasksRepositoryImpl
import kmp.shared.samplecomposenavigation.data.source.TaskSource
import kmp.shared.samplecomposenavigation.domain.repository.TasksRepository
import kmp.shared.samplecomposenavigation.domain.usecase.GetTasksUseCase
import kmp.shared.samplecomposenavigation.domain.usecase.GetTasksUseCaseImpl
import kmp.shared.samplecomposenavigation.infrastructure.db.TaskLocalSource
import kmp.shared.samplecomposenavigation.infrastructure.remote.TaskService
import kmp.shared.samplecomposenavigation.infrastructure.source.TasksSourceImpl
import kmp.shared.samplecomposenavigation.presentation.vm.TodoListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val todoModuleModule = module {
    // View models
    viewModelOf(::TodoListViewModel)

    // Use cases
    factoryOf(::GetTasksUseCaseImpl) bind GetTasksUseCase::class

    // Repositories
    singleOf(::TasksRepositoryImpl) bind TasksRepository::class

    // Sources
    singleOf(::TasksSourceImpl) bind TaskSource::class

    // Remote services
    singleOf(::TaskService)

    // db
    singleOf(::TaskLocalSource)

}