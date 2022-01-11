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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*

@RunWith(AndroidJUnit4::class)
class CartFragmentTest {

    private lateinit var navController : TestNavHostController
    private lateinit var productsScenario : FragmentScenario<CartFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()
        addTestCartItem()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        productsScenario = launchFragmentInContainer()

        productsScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.cartFragment)
        }
    }

    @Test
    fun testBuy() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonBuy))
            .perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.buyFragment)
    }

    @Test
    fun testDeleteCartItemAndBuyEmptyCart() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonDeleteFromCart))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.buttonBuy))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Cart is empty!")).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testGoBack() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonGoBack3))
            .perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.productsFragment)
    }



}