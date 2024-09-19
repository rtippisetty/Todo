package com.ranga.todo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ranga.todo.api.model.Todo

@Entity(tableName = "todo_items")
data class TodoItemEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean
)

fun TodoItemEntity.toDomain() = Todo(
    id = id,
    title = title,
    isCompleted = isCompleted
)

fun Todo.toEntity() = TodoItemEntity(
    id = id,
    title = title,
    isCompleted = isCompleted
)