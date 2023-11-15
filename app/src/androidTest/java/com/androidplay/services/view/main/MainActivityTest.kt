package com.androidplay.services.view.main

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.androidplay.services.R
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@Suppress("SameParameterValue")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun verify_initial_weather_temperature_and_area_name_are_visible() {
        onView(withId(R.id.activity_main_progress)).check(matches(isDisplayed()))
        onView(isRoot()).perform(waitFor(1000)) // To let the data load
        onView(withId(R.id.activity_main_progress)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.activity_main_temperature)).check(matches(withText(any(String::class.java))))
        onView(withId(R.id.activity_main_city_name)).check(matches(withText(any(String::class.java))))
    }


    @Test
    fun verify_when_provided_with_new_query_weather_details_are_visible() {
        // input
        onView(isRoot()).perform(waitFor(1000)) // to let load default location data
        onView(withId(R.id.activity_main_et_area_name)).perform(clearText(), typeText("Bengaluru"))
        onView(withId(R.id.activity_main_bt_fetch)).perform(click())

        // output
        onView(isRoot()).perform(waitFor(1000)) // To let the data load
        onView(withId(R.id.activity_main_temperature)).check(matches(withText(any(String::class.java))))
        onView(withId(R.id.activity_main_city_name)).check(matches(withText("Bengaluru")))
    }

    @Test
    fun verify_when_provided_with_wrong_query_weather_details_are_not_visible() {
        // input
        onView(isRoot()).perform(waitFor(1000)) // to let load default location data
        onView(withId(R.id.activity_main_et_area_name)).perform(clearText(), typeText("Be"))
        onView(withId(R.id.activity_main_bt_fetch)).perform(click())

        // output
        onView(isRoot()).perform(waitFor(1000)) // To let the data load
        onView(withId(R.id.activity_main_temperature)).check(matches(withText("--")))
        onView(withId(R.id.activity_main_city_name)).check(matches(withText("City not found")))
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