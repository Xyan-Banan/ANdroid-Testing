package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import junit.framework.Assert.assertEquals
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class StatisticsUtilsTest{

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros(){
        val tasks = emptyList<Task>()

        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
//        assertEquals(0f, result.completedTasksPercent)
//        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_null_returnsZeros(){
        val tasks = null

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsZeroHundred(){
        val tasks = listOf(Task("title", "description"))

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty(){
        val tasks = listOf(
            Task("title", "description",true),
            Task("title", "description"),
            Task("title", "description", true),
            Task("title", "description"),
            Task("title", "description")
        )

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_both_returnsTwoThirdOneThird(){
        val tasks = listOf(
            Task("title", "description",true),
            Task("title", "description"),
            Task("title", "description", true),
            Task("title", "description"),
            Task("title", "description"),
            Task("title", "description")
        )

        val result = getActiveAndCompletedStats(tasks)
        val oneThird = 100/3f
        val twoThird = oneThird * 2
        assertEquals(oneThird, result.completedTasksPercent)
        assertEquals(twoThird, result.activeTasksPercent)
    }
}