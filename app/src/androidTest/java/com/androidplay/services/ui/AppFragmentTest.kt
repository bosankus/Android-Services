package com.androidplay.services.ui

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.androidplay.services.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test

class AppFragmentTest {

    private lateinit var scenario: FragmentScenario<AppFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun verify_initial_weather_temperature_and_area_name_are_visible() {
        Espresso.onView(withId(R.id.app_fragment_progress))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(1000)) // To let the data load
        Espresso.onView(withId(R.id.app_fragment_temperature))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.any(String::class.java))))
        Espresso.onView(withId(R.id.app_fragment_city_name))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.any(String::class.java))))
    }


    @Test
    fun verify_when_provided_with_new_query_weather_details_are_visible() {
        // input
        Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(1000)) // to let load default location data
        Espresso.onView(withId(R.id.app_fragment_et_area_name))
            .perform(ViewActions.clearText(), ViewActions.typeText("Bengaluru"))
        Espresso.onView(withId(R.id.app_fragment_bt_fetch)).perform(ViewActions.click())

        // output
        Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(1000)) // To let the data load
        Espresso.onView(withId(R.id.app_fragment_temperature))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.any(String::class.java))))
        Espresso.onView(withId(R.id.app_fragment_city_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("Bengaluru")))
    }

    @Test
    fun verify_when_provided_with_wrong_query_weather_details_are_not_visible() {
        // input
        Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(1000)) // to let load default location data
        Espresso.onView(withId(R.id.app_fragment_et_area_name))
            .perform(ViewActions.clearText(), ViewActions.typeText("Be"))
        Espresso.onView(withId(R.id.app_fragment_bt_fetch)).perform(ViewActions.click())

        // output
        Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(1000)) // To let the data load
        Espresso.onView(withId(R.id.app_fragment_temperature))
            .check(ViewAssertions.matches(ViewMatchers.withText("--")))
        Espresso.onView(withId(R.id.app_fragment_city_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("City not found")))
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}