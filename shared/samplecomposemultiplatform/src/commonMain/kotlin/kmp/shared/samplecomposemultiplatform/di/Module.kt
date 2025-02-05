package kmp.shared.samplecomposemultiplatform.di

import kmp.shared.samplecomposemultiplatform.data.repository.TasksRepositoryImpl
import kmp.shared.samplecomposemultiplatform.data.source.TaskSource
import kmp.shared.samplecomposemultiplatform.domain.repository.TasksRepository
import kmp.shared.samplecomposemultiplatform.domain.usecase.GetTasksUseCase
import kmp.shared.samplecomposemultiplatform.domain.usecase.GetTasksUseCaseImpl
import kmp.shared.samplecomposemultiplatform.infrastructure.remote.TaskService
import kmp.shared.samplecomposemultiplatform.infrastructure.source.TasksSourceImpl
import kmp.shared.samplecomposemultiplatform.presentation.vm.TodoListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sampleComposeMultiplatformModule = module {
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

}
