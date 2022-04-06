package com.androidplay.services.view.main

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.androidplay.services.R
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test

@Suppress("SameParameterValue")
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun test_ui_flow_when_activity_isShown() {
        onView(withId(R.id.activity_main_progress)).check(matches(isDisplayed()))
        onView(isRoot()).perform(waitFor(1000)) // To let the data load
        onView(withId(R.id.activity_main_progress)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.activity_main_temperature)).check(matches(withText("-273°C")))
        onView(withId(R.id.activity_main_city_name)).check(matches(withText("Kolkata")))
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}