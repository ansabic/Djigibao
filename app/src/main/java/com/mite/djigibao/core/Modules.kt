package com.mite.djigibao.core

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.room.Room
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mite.djigibao.database.core.DjigibaoDatabase
import com.mite.djigibao.repository.songs.ISongsRepository
import com.mite.djigibao.repository.songs.SongsRepositoryImpl
import com.mite.djigibao.repository.todo.ITodoRepository
import com.mite.djigibao.repository.todo.TodoRepositoryImpl
import com.mite.djigibao.repository.users.IUserRepository
import com.mite.djigibao.repository.users.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {


}
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): IUserRepository
    @Binds
    abstract fun providesSongRepository(songsRepositoryImpl: SongsRepositoryImpl): ISongsRepository
    @Binds
    abstract fun providesTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): ITodoRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext
        application: Context
    ) = Room.databaseBuilder(
            application,
            DjigibaoDatabase::class.java,
            "DjigibaoDatabase"
        )
            .build()

    @Provides
    fun providesUserDao(
        database: DjigibaoDatabase
    ) = database.userDao()

    @Provides
    fun providesSongDao(
        database: DjigibaoDatabase
    ) = database.songDao()

    @Provides
    fun providesTodoDao(
        database: DjigibaoDatabase
    ) = database.todoDao()

    @Singleton
    @Provides
    fun providesSharedPreferencesRead(
        @ApplicationContext
        activity: Context
    ) = activity.getSharedPreferences(SHARED_PREFERENCE_BASE,Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providesSharedPreferencesWrite(
        sharedPreferences: SharedPreferences
    ) = sharedPreferences.edit()

}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideFirebaseApp(
        @ApplicationContext
        app: Context) = FirebaseApp.initializeApp(app)

    @Singleton
    @Provides
    fun provideFirestore() = Firebase.firestore
}