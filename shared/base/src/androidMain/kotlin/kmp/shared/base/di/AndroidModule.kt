package kmp.shared.base.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.ktor.client.engine.android.Android
import kmp.shared.base.infrastructure.db.AppDatabase
import kmp.shared.base.system.Config
import kmp.shared.base.system.ConfigImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformModule = module {
    singleOf(::ConfigImpl) bind Config::class
    single { Android.create() }
    single<AppDatabase> {
        getDatabaseBuilder(get()).build()
    }
}

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("todo.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}