package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.ToastMatcher
import androidx.test.espresso.assertion.ViewAssertions.matches
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class RegisterFragmentTest {
    private lateinit var navController : TestNavHostController
    private lateinit var registerScenario : FragmentScenario<RegisterFragment>

    private val correctUsername = "username"
    private val incorrectEmail = "incorrectEmail.com"
    private val correctEmail = "correctEmail@gmail.com"

    @Before
    fun setup() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        registerScenario = launchFragmentInContainer()

        registerScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.registerFragment)
        }
    }


    @Test
    fun goBackFromRegisterToLogIn() {
        Espresso.onView(ViewMatchers.withId(R.id.btnRegisterGoBack)).perform(ViewActions.click())
        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.logInFragment)
    }

    @Test
    fun noUsername() {
        Espresso.onView(ViewMatchers.withId(R.id.btnRegisterSignUp)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.registerFragment)

//        Espresso.onView(ViewMatchers.withText(R.string.enter_username)).inRoot(ToastMatcher())
//            .check(matches(isDisplayed()))
    }

    @Test
    fun usernameAndNoEmail() {

        Espresso.onView(ViewMatchers.withId(R.id.editTextRegisterId))
            .perform(ViewActions.typeText(correctUsername))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnRegisterSignUp)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.enter_email)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }


    @Test
    fun usernameAndIncorrectEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextRegisterId))
            .perform(ViewActions.typeText(correctUsername))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.editTextSetEmail))
            .perform(ViewActions.typeText(incorrectEmail))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnRegisterSignUp)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.invalid_email)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun usernameCorrectEmailAndNoPasswords() {

        Espresso.onView(ViewMatchers.withId(R.id.editTextRegisterId))
            .perform(ViewActions.typeText(correctUsername))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.editTextSetEmail))
            .perform(ViewActions.typeText(correctEmail))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnRegisterSignUp)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.enter_passwords)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testPasswordsNoEqual() {

        Espresso.onView(ViewMatchers.withId(R.id.editTextRegisterId))
            .perform(ViewActions.typeText(correctUsername))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.editTextSetEmail))
            .perform(ViewActions.typeText(correctEmail))

        Espresso.closeSoftKeyboard()

        val password = 1234
        val repeatPassword = 123

        Espresso.onView(ViewMatchers.withId(R.id.editTextSetPassword))
            .perform(ViewActions.typeText(password.toString()))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.editTextRepeatPassword))
            .perform(ViewActions.typeText(repeatPassword.toString()))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnRegisterSignUp)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.passwords_not_equal)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun allFieldsCorrect() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextRegisterId))
            .perform(ViewActions.typeText(correctUsername + (Random.nextLong(100000000, 999999999).toString()) ))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.editTextSetEmail))
            .perform(ViewActions.typeText((Random.nextLong(100000, 999999)).toString() + correctEmail))

        Espresso.closeSoftKeyboard()

        val password = 1234

        Espresso.onView(ViewMatchers.withId(R.id.editTextSetPassword))
            .perform(ViewActions.typeText(password.toString()))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.editTextRepeatPassword))
            .perform(ViewActions.typeText(password.toString()))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnRegisterSignUp)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.logInFragment)
        Espresso.onView(ViewMatchers.withText("Successfully registered!")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

    }


}