package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*
import pl.edu.uj.ecommerce.Data.*

@RunWith(AndroidJUnit4::class)
class ChangePasswordTest {

    private lateinit var navController : TestNavHostController
    private lateinit var productsScenario : FragmentScenario<UpdateCustomerFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        productsScenario = launchFragmentInContainer()

        productsScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.updateCustomerFragment)
        }
    }

    @Test
    fun testChangePasswordCorrect() {

        val password = "1111"

        Espresso.onView(ViewMatchers.withId(R.id.editTextUpdatePassword))
            .perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.editTextUpdatePasswordRepeat))
            .perform(ViewActions.typeText(password))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonUpdatePassword))
            .perform(ViewActions.click())

        val customer = RetrofitService
            .create()
            .getCustomerByIdCall(CURRENT_CUSTOMER_ID)
            .execute()
            .body()

        assert(customer != null)
        if (customer != null) {
            assert(customer.password == password)
        }

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.productsFragment)
    }

    @Test
    fun testChangePasswordEmpty() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonUpdatePassword))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.buttonUpdatePassword))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Enter your password!")).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.updateCustomerFragment)
    }

    @Test
    fun testChangePasswordNotEqual() {
        val password1 = "1111"
        val password2 = "11111"

        Espresso.onView(ViewMatchers.withId(R.id.editTextUpdatePassword))
            .perform(ViewActions.typeText(password1))
        Espresso.onView(ViewMatchers.withId(R.id.editTextUpdatePasswordRepeat))
            .perform(ViewActions.typeText(password2))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonUpdatePassword))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Passwords are not equal!")).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.updateCustomerFragment)
    }
}