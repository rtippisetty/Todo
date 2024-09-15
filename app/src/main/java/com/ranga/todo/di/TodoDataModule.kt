package com.ranga.todo.di

import com.ranga.todo.data.TodoRepositoryImpl
import com.ranga.todo.domain.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TodoDataModule {
    @Singleton
    @Binds
    fun bindTodoRepository(impl: TodoRepositoryImpl): TodoRepository
}