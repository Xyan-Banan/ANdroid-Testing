package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource

object ServiceLocator {

    private var database: ToDoDatabase? = null

    @Volatile
    private var tasksRepository: TasksRepository? = null

    fun provideTasksRepository(context: Context): TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): TasksRepository {
        val newRepo =
            DefaultTasksRepository(TasksRemoteDataSource, createTasksLocalDataSource(context))
        tasksRepository = newRepo
        return newRepo
    }

    private fun createTasksLocalDataSource(context: Context): TasksDataSource {
        return TasksLocalDataSource((database ?: createDatabase(context)).taskDao())
    }

    private fun createDatabase(context: Context): ToDoDatabase {
        val newDB = Room.databaseBuilder(context, ToDoDatabase::class.java, "Tasks.db").build()
        database = newDB
        return newDB
    }
}