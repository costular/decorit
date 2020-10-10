package com.costular.decorit.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.costular.decorit.core.TestDispatcherProvider
import io.uniflow.test.rule.TestDispatchersRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class ReduxViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val testDispatchersRule = TestDispatchersRule()

    val testDispatcher = testDispatchersRule.testCoroutineDispatcher
    protected val dispatcherProvider = TestDispatcherProvider(testDispatcher)

}

fun ReduxViewModelTest.testBlocking(body: suspend TestCoroutineScope.() -> Unit) {
    testDispatcher.runBlockingTest(body)
}