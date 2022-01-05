package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.R

@RunWith(AndroidJUnit4::class)
class LogInFragmentTest {

    private lateinit var navController : TestNavHostController
    private lateinit var logInScenario : FragmentScenario<LogInFragment>


    @Before
    fun setup() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        logInScenario = launchFragmentInContainer<LogInFragment>()

        logInScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun emptyUsernameAndPassword() {
        onView(withId(R.id.buttonLogIn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.logInFragment)
    }

    @Test
    fun existingUsernameAndPasswordLogIn() {

        val username = "cust1"
        val password = 1234

        onView(withId(R.id.editTextUsername)).perform(typeText(username))
        onView(withId(R.id.editTextPassword)).perform(typeText(password.toString()))

        closeSoftKeyboard()

        onView(withId(R.id.buttonLogIn)).perform(click())

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.productsFragment)
    }

    @Test
    fun nonExistingUsernameAndPasswordLogIn() {
        val username = "nonexistingcustomer"
        val password = 1111

        onView(withId(R.id.editTextUsername)).perform(typeText(username))
        onView(withId(R.id.editTextPassword)).perform(typeText(password.toString()))

        closeSoftKeyboard()

        onView(withId(R.id.buttonLogIn)).perform(click())

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.logInFragment)
    }

    @Test
    fun goToRegisterFragment() {
        onView(withId(R.id.buttonSignUp)).perform(click())

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.registerFragment)
    }




}