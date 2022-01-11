package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.addTestCustomer
import pl.edu.uj.ecommerce.addTestOrder
import pl.edu.uj.ecommerce.addTestProduct

@RunWith(AndroidJUnit4::class)
class OrderFragmentTest {

    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<OrdersFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()
        addTestOrder()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.ordersFragment)
        }
    }

    @Test
    fun testGoToTestOrderDetails() {
        onView(withId(R.id.buttonOrderDetails))
            .perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.orderDetailsFragment)
    }

    @Test
    fun testGoBack() {
        onView(withId(R.id.btnOrdersGoBack))
            .perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.productsFragment)
    }
}