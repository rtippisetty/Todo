package com.ranga.todo.di

import com.ranga.todo.api.GetTodoItemsUseCase
import com.ranga.todo.api.SaveTodoItemUseCase
import com.ranga.todo.domain.GetTodoItemsUseCaseImpl
import com.ranga.todo.domain.SaveTodoItemUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface TodoDomainModule {
    @Binds
    fun bindGetTodoItemsUseCase(impl: GetTodoItemsUseCaseImpl): GetTodoItemsUseCase

    @Binds
    fun bindSaveTodoItemUseCase(impl: SaveTodoItemUseCaseImpl): SaveTodoItemUseCase

}