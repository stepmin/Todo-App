package kmp.shared.core.di

import kmp.shared.auth.di.authModule
import kmp.shared.base.di.baseModule
import kmp.shared.todo.di.sharedTodoModule
import kmp.shared.todo.di.taskDetailModule
import kmp.shared.todo.di.taskListModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    val koinApplication = startKoin {
        appDeclaration()
        modules(
            baseModule,
            authModule,
            sharedTodoModule,
            taskListModule,
            taskDetailModule
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    val doOnStartup =
        koin.getOrNull<() -> Unit>() // doOnStartup is a lambda which is implemented in Swift on iOS side
    doOnStartup?.invoke()

    return koinApplication
}
