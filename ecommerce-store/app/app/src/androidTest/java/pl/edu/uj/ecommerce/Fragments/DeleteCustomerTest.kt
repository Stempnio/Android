package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.ToastMatcher
import pl.edu.uj.ecommerce.addTestCustomer
import pl.edu.uj.ecommerce.addTestProduct


@RunWith(AndroidJUnit4::class)
class DeleteCustomerTest {

    private lateinit var navController : TestNavHostController
    private lateinit var productsScenario : FragmentScenario<DeleteAccountFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        productsScenario = launchFragmentInContainer()

        productsScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.deleteAccountFragment)
        }
    }


    @Test
    fun testDeleteAccount() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonDeleteAccount))
            .perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.logInFragment)

        Espresso.onView(ViewMatchers.withText("Successfully deleted account!")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

    }
}