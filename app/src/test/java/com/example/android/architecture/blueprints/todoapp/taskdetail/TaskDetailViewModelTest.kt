package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaskDetailViewModelTest {
    private lateinit var taskDetailViewModel: TaskDetailViewModel
    val task1 = Task("Task1", "Descriprtion1")
    val task2 = Task("Task2", "Descriprtion2", true)
    val task3 = Task("Task3", "Descriprtion3")
    val testTask = task1

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val tasks = arrayOf(
            task1,
            task2,
            task3,
        )
        val dataTaskRepository = FakeTestRepository()
        dataTaskRepository.addTasks(*tasks)
        taskDetailViewModel = TaskDetailViewModel(dataTaskRepository)
        taskDetailViewModel.start(testTask.id)
    }

    @Test
    fun refresh_refreshesTask() {
        val value =
            taskDetailViewModel.task.getOrAwaitValue(afterObserve = taskDetailViewModel::refresh)

        assertEquals(testTask, value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteTask_deletesTaskAndTriggersEvent() {
        val value =
            taskDetailViewModel.deleteTaskEvent.getOrAwaitValue(afterObserve = taskDetailViewModel::deleteTask)

        assertNotNull(value.getContentIfNotHandled())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun setCompleted() {
        val value = taskDetailViewModel.snackbarText.getOrAwaitValue {
            runBlockingTest { taskDetailViewModel.setCompleted(true) }
        }

        assertNotNull(value.getContentIfNotHandled())
    }


}