package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository

class TasksViewModelFactory(
    private val dataRepository: TasksRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == TasksViewModel::class.java) {
            return TasksViewModel(dataRepository) as T
        } else {
            throw Exception()
        }
    }
}