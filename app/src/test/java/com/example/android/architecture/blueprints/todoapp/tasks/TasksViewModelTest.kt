package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    // Subject under test
    lateinit var tasksViewModel: TasksViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
//        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
        val tasks = arrayOf(
            Task("Title1", "Description1"),
            Task("Title2", "Description2", true),
            Task("Title3", "Description3"),
        )
        val dataTestRepository = FakeTestRepository()
        dataTestRepository.addTasks(*tasks)
//        val dataTestRepository = DefaultTasksRepository.getRepository(ApplicationProvider.getApplicationContext())
        tasksViewModel = TasksViewModel(dataTestRepository)
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // WHEN adding new task
        val value =
            tasksViewModel.newTaskEvent.getOrAwaitValue(afterObserve = tasksViewModel::addNewTask)

        // THEN newTaskEvent should be triggered
        //should call only one of the assertions because value.getContentIfNotHandled() returns content only once
        assertNotNull(value.getContentIfNotHandled())
//        assertNotSame(null, value.getContentIfNotHandled())
//        assertNotEquals(null,value.getContentIfNotHandled())
//        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
//        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // When the filter type is ALL_TASKS
        val value = tasksViewModel.tasksAddViewVisible.getOrAwaitValue {
            tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        }

        // Then the "Add task" action is visible
        assertEquals(true, value)
    }
}